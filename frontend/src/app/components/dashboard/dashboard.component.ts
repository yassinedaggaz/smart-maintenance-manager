import { Component, OnInit, Inject } from '@angular/core';
import { DashboardService } from '@services/dashboard.service';
import { DashboardStats } from '../../models';
import { ChartConfiguration } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats!: DashboardStats;
  isLoading = true;

  chartPannesConfig!: ChartConfiguration<'pie'>;
  chartInterventionsConfig!: ChartConfiguration<'bar'>;

  constructor(@Inject(DashboardService) private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.isLoading = true;
    this.dashboardService.getStats().subscribe({
      next: (response) => {
        this.stats = response.data;
        this.initCharts();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des statistiques', error);
        this.isLoading = false;
      }
    });
  }

  initCharts(): void {
    // Chart Pannes par catégorie
    this.chartPannesConfig = {
      type: 'pie',
      data: {
        labels: ['Signalées', 'En cours', 'Résolues'],
        datasets: [{
          data: [
            this.stats.totalPannes - (this.stats.totalPannes * 0.4),
            Math.round(this.stats.totalPannes * 0.4),
            Math.round(this.stats.totalPannes * 0.3)
          ],
          backgroundColor: ['#ff6384', '#36a2eb', '#ffce56'],
          borderColor: ['#ff6384', '#36a2eb', '#ffce56']
        }]
      }
    };

    // Chart Interventions par statut
    this.chartInterventionsConfig = {
      type: 'bar',
      data: {
        labels: ['Planifiées', 'En cours', 'Terminées'],
        datasets: [{
          label: 'Interventions',
          data: [
            this.stats.interventionsPlanifiees,
            this.stats.interventionsEnCours,
            this.stats.interventionsTerminees
          ],
          backgroundColor: ['#667eea', '#764ba2', '#f093fb']
        }]
      }
    };
  }
}
