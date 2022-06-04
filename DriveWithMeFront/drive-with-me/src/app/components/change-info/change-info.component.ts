import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/dto/user-dto';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-change-info',
  templateUrl: './change-info.component.html',
  styleUrls: ['./change-info.component.css']
})
export class ChangeInfoComponent implements OnInit {

  user: UserDTO = new UserDTO();

  namePattern: string = '';
  nameMessage: string = '';
  
  constructor(private userService: UserService, private router: Router) { 
    this.namePattern = '([A-ZŠĐČĆŽ][a-zšđčćž]+)([ \-][A-ZŠĐČĆŽ][a-zšđčćž]+)*'
    this.nameMessage = 'Only letters are allowed. More than one name is allowed. First letter of every name must be capital and every name must have more than one letter. Names should be separated by \'-\' or \'white space\'.';
  }

  ngOnInit(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.user = user;
    }, () => {
      this.router.navigate(['/login']);
    })
  }

  changeInfo() {
    let user = this.user;
    this.userService.changeInfo(user).subscribe(user => {
      this.user = user;
      alert('Informations successfully changed!');
    }, (error: HttpErrorResponse) => {
      alert(error.error.message);
    })
  }

}
