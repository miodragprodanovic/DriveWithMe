import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RideDTO } from 'src/app/dto/ride-dto';
import { RideRequestDTO } from 'src/app/dto/ride-request-dto';
import { UserDTO } from 'src/app/dto/user-dto';
import { RideService } from 'src/app/service/ride.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-rides-page',
  templateUrl: './rides-page.component.html',
  styleUrls: ['./rides-page.component.css']
})
export class RidesPageComponent implements OnInit {

  loggedUser: UserDTO = new UserDTO();
  loggedIn: Boolean = false;

  rides: Array<RideDTO> = new Array<RideDTO>();
  rideIsSelected: boolean = false;
  selectedRide: RideDTO = new RideDTO();
  free: string = "SOMETHING";

  constructor(private rideService: RideService, private userService: UserService, private router: Router) {
    this.userService.findLoggedUser().subscribe(loggedUser => {
      this.loggedUser = loggedUser;
      this.loggedIn = true;
    }, () => {
      this.loggedIn = false;
    })
   }

  ngOnInit(): void {
    this.rideService.getAllRides().subscribe(rides => {
      this.rides = rides;
    })
    this.userService.findLoggedUser().subscribe(loggedUser => {
      this.loggedUser = loggedUser;
      this.loggedIn = true;
    }, () => {
      this.loggedIn = false;
    })
  }

  seeRideDetails(id: number) {
    this.rideService.getRideById(id).subscribe(ride => {
      this.selectedRide = ride;
      this.changeRideIsSelected(true);
    }, (error: HttpErrorResponse) => {
      this.router.navigate(['/login']);
      alert(error.error.message);
    })
    this.rideService.getRideHasFreeSeats(id).subscribe(ret => {
      if (ret == true) {
        this.free = "TRUE";
      } else {
        this.free = "FALSE";
      }
    })
  }

  changeRideIsSelected(value: boolean) {
    this.rideIsSelected = value;

    let firstDiv = document.getElementById("first") as HTMLDivElement;
    let secondDiv = document.getElementById("second") as HTMLDivElement;
    if (value === true) {
      firstDiv.style.width = "70%";
      secondDiv.style.width = "30%";
      
    } else {
      firstDiv.style.width =  "100%";
      secondDiv.style.width = "0%";
    }
  }

  createRideRequest(rideId: number) {
    let rideRequestDTO = new RideRequestDTO();
    rideRequestDTO.rideId = rideId;
    rideRequestDTO.userEmail = this.loggedUser.email;
    this.userService.findLoggedUser().subscribe(loggedUser => {
      this.loggedUser = loggedUser;
      rideRequestDTO.userEmail = loggedUser.email;
      this.rideService.createRideRequest(rideRequestDTO).subscribe(ride => {
        alert("You created a request for this ride.");
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
    }, (error: HttpErrorResponse) => {
      this.loggedIn = false;
      alert(error.error.message);
    })
    
  }

  viewUserProfile(email: string) {
    alert("User " + email);
  }


}
