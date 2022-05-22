import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateRideComponent } from './components/create-ride/create-ride.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { LoginComponent } from './components/login/login.component';
import { MyRidesComponent } from './components/my-rides/my-rides.component';
import { RegisterComponent } from './components/register/register.component';
import { RidesPageComponent } from './components/rides-page/rides-page.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'home-page', component: HomePageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'logout', component: LoginComponent },
  { path: 'user-profile', component: UserProfileComponent },
  { path: 'rides-page', component: RidesPageComponent },
  { path: 'create-ride', component: CreateRideComponent },
  { path: 'my-rides', component: MyRidesComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
