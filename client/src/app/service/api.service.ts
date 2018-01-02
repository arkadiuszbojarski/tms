import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Router, Params } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';

import { TaskResource } from '../model/task-resource';
import { Task } from '../model/task';
import { Token } from '../model/token';
import { User } from '../model/user';

const API_URL = environment.api.url;
const TASKS_SERVICE = API_URL + '/tasks-service';
const AUTH_SERVICE = API_URL + '/uaa';

@Injectable()
export class ApiService {

  private _tasksUrl: string;
  private _registerUrl: string;
  private _loginUrl: string;

  constructor(private _router: Router, private _http: HttpClient) {
    this._tasksUrl = `${TASKS_SERVICE}/tasks`;
    this._registerUrl = `${AUTH_SERVICE}/register`;
    this._loginUrl = `${AUTH_SERVICE}/oauth/token`;
  }

  private handleError(error: HttpErrorResponse) {
    switch (error.status) {
      case 404:
        this._router.navigate(['/not-found']);
        break;

      default:
        this._router.navigate(['/error']);
        break;
    }
    return Observable.throw(error);
  }

  public getCurrentUser(): string {
    return localStorage.getItem('user');
  }

  public storeCurrentUser(token: string): void {
    localStorage.setItem('user', token);
  }

  private authHeader(user: string): HttpHeaders {
    return new HttpHeaders({ 'Authorization': 'Bearer ' + user });
  }

  public login(user: User): Observable<Token> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('username', user.username);
    params.append('password', user.password);
    params.append('grant_type', 'password');
    params.append('client', 'secret');

    const headers: HttpHeaders = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Basic ' + btoa('client:secret')
    });

    return this._http.post<Token>(this._loginUrl, params.toString(), { headers: headers })
      .catch(error => this.handleError(error));
  }

  public register(user: User, confirm: string): Observable<User> {
    const headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('client:secret')
    });

    return this._http
      .post<any>(this._registerUrl, {
        username: user.username,
        password: user.password,
        confirm: confirm
      }, { headers: headers })
      .catch(error => this.handleError(error));
  }

  public logout(): void {
    localStorage.removeItem('user');
    this._router.navigate(['/login']);
  }

  public createTask(task: Task): Observable<Task> {
    const token: string = this.getCurrentUser();
    const headers: HttpHeaders = this.authHeader(token);
    return this._http
      .post<Task>(this._tasksUrl, task, { headers: headers })
      .catch(error => this.handleError(error));
  }

  public updateTask(task: Task): Observable<Task> {
    const current: string = this.getCurrentUser();
    const headers: HttpHeaders = this.authHeader(current);
    const result = this._http
      .put<Task>(task._links.self.href, task, { headers: headers })
      .catch(error => this.handleError(error));

    return result;
  }

  public readTask(id: number): Observable<Task> {
    const current: string = this.getCurrentUser();
    const headers: HttpHeaders = this.authHeader(current);
    return this._http
      .get<Task>(`${this._tasksUrl}/${id}`, { headers: headers })
      .catch(error => this.handleError(error));
  }

  public searchTasks(query: Params): Observable<TaskResource> {
    const current: string = this.getCurrentUser();
    const headers: HttpHeaders = this.authHeader(current);
    return this._http
      .get<TaskResource>(this._tasksUrl, { headers: headers, params: query })
      .catch(error => this.handleError(error));
  }

  public deleteTask(task: Task) {
    const current: string = this.getCurrentUser();
    const headers: HttpHeaders = this.authHeader(current);
    return this._http
      .delete(task._links.self.href, { headers: headers })
      .catch(error => this.handleError(error));
  }
}
