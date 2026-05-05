import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PanneService } from '@services/panne.service';
import { EquipementService } from '@services/equipement.service';
import { Equipement, StatutPanne, CategoriePanne } from '../../../models';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-panne-form',
  templateUrl: './panne-form.component.html',
  styleUrls: ['./panne-form.component.scss']
})
export class PanneFormComponent implements OnInit {
  panneForm!: FormGroup;
  isLoading = false;
  isEditMode = false;
  panneId: number | null = null;
  equipements: Equipement[] = [];
  categorieValues = Object.values(CategoriePanne);
  statutValues = Object.values(StatutPanne);

  constructor(
    private fb: FormBuilder,
    @Inject(PanneService) private panneService: PanneService,
    @Inject(EquipementService) private equipementService: EquipementService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.initForm();
  }

  ngOnInit(): void {
    this.loadEquipements();
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.panneId = +id;
        this.loadPanne();
      }
    });
  }

  initForm(): void {
    this.panneForm = this.fb.group({
      equipementId: ['', Validators.required],
      categorie: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]],
      severite: ['MOYENNE', Validators.required],
      statut: [StatutPanne.SIGNALEEE, Validators.required],
      coutEstime: [0, [Validators.required, Validators.min(0)]]
    });
  }

  loadEquipements(): void {
    this.equipementService.getAll().subscribe({
      next: (response) => {
        this.equipements = response.data || [];
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des équipements', 'Fermer', { duration: 3000 });
      }
    });
  }

  loadPanne(): void {
    if (!this.panneId) return;
    this.isLoading = true;
    this.panneService.getById(this.panneId).subscribe({
      next: (response) => {
        const panne = response.data;
        this.panneForm.patchValue({
          equipementId: panne.equipementId,
          categorie: panne.categorie,
          description: panne.description,
          severite: panne.severite,
          statut: panne.statut,
          coutEstime: panne.coutEstime
        });
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement de la panne', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  submit(): void {
    if (this.panneForm.invalid) return;
    this.isLoading = true;

    const formValue = this.panneForm.value;

    if (this.isEditMode && this.panneId) {
      this.panneService.update(this.panneId, formValue).subscribe({
        next: () => {
          this.snackBar.open('Panne modifiée avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/pannes']);
          this.isLoading = false;
        },
        error: () => {
          this.snackBar.open('Erreur lors de la modification', 'Fermer', { duration: 3000 });
          this.isLoading = false;
        }
      });
    } else {
      this.panneService.create(formValue).subscribe({
        next: () => {
          this.snackBar.open('Panne signalée avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/pannes']);
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
    this.router.navigate(['/pannes']);
  }
}
