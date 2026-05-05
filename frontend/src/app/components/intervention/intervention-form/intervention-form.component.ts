import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InterventionService } from '@services/intervention.service';
import { EquipementService } from '@services/equipement.service';
import { TechnicienService } from '@services/technicien.service';
import { Equipement, Technicien, StatutIntervention } from '../../../models';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-intervention-form',
  templateUrl: './intervention-form.component.html',
  styleUrls: ['./intervention-form.component.scss'],
})
export class InterventionFormComponent implements OnInit {
  interventionForm!: FormGroup;
  isLoading = false;
  isEditMode = false;
  interventionId: number | null = null;
  equipements: Equipement[] = [];
  techniciens: Technicien[] = [];
  statutValues = Object.values(StatutIntervention);

  constructor(
    private fb: FormBuilder,
    @Inject(InterventionService) private interventionService: InterventionService,
    @Inject(EquipementService) private equipementService: EquipementService,
    @Inject(TechnicienService) private technicienService: TechnicienService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.initForm();
  }

  ngOnInit(): void {
    this.loadEquipementsAndTechniciens();
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.interventionId = +id;
        this.loadIntervention();
      }
    });
  }

  initForm(): void {
    this.interventionForm = this.fb.group({
      equipementId: ['', Validators.required],
      technicienId: [''],
      statut: [StatutIntervention.PLANIFIEE, Validators.required],
      dateDebut: ['', Validators.required],
      dateFin: [''],
      description: ['', [Validators.required, Validators.minLength(10)]],
      cout: [0, [Validators.required, Validators.min(0)]]
    });
  }

  loadEquipementsAndTechniciens(): void {
    this.equipementService.getAll().subscribe({
      next: (response) => {
        this.equipements = response.data || [];
      }
    });

    this.technicienService.getAll().subscribe({
      next: (response) => {
        this.techniciens = response.data || [];
      }
    });
  }

  loadIntervention(): void {
    if (!this.interventionId) return;
    this.isLoading = true;
    this.interventionService.getById(this.interventionId).subscribe({
      next: (response) => {
        const intervention = response.data;
        this.interventionForm.patchValue({
          equipementId: intervention.equipementId,
          technicienId: intervention.technicienId,
          statut: intervention.statut,
          dateDebut: new Date(intervention.dateDebut),
          dateFin: intervention.dateFin ? new Date(intervention.dateFin) : null,
          description: intervention.description,
          cout: intervention.cout
        });
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement de l\'intervention', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  submit(): void {
    if (this.interventionForm.invalid) return;
    this.isLoading = true;

    const formValue = this.interventionForm.value;

    if (this.isEditMode && this.interventionId) {
      this.interventionService.update(this.interventionId, formValue).subscribe({
        next: () => {
          this.snackBar.open('Intervention modifiée avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/interventions']);
          this.isLoading = false;
        },
        error: () => {
          this.snackBar.open('Erreur lors de la modification', 'Fermer', { duration: 3000 });
          this.isLoading = false;
        }
      });
    } else {
      this.interventionService.create(formValue).subscribe({
        next: () => {
          this.snackBar.open('Intervention créée avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/interventions']);
          this.isLoading = false;
        },
        error: () => {
          this.snackBar.open('Erreur lors de la création', 'Fermer', { duration: 3000 });
          this.isLoading = false;
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/interventions']);
  }
}
