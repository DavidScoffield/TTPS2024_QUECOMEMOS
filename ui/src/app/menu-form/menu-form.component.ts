import { CommonModule } from '@angular/common'
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router'
import { MenuService } from '../services/menu.service'
import { Food } from '../models/food.model'
import { HlmInputDirective } from '@spartan-ng/ui-input-helm'
import { HlmLabelDirective } from '@spartan-ng/ui-label-helm'
import {
  HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective,
} from '@spartan-ng/ui-card-helm'
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm'
//import { BrnSelectImports } from '@spartan-ng/brain/select';
import { HlmSelectImports } from '@spartan-ng/ui-select-helm'
import { FoodService } from '../services/food.service'
import { Menu } from '../models/menu.model'

@Component({
  selector: 'app-menu-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HlmCardContentDirective,
    HlmButtonDirective,
    HlmCardDescriptionDirective,
    HlmCardDirective,
    HlmCardFooterDirective,
    HlmCardHeaderDirective,
    HlmCardTitleDirective,
    HlmInputDirective,
    HlmLabelDirective,
    HlmSelectImports,
  ],
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.css'],
})
export class MenuFormComponent implements OnInit {
  @Input() menuId?: number // Recibe el ID del menú en caso de edición
  @Output() save = new EventEmitter<Menu>()
  @Output() closeForm = new EventEmitter()
  foodsLoaded: Food[] = []
  foodsByType: { [key: string]: Food[] } = {}
  menuForm: FormGroup
  isEditMode = false

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private foodService: FoodService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.menuForm = this.fb.group({
      name: ['', Validators.required],
      picture: [''],
      price: ['', [Validators.required, Validators.min(0)]],
      entrada: ['0', Validators.required],
      platoPrincipal: ['0', Validators.required],
      bebida: ['0', Validators.required],
      postre: ['0', Validators.required],
    })
  }

  ngOnInit(): void {
    if (this.menuId) {
      this.isEditMode = true

      // Suscribirse al Observable de getMenu()
      this.menuService.getMenu(this.menuId).subscribe((menu) => {
        // Una vez que recibimos el objeto 'menu', podemos acceder a sus propiedades
        this.menuForm.patchValue(menu)
        const foodsByType = this.groupFoodsByType(menu.foods)

        if (foodsByType['ENTRADA'])
          this.menuForm
            .get('entrada')
            ?.setValue(foodsByType['ENTRADA'][0]?.id || 0)
        if (foodsByType['PLATO PRINCIPAL'])
          this.menuForm
            .get('platoPrincipal')
            ?.setValue(foodsByType['PLATO PRINCIPAL'][0]?.id || 0)

        if (foodsByType['BEBIDA'])
          this.menuForm
            .get('bebida')
            ?.setValue(foodsByType['BEBIDA'][0]?.id || 0)
        if (foodsByType['POSTRE'])
          this.menuForm
            .get('postre')
            ?.setValue(foodsByType['POSTRE'][0]?.id || 0)

        // menu.foods.forEach((food) => this.addFood(food.id))
      })
    }

    // Cargar los alimentos independientemente
    this.foodService.getFoods().subscribe((foods) => {
      this.foodsLoaded = foods

      // Actualizar foodsByType agrupando los alimentos por tipo
      this.foodsByType = this.groupFoodsByType(foods)
    })
  }

  // Función auxiliar para agrupar alimentos por tipo
  private groupFoodsByType(foods: Food[]): { [key: string]: Food[] } {
    return foods.reduce((acc, food) => {
      if (!acc[food.type]) {
        acc[food.type] = []
      }
      acc[food.type].push(food)
      return acc
    }, {} as { [key: string]: Food[] })
  }

  get foods() {
    return this.menuForm.get('foods') as FormArray<FormGroup>
  }

  addFood(foodId: number | void): void {
    const foodForm = this.fb.group({
      food: [Number(foodId) || '', Validators.required], // Guardamos solo el ID o vacío si no se pasa un valor
    })
    this.foods.push(foodForm)
  }

  removeFood(index: number): void {
    this.foods.removeAt(index)
  }

  onSubmit(): void {
    if (this.menuForm.valid) {
      const foodIds = [
        this.menuForm.value.entrada,
        this.menuForm.value.platoPrincipal,
        this.menuForm.value.bebida,
        this.menuForm.value.postre,
      ].filter((food) => Number(food) > 0)

      if (foodIds.length < 2) {
        alert('Debes seleccionar al menos 2 comidas')
        return
      }

      const menuData = {
        ...this.menuForm.value,
        foods: [], // Vacío porque no necesitamos enviar objetos completos
        foodsIds: foodIds, // Enviamos los IDs de las comidas seleccionadas
      }

      if (this.isEditMode && this.menuId) {
        this.menuService.updateMenu(this.menuId, menuData).subscribe((menu) => {
          this.save.emit(menu) // Notifica que el formulario debe cerrarse
        })
      } else {
        this.menuService.addMenu(menuData).subscribe((menu) => {
          this.save.emit(menu) // Notifica que el formulario debe cerrarse
        })
      }
    }
  }

  onCancel(): void {
    this.closeForm.emit() // Emite el evento para cerrar el formulario sin guardar
  }
}
