import { Injectable } from '@angular/core'
import { ApiService } from './api.service' // Importamos el servicio base
import { Observable, tap } from 'rxjs'
import { AuthService } from './auth-service.service'
import { HttpResponse } from '@angular/common/http'

// Definir la estructura de los datos de registro
export interface RegisterData {
  dni: string
  password: string
  email: string
  roleSelected: string
  name: string
}

export interface LoginData {
  dni: string
  password: string
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private subpath = 'users' // Endpoint del backend para el registro

  constructor(
    private apiService: ApiService,
    private authService: AuthService
  ) {}

  /**
   * Método para registrar un usuario.
   * @param registerData Datos del usuario a registrar.
   * @returns Observable con la respuesta del backend.
   */
  register(registerData: RegisterData): Observable<HttpResponse<any>> {
    const endpoint = this.subpath + '/register'

    return this.apiService.post<any>(endpoint, registerData)
  }

  /**
   * Método para loguear un usuario.
   * @param loginData Datos del usuario a loguear.
   * @returns Observable con la respuesta del backend.
   */

  login(loginData: LoginData): Observable<HttpResponse<any>> {
    const endpoint = this.subpath + '/login'

    return this.apiService.post<any>(endpoint, loginData).pipe(
      tap((response) => {
        const token = response.headers?.get('Authorization')

        if (token) {
          this.authService.setToken(token)

          this.authService.setData('user', response.body.data)
        }
      })
    )
  }
}
