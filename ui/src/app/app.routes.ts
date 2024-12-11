import { Routes } from '@angular/router'
import { LoginRegisterTabs } from './login-register-tabs/login-register-tabs.component'
import { MenuFormComponent } from './menu-form/menu-form.component'
import { MenuListComponent } from './menu-list/menu-list.component'
import { AuthGuard } from './auth/auth.guard' // Importa el guardia

export const routes: Routes = [
  { path: '', component: LoginRegisterTabs, canActivate: [AuthGuard] }, // Ruta no protegida (login)
  { path: 'menus', component: MenuListComponent, canActivate: [AuthGuard] }, // Ruta protegida
  { path: 'menu/new', component: MenuFormComponent, canActivate: [AuthGuard] }, // Ruta protegida
  { path: 'menu/:id', component: MenuFormComponent, canActivate: [AuthGuard] }, // Ruta protegida
  { path: '**', redirectTo: 'menus' },
]
