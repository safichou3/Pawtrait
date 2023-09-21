/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CategoryDetailComponent from '@/entities/category/category-details.vue';
import CategoryClass from '@/entities/category/category-details.component';
import CategoryService from '@/entities/category/category.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Category Management Detail Component', () => {
    let wrapper: Wrapper<CategoryClass>;
    let comp: CategoryClass;
    let categoryServiceStub: SinonStubbedInstance<CategoryService>;

    beforeEach(() => {
      categoryServiceStub = sinon.createStubInstance<CategoryService>(CategoryService);

      wrapper = shallowMount<CategoryClass>(CategoryDetailComponent, {
        store,
        localVue,
        router,
        provide: { categoryService: () => categoryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCategory = { id: 'ABC' };
        categoryServiceStub.find.resolves(foundCategory);

        // WHEN
        comp.retrieveCategory('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.category).toBe(foundCategory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCategory = { id: 'ABC' };
        categoryServiceStub.find.resolves(foundCategory);

        // WHEN
        comp.beforeRouteEnter({ params: { categoryId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.category).toBe(foundCategory);
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
