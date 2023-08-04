import React from 'react';

import login_button from '../../images/kakao_login_button.png';
import { KAKAO_AUTH_URL } from '../../services/loginHandler/OAuth';

function Login() {
    return (
        <div className='login-container'>
            <a href={KAKAO_AUTH_URL}><img src={login_button} alt='login_button'/></a>
        </div>
    );
}

export default Login;