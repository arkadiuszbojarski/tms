import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Task } from '../../model/task';
import { TaskDataService } from '../../service/task-data.service';
import { TaskResource } from '../../model/task-resource';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  public tasks: Observable<TaskResource>;

  constructor(private _route: ActivatedRoute, private _service: TaskDataService) { }

  private refreshTasks() {
    this.tasks = this._service.searchTasks(this._route.snapshot.queryParams);
  }

  ngOnInit() {
    this.refreshTasks();
    this._route.queryParams.subscribe(query => this.refreshTasks());
  }

  onToggle(task: Task) {
    this._service.toggleCompleted(task).subscribe(toggled => this.refreshTasks());
  }

  onCreate(task: Task) {
    this._service.createTask(task).subscribe(created => this.refreshTasks());
  }

  onUpdate(task: Task) {
    this._service.updateTask(task).subscribe(updated => this.refreshTasks());
  }

  onDelete(task: Task) {
    this._service.deleteTask(task).subscribe((_) => this.refreshTasks());
  }
}
