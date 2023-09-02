import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import axios from 'axios';
import Table from './table/Table';
import './Info.css'

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;

function Info(props) {
    const { query } = useParams();
    const [searchResult, setSearchResult] = useState([]);
    const [profile, setProfile] = useState([]);
    const [bpr, setBpr] = useState([]);
    const [cate, setCate] = useState("whole");
    const [bprCate, setBprCate] = useState("4major")
    const [chang, setChang] = useState();

    useEffect(() => {
        if(chang === 'bpr'){
            async function searchBpr(userNm) {
                const res = await axios.get(SERVER_SEARCH_URL+`/api/search/pbr?category=${encodeURIComponent(bprCate)}&userNm=${encodeURIComponent(userNm)}`)
                    .catch((error) => {
                        console.error(error);
                    });

                if(res.data.searchSuccess) {
                    console.log(res.data);
                    setBpr(parseDate(res.data.bestRecordDtoList, 'bpr'));
                }
            }
            searchBpr(query);
        }
        else if(chang === 'records'){
            async function searchRecords(userNm) {
                const res = await axios.get(SERVER_SEARCH_URL+`/api/search/records?category=${encodeURIComponent(cate)}&userNm=${encodeURIComponent(userNm)}`)
                    .catch((error) => {
                        console.error(error);
                    });

                if(res.data.searchSuccess) {
                    console.log(res.data);
                    setSearchResult(parseDate(res.data.recordsDtoList));
                }
            }
            searchRecords(query);
        }

        else{
            async function search(userNm) {
                const res = await axios.get(SERVER_SEARCH_URL+`/api/search?userNm=${encodeURIComponent(userNm)}`)
                    .catch((error) => {
                        console.error(error);
                    });

                if(res.data.searchSuccess) {
                    console.log(res.data);
                    setSearchResult(parseDate(res.data.recordsDtoList, 'searchResult'));
                    setProfile(res.data.profileDto);
                    setBpr(parseDate(res.data.bestRecordDtoList, 'bpr'));
                }
            }
            search(query);

        }
    }, [query, cate, bprCate, chang]);

    function parseDate(data, id) {
        return data.map(item => {
            // 기존 객체 복사
            const newItem = {...item};
            // 특정 key의 값을 변경
            if (id === 'searchResult') {
                newItem.recordDate = newItem.recordDate.split("T")[0];
            }
            else{
                newItem.bestRecordDate = newItem.bestRecordDate.split("T")[0];
            }
            return newItem;
        });
    }

    function change(categoryNm){
        setChang('records');
        setCate(categoryNm);
    }

    function changeBpr(categoryNm) {
        setChang('bpr');
        setBprCate(categoryNm)
    }

    return (
        <div>
            <div className='left'>
                <div className='left_high'>
                    <h1>{profile.nickname}</h1>
                </div>
                <div className='left_middle'>
                    <p>랭킹:</p>
                    <p>분류:</p>
                    <p>Tier:</p>
                    <p>4대:</p>
                    <p>최근 갱신일:</p>
                </div>
                <div className='left_low'>
                    <div className='left_low_button'>
                        <button onClick={()=>changeBpr('4major')}>4대운동</button>
                        <button onClick={()=>changeBpr('free-style')}>자유운동</button>
                        <button onClick={()=>changeBpr('bare-body')}>맨몸운동</button>
                    </div>
                    <Table data={bpr} name={0}/>
                </div>
            </div>
            <div className='right'>
                <div className='right_button'>
                    <button onClick={()=>change('whole')}>전체</button>
                    <button onClick={()=>change('4major')}>4대운동</button>
                    <button onClick={()=>change('free-style')}>자유운동</button>
                    <button onClick={()=>change('bare-body')}>맨몸운동</button>
                </div>
                <Table data={searchResult} name={1}/>
            </div>
        </div>
    );

}

export default Info;