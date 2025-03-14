import {Routes} from '@angular/router';
import {UsersComponent} from './users/users.component';
import {CoursesComponent} from './courses/courses.component';

export const routes: Routes = [
    {path: '', component: UsersComponent},
    {path: 'users', component: UsersComponent},
    {path: 'courses', component: CoursesComponent},
];
