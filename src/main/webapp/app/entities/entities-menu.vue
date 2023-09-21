<template>
  <div>
    <b-dropdown-item to="/application-user">
      <font-awesome-icon icon="asterisk" />
      <span>Application User</span>
    </b-dropdown-item>
    <b-dropdown-item to="/photo">
      <font-awesome-icon icon="asterisk" />
      <span>Photo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span>Category</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tag">
      <font-awesome-icon icon="asterisk" />
      <span>Tag</span>
    </b-dropdown-item>
    <b-dropdown-item to="/photo-tag">
      <font-awesome-icon icon="asterisk" />
      <span>Photo Tag</span>
    </b-dropdown-item>
    <b-dropdown-item to="/like">
      <font-awesome-icon icon="asterisk" />
      <span>Like</span>
    </b-dropdown-item>
    <b-dropdown-item to="/report">
      <font-awesome-icon icon="asterisk" />
      <span>Report</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
