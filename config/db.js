const mongoose = require("mongoose");
const COMMON = require("../COMMON");
mongoose.set("strictQuery",true);

const connect = async()=>{
   try{ 
      await mongoose.connect(COMMON.uri);
      console.log("Connect Successfully");
   }catch(error) {
      console.log(error);
   }
};

module.exports={connect};