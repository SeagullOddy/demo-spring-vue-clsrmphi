<script setup>
import {ref} from "vue";
import {Search} from "@element-plus/icons-vue";
import HomeCard from "@/components/HomeCard.vue";

const showTabsTip = ref(false)
const showCreateCourseDialog = ref(false)
const showAddCourseDialog = ref(false)
const showTeacherArchiveCourseDialog = ref(false)
const showStudentArchiveCourseDialog = ref(false)
const showDeleteCourseDialog = ref(false)
const showSortCourseDialog = ref(false)
</script>

<template>
  <div class="view-home-box">
    <div class="view-home">
      <div class="home-content">
        <!--    置顶课程    -->
        <div class="header-common-border hasborder">
          <div class="top-handler">
            <h2 class="left">置顶课程</h2>
            <div class="right">
              <el-button size="large" type="primary" id="stu-home-element1" class="el-icon-plus">
                加入课程
              </el-button>
            </div>
          </div>
          <div class="class-box" style="max-height: 320px;">
            <!--    component home card     -->
          </div>
        </div>
        <!--    小标头    -->
        <div class="other-header flex-between">
          <el-tabs tab-position="top" class="right">
            <el-tab-pane label="我学的"></el-tab-pane>
            <el-tab-pane label="我协助的"></el-tab-pane>
          </el-tabs>
          <div class="left flex-between">
            <el-button size="large">归档管理</el-button>
            <el-input class="mgl-24" :suffix-icon="Search" placeholder="搜索我学的课程"
                      size="large"/>
          </div>
        </div>
        <!--    课程列表    -->
        <div class="class-chapter common-border">
          <div class="class-handle">
            <h3 class="left" style="cursor: pointer;"> 2022-2023 第二学期 </h3>
            <div class="right"><!---->
              <button type="button" class="el-button el-button--text el-button--medium"
                      style="display: none;"><!----><i
                  class="el-icon-caret-bottom"></i><span>展开 </span>
              </button>
              <button type="button" class="el-button el-button--text el-button--medium"><!----><i
                  class="el-icon-caret-top"></i><span> 收起 </span>
              </button>
            </div>
          </div>
          <div class="class-box">
            <!--      TODO      -->
            <HomeCard/>
          </div>
        </div>
      </div>
      <!--  tabs tip   -->
      <el-dialog v-model="showTabsTip" class="custom-dialog" draggable>
        <!--   内容     -->
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <div class="footer_right">
                <el-button @click="showTabsTip = false" type="primary" size="large">
                  知道了
                </el-button>
              </div>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--  创建课程  -->
      <el-dialog v-model="showCreateCourseDialog" class="custom-dialog add-classRoom-dialog"
                 title="创建课程" draggable>
        <!--   内容   -->
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <div class="footer_right">
                <el-button size="large" @click="showCreateCourseDialog = false">
                  取消
                </el-button>
                <el-button type="primary" size="large">
                  确定
                </el-button>
              </div>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--  加入课程  -->
      <el-dialog v-model="showAddCourseDialog" title="加入课程"
                 class="custom-dialog" draggable>
        <!--   内容     -->
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <span class="dialog-footer">
                <el-button size="large">取消</el-button>
                <el-button size="large" type="primary">确认</el-button>
              </span>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--  教师归档此课程  -->
      <el-dialog v-model="showTeacherArchiveCourseDialog" title="要归档此课程吗"
                 class="custom-dialog" draggable>
        您可以在 <span
          class="font-color--main">“课程 - 归档管理”</span>中查看此课程 <br><span
          class="font-color--main">【归档全部】</span>学生的课程也会一起被归档 <br><span
          class="font-color--main">【归档自己】</span>学生的课程不会被归档
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <span class="dialog-footer">
                <el-button size="large">取消</el-button>
                <el-button size="large" plain>归档自己</el-button>
                <el-button size="large" type="primary">归档自己</el-button>
              </span>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--  学生归档此课程  -->
      <el-dialog v-model="showStudentArchiveCourseDialog" title="要归档此课程吗"
                 class="custom-dialog" draggable>
        <!--   内容     -->
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <span class="dialog-footer">
                <el-button size="large">取消</el-button>
                <el-button size="large" type="primary">确定</el-button>
              </span>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--  删除课程  -->
      <el-dialog v-model="showDeleteCourseDialog" title="确定删除此课程吗"
                 class="custom-dialog" draggable>
        <p>您的这个课程的任何信息或评论将被永久删除</p>
        <p class="font-color--warning">警告：此操作无法撤销</p>
        <template #footer>
          <div class="dialog-footer dialog-footer-more">
            <div class="footer-left">
              <p>忘记密码？</p>
              <p>可前往<span style="color: rgb(66, 133, 244); cursor: pointer;">账号设置</span>处修改
              </p>
            </div>
            <div class="footer-right">
              <el-button size="large">取消</el-button>
              <el-button type="primary" size="large">删除</el-button>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--  课程排序  -->
      <el-dialog v-model="showSortCourseDialog" title="课程排序"
                 class="custom-dialog" draggable>
        <div class="list-box">
          <ul>
            <!--   课程列表   -->
            <li aria-hidden="true" class="item flex-between">
              <div class="left"
                   style="background-image: url('https://assets.ketangpai.com/theme/min/58.png');">
                  <span class="tag-flag">
                    <span class="text">教</span>
                  </span>
                <p>加课码 4UPWBU</p>
              </div>
              <div class="right">
                <div class="header flex-align flex-between">
                  <span></span>
                </div>
                <div class="name"> 熟悉 互动课堂</div>
                <div class="bottom flex-align">
                  <div class="head-box"></div>
                  成员20人
                </div>
                <i class="icon-move_outline font_family"></i>
              </div>
            </li>
          </ul>
        </div>
        <template #footer>
          <span class="dialog-footer dialog-footer-more">
                <el-button size="large">取消</el-button>
                <el-button type="primary" size="large">确定</el-button>
            </span>
        </template>
      </el-dialog>
    </div>
    <div class="remind-dialog-area">
      <el-dialog title="系统提示">
      </el-dialog>
    </div>
  </div>
