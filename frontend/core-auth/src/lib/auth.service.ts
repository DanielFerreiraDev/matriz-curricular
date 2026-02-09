import { BehaviorSubject } from 'rxjs';

export class AuthService {
  private authenticated$ = new BehaviorSubject<boolean>(false);

  setAuthenticated(value: boolean): void {
    this.authenticated$.next(value);
  }

  isAuthenticated$() {
    return this.authenticated$.asObservable();
  }
}

export const authService = new AuthService();
