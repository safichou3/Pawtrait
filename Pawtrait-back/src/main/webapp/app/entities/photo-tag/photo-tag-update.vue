<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.photoTag.home.createOrEditLabel" data-cy="PhotoTagCreateUpdateHeading">Create or edit a PhotoTag</h2>
        <div>
          <div class="form-group" v-if="photoTag.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="photoTag.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-tag-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="photo-tag-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.photoTag.createdAt.$invalid, invalid: $v.photoTag.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.photoTag.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-tag-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="photo-tag-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.photoTag.updatedAt.$invalid, invalid: $v.photoTag.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.photoTag.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-tag-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="photo-tag-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.photoTag.deletedAt.$invalid, invalid: $v.photoTag.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.photoTag.deletedAt.$model)"
                @change="updateInstantField('deletedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-tag-user">User</label>
            <select class="form-control" id="photo-tag-user" data-cy="user" name="user" v-model="photoTag.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="photoTag.user && tagOption.id === photoTag.user.id ? photoTag.user : tagOption"
                v-for="tagOption in tags"
                :key="tagOption.id"
              >
                {{ tagOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-tag-photo">Photo</label>
            <select class="form-control" id="photo-tag-photo" data-cy="photo" name="photo" v-model="photoTag.photo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="photoTag.photo && photoOption.id === photoTag.photo.id ? photoTag.photo : photoOption"
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
            :disabled="$v.photoTag.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./photo-tag-update.component.ts"></script>