</template>

<style>
.view-home-box {
  max-width: 1150px;
  margin: 0 auto
}

.view-home {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between
}

.view-home .card-placeholder {
  width: 260px;
  height: 288px;
  background: #fff;
  border: 1px solid #e2e6ed;
  border-radius: 8px;
  overflow: hidden;
  position: relative
}

.view-home .card-placeholder .card-ph-top {
  cursor: pointer;
  padding: 18px 24px;
  height: 152px;
  background-color: #f2f6fc
}

.view-home .card-placeholder .card-ph-middle {
  cursor: pointer;
  height: 96px;
  padding: 16px 20px
}

.view-home .card-placeholder .card-ph-bottom {
  border-top: 1px solid #dadce0;
  display: flex;
  justify-content: space-between;
  height: 40px;
  padding: 10px 14px 10px 24px;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #3c4043;
  line-height: 16px
}

.view-home .el-button {
  padding: 10px 20px;
  margin-left: 12px
}

.view-home .common-border {
  border: 1px solid #dadce0;
  border-radius: 8px;
  padding: 18px
}

.view-home .noborder {
  border: none !important;
  padding-bottom: 0
}

.view-home .header-common-border {
  border-radius: 8px;
  padding-top: 18px;
  padding-bottom: 8px
}

.view-home .hasborder {
  border: 1px solid #dadce0;
  padding: 18px
}

.view-home .top-handler {
  display: flex;
  justify-content: space-between
}

.view-home .top-handler .left {
  font-size: 20px;
  font-family: PingFangSC, PingFangSC-Medium;
  color: #575a5b;
  font-weight: 500
}

.view-home .top-handler .right {
  display: flex;
  align-items: center;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  color: #4285f4
}

.view-home .top-handler .right i {
  margin: 0
}

.view-home .home-content {
  flex: 1
}

.view-home .home-content .other-header {
  padding-top: 12px;
  padding-bottom: 12px;
  font-size: 20px;
  color: #575a5b;
  line-height: 24px
}

.view-home .home-content .other-header .el-tabs__nav-wrap:after {
  background-color: #e8f0ff
}

.view-home .home-content .other-header .el-input__wrapper {
  border-radius: 51px
}

.view-home .home-content .class-box {
  margin: 8px -10px 0;
  display: flex;
  flex-flow: row wrap;
  overflow-y: auto
}

.view-home .home-content .class-box .component-no_data {
  padding-top: 0;
  margin: 0 auto
}

.view-home .home-content .class-box > div {
  margin: 11px
}

.view-home .home-content .class-chapter {
  background: #f8f9fa;
  margin-bottom: 24px
}

.view-home .home-content .class-chapter .class-handle {
  display: flex;
  flex-flow: nowrap;
  justify-content: space-between
}

.view-home .home-content .class-chapter .class-handle .el-button {
  padding-top: 0;
  padding-bottom: 0
}

.view-home .rule-form {
  padding-bottom: 4px
}

.view-home .rule-form .el-form-item {
  border-radius: 4px 4px 0 0
}

