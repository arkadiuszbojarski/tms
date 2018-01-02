export class Task {
  title: string;
  start: Date;
  deadline: Date;
  repeatUntil: Date;
  repeatDays: number;
  repeatMonths: number;
  repeatYears: number;
  completed: boolean;
  _links: {
    self: {
      href: string
    }
  };

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
