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
import { HlmCheckboxModule } from '../../../libs/ui/ui-checkbox-helm/src/index'
import { HlmCheckboxComponent } from '../../../libs/ui/ui-checkbox-helm/src/lib/hlm-checkbox.component'

@Component({
  selector: 'app-food-form',
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
    HlmCheckboxModule,
    HlmSelectImports,
    HlmCheckboxComponent,
  ],
  templateUrl: './food-form.component.html',
  styleUrls: ['./food-form.component.css'],
})
export class FoodFormComponent implements OnInit {
  @Input() foodId?: number // Recibe el ID del menú en caso de edición
  @Output() save = new EventEmitter<Food>()
  @Output() closeForm = new EventEmitter()
  foodForm: FormGroup
  isEditMode = false

  constructor(private fb: FormBuilder, private foodService: FoodService) {
    this.foodForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      isVegetarian: [false, Validators.required],
    })
  }

  ngOnInit(): void {
    if (this.foodId) {
      this.isEditMode = true

      this.foodService.getFood(this.foodId).subscribe((food) => {
        this.foodForm.patchValue(food)
      })
    }
  }

  onSubmit(): void {
    if (this.foodForm.valid) {
      const foodData = {
        ...this.foodForm.value,
      }

      if (this.isEditMode && this.foodId) {
        this.foodService.updateFood(this.foodId, foodData).subscribe((food) => {
          this.save.emit(food)
        })
      } else {
        this.foodService.addFood(foodData).subscribe((food) => {
          this.save.emit(food) //
        })
      }
    }
  }

  onCancel(): void {
    this.closeForm.emit()
  }
}
