import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../service/api.service';
import { Observable } from 'rxjs/Observable';
import { User } from '../../model/user';
import { Token } from '../../model/token';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registering = false;
  user: User = new User();
  confirm = '';

  constructor(private _router: Router, private _service: ApiService) { }

  public register() {
    this.registering = true;
    this._service.register(this.user, this.confirm).subscribe((registered: User) => {
      this._service.login(this.user).subscribe((token: Token) => {
        this._service.storeCurrentUser(token.access_token);
        this._router.navigate(['/tasks']);
        this.user = new User();
        this.confirm = '';
      });
    });
  }
}
