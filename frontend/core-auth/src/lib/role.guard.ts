import { CanMatchFn } from '@angular/router';
import { inject } from '@angular/core';
import { AuthFacadeService } from '@org/data-access';

export const roleGuard = (role: 'ALUNO' | 'COORDENADOR'): CanMatchFn => () => {
  const auth = inject(AuthFacadeService);
  return auth.hasRole(role);

};
