import * as fs from 'fs';
import * as readline from 'readline'; 
import Operation from './Operation';

export default class ReadFileSyncOperation implements Operation {
    calculate(leftValue: number, rightValue: number): Promise<number> {
        let content = fs.readFileSync("./data/data.txt", "utf-8");

        let lines = content.split("\n");
        if (!lines) {
            return Promise.reject(-1);
        }

        let resultLine = lines[leftValue-1];
        if (!resultLine) {
            return Promise.reject(-2);
        }

        let result = resultLine.length;
        return Promise.resolve(result);
    }
}
