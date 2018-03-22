import Operation from './Operation';

export default class PiOperation implements Operation {
    calculate(leftValue: number, rightValue: number): Promise<number> {
        let result = 0;

        //SUM( +/-1 / 2k+1)
        let multiplier = 1;
        for (let i = 0; i < leftValue; i++) {
            result = result + multiplier / (2 * i + 1);
            multiplier = multiplier * -1;
        }
        result = result * 4;
        return Promise.resolve(result);
    }
}
