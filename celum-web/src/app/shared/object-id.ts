export class ObjectId {
    constructor(
    public timestamp: number,
    public machineIdentifier: number,
    public processIdentifier: number,
    public counter: number
    ) { }
}
