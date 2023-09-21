<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pawtraitApp.tag.home.createOrEditLabel" data-cy="TagCreateUpdateHeading">Create or edit a Tag</h2>
        <div>
          <div class="form-group" v-if="tag.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tag.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tag-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="tag-name"
              data-cy="name"
              :class="{ valid: !$v.tag.name.$invalid, invalid: $v.tag.name.$invalid }"
              v-model="$v.tag.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tag-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="tag-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.tag.createdAt.$invalid, invalid: $v.tag.createdAt.$invalid }"
                :value="convertDateTimeFromServer($v.tag.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tag-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="tag-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.tag.updatedAt.$invalid, invalid: $v.tag.updatedAt.$invalid }"
                :value="convertDateTimeFromServer($v.tag.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tag-deletedAt">Deleted At</label>
            <div class="d-flex">
              <input
                id="tag-deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                class="form-control"
                name="deletedAt"
                :class="{ valid: !$v.tag.deletedAt.$invalid, invalid: $v.tag.deletedAt.$invalid }"
                :value="convertDateTimeFromServer($v.tag.deletedAt.$model)"
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
            :disabled="$v.tag.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tag-update.component.ts"></script>
