import React, {useState} from 'react';
import axios from 'axios';
function Mypage() {
    const [nickname, setNickname] = useState(localStorage.getItem('nickname')); // 초기값은 빈 문자열

    async function fetchNick () {
        console.log("sssssss");
        try {
            await axios.put(`api/profile?${nickname}`);
        } catch(error) {
            console.error(error);
        };
    }
    const handleChangeNickname = () => {
        // 입력된 값을 닉네임 상태로 설정
        if(nickname.length <3 || nickname.length >8){
            alert("닉네임은 최소 3글자 및 최대 7글자까지 가능합니다.")
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
            <button onClick={handleChangeNickname}>닉네임 변경</button>
        </div>
    );
}

export default Mypage;