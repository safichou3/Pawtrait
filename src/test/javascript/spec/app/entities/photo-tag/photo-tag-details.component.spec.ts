/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PhotoTagDetailComponent from '@/entities/photo-tag/photo-tag-details.vue';
import PhotoTagClass from '@/entities/photo-tag/photo-tag-details.component';
import PhotoTagService from '@/entities/photo-tag/photo-tag.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PhotoTag Management Detail Component', () => {
    let wrapper: Wrapper<PhotoTagClass>;
    let comp: PhotoTagClass;
    let photoTagServiceStub: SinonStubbedInstance<PhotoTagService>;

    beforeEach(() => {
      photoTagServiceStub = sinon.createStubInstance<PhotoTagService>(PhotoTagService);

      wrapper = shallowMount<PhotoTagClass>(PhotoTagDetailComponent, {
        store,
        localVue,
        router,
        provide: { photoTagService: () => photoTagServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPhotoTag = { id: 'ABC' };
        photoTagServiceStub.find.resolves(foundPhotoTag);

        // WHEN
        comp.retrievePhotoTag('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.photoTag).toBe(foundPhotoTag);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhotoTag = { id: 'ABC' };
        photoTagServiceStub.find.resolves(foundPhotoTag);

        // WHEN
        comp.beforeRouteEnter({ params: { photoTagId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.photoTag).toBe(foundPhotoTag);
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
