import { CommonModule } from '@angular/common'
import { Component } from '@angular/core'
import { RouterModule } from '@angular/router'
import { NavbarComponent } from './navbar/navbar.component'
import { HlmToasterComponent } from '../../libs/ui/ui-sonner-helm/src/lib/hlm-toaster.component'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent, HlmToasterComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'QUECOMEMOS'
}
