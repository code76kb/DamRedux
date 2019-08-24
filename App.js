import React from 'react';
import { PersistGate } from 'redux-persist/integration/react';
import { Provider } from 'react-redux';
import { store, persistor } from './src/store';
import First from "./src/first.js"

class App extends React.PureComponent{

  render(){
    return(
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          <First/>
        </PersistGate>         
     </Provider>
    )
  }

}

export default App;