import { Component, OnInit } from '@angular/core';
import { ObjectId } from '../shared/object-id';
import { User } from '../shared/user';
import { Course } from '../shared/course';
import { RestApiService } from "../shared/rest-api.service";
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { CourseModalComponent } from '../modal/course-modal/course-modal.component';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {

  courses: any = [];
  users: usersCheckBox[];
  usersData: any = [];

  currentCourse: Course;

  constructor(public restApi: RestApiService, private modalService: NgbModal) { }

  ngOnInit() {
    this.loadCourses();
    this.loadUsers();
    this.users = [];
    this.currentCourse = new Course();
  }

  courseDetail(course: Course) {
    this.currentCourse = course;

    this.users.forEach(usersCheckBox => {
      usersCheckBox.hasCourse = false;
    });

    if (this.currentCourse.users) {

      this.users.forEach(usersCheckBox => {
        this.currentCourse.users.forEach(userObjectId => {
          usersCheckBox.hasCourse = false;

          if (usersCheckBox.user.id === userObjectId)
            usersCheckBox.hasCourse = true;
        });
      });

    }

  }

  loadUsers() {
    this.restApi.getUsers().subscribe((data: {}) => {
      this.usersData = data;
      this.usersData.forEach(user => {
        var checkBox = new usersCheckBox();
        checkBox.user = user;
        this.users.push(checkBox);
      });
    })
  }

  loadCourses() {
    this.restApi.getCourses().subscribe((data: {}) => {
      this.courses = data;
    })
  }

  deleteCourse(id: number) {
    this.restApi.deleteCourse(id).subscribe((data: {}) => {
      this.restApi.getCourses().subscribe((data: {}) => {
        this.courses = data;
        this.currentCourse = new Course();
      })
    })
  }

  saveCourse(course: Course) {
    course.users = [];

    this.users.forEach(usersCheckBox => {
      if (usersCheckBox.hasCourse == true)
        course.users.push(usersCheckBox.user.id);
    });

    this.restApi.saveCourse(course).subscribe((data: {}) => {
      this.restApi.getCourses().subscribe((data: {}) => {
        this.courses = data;
      });
    });
  }

  openCourseFormModal() {
    const modalRef = this.modalService.open(CourseModalComponent);

    modalRef.result.then((course) => {
      this.saveCourse(course);
    }).catch((error) => {
      console.log(error);
    });
  }
}

class usersCheckBox {

  user: User = new User();
  hasCourse: boolean = false
}


