import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserRegisterDTO } from 'src/app/dto/user-register-dto';
import { UserRole } from 'src/app/model/user-role';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: UserRegisterDTO = new UserRegisterDTO();

  namePattern: string = '';
  nameMessage: string = '';
  emailMessage: string = '';
  passwordMessage: string = '';
  registrationMessage: string = '';

  passwordsMatch: boolean = true;
  emailExists: boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.user.userRole = UserRole.Client;
    this.namePattern = '([A-ZŠĐČĆŽ][a-zšđčćž]+)([ \-][A-ZŠĐČĆŽ][a-zšđčćž]+)*'
    this.nameMessage = 'Only letters are allowed. More than one name is allowed. First letter of every name must be capital and every name must have more than one letter. Names should be separated by \'-\' or \'white space\'.';
    this.emailMessage = 'This email already exists!';
    this.passwordMessage = 'Second password must match the first one!';
    this.registrationMessage = 'Successfully registrated. Go to your email and verify the account.';
  }

  ngOnInit(): void {
  }

  register(): void {
    if (this.validateEmail()) {
      if (this.validatePassword()) {
        this.createUser();
        this.userService.register(this.user).subscribe(user => {
          alert(this.registrationMessage);
          this.router.navigate(['/login']);
        }, () => {
          alert('Registration error! ' + this.emailMessage);
        })
      } else {
        alert(this.passwordMessage);
      }
    } else {
      alert(this.emailMessage);
    }
  }

  createUser(): void {
    let { firstName, lastName, email, password } = this.getElements();

    this.user.firstName = firstName
    this.user.lastName = lastName
    this.user.email = email
    this.user.password = password
  }

  private getElements() {
    let firstNameEl = document.getElementById('firstName') as HTMLInputElement;
    let firstName = firstNameEl.value;
    let lastNameEl = document.getElementById('lastName') as HTMLInputElement;
    let lastName = lastNameEl.value;
    let emailEl = document.getElementById('email') as HTMLInputElement;
    let email = emailEl.value;
    let passwordEl = document.getElementById('password1') as HTMLInputElement;
    let password = passwordEl.value;
    return { firstName, lastName, email, password };
  }

  checkEmail(): void {
    let emailEl = document.getElementById('email') as HTMLInputElement;
    let email = emailEl.value;

    this.userService.getUserByEmail(email).subscribe(user => {
      this.emailExists = user.confirmed;
    }, () => {
      this.emailExists = false;
    })
  }

  checkPassword(): void {
    let passwordEl1 = document.getElementById('password1') as HTMLInputElement;
    let password1 = passwordEl1.value;
    let passwordEl2 = document.getElementById('password2') as HTMLInputElement;
    let password2 = passwordEl2.value;

    if (password1 === password2) {
      this.passwordsMatch = true;
    } else {
      this.passwordsMatch = false;
    }
  }

  validateEmail(): boolean {
    return !this.emailExists;
  }

  validatePassword(): boolean {
    return this.passwordsMatch;
  }
}
