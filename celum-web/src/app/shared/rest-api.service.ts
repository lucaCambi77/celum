import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Course} from './course';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  // Define API
  apiURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }


  /*========================================
    CRUD User for consuming RESTful API :
  =========================================*/

  // HttpClient API get() method => Fetch users list
  getUsers(): Observable<User> {
    return this.http.get<User>(this.apiURL + '/user')
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API get() method => Fetch user
  getUser(id: number): Observable<User> {
    return this.http.get<User>(this.apiURL + '/user/' + id)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API post() method => Create user
  saveUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiURL + '/user', JSON.stringify(user), this.httpOptions)
      .pipe(
        retry(0),
        catchError(this.handleError)
      )
  }


  // HttpClient API delete() method => Delete user
  deleteUser(id: number) {
    return this.http.delete<User>(this.apiURL + '/user/' + id, this.httpOptions)
      .pipe(
        retry(0),
        catchError(this.handleError)
      )
  }

  /*========================================
   CRUD Course for consuming RESTful API :
 =========================================*/

  // HttpClient API get() method => Fetch course list
  getCourses(): Observable<Course> {
    return this.http.get<Course>(this.apiURL + '/course')
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API get() method => Fetch courses
  getCourse(id: number): Observable<Course> {
    return this.http.get<Course>(this.apiURL + '/course/' + id)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API post() method => Create course
  saveCourse(course: Course): Observable<Course> {
    return this.http.post<Course>(this.apiURL + '/course', JSON.stringify(course), this.httpOptions)
      .pipe(
        retry(0),
        catchError(this.handleError)
      )
  }


  // HttpClient API delete() method => Delete course
  deleteCourse(id: number) {
    return this.http.delete<Course>(this.apiURL + '/course/' + id, this.httpOptions)
      .pipe(
        retry(0),
        catchError(this.handleError)
      )
  }

  // Error handling
  handleError(error) {
    console.log(error);
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
