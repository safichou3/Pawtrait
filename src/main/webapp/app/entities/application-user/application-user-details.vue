<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="applicationUser">
        <h2 class="jh-entity-heading" data-cy="applicationUserDetailsHeading"><span>ApplicationUser</span> {{ applicationUser.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Profile Picture</span>
          </dt>
          <dd>
            <span>{{ applicationUser.profilePicture }}</span>
          </dd>
          <dt>
            <span>Bio</span>
          </dt>
          <dd>
            <span>{{ applicationUser.bio }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ applicationUser.status }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ applicationUser.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Updated At</span>
          </dt>
          <dd>
            <span>{{ applicationUser.updatedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Deleted At</span>
          </dt>
          <dd>
            <span>{{ applicationUser.deletedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Internal User</span>
          </dt>
          <dd>
            {{ applicationUser.internalUser ? applicationUser.internalUser.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="applicationUser.id"
          :to="{ name: 'ApplicationUserEdit', params: { applicationUserId: applicationUser.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./application-user-details.component.ts"></script>
