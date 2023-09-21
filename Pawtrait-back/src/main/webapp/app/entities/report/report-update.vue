<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.report.home.createOrEditLabel" data-cy="ReportCreateUpdateHeading">Create or edit a Report</h2>
        <div>
          <div class="form-group" v-if="report.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="report.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="report-type"
              data-cy="type"
              :class="{ valid: !$v.report.type.$invalid, invalid: $v.report.type.$invalid }"
              v-model="$v.report.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="report-description"
              data-cy="description"
              :class="{ valid: !$v.report.description.$invalid, invalid: $v.report.description.$invalid }"
              v-model="$v.report.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-resolutionDate">Resolution Date</label>
            <div class="d-flex">
              <input
                id="report-resolutionDate"
                data-cy="resolutionDate"
                type="datetime-local"
                class="form-control"
                name="resolutionDate"
                :class="{ valid: !$v.report.resolutionDate.$invalid, invalid: $v.report.resolutionDate.$invalid }"
                :value="convertDateTimeFromServer($v.report.resolutionDate.$model)"
                @change="updateInstantField('resolutionDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="report-status"
              data-cy="status"
              :class="{ valid: !$v.report.status.$invalid, invalid: $v.report.status.$invalid }"
              v-model="$v.report.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="report-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.report.createdAt.$invalid, invalid: $v.report.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.report.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="report-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.report.updatedAt.$invalid, invalid: $v.report.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.report.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="report-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.report.deletedAt.$invalid, invalid: $v.report.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.report.deletedAt.$model)"
                @change="updateInstantField('deletedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-reportedBy">Reported By</label>
            <select class="form-control" id="report-reportedBy" data-cy="reportedBy" name="reportedBy" v-model="report.reportedBy">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="report.reportedBy && userOption.id === report.reportedBy.id ? report.reportedBy : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-resolvedBy">Resolved By</label>
            <select class="form-control" id="report-resolvedBy" data-cy="resolvedBy" name="resolvedBy" v-model="report.resolvedBy">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="report.resolvedBy && userOption.id === report.resolvedBy.id ? report.resolvedBy : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="report-photo">Photo</label>
            <select class="form-control" id="report-photo" data-cy="photo" name="photo" v-model="report.photo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="report.photo && photoOption.id === report.photo.id ? report.photo : photoOption"
                v-for="photoOption in photos"
                :key="photoOption.id"
              >
                {{ photoOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.report.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./report-update.component.ts"></script>
