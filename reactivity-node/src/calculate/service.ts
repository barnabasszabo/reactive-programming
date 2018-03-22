import Operation from './Operation';
import AddOperation from './AddOperation';
import MultiplyOperation from './MultiplyOperation';
import ReadFileOperation from './ReadFileOperation';
import ReadFileSyncOperation from './ReadFileSyncOperation';
import PiOperation from './PiOperation';
import MongoOperation from './MongoOperation';

let operations: any = {};
operations.ADD = new AddOperation();
operations.MULTIPLY = new MultiplyOperation();
operations.PI = new PiOperation();
operations.READFILE = new ReadFileOperation();
operations.READDB = new MongoOperation();

let syncOperations: any = {};
syncOperations.ADD = new AddOperation();
syncOperations.MULTIPLY = new MultiplyOperation();
syncOperations.PI = new PiOperation();
syncOperations.READFILE = new ReadFileSyncOperation();
syncOperations.READDB = new MongoOperation();

export default function calculate(req, res, next){
    Promise.all(
        req.body.map(calculateOperation.bind(null, req.query.sync))
    ).then((result)=>{
        res.json(result);    
    });     
}

async function calculateOperation(sync, operationElement) {
    let operation = getOperation(sync, operationElement);
    if (!operation) {
        return 0;
    }
    let leftValue = await getLeftValue(sync, operationElement);
    let rightValue = await getRightValue(sync, operationElement);

    let result = 0;
    try {
        // console.log("Executing operation ", sync, operationElement.operation, leftValue, rightValue);
        result = await operation.calculate(leftValue, rightValue)
    } catch (error) {
        console.log("Error during opereation", sync, operationElement.operation, leftValue, rightValue, error);
    }
    
    return result;
}

function getOperation(sync, operation): Operation {
    if (sync) {
        return syncOperations[operation.operation];    
    }
    return operations[operation.operation];
}

async function getLeftValue(sync, operation): Promise<number> {
    if (operation.leftOperation) {
        return calculateOperation(sync, operation.leftOperation);
    }
    return Promise.resolve(operation.leftValue);
}

async function getRightValue(sync, operation): Promise<number> {
    if (operation.rightOperation) {
        return calculateOperation(sync, operation.rightOperation);
    }
    return Promise.resolve(operation.rightValue);
}