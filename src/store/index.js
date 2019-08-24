import { createStore, applyMiddleware, compose } from "redux";
import {persistStore} from "redux-persist";
import createSagaMiddleware from 'redux-saga';
import {createLogger} from 'redux-logger';
import persistedReducer from "../reducers";
import rootSaga from "../saga/rootSagas";


const middleware = [];

const sagaMiddleware = createSagaMiddleware()
middleware.push(sagaMiddleware);
middleware.push(createLogger());

const store = createStore(persistedReducer,applyMiddleware(...middleware));

let persistor = persistStore(store);

sagaMiddleware.run(rootSaga);

export {store, persistor};