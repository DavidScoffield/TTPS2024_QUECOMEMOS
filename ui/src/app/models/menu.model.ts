import { Food } from './food.model'

export interface Menu {
  id?: number
  name: string
  foods: Food[]
  price: number
  picture: string
}
