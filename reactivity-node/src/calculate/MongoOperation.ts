import * as mongoose from 'mongoose';
import * as config from '../config';
import Operation from './Operation';

export default class MongoOperation implements Operation {
    private static model;

    constructor() {
        mongoose.connect(config.config.mongo.url, {poolSize: config.config.mongo.poolSize});
        mongoose.Promise = global.Promise;
        let db = mongoose.connection;
        db.on("error", function(error){
           console.log("Mongo Error", error);
        });
        db.on("open", (error)=>{
           if (error) {
               console.log("Mongo Error", error);
               return;
           }
           console.log("Mongo Connected");
           this.initModel();
        });

    }

    initModel() {
        if (MongoOperation.model) {
            return;
        }

        let ExampleSchema = new mongoose.Schema({
            id: Number,
            value: Number
        });

        MongoOperation.model = mongoose.model('Example', ExampleSchema);
        MongoOperation.model.create({id:1, value:10}, (error)=>{
            if (error) console.log("Mongoose error", error);
        });
        MongoOperation.model.create({id:2, value:20}, (error)=>{
            if (error) console.log("Mongoose error", error);
        });
        MongoOperation.model.create({id:0, value:30}, (error)=>{
            if (error) console.log("Mongoose error", error);
        });
        console.log("Mongo model ready");
    }

    calculate(leftValue: number, rightValue: number): Promise<number> {
        return new Promise((resolve, reject) => {
            MongoOperation.model.find({id: leftValue}, (error, examples)=>{
                if (error) {
                    reject(error);
                    return;
                }

                let result = examples[0] ? examples[0].value : 0;
                resolve(result);
            });    
        });
    }
}
