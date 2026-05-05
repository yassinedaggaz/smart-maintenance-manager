import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Panne, ApiResponse } from '../models/index';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PanneService {
  private apiUrl = environment.apiUrl + '/pannes';

  constructor(private http: HttpClient) {}

  create(panne: Partial<Panne>): Observable<ApiResponse<Panne>> {
    return this.http.post<ApiResponse<Panne>>(this.apiUrl, panne);
  }

  getById(id: number): Observable<ApiResponse<Panne>> {
    return this.http.get<ApiResponse<Panne>>(`${this.apiUrl}/${id}`);
  }

  getAll(): Observable<ApiResponse<Panne[]>> {
    return this.http.get<ApiResponse<Panne[]>>(this.apiUrl);
  }

  update(id: number, panne: Partial<Panne>): Observable<ApiResponse<Panne>> {
    return this.http.put<ApiResponse<Panne>>(`${this.apiUrl}/${id}`, panne);
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }

  getByStatut(statut: string): Observable<ApiResponse<Panne[]>> {
    return this.http.get<ApiResponse<Panne[]>>(`${this.apiUrl}/statut/${statut}`);
  }

  getByEquipementId(equipementId: number): Observable<ApiResponse<Panne[]>> {
    return this.http.get<ApiResponse<Panne[]>>(`${this.apiUrl}/equipement/${equipementId}`);
  }

  changeStatut(id: number, nouveauStatut: string): Observable<ApiResponse<Panne>> {
    return this.http.put<ApiResponse<Panne>>(`${this.apiUrl}/${id}/statut/${nouveauStatut}`, {});
  }
}
