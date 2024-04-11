const mongoose = require("mongoose");
const xeMay = new mongoose.Schema(
   {
      anhPh34430:{type:String},
      giaBanPh34430:{type:Number},
      mauSacPh34430:{type:String},
      moTaPh34430:{type:String},
      tenXePh34430:{type:String}
   },
   {
      timestamps:true,
   }
);

module.exports = mongoose.model("xemay",xeMay);