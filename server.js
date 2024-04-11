const express = require("express");
var app = express();
const port = 3000;
var logger = require('morgan');

const database = require('./config/db');
const apiMobile = require('./router/api');

app.use(express.json());
app.use(logger('dev'));

app.use('/api',apiMobile);
database.connect();

app.listen(port, ()=>{
   console.log("Example app listening on port" + port);
});

app.get('/',(req,res)=>{
   res.send("WEB")
})
module.exports=app;