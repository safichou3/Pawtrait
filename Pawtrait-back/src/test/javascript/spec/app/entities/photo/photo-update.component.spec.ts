/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import PhotoUpdateComponent from '@/entities/photo/photo-update.vue';
import PhotoClass from '@/entities/photo/photo-update.component';
import PhotoService from '@/entities/photo/photo.service';

import UserService from '@/entities/user/user.service';

import CategoryService from '@/entities/category/category.service';
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
  describe('Photo Management Update Component', () => {
    let wrapper: Wrapper<PhotoClass>;
    let comp: PhotoClass;
    let photoServiceStub: SinonStubbedInstance<PhotoService>;

    beforeEach(() => {
      photoServiceStub = sinon.createStubInstance<PhotoService>(PhotoService);

      wrapper = shallowMount<PhotoClass>(PhotoUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          photoService: () => photoServiceStub,
          alertService: () => new AlertService(),

          userService: () => new UserService(),

          categoryService: () =>
            sinon.createStubInstance<CategoryService>(CategoryService, {
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
        comp.photo = entity;
        photoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(photoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.photo = entity;
        photoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(photoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhoto = { id: 'ABC' };
        photoServiceStub.find.resolves(foundPhoto);
        photoServiceStub.retrieve.resolves([foundPhoto]);

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
