import { CommonModule } from '@angular/common'
import { Component, OnInit } from '@angular/core'
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router'
import { MenuService } from '../menu.service'
import { Food } from '../models/food.model'

@Component({
  selector: 'app-menu-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.css'],
})
export class MenuFormComponent implements OnInit {
  menuForm: FormGroup
  isEditMode = false
  menuId: number | null = null

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.menuForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      foods: this.fb.array([]),
    })
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id')
      if (id) {
        this.isEditMode = true
        this.menuId = +id
        const menu = this.menuService.getMenu(this.menuId)
        if (menu) {
          this.menuForm.patchValue(menu)
          menu.foods.forEach((food) => this.addFood(food))
        }
      }
    })
  }

  get foods() {
    return this.menuForm.get('foods') as FormArray<FormGroup>
  }

  addFood(food: any = {}): void {
    const foodForm = this.fb.group({
      name: [food.name || '', Validators.required],
      description: [food.description || '', Validators.required],
      price: [food.price || '', [Validators.required, Validators.min(0)]],
    })
    this.foods.push(foodForm)
  }

  removeFood(index: number): void {
    this.foods.removeAt(index)
  }

  onSubmit(): void {
    if (this.menuForm.valid) {
      if (this.isEditMode && this.menuId) {
        this.menuService.updateMenu({ ...this.menuForm.value, id: this.menuId })
      } else {
        this.menuService.addMenu(this.menuForm.value)
      }
      this.router.navigate(['/'])
    }
  }
}
