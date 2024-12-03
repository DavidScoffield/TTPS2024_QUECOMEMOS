import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterLink } from '@angular/router'
import { MenuService } from '../menu.service'
import { Menu } from '../models/menu.model'

@Component({
  selector: 'app-menu-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css'],
})
export class MenuListComponent implements OnInit {
  menus: Menu[] = []

  constructor(private menuService: MenuService) {}

  ngOnInit(): void {
    this.menuService.getMenus().subscribe((menus) => {
      this.menus = menus
    })
  }

  deleteMenu(id: number): void {
    this.menuService.deleteMenu(id)
  }
}
