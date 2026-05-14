import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';

// Layout
import { LayoutComponent } from './components/layout/layout.component';
import { HomeComponent } from './components/home/home.component';

// Auth Components
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

// Main Components
import { DashboardComponent } from './components/dashboard/dashboard.component';

// Equipement Components
import { EquipementListComponent } from './components/equipement/equipement-list/equipement-list.component';
import { EquipementFormComponent } from './components/equipement/equipement-form/equipement-form.component';

// Panne Components
import { PanneListComponent } from './components/panne/panne-list/panne-list.component';
import { PanneFormComponent } from './components/panne/panne-form/panne-form.component';

// Technicien Components
import { TechnicienListComponent } from './components/technicien/technicien-list/technicien-list.component';
import { TechnicienFormComponent } from './components/technicien/technicien-form/technicien-form.component';

// Intervention Components
import { InterventionListComponent } from './components/intervention/intervention-list/intervention-list.component';
import { InterventionFormComponent } from './components/intervention/intervention-form/intervention-form.component';

const routes: Routes = [
  {
    path: 'auth',
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: '', redirectTo: 'login', pathMatch: 'full' }
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      {
        path: 'equipements',
        children: [
          { path: '', component: EquipementListComponent },
          { path: 'new', component: EquipementFormComponent },
          { path: ':id/edit', component: EquipementFormComponent }
        ]
      },
      {
        path: 'pannes',
        children: [
          { path: '', component: PanneListComponent },
          { path: 'new', component: PanneFormComponent },
          { path: ':id/edit', component: PanneFormComponent }
        ]
      },
      {
        path: 'techniciens',
        children: [
          { path: '', component: TechnicienListComponent },
          { path: 'new', component: TechnicienFormComponent },
          { path: ':id/edit', component: TechnicienFormComponent }
        ]
      },
      {
        path: 'interventions',
        children: [
          { path: '', component: InterventionListComponent },
          { path: 'new', component: InterventionFormComponent },
          { path: ':id/edit', component: InterventionFormComponent }
        ]
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
