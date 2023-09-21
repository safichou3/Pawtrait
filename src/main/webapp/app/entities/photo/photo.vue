<template>
  <div>
    <h2 id="page-heading" data-cy="PhotoHeading">
      <span id="photo-heading">Photos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PhotoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-photo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Photo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && photos && photos.length === 0">
      <span>No photos found</span>
    </div>
    <div class="table-responsive" v-if="photos && photos.length > 0">
      <table class="table table-striped" aria-describedby="photos">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('photoUrl')">
              <span>Photo Url</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'photoUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span>Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cuteness')">
              <span>Cuteness</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cuteness'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span>Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span>Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deletedAt')">
              <span>Deleted At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deletedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.id')">
              <span>User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category.id')">
              <span>Category</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="photo in photos" :key="photo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PhotoView', params: { photoId: photo.id } }">{{ photo.id }}</router-link>
            </td>
            <td>{{ photo.photoUrl }}</td>
            <td>{{ photo.description }}</td>
            <td>{{ photo.cuteness }}</td>
            <td>{{ photo.createdAt | formatDate }}</td>
            <td>{{ photo.updatedAt | formatDate }}</td>
            <td>{{ photo.deletedAt | formatDate }}</td>
            <td>
              {{ photo.user ? photo.user.id : '' }}
            </td>
            <td>
              <div v-if="photo.category">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: photo.category.id } }">{{ photo.category.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PhotoView', params: { photoId: photo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PhotoEdit', params: { photoId: photo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(photo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="pawtraitApp.photo.delete.question" data-cy="photoDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-photo-heading">Are you sure you want to delete this Photo?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-photo"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePhoto()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./photo.component.ts"></script>
