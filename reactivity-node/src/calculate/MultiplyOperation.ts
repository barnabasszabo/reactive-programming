import Operation from './Operation';

export default class MultiplyOperation implements Operation {
    calculate(leftValue: number, rightValue: number): Promise<number> {
        let result = leftValue * rightValue;
        return Promise.resolve(result);
    }
}
