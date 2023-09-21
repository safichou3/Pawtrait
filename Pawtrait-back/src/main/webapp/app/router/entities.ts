import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const ApplicationUser = () => import('@/entities/application-user/application-user.vue');
// prettier-ignore
const ApplicationUserUpdate = () => import('@/entities/application-user/application-user-update.vue');
// prettier-ignore
const ApplicationUserDetails = () => import('@/entities/application-user/application-user-details.vue');
// prettier-ignore
const Photo = () => import('@/entities/photo/photo.vue');
// prettier-ignore
const PhotoUpdate = () => import('@/entities/photo/photo-update.vue');
// prettier-ignore
const PhotoDetails = () => import('@/entities/photo/photo-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// prettier-ignore
const Tag = () => import('@/entities/tag/tag.vue');
// prettier-ignore
const TagUpdate = () => import('@/entities/tag/tag-update.vue');
// prettier-ignore
const TagDetails = () => import('@/entities/tag/tag-details.vue');
// prettier-ignore
const PhotoTag = () => import('@/entities/photo-tag/photo-tag.vue');
// prettier-ignore
const PhotoTagUpdate = () => import('@/entities/photo-tag/photo-tag-update.vue');
// prettier-ignore
const PhotoTagDetails = () => import('@/entities/photo-tag/photo-tag-details.vue');
// prettier-ignore
const Like = () => import('@/entities/like/like.vue');
// prettier-ignore
const LikeUpdate = () => import('@/entities/like/like-update.vue');
// prettier-ignore
const LikeDetails = () => import('@/entities/like/like-details.vue');
// prettier-ignore
const Report = () => import('@/entities/report/report.vue');
// prettier-ignore
const ReportUpdate = () => import('@/entities/report/report-update.vue');
// prettier-ignore
const ReportDetails = () => import('@/entities/report/report-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'application-user',
      name: 'ApplicationUser',
      component: ApplicationUser,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-user/new',
      name: 'ApplicationUserCreate',
      component: ApplicationUserUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-user/:applicationUserId/edit',
      name: 'ApplicationUserEdit',
      component: ApplicationUserUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-user/:applicationUserId/view',
      name: 'ApplicationUserView',
      component: ApplicationUserDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo',
      name: 'Photo',
      component: Photo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo/new',
      name: 'PhotoCreate',
      component: PhotoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo/:photoId/edit',
      name: 'PhotoEdit',
      component: PhotoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo/:photoId/view',
      name: 'PhotoView',
      component: PhotoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category',
      name: 'Category',
      component: Category,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/new',
      name: 'CategoryCreate',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/edit',
      name: 'CategoryEdit',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/view',
      name: 'CategoryView',
      component: CategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tag',
      name: 'Tag',
      component: Tag,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tag/new',
      name: 'TagCreate',
      component: TagUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tag/:tagId/edit',
      name: 'TagEdit',
      component: TagUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tag/:tagId/view',
      name: 'TagView',
      component: TagDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo-tag',
      name: 'PhotoTag',
      component: PhotoTag,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo-tag/new',
      name: 'PhotoTagCreate',
      component: PhotoTagUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo-tag/:photoTagId/edit',
      name: 'PhotoTagEdit',
      component: PhotoTagUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'photo-tag/:photoTagId/view',
      name: 'PhotoTagView',
      component: PhotoTagDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'like',
      name: 'Like',
      component: Like,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'like/new',
      name: 'LikeCreate',
      component: LikeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'like/:likeId/edit',
      name: 'LikeEdit',
      component: LikeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'like/:likeId/view',
      name: 'LikeView',
      component: LikeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'report',
      name: 'Report',
      component: Report,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'report/new',
      name: 'ReportCreate',
      component: ReportUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'report/:reportId/edit',
      name: 'ReportEdit',
      component: ReportUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'report/:reportId/view',
      name: 'ReportView',
      component: ReportDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
