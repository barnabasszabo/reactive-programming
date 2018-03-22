import app from './app';

let port = process.env.PORT || 3000;

app.listen(port, (err)=>{
    if (err) {
        console.log("Error during startup", err);
    }
    console.log("Server started at ", port);
})