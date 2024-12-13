import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterLink } from '@angular/router'
import { MenuService } from '../services/menu.service'
import { Menu } from '../models/menu.model'
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm';
import { HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective} from '@spartan-ng/ui-card-helm';
import { HlmSeparatorDirective } from '@spartan-ng/ui-separator-helm';
import { MenuFormComponent } from '../menu-form/menu-form.component'
//import { BrnSeparatorComponent } from '@spartan-ng/brain/separator';

@Component({
  selector: 'app-menu-list',
  standalone: true,
  imports: [CommonModule, RouterLink, HlmButtonDirective,  HlmCardContentDirective,
    HlmCardDescriptionDirective,
    HlmCardDirective,
    HlmCardFooterDirective,
    HlmCardHeaderDirective,
    HlmCardTitleDirective, HlmSeparatorDirective, MenuFormComponent],
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css'],
})
export class MenuListComponent implements OnInit {
  menus: Menu[] = [];
  showForm = false; // Flag para mostrar/ocultar el formulario
  selectedMenuId?: number; // Para pasar el ID al formulario (en caso de edición)

  constructor(private menuService: MenuService) {}

  ngOnInit(): void {
    this.menuService.getMenus().subscribe((menus) => {
      this.menus = menus;
    });
  }

  deleteMenu(id: number): void {
    this.menuService.deleteMenu(id);
  }

  openForm(menuId?: number): void {
    this.selectedMenuId = menuId;
    this.showForm = true; // Muestra el formulario
  }

  closeForm(): void {
    this.showForm = false; // Oculta el formulario
    this.selectedMenuId = undefined;
    this.ngOnInit(); // Refresca la lista de menús
  }
}
