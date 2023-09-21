<template>
  <div>
    <h2 id="page-heading" data-cy="ReportHeading">
      <span id="report-heading">Reports</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'ReportCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-report"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Report </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && reports && reports.length === 0">
      <span>No reports found</span>
    </div>
    <div class="table-responsive" v-if="reports && reports.length > 0">
      <table class="table table-striped" aria-describedby="reports">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span>Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span>Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('resolutionDate')">
              <span>Resolution Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'resolutionDate'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('reportedBy.id')">
              <span>Reported By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'reportedBy.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('resolvedBy.id')">
              <span>Resolved By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'resolvedBy.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('photo.id')">
              <span>Photo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'photo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in reports" :key="report.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ReportView', params: { reportId: report.id } }">{{ report.id }}</router-link>
            </td>
            <td>{{ report.type }}</td>
            <td>{{ report.description }}</td>
            <td>{{ report.resolutionDate | formatDate }}</td>
            <td>{{ report.status }}</td>
            <td>{{ report.createdAt | formatDate }}</td>
            <td>{{ report.updatedAt | formatDate }}</td>
            <td>{{ report.deletedAt | formatDate }}</td>
            <td>
              {{ report.reportedBy ? report.reportedBy.id : '' }}
            </td>
            <td>
              {{ report.resolvedBy ? report.resolvedBy.id : '' }}
            </td>
            <td>
              <div v-if="report.photo">
                <router-link :to="{ name: 'PhotoView', params: { photoId: report.photo.id } }">{{ report.photo.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ReportView', params: { reportId: report.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ReportEdit', params: { reportId: report.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(report)"
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
        ><span id="pawtraitApp.report.delete.question" data-cy="reportDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-report-heading">Are you sure you want to delete this Report?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-report"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeReport()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./report.component.ts"></script>
