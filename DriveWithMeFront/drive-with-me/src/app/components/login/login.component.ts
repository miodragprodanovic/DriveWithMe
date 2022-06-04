import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserLoginDTO } from 'src/app/dto/user-login-dto';
import { LoginService } from 'src/app/service/login.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserLoginDTO = new UserLoginDTO();
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
