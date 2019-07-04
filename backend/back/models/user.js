const bcrypt = require('bcrypt');
const uuid = require('../utils/uuid');

module.exports = (sequelize,DataTypes)=>{
    const User = sequelize.define('User',{
        uuid: {
            allowNull: false,
            unique: true,
            type: 'BINARY(16)',
            defaultValue: () => Buffer(uuid(), 'hex'),
            get: function() {
              return Buffer.from(this.getDataValue('uuid'))
                .toString('hex')
            }
          },
          email: {
            allowNull: false,
            unique: true,
            type: DataTypes.STRING,
            validate: {
              isEmail: true
            }
          },
          password: {
            allowNull: false,
            type: DataTypes.STRING
          },
          name :{
            allowNull:false,
            type: DataTypes.STRING
          }
    },{
        charset:'utf8',
        collate:'utf8_general_ci',
    })
    User.associate = (db) =>{
        // associations
        db.User.hasMany(db.Image);
        }
    
      User.beforeSave(async (user, options) => {
        if (user.changed('password')) {
          const salt = await bcrypt.genSalt(10);
          user.password = await bcrypt.hash(user.password, salt);
        }
      })
      User.prototype.toWeb = function() {
        const values = Object.assign({}, this.get())
    
        delete values.id
        delete values.password
    
        return values
      }
      return User
}