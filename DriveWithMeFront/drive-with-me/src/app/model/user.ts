import { UserRole } from "./user-role";
import { Location } from "./location";

export class User {
    id!: number;
    email!: string;
    password!: string;
    firstName!: string;
    lastName!: string;
    location!: Location;
    mobileNumber!: string;
    biography!: string;
    car!: string;
    userRole!: UserRole;
    confirmed!: boolean;
}
