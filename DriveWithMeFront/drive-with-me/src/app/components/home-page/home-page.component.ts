import { Component, OnInit } from '@angular/core';
import { UserDTO } from 'src/app/dto/user-dto';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  users: Array<UserDTO> = new Array<UserDTO>();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(ret => {
      this.users = ret;
    })
  }

}
