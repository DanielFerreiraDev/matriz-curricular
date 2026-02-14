export type CatalogoItem = { id: number; nome: string };
export type HorarioItem = { id: number; diaSemana: string; inicio: string; fim: string };

export type AulaCreateRequest = {
  disciplinaId: number;
  professorId: number;
  horarioId: number;
  vagasMaximas: number;
  cursosAutorizadosIds: number[];
};

export type AulaUpdateRequest = {
  disciplinaId: number;
  professorId: number;
  horarioId: number;
  vagasMaximas: number;
  cursosAutorizadosIds: number[];
}

export type AulaResponse = {
  id: number;
  disciplinaId: number;
  disciplinaNome: string;
  professorId: number;
  professorNome: string;
  horarioId: number;
  diaSemana: string;
  inicio: string;
  fim: string;
  vagasMaximas: number;
  vagasOcupadas: number;
  ativa: boolean;
  cursosAutorizadosIds: number[];
};

export type MinhaMatricula = {
  matriculaId: number;
  aulaId: number;
  disciplina: string;
  professor: string;
  diaSemana: string;
  inicio: string;
  fim: string;
};
