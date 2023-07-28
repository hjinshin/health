import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import './Navbar.css'

class Navbar extends Component {
    render() {
        return (
            <div>
                <div className='navbar'>
                    <span>|</span>
                    <Link className='navbarMenu' to={'/'}>홈</Link>
                    <span>|</span>
                    <Link className='navbarMenu' to={'/userRank'}>사용자 랭킹</Link>
                    <span>|</span>
                    <Link className='navbarMenu' to={'/fitnessRank'}>운동 랭킹</Link>
                    <span>|</span>
                </div>
            </div>
        );
    }
}

export default Navbar;