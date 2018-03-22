import * as fs from 'fs';
import * as readline from 'readline'; 
import Operation from './Operation';

export default class ReadFileOperation implements Operation {
    calculate(leftValue: number, rightValue: number): Promise<number> {
        return new Promise( (resolve, reject)=>{
            let lineReader = readline.createInterface({
                input: fs.createReadStream("./data/data.txt")
            });

            let index = 0;
            let found = false;
            lineReader.on("line", (line)=>{
                if (index == leftValue) {
                    let result = line.length;
                    resolve(result);
                    found = true;
                    lineReader.close();
                }
                index++;
            });
            lineReader.on("close", ()=>{
                if (!found) {
                    reject(-1);
                }
            });
        }); 
    }
}
