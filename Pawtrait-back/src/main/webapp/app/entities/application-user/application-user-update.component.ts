import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import { IApplicationUser, ApplicationUser } from '@/shared/model/application-user.model';
import ApplicationUserService from './application-user.service';

const validations: any = {
  applicationUser: {
    profilePicture: {},
    bio: {},
    status: {},
    createdAt: {},
    updatedAt: {},
    deletedAt: {},
  },
};

@Component({
  validations,
})
export default class ApplicationUserUpdate extends Vue {
  @Inject('applicationUserService') private applicationUserService: () => ApplicationUserService;
  @Inject('alertService') private alertService: () => AlertService;

  public applicationUser: IApplicationUser = new ApplicationUser();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.applicationUserId) {
        vm.retrieveApplicationUser(to.params.applicationUserId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.applicationUser.id) {
      this.applicationUserService()
        .update(this.applicationUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A ApplicationUser is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.applicationUserService()
        .create(this.applicationUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A ApplicationUser is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.applicationUser[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.applicationUser[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.applicationUser[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.applicationUser[field] = null;
    }
  }

  public retrieveApplicationUser(applicationUserId): void {
    this.applicationUserService()
      .find(applicationUserId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        res.deletedAt = new Date(res.deletedAt);
        this.applicationUser = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
