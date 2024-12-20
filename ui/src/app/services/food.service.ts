import { Injectable } from '@angular/core'
import { Food } from '../models/food.model'
import { ApiService } from './api.service'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'
import { ApiResponseDTO } from '../models/apiResponseDTO.model'

@Injectable({
  providedIn: 'root',
})
export class FoodService {
  private subpath = 'foods'

  constructor(private apiService: ApiService) {}

  getFoods(): Observable<Food[]> {
    return this.apiService.get<ApiResponseDTO<Food[]>>(`${this.subpath}`).pipe(
      map((response) => {
        // Verifica que el cuerpo de la respuesta exista
        if (!response.body || !response.body.data) {
          throw new Error('No foods found') // Error si el cuerpo no contiene datos
        }
        return response.body.data // Extrae la lista de comidas desde `data`
      })
    )
  }

  getFood(id: number): Observable<Food> {
    return this.apiService
      .get<ApiResponseDTO<Food>>(`${this.subpath}/${id}`)
      .pipe(
        map((response) => {
          if (!response.body || !response.body.data) {
            throw new Error('Food not found') // Lanza error si no hay un menú
          }
          return response.body.data
        })
      )
  }

  /**
   * Agrega un nuevo menú.
   */
  addFood(food: Omit<Food, 'id'>): Observable<Food> {
    return this.apiService
      .post<ApiResponseDTO<Food>>(`${this.subpath}/register`, food)
      .pipe(
        map((response) => {
          if (!response.body || !response.body.data) {
            throw new Error('Food not found') // Lanza error si no hay una comida
          }
          return response.body.data
        })
      )
  }

  /**
   * Actualiza un menú existente.
   */
  updateFood(id: number, updatedFood: Food): Observable<Food> {
    return this.apiService
      .put<ApiResponseDTO<Food>>(`${this.subpath}/update/${id}`, updatedFood)
      .pipe(
        map((response) => {
          if (!response.body || !response.body.data) {
            throw new Error('Food not found') // Lanza error si no hay un menú
          }
          return response.body.data
        })
      )
  }
}
