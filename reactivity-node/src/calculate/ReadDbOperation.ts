import * as config from '../config';
import Database from '../database';
import Operation from './Operation';

const selectValue = "SELECT value FROM data WHERE id=?";

export default class MultiplyOperation implements Operation {
    private database;

    constructor() {
        this.database = new Database(config.config.db);
    }

    calculate(leftValue: number, rightValue: number): Promise<number> {
        return this.database
            .openConnection()
            .then((connection) => {
                return this.database.query(connection, selectValue, [leftValue]);
            })
            .then((results) => {
                if (!results || !results[0]) {
                    return 0;
                }
                
                return results[0].value;
            });
    }
}
