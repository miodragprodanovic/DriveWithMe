import { Location } from "../model/location";
import { Price } from "../model/price";
import { UserDTO } from "./user-dto";

export class RideDTO {
    id!: number;
    departureTime!: Date;
    arrivalTime!: Date;
    startingPoint!: Location;
    destination!: Location;
    driver!: UserDTO;
    maxPassengers!: number;
    passengers!: UserDTO[];
    requests!: UserDTO[];
    price!: Price;
    rules!: string;
}
