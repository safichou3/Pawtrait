/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PhotoTagComponent from '@/entities/photo-tag/photo-tag.vue';
import PhotoTagClass from '@/entities/photo-tag/photo-tag.component';
import PhotoTagService from '@/entities/photo-tag/photo-tag.service';
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
  describe('PhotoTag Management Component', () => {
    let wrapper: Wrapper<PhotoTagClass>;
    let comp: PhotoTagClass;
    let photoTagServiceStub: SinonStubbedInstance<PhotoTagService>;

    beforeEach(() => {
      photoTagServiceStub = sinon.createStubInstance<PhotoTagService>(PhotoTagService);
      photoTagServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PhotoTagClass>(PhotoTagComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          photoTagService: () => photoTagServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      photoTagServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllPhotoTags();
      await comp.$nextTick();

      // THEN
      expect(photoTagServiceStub.retrieve.called).toBeTruthy();
      expect(comp.photoTags[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should load a page', async () => {
      // GIVEN
      photoTagServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(photoTagServiceStub.retrieve.called).toBeTruthy();
      expect(comp.photoTags[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      photoTagServiceStub.retrieve.reset();
      photoTagServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(photoTagServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.photoTags[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
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
      photoTagServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(photoTagServiceStub.retrieve.callCount).toEqual(1);

      comp.removePhotoTag();
      await comp.$nextTick();

      // THEN
      expect(photoTagServiceStub.delete.called).toBeTruthy();
      expect(photoTagServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
