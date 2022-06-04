import { UserRole } from "../model/user-role";

export class UserRegisterDTO {
    email!: string;
    password!: string;
    firstName!: string;
    lastName!: string;
    location!: Location;
    mobileNumber!: string;
    userRole!: UserRole;
}
