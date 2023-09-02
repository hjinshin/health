import React, {useEffect, useState, useRef } from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";
import './ChangeInfo.css'

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;
function ChangeInfo(props) {
    const [nickname, setNickname] = useState(sessionStorage.getItem('nickname')); // 초기값은 빈 문자열
    const [img, setImg] = useState({
        url: undefined,
        tempData: undefined,
        origin: undefined
    });
    const navigate = useNavigate();
    const imageInputRef = useRef(null);

    useEffect(() => {
        async function loadImg() {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/api/image`,
                params: {
                    "nickname": `${sessionStorage.getItem('nickname')}`,
                },
                responseType: 'arraybuffer',
            }).then((res) => {
                const blob = new Blob([res.data], { type: 'image/png' });
                const imageUrl = URL.createObjectURL(blob);
                setImg((prev) => {
                    return {...prev, url: imageUrl, origin: imageUrl};
                });
            });
           }
           loadImg();
    }, []);

    function resetFileInput() {
        if(imageInputRef.current) 
            imageInputRef.current.value = '';
    }
    function onClickImageUpload() {
        imageInputRef.current.click();
    }
    
    
    function handleChangeFile(e) {
        const maxSize = 100 * 1024;
        const selectedImg = e.target.files[0];
        if(selectedImg !== undefined) {
            if(selectedImg.size > maxSize) 
                alert("이미지 용량이 너무 큽니다(최대 100KB)");
            else { 
                setImg((prev) => {
                    return {...prev, tempData: selectedImg};
                });
                const blob = new Blob([selectedImg], { type: 'image/png' });
                const imageUrl = URL.createObjectURL(blob);
                setImg((prev) => {
                    return {...prev, url: imageUrl};
                });
            }           
        } else {
            setImg((prev) => {
                return {...prev, tempData: undefined, url: img.origin};
            });            
        }
      }
     
      async function sendImg(file){
        if(file !== undefined) {
            const fd = new FormData();
            fd.append("file", file);
            axios.post(SERVER_SEARCH_URL + '/api/image', fd, {
            headers: {
                "Authorization": sessionStorage.getItem('Authorization'),
                "Content-Type": `multipart/form-data`,
            }
            })
            .then((res) => {
                const blob = new Blob([file], { type: 'image/png' });
                const imageUrl = URL.createObjectURL(blob);
                setImg((prev) => {
                    return {...prev, url: imageUrl, tempData: undefined, origin: imageUrl};
                });
            })            
        }
        resetFileInput();
      }

    async function fetchNick () {
        await axios({
            method: "PUT",
            url: SERVER_SEARCH_URL + `/api/profile`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization'),

            },
            params: { "nickname": nickname }
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
        });
    };

    const handleChangeNickname = () => {;
        // 입력된 값을 닉네임 상태로 설정
        if(nickname.length <2 || nickname.length >21)
            alert("닉네임은 최소 3글자 및 최대 20글자까지 가능합니다.")
        else
            fetchNick();            
    };

    const handleInputChange = (event) => {
        // input 요소의 값이 변경될 때마다 상태 업데이트
        setNickname(event.target.value);
    };

        return (
        <div>
            <div className='image-update'>
                <img className='image' src={img.url} alt='프로필 이미지'/>
                <input className='image-input' type="file" id="file" ref={imageInputRef} onChange={handleChangeFile}></input>
                <button onClick={onClickImageUpload}>이미지 선택</button>
                <button className='image-button' onClick={() => {sendImg(img.tempData)}}>프로필 이미지 변경</button>                
            </div>
            <div className='nickname-update'>
                <input
                    className='nickname-input'
                    type="text"
                    placeholder="새로운 닉네임을 입력하세요"
                    value={nickname}
                    onChange={handleInputChange}
                />
                <button className='nickname-button' onClick={handleChangeNickname}>정보 변경</button>
            </div>
        </div>
    );
}

export default ChangeInfo;