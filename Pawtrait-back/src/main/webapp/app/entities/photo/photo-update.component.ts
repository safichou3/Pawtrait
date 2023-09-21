import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';

import { IPhoto, Photo } from '@/shared/model/photo.model';
import PhotoService from './photo.service';

const validations: any = {
  photo: {
    photoUrl: {},
    description: {},
    cuteness: {},
    createdAt: {},
    updatedAt: {},
    deletedAt: {},
  },
};

@Component({
  validations,
})
export default class PhotoUpdate extends Vue {
  @Inject('photoService') private photoService: () => PhotoService;
  @Inject('alertService') private alertService: () => AlertService;

  public photo: IPhoto = new Photo();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.photoId) {
        vm.retrievePhoto(to.params.photoId);
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
    if (this.photo.id) {
      this.photoService()
        .update(this.photo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Photo is updated with identifier ' + param.id;
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
      this.photoService()
        .create(this.photo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Photo is created with identifier ' + param.id;
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
      this.photo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.photo[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.photo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.photo[field] = null;
    }
  }

  public retrievePhoto(photoId): void {
    this.photoService()
      .find(photoId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        res.deletedAt = new Date(res.deletedAt);
        this.photo = res;
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
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
  }
}
