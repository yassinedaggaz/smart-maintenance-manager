import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Technicien, ApiResponse } from '../models';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TechnicienService {
  private apiUrl = environment.apiUrl + '/techniciens';

  constructor(private http: HttpClient) {}

  create(technicien: Partial<Technicien>): Observable<ApiResponse<Technicien>> {
    return this.http.post<ApiResponse<Technicien>>(this.apiUrl, technicien);
  }

  getById(id: number): Observable<ApiResponse<Technicien>> {
    return this.http.get<ApiResponse<Technicien>>(`${this.apiUrl}/${id}`);
  }

  getAll(): Observable<ApiResponse<Technicien[]>> {
    return this.http.get<ApiResponse<Technicien[]>>(this.apiUrl);
  }

  update(id: number, technicien: Partial<Technicien>): Observable<ApiResponse<Technicien>> {
    return this.http.put<ApiResponse<Technicien>>(`${this.apiUrl}/${id}`, technicien);
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }

  getAvailable(): Observable<ApiResponse<Technicien[]>> {
    return this.http.get<ApiResponse<Technicien[]>>(`${this.apiUrl}/available`);
  }

  changeDisponibilite(id: number, nouvelleDisponibilite: string): Observable<ApiResponse<Technicien>> {
    return this.http.put<ApiResponse<Technicien>>(`${this.apiUrl}/${id}/disponibilite/${nouvelleDisponibilite}`, {});
  }

  getTechniciensByWorkload(): Observable<ApiResponse<Technicien[]>> {
    return this.http.get<ApiResponse<Technicien[]>>(`${this.apiUrl}/workload`);
  }
}
