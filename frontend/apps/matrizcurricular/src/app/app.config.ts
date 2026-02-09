import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  provideHttpClient,
  withInterceptors,
  withFetch,
} from '@angular/common/http';

import { appRoutes } from './app.routes';
import { authInterceptor, KEYCLOAK_PROVIDER } from '@org/core-auth';

export const appConfig: ApplicationConfig = {
  providers: [
    KEYCLOAK_PROVIDER,
    provideRouter(appRoutes),
    provideHttpClient(withFetch(), withInterceptors([authInterceptor])),
  ],
};
