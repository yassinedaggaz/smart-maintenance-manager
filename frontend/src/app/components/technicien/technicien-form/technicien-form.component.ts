import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TechnicienService } from '@services/technicien.service';
import { DisponibiliteStatut } from '../../../models';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-technicien-form',
  templateUrl: './technicien-form.component.html',
  styleUrls: ['./technicien-form.component.scss'],
})

export class TechnicienFormComponent implements OnInit {
  technicienForm!: FormGroup;
  isLoading = false;
  isEditMode = false;
  technicienId: number | null = null;
  disponibiliteValues = Object.values(DisponibiliteStatut);
  competencesList: string[] = ['Électrique', 'Mécanique', 'Hydraulique', 'Pneumatique', 'Automatisme', 'CNC', 'Robottique'];
  selectedCompetences: string[] = [];
  dateEmbauche: Date | null = null;

  constructor(
    private fb: FormBuilder,
    @Inject(TechnicienService) private technicienService: TechnicienService,
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
        this.technicienId = +id;
        this.loadTechnicien();
      }
    });
  }

  initForm(): void {
    this.technicienForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.pattern(/^[\d\s\-\+\(\)]+$/)]],
      competences: [''],
      disponibilite: [DisponibiliteStatut.DISPONIBLE, Validators.required],
      dateEmbauche: ['', Validators.required]
    });
  }

  loadTechnicien(): void {
    if (!this.technicienId) return;
    this.isLoading = true;
    this.technicienService.getById(this.technicienId).subscribe({
      next: (response) => {
        const technicien = response.data;
        const competences = technicien.competences || '';
        this.selectedCompetences = Array.isArray(competences) ? competences : competences.length > 0 ?  competences.split(',') : [];
        this.technicienForm.patchValue({
          nom: technicien.nom,
          email: technicien.email,
          telephone: technicien.telephone,
          competences: this.selectedCompetences,
          disponibilite: technicien.disponibilite,
          dateEmbauche: technicien.dateEmbauche ? new Date(technicien.dateEmbauche) : null
        });
        this.isLoading = false;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement du technicien', 'Fermer', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  toggleCompetence(competence: string): void {
    const index = this.selectedCompetences.indexOf(competence);
    if (index >= 0) {
      this.selectedCompetences.splice(index, 1);
    } else {
      this.selectedCompetences.push(competence);
    }
    console.error(this.selectedCompetences);
    this.technicienForm.patchValue({ competences: this.selectedCompetences });
  }

  isCompetenceSelected(competence: string): boolean {
    return this.selectedCompetences.includes(competence);
  }

  submit(): void {
    if (this.technicienForm.invalid || this.selectedCompetences.length === 0) {
      this.snackBar.open('Sélectionnez au moins une compétence', 'Fermer', { duration: 3000 });
      return;
    }
    this.isLoading = true;

    const formValue = {
      ...this.technicienForm.value,
      competences: this.selectedCompetences.join(',')
    };

    if (this.isEditMode && this.technicienId) {
      this.technicienService.update(this.technicienId, formValue).subscribe({
        next: () => {
          this.snackBar.open('Technicien modifié avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/techniciens']);
          this.isLoading = false;
        },
        error: () => {
          this.snackBar.open('Erreur lors de la modification', 'Fermer', { duration: 3000 });
          this.isLoading = false;
        }
      });
    } else {
      this.technicienService.create(formValue).subscribe({
        next: () => {
          this.snackBar.open('Technicien créé avec succès', 'Fermer', { duration: 3000 });
          this.router.navigate(['/techniciens']);
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
    this.router.navigate(['/techniciens']);
  }
}
