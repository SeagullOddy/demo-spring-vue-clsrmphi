<script setup>
import {computed, reactive, ref} from "vue";
import {ArrowUp} from "@element-plus/icons-vue";
import CourseCard from "@/components/CourseCard.vue";
import {useStore} from "@/stores";

const showTeacherArchiveCourseDialog = ref(false)
const showStudentArchiveCourseDialog = ref(false)
const showDeleteCourseDialog = ref(false)
const showSortCourseDialog = ref(false)

// 用户是否为老师
const isTeacher = useStore().getAccount().roleType === 'TEACHER'

// 置顶课程板块
const hasPinnedCourse = ref(useStore().getPinnedCourses().length > 0)
const pinnedCourseClass = computed(() => {
  return 'header-common-border' + (hasPinnedCourse.value ? ' hasborder' : ' ')
})

// 归档管理
const showArchivingCourseDialog = ref(false)
const archivingCourse = () => {
  // TODO 调用后端接口
}

// 创建课程
const showCreateCourseDialog = ref(false)
const showCreateCourseExtraForm = ref(false)
const createCourseFormRef = ref()
const createCourseExtraFormRef = ref()
const createCourseFormData = ref({
  name: '',
  teachingClass: '',
  academicYear: '',
  academicTerm: '',
})
const yearNow = new Date().getFullYear()
// TODO 根据年份自动计算学年选项
const academicYearOptions = [
  {value: '2020-2021', label: '2020-2021'},
  {value: '2021-2022', label: '2021-2022'},
  {value: '2022-2023', label: '2022-2023'}
]
const academicTermOptions = [
  {value: 'FULL_YEAR', label: '全年'},
  {value: 'FIRST_TERM', label: '第一学期'},
  {value: 'SECOND_TERM', label: '第二学期'},
]
const createCourseExtraFormData = ref({
  teachingMode: '',
  academicHour: '',
  description: '',
  teachingPlace: '',
  institutionRelated: false,
})
const createCourseFormRules = {
  name: [
    {required: true, message: '必填项', trigger: 'blur'},
  ],
  teachingClass: [
    {required: true, message: '必填项', trigger: 'blur'},
  ],
  academicYearAndTerm: [
    {required: true, message: '必填项', trigger: 'blur'},
  ],
}
const createCourseExtraFormRules = {}
const cancelCreateCourse = () => {
  showCreateCourseExtraForm.value = false
  showCreateCourseDialog.value = false
  createCourseFormRef.value.resetFields()
  createCourseExtraFormRef.value.resetFields()
  createCourseFormData.value = {
    name: '',
    teachingClass: '',
    academicYear: '',
    academicTerm: '',
  }
  createCourseExtraFormData.value = {
    teachingMode: '',
    academicHour: '',
    description: '',
    teachingPlace: '',
    institutionRelated: false,
  }
}
const createCourse = async () => {
  if (!createCourseFormRef.value || !createCourseExtraFormRef.value) {
    return
  }
  await createCourseFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    // TODO: 调用后端接口
    showCreateCourseDialog.value = false
  })
}

// 加入课程
const showAddCourseDialog = ref(false)
const addCourseFormRef = ref()
const addCourseFormData = reactive({
  code: ''
})
const addCourseFormRules = {
  code: [
    {required: true, message: '必填项', trigger: 'blur'},
    {min: 6, max: 6, message: '长度为6位', trigger: 'blur'}
  ]
}
const cancelAddCourse = () => {
  showAddCourseDialog.value = false
  addCourseFormRef.value.resetFields()
}
const addCourse = async () => {
  if (!addCourseFormRef.value) {
    return
  }
  await addCourseFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    // TODO: 调用后端接口
    showAddCourseDialog.value = false
  })
}

</script>

