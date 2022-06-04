import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/dto/user-dto';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: UserDTO = new UserDTO();

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.user = user;
    }, () => {
      this.router.navigate(['/login']);
    })
  }

}
