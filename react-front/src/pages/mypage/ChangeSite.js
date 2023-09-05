import React, {useEffect, useState} from 'react';
import axios from "axios";
import './ChangeSite.css';
import Table from './table/Table';

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
    const recordPlusValue = (e) => {
        const regex = /^[0-9]+$/;
        if (regex.test(e.target.value)) {
            setRecordValue(e.target.value)}
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

    const [deleteNickname, setDeleteNickname] = useState('');
    const [confirm, setConfirm] = useState(0);
    const [recordList, setRecordList] = useState('');
    const recordDeleteNN = (event) => {
        setDeleteNickname(event.target.value);
    };
    async function showRecord () {
        await axios({
            method: "GET",
            url: SERVER_SEARCH_URL + `/api/record`,
            params: {
                "nickname": deleteNickname
            }
        }).then((res) => {
            const copy2 = [];
            res.data.forEach(item => {
                let copy1 = {};
                copy1 = {'categoryName': item.eid.cid.categoryName,
                    'exerciseName':item.eid.exerciseName,
                    'recordValue' : item.recordValue,
                    'rid' : item.rid};
                copy2.push(copy1);
            });
            setRecordList(copy2);
            setConfirm(1);
        });
    };
    function show(){
        if(confirm === 1){
            return <Table data={recordList} onSubmit={onSubmit}/>;
        }
    }
    async function onSubmit (rid) {
        await axios({
            method: "DELETE",
            url: SERVER_SEARCH_URL + `/api/record`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            params: {
                "rid": rid
            }
        });
        window.location.reload();
    };

    const [categoryList, setCategoryList] = useState([]);
    useEffect(()=>{
        async function fetchCategoryList () {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/api/category`,
            }).then((res) => {
                const copy = [];
                res.data.forEach((item, index) => {
                    copy.push(item.cid);
                });
                setCategoryList(copy);
            });
        };
        fetchCategoryList();
    }, [])

    const [selectedOption, setSelectedOption] = useState('');
    const handleSelectChange = (event) => {
        setSelectedOption(event.target.value);
    };
    async function categoryDelete () {
        await axios({
            method: "DELETE",
            url: SERVER_SEARCH_URL + `/api/category`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            params: {
                "cid": selectedOption
            }
        }).then((res)=>{
            if(res.data.success){
                //나중에 메시지 추가
            }
            else{
                alert(res.data.message);
            }
        })
    };

    const [subSelectedOption, setSubSelectedOption] = useState('');
    const [subSelectedOption2, setSubSelectedOption2]= useState('');
    const [subcategoryList, setSubcategoryList] = useState([]);

    useEffect(()=> {
        async function fetchSubcategoryList () {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/api/subcategory`,
                params: {
                    "cid": subSelectedOption
                }
            }).then((res) => {
                const copy = [];
                res.data.forEach((item, index) => {
                    copy.push(item.eid);
                });
                setSubcategoryList(copy);
            });
        };
        fetchSubcategoryList();
    }, [subSelectedOption])
    function subHandleSelectChange(event){
        setSubSelectedOption(event.target.value);
    }
    function subHandleSelectChange2(event){
        setSubSelectedOption2(event.target.value);
    }
    async function subCategoryDelete () {
        await axios({
            method: "DELETE",
            url: SERVER_SEARCH_URL + `/api/subcategory`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Authorization": sessionStorage.getItem('Authorization')
            },
            params: {
                "eid": subSelectedOption2
            }
        });
        window.location.reload();
    };

    return (
        <div>
            <h4>데이터 추가</h4>
            <p>기록 추가</p>
            <div className='record-plus'>
                <input type="text" placeholder="닉네임을 입력하세요" value={nickname} onChange={recordPlusNick}/>
                <input type="text" placeholder="운동명을 입력하세요" value={exercise} onChange={recordPlusExercise}/>
                <input type="text" placeholder="기록값을 입력하세요" value={recordValue} onChange={recordPlusValue}/>
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
                <input type="text" placeholder="닉네임을 입력하세요" value={deleteNickname} onChange={recordDeleteNN}/>
                <button onClick={showRecord}>확인</button>
                {show()}
            </div>
            <div className='category-delete'>
                <select value={selectedOption} onChange={handleSelectChange}>
                    <option value="">선택하세요</option>
                    {categoryList.map((option, index) => (
                        <option key={index} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
                <button onClick={categoryDelete}>카테고리 제거</button>
            </div>
            <div className='subcategory-delete'>
                <select value={subSelectedOption} onChange={subHandleSelectChange}>
                    <option value="">선택하세요</option>
                    {categoryList.map((option, index) => (
                        <option key={index} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
                <select value={subSelectedOption2} onChange={subHandleSelectChange2}>
                    <option value="">선택하세요</option>
                    {subcategoryList.map((option, index) => (
                        <option key={index} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
                <button onClick={subCategoryDelete}>서브카테고리 제거</button>
            </div>
        </div>
    );
}

export default ChangeSite;