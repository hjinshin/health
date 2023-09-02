import React, {useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;
function ChangeInfo(props) {
    const [nickname, setNickname] = useState(sessionStorage.getItem('nickname')); // 초기값은 빈 문자열
    const navigate = useNavigate();
    async function fetchNick () {
        await axios({
            method: "PUT",
            url: SERVER_SEARCH_URL + `/api/profile`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            params: {
                "nickname": nickname
            }
        }).then((res) => {
            console.log(res.data);
            if(res.data.success){
                setNickname(res.data.message);
                sessionStorage.setItem("nickname", nickname);
                navigate('/mypage');
            }
            else{
                alert(res.data.message);
            }
            window.location.reload();
        });
    };

    const handleChangeNickname = () => {
        // 입력된 값을 닉네임 상태로 설정
        if(nickname.length <2 || nickname.length >21){
            alert("닉네임은 최소 3글자 및 최대 20글자까지 가능합니다.")
        }
        else{
            fetchNick();
        }
    };

    const handleInputChange = (event) => {
        // input 요소의 값이 변경될 때마다 상태 업데이트
        setNickname(event.target.value);
    };

        return (
        <div>
            <input
                type="text"
                placeholder="새로운 닉네임을 입력하세요"
                value={nickname}
                onChange={handleInputChange}
            />
            <button onClick={handleChangeNickname}>정보 변경</button>
        </div>
    );
}

export default ChangeInfo;