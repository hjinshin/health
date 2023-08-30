import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./components/Header"
import Navbar from "./components/Navbar"
import Home from "./pages/home/Home"
import { useEffect } from 'react';
import axios from 'axios';
//import Footer from "./components/Footer"

function App() {
  useEffect(() => {
    async function login() {
      await axios({
          method: "GET",
          url: `/api/access-token`,
          headers: {
              "Content-Type": "application/json;charset=utf-8"
          }
      }).then((res) => {
          console.log(res);
      });
  };
  login();
  }, []);
  
  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Navbar/>
        <Routes>
          <Route path='/*' element={<Home />}/>
        </Routes>
        {/* <Footer/> */}
      </div>
    </BrowserRouter>
  );
}

export default App;
