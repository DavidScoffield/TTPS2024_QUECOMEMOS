import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  getToken(): string | null {
    return localStorage.getItem('token')
  }

  setToken(token: string): void {
    localStorage.setItem('token', token)
  }

  setData(key: string, data: any): void {
    localStorage.setItem(key, JSON.stringify(data))
  }

  getData(key: string): any {
    const data = localStorage.getItem(key)
    return data ? JSON.parse(data) : null
  }

  logout(): void {
    localStorage.clear()
  }

  isAuthenticated(): boolean {
    const token = this.getToken()
    return token != null
  }
}
