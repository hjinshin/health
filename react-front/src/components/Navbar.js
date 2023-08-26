import React from 'react';
import { useLocation } from 'react-router-dom';
import './Navbar.css'

function Navbar() {
    const location = useLocation();
    const {pathname} = location;
    let category = pathname;

    return (
        <div className='navbar-container'>
            <div className='navbar'>
                <span className='navbarline'>|</span>
                <a className={`navbarMenu ${category === '/' ? 'selected' : 'unselected'}`} href={'/'}>Home</a>
                <span className='navbarline'>|</span>
                <a className={`navbarMenu ${category.includes('/userRank') ? 'selected' : 'unselected'}`} href={'/userRank/4-major'}>User ranking</a>
                <span className='navbarline'>|</span>
                <a className={`navbarMenu ${category.includes('/fitnessRank') ? 'selected' : 'unselected'}`} href={'/fitnessRank'}>Exercise</a>    
                <span className='navbarline'>|</span>
            </div>
        </div>
    );
}

export default Navbar;