import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RideService } from 'src/app/service/ride.service';
import { Ride } from 'src/app/model/ride';
import { HttpErrorResponse } from '@angular/common/http';
import { Location } from 'src/app/model/location';
import { Price } from 'src/app/model/price';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-create-ride',
  templateUrl: './create-ride.component.html',
  styleUrls: ['./create-ride.component.css']
})
export class CreateRideComponent implements OnInit {

  loggedUser: User = new User();

  inputDatesValid: Boolean = true;
  dateTimeInvalidMessage: string = "";

  constructor(private rideService: RideService, private userService: UserService, private router: Router) { 
    this.dateTimeInvalidMessage = "Departure time and Arrival time wrong input!";
    this.userService.findLoggedUser().subscribe(loggedUser => {
      this.loggedUser = loggedUser;
    })
  }

  ngOnInit(): void {
    this.userService.findLoggedUser().subscribe(loggedUser => {
      this.loggedUser = loggedUser;
    }, () => {
      this.router.navigate(['/login']);
    })
  }

  createRide(): void {
    if (this.checkRide()) {
      let { startingPointCountry, startingPointCity, startingPointAddress, destinationCountry, destinationCity, destinationAddress, departureTime, arrivalTime, currency, worth, maxPassengers, rules } = this.getInputElements();

      let ride = this.createNewRide(startingPointCountry, startingPointCity, startingPointAddress, destinationCountry, destinationCity, destinationAddress, departureTime, arrivalTime, currency, worth, maxPassengers, rules);

      this.userService.findLoggedUser().subscribe(loggedUser => {
        this.loggedUser = loggedUser;
        ride.driver = loggedUser;
        this.rideService.createRide(ride).subscribe(ride => {
          this.router.navigate(['/rides-page']);
        }, (error: HttpErrorResponse) => {
          alert(error.error.message);
        })
      }, (error: HttpErrorResponse) => {
        alert(error.error.message);
      })
    }
  }

  private createNewRide(startingPointCountry: string, startingPointCity: string, startingPointAddress: string, destinationCountry: string, destinationCity: string, destinationAddress: string, departureTime: Date, arrivalTime: Date, currency: string, worth: number, maxPassengers: number, rules: string) {
    let ride = new Ride();
    ride.startingPoint = new Location();
    ride.startingPoint.country = startingPointCountry;
    ride.startingPoint.city = startingPointCity;
    ride.startingPoint.address = startingPointAddress;
    ride.destination = new Location();
    ride.destination.country = destinationCountry;
    ride.destination.city = destinationCity;
    ride.destination.address = destinationAddress;
    ride.departureTime = departureTime;
    ride.arrivalTime = arrivalTime;
    ride.price = new Price();
    ride.price.currency = currency;
    ride.price.worth = worth;
    ride.maxPassengers = maxPassengers;
    ride.rules = rules;
    ride.passengers = [];
    ride.requests = [];
    ride.driver = this.loggedUser;
    return ride;
  }

  private getInputElements() {
    let startingPointCountryEl = document.getElementById('startingPointCountry') as HTMLInputElement;
    let startingPointCountry = startingPointCountryEl.value;
    let startingPointCityEl = document.getElementById('startingPointCity') as HTMLInputElement;
    let startingPointCity = startingPointCityEl.value;
    let startingPointAddressEl = document.getElementById('startingPointAddress') as HTMLInputElement;
    let startingPointAddress = startingPointAddressEl.value;
    let destinationCountryEl = document.getElementById('destinationCountry') as HTMLInputElement;
    let destinationCountry = destinationCountryEl.value;
    let destinationCityEl = document.getElementById('destinationCity') as HTMLInputElement;
    let destinationCity = destinationCityEl.value;
    let destinationAddressEl = document.getElementById('destinationAddress') as HTMLInputElement;
    let destinationAddress = destinationAddressEl.value;

    let departureTimeEl = document.getElementById('departureTime') as HTMLInputElement;
    let departureTime = new Date(departureTimeEl.value);
    let arrivalTimeEl = document.getElementById('arrivalTime') as HTMLInputElement;
    let arrivalTime = new Date(arrivalTimeEl.value);

    let currencyEl = document.getElementById('currency') as HTMLInputElement;
    let currency = currencyEl.value;
    let worthEl = document.getElementById('worth') as HTMLInputElement;
    let worth = parseFloat(worthEl.value);

    let maxPassengersEl = document.getElementById('maxPassengers') as HTMLInputElement;
    let maxPassengers = parseInt(maxPassengersEl.value);
    let rulesEl = document.getElementById('rules') as HTMLInputElement;
    let rules = rulesEl.value;
    return { startingPointCountry, startingPointCity, startingPointAddress, destinationCountry, destinationCity, destinationAddress, departureTime, arrivalTime, currency, worth, maxPassengers, rules };
  }

  checkRide(): Boolean {
    return this.checkDates();
  }

  checkDates(): Boolean {
    let departureTimeEl = document.getElementById('departureTime') as HTMLInputElement;
    let departureTime = new Date(departureTimeEl.value);
    let arrivalTimeEl = document.getElementById('arrivalTime') as HTMLInputElement;
    let arrivalTime = new Date(arrivalTimeEl.value);
    if (departureTime.valueOf() <= new Date().valueOf()) {
      this.inputDatesValid = false;
      return false;
    } else if (departureTime.valueOf() >= arrivalTime.valueOf()) {
      this.inputDatesValid = false;
      return false;
    }

    this.inputDatesValid = true;

    return true;
  }

}
