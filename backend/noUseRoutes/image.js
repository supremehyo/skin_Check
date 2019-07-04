const express = require('express');
const db = require('../models');
const multer = require('multer');
const router = express.Router();
const path = require('path') ;
const fs =require('fs') ;


fs.readdir('uploads',(error)=>{
    if(error){
        console.error('upload 폴더가 없어 upload 폴더를 생성합니다.');
        fs.mkdirSync('uploads');
    }
})
const uploadOption = multer({
    storage : multer.diskStorage({
        destination(req,file,cb){
            cb(null,'uploads/')
        },
        filename(req,file,cb){
            const ext = path.extname(file.originalname);
            cb(null,path.basename(file.originalname,ext)+req.user.uuid+new Date().valueOf()+ext);
        },
    }),
    limits : {fileSize : 5 * 1024 * 1024}
})

router.post('/',uploadOption.single('picture'),async(req,res,next)=>{
//  이미지 처리
    console.log(req.file)
    
    try{
        res.send('이미지 업로드 완료');
    }catch(e){
        next(e);
    }
})

module.exports = router;