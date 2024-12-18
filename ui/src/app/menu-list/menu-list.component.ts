import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { MenuService } from '../services/menu.service'
import { Menu } from '../models/menu.model'
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm';
import { HlmCardContentDirective,
  HlmCardDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective} from '@spartan-ng/ui-card-helm';
import { HlmSeparatorDirective } from '@spartan-ng/ui-separator-helm';
import { MenuFormComponent } from '../menu-form/menu-form.component'
import { FoodService } from '../services/food.service';
//import { BrnSeparatorComponent } from '@spartan-ng/brain/separator';

@Component({
  selector: 'app-menu-list',
  standalone: true,
  imports: [CommonModule, HlmButtonDirective,  HlmCardContentDirective,
    HlmCardDirective,
    HlmCardHeaderDirective,
    HlmCardTitleDirective, HlmSeparatorDirective, MenuFormComponent],
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css'],
})
export class MenuListComponent implements OnInit {
  menus: Menu[]= [];
  showForm = false; // Flag para mostrar/ocultar el formulario
  selectedMenuId?: number; // Para pasar el ID al formulario (en caso de edición)

  constructor(private menuService: MenuService, private foodService: FoodService) {}

  ngOnInit(): void {
    this.menuService.getMenus().subscribe((menus) => {
      this.menus = menus;
    });
  }

  deleteMenu(id: number): void {
    this.menuService.deleteMenu(id).subscribe(()=>{
      this.menus = this.menus.filter((m) => m.id !== id);
    });
  }

  openForm(menuId?: number): void {
    this.selectedMenuId = menuId;
    this.showForm = true; // Muestra el formulario
  }

  saveMenu(menu: Menu): void {
    if (this.selectedMenuId) {
      // Actualizar usuario existente
      const index = this.menus.findIndex((m) => m.id === menu.id);
      this.menus[index] = menu;
    } else {
      // Agregar nuevo usuario
      this.menus.push(menu);
    }
    this.closeForm();
  }

  closeForm(): void {
    this.showForm = false; // Oculta el formulario
    this.selectedMenuId = undefined;
  }
  

}
