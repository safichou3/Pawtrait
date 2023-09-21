<template>
  <div>
    <h2 id="page-heading" data-cy="ApplicationUserHeading">
      <span id="application-user-heading">Application Users</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'ApplicationUserCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-application-user"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Application User </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && applicationUsers && applicationUsers.length === 0">
      <span>No applicationUsers found</span>
    </div>
    <div class="table-responsive" v-if="applicationUsers && applicationUsers.length > 0">
      <table class="table table-striped" aria-describedby="applicationUsers">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('profilePicture')">
              <span>Profile Picture</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'profilePicture'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bio')">
              <span>Bio</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bio'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('internalUser.id')">
              <span>Internal User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'internalUser.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="applicationUser in applicationUsers" :key="applicationUser.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApplicationUserView', params: { applicationUserId: applicationUser.id } }">{{
                applicationUser.id
              }}</router-link>
            </td>
            <td>{{ applicationUser.profilePicture }}</td>
            <td>{{ applicationUser.bio }}</td>
            <td>{{ applicationUser.status }}</td>
            <td>{{ applicationUser.createdAt | formatDate }}</td>
            <td>{{ applicationUser.updatedAt | formatDate }}</td>
            <td>{{ applicationUser.deletedAt | formatDate }}</td>
            <td>
              {{ applicationUser.internalUser ? applicationUser.internalUser.id : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ApplicationUserView', params: { applicationUserId: applicationUser.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ApplicationUserEdit', params: { applicationUserId: applicationUser.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(applicationUser)"
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
        ><span id="pawtraitApp.applicationUser.delete.question" data-cy="applicationUserDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-applicationUser-heading">Are you sure you want to delete this Application User?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-applicationUser"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeApplicationUser()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./application-user.component.ts"></script>
