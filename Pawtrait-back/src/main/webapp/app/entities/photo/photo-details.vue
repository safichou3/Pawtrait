<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="photo">
        <h2 class="jh-entity-heading" data-cy="photoDetailsHeading"><span>Photo</span> {{ photo.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Photo Url</span>
          </dt>
          <dd>
            <span>{{ photo.photoUrl }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ photo.description }}</span>
          </dd>
          <dt>
            <span>Cuteness</span>
          </dt>
          <dd>
            <span>{{ photo.cuteness }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ photo.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Updated At</span>
          </dt>
          <dd>
            <span>{{ photo.updatedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Deleted At</span>
          </dt>
          <dd>
            <span>{{ photo.deletedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            {{ photo.user ? photo.user.id : '' }}
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <div v-if="photo.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: photo.category.id } }">{{ photo.category.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="photo.id" :to="{ name: 'PhotoEdit', params: { photoId: photo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./photo-details.component.ts"></script>
