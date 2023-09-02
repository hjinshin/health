import React, {useEffect, useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;
function ChangeInfo(props) {
    const [nickname, setNickname] = useState(sessionStorage.getItem('nickname')); // 초기값은 빈 문자열
    const [img, setImg] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        async function loadImg() {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/api/image`,
                headers: {
                    "Authorization": sessionStorage.getItem('Authorization'),
                },
                responseType: 'arraybuffer',
            }).then((res) => {
                const blob = new Blob([res.data], { type: 'image/png' });
                const imageUrl = URL.createObjectURL(blob);
                setImg(imageUrl);
            });
           }
           loadImg();
    }, []);

    
    function handleChangeFile(e) {
        const maxSize = 100 * 1024;
        const selectedImg = e.target.files[0];
        if(selectedImg.size > maxSize)
            alert("이미지 용량이 너무 큽니다(최대 100KB)");
        else
            sendImg(e.target.files[0]);
      }
     
      async function sendImg(file){
        const fd = new FormData();
        fd.append("file", file);
        console.log(fd);
        axios.post(SERVER_SEARCH_URL + '/api/image', fd, {
          headers: {
            "Authorization": sessionStorage.getItem('Authorization'),
            "Content-Type": `multipart/form-data`,
          }
        })
        .then((res) => {
          console.log(res.data);
        })   
        window.location.reload();  
      }
    async function fetchNick () {
        await axios({
            method: "PUT",
            url: SERVER_SEARCH_URL + `/api/profile`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization'),

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

    const handleChangeNickname = () => {;
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
            <img src = {img} width={'100px'} height={'100px'} alt='프로필 이미지'/>
            <input type="file" id="file" onChange={handleChangeFile}></input>
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