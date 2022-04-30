import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { LoginService } from 'src/app/service/login.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  wrongCredentials: boolean = false;

  constructor(private userService: UserService, private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {}

  login(): void {
    this.userService.login(this.user).subscribe(successfullLogin => {
      if (successfullLogin) {
        this.wrongCredentials = false
        this.router.navigate(['/'])
        this.loginService.updateLoggedIn(true)
      } else {
        alert("Login error!")
      }
    }, () => {
      this.wrongCredentials = true
    })
  }
}
