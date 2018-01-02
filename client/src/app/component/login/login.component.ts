import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../service/api.service';
import { User } from '../../model/user';
import { Token } from '../../model/token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public logging = false;
  public user: User = new User();

  constructor(private _router: Router, private _service: ApiService) { }

  public ngOnInit() {
    this._service.logout();
  }

  public login(): void {
    this.logging = true;
    this._service.login(this.user).subscribe((token: Token) => {
      this._service.storeCurrentUser(token.access_token);
      this._router.navigate(['/tasks']);
      this.user = new User();
    });
  }
}
