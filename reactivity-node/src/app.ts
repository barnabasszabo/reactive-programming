import * as express from 'express';
import * as bodyParser from 'body-parser';
import calculate from './calculate/service';

class App {
    public express;

    constructor(){
        this.express = express();
        this.initRoutes();
    }

    private initRoutes() {
        this.express.use(bodyParser.json({limit: '500mb'}));
        this.express.post("/calculate", calculate);
    }
}

export default new App().express;