<template>
  <div class="view-home-box">
    <div class="view-home">
      <div class="home-content">
        <!--    创建/加入课程和置顶课程列表    -->
        <div :class="pinnedCourseClass">
          <div class="top-handler">
            <h2 class="left">
              <span v-if="hasPinnedCourse">置顶课程</span>
            </h2>
            <div class="right">
              <!--   根据身份决定创建/加入课程按钮功能   -->
              <!--    使用 v-if/v-else     -->
              <el-dropdown v-if="isTeacher" id="home-element2">
                <span class="el-dropdown-link el-dropdown-selfdefine">
                  <el-button size="large" type="primary"
                             class="el-icon-plus">创建/加入课程</el-button>
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="showCreateCourseDialog = true">创建课程
                    </el-dropdown-item>
                    <el-dropdown-item @click="showAddCourseDialog = true">加入课程
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <el-button v-else @click="showAddCourseDialog = true" size="large" type="primary"
                         id="stu-home-element1" class="el-icon-plus">
                加入课程
              </el-button>
            </div>
          </div>
          <!--     置顶课程列表    -->
          <div class="class-box" style="max-height: 320px;">
            <!--   TODO component course card     -->
            <template v-for="course in useStore().getPinnedCourses()">
              <CourseCard :time="course.academicYear + ' ' + course.academicTerm"
                          classname="course.teachingClass" code="XhFWkk3" title="course.name"
                          mainteacher="扎根金融股">
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item>归档</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </CourseCard>
            </template>
          </div>
        </div>
        <!--    TODO 小标头    -->
        <div class="other-header flex-between">
          <el-tabs tab-position="top" class="right">
            <el-tab-pane v-if="isTeacher" label="我教的"/>
            <el-tab-pane label="我学的"/>
            <el-tab-pane label="我协助的"/>
          </el-tabs>
          <div class="left flex-between">
            <el-button @click="showArchivingCourseDialog = true" size="large">归档管理</el-button>
          </div>
        </div>
        <!--   TODO 课程列表    -->
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
            <CourseCard time="2021年" classname="121230204" code="XhFWkk3" title="软件工程II"
                        mainteacher="扎根金融股">
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>归档</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </CourseCard>
          </div>
        </div>
      </div>
      <!--   归档管理弹窗   -->
      <el-dialog v-model="showArchivingCourseDialog" class="component-teacher_file custom-dialog"
                 title="归档课程" draggable>
        <!--  TODO 展示归档的课程  -->
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="archivingCourse()" size="large" type="primary">确定</el-button>
          </span>
        </template>
      </el-dialog>
      <!--  创建课程  -->
      <el-dialog v-model="showCreateCourseDialog" class="custom-dialog add-classRoom-dialog"
                 title="创建课程" width="70%" draggable>
        <el-form ref="createCourseFormRef" :model="createCourseFormData"
                 :rules="createCourseFormRules" size="large" status-icon>
          <el-form-item class="margin-bottom-20 cus-formitem" label="课程名称" prop="name">
            <el-input v-model="createCourseFormData.name" maxlength="50" show-word-limit/>
          </el-form-item>
          <el-form-item class="margin-bottom-20 cus-formitem" label="教学班级" prop="teachingClass">
            <el-input v-model="createCourseFormData.teachingClass" maxlength="30" show-word-limit/>
          </el-form-item>
          <el-form-item prop="academicYearAndTerm">
            <el-row class="flex-align" :gutter="100" style="margin-top: 24px">
              <el-col :span="8">
                <i style="color: rgb(235, 80, 80); margin-right: 4px;">*</i>选择学年 - 学期
              </el-col>
              <el-col :span="8">
                <el-select v-model="createCourseFormData.academicYear" placeholder="请选择学年"
                           :suffix-icon="ArrowUp">
                  <el-option v-for="option in academicYearOptions" :key="option.value"
                             :label="option.label" :value="option.value"/>
                </el-select>
              </el-col>
              <el-col :span="8">
                <el-select v-model="createCourseFormData.academicTerm" placeholder="请选择学期"
                           :suffix-icon="ArrowUp">
                  <el-option v-for="option in academicTermOptions" :key="option.value"
                             :label="option.label" :value="option.value"/>
                </el-select>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
        <span class="senior-set" @click="showCreateCourseExtraForm = !showCreateCourseExtraForm">
          更多信息
          <i v-if="showCreateCourseExtraForm" class="el-icon-arrow-up"></i>
          <i v-else class="el-icon-arrow-down"></i>
        </span>
        <el-form ref="createCourseExtraFormRef" :model="createCourseExtraFormData"
                 :rules="createCourseExtraFormRules" size="large">
          <div v-if="showCreateCourseExtraForm" class="none-view">
            <div class="line-desc">
              <div class="TeachingRadio">
                <span>授课模式</span>
                <el-radio-group v-model="createCourseExtraFormData.teachingMode">
                  <el-radio label="线上" model-value="ONLINE">线上</el-radio>
                  <el-radio label="线下" model-value="OFF_LINE">线下</el-radio>
                  <el-radio label="混合" model-value="MIXED">混合</el-radio>
                </el-radio-group>
              </div>
              <div class="TeachingTimer">
                <span>学时数</span>
                <div class="TeachingTimer-select">
                  <div class="TeachingTimer-input">
                    <input v-model="createCourseExtraFormData.academicHour" type="number"
                           placeholder="请输入学时"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="courseIntro">
              <h2 class="mb12">课程介绍</h2>
              <el-input v-model="createCourseExtraFormData.description" type="textarea"/>
            </div>
            <div class="classAddress">
              <label class="flex-align">
                <span class="mgr-16">授课地点</span>
                <el-input v-model="createCourseExtraFormData.teachingPlace" maxlength="50"
                          label="授课地点" show-word-limit/>
              </label>
            </div>
            <el-row style="margin-top: 16px; line-height: 40px">
              <span class="mgr-16">机构相关</span>
              <el-switch v-model="createCourseExtraFormData.institutionRelated"
                         active-text="对机构后台可见"/>
              <p class="font12" style="color: rgb(153, 153, 153);">
                开启后，机构后台管理员可以看见该教师在线上开设的课程及教学情况</p>
            </el-row>
          </div>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <div class="footer_right">
                <el-button size="large" @click="cancelCreateCourse()">
                  取消
                </el-button>
                <el-button type="primary" size="large" @click="createCourse()">
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
        <el-form ref="addCourseFormRef" :model="addCourseFormData" :rules="addCourseFormRules"
                 size="large" status-icon>
          <el-form-item class="margin-bottom cus-formitem" label="加课码" prop="code">
            <el-input name="code" v-model="addCourseFormData.code"
                      placeholder="请输入课程加课码"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <div class="footer_area">
              <span class="dialog-footer">
                <el-button @click="cancelAddCourse()" size="large">取消</el-button>
                <el-button @click="addCourse()" size="large" type="primary">确认</el-button>
              </span>
            </div>
          </div>
        </template>
      </el-dialog>
      <!--   TODO Behind   -->
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
