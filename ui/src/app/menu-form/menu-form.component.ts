import { CommonModule } from '@angular/common'
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router'
import { MenuService } from '../services/menu.service'
import { Food } from '../models/food.model'
import { HlmInputDirective } from '@spartan-ng/ui-input-helm';
import { HlmLabelDirective } from '@spartan-ng/ui-label-helm';
import { HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective } from '@spartan-ng/ui-card-helm';
  import { HlmButtonDirective } from '@spartan-ng/ui-button-helm';
  //import { BrnSelectImports } from '@spartan-ng/brain/select';
  import { HlmSelectImports } from '@spartan-ng/ui-select-helm';
import { FoodService } from '../services/food.service'
import { Menu } from '../models/menu.model'

@Component({
  selector: 'app-menu-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,  HlmCardContentDirective, HlmButtonDirective,
    HlmCardDescriptionDirective,
    HlmCardDirective,
    HlmCardFooterDirective,
    HlmCardHeaderDirective,
    HlmCardTitleDirective, HlmInputDirective, HlmLabelDirective, HlmSelectImports],
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.css'],
})
export class MenuFormComponent implements OnInit {
  @Input() menuId?: number; // Recibe el ID del menú en caso de edición
  @Output() save = new EventEmitter<Menu>();
  @Output() closeForm = new EventEmitter(); 
  foodsLoaded:Food[] = []
  menuForm: FormGroup;
  isEditMode = false;

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
      foods: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    if (this.menuId) {
      this.isEditMode = true;
      
      // Suscribirse al Observable de getMenu()
      this.menuService.getMenu(this.menuId).subscribe(menu => {
        // Una vez que recibimos el objeto 'menu', podemos acceder a sus propiedades
        this.menuForm.patchValue(menu);
        menu.foods.forEach((food) => this.addFood(food.id));
      });
    }
  
    // Cargar los alimentos independientemente
    this.foodService.getFoods().subscribe((foods)=>{
      this.foodsLoaded = foods;
    });
  }

  get foods() {
    return this.menuForm.get('foods') as FormArray<FormGroup>;
  }

  addFood(foodId: number | void): void {
    const foodForm = this.fb.group({
      food: [Number(foodId) || '', Validators.required], // Guardamos solo el ID o vacío si no se pasa un valor
    });
    this.foods.push(foodForm);
  }

  removeFood(index: number): void {
    this.foods.removeAt(index);
  }

  onSubmit(): void {
    if (this.menuForm.valid) {
      const foodIds = this.menuForm.value.foods.map((food: { food: any }) => Number(food.food)); // Extraemos los IDs seleccionados
  
      const menuData = {
        ...this.menuForm.value,
        foods: [], // Vacío porque no necesitamos enviar objetos completos
        foodsIds: foodIds, // Enviamos los IDs de las comidas seleccionadas
      };
  
      if (this.isEditMode && this.menuId) {
        this.menuService.updateMenu(this.menuId, menuData).subscribe((menu) => {
          this.save.emit(menu); // Notifica que el formulario debe cerrarse
        });
      } else {
        this.menuService.addMenu(menuData).subscribe((menu) => {
          this.save.emit(menu); // Notifica que el formulario debe cerrarse
        });
      }
    }
  }

  onCancel(): void {
    this.closeForm.emit(); // Emite el evento para cerrar el formulario sin guardar
  }
}
