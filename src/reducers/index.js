import { AsyncStorage } from 'react-native';
import {persistReducer} from "redux-persist";
import rootReducer from "./RootReducer"

const persistConfig = {
    key:'dealShare_root',
    storage:AsyncStorage,
    whitelist:[],//Reducer's to be saved 
    blacklist:["userReducer"], // Not saved, they gonna die! 
}

const persistedReducer = persistReducer(persistConfig,rootReducer);

export default persistedReducer;