<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="photoTag">
        <h2 class="jh-entity-heading" data-cy="photoTagDetailsHeading"><span>PhotoTag</span> {{ photoTag.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ photoTag.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Updated At</span>
          </dt>
          <dd>
            <span>{{ photoTag.updatedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Deleted At</span>
          </dt>
          <dd>
            <span>{{ photoTag.deletedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            <div v-if="photoTag.user">
              <router-link :to="{ name: 'TagView', params: { tagId: photoTag.user.id } }">{{ photoTag.user.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Photo</span>
          </dt>
          <dd>
            <div v-if="photoTag.photo">
              <router-link :to="{ name: 'PhotoView', params: { photoId: photoTag.photo.id } }">{{ photoTag.photo.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="photoTag.id" :to="{ name: 'PhotoTagEdit', params: { photoTagId: photoTag.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./photo-tag-details.component.ts"></script>
