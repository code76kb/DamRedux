import {OFFER_ALL_DATA,
        OFFER_ALL_DATA_SUCCESS,
        OFFER_ALL_DATA_ERROR,
        TEST,           
} from "../actions/actionTypes";


const initialState = {
    test: "Hello world",
    offerAllData:{},
    error:{},
  };

const userReducer = (state = initialState, action) => {
    switch (action.type) {
      
      case TEST: {
       return { ...state, test:action.data}
      }    
      
      case OFFER_ALL_DATA_SUCCESS:{
        return {...state, offerAllData:action.data};
      }

      case OFFER_ALL_DATA_ERROR:{
        return {...state, error:action.data};
      }

      default: {
        return state;
      }
    
    }
  };

  export default userReducer;