import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CatalogoItem, HorarioItem } from './api';
import {environment} from "./environment";

@Injectable({ providedIn: 'root' })
export class CatalogService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  disciplinas() { return this.http.get<CatalogoItem[]>(`${this.baseUrl}/api/catalogo/disciplinas`); }
  professores() { return this.http.get<CatalogoItem[]>(`${this.baseUrl}/api/catalogo/professores`); }
  cursos() { return this.http.get<CatalogoItem[]>(`${this.baseUrl}/api/catalogo/cursos`); }
  horarios() { return this.http.get<HorarioItem[]>(`${this.baseUrl}/api/catalogo/horarios`); }
}
