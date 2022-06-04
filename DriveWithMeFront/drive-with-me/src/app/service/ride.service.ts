import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { RideDTO } from '../dto/ride-dto';
import { Observable } from 'rxjs';
import { RideRequestDTO } from '../dto/ride-request-dto';

@Injectable({
  providedIn: 'root'
})
export class RideService {

  urlGetAllRides: string;
  urlGetRideById: string;
  urlRidesByUserAsDriver: string;
  urlRidesByUserAsPassenger: string;
  urlRideRequestsByUserAsPassenger: string;
  urlTodayRides: string;
  urlRidesInPriceRange: string;
  urlRidesFromStartingPointToDestination: string;
  urlRidesFromStartingPointToDestinationInDateRange: string;
  urlRideHasFreeSeats: string;
  urlCreateRide: string;
  urlDeleteRide: string;
  urlCreateRideRequest: string;
  urlCancelRideRequest: string;
  urlCancelRide: string;
  urlAcceptRideRequest: string;
  urlDeclineRideRequest: string;

  constructor(private http: HttpClient) { 
    this.urlGetAllRides = 'http://localhost:8080/api/rides/allRides',
    this.urlGetRideById = 'http://localhost:8080/api/rides/rideById/',
    this.urlRidesByUserAsDriver = 'http://localhost:8080/api/rides/ridesByUserAsDriver',
    this.urlRidesByUserAsPassenger = 'http://localhost:8080/api/rides/ridesByUserAsPassenger',
    this.urlRideRequestsByUserAsPassenger = 'http://localhost:8080/api/rides/rideRequestsByUserAsPassenger',
    this.urlTodayRides = 'http://localhost:8080/api/rides/todayRides',
    this.urlRidesInPriceRange = 'http://localhost:8080/api/rides/ridesInPriceRange',
    this.urlRidesFromStartingPointToDestination = 'http://localhost:8080/api/rides/ridesFromStartingPointToDestination',
    this.urlRidesFromStartingPointToDestinationInDateRange = 'http://localhost:8080/api/rides/ridesFromStartingPointToDestinationInDateRange',
    this.urlRideHasFreeSeats = 'http://localhost:8080/api/rides/rideHasFreeSeats/',
    this.urlCreateRide = 'http://localhost:8080/api/rides/createRide',
    this.urlDeleteRide = 'http://localhost:8080/api/rides/deleteRide/',
    this.urlCreateRideRequest = 'http://localhost:8080/api/rides/createRideRequest',
    this.urlCancelRideRequest = 'http://localhost:8080/api/rides/cancelRideRequest',
    this.urlCancelRide = 'http://localhost:8080/api/rides/cancelRide',
    this.urlAcceptRideRequest = 'http://localhost:8080/api/rides/acceptRideRequest',
    this.urlDeclineRideRequest = 'http://localhost:8080/api/rides/declineRideRequest'
  }

  public getAllRides(): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<Array<RideDTO>>(this.urlGetAllRides, { headers: headers } );
  }

  public getRideById(rideId: number): Observable<RideDTO> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<RideDTO>(this.urlGetRideById + rideId.toString(), { headers: headers } );
  }

  public getRidesByUserAsDriver(id: number): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("id", id);

    return this.http.get<Array<RideDTO>>(this.urlRidesByUserAsDriver, { headers: headers, params: params, withCredentials: true } );
  }

  public getRidesByUserAsPassenger(id: number): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("id", id);

    return this.http.get<Array<RideDTO>>(this.urlRidesByUserAsPassenger, { headers: headers, params: params, withCredentials: true } );
  }

  public getRideRequestsByUserAsPassenger(id: number): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("id", id);

    return this.http.get<Array<RideDTO>>(this.urlRideRequestsByUserAsPassenger, { headers: headers, params: params, withCredentials: true } );
  }

  public getTodayRides(): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<Array<RideDTO>>(this.urlTodayRides, { headers: headers, withCredentials: true } );
  }

  public getRidesInPriceRange(currency: string, from: number, to: number): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("currency", currency).set("from", from).set("to", to);

    return this.http.get<Array<RideDTO>>(this.urlRidesInPriceRange, { headers: headers, params: params, withCredentials: true } );
  }

  public getRidesFromStartingPointToDestination(startingPointCity: string, destinationCity: string): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("startingPointCity", startingPointCity).set("destinationCity", destinationCity);

    return this.http.get<Array<RideDTO>>(this.urlRidesFromStartingPointToDestination, { headers: headers, params: params, withCredentials: true } );
  }

  public getRidesFromStartingPointToDestinationInDateRange(startingPointCity: string, destinationCity: string, startTime: string, endTime: string): Observable<Array<RideDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("startingPointCity", startingPointCity).set("destinationCity", destinationCity).set("startTime", startTime).set("endTime", endTime);

    return this.http.get<Array<RideDTO>>(this.urlRidesFromStartingPointToDestinationInDateRange, { headers: headers, params: params, withCredentials: true } );
  }

  public getRideHasFreeSeats(rideId: number): Observable<Boolean> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<Boolean>(this.urlRideHasFreeSeats + rideId.toString(), { headers: headers, withCredentials: true } );
  }

  public createRide(ride: RideDTO): Observable<RideDTO> {
    return this.http.post<RideDTO>(this.urlCreateRide, ride, { withCredentials: true });
  }

  public deleteRide(rideId: number): Observable<Boolean> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.delete<Boolean>(this.urlDeleteRide + rideId.toString(), { headers: headers, withCredentials: true });
  }

  public createRideRequest(rideRequestDTO: RideRequestDTO): Observable<RideDTO> {
    return this.http.put<RideDTO>(this.urlCreateRideRequest, rideRequestDTO, { withCredentials: true });
  }

  public cancelRideRequest(rideRequestDTO: RideRequestDTO): Observable<Boolean> {
    return this.http.put<Boolean>(this.urlCancelRideRequest, rideRequestDTO, { withCredentials: true });
  }

  public cancelRide(rideRequestDTO: RideRequestDTO): Observable<Boolean> {
    return this.http.put<Boolean>(this.urlCancelRide, rideRequestDTO, { withCredentials: true });
  }

  public acceptRideRequest(rideRequestDTO: RideRequestDTO): Observable<Boolean> {
    return this.http.put<Boolean>(this.urlAcceptRideRequest, rideRequestDTO, { withCredentials: true });
  }

  public declineRideRequest(rideRequestDTO: RideRequestDTO): Observable<Boolean> {
    return this.http.put<Boolean>(this.urlDeclineRideRequest, rideRequestDTO, { withCredentials: true });
  }
}
