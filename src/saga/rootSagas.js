import {put,call,takeLatest,fork,all} from "redux-saga/effects";
import NETWORK from "../network";
import {OFFER_ALL_DATA} from "../actions/actionTypes";
import { getOfferAllDataSuccess, getOfferAllDataError } from "../actions/userActions";

const TAG = "RootSagas :";

function * getOfferAllData(action){
    try{
        console.log(TAG," getOfferAllData action :"+JSON.stringify(action));
        
        const response = yield call(NETWORK.get,"/index.php",action.data);
        yield put(getOfferAllDataSuccess(response));

    }catch(error){
        yield put(getOfferAllDataError(error));
    }
}


function * sagas(){
    yield [yield takeLatest(OFFER_ALL_DATA, getOfferAllData)]
};

export default function * rootSaga(){
    yield all([fork(sagas)]);
}

