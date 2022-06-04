import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ChangePasswordDTO } from 'src/app/dto/change-password-dto';
import { ChangePasswordFormDTO } from 'src/app/dto/change-password-form-dto';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  user: ChangePasswordFormDTO = new ChangePasswordFormDTO();
  loggedUser: User = new User();

  credentialsMessage: string = '';
  passwordMessage: string = '';

  credentialsMatch: boolean = true;
  passwordsMatch: boolean = true;

  constructor(private userService: UserService) { 
    this.credentialsMessage = 'Wrong credentials!';
    this.passwordMessage = 'Second password must match the first one!';
  }

  ngOnInit(): void {
    this.userService.findLoggedUser().subscribe(userDTO => {
      this.userService.getUserByEmail(userDTO.email).subscribe(user => {
        this.loggedUser = user;
      })
    })
  }

  checkPassword(): void {
    if (this.user.newPassword == this.user.repeatedNewPassword) {
      this.passwordsMatch = true;
    } else {
      this.passwordsMatch = false;
    }
  }

  checkUser(): void {
    if (this.user.email == this.loggedUser.email && this.user.oldPassword == this.loggedUser.password) {
      this.credentialsMatch = true;
    } else {
      this.credentialsMatch = false;
    }
  }

  checkDTO(): boolean {
    return this.credentialsMatch && this.passwordsMatch;
  }

  changePassword() {
    this.checkUser();
    this.checkPassword();
    if (this.checkDTO()) {
      let changePasswordDTO = new ChangePasswordDTO();
      changePasswordDTO.email = this.user.email;
      changePasswordDTO.oldPassword = this.user.oldPassword;
      changePasswordDTO.newPassword = this.user.newPassword;
      this.userService.changePassword(changePasswordDTO).subscribe(userDTO => {
        alert('Password successfully changed!');
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
    }

  }

}
