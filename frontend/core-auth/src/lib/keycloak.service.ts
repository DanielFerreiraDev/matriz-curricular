import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import Keycloak from 'keycloak-js';

@Injectable({ providedIn: 'root' })
export class KeycloakService {
  private readonly platformId = inject(PLATFORM_ID);
  private keycloak?: Keycloak;

  get token(): string | undefined {
    return this.keycloak?.token;
  }

  async init(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    this.keycloak = new Keycloak({
      url: 'http://localhost:8080',
      realm: 'matriz-curricular',
      clientId: 'frontend',
    });

    const authenticated = await this.keycloak.init({
      onLoad: 'login-required',
      checkLoginIframe: false,
      silentCheckSsoRedirectUri:
        window.location.origin + '/assets/silent-check-sso.html',
    });

  }

  logout(): void {
    if (this.keycloak instanceof Keycloak) {
      this.keycloak.logout({
        redirectUri: window.location.origin
      });
    }
  }

}
