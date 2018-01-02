import { Injectable } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Observable } from 'rxjs/Observable';

import { TaskResource } from '../model/task-resource';
import { Task } from '../model/task';
import { Params } from '@angular/router/src/shared';
import { ReplaySubject } from 'rxjs/ReplaySubject';

@Injectable()
export class TaskDataService {

  constructor(private _service: ApiService) { }

  createTask(task: Task): Observable<Task> {
    return this._service.createTask(task);
  }

  updateTask(task: Task): Observable<Task> {
    return this._service.updateTask(task);
  }

  toggleCompleted(task: Task): Observable<Task> {
    task.completed = !task.completed;
    return this._service.updateTask(task);
  }

  readTask(id: number): Observable<Task> {
    return this._service.readTask(id);
  }

  searchTasks(query: Params): Observable<TaskResource> {
    return this._service.searchTasks(query);
  }

  deleteTask(task: Task): Observable<Task> {
    return this._service.deleteTask(task);
  }
}
