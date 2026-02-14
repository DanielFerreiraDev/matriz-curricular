import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AulaCreateRequest, AulaResponse, AulaUpdateRequest, MinhaMatricula} from './api';
import {environment} from "./environment";

@Injectable({ providedIn: 'root' })
export class MatrizService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // COORDENADOR
  criarAula(body: AulaCreateRequest) {
    return this.http.post<AulaResponse>(`${this.baseUrl}/api/aulas`, body);
  }

  listarAulasCoordenador() {
    return this.http.get<AulaResponse[]>(`${this.baseUrl}/api/aulas`);
  }

  editarAula(aulaId: number, body: AulaUpdateRequest) {
    return this.http.put<AulaResponse>(`${this.baseUrl}/api/aulas/${aulaId}`, body);
  }

  excluirAula(aulaId: number) {
    return this.http.delete<void>(`${this.baseUrl}/api/aulas/${aulaId}`);
  }

  // ALUNO
  listarDisponiveis() {
    return this.http.get<AulaResponse[]>(`${this.baseUrl}/api/aulas/disponiveis`);
  }

  matricular(aulaId: number) {
    return this.http.post<void>(`${this.baseUrl}/api/matriculas/aula/${aulaId}`, {});
  }

  minhasMatriculas() {
    return this.http.get<MinhaMatricula[]>(`${this.baseUrl}/api/matriculas`);
  }
}
