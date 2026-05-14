import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Interceptors
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';

// Layout Components
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
import { MatIcon } from "@angular/material/icon";
import { MatCard } from "@angular/material/card";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatSelect, MatOption } from "@angular/material/select";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { MatChipSet, MatChip } from "@angular/material/chips";
import { MatPaginator } from "@angular/material/paginator";

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MaterialModule } from './material/material.module';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    EquipementListComponent,
    EquipementFormComponent,
    PanneListComponent,
    PanneFormComponent,
    TechnicienListComponent,
    TechnicienFormComponent,
    InterventionListComponent,
    InterventionFormComponent,
    LayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgChartsModule,
    MatIcon,
    MatCard,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    MatProgressSpinner,
    MatChipSet,
    MatChip,
    MatPaginator,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MaterialModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
