import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import LoginHandler from './services/loginoutHandler/LoginHandler';
import LogoutHandler from './services/loginoutHandler/LogoutHandler';
import Login from './pages/login/Login';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
          <Routes>
		  		<Route path='/*' element={<Home />}/>
            	<Route path='/login' element={<Login />}/>
                <Route path='/auth/kakao/callback' element={<LoginHandler />}/>
				<Route path='/auth/kakao/logout' element={<LogoutHandler />}/>
            </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