.view-home .rule-form .el-form-item .el-form-item__label {
  padding-left: 16px;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left
}

.view-home .rule-form .cus-formitem {
  display: flex;
  align-items: center
}

.view-home .rule-form .cus-formitem .el-form-item__content {
  flex: 1
}

.add-classRoom-dialog .senior-set {
  color: #2092e6;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 15px;
  display: block
}

.add-classRoom-dialog .senior-set i {
  font-weight: 700;
  margin-left: 8px
}

.add-classRoom-dialog .none-view {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 24px
}

.add-classRoom-dialog .none-view .line-desc {
  display: flex;
  align-items: flex-start;
  margin-bottom: 24px
}

.add-classRoom-dialog .TeachingRadio {
  display: flex;
  align-items: center;
  margin-right: 50px;
  color: #3c4043;
  font-size: 14px
}

.add-classRoom-dialog .TeachingRadio > span {
  margin-right: 15px
}

.add-classRoom-dialog .TeachingTimer {
  display: flex;
  align-items: center
}

.add-classRoom-dialog .TeachingTimer > span {
  color: #3c4043;
  font-size: 14px;
  margin-right: 8px
}

.add-classRoom-dialog .TeachingTimer .TeachingTimer-select {
  position: relative
}

.add-classRoom-dialog .TeachingTimer .TeachingTimer-select .TeachingTimer-input {
  width: 110px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #dadce0;
  border-radius: 4px;
  background: #fff
}

.add-classRoom-dialog .TeachingTimer .TeachingTimer-select .TeachingTimer-input input {
  width: 100px;
  outline: none;
  color: #afb1b3;
  font-size: 12px
}

.add-classRoom-dialog .TeachingTimer .TeachingTimer-select .TeachingTimer-input i {
  cursor: pointer
}

.add-classRoom-dialog .TeachingTimer .TeachingTimer-select .TeachingTimer-slet {
  position: absolute;
  width: 100px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  border: 1px solid #c0c4cc;
  border-top: 0;
  cursor: pointer;
  background: #fff
}

.view-home .tag-flag {
  min-width: 20px;
  max-width: 50px;
  height: 18px;
  line-height: 15px;
  display: inline-block;
  border-radius: 2px;
  color: #fff;
  font-size: 12px;
  text-align: center;
  margin-right: 8px
}

.view-home .tag-flag.role-t {
  background-color: #4285f4;
  border: 1px solid #4285f4
}

.view-home .tag-flag.role-s {
  background-color: #fff;
  border: 1px solid #4285f4;
  color: #4285f4
}

.view-home .tag-flag-pattern {
  width: 62px;
  height: 26px;
  border-radius: 0 0 0 8px;
  border-bottom: 2px solid #fff;
  border-left: 2px solid #fff;
  color: #ffefe5;
  line-height: 26px;
  font-size: 14px;
  text-align: center
}

.view-home .tag-flag-pattern.bg-y {
  background: linear-gradient(270deg, #fac966 0, #ffe1ad);
  color: #6b512e
}

.view-home .tag-flag-pattern.bg-r {
  background-color: #ff6000
}

.view-home .tag-flag-pattern.bg-g {
  background-color: #00ca90
}

.view-home .head-box {
  display: flex;
  align-items: center
}

.view-home .head-box img {
  display: block;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  border: 4px solid #fff
}

.view-home .head-box img:not(:first-child) {
  margin-left: -8px
}

.view-home .margin-bottom {
  margin-bottom: 12px
}

.view-home .margin-bottom-20 {
  margin-bottom: 20px
}

.view-home .margin-top {
  margin-top: 12px
}

.view-home .margin-left {
  margin-left: 12px
}

.view-home .margin-right {
  margin-right: 12px
}

.component-class_order .flip-list-move {
  transition: transform .5s
}

.component-class_order .no-move {
  transition: transform 0s
}

.component-class_order .ghost {
  opacity: .5;
  background: #c8ebfb
}

.component-class_order .el-dialog {
  margin-top: 8vh !important;
  width: 800px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 0 8px 0 #6f6f6f
}

.component-class_order .el-dialog__body {
  height: 70vh;
  overflow: auto
}

.component-class_order .list-box .item {
  cursor: move;
  margin-bottom: 16px;
  width: 736px;
  height: 112px;
  padding: 16px;
  background: #fff;
  border: 1px solid #dadce0;
  border-radius: 4px
}

.component-class_order .list-box .item .left {
  background: no-repeat 50%/cover;
  margin-right: 16px;
  position: relative;
  width: 96px;
  height: 80px;
  background: #faa723;
  border-radius: 8px 8px 0 0
}

.component-class_order .list-box .item .left .tag-flag {
  position: absolute;
  top: 0;
  left: 0;
  background-color: #4285f4;
  border: 1px solid #4285f4
}

.component-class_order .list-box .item .left p {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 96px;
  height: 24px;
  background: rgba(0, 0, 0, .4);
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Semibold;
  font-weight: 600;
  text-align: center;
  color: #fff;
  line-height: 24px
}

.component-class_order .list-box .item .right {
  position: relative;
  flex: 1
}

.component-class_order .list-box .item .right .header {
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #333;
  line-height: 20px
}

.component-class_order .list-box .item .right .name {
  margin-top: 4px;
  font-size: 18px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  color: #333;
  line-height: 24px
}

.component-class_order .list-box .item .right .bottom {
  margin-top: 6px
}

.component-class_order .list-box .item .right .bottom .head-box img {
  width: 20px;
  height: 20px;
  border-width: 2px
}

.component-class_order .list-box .item .right .font_family {
  font-size: 24px;
  position: absolute;
  right: 0;
  top: 50%;
  margin-top: -12px
}

.component-class_order .el-dialog__footer {
  height: 84px;
  padding: 24px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, .1)
}

