import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { CoursesComponent } from './courses/courses.component';

const routes: Routes = [
  { path:'', component: UsersComponent},
  { path:'users', component: UsersComponent},
  { path:'courses', component: CoursesComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
