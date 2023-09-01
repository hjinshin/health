import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function LogoutHandler(props) {
    const navigate = useNavigate();

    useEffect(() => {
        async function kakaoLogout() {
            await axios({
                method: "GET",
                url: `/auth/kakao/logout`,
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