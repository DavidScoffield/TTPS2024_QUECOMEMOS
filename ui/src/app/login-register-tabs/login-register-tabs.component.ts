import { Component } from '@angular/core'
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { provideIcons } from '@ng-icons/core'
import { lucideChevronDown, lucideChevronUp } from '@ng-icons/lucide'
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm'
import {
  HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective,
} from '@spartan-ng/ui-card-helm'
import { HlmInputDirective } from '@spartan-ng/ui-input-helm'
import { HlmLabelDirective } from '@spartan-ng/ui-label-helm'
import { BrnSelectImports } from '@spartan-ng/ui-select-brain'
import { HlmSelectImports } from '@spartan-ng/ui-select-helm'
import {
  HlmTabsComponent,
  HlmTabsContentDirective,
  HlmTabsListComponent,
  HlmTabsTriggerDirective,
} from '@spartan-ng/ui-tabs-helm'

@Component({
  selector: 'login-register-tabs',
  standalone: true,
  imports: [
    HlmTabsComponent,
    HlmTabsListComponent,
    HlmTabsTriggerDirective,
    HlmTabsContentDirective,

    HlmCardContentDirective,
    HlmCardDescriptionDirective,
    HlmCardDirective,
    HlmCardFooterDirective,
    HlmCardHeaderDirective,
    HlmCardTitleDirective,

    HlmLabelDirective,
    HlmInputDirective,
    HlmButtonDirective,

    BrnSelectImports,
    HlmSelectImports,

    ReactiveFormsModule,
  ],
  providers: [provideIcons({ lucideChevronUp, lucideChevronDown })],
  host: {
    class: 'block w-full max-w-lg mx-auto mt-10 mb-10',
  },
  templateUrl: './login-register-tabs.component.html',
})
export class LoginRegisterTabs {
  loginForm: FormGroup
  registerForm: FormGroup

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      dni: [
        '',
        [Validators.required, Validators.minLength(7), Validators.maxLength(8)],
      ],
      password: ['', [Validators.required, Validators.minLength(6)]],
    })

    this.registerForm = this.fb.group(
      {
        dni: [
          '',
          [
            Validators.required,
            Validators.minLength(7),
            Validators.maxLength(8),
          ],
        ],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        name: ['', [Validators.required]],
        role: ['', [Validators.required]],
      },
      {
        validators: [this.matchPasswords('password', 'confirmPassword')],
      }
    )
  }

  onLogin() {
    if (this.loginForm.valid) {
      console.log('Formulario de Login:', this.loginForm.value)
    }
  }

  onRegister() {
    if (this.registerForm.valid) {
      console.log('Formulario de Registro:', this.registerForm.value)
    }
  }

  private matchPasswords(passwordKey: string, confirmPasswordKey: string) {
    return (formGroup: AbstractControl) => {
      const password = formGroup.get(passwordKey)?.value
      const confirmPassword = formGroup.get(confirmPasswordKey)?.value

      if (password !== confirmPassword) {
        formGroup.get(confirmPasswordKey)?.setErrors({ passwordMismatch: true })
      } else {
        formGroup.get(confirmPasswordKey)?.setErrors(null)
      }
    }
  }
}
