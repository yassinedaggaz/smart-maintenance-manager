export interface Equipement {
  id: number;
  nom: string;
  etat: EtatEquipement;
  dateAcquisition: string;
  description?: string;
  createdAt: string;
  updatedAt: string;
  pannes?: Panne[];
  interventions?: Intervention[];
  marque: string;
  modele: string;
  type: string;
}

export enum EtatEquipement {
  EN_SERVICE = 'EN_SERVICE',
  EN_PANNE = 'EN_PANNE',
  EN_MAINTENANCE = 'EN_MAINTENANCE'
}

export interface Panne {
  id: number;
  description: string;
  categorie: CategoriePanne;
  dateSignalement: string;
  statut: StatutPanne;
  remarques?: string;
  equipementId: number;
  equipementNom?: string;
  createdAt: string;
  updatedAt: string;
  coutEstime?: number;
  severite: string;
}

export enum CategoriePanne {
  ELECTRIQUE = 'ELECTRIQUE',
  MECANIQUE = 'MECANIQUE',
  HYDRAULIQUE = 'HYDRAULIQUE',
  ELECTRONIQUE = 'ELECTRONIQUE',
  LOGICIEL = 'LOGICIEL',
  AUTRE = 'AUTRE'
}

export enum StatutPanne {
  SIGNALEEE = 'SIGNALEEE',
  EN_COURS = 'EN_COURS',
  RESOLUE = 'RESOLUE'
}

export interface Technicien {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  competences?: string;
  disponibilite: DisponibiliteStatut;
  createdAt: string;
  updatedAt: string;
  interventions?: Intervention[];
  dateEmbauche: Date | null;
}

export enum DisponibiliteStatut {
  DISPONIBLE = 'DISPONIBLE',
  OCCUPE = 'OCCUPE',
  EN_CONGE = 'EN_CONGE'
}

export interface Intervention {
  id: number;
  dateDebut: string;
  dateFin?: string;
  statut: StatutIntervention;
  cout?: number;
  description?: string;
  remarques?: string;
  equipementId: number;
  equipementNom?: string;
  technicienId: number;
  technicienNom?: string;
  createdAt: string;
  updatedAt: string;
}

export enum StatutIntervention {
  PLANIFIEE = 'PLANIFIEE',
  EN_COURS = 'EN_COURS',
  TERMINEEE = 'TERMINEEE',
  ANNULEE = 'ANNULEE'
}

export interface User {
  id: number;
  username: string;
  email: string;
  role: UserRole;
}

export enum UserRole {
  ADMIN = 'ADMIN',
  TECHNICIEN = 'TECHNICIEN'
}

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthRegisterRequest {
  username: string;
  email: string;
  password: string;
  role?: UserRole;
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  userId: number;
  username: string;
  email: string;
  role: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

export interface DashboardStats {
  totalEquipements: number;
  totalPannes: number;
  totalTechniciens: number;
  totalInterventions: number;
  interventionsEnCours: number;
  interventionsPlanifiees: number;
  interventionsTerminees: number;
  coutTotalMaintenance: number;
  coutMoyenParIntervention: number;
  equipementsLePlusEnPanne: EquipementPanne[];
  technicienChargeWork: TechnicienCharge[];
}

export interface EquipementPanne {
  equipementId: number;
  equipementNom: string;
  nombrePannes: number;
}

export interface TechnicienCharge {
  technicienId: number;
  technicienNom: string;
  nombreInterventions: number;
}
