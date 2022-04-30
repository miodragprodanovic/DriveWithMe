import { UserRole } from "./user-role";

export class User {
    id!: number;
    firstName!: string;
    lastName!: string;
    email!: string;
    password!: string;
    userRole!: UserRole;
    confirmed!: boolean;
}
