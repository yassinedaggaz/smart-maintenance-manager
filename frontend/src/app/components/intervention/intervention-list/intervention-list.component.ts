import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { InterventionService } from '@services/intervention.service';
import { Intervention, StatutIntervention } from '../../../models';
import { AuthService } from '@services/auth.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EquipementService } from '@services/equipement.service';

@Component({
  selector: 'app-intervention-list',
  templateUrl: './intervention-list.component.html',
  styleUrls: ['./intervention-list.component.scss']
})
export class InterventionListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['id', 'equipementNom', 'technicienNom', 'statut', 'dateDebut', 'cout', 'actions'];
  dataSource = new MatTableDataSource<Intervention>();
  isLoading = true;
  filterStatut: StatutIntervention | null = null;
  statutValues = Object.values(StatutIntervention);

  constructor(
    @Inject(EquipementService) private equipementService: EquipementService,
    @Inject(InterventionService) private interventionService: InterventionService,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}

  get isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  ngOnInit(): void {
    this.loadInterventions();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadInterventions(): void {
    this.isLoading = true;
    this.interventionService.getAll().subscribe({
      next: (response) => {
        let data = response.data || [];
        if (this.filterStatut) {
          data = data.filter((i: Intervention) => i.statut === this.filterStatut);
        }
        this.dataSource.data = data;
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des interventions', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  applyFilter(statut: StatutIntervention | null): void {
    this.filterStatut = statut;
    this.loadInterventions();
  }

  deleteIntervention(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette intervention?')) {
      this.interventionService.delete(id).subscribe({
        next: () => {
          this.snackBar.open('Intervention supprimée avec succès', 'Fermer', { duration: 3000 });
          this.loadInterventions();
        },
        error: () => {
          this.snackBar.open('Erreur lors de la suppression', 'Fermer', { duration: 3000 });
        }
      });
    }
  }

  getStatutColor(statut: StatutIntervention): string {
    switch (statut) {
      case StatutIntervention.PLANIFIEE: return 'info';
      case StatutIntervention.EN_COURS: return 'accent';
      case StatutIntervention.TERMINEEE: return 'success';
      case StatutIntervention.ANNULEE: return 'warn';
      default: return '';
    }
  }

  getStatutLabel(statut: StatutIntervention): string {
    switch (statut) {
      case StatutIntervention.PLANIFIEE: return 'Planifiée';
      case StatutIntervention.EN_COURS: return 'En cours';
      case StatutIntervention.TERMINEEE: return 'Terminée';
      case StatutIntervention.ANNULEE: return 'Annulée';
      default: return '';
    }
  }
}
