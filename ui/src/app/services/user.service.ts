import { Injectable } from '@angular/core'
import { ApiService } from './api.service' // Importamos el servicio base
import { Observable } from 'rxjs'

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

  constructor(private apiService: ApiService) {}

  /**
   * Método para registrar un usuario.
   * @param registerData Datos del usuario a registrar.
   * @returns Observable con la respuesta del backend.
   */
  register(registerData: RegisterData): Observable<any> {
    const endpoint = this.subpath + '/register'

    return this.apiService.post<any>(endpoint, registerData)
  }

  /**
   * Método para loguear un usuario.
   * @param loginData Datos del usuario a loguear.
   * @returns Observable con la respuesta del backend.
   */

  login(loginData: LoginData): Observable<any> {
    const endpoint = this.subpath + '/login'

    return this.apiService.post<any>(endpoint, loginData)
  }
}