.component-home_card {
  width: 255px;
  height: 234px;
  background: #fff;
  border: 1px solid #e2e6ed;
  border-radius: 8px;
  overflow: hidden;
  position: relative
}

.component-home_card .tag-flag-pattern {
  position: absolute;
  top: 0;
  right: 0
}

.component-home_card .jigou {
  position: absolute;
  right: -12px;
  top: -3px;
  display: flex;
  padding: 3px;
  border-radius: 3px
}

.component-home_card .jigou img {
  width: 14px;
  margin-right: 4px
}

.component-home_card .default-tag {
  position: absolute;
  right: -5%;
  top: 58%;
  width: 30%
}

.component-home_card .header-info {
  cursor: pointer;
  padding: 18px 24px;
  height: 152px;
  color: #fff;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50%;
  background-color: #3367d5
}

.component-home_card .header-info .time {
  opacity: .6;
  font-size: 12px
}

.component-home_card .header-info .name {
  margin-top: 3px;
  font-size: 18px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  color: #fff;
  line-height: 32px
}

.component-home_card .header-info .classname {
  font-size: 12px
}

.component-home_card .header-info .chapter, .component-home_card .header-info .classname {
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  color: #fff;
  line-height: 24px
}

.component-home_card .header-info .chapter {
  font-size: 16px
}

.component-home_card .header-info .qrcode {
  margin-top: 18px;
  display: flex;
  position: relative;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  color: #fff;
  line-height: 16px
}

.component-home_card .header-info .qrcode .img {
  display: block;
  width: 14px;
  margin-right: 8px
}

.component-home_card .content-info {
  cursor: pointer;
  height: 40px;
  padding: 0 20px 16px 20px
}

.component-home_card .content-info .work {
  display: flex;
  height: 40px;
  align-items: center;
  justify-content: space-between
}

.component-home_card .content-info .work .courseStatus {
  display: flex;
  align-items: center;
  color: #f50;
  font-size: 12px;
  font-weight: 700
}

.component-home_card .content-info .work .courseStatus .live-statusIcon {
  width: 20px;
  height: 20px;
  background: #f50;
  border-radius: 50%;
  text-align: center;
  line-height: 20px;
  margin-right: 8px
}

.component-home_card .content-info .work .courseStatus .live-statusIcon img {
  width: 11px
}

.component-home_card .content-info .work .courseStatus .statusIcon {
  margin-right: 8px
}

.component-home_card .content-info .des {
  margin-top: 8px
}

.component-home_card .content-info .des p {
  font-size: 14px;
  line-height: 20px
}

.component-home_card .content-info .des p, .component-home_card .user-info {
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #3c4043
}

.component-home_card .user-info {
  display: flex;
  justify-content: space-between;
  height: 40px;
  padding: 8px 14px 12px;
  font-size: 12px;
  line-height: 16px
}

.component-home_card .user-info .left, .component-home_card .user-info .right {
  display: flex;
  align-items: center
}

.component-home_card .user-info .right img {
  display: block;
  width: 14px
}

.component-home_card .user-info .right .set-top {
  cursor: pointer;
  display: flex;
  align-items: center
}

.component-home_card .user-info .right .set-top img {
  margin-right: 4px
}

.component-home_card .user-info .right .handle {
  cursor: pointer;
  margin-left: 11px
}

.component-home_card .perfect .el-dialog--center .el-dialog__header {
  display: none
}

