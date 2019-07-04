//import {PythonShell} from 'python-shell';
let {PythonShell} = require('python-shell')
let options = {
  mode: 'text',
  pythonPath: '/home/ubuntu/anaconda3/envs/Jinwoo/bin/python',
  pythonOptions: ['-u'],
  scriptPath: '',
  args: []

};

PythonShell.run('./a.py', options, function (err, results) {
  if (err) throw err;
  console.log('results: %j', results);

});
// PythonShell.runString('x=1+1;print(x)', null, function (err) {
//     if (err) throw err;
//     console.log('finished');
//   });
