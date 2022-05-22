import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReplaySubject } from 'rxjs';
import { LoginService } from 'src/app/service/login.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  loginStatus: ReplaySubject<boolean> = new ReplaySubject<boolean>();
  
  constructor(private loginService: LoginService, private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loginService.loginStatus.subscribe(state => {
      this.loginStatus.next(state);
    })
  }

  logout(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.userService.logout(user).subscribe(()=>{
        this.loginService.updateLoggedIn(false);
        alert("LOGGED OFF");
      }, (error: HttpErrorResponse) => {
        alert("error logging off: " + error.status)
      })
    })
  }

  userProfile(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.router.navigate(['/user-profile']);
    }, () => {
      this.router.navigate(['/login']);
      alert('You are not logged in!');
    })
  }

  myRides(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.router.navigate(['/my-rides']);
    }, () => {
      this.router.navigate(['/login']);
      alert('You are not logged in!');
    })
  }

  createRide(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.router.navigate(['/create-ride']);
    }, () => {
      this.router.navigate(['/login']);
      alert('You are not logged in!');
    })
  }

}
