import { Injectable } from '@angular/core'
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router'
import { Observable } from 'rxjs'
import { AuthService } from '../services/auth-service.service'

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const isLoggedIn = this.authService.isAuthenticated()
    const isLoginRoute = state.url === '/' // Ajusta si tu ruta de login cambia

    if (isLoggedIn && isLoginRoute) {
      this.router.navigate(['/menus'])
      return false
    } else if (!isLoggedIn && !isLoginRoute) {
      this.router.navigate(['/'])
      return false
    }

    return true
  }
}
