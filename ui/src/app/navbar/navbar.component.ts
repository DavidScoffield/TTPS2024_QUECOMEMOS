import { Component } from '@angular/core'
import { Router, RouterLink } from '@angular/router'
import { provideIcons } from '@ng-icons/core'
import { lucideGlobe, lucideMicVocal } from '@ng-icons/lucide'
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm'
import { BrnMenuTriggerDirective } from '@spartan-ng/ui-menu-brain'
import {
  HlmMenuBarComponent,
  HlmMenuBarItemDirective,
  HlmMenuComponent,
  HlmMenuGroupComponent,
  HlmMenuItemDirective,
  HlmMenuSeparatorComponent,
} from '@spartan-ng/ui-menu-helm'
import { AuthService } from '../services/auth-service.service'

@Component({
  selector: 'app-navbar',
  standalone: true,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  host: {
    class: 'block',
  },
  imports: [
    BrnMenuTriggerDirective,
    HlmMenuComponent,
    HlmMenuBarComponent,
    HlmMenuItemDirective,
    HlmMenuSeparatorComponent,
    HlmMenuBarItemDirective,
    HlmMenuGroupComponent,
    HlmButtonDirective,
    RouterLink,
  ],
  providers: [provideIcons({ lucideMicVocal, lucideGlobe })],
})
export class NavbarComponent {
  constructor(private authService: AuthService, private router: Router) {}

  get name(): string | undefined {
    return this.authService.getData('user')?.name
  }

  get showNavbar(): boolean {
    return this.router.url !== '/'
  }

  public logout(): void {
    this.authService.logout()
    this.router.navigate(['/'])
  }
}
