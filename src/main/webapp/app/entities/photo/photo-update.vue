<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.photo.home.createOrEditLabel" data-cy="PhotoCreateUpdateHeading">Create or edit a Photo</h2>
        <div>
          <div class="form-group" v-if="photo.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="photo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-photoUrl">Photo Url</label>
            <input
              type="text"
              class="form-control"
              name="photoUrl"
              id="photo-photoUrl"
              data-cy="photoUrl"
              :class="{ valid: !$v.photo.photoUrl.$invalid, invalid: $v.photo.photoUrl.$invalid }"
              v-model="$v.photo.photoUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="photo-description"
              data-cy="description"
              :class="{ valid: !$v.photo.description.$invalid, invalid: $v.photo.description.$invalid }"
              v-model="$v.photo.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-cuteness">Cuteness</label>
            <input
              type="number"
              class="form-control"
              name="cuteness"
              id="photo-cuteness"
              data-cy="cuteness"
              :class="{ valid: !$v.photo.cuteness.$invalid, invalid: $v.photo.cuteness.$invalid }"
              v-model.number="$v.photo.cuteness.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="photo-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.photo.createdAt.$invalid, invalid: $v.photo.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.photo.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="photo-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.photo.updatedAt.$invalid, invalid: $v.photo.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.photo.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="photo-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.photo.deletedAt.$invalid, invalid: $v.photo.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.photo.deletedAt.$model)"
                @change="updateInstantField('deletedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-user">User</label>
            <select class="form-control" id="photo-user" data-cy="user" name="user" v-model="photo.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="photo.user && userOption.id === photo.user.id ? photo.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="photo-category">Category</label>
            <select class="form-control" id="photo-category" data-cy="category" name="category" v-model="photo.category">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="photo.category && categoryOption.id === photo.category.id ? photo.category : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.id }}
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
            :disabled="$v.photo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./photo-update.component.ts"></script>
