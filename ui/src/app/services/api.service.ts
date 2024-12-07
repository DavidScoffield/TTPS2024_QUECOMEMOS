import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable, throwError } from 'rxjs'
import { catchError } from 'rxjs/operators'
import { CustomError } from '../error/CustomError'

// URL base de la API
const API_URL = 'http://localhost:8080/api/'

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  /**
   * Método para realizar solicitudes POST.
   * @param endpoint El endpoint al que enviar la solicitud.
   * @param body Los datos que se enviarán.
   * @returns Observable con la respuesta.
   */
  post<T>(endpoint: string, body: any): Observable<T> {
    return this.http
      .post<T>(`${API_URL}${endpoint}`, body, {
        headers: this.getHeaders(),
      })
      .pipe(catchError(this.handleError))
  }

  /**
   * Método para realizar solicitudes GET.
   * @param endpoint El endpoint al que enviar la solicitud.
   * @returns Observable con la respuesta.
   */
  get<T>(endpoint: string): Observable<T> {
    return this.http
      .get<T>(`${API_URL}${endpoint}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError))
  }

  /**
   * Método para manejar los errores HTTP.
   * @param error El error recibido.
   * @returns Observable con el error procesado.
   */
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ocurrió un error desconocido'

    console.log({ error })

    const customError = new CustomError(
      error.error.message || error.message || errorMessage,
      error.error.status,
      error.status
    )

    // TODO: Aca podríamos agregar log o un servicio de notificaciones.
    return throwError(() => customError)
  }

  /**
   * Obtener los headers de la solicitud (si se necesitan más headers, se pueden agregar aca).
   * @returns Los headers para la solicitud HTTP.
   */
  private getHeaders() {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      // 'Authorization': 'Bearer ' + token // Para agregar el token
    })
  }
}
