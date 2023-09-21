<template>
  <div>
    <h2 id="page-heading" data-cy="LikeHeading">
      <span id="like-heading">Likes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'LikeCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-like">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Like </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && likes && likes.length === 0">
      <span>No likes found</span>
    </div>
    <div class="table-responsive" v-if="likes && likes.length > 0">
      <table class="table table-striped" aria-describedby="likes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('photo.id')">
              <span>Photo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'photo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="like in likes" :key="like.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LikeView', params: { likeId: like.id } }">{{ like.id }}</router-link>
            </td>
            <td>{{ like.createdAt | formatDate }}</td>
            <td>{{ like.updatedAt | formatDate }}</td>
            <td>{{ like.deletedAt | formatDate }}</td>
            <td>
              {{ like.user ? like.user.id : '' }}
            </td>
            <td>
              <div v-if="like.photo">
                <router-link :to="{ name: 'PhotoView', params: { photoId: like.photo.id } }">{{ like.photo.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LikeView', params: { likeId: like.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LikeEdit', params: { likeId: like.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(like)"
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
        ><span id="pawtraitApp.like.delete.question" data-cy="likeDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-like-heading">Are you sure you want to delete this Like?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-like"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeLike()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./like.component.ts"></script>
