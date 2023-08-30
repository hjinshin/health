import React from 'react';
import Header from "./Header"
import Navbar from "./Navbar"
import Main from "../pages/main/Main"
//import Footer from "./components/Footer"

function AppLayout(props) {
    return (
        <div>
            <Header/>
            <Navbar/>
            <Main />
            {/* <Footer/> */}
        </div>
    );
}

export default AppLayout;