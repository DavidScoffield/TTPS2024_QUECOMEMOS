import { Injectable } from '@angular/core'
import { BehaviorSubject, Observable } from 'rxjs'
import { Menu } from '../models/menu.model'
import { ApiService } from './api.service' // Asegúrate de tener el ApiService importado
import { map } from 'rxjs/operators'
import { ApiResponseDTO } from '../models/apiResponseDTO.model'

@Injectable({
  providedIn: 'root',
})
export class MenuService {
  private menusSubject = new BehaviorSubject<Menu[]>([]) // Utilizamos un BehaviorSubject para manejar los menús
  private subpath = 'menus' // Ruta del endpoint en el backend

  constructor(private apiService: ApiService) {}

  /**
   * Obtiene todos los menús desde el backend.
   */
  getMenus(): Observable<Menu[]> {
    return this.apiService.get<ApiResponseDTO<Menu[]>>(`${this.subpath}`).pipe(
      map(response => {
        if (!response.body||!response.body.data) {
          throw new Error('Menu not found'); // Lanza error si no hay un menú
        }
        return response.body.data;
      })
    );
  }

  /**
   * Obtiene un menú específico por su ID.
   */
  getMenu(id: number): Observable<Menu> {
    return this.apiService.get<ApiResponseDTO<Menu>>(`menus/${id}`).pipe(
      map(response => {
        if (!response.body||!response.body.data) {
          throw new Error('Menu not found'); // Lanza error si no hay un menú
        }
        return response.body.data;
      })
    );
  }

  /**
   * Agrega un nuevo menú.
   */
  addMenu(menu: Omit<Menu, 'id'>): Observable<Menu> {
    return this.apiService.post<ApiResponseDTO<Menu>>(`${this.subpath}/register`, menu).pipe(
      map(response => {
        if (!response.body||!response.body.data) {
          throw new Error('Menu not found'); // Lanza error si no hay un menú
        }
        return response.body.data;
      })
    );
  }

  /**
   * Actualiza un menú existente.
   */
  updateMenu(id: number, updatedMenu: Menu): Observable<Menu> {
    return this.apiService.put<ApiResponseDTO<Menu>>(`${this.subpath}/update/${id}`, updatedMenu).pipe(
      map(response => {
        if (!response.body||!response.body.data) {
          throw new Error('Menu not found'); // Lanza error si no hay un menú
        }
        return response.body.data;
      })
    );
  }

  /**
   * Elimina un menú por su ID.
   */
  deleteMenu(id: number): Observable<void|null> {
    return this.apiService.delete<void>(`${this.subpath}/${id}`).pipe(
      map(response => response.body) // Extrae solo el cuerpo de la respuesta (aunque sea vacío)
    )
  }
}

