import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CookieInterceptor } from './auth/cookie.interceptor';
import { MaterialLibraryModule } from './shared/material/material-library.module';
import { AdminModule } from './Admin/admin.module';
import { AuthGuard } from './auth/auth.guard';
import { AuthModule } from './auth/auth.module';
import { EmployeeModule } from './Employee/employee.Module';
import { AddTimeWorkComponent } from './Employee/list-work/add-time-work/add-time-work.component';
import { SideBarComponent } from './shared/side-bar/side-bar.component';
import { NavBarComponent } from './shared/nav-bar/nav-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    AddTimeWorkComponent,
    SideBarComponent,
    NavBarComponent,
  ],
  imports: [
    AdminModule,
    BrowserAnimationsModule,
    MaterialLibraryModule,
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AuthModule,
    EmployeeModule,
  ],
  providers: [
    AuthGuard,
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CookieInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
