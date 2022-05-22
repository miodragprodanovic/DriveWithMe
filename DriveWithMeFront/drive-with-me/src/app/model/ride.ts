import { Location } from "./location";
import { Price } from "./price";
import { User } from "./user";

export class Ride {
    id!: number;
    departureTime!: Date;
    arrivalTime!: Date;
    startingPoint!: Location;
    destination!: Location;
    driver!: User;
    maxPassengers!: number;
    passengers!: User[];
    requests!: User[];
    price!: Price;
    rules!: string;
}
