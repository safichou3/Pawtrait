<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.like.home.createOrEditLabel" data-cy="LikeCreateUpdateHeading">Create or edit a Like</h2>
        <div>
          <div class="form-group" v-if="like.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="like.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="like-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="like-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.like.createdAt.$invalid, invalid: $v.like.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.like.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="like-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="like-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.like.updatedAt.$invalid, invalid: $v.like.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.like.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="like-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="like-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.like.deletedAt.$invalid, invalid: $v.like.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.like.deletedAt.$model)"
                @change="updateInstantField('deletedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="like-user">User</label>
            <select class="form-control" id="like-user" data-cy="user" name="user" v-model="like.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="like.user && userOption.id === like.user.id ? like.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="like-photo">Photo</label>
            <select class="form-control" id="like-photo" data-cy="photo" name="photo" v-model="like.photo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="like.photo && photoOption.id === like.photo.id ? like.photo : photoOption"
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
            :disabled="$v.like.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./like-update.component.ts"></script>
