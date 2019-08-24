import {combineReducers} from "redux";
import user  from './UserReducer';

const rootReducer = combineReducers({user:user});

export default rootReducer;