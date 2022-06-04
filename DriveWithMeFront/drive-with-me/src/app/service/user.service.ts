import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { UserDTO } from '../dto/user-dto';
import { UserRegisterDTO } from '../dto/user-register-dto';
import { UserLoginDTO } from '../dto/user-login-dto';
import { ChangePasswordDTO } from '../dto/change-password-dto';

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
  urlChangeInfo: string;
  urlChangePassword: string

  constructor(private http: HttpClient) { 
    this.urlGetAllUsers = 'http://localhost:8080/api/users/allUsers',
    this.urlGetUserByEmail = 'http://localhost:8080/api/users/userByEmail',
    this.urlRegister = 'http://localhost:8080/api/users/register',
    this.urlCanLogin = 'http://localhost:8080/api/users/canLogin',
    this.urlLogin = 'http://localhost:8080/api/users/login',
    this.urlLogout = 'http://localhost:8080/api/users/logout',
    this.urlFindLoggedUser = 'http://localhost:8080/api/users/loggedUser',
    this.urlChangeInfo = 'http://localhost:8080/api/users/changeInfo',
    this.urlChangePassword = 'http://localhost:8080/api/users/changePassword'
  }

  public getAllUsers(): Observable<Array<UserDTO>> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<Array<UserDTO>>(this.urlGetAllUsers, { headers: headers } );
  }

  public getUserByEmail(email: string): Observable<User> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("email", email);

    return this.http.get<User>(this.urlGetUserByEmail, { headers: headers, params: params, withCredentials: true } );
  }

  public register(userRegisterDTO: UserRegisterDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(this.urlRegister, userRegisterDTO, { withCredentials: true } );
  }

  public canLogin(userLoginDTO: UserLoginDTO): Observable<boolean> {
    return this.http.post<boolean>(this.urlCanLogin, userLoginDTO, { withCredentials: true } );
  }

  public login(userLoginDTO: UserLoginDTO): Observable<boolean> {
    return this.http.post<boolean>(this.urlLogin, userLoginDTO, { withCredentials: true } );
  }

  public logout(userDTO: UserDTO): Observable<boolean> {
    return this.http.post<boolean>(this.urlLogout, userDTO, { withCredentials: true } );
  }

  public findLoggedUser(): Observable<UserDTO> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.get<UserDTO>(this.urlFindLoggedUser, { headers: headers, withCredentials: true } );
  }

  public changeInfo(userDTO: UserDTO): Observable<UserDTO> {
    return this.http.put<UserDTO>(this.urlChangeInfo, userDTO, { withCredentials: true } );
  }

  public changePassword(changePasswordDTO: ChangePasswordDTO): Observable<UserDTO> {
    return this.http.put<UserDTO>(this.urlChangePassword, changePasswordDTO, { withCredentials: true } );
  }

}
