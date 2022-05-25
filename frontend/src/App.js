import React, { Suspense } from 'react'
import { Route, Switch } from 'react-router-dom'

import Hoppy from './views/MainPage/hoppy'
import LoginPage from './views/LoginPage/LoginPage';
import AuthRedirectHandler from './AuthRedirectHandler';

function App() {
    return (
        <Suspense fallback={(<div>loading...</div>)}>
          <div>
            <Switch>
              <Route exact path="/" component={Hoppy} />
              <Route exact path="/login" component={LoginPage} />
              <Route exact path="/oauth/login/kakao" component={AuthRedirectHandler} />
            </Switch>
          </div>
        </Suspense>
        );
}

export default App;