import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { UsersComponent } from './users/users.component';
import { CoursesComponent } from './courses/courses.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserModalComponent } from './modal/user-modal/user-modal.component';
import { CourseModalComponent } from './modal/course-modal/course-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    UsersComponent,
    CoursesComponent,
    UserModalComponent,
    CourseModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    UserModalComponent,
    CourseModalComponent
  ]
})
export class AppModule { }
