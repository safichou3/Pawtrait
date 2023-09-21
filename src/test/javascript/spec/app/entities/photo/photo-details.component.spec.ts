/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PhotoDetailComponent from '@/entities/photo/photo-details.vue';
import PhotoClass from '@/entities/photo/photo-details.component';
import PhotoService from '@/entities/photo/photo.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Photo Management Detail Component', () => {
    let wrapper: Wrapper<PhotoClass>;
    let comp: PhotoClass;
    let photoServiceStub: SinonStubbedInstance<PhotoService>;

    beforeEach(() => {
      photoServiceStub = sinon.createStubInstance<PhotoService>(PhotoService);

      wrapper = shallowMount<PhotoClass>(PhotoDetailComponent, {
        store,
        localVue,
        router,
        provide: { photoService: () => photoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPhoto = { id: 'ABC' };
        photoServiceStub.find.resolves(foundPhoto);

        // WHEN
        comp.retrievePhoto('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.photo).toBe(foundPhoto);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhoto = { id: 'ABC' };
        photoServiceStub.find.resolves(foundPhoto);

        // WHEN
        comp.beforeRouteEnter({ params: { photoId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.photo).toBe(foundPhoto);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
