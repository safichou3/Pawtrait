import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import TagService from '@/entities/tag/tag.service';
import { ITag } from '@/shared/model/tag.model';

import PhotoService from '@/entities/photo/photo.service';
import { IPhoto } from '@/shared/model/photo.model';

import { IPhotoTag, PhotoTag } from '@/shared/model/photo-tag.model';
import PhotoTagService from './photo-tag.service';

const validations: any = {
  photoTag: {
    createdAt: {},
    updatedAt: {},
    deletedAt: {},
  },
};

@Component({
  validations,
})
export default class PhotoTagUpdate extends Vue {
  @Inject('photoTagService') private photoTagService: () => PhotoTagService;
  @Inject('alertService') private alertService: () => AlertService;

  public photoTag: IPhotoTag = new PhotoTag();

  @Inject('tagService') private tagService: () => TagService;

  public tags: ITag[] = [];

  @Inject('photoService') private photoService: () => PhotoService;

  public photos: IPhoto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.photoTagId) {
        vm.retrievePhotoTag(to.params.photoTagId);
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
    if (this.photoTag.id) {
      this.photoTagService()
        .update(this.photoTag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A PhotoTag is updated with identifier ' + param.id;
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
      this.photoTagService()
        .create(this.photoTag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A PhotoTag is created with identifier ' + param.id;
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
      this.photoTag[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.photoTag[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.photoTag[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.photoTag[field] = null;
    }
  }

  public retrievePhotoTag(photoTagId): void {
    this.photoTagService()
      .find(photoTagId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        res.deletedAt = new Date(res.deletedAt);
        this.photoTag = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tagService()
      .retrieve()
      .then(res => {
        this.tags = res.data;
      });
    this.photoService()
      .retrieve()
      .then(res => {
        this.photos = res.data;
      });
  }
}
