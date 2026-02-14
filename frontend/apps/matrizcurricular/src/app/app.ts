import { CommonModule } from '@angular/common';
import { AuthFacadeService } from '@org/data-access'; // se estiver no app, mas agora está na lib data-access:

import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import {RouterLink, RouterModule, RouterOutlet} from '@angular/router';
import { KeycloakService } from '@org/core-auth';

@Component({
  imports: [RouterModule, CommonModule],
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
  private readonly auth = inject(AuthFacadeService);
  get isAluno() { return this.auth.hasRole('ALUNO'); }
  get isCoord() { return this.auth.hasRole('COORDENADOR'); }

  protected title = 'Matriz Curricular';

  private readonly keycloak = inject(KeycloakService);

  logout(): void {
    this.keycloak.logout();
  }
}
