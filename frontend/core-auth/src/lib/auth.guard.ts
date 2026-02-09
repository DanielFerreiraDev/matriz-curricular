import { CanActivateFn } from '@angular/router';
import { map } from 'rxjs';
import { authService } from './auth.service';

export const authGuard: CanActivateFn = () =>
  authService.isAuthenticated$().pipe(map(Boolean));
