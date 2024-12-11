import { Component, ElementRef, ViewChild } from '@angular/core'
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { Router } from '@angular/router'
import { provideIcons } from '@ng-icons/core'
import { lucideEye, lucideLoaderCircle } from '@ng-icons/lucide'
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
import { toast } from 'ngx-sonner'
import { HlmIconComponent } from '../../../libs/ui/ui-icon-helm/src/lib/hlm-icon.component'
import { CustomError } from '../error/CustomError'
import { UserService } from '../services/user.service'

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
    HlmIconComponent,
  ],
  providers: [
    provideIcons({
      lucideEye,
      lucideLoaderCircle,
    }),
  ],
  host: {
    class: 'block w-full max-w-lg mx-auto mt-24 mb-10',
  },
  templateUrl: './login-register-tabs.component.html',
})
export class LoginRegisterTabs {
  @ViewChild('loginButton') loginButtonComponent!: ElementRef<HTMLButtonElement>
  @ViewChild('registerButton')
  registerButtonComponent!: ElementRef<HTMLButtonElement>

  isLoadingLogin = false
  isLoadingRegister = false

  loginForm: FormGroup
  registerForm: FormGroup

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
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
    if (!this.loginForm.valid) {
      console.log('Formulario de Login invalido', this.loginForm)
    }

    const { dni, password } = this.loginForm.value

    this.isLoadingLogin = true

    this.userService.login({ dni, password }).subscribe({
      next: (response) => {
        toast.success('Usuario logueado', {
          description: 'Bienvenido de nuevo.',
        })

        this.resetForm(this.loginForm)
        this.router.navigate(['/menus'])
      },
      error: (error: CustomError) => {
        console.error('Error en el login:', error)

        toast.error('Error en el login', {
          description: error.message,
        })
      },
      complete: () => {
        this.isLoadingLogin = false
      },
    })
  }

  onRegister() {
    if (!this.registerForm.valid) {
      console.log('Formulario de registro inválido')
    }
    const { dni, email, password, role, name } = this.registerForm.value

    this.isLoadingRegister = true

    this.userService
      .register({ dni, email, password, roleSelected: role, name })
      .subscribe({
        next: () => {
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
        complete: () => {
          this.isLoadingRegister = false
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
