import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';

import {
  CatalogService,
  MatrizService,
  CatalogoItem,
  HorarioItem,
  AulaCreateRequest,
  AulaResponse, AulaUpdateRequest,
} from '@org/data-access';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <section style="display:grid; gap:16px; max-width:980px;">
      <h2>Coordenador - Gestão da Matriz Curricular</h2>

      <div style="border:1px solid #444; padding:12px; border-radius:10px;">
        <h3>Criar aula</h3>

        <div style="display:grid; gap:10px;">
          <label>
            Disciplina
            <select [(ngModel)]="form.disciplinaId">
              <option *ngFor="let d of disciplinas" [ngValue]="d.id">{{ d.nome }}</option>
            </select>
          </label>

          <label>
            Professor
            <select [(ngModel)]="form.professorId">
              <option *ngFor="let p of professores" [ngValue]="p.id">{{ p.nome }}</option>
            </select>
          </label>

          <label>
            Horário
            <select [(ngModel)]="form.horarioId">
              <option *ngFor="let h of horarios" [ngValue]="h.id">
                {{ h.diaSemana }} {{ h.inicio }}-{{ h.fim }}
              </option>
            </select>
          </label>

          <label>
            Vagas máximas
            <input type="number" [(ngModel)]="form.vagasMaximas" min="1"/>
          </label>

          <div>
            <div><b>Cursos disponíveis</b></div>
            <div style="display:flex; flex-wrap:wrap; gap:8px; margin-top:6px;">
              <label *ngFor="let c of cursos" style="border:1px solid #666; padding:6px 8px; border-radius:8px;">
                <input
                  type="checkbox"
                  [checked]="form.cursosAutorizadosIds.includes(c.id)"
                  (change)="toggleCurso(c.id)"
                />
                {{ c.nome }}
              </label>
            </div>
          </div>

          <button (click)="criarAula()">Criar</button>

          <p *ngIf="msg" style="margin:0;">{{ msg }}</p>
        </div>
      </div>

      <div style="border:1px solid #444; padding:12px; border-radius:10px;">
        <h3>Aulas</h3>
        <button (click)="carregarAulas()">Atualizar</button>

        <table border="1" cellpadding="6" cellspacing="0" style="width:100%; margin-top:10px;">
          <tr>
            <th>ID</th><th>Disciplina</th><th>Professor</th><th>Dia</th><th>Início</th><th>Fim</th><th>Vagas</th><th>Ativa</th><th>Ações</th>
          </tr>
          <ng-container *ngFor="let a of aulas">
            <tr>
              <td>{{ a.id }}</td>
              <td>{{ a.disciplinaNome }}</td>
              <td>{{ a.professorNome }}</td>
              <td>{{ a.diaSemana }}</td>
              <td>{{ a.inicio }}</td>
              <td>{{ a.fim }}</td>
              <td>{{ a.vagasOcupadas }}/{{ a.vagasMaximas }}</td>
              <td>{{ a.ativa ? 'Sim' : 'Não' }}</td>

              <td>
                <ng-container *ngIf="a.ativa; else aulaDesativada">
                  <button (click)="iniciarEdicao(a)">Editar</button>
                  <button (click)="excluir(a.id)">Desativar</button>
                </ng-container>

                <ng-template #aulaDesativada>
                  <span style="color: #999; font-weight: 600;">Desativada</span>
                </ng-template>
              </td>
            </tr>

            <tr *ngIf="editandoId === a.id">
              <td colspan="9">
                <div style="display:grid; gap:10px; padding:10px; border:1px solid #666; border-radius:10px;">
                  <b>Editando aula {{ a.id }}</b>

                  <label>
                    Professor
                    <select [(ngModel)]="editForm.professorId">
                      <option *ngFor="let p of professores" [ngValue]="p.id">{{ p.nome }}</option>
                    </select>
                  </label>

                  <label>
                    Horário
                    <select [(ngModel)]="editForm.horarioId">
                      <option *ngFor="let h of horarios" [ngValue]="h.id">
                        {{ h.diaSemana }} {{ h.inicio }}-{{ h.fim }}
                      </option>
                    </select>
                  </label>

                  <div>
                    <div><b>Cursos autorizados</b></div>
                    <div style="display:flex; flex-wrap:wrap; gap:8px; margin-top:6px;">
                      <label *ngFor="let c of cursos" style="border:1px solid #666; padding:6px 8px; border-radius:8px;">
                        <input
                          type="checkbox"
                          [checked]="editForm.cursosAutorizadosIds.includes(c.id)"
                          (change)="toggleCursoEdicao(c.id)"
                        />
                        {{ c.nome }}
                      </label>
                    </div>
                  </div>

                  <div style="display:flex; gap:8px;">
                    <button (click)="salvarEdicao(a.id)">Salvar</button>
                    <button (click)="cancelarEdicao()">Cancelar</button>
                  </div>
                </div>
              </td>
            </tr>
          </ng-container>
        </table>
      </div>
    </section>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CoordenadorPageComponent implements OnInit {
  msg = '';

  disciplinas: CatalogoItem[] = [];
  professores: CatalogoItem[] = [];
  cursos: CatalogoItem[] = [];
  horarios: HorarioItem[] = [];

  aulas: AulaResponse[] = [];

  form: AulaCreateRequest = {
    disciplinaId: 0,
    professorId: 0,
    horarioId: 0,
    vagasMaximas: 40,
    cursosAutorizadosIds: [],
  };
  editandoId: number | null = null;
  editForm: AulaUpdateRequest = {
    disciplinaId: 0,
    professorId: 0,
    horarioId: 0,
    vagasMaximas: 40,
    cursosAutorizadosIds: [],
  };


  constructor(private catalog: CatalogService, private matriz: MatrizService) {}

  ngOnInit(): void {
    forkJoin({
      disciplinas: this.catalog.disciplinas(),
      professores: this.catalog.professores(),
      cursos: this.catalog.cursos(),
      horarios: this.catalog.horarios(),
    }).subscribe({
      next: (r) => {
        this.disciplinas = r.disciplinas;
        this.professores = r.professores;
        this.cursos = r.cursos;
        this.horarios = r.horarios;

        this.form.disciplinaId = this.disciplinas[0]?.id ?? 0;
        this.form.professorId = this.professores[0]?.id ?? 0;
        this.form.horarioId = this.horarios[0]?.id ?? 0;

        this.carregarAulas();
      },
      error: (e) => (this.msg = this.errMsg(e)),
    });
  }

  toggleCurso(id: number) {
    const set = new Set(this.form.cursosAutorizadosIds);
    set.has(id) ? set.delete(id) : set.add(id);
    this.form.cursosAutorizadosIds = Array.from(set);
  }

  criarAula() {
    this.msg = '';
    if (!this.form.cursosAutorizadosIds.length) {
      this.msg = 'Selecione ao menos 1 curso disponível.';
      return;
    }
    this.matriz.criarAula(this.form).subscribe({
      next: () => {
        this.msg = 'Aula criada com sucesso!';
        this.carregarAulas();
      },
      error: (e) => (this.msg = this.errMsg(e)),
    });
  }

  carregarAulas() {
    this.matriz.listarAulasCoordenador().subscribe({
      next: (a) => (this.aulas = a),
      error: (e) => (this.msg = this.errMsg(e)),
    });
  }

  iniciarEdicao(a: AulaResponse) {
    this.msg = '';
    this.editandoId = a.id;

    this.editForm = {
      disciplinaId: a.disciplinaId,
      professorId: a.professorId,
      horarioId: this.horarios[0]?.id ?? 0,
      vagasMaximas: this.horarios[0]?.id ?? 0,
      cursosAutorizadosIds: [...(a.cursosAutorizadosIds ?? [])],
    };

  }

  cancelarEdicao() {
    this.editandoId = null;
  }

  toggleCursoEdicao(id: number) {
    const set = new Set(this.editForm.cursosAutorizadosIds);
    set.has(id) ? set.delete(id) : set.add(id);
    this.editForm.cursosAutorizadosIds = Array.from(set);
  }

  salvarEdicao(aulaId: number) {
    this.msg = '';

    if (!this.editForm.cursosAutorizadosIds.length) {
      this.msg = 'Selecione ao menos 1 curso autorizado.';
      return;
    }

    this.matriz.editarAula(aulaId, this.editForm).subscribe({
      next: () => {
        this.msg = 'Aula atualizada!';
        this.cancelarEdicao();
      },
      error: (e) => (this.msg = this.errMsg(e)),
    });
    this.carregarAulas();
  }

  excluir(aulaId: number) {
    this.msg = '';
    const ok = confirm('Desativar esta aula? (não permitido se houver matrículas)');
    if (!ok) return;

    this.matriz.excluirAula(aulaId).subscribe({
      next: () => {
        this.msg = 'Aula desativada!';
        this.carregarAulas();
      },
      error: (e) => (this.msg = this.errMsg(e)),
    });
  }

  private errMsg(e: any): string {
    if (typeof e?.error === 'string') return e.error;
    if (e?.error?.message) return e.error.message;
    return e?.message ?? 'Erro inesperado';
  }
}
