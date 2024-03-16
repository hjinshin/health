import React, {useEffect, useState} from 'react';
import { KAKAO_LOGOUT_URL } from '../services/loginoutHandler/OAuth';
import './Header.css'

import muscleleft from '../images/muscleleft.png'
import muscleright from '../images/muscleright.png'
function Header() {
    const [nick, setNick] = useState();

    useEffect(() => {
        function checkUserNick() {
            const item = sessionStorage.getItem('nickname');
            if (item) {
                setNick(item);
            }
        }

        checkUserNick();
        window.addEventListener('storage', checkUserNick);

        return () => {
            window.removeEventListener('storage', checkUserNick);
        }
    }, [])

    function log_on(){
        if(!nick){
            return <a href={'/login'} className='log-in-out'>
                <button type='button' className='login-button'>Login</button>
            </a>
        }
        else{
            return <><a href={'/mypage'} className='mypage'>{nick}</a>
                <a href={KAKAO_LOGOUT_URL} className='log-in-out'>
                    <button type='button' className='logout-button'>logout</button>
                </a>
            </>
        }
    }

    return (
        <div className='header-container'>
            <div className='logo-container'>
                <a href='/' className='logo'>
                    <img className='muscleleft' src={muscleleft} alt='ml' />
                    <p className='health-logo'>HEALTH.GG?</p>
                    <img className='muscleright' src={muscleright} alt='mr' />
                </a>
            </div>
            {log_on()}
        </div>
    );
}

export default Header;