<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.applicationUser.home.createOrEditLabel" data-cy="ApplicationUserCreateUpdateHeading">
          Create or edit a ApplicationUser
        </h2>
        <div>
          <div class="form-group" v-if="applicationUser.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="applicationUser.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-profilePicture">Profile Picture</label>
            <input
              type="text"
              class="form-control"
              name="profilePicture"
              id="application-user-profilePicture"
              data-cy="profilePicture"
              :class="{ valid: !$v.applicationUser.profilePicture.$invalid, invalid: $v.applicationUser.profilePicture.$invalid }"
              v-model="$v.applicationUser.profilePicture.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-bio">Bio</label>
            <input
              type="text"
              class="form-control"
              name="bio"
              id="application-user-bio"
              data-cy="bio"
              :class="{ valid: !$v.applicationUser.bio.$invalid, invalid: $v.applicationUser.bio.$invalid }"
              v-model="$v.applicationUser.bio.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="application-user-status"
              data-cy="status"
              :class="{ valid: !$v.applicationUser.status.$invalid, invalid: $v.applicationUser.status.$invalid }"
              v-model="$v.applicationUser.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="application-user-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.applicationUser.createdAt.$invalid, invalid: $v.applicationUser.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.applicationUser.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="application-user-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.applicationUser.updatedAt.$invalid, invalid: $v.applicationUser.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.applicationUser.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="application-user-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.applicationUser.deletedAt.$invalid, invalid: $v.applicationUser.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.applicationUser.deletedAt.$model)"
                @change="updateInstantField('deletedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-user-internalUser">Internal User</label>
            <select
              class="form-control"
              id="application-user-internalUser"
              data-cy="internalUser"
              name="internalUser"
              v-model="applicationUser.internalUser"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  applicationUser.internalUser && userOption.id === applicationUser.internalUser.id
                    ? applicationUser.internalUser
                    : userOption
                "
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
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
            :disabled="$v.applicationUser.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./application-user-update.component.ts"></script>
