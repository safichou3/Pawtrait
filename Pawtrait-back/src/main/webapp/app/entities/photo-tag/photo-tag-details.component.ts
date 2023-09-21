import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPhotoTag } from '@/shared/model/photo-tag.model';
import PhotoTagService from './photo-tag.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PhotoTagDetails extends Vue {
  @Inject('photoTagService') private photoTagService: () => PhotoTagService;
  @Inject('alertService') private alertService: () => AlertService;

  public photoTag: IPhotoTag = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.photoTagId) {
        vm.retrievePhotoTag(to.params.photoTagId);
      }
    });
  }

  public retrievePhotoTag(photoTagId) {
    this.photoTagService()
      .find(photoTagId)
      .then(res => {
        this.photoTag = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
