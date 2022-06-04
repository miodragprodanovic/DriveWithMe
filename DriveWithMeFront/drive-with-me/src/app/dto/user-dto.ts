import { Location } from "../model/location";
import { UserRole } from "../model/user-role";

export class UserDTO {
    id!: number;
    email!: string;
    firstName!: string;
    lastName!: string;
    location!: Location;
    mobileNumber!: string;
    biography!: string;
    car!: string;
    userRole!: UserRole;
    confirmed!: boolean;
}
