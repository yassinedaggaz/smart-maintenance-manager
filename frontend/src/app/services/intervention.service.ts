import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Intervention, ApiResponse } from '../models';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InterventionService {
  private apiUrl = environment.apiUrl + '/interventions';

  constructor(private http: HttpClient) {}

  create(intervention: Partial<Intervention>): Observable<ApiResponse<Intervention>> {
    return this.http.post<ApiResponse<Intervention>>(this.apiUrl, intervention);
  }

  getById(id: number): Observable<ApiResponse<Intervention>> {
    return this.http.get<ApiResponse<Intervention>>(`${this.apiUrl}/${id}`);
  }

  getAll(): Observable<ApiResponse<Intervention[]>> {
    return this.http.get<ApiResponse<Intervention[]>>(this.apiUrl);
  }

  update(id: number, intervention: Partial<Intervention>): Observable<ApiResponse<Intervention>> {
    return this.http.put<ApiResponse<Intervention>>(`${this.apiUrl}/${id}`, intervention);
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }

  getByStatut(statut: string): Observable<ApiResponse<Intervention[]>> {
    return this.http.get<ApiResponse<Intervention[]>>(`${this.apiUrl}/statut/${statut}`);
  }

  getByEquipementId(equipementId: number): Observable<ApiResponse<Intervention[]>> {
    return this.http.get<ApiResponse<Intervention[]>>(`${this.apiUrl}/equipement/${equipementId}`);
  }

  getByTechnicienId(technicienId: number): Observable<ApiResponse<Intervention[]>> {
    return this.http.get<ApiResponse<Intervention[]>>(`${this.apiUrl}/technicien/${technicienId}`);
  }

  assignTechnicien(interventionId: number, technicienId: number): Observable<ApiResponse<Intervention>> {
    return this.http.post<ApiResponse<Intervention>>(
      `${this.apiUrl}/${interventionId}/assign/${technicienId}`, 
      {}
    );
  }

  changeStatut(id: number, nouveauStatut: string): Observable<ApiResponse<Intervention>> {
    return this.http.put<ApiResponse<Intervention>>(`${this.apiUrl}/${id}/statut/${nouveauStatut}`, {});
  }
}
