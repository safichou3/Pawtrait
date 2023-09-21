/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApplicationUserDetailComponent from '@/entities/application-user/application-user-details.vue';
import ApplicationUserClass from '@/entities/application-user/application-user-details.component';
import ApplicationUserService from '@/entities/application-user/application-user.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ApplicationUser Management Detail Component', () => {
    let wrapper: Wrapper<ApplicationUserClass>;
    let comp: ApplicationUserClass;
    let applicationUserServiceStub: SinonStubbedInstance<ApplicationUserService>;

    beforeEach(() => {
      applicationUserServiceStub = sinon.createStubInstance<ApplicationUserService>(ApplicationUserService);

      wrapper = shallowMount<ApplicationUserClass>(ApplicationUserDetailComponent, {
        store,
        localVue,
        router,
        provide: { applicationUserService: () => applicationUserServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApplicationUser = { id: 'ABC' };
        applicationUserServiceStub.find.resolves(foundApplicationUser);

        // WHEN
        comp.retrieveApplicationUser('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.applicationUser).toBe(foundApplicationUser);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApplicationUser = { id: 'ABC' };
        applicationUserServiceStub.find.resolves(foundApplicationUser);

        // WHEN
        comp.beforeRouteEnter({ params: { applicationUserId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.applicationUser).toBe(foundApplicationUser);
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
