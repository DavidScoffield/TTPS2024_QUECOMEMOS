// src/app/app.routes.ts
import { Routes } from '@angular/router'
import { MenuListComponent } from './menu-list/menu-list.component'
import { MenuFormComponent } from './menu-form/menu-form.component'

export const routes: Routes = [
  { path: '', component: MenuListComponent },
  { path: 'menu/new', component: MenuFormComponent },
  { path: 'menu/:id', component: MenuFormComponent },
]
