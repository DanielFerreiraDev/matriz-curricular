import { Injectable, inject } from '@angular/core';
import { KeycloakService } from '@org/core-auth';

type JwtPayload = {
  sub?: string;
  realm_access?: { roles?: string[] };
  cursoId?: number | string;
};

@Injectable({ providedIn: 'root' })
export class AuthFacadeService {
  private readonly kc = inject(KeycloakService);

  token(): string | undefined {
    return this.kc.token;
  }

  payload(): JwtPayload | null {
    const token = this.kc.token;
    if (!token) return null;

    const parts = token.split('.');
    if (parts.length < 2) return null;

    try {
      const json = this.base64UrlDecode(parts[1]);
      return JSON.parse(json) as JwtPayload;
    } catch {
      return null;
    }
  }

  sub(): string | undefined {
    return this.payload()?.sub;
  }

  roles(): string[] {
    return this.payload()?.realm_access?.roles ?? [];
  }

  hasRole(role: 'ALUNO' | 'COORDENADOR'): boolean {
    return this.roles().includes(role);
  }

  cursoId(): number | undefined {
    const v = this.payload()?.cursoId;
    if (typeof v === 'number') return v;
    if (typeof v === 'string') {
      const n = Number(v);
      return Number.isFinite(n) ? n : undefined;
    }
    return undefined;
  }

  private base64UrlDecode(input: string): string {
    const base64 = input.replace(/-/g, '+').replace(/_/g, '/');
    const padded = base64 + '==='.slice((base64.length + 3) % 4);
    const decoded = atob(padded);
    return decodeURIComponent(
      decoded
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
  }
}
