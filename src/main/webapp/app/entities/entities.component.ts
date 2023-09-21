import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import ApplicationUserService from './application-user/application-user.service';
import PhotoService from './photo/photo.service';
import CategoryService from './category/category.service';
import TagService from './tag/tag.service';
import PhotoTagService from './photo-tag/photo-tag.service';
import LikeService from './like/like.service';
import ReportService from './report/report.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('applicationUserService') private applicationUserService = () => new ApplicationUserService();
  @Provide('photoService') private photoService = () => new PhotoService();
  @Provide('categoryService') private categoryService = () => new CategoryService();
  @Provide('tagService') private tagService = () => new TagService();
  @Provide('photoTagService') private photoTagService = () => new PhotoTagService();
  @Provide('likeService') private likeService = () => new LikeService();
  @Provide('reportService') private reportService = () => new ReportService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
