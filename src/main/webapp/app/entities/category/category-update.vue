<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.category.home.createOrEditLabel" data-cy="CategoryCreateUpdateHeading">Create or edit a Category</h2>
        <div>
          <div class="form-group" v-if="category.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="category.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="category-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="category-name"
              data-cy="name"
              :class="{ valid: !$v.category.name.$invalid, invalid: $v.category.name.$invalid }"
              v-model="$v.category.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="category-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="category-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.category.createdAt.$invalid, invalid: $v.category.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.category.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="category-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="category-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.category.updatedAt.$invalid, invalid: $v.category.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.category.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="category-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="category-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.category.deletedAt.$invalid, invalid: $v.category.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.category.deletedAt.$model)"
                @change="updateInstantField('deletedAt', $event)"
              />
            </div>
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
            :disabled="$v.category.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./category-update.component.ts"></script>
