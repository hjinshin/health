import React from 'react';

import './Header.css'

import muscleleft from '../images/muscleleft.png'
import muscleright from '../images/muscleright.png'

function Header() {
    function onClick(e) {
        console.log('click'); 
    }
    return (
        <div className='header-container'>
            <div className='logo-container'>
                <a href='/' className='logo'>
                    <img className='muscleleft' src={muscleleft} alt='ml' />
                    <p className='health-logo'>HEALTH.GG</p>
                    <img className='muscleright' src={muscleright} alt='mr' />
                </a>
            </div>
            <a href={'/login'}>
                <button type='button' className='login-button' onClick={onClick}>Login</button>
            </a>
        </div>
    );
}

export default Header;