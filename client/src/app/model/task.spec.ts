import { Task } from './task';

const values = {
  completed: {
    title: 'completed',
    completed: true
  }
};

describe('Task', () => {
  it('should create an instance', () => {
    expect(new Task()).toBeTruthy();
  });

  it('should accept values in the constructor', () => {
    const task: Task = new Task(values.completed);
    expect(task.title).toEqual(values.completed.title);
    expect(task.completed).toEqual(values.completed.completed);
  });
});
