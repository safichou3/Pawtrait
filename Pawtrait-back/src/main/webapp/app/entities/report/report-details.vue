<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="report">
        <h2 class="jh-entity-heading" data-cy="reportDetailsHeading"><span>Report</span> {{ report.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ report.type }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ report.description }}</span>
          </dd>
          <dt>
            <span>Resolution Date</span>
          </dt>
          <dd>
            <span>{{ report.resolutionDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ report.status }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ report.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Updated At</span>
          </dt>
          <dd>
            <span>{{ report.updatedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Deleted At</span>
          </dt>
          <dd>
            <span>{{ report.deletedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Reported By</span>
          </dt>
          <dd>
            {{ report.reportedBy ? report.reportedBy.id : '' }}
          </dd>
          <dt>
            <span>Resolved By</span>
          </dt>
          <dd>
            {{ report.resolvedBy ? report.resolvedBy.id : '' }}
          </dd>
          <dt>
            <span>Photo</span>
          </dt>
          <dd>
            <div v-if="report.photo">
              <router-link :to="{ name: 'PhotoView', params: { photoId: report.photo.id } }">{{ report.photo.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="report.id" :to="{ name: 'ReportEdit', params: { reportId: report.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./report-details.component.ts"></script>
