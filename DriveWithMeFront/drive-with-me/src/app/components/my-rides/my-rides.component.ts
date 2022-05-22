import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RideRequestDTO } from 'src/app/dto/ride-request-dto';
import { Ride } from 'src/app/model/ride';
import { RideService } from 'src/app/service/ride.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-my-rides',
  templateUrl: './my-rides.component.html',
  styleUrls: ['./my-rides.component.css']
})
export class MyRidesComponent implements OnInit {

  ridesAsDriver: Array<Ride> = new Array<Ride>();
  ridesAsPassenger: Array<Ride> = new Array<Ride>();
  rideRequests: Array<Ride> = new Array<Ride>();

  selectedRide: Ride = new Ride();
  rideIsSelected: Boolean = false;

  constructor(private rideService: RideService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.findLoggedUser().subscribe(user => {
      this.rideService.getRidesByUserAsDriver(user.id).subscribe(rides => {
        this.ridesAsDriver = rides;
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
      this.rideService.getRidesByUserAsPassenger(user.id).subscribe(rides => {
        this.ridesAsPassenger = rides;
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
      this.rideService.getRideRequestsByUserAsPassenger(user.id).subscribe(rides => {
        this.rideRequests = rides;
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
    }, (error: HttpErrorResponse) => {
      this.router.navigate(['/login']);
      alert(error.error.message);
    })
  }

  seeRideDetails(rideId: number) {
    this.rideService.getRideById(rideId).subscribe(ride => {
      this.selectedRide = ride;
      this.changeRideIsSelected(true);
    }, (error: HttpErrorResponse) => {
      this.router.navigate(['/login']);
      alert(error.error.message);
    })
  }

  deleteRide(rideId: number) {
    this.rideService.deleteRide(rideId).subscribe(value => {
      if (value == true) {
        window.location.reload();
        alert("Ride successfully deleted!");
      } else {
        alert("Error deleting ride!");
      }
    }, (error: HttpErrorResponse) => {
      alert(error.error.message);
    })
  }

  cancelRide(rideId: number) {
    this.userService.findLoggedUser().subscribe(user => {
      let rideRequestDTO = new RideRequestDTO();
      rideRequestDTO.rideId = rideId;
      rideRequestDTO.userEmail = user.email;
      this.rideService.cancelRide(rideRequestDTO).subscribe(value => {
        if (value == true) {
          window.location.reload();
          alert("Ride successfully canceled!");
        } else {
          alert("Error canceling ride!");
        }
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
    })
  }

  cancelRideRequest(rideId: number) {
    this.userService.findLoggedUser().subscribe(user => {
      let rideRequestDTO = new RideRequestDTO();
      rideRequestDTO.rideId = rideId;
      rideRequestDTO.userEmail = user.email;
      this.rideService.cancelRideRequest(rideRequestDTO).subscribe(value => {
        if (value == true) {
          window.location.reload();
          alert("Ride request successfully canceled!");
        } else {
          alert("Error canceling ride request!");
        }
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
    })
  }

  acceptRideRequest(rideId: number, userEmail: string) {
    let rideRequestDTO = new RideRequestDTO();
    rideRequestDTO.rideId = rideId;
    rideRequestDTO.userEmail = userEmail;
    this.rideService.acceptRideRequest(rideRequestDTO).subscribe(value => {
      if (value == true) {
        window.location.reload();
        alert("Ride request successfully accepted!");
      } else {
        alert("Error accepting ride request!");
      }
    }, (error: HttpErrorResponse) => {
      alert(error.error.message);
    })
  }

  declineRideRequest(rideId: number, userEmail: string) {
    let rideRequestDTO = new RideRequestDTO();
    rideRequestDTO.rideId = rideId;
    rideRequestDTO.userEmail = userEmail;
    this.rideService.declineRideRequest(rideRequestDTO).subscribe(value => {
      if (value == true) {
        window.location.reload();
        alert("Ride request successfully declined!");
      } else {
        alert("Error declining ride request!");
      }
    }, (error: HttpErrorResponse) => {
      alert(error.error.message);
    })
  }

  changeRideIsSelected(value: boolean) {
    this.rideIsSelected = value;

    let firstDiv = document.getElementById("rideDetails") as HTMLDivElement;
  }

  viewUserProfile(email: string) {
    alert("User " + email);
  }

}