.component-home_card .perfect .el-dialog--center .el-dialog__body {
  padding: 0
}

.component-home_card .perfect .el-dialog--center .el-dialog__body .header {
  height: 120px;
  background: rgba(78, 142, 228, .1);
  text-align: center;
  padding-top: 30px
}

.component-home_card .perfect .el-dialog--center .el-dialog__body .header i {
  display: inline-block;
  width: 40px;
  height: 40px;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 40px;
  vertical-align: middle;
  font-size: 24px;
  color: #969696;
  margin-right: 16px
}

.component-home_card .perfect .el-dialog--center .el-dialog__body .header span {
  vertical-align: middle
}

.component-home_card .perfect .el-dialog--center .el-dialog__body .header .txt {
  color: #969696;
  margin-top: 16px
}

.component-home_card .perfect .el-dialog--center .el-dialog__body .form-box {
  padding: 24px
}

.default_add .header-info {
  padding: 0
}

.default_add .header-info img {
  display: block
}

.default_add .content-info {
  padding: 16px 0 0
}

.default_add .content-info p {
  margin-top: 16px;
  color: #4285f4;
  text-align: center;
  line-height: 16px;
  font-size: 16px
}

.default_add .content-info p i {
  font-size: 24px
}

.default_add .user-info {
  border-top-color: transparent
}

.component-teacher_file .el-dialog {
  width: 884px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 0 8px 0 #6f6f6f
}

.component-teacher_file .el-dialog__body {
  height: 505px;
  display: flex;
  flex-flow: column nowrap
}

.component-teacher_file .file-header {
  padding-bottom: 24px;
  border-bottom: 1px solid #dadce0
}

.component-teacher_file .file-header .el-input__inner {
  border-radius: 40px
}

.component-teacher_file .file-box {
  align-items: normal;
  flex: 1
}

.component-teacher_file .file-box .semester {
  border-right: 1px solid #dadce0;
  flex: 1 1 30%;
  max-height: 400px;
  overflow-x: auto
}

.component-teacher_file .file-box .semester .item {
  cursor: pointer;
  padding: 24px;
  font-size: 14px;
  text-align: left;
  color: #3c4043;
  line-height: 16px;
  border-left: 2px solid transparent
}

.component-teacher_file .file-box .semester .active {
  background: #e8f0ff;
  color: #4285f4;
  border-left-color: #4285f4
}

.component-teacher_file .file-box .list-box {
  padding-left: 24px;
  padding-top: 24px;
  flex: 1 1 70%;
  overflow: auto;
  height: 400px
}

.component-teacher_file .file-box .list-box .item {
  margin-bottom: 16px;
  width: 100%;
  height: 112px;
  padding: 16px;
  background: #fff;
  border: 1px solid #dadce0;
  border-radius: 4px
}

.component-teacher_file .file-box .list-box .item .left {
  background: no-repeat 50%/cover;
  margin-right: 16px;
  position: relative;
  width: 96px;
  height: 80px;
  background: #faa723;
  border-radius: 8px 8px 0 0;
  cursor: pointer
}

.component-teacher_file .file-box .list-box .item .left .tag-flag {
  position: absolute;
  top: 0;
  left: 8px
}

.component-teacher_file .file-box .list-box .item .left p {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 96px;
  height: 24px;
  background: rgba(0, 0, 0, .4);
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Semibold;
  font-weight: 600;
  text-align: center;
  color: #fff;
  line-height: 24px
}

.component-teacher_file .file-box .list-box .item .right {
  flex: 1
}

.component-teacher_file .file-box .list-box .item .right .header {
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #333;
  line-height: 20px
}

.component-teacher_file .file-box .list-box .item .right .header img {
  display: block;
  width: 24px
}

.component-teacher_file .file-box .list-box .item .right .name {
  margin-top: 4px;
  font-size: 18px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  color: #333;
  line-height: 24px;
  cursor: pointer
}

.component-teacher_file .file-box .list-box .item .right .bottom {
  margin-top: 6px
}

.component-teacher_file .file-box .list-box .item .right .bottom .head-box img {
  width: 20px;
  height: 20px;
  border-width: 2px
}

.component-teacher_file .el-dialog__footer {
  height: 84px;
  padding: 24px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, .1)
}

.component-teacher_file .component-no_data {
  padding-top: 0
}

.home-aside {
  margin-left: 20px;
  flex: 0 0 260px;
  align-self: flex-start;
  width: 260px;
  transition: all .3s
}

.home-aside .list-box {
  padding-top: 8px
}

