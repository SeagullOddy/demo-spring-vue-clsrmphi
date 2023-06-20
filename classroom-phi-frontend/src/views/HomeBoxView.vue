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

<style src="@/assets/css/home-box.css"/>
