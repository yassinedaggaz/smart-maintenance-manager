import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { TechnicienService } from '@services/technicien.service';
import { AuthService } from '@services/auth.service';
import { Technicien, DisponibiliteStatut } from '../../../models';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-technicien-list',
  templateUrl: './technicien-list.component.html',
  styleUrls: ['./technicien-list.component.scss'],
  providers: [TechnicienService]
})
export class TechnicienListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['id', 'nom', 'email', 'telephone', 'competences', 'disponibilite', 'actions'];
  dataSource = new MatTableDataSource<Technicien>();
  isLoading = true;
  filterDisponibilite: DisponibiliteStatut | null = null;
  disponibiliteValues = Object.values(DisponibiliteStatut);

  constructor(
    @Inject(TechnicienService) private technicienService: TechnicienService,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}

  get isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  ngOnInit(): void {
    this.loadTechniciens();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadTechniciens(): void {
    this.isLoading = true;
    this.technicienService.getAll().subscribe({
      next: (response) => {
        let data = response.data || [];

        data = data.map((t: Technicien) => ({
          ...t,
          competences: typeof t.competences === 'string'
            ? t.competences.split(',').map((s: string) => s.trim())
            : t.competences
        }));


        if (this.filterDisponibilite) {
          data = data.filter((t: Technicien) => t.disponibilite === this.filterDisponibilite);
        }
        this.dataSource.data = data;
        
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des techniciens', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  applyFilter(disponibilite: DisponibiliteStatut | null): void {
    this.filterDisponibilite = disponibilite;
    this.loadTechniciens();
  }

  deleteTechnicien(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce technicien?')) {
      this.technicienService.delete(id).subscribe({
        next: () => {
          this.snackBar.open('Technicien supprimé avec succès', 'Fermer', { duration: 3000 });
          this.loadTechniciens();
        },
        error: () => {
          this.snackBar.open('Erreur lors de la suppression', 'Fermer', { duration: 3000 });
        }
      });
    }
  }

  getDisponibiliteColor(disponibilite: DisponibiliteStatut): string {
    switch (disponibilite) {
      case DisponibiliteStatut.DISPONIBLE: return 'success';
      case DisponibiliteStatut.OCCUPE: return 'warn';
      case DisponibiliteStatut.EN_CONGE: return 'accent';
      default: return '';
    }
  }

  getDisponibiliteLabel(disponibilite: DisponibiliteStatut): string {
    switch (disponibilite) {
      case DisponibiliteStatut.DISPONIBLE: return 'Disponible';
      case DisponibiliteStatut.OCCUPE: return 'Occupé';
      case DisponibiliteStatut.EN_CONGE: return 'En congé';
      default: return '';
    }
  }
}
