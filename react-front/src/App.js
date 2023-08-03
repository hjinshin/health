import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./components/Header"
import Navbar from "./components/Navbar"
import Home from "./pages/home/Home"
import UserRank from "./pages/user-ranking/UserRank"
import FitnessRank from "./pages/fitness-ranking/FitnessRank"
import Login from "./pages/login/Login"
import Footer from "./components/Footer"

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Navbar/>
        <Routes>
          <Route path='/*' element={<Home />}/>
          <Route path='/userRank/*' element={<UserRank />}/>
          <Route path='/fitnessRank/*' element={<FitnessRank />}/>
          <Route path='/login' element={<Login />}/>
        </Routes>
        <Footer/>
      </div>
    </BrowserRouter>

  );
}

export default App;
