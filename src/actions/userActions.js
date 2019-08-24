import { TEST,
         OFFER_ALL_DATA,
         OFFER_ALL_DATA_SUCCESS,
         OFFER_ALL_DATA_ERROR
        } from "./actionTypes";

 
export function testIt(data){
    return {
        type:TEST,
        data
    }
}

export const getOfferAllData=()=>{
    return {
        type:OFFER_ALL_DATA
    };
}

export const getOfferAllDataSuccess=(data)=>{
    return {
        type:OFFER_ALL_DATA_SUCCESS,
        data
    };
}

export const getOfferAllDataError=(data)=>{
    return {
        type:OFFER_ALL_DATA_ERROR,
        data
    };
}