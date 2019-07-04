
const jwt = require('jsonwebtoken') ;
const db =require('../models');

module.exports =  async (req, res, next) => {
  try {
    req.user = null
    
    if (req.headers.authorization) {
      
      
      let uuid
      jwt.verify(
        req.headers.authorization.split(' ')[1],
        process.env.JWT_SECRET,
        (err, payload) => {
          if (err) {
           
            return next(err)
          }
          
          uuid = payload.uuid
        })
        
      
      const user = await db.User.findOne({
        where : {
          uuid:Buffer(uuid,'hex')
        }
      })
      
      if (!user) {
        return res.status(404).json('사용자를 찾을 수 없습니다.')
      }

      req.user = user
    }

    next()
  } catch (e) {
    next(e)
  }
}