import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { TaskListHeaderComponent } from './component/task-list-header/task-list-header.component';
import { TaskListComponent } from './component/task-list/task-list.component';
import { TaskListItemComponent } from './component/task-list-item/task-list-item.component';
import { TaskListFooterComponent } from './component/task-list-footer/task-list-footer.component';
import { TasksComponent } from './component/tasks/tasks.component';
import { ApiService } from './service/api.service';
import { TaskDataService } from './service/task-data.service';
import { LoginComponent } from './component/login/login.component';
import { AuthGuard } from './model/auth-guard';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { ErrorComponent } from './component/error/error.component';
import { RegisterComponent } from './component/register/register.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SelectComponent } from './component/select/select.component';

@NgModule({
  declarations: [
    AppComponent,
    TaskListHeaderComponent,
    TaskListComponent,
    TaskListItemComponent,
    TaskListFooterComponent,
    TasksComponent,
    LoginComponent,
    PageNotFoundComponent,
    ErrorComponent,
    RegisterComponent,
    SelectComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule.forRoot()
  ],
  providers: [ApiService, TaskDataService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
