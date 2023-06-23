<script setup>
import {get} from "@/net";
import {ref} from "vue";

// 课程卡片的值
const props = defineProps({
  modelValue: {
    type: Object,
    required: true,
    default: () => { // 默认值 不传的时候触发
      return {
        id: 0,
        name: '',
        academicYear: '',
        academicTerm: '',
        teachingClass: '',
        headcount: 0,
      }
    }
  }
})

const course = ref(props.modelValue)

// 课程加课码
const code = ref({
  id: '',
  code: '',
  disabled: 0,
})
get('/api/code/find?courseId=' + course.value.id, {
  onSuccess: (result) => {
    code.value = result.data
  }
})
</script>

<template>
  <div class="component-home_card">
    <!--  TODO 背景图片  -->
    <div class="header-info" style="">
      <p class="time">{{ course.academicYear + ' ' + course.academicTerm }}</p>
      <h3 :title="course.name" class="name text_overflow1">{{ course.name }}</h3>
      <p :title="course.teachingClass" class="classname text_overflow1">
        {{ course.teachingClass }}</p>
      <div class="qrcode">
        <img src="@/assets/img/qr.0b314396.svg" class="img" alt=""><span style="font-size: 14px;">加课码:{{
          code.code
        }}</span>
      </div>
    </div>
    <div class="content-info"></div>
    <div class="user-info">
      <div class="left"><span style="cursor: pointer;">成员： {{course.headcount}} 人</span></div>
      <div class="right">
        <div class="set-top"><img src="@/assets/img/top.6cb08607.svg" alt="">置顶</div>
        <el-dropdown class="handle" size="large">
          <span class="el-dropdown-link el-dropdown-selfdefine">
            <img src="@/assets/img/etc.52da4ee0.svg" alt="">
          </span>
          <template #dropdown>
            <slot name="dropdown"/>
          </template>
        </el-dropdown>
      </div>
    </div>
    <el-dialog align-center>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="large">取消</el-button>
          <el-button type="primary" size="large">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>
