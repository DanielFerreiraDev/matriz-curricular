import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { KeycloakService } from '@org/core-auth';

@Component({
  imports: [RouterModule],
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
  protected title = 'Matriz Curricular';

  private readonly keycloak = inject(KeycloakService);

  logout(): void {
    this.keycloak.logout();
  }
}
