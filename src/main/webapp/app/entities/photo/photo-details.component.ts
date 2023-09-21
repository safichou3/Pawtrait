import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPhoto } from '@/shared/model/photo.model';
import PhotoService from './photo.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PhotoDetails extends Vue {
  @Inject('photoService') private photoService: () => PhotoService;
  @Inject('alertService') private alertService: () => AlertService;

  public photo: IPhoto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.photoId) {
        vm.retrievePhoto(to.params.photoId);
      }
    });
  }

  public retrievePhoto(photoId) {
    this.photoService()
      .find(photoId)
      .then(res => {
        this.photo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
