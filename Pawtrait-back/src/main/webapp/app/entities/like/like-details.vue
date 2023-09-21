<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="like">
        <h2 class="jh-entity-heading" data-cy="likeDetailsHeading"><span>Like</span> {{ like.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ like.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Updated At</span>
          </dt>
          <dd>
            <span>{{ like.updatedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Deleted At</span>
          </dt>
          <dd>
            <span>{{ like.deletedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            {{ like.user ? like.user.id : '' }}
          </dd>
          <dt>
            <span>Photo</span>
          </dt>
          <dd>
            <div v-if="like.photo">
              <router-link :to="{ name: 'PhotoView', params: { photoId: like.photo.id } }">{{ like.photo.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="like.id" :to="{ name: 'LikeEdit', params: { likeId: like.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./like-details.component.ts"></script>
