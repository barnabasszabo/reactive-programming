export default interface Operation {
    calculate(leftValue: number, rightValue: number): Promise<number>;
}