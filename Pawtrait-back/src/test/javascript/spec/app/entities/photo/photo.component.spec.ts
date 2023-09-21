/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PhotoComponent from '@/entities/photo/photo.vue';
import PhotoClass from '@/entities/photo/photo.component';
import PhotoService from '@/entities/photo/photo.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Photo Management Component', () => {
    let wrapper: Wrapper<PhotoClass>;
    let comp: PhotoClass;
    let photoServiceStub: SinonStubbedInstance<PhotoService>;

    beforeEach(() => {
      photoServiceStub = sinon.createStubInstance<PhotoService>(PhotoService);
      photoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PhotoClass>(PhotoComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          photoService: () => photoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      photoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllPhotos();
      await comp.$nextTick();

      // THEN
      expect(photoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.photos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should load a page', async () => {
      // GIVEN
      photoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(photoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.photos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      photoServiceStub.retrieve.reset();
      photoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(photoServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.photos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      photoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(photoServiceStub.retrieve.callCount).toEqual(1);

      comp.removePhoto();
      await comp.$nextTick();

      // THEN
      expect(photoServiceStub.delete.called).toBeTruthy();
      expect(photoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
