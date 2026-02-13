import { Route } from '@angular/router';
import { roleGuard } from '../../../../core-auth/src/lib/role.guard';

export const appRoutes: Route[] = [
  { path: '', redirectTo: 'aluno', pathMatch: 'full' },

  {
    path: 'coordenador',
    canMatch: [roleGuard('COORDENADOR')],
    loadChildren: () =>
      import('@org/feature-coordenador').then((m) => m.featureCoordenadorRoutes),
  },
  {
    path: 'aluno',
    canMatch: [roleGuard('ALUNO')],
    loadChildren: () =>
      import('@org/feature-aluno').then((m) => m.featureAlunoRoutes),
  },

  { path: '**', redirectTo: 'aluno' },
];
