import React, {useState} from 'react';
import axios from "axios";
import './ChangeSite.css';

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;
function ChangeSite(props) {
    const [nickname, setNickname] = useState('');
    const [exercise, setExercise] = useState('');
    const [recordValue, setRecordValue] = useState('');
    const recordPlusNick = (event) => {
        setNickname(event.target.value);
    };
    const recordPlusExercise = (event) => {
        setExercise(event.target.value);
    };
    const recordPlusValue = (event) => {
        setRecordValue(event.target.value);
    };

    async function recordPlus () {
        await axios({
            method: "PUT",
            url: SERVER_SEARCH_URL + `/api/record`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            data: {
                "nickname": nickname,
                "exerciseName": exercise,
                "value": recordValue
            }
        }).then((res) => {
            if(!res.data.success){
                alert(res.data.message);
            }
            window.location.reload();
        });
    };

    const [cid, setCid] = useState('');
    const [category, setCategory] = useState('');
    const categoryPlusCid = (event) => {
        setCid(event.target.value);
    };
    const categoryPlusCategory = (event) => {
        setCategory(event.target.value);
    };

    async function categoryPlus () {
        await axios({
            method: "PUT",
            url: SERVER_SEARCH_URL + `/api/category`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            data: {
                "cid": cid,
                "categoryName": category,
            }
        }).then((res) => {
            if(!res.data.success){
                alert(res.data.message);
            }
            window.location.reload();
        });
    };
    const [eid, setEid] = useState('');
    const [subcategoryCid, setSubcategoryCid] = useState('');
    const [exerciseName, setExerciseName] = useState('');
    const subcategoryPlusEid = (event) => {
        setEid(event.target.value);
    };
    const subcategoryPlusCid = (event) => {
        setSubcategoryCid(event.target.value);
    };
    const subcategoryPlusEN = (event) => {
        setExerciseName(event.target.value);
    };

    async function subcategoryPlus () {
        await axios({
            method: "PUT",
            url: SERVER_SEARCH_URL + `/api/subcategory`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            data: {
                "eid": eid,
                "cid": subcategoryCid,
                "exerciseName": exerciseName
            }
        }).then((res) => {
            if(!res.data.success){
                alert(res.data.message);
            }
            window.location.reload();
        });
    };
    return (
        <div>
            <h4>데이터 추가</h4>
            <p>기록 추가</p>
            <div className='record-plus'>
                <input type="text" placeholder="닉네임을 입력하세요" value={nickname} onChange={recordPlusNick}/>
                <input type="text" placeholder="운동명을 입력하세요" value={exercise} onChange={recordPlusExercise}/>
                <input type="text" placeholder="운동명을 입력하세요" value={recordValue} onChange={recordPlusValue}/>
                <button onClick={recordPlus}>기록 추가</button>
            </div>
            <p>카테고리 추가</p>
            <div className='category-plus'>
                <input type="text" placeholder="cid를 입력하세요" value={cid} onChange={categoryPlusCid}/>
                <input type="text" placeholder="카테고리명을 입력하세요" value={category} onChange={categoryPlusCategory}/>
                <button onClick={categoryPlus}>카테고리 추가</button>
            </div>
            <p>서브카테고리 추가</p>
            <div className='subcategory-plus'>
                <input type="text" placeholder="eid를 입력하세요" value={eid} onChange={subcategoryPlusEid}/>
                <input type="text" placeholder="cid를 입력하세요" value={subcategoryCid} onChange={subcategoryPlusCid}/>
                <input type="text" placeholder="운동명을 입력하세요" value={exerciseName} onChange={subcategoryPlusEN}/>
                <button onClick={subcategoryPlus}>기록 추가</button>
            </div>
            <h4>데이터 삭제</h4>
            <p>기록 삭제</p>
            <div className='record-delete'>
            </div>
        </div>
    );
}

export default ChangeSite;