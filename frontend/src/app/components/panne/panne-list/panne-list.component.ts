import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { PanneService } from '@services/panne.service';
import { AuthService } from '@services/auth.service';
import { Panne, StatutPanne } from '../../../models/index';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-panne-list',
  templateUrl: './panne-list.component.html',
  styleUrls: ['./panne-list.component.scss']
})
export class PanneListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['id', 'equipementNom', 'categorie', 'statut', 'dateSignalement', 'actions'];
  dataSource = new MatTableDataSource<Panne>();
  isLoading = true;
  filterStatut: StatutPanne | null = null;
  statutValues = Object.values(StatutPanne);

  constructor(
    @Inject(PanneService) private panneService: PanneService,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}

  get isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  ngOnInit(): void {
    this.loadPannes();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadPannes(): void {
    this.isLoading = true;
    this.panneService.getAll().subscribe({
      next: (response) => {
        let data = response.data || [];
        if (this.filterStatut) {
          data = data.filter((p: { statut: StatutPanne | null; }) => p.statut === this.filterStatut);
        }
        this.dataSource.data = data;
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des pannes', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  applyFilter(statut: StatutPanne | null): void {
    this.filterStatut = statut;
    this.loadPannes();
  }

  deletePanne(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette panne?')) {
      this.panneService.delete(id).subscribe({
        next: () => {
          this.snackBar.open('Panne supprimée avec succès', 'Fermer', { duration: 3000 });
          this.loadPannes();
        },
        error: () => {
          this.snackBar.open('Erreur lors de la suppression', 'Fermer', { duration: 3000 });
        }
      });
    }
  }

  getStatutColor(statut: StatutPanne): string {
    switch (statut) {
      case StatutPanne.SIGNALEEE: return 'warn';
      case StatutPanne.EN_COURS: return 'accent';
      case StatutPanne.RESOLUE: return 'success';
      default: return '';
    }
  }

  getStatutLabel(statut: StatutPanne): string {
    switch (statut) {
      case StatutPanne.SIGNALEEE: return 'Signalée';
      case StatutPanne.EN_COURS: return 'En cours';
      case StatutPanne.RESOLUE: return 'Résolue';
      default: return '';
    }
  }
}
