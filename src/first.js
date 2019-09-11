
import React from 'react';
import {
  View,
  Text,
  Image,
  NativeModules,
  DeviceEventEmitter
} from 'react-native';
import { connect } from 'react-redux';
import {getOfferAllData,testIt} from "./actions/userActions"; 

const ImagePicker = NativeModules.ImagePickerModule;
const ChildLabor = NativeModules.ChildLabor;
const WareHouse = NativeModules.WareHouse;
const GPSWatcher = NativeModules.GPSWatcher;

const TAG = "First :";

class First extends React.Component{

  constructor(props){
    super(props); 
    this.state={img:"",count:0,gps:'false'};
  }
  
  componentDidMount(){
    console.log(TAG,"Did Mount :"+JSON.stringify(this.props));
    //Dispatch
    // this.props.dispatch({type:"TEST",data:"Bye Bye World !!"});  //Works 
    
    // this.props.dispatch(testIt({data:"Bye Bye World !!"})); //Works

    // https://stagingrnapi.dealshare.in/index.php?r=v2%2Foffer%2Fall&inviteToken=&page=0&pagev2=temp&filter=topdeal&tokens=&lang=en&auth_key=CIl_Nz4alKz-gFmLBk0dKD6N45sZby3R&city=1088&source=app&appVersion=0.1.51&deviceId=3fce03e8febca8d9&network=&isEmulator=false

    //Working!!!
    // this.props.dispatch(getOfferAllData({query:{r:"v2/offer/all",
    //                                      lang:'en',
    //                                      auth_key:"CIl_Nz4alKz-gFmLBk0dKD6N45sZby3R",
    //                                      city:'1088',
    //                                     }}));
    
    // WareHouse.getWareHouseData({},(success)=>{
    // console.log(TAG,"WareHouse DAta status :"+success);    
    // },
    // (error)=>{
    //   console.log(TAG,"WareHouse DAta fetch Error :");    
    // });

    DeviceEventEmitter.addListener("GPsStatus",(e)=>{
     console.log(TAG,"Gps event listner :"+JSON.stringify(e));
     this.setState({count:(this.state.count+1),gps:e.status});
    })
    GPSWatcher.startGpsWatch({},(status)=>{
       console.log(TAG,"Gps status :"+status);
       this.setState({count:(this.state.count+1),gps:status});
    })

  }

  componentWillReceiveProps(newProps){
    this.props = newProps;
    console.log(TAG,"Will Receive Props TestIt:"+JSON.stringify(this.props));
  }

  //openImg Picker
  openPicker=()=>{
    ImagePicker.openPickerDialog({},
    (uri)=>{
     console.log(TAG,"ImagePicker Success:",uri);    
     this.setState({img:uri}); 
    },
    (error)=>{
     console.log(TAG,"ImagePicker Error:",error);
    })
  }


  serviceTest=()=>{
    ChildLabor.startWork(
    {job_id:'209'},
    (success)=>{
     console.log(TAG,"Job Successs Callback :",success)
    },
    (error)=>{});

    setTimeout(()=>ChildLabor.stopWork(),60000);
  }

  serviceStart=()=>{
    ChildLabor.startWork(
    {job_id:'202'},
    (success)=>{
     console.log(TAG,"Job Start Successs Callback :",success)
    },
    (error)=>{});
  }

  serviceStop=()=>{
    ChildLabor.stopWork(
    {job_id:'209'},
    (success)=>{
     console.log(TAG,"Job Stop Successs Callback :",success)
    },
    (error)=>{
    });
  }

  render()
  {
    return (
        <View>
          <Text style={{textAlign:'center',padding:20}} onPress={this.serviceTest}>{"Hello Redux!!"}</Text>
          <Image style={{width:200,height:200}} source={{uri:this.state.img}}></Image>
          <Text style={{textAlign:'center',padding:20}} onPress={this.serviceStart}>{"Start!!"}</Text>
          <Text style={{textAlign:'center',padding:20}} onPress={this.serviceStop}>{"Stop!!"}</Text>

          <Text style={{textAlign:'center',padding:20}} onPress={this.serviceStop}>{"GPS status!!"}</Text>
          <Text style={{textAlign:'center',padding:20}} onPress={this.serviceStop}>{this.state.gps.toString()}</Text>
          <Text style={{textAlign:'center',padding:20}} onPress={this.serviceStop}>{this.state.count}</Text>


        </View>
    );
   }
}


const mapStateToProps=(state)=>{
  return {test} = state
}

// const mapDispatchToProps=(dispatch)=>{
//   return {getOfferAllData:()=>dispatch()}
// }

export default connect(mapStateToProps)(First);
