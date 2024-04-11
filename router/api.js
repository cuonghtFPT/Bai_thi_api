var express = require("express");
var router = express.Router();

const xeMay = require("../model/xeMay");

router.get("/", (rq, rs) => {
   rs.send("Vào API Mobile")
});

router.get("/get-list-xeMay", async (rq, rs) => {
   try {
      const data = await xeMay.find();
      rs.send(data);
   } catch (error) {
      console.log(error);
      rs.status(500).json({
         status: 500,
         messenger: "Internal Server Error",
         data: [],

      })
   }
});

router.post("/port-xeMay", async (rq, rs) => {
   try {
      const data = rq.body;
      const newXemay = new xeMay({
         anhPh34430: data.anhPh34430,
         giaBanPh34430: data.giaBanPh34430,
         mauSacPh34430: data.mauSacPh34430,
         moTaPh34430: data.moTaPh34430,
         tenXePh34430: data.tenXePh34430
      });
      const result = await newXemay.save();

      rs.status(201).json({
         status: 201,
         messenger: "Crerate xeMay succesfully",
         data: result,
      })
   } catch (error) {
      console.log(error);
      rs.status(500).json({
         status: 500,
         messenger: "Internal server Error",
         data: [],
      })
   }
});

router.get("/get-xeMay/:id", async (req, res) => {
   try {
      const id = req.params.id;
      const xeMayDetail = await xeMay.findById(id);
      res.json(xeMayDetail)
   
   } catch (error) {
      console.log(error);
      res.status(500).json({
         status: 500,
         message: "Lỗi máy chủ nội bộ",
         data: null
      });
   }
});
// Xóa một sản phẩm giày dựa trên ID
router.delete("/delete-xeMay-by-id/:id", async (rq, rs) => {
   try {
     const { id } = rq.params;
     const result = await xeMay.findByIdAndDelete(id);
 
     if (result) {
       rs.json({
         status: 200,
         messenger: "Delete xeMay successfully",
         data: result,
       });
     } else {
       rs.status(404).json({
         status: 404,
         messenger: "xeMay not found",
         data: [],
       });
     }
   } catch (error) {
     console.log(error);
     rs.status(500).json({
       status: 500,
       messenger: "Internal Server Error",
       data: [],
     });
   }
 });

 // Cập nhật thông tin một sản phẩm giày dựa trên ID
router.put("/update-xeMay-by-id/:id", async (rq, rs) => {
   try {
     const { id } = rq.params;
     const data = rq.body;

     const updateXemay = await xeMay.findByIdAndUpdate(id, data, { new: true });
 
     if (updateXemay) {
       rs.json({
         status: 200,
         messenger: "Update xeMay successfully",
         data: updateXemay,
       });
     } else {
       rs.status(404).json({
         status: 404,
         messenger: "Xemay not found",
         data: [],
       });
     }
   } catch (error) {
     console.log(error);
     rs.status(500).json({
       status: 500,
       messenger: "Internal Server Error",
       data: [],
     });
   }
 });
// Route để tìm xe theo tên
router.get("/search-shoe", async (req, res) => {
   try {
      const key = req.query.key;
      const data = await xeMay.find({
         tenXePh34430: { $regex: key, $options: "i" },
      }).sort({ createAt: -1 });

      if (data) {
         res.send(data);
      } else {
         res.json({
            status: 400,
            msg: "That bai",
            data: [],
         });
      }
   } catch (error) {
      console.log("router.get  error:", error);
   }
});

module.exports = router;