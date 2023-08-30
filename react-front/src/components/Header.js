import React, {useEffect, useState} from 'react';

import './Header.css'

import muscleleft from '../images/muscleleft.png'
import muscleright from '../images/muscleright.png'

function Header() {
    const [nick, setNick] = useState();
    function onClick(e) {
        console.log('click');
    }
    useEffect(() => {
        console.log("asdsadasdsa");
        function checkUserNick() {
            const item = localStorage.getItem('nickname')

            if (item) {
                setNick(item)
            }
            window.dispatchEvent(new Event("storage"));
        }

        checkUserNick()
        window.addEventListener('storage', checkUserNick)

        return () => {
            window.removeEventListener('storage', checkUserNick)
        }
    }, [])

    function log_on(){
        console.log(nick);
        if(!nick){
            return <a href={'/login'}>
                <button type='button' className='login-button' onClick={onClick}>Login</button>
            </a>
        }
        else{
            return <><a href={'/mypage'}>{nick}</a>
                <button type='button' className='login-button' onClick={log_out}>logout</button>
            </>
        }
    }

    function log_out(){
        localStorage.removeItem('nickname');
        setNick(null);
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
            {log_on()}
        </div>
    );
}

export default Header;