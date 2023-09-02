import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;

function LogoutHandler(props) {
    const navigate = useNavigate();

    useEffect(() => {
        async function kakaoLogout() {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/auth/kakao/logout`,
                headers: {
                    "Content-Type": "application/json;charset=utf-8",
                    "Authorization": sessionStorage.getItem('Authorization'),
                }
            }).then((res) => {
                console.log(res.data);
                if(res.data.success){
                    sessionStorage.removeItem('nickname');
                    sessionStorage.removeItem('Authorization');
                }
                else{
                    alert("로그아웃 실패");
                }
                navigate("/");
            });
        };
        kakaoLogout();
    }, [navigate]);
    return (
        <div>
            
        </div>
    );
}

export default LogoutHandler;