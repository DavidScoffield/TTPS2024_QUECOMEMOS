import { Component } from '@angular/core'
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
  selector: 'spartan-tabs-preview',
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
  ],
  providers: [provideIcons({ lucideChevronUp, lucideChevronDown })],
  host: {
    class: 'block w-full max-w-lg mx-auto mt-10 mb-10',
  },
  templateUrl: './login-register-tabs.component.html',
})
export class LoginRegisterTabs {}
