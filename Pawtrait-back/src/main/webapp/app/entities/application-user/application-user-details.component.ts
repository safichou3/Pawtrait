import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApplicationUser } from '@/shared/model/application-user.model';
import ApplicationUserService from './application-user.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ApplicationUserDetails extends Vue {
  @Inject('applicationUserService') private applicationUserService: () => ApplicationUserService;
  @Inject('alertService') private alertService: () => AlertService;

  public applicationUser: IApplicationUser = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.applicationUserId) {
        vm.retrieveApplicationUser(to.params.applicationUserId);
      }
    });
  }

  public retrieveApplicationUser(applicationUserId) {
    this.applicationUserService()
      .find(applicationUserId)
      .then(res => {
        this.applicationUser = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
