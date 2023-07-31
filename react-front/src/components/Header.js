import React from 'react';
import { Link } from 'react-router-dom';

import './Header.css'

function Header() {
    function onClick(e) {
        console.log('click'); 
    }
    return (
        <div className='header-container'>
            <div className='logo-container'>
                <a href='/' className='logo'>
                    <h2>health.gg</h2>
                </a>
            </div>
            <Link to={'/login'}>
                <button type='button' className='login-button' onClick={onClick}>Login</button>
            </Link>
        </div>
    );
}

export default Header;