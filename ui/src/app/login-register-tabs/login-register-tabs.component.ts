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
import { HlmSelectImports } from '@spartan-ng/ui-select-helm'
import {
  HlmTabsComponent,
  HlmTabsContentDirective,
  HlmTabsListComponent,
  HlmTabsTriggerDirective,
} from '@spartan-ng/ui-tabs-helm'
import { CustomError } from '../error/CustomError'
import { UserService } from '../services/user.service'
import { toast } from 'ngx-sonner'
import { BrnSelectImports } from '@spartan-ng/ui-select-brain'
import { HlmToasterComponent } from '@spartan-ng/ui-sonner-helm'

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
  showToast() {
    console.log('toastr')
    toast('Event has been created', {
      description: 'Sunday, December 03, 2024 at 9:00 AM',
      action: {
        label: 'Undo',
        onClick: () => console.log('Undo'),
      },
    })
  }
  loginForm: FormGroup
  registerForm: FormGroup

  constructor(private fb: FormBuilder, private registerService: UserService) {
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
          '1231231',
          [
            Validators.required,
            Validators.minLength(7),
            Validators.maxLength(8),
          ],
        ],
        password: ['password', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['password', [Validators.required]],
        email: ['1@gmail.com', [Validators.required, Validators.email]],
        name: ['david', [Validators.required]],
        role: ['CLIENT', [Validators.required]],
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
    if (!this.registerForm.valid) {
      console.log('Formulario inválido')
    }
    const { dni, email, password, role, name } = this.registerForm.value

    this.registerService
      .register({ dni, email, password, roleSelected: role, name })
      .subscribe({
        next: (response) => {
          toast.success('Usuario registrado', {
            description: 'Ya puedes iniciar sesión con tu cuenta.',
          })

          this.resetForm(this.registerForm)

          this.loginButtonComponent.nativeElement.click()
        },
        error: (error: CustomError) => {
          console.error('Error en el registro:', error)

          toast.error('Error en el registro', {
            description: error.message,
          })
        },
      })
  }

  private resetForm(form: FormGroup) {
    form.reset()
    Object.keys(form.controls).forEach((key) => {
      form.get(key)?.setErrors(null)
    })
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

  public showPassword(input: HTMLInputElement) {
    input.type = input.type === 'password' ? 'text' : 'password'
  }
}
