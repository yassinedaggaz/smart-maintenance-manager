import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EquipementService } from '@services/equipement.service';
import { Equipement, EtatEquipement } from '../../../models';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-equipement-form',
  templateUrl: './equipement-form.component.html',
  styleUrls: ['./equipement-form.component.scss']
})
export class EquipementFormComponent implements OnInit {
  equipementForm!: FormGroup;
  isLoading = false;
  isEditMode = false;
  equipementId: number | null = null;
  etatValues = Object.values(EtatEquipement);

  constructor(
    private fb: FormBuilder,
    @Inject(EquipementService) private equipementService: EquipementService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.initForm();
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.equipementId = +id;
        this.loadEquipement();
      }
    });
  }

  initForm(): void {
    this.equipementForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(3)]],
      type: ['', [Validators.required, Validators.minLength(2)]],
      marque: ['', Validators.required],
      modele: ['', Validators.required],
      etat: [EtatEquipement.EN_SERVICE, Validators.required],
      dateAcquisition: ['', Validators.required],
      description: ['']
    });
  }

  loadEquipement(): void {
    if (!this.equipementId) return;
    this.isLoading = true;
    this.equipementService.getById(this.equipementId).subscribe({
      next: (response) => {
        const equipement = response.data;
        this.equipementForm.patchValue({
          nom: equipement.nom,
          type: equipement.type,
          marque: equipement.marque,
          modele: equipement.modele,
          etat: equipement.etat,
          dateAcquisition: new Date(equipement.dateAcquisition),
          description: equipement.description
        });
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement de l\'équipement', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  submit(): void {
    if (this.equipementForm.invalid) return;
    this.isLoading = true;

    const formValue = this.equipementForm.value;

    if (this.isEditMode && this.equipementId) {
      this.equipementService.update(this.equipementId, formValue).subscribe({
        next: () => {
          this.snackBar.open('Équipement modifié avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/equipements']);
          this.isLoading = false;
        },
        error: () => {
          this.snackBar.open('Erreur lors de la modification', 'Fermer', { duration: 3000 });
          this.isLoading = false;
        }
      });
    } else {
      this.equipementService.create(formValue).subscribe({
        next: () => {
          this.snackBar.open('Équipement créé avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/equipements']);
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
    this.router.navigate(['/equipements']);
  }
}
