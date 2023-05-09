export class Reservation {
    constructor(
        public id : number,
        public roomID: number,
        public check_in: string,
        public check_out:string
    ) {}
}