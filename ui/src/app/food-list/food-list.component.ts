import { Component } from '@angular/core'
import {
  HlmCaptionComponent,
  HlmTableComponent,
  HlmTdComponent,
  HlmThComponent,
  HlmTrowComponent,
} from '@spartan-ng/ui-table-helm'
import { FoodService } from '../services/food.service'
import { Food } from '../models/food.model'
import { FoodFormComponent } from '../food-form/food-form.component'
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm'

@Component({
  selector: 'app-food-list',
  standalone: true,
  templateUrl: './food-list.component.html',
  styleUrl: './food-list.component.css',
  imports: [
    HlmTableComponent,
    HlmTrowComponent,
    HlmThComponent,
    HlmTdComponent,
    HlmCaptionComponent,
    HlmButtonDirective,
    FoodFormComponent,
  ],
  host: {
    class: 'w-full overflow-x-auto',
  },
})
export class FoodListComponent {
  protected foods: Food[] = []
  showForm = false // Flag para mostrar/ocultar el formulario
  selectedFoodId?: number // Para pasar el ID al formulario (en caso de edición)

  constructor(private foodService: FoodService) {}

  ngOnInit(): void {
    this.foodService.getFoods().subscribe((foods) => {
      this.foods = foods
    })
  }

  openForm(foodId?: number): void {
    console.log(foodId)

    this.selectedFoodId = foodId
    this.showForm = true // Muestra el formulario
  }

  saveFood(food: Food): void {
    if (this.selectedFoodId) {
      // Actualizar usuario existente
      const index = this.foods.findIndex((m) => m.id === food.id)
      this.foods[index] = food
    } else {
      // Agregar nuevo usuario
      this.foods.push(food)
    }
    this.closeForm()
  }

  closeForm(): void {
    this.showForm = false // Oculta el formulario
    this.selectedFoodId = undefined
  }
}
