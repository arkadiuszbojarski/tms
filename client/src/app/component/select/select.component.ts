import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRouteSnapshot } from '@angular/router/src/router_state';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.css']
})
export class SelectComponent {

  public title: string;
  public selectFrom: NgbDateStruct;
  public selectTo: NgbDateStruct;

  constructor(private _route: ActivatedRoute, private _router: Router) {
    const query = this._route.snapshot.queryParamMap;

    if (query.keys.includes('from') && query.keys.includes('to')) {
      const fromDate: Date = new Date(query.get('from'));
      const toDate: Date = new Date(query.get('to'));

      this.selectFrom = { year: fromDate.getFullYear(), month: fromDate.getMonth() + 1, day: fromDate.getDate() };
      this.selectTo = { year: toDate.getFullYear(), month: toDate.getMonth() + 1, day: toDate.getDate() };
    }

    if (query.keys.includes('title')) {
      this.title = query.get('title');
    }
  }

  public refresh(): void {
    this._router.navigate([], {
      relativeTo: this._route,
      queryParams: {
        from: this.selectFrom ? `${this.selectFrom.year}-${this.selectFrom.month}-${this.selectFrom.day}` : null,
        to: this.selectTo ? `${this.selectTo.year}-${this.selectTo.month}-${this.selectTo.day + 1}` : null,
        title: this.title
      },
      queryParamsHandling: 'merge'
    });
  }

  public forToday(): void {
    const now: Date = new Date();
    this.selectFrom = { year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate() };
    this.selectTo = { year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate() };
    this.refresh();
  }

  public forRange(): void {
    this.refresh();
  }

  public clear(): void {
    this.selectFrom = null;
    this.selectTo = null;
    this.refresh();
  }

}
