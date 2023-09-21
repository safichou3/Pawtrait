/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import PhotoTagUpdateComponent from '@/entities/photo-tag/photo-tag-update.vue';
import PhotoTagClass from '@/entities/photo-tag/photo-tag-update.component';
import PhotoTagService from '@/entities/photo-tag/photo-tag.service';

import TagService from '@/entities/tag/tag.service';

import PhotoService from '@/entities/photo/photo.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PhotoTag Management Update Component', () => {
    let wrapper: Wrapper<PhotoTagClass>;
    let comp: PhotoTagClass;
    let photoTagServiceStub: SinonStubbedInstance<PhotoTagService>;

    beforeEach(() => {
      photoTagServiceStub = sinon.createStubInstance<PhotoTagService>(PhotoTagService);

      wrapper = shallowMount<PhotoTagClass>(PhotoTagUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          photoTagService: () => photoTagServiceStub,
          alertService: () => new AlertService(),

          tagService: () =>
            sinon.createStubInstance<TagService>(TagService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          photoService: () =>
            sinon.createStubInstance<PhotoService>(PhotoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.photoTag = entity;
        photoTagServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(photoTagServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.photoTag = entity;
        photoTagServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(photoTagServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhotoTag = { id: 'ABC' };
        photoTagServiceStub.find.resolves(foundPhotoTag);
        photoTagServiceStub.retrieve.resolves([foundPhotoTag]);

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
