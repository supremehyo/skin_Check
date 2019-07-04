const express =require('express');
const db = require('../models');
const bcrypt = require('bcrypt');
const router = express.Router();
const response = require('../utils/response');
const jwt = require('jsonwebtoken');

router.get('/',(req,res,next)=>{//사용자 정보 조회
    res.send('안녕하세요');
})
router.post('/login',async (req,res,next)=>{//로그인 
    try {
        const email = req.body.email
        const password = req.body.password
        
        const user = await db.User.findOne({
            where :{
                email
            }
        })
        if(!user){
            return res.status(404).json('사용자를 찾을 수 없습니다.')
        }
    
        // 비밀번호 compare
        const match = await bcrypt.compare(password, user.password)
    
        if (!match) {
          return res.status(404).json('비밀 번호를 확인 해주세요')
        }
    
        // jwt payload 에 담길 내용
        const payload = {
          email: user.email,
          uuid: user.uuid
        }
    
        const token = jwt.sign(payload, process.env.JWT_SECRET, {
          expiresIn: process.env.JWT_EXPIRESIN
        })
    
        return response(res, {
          token
        })
      } catch (e) {
        next(e)
      }
})
router.post('/signUp',async (req,res,next)=>{//회원가입
    try{
        const email = req.body.email
        const password = req.body.password
        const name = req.body.name
        
        const user = await db.User.findOne({
            where:{
                email
            }
        })
        if (user) {
          return res.status(422).json('이미 회원가입되어있습니다.');
        }
    
        
        const newUser = await db.User.create({
            email,
            password,
            name,
        })
        return response(res,{'message':'회원가입되었습니다.'},200)
      }catch(e){
        next(e)
      }
})

module.exports = router;
