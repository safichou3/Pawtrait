import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import PhotoService from '@/entities/photo/photo.service';
import { IPhoto } from '@/shared/model/photo.model';

import { ILike, Like } from '@/shared/model/like.model';
import LikeService from './like.service';

const validations: any = {
  like: {
    createdAt: {},
    updatedAt: {},
    deletedAt: {},
  },
};

@Component({
  validations,
})
export default class LikeUpdate extends Vue {
  @Inject('likeService') private likeService: () => LikeService;
  @Inject('alertService') private alertService: () => AlertService;

  public like: ILike = new Like();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('photoService') private photoService: () => PhotoService;

  public photos: IPhoto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.likeId) {
        vm.retrieveLike(to.params.likeId);
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
    if (this.like.id) {
      this.likeService()
        .update(this.like)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Like is updated with identifier ' + param.id;
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
      this.likeService()
        .create(this.like)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Like is created with identifier ' + param.id;
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
      this.like[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.like[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.like[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.like[field] = null;
    }
  }

  public retrieveLike(likeId): void {
    this.likeService()
      .find(likeId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        res.deletedAt = new Date(res.deletedAt);
        this.like = res;
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
    this.photoService()
      .retrieve()
      .then(res => {
        this.photos = res.data;
      });
  }
}
