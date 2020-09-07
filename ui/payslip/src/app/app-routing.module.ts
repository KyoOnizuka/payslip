import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { BrowserModule  } from '@angular/platform-browser';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./Admin/admin.module').then(m => m.AdminModule),
    canLoad: [AuthGuard]
  },
  {
    path: 'employee',
    loadChildren: () => import('./Employee/employee.Module').then(m => m.EmployeeModule),
    canLoad: [AuthGuard]
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
