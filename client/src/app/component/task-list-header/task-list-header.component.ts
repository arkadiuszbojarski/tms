import { Component, Output, EventEmitter } from '@angular/core';
import { Task } from '../../model/task';
import { NgbDateStruct, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { NgbCalendarGregorian } from '@ng-bootstrap/ng-bootstrap/datepicker/ngb-calendar';
import { Schedule } from '../../model/schedule';
import { NgbDateStructAdapter } from '@ng-bootstrap/ng-bootstrap/datepicker/ngb-date-adapter';

const equals = (one: NgbDateStruct, two: NgbDateStruct) =>
  one && two && two.year === one.year && two.month === one.month && two.day === one.day;

const before = (one: NgbDateStruct, two: NgbDateStruct) =>
  !one || !two ? false : one.year === two.year ? one.month === two.month ? one.day === two.day
    ? false : one.day < two.day : one.month < two.month : one.year < two.year;

const after = (one: NgbDateStruct, two: NgbDateStruct) =>
  !one || !two ? false : one.year === two.year ? one.month === two.month ? one.day === two.day
    ? false : one.day > two.day : one.month > two.month : one.year > two.year;

@Component({
  selector: 'app-task-list-header',
  templateUrl: './task-list-header.component.html',
  styleUrls: ['./task-list-header.component.css']
})
export class TaskListHeaderComponent {

  task: Task = new Task();
  schedule: Schedule = new Schedule();
  public isCollapsed = false;

  @Output()
  create: EventEmitter<Task> = new EventEmitter();

  constructor() { }

  private toDate(ngbDatestruct: NgbDateStruct) {
    return new Date(ngbDatestruct.year, ngbDatestruct.month - 1, ngbDatestruct.day, 0, 0, 0, );
  }

  public maxDate(): NgbDateStruct {
    const a: NgbDateStruct = this.schedule.deadline || this.schedule.until;
    const b: NgbDateStruct = this.schedule.until || this.schedule.deadline;
    if (a === b) { return a; }
    return before(a, b) ? a : b;
  }

  createTask() {
    if (this.schedule.start) { this.task.start = this.toDate(this.schedule.start); }
    if (this.schedule.deadline) { this.task.deadline = this.toDate(this.schedule.deadline); }
    if (this.schedule.until) { this.task.repeatUntil = this.toDate(this.schedule.until); }
    this.create.emit(this.task);
    this.task = new Task();
  }

  decreaseRepeatDays(): void {
    this.task.repeatDays = this.task.repeatDays || 0;
    if (this.task.repeatDays > 0) { this.task.repeatDays--; }
  }

  increaseRepeatDays(): void {
    this.task.repeatDays = this.task.repeatDays || 0;
    this.task.repeatDays++;
  }

  decreaseRepeatMonths(): void {
    this.task.repeatMonths = this.task.repeatMonths || 0;
    if (this.task.repeatMonths > 0) { this.task.repeatMonths--; }
  }

  increaseRepeatMonths(): void {
    this.task.repeatMonths = this.task.repeatMonths || 0;
    this.task.repeatMonths++;
  }

  decreaseRepeatYears(): void {
    this.task.repeatYears = this.task.repeatYears || 0;
    if (this.task.repeatYears > 0) { this.task.repeatYears--; }
  }

  increaseRepeatYears(): void {
    this.task.repeatYears = this.task.repeatYears || 0;
    this.task.repeatYears++;
  }

  clearForm(): void {
    this.schedule = new Schedule();
    this.task = new Task();
  }
}
