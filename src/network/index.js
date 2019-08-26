import axios from 'axios';

const TAG = "Network :";

function axiosInst(options){
    const defaultHeaders = {
        'Content-Type': 'application/json',
      }

    return axios.create({
      baseURL:"https://api.dealshare.in",
      timeout:options.timeout || 10000,
      headers:defaultHeaders,
    });
}

function executeRequest(method, api, payload, options={}){
    const body = method === "get" || !payload ? {} : {payload}
    
    const req = {
        method,
        url: api,
        params: options.query,
        ...body
    }
    console.log(TAG," axio options :"+JSON.stringify(options));
    console.log(TAG," axio Execute :"+JSON.stringify(req));
    
    const axiosInstence = axiosInst(options);
    return new Promise((resolve,reject)=>{
          return axiosInstence.request(req)
          .then(res=>{
              resolve(res);
          })
          .catch(error=>{
              reject(error);
          });
    });
}

export default {
    get(api,options){
        console.log(TAG,"get options :"+JSON.stringify(options));
        return executeRequest("get",api,null,options);
    },

    post(api,payload,options){
        return executeRequest("post",api,payload,options);
    }
}