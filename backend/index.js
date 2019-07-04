require('dotenv').config();
const express =require('express') 
const morgan =require('morgan') 

const db = require('./models');
const userAPIRouter = require('./routes/user');
const imageAPIRouter = require('./routes/image');
const jwtMiddleware =require('./utils/jwt.middleware') 

const app = express();
db.sequelize.sync();

app.use(morgan('dev'));
app.use(express.static('uploads'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(jwtMiddleware);

app.use('/api/user',userAPIRouter);
app.use('/api/image',imageAPIRouter);

app.listen(8010,()=>{
    console.log('server is running on http://localhost:8010');
});