.home-aside .list-box .small-btn {
  cursor: pointer;
  width: 38px;
  height: 22px;
  border: 1px solid #c1c4cd;
  border-radius: 2px;
  text-align: center;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  color: #6d7790;
  line-height: 20px
}

.home-aside .list-box .item {
  margin-top: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid #dadce0;
  cursor: pointer
}

.home-aside .list-box .item .h-info .h-icon {
  display: block;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  font-size: 24px;
  margin-right: 8px
}

.home-aside .list-box .item .h-info .h-type {
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  color: #575a5b;
  line-height: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 75px
}

.home-aside .list-box .item .h-info .h-time {
  margin-left: 8px;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  color: #74787c;
  line-height: 16px
}

.home-aside .list-box .item .h-info img {
  width: 24px;
  height: 24px;
  margin-right: 8px
}

.home-aside .list-box .item .h-info .icon-kejian {
  color: #00ca90
}

.home-aside .list-box .item .h-info .icon-ziliao {
  color: #71d516
}

.home-aside .list-box .item .h-info .icon-hudong {
  color: #ff941f
}

.home-aside .list-box .item .h-info .icon-zuoye {
  color: #31ccd2
}

.home-aside .list-box .item .h-info .icon-huati {
  color: #ffb607
}

.home-aside .list-box .item .h-info .icon-ceshi {
  color: #ffda09
}

.home-aside .list-box .item .h-info .icon-gonggao {
  color: #6642f4
}

.home-aside .list-box .item .tag {
  margin-top: 8px;
  width: 56px;
  height: 20px;
  background: rgba(255, 148, 31, .1);
  border-radius: 3px;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: center;
  color: #ff941f;
  line-height: 16px
}

.home-aside .list-box .item .tag-info {
  margin-top: 8px;
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  color: #4285f4;
  line-height: 20px
}

.home-aside .list-box .item .detail {
  margin-top: 8px;
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #3c4043;
  line-height: 24px;
  word-break: break-all;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden
}

.home-aside .list-box .item .detail span {
  color: #74787c
}

.home-aside .list-box .item .bottom {
  margin-top: 8px;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #ff941f;
  line-height: 16px
}

.home-aside .list-box .item:last-child {
  border-bottom: 0;
  padding-bottom: 0
}

.home-aside .list-box .bottom-from {
  font-size: 12px;
  color: #74787c
}

.home-aside .list-box .bottom-btn {
  margin-top: 16px;
  font-size: 12px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: center;
  color: #4285f4;
  line-height: 16px
}

.home-aside .ellipsis-two {
  text-overflow: -o-ellipsis-lastline;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical
}

.choose-course-module .el-dialog__body {
  padding: 0
}

.choose-course-module .el-dialog__body p {
  padding: 16px 32px 0
}

.choose-course-module .el-dialog__body .type-list {
  padding: 34px 32px
}

.choose-course-module .el-dialog__body .type-list .list-item {
  margin-right: 24px;
  width: 82px;
  height: 38px;
  background: #f8f8f8;
  border-radius: 18px
}

.choose-course-module .el-dialog__body .type-list .list-item.active {
  color: #fff;
  background: #2791ff
}

.choose-course-module .el-dialog__body .preview-box {
  padding: 17px 55px;
  width: 921px;
  height: 272px;
  opacity: 1;
  background: #f7f7f7
}

.choose-course-module .el-dialog__body .preview-box p {
  padding: 0
}

.choose-course-module .el-dialog__body .preview-box p span {
  margin-right: 183px
}

.choose-course-module .el-dialog__body .preview-box .content .events-list {
  margin-bottom: 16px
}

.choose-course-module .el-dialog__body .preview-box .content .events-list .list-item {
  margin-right: 16px;
  padding: 12px 6px;
  line-height: 16px;
  border-bottom: 4px solid transparent
}

.choose-course-module .el-dialog__body .preview-box .content .events-list .list-item.active {
  color: #2791ff;
  border-bottom-color: #2791ff
}

.choose-course-module .el-dialog__body .preview-box .content .skeleton {
  padding: 24px;
  border: 1px solid #dfdfdf
}

.choose-course-module .el-dialog__body .preview-box .content .skeleton .circle {
  margin-right: 24px;
  width: 46px;
  height: 46px;
  border-radius: 50%;
  background: #e6e6e6
}

.choose-course-module .el-dialog__body .preview-box .content .skeleton .h4 {
  margin-bottom: 16px;
  width: 252px;
  height: 16px;
  background: #e6e6e6
}

.choose-course-module .el-dialog__body .preview-box .content .skeleton .p {
  width: 108px;
  height: 8px;
  background: #e6e6e6
}

