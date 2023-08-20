import React from 'react';
import './Navbar.css'

function Navbar() {
    return (
        <div>
            <div className='navbar'>
                <span className='navbarline'>|</span>
                <a className='navbarMenu' href={'/'}>Home</a>
                <span className='navbarline'>|</span>
                <a className='navbarMenu' href={'/userRank'}>User ranking</a>
                <span className='navbarline'>|</span>
                <a className='navbarMenu' href={'/fitnessRank'}>Exercise</a>    
                <span className='navbarline'>|</span>
            </div>
        </div>
    );
}

export default Navbar;