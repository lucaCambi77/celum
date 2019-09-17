import { Component, OnInit } from '@angular/core';
import { ObjectId } from '../shared/object-id';
import { User } from '../shared/user';
import { Course } from '../shared/course';
import { RestApiService } from "../shared/rest-api.service";
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { UserModalComponent } from '../modal/user-modal/user-modal.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: any = [];

  courses: CourseCheckBox[];
  currentUser: User = new User();
  courseData: any = [];

  constructor(public restApi: RestApiService, private modalService: NgbModal) {
  }

  ngOnInit() {

    this.courses = [];

    this.loadUsers();
    this.loadCourses();
  }

  userDetail(user: User) {
    this.currentUser = user;

    this.courses.forEach(courseCheckBox => {
      courseCheckBox.hasUser = false;
    });

    if (this.currentUser.courses) {

      this.currentUser.courses.forEach(userObjectId => {
        this.courses.forEach(courseCheckBox => {
          courseCheckBox.hasUser = false;

          if (courseCheckBox.course._id === userObjectId)
            courseCheckBox.hasUser = true;

        });
      });
    }

  }

  loadCourses() {
    this.restApi.getCourses().subscribe((data: {}) => {
      this.courseData = data;

      this.courseData.forEach(course => {
        var checkBox = new CourseCheckBox();
        checkBox.course = course;
        this.courses.push(checkBox);
      });
    })
  }

  loadUsers() {
    this.restApi.getUsers().subscribe((data: {}) => {
      this.users = data;
    })
  }

  deleteUser(id: number) {
    this.restApi.deleteUser(id).subscribe((data: {}) => {
      this.restApi.getUsers().subscribe((data: {}) => {
        this.users = data;
        this.currentUser = new User();
      })
    })
  }

  saveUser(user: User) {
    user.courses = [];

    this.courses.forEach(courseCheckBox => {
      if (courseCheckBox.hasUser == true)
        user.courses.push(courseCheckBox.course._id);
    });

    this.restApi.saveUser(user).subscribe((data: {}) => {
      this.restApi.getUsers().subscribe((data: {}) => {
        this.users = data;
      })
    });
  }

  openUserFormModal() {
    const modalRef = this.modalService.open(UserModalComponent);

    modalRef.result.then((user) => {
      this.restApi.saveUser(user).subscribe((data: {}) => {

        this.restApi.getUsers().subscribe((data: {}) => {
          this.users = data;
        })
      });
    }).catch((error) => {
      console.log(error);
    });
  }
}

class CourseCheckBox {

  course: Course = new Course();
  hasUser: boolean = false
}



