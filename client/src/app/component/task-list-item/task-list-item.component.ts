import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Task } from '../../model/task';

@Component({
  selector: 'app-task-list-item',
  templateUrl: './task-list-item.component.html',
  styleUrls: ['./task-list-item.component.css']
})
export class TaskListItemComponent {

  @Input()
  task: Task;

  @Output()
  toggle: EventEmitter<Task> = new EventEmitter();

  @Output()
  update: EventEmitter<Task> = new EventEmitter();

  @Output()
  delete: EventEmitter<Task> = new EventEmitter();

  constructor() { }

  deleteTask() {
    this.delete.emit(this.task);
  }

  toggleCompleted() {
    this.toggle.emit(this.task);
  }

  updateTask() {
    this.update.emit(this.task);
  }
}
