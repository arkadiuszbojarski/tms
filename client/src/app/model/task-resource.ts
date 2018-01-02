import { Task } from './task';

export class TaskResource {
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number;
  };
  _embedded: {
    tasks: Task[]
  };
}
