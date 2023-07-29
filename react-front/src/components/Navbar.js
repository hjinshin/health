import React from 'react';
import './Navbar.css'

function Navbar() {
    return (
        <div>
            <div className='navbar'>
                <span>|</span>
                <a className='navbarMenu' href={'/'}>홈</a>
                <span>|</span>
                <a className='navbarMenu' href={'/userRank'}>사용자 랭킹</a>
                <span>|</span>
                <a className='navbarMenu' href={'/fitnessRank'}>운동 랭킹</a>
                <span>|</span>
            </div>
        </div>
    );
}

export default Navbar;