.choose-course-module .el-dialog__body .preview-box .content .skeleton .rect {
  margin-right: 20px;
  width: 32px;
  height: 32px;
  background: #e6e6e6
}

.custom-tabs-tip h4 {
  margin-bottom: 28px;
  width: 600px;
  line-height: 21px;
  font-size: 18px;
  font-weight: 700;
  text-align: center
}

.custom-tabs-tip img {
  max-width: 100%
}

.remind-dialog .el-dialog__header {
  display: none
}

.remind-dialog .el-dialog__body {
  padding-left: 30px;
  padding-right: 30px;
  padding-top: 30px
}

.remind-dialog .el-dialog__footer {
  display: none
}

.remind-dialog .title {
  margin-bottom: 10px
}

.remind-dialog .content {
  line-height: 25px
}

.remind-dialog .inscription-area {
  width: 100%;
  margin-top: 25px
}

.remind-dialog .inscription-area .inscription {
  text-align: right;
  margin-bottom: 5px
}

.remind-dialog .inscription-area .inscriptiondate {
  text-align: right
}

.remind-dialog .footer {
  text-align: center;
  margin-top: 10px
}

.remind-dialog .footer .btn {
  display: inline-block;
  cursor: pointer
}

.remind-dialog .footer .btnp {
  color: #fff;
  background-color: #4285f4;
  border-color: #4285f4;
  padding: 6px;
  border-radius: 2px;
  margin-right: 30px;
  padding-left: 8px;
  padding-right: 8px
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .el-dialog__body {
  padding: 20px 18px 15px;
  min-height: 300px
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .el-dialog__body .desc {
  margin-bottom: 12px
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .radio-list .el-radio {
  padding: 16px 0;
  display: flex;
  align-items: center;
  margin-right: 0
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .radio-list .el-radio .el-radio__label {
  padding-left: 15px;
  display: flex
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .radio-list .el-radio .el-radio__label .cover {
  margin-right: 18px;
  width: 142px;
  height: 79px
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .radio-list .el-radio .el-radio__label .detail {
  flex: 1
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .radio-list .el-radio .el-radio__label .detail h4 {
  font-size: 16px;
  color: #333
}

.homepage-course-copy-to-teach-research-room .copy-to-teach-research-room-dialog .radio-list .el-radio .el-radio__label .detail p {
  margin-top: 12px;
  font-size: 12px;
  font-weight: 400;
  line-height: 14px;
  color: #a5a5a5
}

[class*=driver-close-btn] {
  background: transparent;
  color: #969696;
  border: none
}

div#driver-popover-item .driver-popover-footer button {
  background-color: #1890ff;
  color: #fff;
  text-shadow: none;
  border: none;
  line-height: 1.8;
  border-radius: 4px
}

div#driver-popover-item .driver-popover-footer button:hover {
  background-color: #50abff
}

div#driver-popover-item .driver-popover-footer button:active {
  background-color: #1269ba
}

div#driver-popover-item .driver-popover-footer .driver-close-btn {
  background: transparent;
  color: #969696;
  border: none
}

div#driver-popover-item .driver-popover-footer .driver-close-btn:hover {
  background-color: transparent
}

.jgAuthentication .el-dialog__header {
  display: none
}

.jgAuthentication .jgAuthentication-content > p {
  margin-bottom: 15px;
  color: #606266;
  font-weight: 700
}

.jgAuthentication .jgAuthentication-content .dialog-input {
  display: flex;
  border-bottom: 1px solid #dadce0;
  padding-bottom: 10px
}

.jgAuthentication .jgAuthentication-content .dialog-input input {
  flex: 1;
  padding-left: 10px
}

.jgAuthentication .el-dialog__footer {
  border-top: none;
  text-align: right !important;
  padding: 0 30px 30px
}

.jgAuthentication .margin-b20 {
  margin-bottom: 20px
}

.AuthenticationCancel .el-dialog__header {
  display: none
}

.AuthenticationCancel .el-dialog__footer {
  border-top: none;
  padding: 0 30px 30px
}

.AuthenticationCancel .AuthenticationCancel-content h5 {
  margin-bottom: 15px;
  color: #606266;
  font-weight: 700
}

.AuthenticationCancel .AuthenticationCancel-content p {
  margin-bottom: 20px
}

.AuthenticationCancel .AuthenticationCancel-content .img {
  margin: 0 auto;
  display: block
}

.jgBinding .el-dialog__header {
  text-align: left;
  font-weight: 700
}

.jgBinding .el-dialog__footer {
  border-top: none;
  text-align: right !important;
  padding: 0 30px 30px
}

.jgBinding .jgBinding-content .desc {
  margin-bottom: 15px
}

.jgBinding .jgBinding-content .desc span {
  color: red
}

.jgBinding .jgBinding-content .desc-title {
  display: flex;
  align-items: center;
  margin-bottom: 15px
}

.jgBinding .jgBinding-content .desc-title span {
  flex: 1;
  text-align: center;
  font-size: 16px;
  color: #333;
  font-weight: 700
}

.jgBinding .jgBinding-content .bandingtableData {
  height: 350px;
  overflow-y: scroll
}

.js-confirm-danger-match {
  padding: 2px 4px;
  color: #c0341d;
  background-color: #fbe5e1;
  border-radius: 4px
}

.checkedClassDialogVisible .checkedClassDialogVisible-content .checkedClass {
  background: #f3f8ff;
  border-radius: 4px;
  padding: 10px 20px;
  margin-bottom: 20px
}

.checkedClassDialogVisible .checkedClassDialogVisible-content .checkedClass h5 {
  font-size: 14px;
  color: #4d4d4d;
  font-weight: 700;
  margin-bottom: 10px
}

.checkedClassDialogVisible .checkedClassDialogVisible-content .checkedClass p span {
  width: 40%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: inline-block
}

.checkedClassDialogVisible .checkedClassDialogVisible-content .checkedClass p span:first-child {
  font-size: 16px;
  color: #357ae8;
  font-weight: 500
}

.checkedClassDialogVisible .checkedClassDialogVisible-content ul {
  max-height: 350px;
  overflow-y: scroll
}

.checkedClassDialogVisible .checkedClassDialogVisible-content ul li {
  display: flex;
  align-items: center;
  height: 45px;
  cursor: pointer
}

.checkedClassDialogVisible .checkedClassDialogVisible-content ul li .radios {
  height: 15px;
  width: 15px;
  border-radius: 50%;
  margin-right: 8px;
  border: 1px solid #ccc
}

.checkedClassDialogVisible .checkedClassDialogVisible-content ul li .list {
  display: flex;
  align-items: center;
  flex: 1;
  width: 90%
}

.checkedClassDialogVisible .checkedClassDialogVisible-content ul li .list span {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 40%
}

.checkedClassDialogVisible .checkedClassDialogVisible-content ul .active-li .radios {
  background: #357ae8;
  border-color: transparent
}

.view-home-box .home-collapse {
  border: 0 !important;
  margin-bottom: 20px
}

.view-home-box .home-collapse .el-collapse-item__header, .view-home-box .home-collapse .el-collapse-item__wrap {
  background: #fdc
}

.view-home-box .home-collapse .el-collapse-item__header {
  padding: 18px;
  color: #ff6000;
  font-size: 14px;
  font-weight: 700
}

.view-home-box .home-collapse .el-collapse-item__header img {
  width: 24px;
  height: 24px;
  margin-right: 16px
}

.view-home-box .home-collapse .el-collapse-item__header i {
  font-weight: 700;
  font-size: 16px
}

.view-home-box .home-collapse .home-collapse-list {
  padding: 0 18px;
  display: flex;
  flex-wrap: wrap;
  height: 130px;
  overflow-y: auto
}

.view-home-box .home-collapse .home-collapse-list .collapse-item {
  border: 1px solid #ffd1ba;
  border-radius: 5px;
  background: #fff;
  cursor: pointer;
  margin-bottom: 18px;
  margin-right: 18px;
  padding: 15px;
  display: flex;
  align-items: center;
  width: 345px
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .imgs {
  width: 64px;
  height: 64px;
  margin-right: 16px;
  border-radius: 2px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50%
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc {
  flex: 1
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc .name {
  font-size: 16px;
  color: #3c4043;
  width: 190px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc p {
  font-size: 12px;
  color: #74787c;
  display: flex;
  align-items: center;
  justify-content: space-between
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc p .other {
  display: flex;
  align-items: center;
  color: #4285f4
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc p .other img {
  width: 20px;
  height: 20px;
  margin-right: 8px
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc p .zb {
  display: flex;
  align-items: center;
  color: #f50
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc p .zb .img-box {
  width: 20px;
  height: 20px;
  background: #f50;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 8px
}

.view-home-box .home-collapse .home-collapse-list .collapse-item .desc p .zb .img-box img {
  width: 13px;
  height: 13px
}

.view-home-box .home-collapse .home-collapse-list .margin-right {
  margin-right: 18px
}

.dialog-footer-more {
  display: flex;
  justify-content: space-between;
  align-items: center;
  align-content: center
}

.dialog-footer-more .footer-left {
  font-size: 12px;
  display: flex
}
</style>
