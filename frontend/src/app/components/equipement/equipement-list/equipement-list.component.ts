import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { EquipementService } from '@services/equipement.service';
import { AuthService } from '@services/auth.service';
import { Equipement, EtatEquipement } from '../../../models';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-equipement-list',
  templateUrl: './equipement-list.component.html',
  styleUrls: ['./equipement-list.component.scss']
})
export class EquipementListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['id', 'nom', 'type', 'etat', 'dateAcquisition', 'actions'];
  dataSource = new MatTableDataSource<Equipement>();
  isLoading = true;
  filterEtat: EtatEquipement | null = null;
  etatValues = Object.values(EtatEquipement);

  constructor(
    // private equipementService: EquipementService,
    @Inject(EquipementService) private equipementService: EquipementService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}

  get isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  ngOnInit(): void {
    this.loadEquipements();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadEquipements(): void {
    this.isLoading = true;
    this.equipementService.getAll().subscribe({
      next: (response) => {
        let data = response.data || [];
        if (this.filterEtat) {
          data = data.filter((e: { etat: EtatEquipement | null; }) => e.etat === this.filterEtat);
        }
        this.dataSource.data = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.snackBar.open('Erreur lors du chargement des équipements', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  applyFilter(etat: EtatEquipement | null): void {
    this.filterEtat = etat;
    this.loadEquipements();
  }

  deleteEquipement(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet équipement?')) {
      this.equipementService.delete(id).subscribe({
        next: () => {
          this.snackBar.open('Équipement supprimé avec succès', 'Fermer', { duration: 3000 });
          this.loadEquipements();
        },
        error: () => {
          this.snackBar.open('Erreur lors de la suppression', 'Fermer', { duration: 3000 });
        }
      });
    }
  }

  getEtatColor(etat: EtatEquipement): string {
    switch (etat) {
      case EtatEquipement.EN_SERVICE: return 'success';
      case EtatEquipement.EN_PANNE: return 'warn';
      case EtatEquipement.EN_MAINTENANCE: return 'accent';
      default: return '';
    }
  }

  getEtatLabel(etat: EtatEquipement): string {
    switch (etat) {
      case EtatEquipement.EN_SERVICE: return 'En service';
      case EtatEquipement.EN_PANNE: return 'En panne';
      case EtatEquipement.EN_MAINTENANCE: return 'En maintenance';
      default: return '';
    }
  }
}
