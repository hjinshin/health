import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import LoginHandler from './services/loginHandler/LoginHandler';
import Login from './pages/login/Login';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
          <Routes>
		  		<Route path='/*' element={<Home />}/>
            	<Route path='/login' element={<Login />}/>
                <Route path='/auth/kakao/callback' element={<LoginHandler />}/>
            </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
