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
  @Output() closeForm = new EventEmitter<void>(); // Notifica al componente padre cuando se cierra el formulario

  menuForm: FormGroup;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
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
      const menu = this.menuService.getMenu(this.menuId);
      if (menu) {
        this.menuForm.patchValue(menu);
        menu.foods.forEach((food) => this.addFood(food));
      }
    }
  }

  get foods() {
    return this.menuForm.get('foods') as FormArray<FormGroup>;
  }

  addFood(food: any = {}): void {
    const foodForm = this.fb.group({
      foodId: ['', Validators.required],
    });
    this.foods.push(foodForm);
  }

  removeFood(index: number): void {
    this.foods.removeAt(index);
  }

  onSubmit(): void {
    if (this.menuForm.valid) {
      if (this.isEditMode && this.menuId) {
        this.menuService.updateMenu({ ...this.menuForm.value, id: this.menuId });
      } else {
        this.menuService.addMenu(this.menuForm.value);
      }
      this.closeForm.emit(); // Notifica que el formulario debe cerrarse
    }
  }

  onCancel(): void {
    this.closeForm.emit(); // Emite el evento para cerrar el formulario sin guardar
  }
}
