<template>
  <div class="review p-2">
    <div class="d-flex">
      <div>
        <!-- when there is photoId -->
        <img
          v-if="review.byUserPhotoId"
          :src="API_URL + '/files/' + review.byUserPhotoId"
          class="mr-1 user-photo rounded-circle"
        />
        <!-- else -->
        <img
          v-if="!review.byUserPhotoId"
          src="@/assets/images/logo-200x200.png"
          class="mr-1 user-photo rounded-circle"
        />
      </div>
      <div class="ml-2">
        <div>{{ review.byUserFirst }} {{ review.byUserLast }}</div>
        <div class="d-flex align-items-center pt-1">
          <i class="fas fa-comments"></i>
          <span class="ml-1">{{ (review.comments || []).length }}</span>
          <Rating v-model="review.rate" class="ml-2 pt-1" :readonly="true" />
        </div>
        <div>
          <small>{{ review.content }}</small>
        </div>
      </div>

      <span class="flex-grow-1"></span>
      <div v-if="managable" class="manage-review">
        <Button
          type="button"
          icon="pi pi-pencil"
          class="p-button-primary"
          v-on:click="doEdit()"
        />
        <Button
          type="button"
          icon="pi pi-times"
          class="p-button-danger"
          v-on:click="doDelete()"
        />
      </div>
    </div>
    <div class="ml-5">
      <hr v-if="(review.comments || []).length" />
      <CommentView
        v-for="comment of review.comments"
        :key="comment.id"
        :model="comment"
        v-on:doReload="doReload()"
      />
    </div>

    <CommentForm
      v-if="authApi.isLogin"
      :type="type"
      :oId="oId"
      :pId="review.id"
      v-on:doReload="doReload()"
      class="ml-5"
    />

    <Dialog
      v-if="authApi.isLogin"
      :visible.sync="showEditForm"
      :style="{ width: '50vw' }"
      :modal="true"
    >
      <ReviewForm :model="review" v-on:doReload="doReload()" />
    </Dialog>
  </div>
</template>

<script lang="ts">
/* eslint-disable space-before-function-paren */

import { Component, Prop, Vue } from 'vue-property-decorator'
import { AuthService } from '../auth/AuthService'
import { ReviewService } from './ReviewService'
import { Review } from './Review'
import { apiUrl } from '../Api'

@Component
export default class ReviewView extends Vue {
  API_URL = apiUrl

  @Prop() type!: string
  @Prop() oId!: number

  @Prop() indent!: number
  @Prop() model!: Review

  showEditForm = false
  review: Review = {} as any

  authApi = AuthService.getInstance()
  reviewApi = ReviewService.getInstance()

  get managable() {
    return (
      this.authApi.isLogin &&
      this.authApi.response.userId === this.review.byUserId
    )
  }

  mounted() {
    this.review = this.model
  }

  doReload() {
    this.showEditForm = false
    this.$emit('doReload', null)
  }

  doEdit() {
    this.showEditForm = true
  }

  doDelete() {
    if (confirm('Are you sure?')) {
      this.reviewApi
        .delete(this.review.id)
        .then((resp: any) => this.doReload())
        .catch((err: any) => console.log(err))
    }
  }
}
</script>

<style scoped lang="less">
.review {
  background-color: #ecf0f1;
}
.user-photo {
  width: 48px;
  height: 48px;
  border: solid 1px lightgray;
}
.manage-review button {
  height: 19px;
  width: 19px;
}
</style>
