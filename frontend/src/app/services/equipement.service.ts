import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipement, ApiResponse } from '../models/index';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EquipementService {
  private apiUrl = environment.apiUrl + '/equipements';

  constructor(private http: HttpClient) {}

  create(equipement: Partial<Equipement>): Observable<ApiResponse<Equipement>> {
    return this.http.post<ApiResponse<Equipement>>(this.apiUrl, equipement);
  }

  getById(id: number): Observable<ApiResponse<Equipement>> {
    return this.http.get<ApiResponse<Equipement>>(`${this.apiUrl}/${id}`);
  }

  getAll(): Observable<ApiResponse<Equipement[]>> {
    return this.http.get<ApiResponse<Equipement[]>>(this.apiUrl);
  }

  update(id: number, equipement: Partial<Equipement>): Observable<ApiResponse<Equipement>> {
    return this.http.put<ApiResponse<Equipement>>(`${this.apiUrl}/${id}`, equipement);
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }

  getByEtat(etat: string): Observable<ApiResponse<Equipement[]>> {
    return this.http.get<ApiResponse<Equipement[]>>(`${this.apiUrl}/etat/${etat}`);
  }

  getTop5WithMostPannes(): Observable<ApiResponse<Equipement[]>> {
    return this.http.get<ApiResponse<Equipement[]>>(`${this.apiUrl}/top/pannes`);
  }

  changeEtat(id: number, nouvelEtat: string): Observable<ApiResponse<Equipement>> {
    return this.http.put<ApiResponse<Equipement>>(`${this.apiUrl}/${id}/etat/${nouvelEtat}`, {});
  }
}
