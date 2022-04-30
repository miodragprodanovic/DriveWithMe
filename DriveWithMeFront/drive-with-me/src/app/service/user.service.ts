import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  urlGetAllUsers: string;
  urlGetUserByEmail: string;
  urlCanLogin: string;
  urlLogin: string;
  urlLogout: string;
  urlRegister: string;
  urlFindLoggedUser: string;

  constructor(private http: HttpClient) { 
    this.urlGetAllUsers = 'http://localhost:8080/api/users/allUsers',
    this.urlGetUserByEmail = 'http://localhost:8080/api/users/findUserByEmail',
    this.urlRegister = 'http://localhost:8080/api/users/register',
    this.urlCanLogin = 'http://localhost:8080/api/users/canLogin',
    this.urlLogin = 'http://localhost:8080/api/users/login',
    this.urlLogout = 'http://localhost:8080/api/users/logout',
    this.urlFindLoggedUser = 'http://localhost:8080/api/users/findLoggedUser'
  }

  public getAllUsers(): Observable<Array<User>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<Array<User>>(this.urlGetAllUsers, { headers: headers } );
  }

  public getUserByEmail(email: string): Observable<User> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("email", email);
    return this.http.get<User>(this.urlGetUserByEmail, { headers: headers, params: params, withCredentials: true } );
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(this.urlRegister, user, { withCredentials: true } );
  }

  public canLogin(user: User): Observable<boolean> {
    return this.http.post<boolean>(this.urlCanLogin, user, { withCredentials: true } );
  }

  public login(user: User): Observable<boolean> {
    return this.http.post<boolean>(this.urlLogin, user, { withCredentials: true } );
  }

  public logout(user: User): Observable<boolean> {
    return this.http.post<boolean>(this.urlLogout, user, { withCredentials: true } );
  }

  public findLoggedUser(): Observable<User> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<User>(this.urlFindLoggedUser, { headers: headers, withCredentials: true } );
  }

}
