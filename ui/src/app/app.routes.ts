// src/app/app.routes.ts
import { Routes } from '@angular/router'
import { LoginRegisterTabs } from './login-register-tabs/login-register-tabs.component'
import { MenuFormComponent } from './menu-form/menu-form.component'

export const routes: Routes = [
  { path: '', component: LoginRegisterTabs },
  { path: 'menu/new', component: MenuFormComponent },
  { path: 'menu/:id', component: MenuFormComponent },
]
