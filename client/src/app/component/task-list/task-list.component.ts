import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Task } from '../../model/task';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent {

  @Input()
  tasks: Task[];

  @Output()
  toggle: EventEmitter<Task> = new EventEmitter();

  @Output()
  delete: EventEmitter<Task> = new EventEmitter();

  @Output()
  update: EventEmitter<Task> = new EventEmitter();

  constructor() {}

  onToggle(task: Task) {
    this.toggle.emit(task);
  }

  onDelete(task: Task) {
    this.delete.emit(task);
  }

  onUpdate(task: Task) {
    this.update.emit(task);
  }

}
