import { APP_INITIALIZER, Provider } from '@angular/core';
import { KeycloakService } from './keycloak.service';

export function initKeycloak(keycloak: KeycloakService) {
  return () => keycloak.init();
}

export const KEYCLOAK_PROVIDER: Provider = {
  provide: APP_INITIALIZER,
  multi: true,
  deps: [KeycloakService],
  useFactory: (keycloak: KeycloakService) => {
    return () => keycloak.init();
  },
};
