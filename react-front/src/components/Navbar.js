import React from 'react';
import { useLocation } from 'react-router-dom';
import './Navbar.css'
<style>
  @import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Hahmlet:wght@100&display=swap');
</style>

function Navbar() {
    const location = useLocation();
    const {pathname} = location;
    let category = pathname;

    return (
        <div className='navbar-back'>
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
        </div>
    );
}

export default Navbar;