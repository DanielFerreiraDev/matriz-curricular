import {Component, ChangeDetectionStrategy, OnInit, ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatrizService, AulaResponse, MinhaMatricula, AuthFacadeService } from '@org/data-access';

@Component({
  standalone: true,
  imports: [CommonModule],
  template: `
    <section style="display:grid; gap:16px; max-width:980px;">
      <h2>Aluno - Matrícula</h2>

      <p *ngIf="cursoId == null" style="color:#b00;">
        Token sem <b>cursoId</b>. Configure o atributo do usuário no Keycloak e mapeie para o token.
      </p>

      <div style="border:1px solid #444; padding:12px; border-radius:10px;">
        <h3>Aulas disponíveis</h3>
        <button (click)="carregarDisponiveis()">Atualizar</button>
        <p *ngIf="msg" style="margin:8px 0 0;">{{ msg }}</p>

        <table border="1" cellpadding="6" cellspacing="0" style="width:100%; margin-top:10px;">
          <tr>
            <th>ID</th><th>Disciplina</th><th>Professor</th><th>Dia</th><th>Início</th><th>Fim</th><th>Vagas</th><th></th>
          </tr>
          <tr *ngFor="let a of disponiveis">
            <td>{{ a.id }}</td>
            <td>{{ a.disciplinaNome }}</td>
            <td>{{ a.professorNome }}</td>
            <td>{{ a.diaSemana }}</td>
            <td>{{ a.inicio }}</td>
            <td>{{ a.fim }}</td>
            <td>{{ a.vagasOcupadas }}/{{ a.vagasMaximas }}</td>
            <td><button (click)="matricular(a.id)">Matricular</button></td>
          </tr>
        </table>
      </div>

      <div style="border:1px solid #444; padding:12px; border-radius:10px;">
        <h3>Minhas matrículas</h3>
        <button (click)="carregarMinhas()">Atualizar</button>

        <table border="1" cellpadding="6" cellspacing="0" style="width:100%; margin-top:10px;">
          <tr>
            <th>ID</th><th>Aula</th><th>Disciplina</th><th>Professor</th><th>Dia</th><th>Início</th><th>Fim</th>
          </tr>
          <tr *ngFor="let m of minhas">
            <td>{{ m.matriculaId }}</td>
            <td>{{ m.aulaId }}</td>
            <td>{{ m.disciplina }}</td>
            <td>{{ m.professor }}</td>
            <td>{{ m.diaSemana }}</td>
            <td>{{ m.inicio }}</td>
            <td>{{ m.fim }}</td>
          </tr>
        </table>
      </div>
    </section>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlunoPageComponent implements OnInit {
  msg = '';
  disponiveis: AulaResponse[] = [];
  minhas: MinhaMatricula[] = [];
  cursoId?: number;

  constructor(private matriz: MatrizService, private auth: AuthFacadeService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cursoId = this.auth.cursoId();
    this.carregarDisponiveis();
    this.carregarMinhas();
  }

  carregarDisponiveis() {
    this.msg = '';
    this.matriz.listarDisponiveis().subscribe({
      next: (a) => {
        this.disponiveis = a;
        this.cdr.detectChanges();
      },
      error: (e) => {
        this.msg = this.errMsg(e.error);
        this.cdr.detectChanges();
      },
    });
  }

  carregarMinhas() {
    this.matriz.minhasMatriculas().subscribe({
      next: (m) => {
        this.minhas = m;
        this.cdr.detectChanges();
      },
      error: (e) => {
        this.msg = this.errMsg(e.error);
        this.cdr.detectChanges();
      },
    });
  }

  matricular(aulaId: number) {
    this.msg = '';
    this.matriz.matricular(aulaId).subscribe({
      next: () => {
        this.msg = 'Matrícula realizada!';
        this.carregarDisponiveis();
        this.carregarMinhas();
      },
      error: (e) => {
        this.msg = this.errMsg(e.error);
        this.cdr.detectChanges();
      },
    });
  }

  private errMsg(e: any): string {
    if (typeof e?.error === 'string') return e.error;
    if (e?.error?.message) return e.error.message;
    return e?.message ?? 'Erro inesperado';
  }
}
