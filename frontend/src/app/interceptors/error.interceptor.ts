import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.logout();
          this.router.navigate(['/auth/login']);
          this.snackBar.open('Vous devez vous authentifier', 'Fermer', { duration: 3000 });
        }

        if (error.status === 403) {
          this.snackBar.open('Accès refusé', 'Fermer', { duration: 3000 });
        }

        if (error.status >= 500) {
          this.snackBar.open('Erreur serveur. Veuillez réessayer', 'Fermer', { duration: 3000 });
        }

        return throwError(() => error);
      })
    );
  }
}
