import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;

function Loginhandler(props) {
    const navigate = useNavigate();
    const code = new URL(window.location.href).searchParams.get("code");

    useEffect(() => {
        async function kakaoLogin() {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/auth/kakao/callback/?code=${code}`,
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                }
            }).then((res) => {
                console.log(res);
                if(res.data.loginSuccess){
                    sessionStorage.setItem('nickname', res.data.userInfo.nickname);
                    const token = res.headers.get('Authorization');
                    sessionStorage.setItem('Authorization', token);
                    sessionStorage.setItem('auth', res.data.userInfo.role);
                }
                else{
                    alert("로그인 실패");
                }
                navigate("/");
            });
        };
        kakaoLogin();
    }, [code, navigate]);
    return (
        <div className="LoginHandler">
            <div className="notice">
                <p>로그인 중입니다.</p>
                <p>잠시만 기다려주세요.</p>
                <div className="spinner"></div>
            </div>
      </div>
    );
}

export default Loginhandler;