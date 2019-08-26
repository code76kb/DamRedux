
import React from 'react';
import {
  View,
  Text,
} from 'react-native';
import { connect } from 'react-redux';
import {getOfferAllData,testIt} from "./actions/userActions"; 

const TAG = "First :";

class First extends React.Component{
  
  componentDidMount(){
    console.log(TAG,"Did Mount :"+JSON.stringify(this.props));
    //Dispatch
    // this.props.dispatch({type:"TEST",data:"Bye Bye World !!"});  //Works 
    
    // this.props.dispatch(testIt({data:"Bye Bye World !!"})); //Works

    // https://stagingrnapi.dealshare.in/index.php?r=v2%2Foffer%2Fall&inviteToken=&page=0&pagev2=temp&filter=topdeal&tokens=&lang=en&auth_key=CIl_Nz4alKz-gFmLBk0dKD6N45sZby3R&city=1088&source=app&appVersion=0.1.51&deviceId=3fce03e8febca8d9&network=&isEmulator=false

    this.props.dispatch(getOfferAllData({query:{r:"v2/offer/all",
                                         lang:'en',
                                         auth_key:"CIl_Nz4alKz-gFmLBk0dKD6N45sZby3R",
                                         city:'1088',
                                        }}));
    
  }

  componentWillReceiveProps(newProps){
    this.props = newProps;
    console.log(TAG,"Will Receive Props TestIt:"+JSON.stringify(this.props));
  }

  render()
  {
    return (
        <View>
          <Text>{"Hello Redux!!"}</Text>
        </View>
    );
   }
}


const mapStateToProps=(state)=>{
  return {test,offerAllData,error} = state
}

// const mapDispatchToProps=(dispatch)=>{
//   return {getOfferAllData:()=>dispatch()}
// }

export default connect(mapStateToProps)(First);
