const express = require('express');
const db = require('../models');
const multer = require('multer');
const router = express.Router();
const path = require('path') ;
const response = require('../utils/response');
const fs =require('fs') ;
let {PythonShell} = require('python-shell');
const options = {
	mode:'text',
	pythonPath:'/home/ubuntu/anaconda3/envs/Jinwoo/bin/python',
	pythonOptions: ['-u'],
	scriptPath: '',
	args :[]
};
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
router.post('/new',uploadOption.single('picture'),async(req,res,next)=>{
	options.args = [req.file.filename];
	console.log(req.file.filename);
	PythonShell.run('b.py',options,function(err,results){
		if(err)throw err;
		console.log(results);
		res.json({filename:'new'+req.file.filename});
	});
});
router.post('/',uploadOption.single('picture'),async(req,res,next)=>{
//  이미지 처리
    console.log('파일 정보',req.file)
    
    
	options.args = [req.file.filename];
	    PythonShell.run('a.py',options,function(err,results){
		    if(err) throw err;
		    console.log(results);
		    response(res,{
			    results
		    })
	    })
        
    
})

module.exports = router;
