import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginRegisterTabsComponent } from './login-register-tabs.component';

describe('LoginRegisterTabsComponent', () => {
  let component: LoginRegisterTabsComponent;
  let fixture: ComponentFixture<LoginRegisterTabsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginRegisterTabsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginRegisterTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
