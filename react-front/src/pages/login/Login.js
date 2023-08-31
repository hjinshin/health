import React from 'react';
import './Login.css'

import login_button from '../../images/kakao_login_button.png';
import { KAKAO_AUTH_URL } from '../../services/loginoutHandler/OAuth';

function Login() {
    return (
        <div className='login-tool'>       
            <div className='login-text'>health.gg 에서 여러분의 정보를<br></br> 
                등록하고 티어를 확인하세요 :)
            </div>  
            <div className='login-container'>
                <a href={KAKAO_AUTH_URL}><img src={login_button} alt='login_button'/></a>
            </div>     

        </div>
    );
}


export default Login;