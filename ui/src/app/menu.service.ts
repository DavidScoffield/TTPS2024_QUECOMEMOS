// src/app/menu.service.ts
import { Injectable } from '@angular/core'
import { BehaviorSubject, Observable } from 'rxjs'
import { Menu } from './models/menu.model'
import { Food } from './models/food.model'

@Injectable({
  providedIn: 'root',
})
export class MenuService {
  private menus: Menu[] = []
  private menusSubject = new BehaviorSubject<Menu[]>([])

  constructor() {
    // Initialize with some sample data
    this.addMenu({
      name: 'Lunch Menu',
      price: 10,
      picture: 'https://via.placeholder.com/150',
      foods: [
        {
          name: 'Burger',
          isVegetarian: false,
          type: 'Main',
        },
        {
          name: 'Salad',
          isVegetarian: true,
          type: 'Side',
        },
      ],
    })
  }

  getMenus(): Observable<Menu[]> {
    return this.menusSubject.asObservable()
  }

  getMenu(id: number): Menu | undefined {
    return this.menus.find((menu) => menu.id === id)
  }

  addMenu(menu: Omit<Menu, 'id'>): void {
    const newMenu: Menu = {
      ...menu,
      id: this.menus.length + 1,
      foods: menu.foods.map((food, index) => ({ ...food, id: index + 1 })),
    }
    this.menus.push(newMenu)
    this.menusSubject.next([...this.menus])
  }

  updateMenu(updatedMenu: Menu): void {
    const index = this.menus.findIndex((menu) => menu.id === updatedMenu.id)
    if (index !== -1) {
      this.menus[index] = updatedMenu
      this.menusSubject.next([...this.menus])
    }
  }

  deleteMenu(id: number): void {
    this.menus = this.menus.filter((menu) => menu.id !== id)
    this.menusSubject.next([...this.menus])
  }

  addFood(menuId: number, food: Omit<Food, 'id'>): void {
    const menu = this.menus.find((m) => m.id === menuId)
    if (menu) {
      const newFood: Food = { ...food, id: menu.foods.length + 1 }
      menu.foods.push(newFood)
      this.menusSubject.next([...this.menus])
    }
  }

  updateFood(menuId: number, updatedFood: Food): void {
    const menu = this.menus.find((m) => m.id === menuId)
    if (menu) {
      const index = menu.foods.findIndex((f) => f.id === updatedFood.id)
      if (index !== -1) {
        menu.foods[index] = updatedFood
        this.menusSubject.next([...this.menus])
      }
    }
  }

  deleteFood(menuId: number, foodId: number): void {
    const menu = this.menus.find((m) => m.id === menuId)
    if (menu) {
      menu.foods = menu.foods.filter((f) => f.id !== foodId)
      this.menusSubject.next([...this.menus])
    }
  }
}
