import { Injectable } from '@angular/core';
import { BehaviorSubject, ReplaySubject } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginStatus: ReplaySubject<boolean> = new ReplaySubject<boolean>();

  constructor(private userService: UserService) {
    this.checkLoginStatus();
  }

  public getLoginStatus() {
    return this.loginStatus.asObservable();
  }

  public checkLoginStatus(): void {
    this.userService.findLoggedUser().subscribe(() => {
      this.loginStatus.next(true);
    }, () => {
      this.loginStatus.next(false);
    })
  }

  public updateLoggedIn(loginStatus: boolean) {
    this.loginStatus.next(loginStatus);
  }
}
