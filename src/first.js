
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
    this.props.dispatch({type:"TEST",data:"Bye Bye World !!"});  //Works 
    // this.props.dispatch(()=>testIt({data:"Bye Bye World !!"}) );  // Gives an error : action must be a plain object


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
