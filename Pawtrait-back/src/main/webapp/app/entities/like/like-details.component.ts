import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILike } from '@/shared/model/like.model';
import LikeService from './like.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LikeDetails extends Vue {
  @Inject('likeService') private likeService: () => LikeService;
  @Inject('alertService') private alertService: () => AlertService;

  public like: ILike = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.likeId) {
        vm.retrieveLike(to.params.likeId);
      }
    });
  }

  public retrieveLike(likeId) {
    this.likeService()
      .find(likeId)
      .then(res => {
        this.like = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
