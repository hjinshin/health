import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Table from './table/Table';
import './Info.css'

const SERVER_SEARCH_URL = 'http://localhost:8080';

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
            console.log('bprbprbprbprbprbprbprbprbprbpr');
            async function searchBpr(userNm) {
                const res = await axios.get(SERVER_SEARCH_URL+`/api/search/pbr?category=${encodeURIComponent(bprCate)}&userNm=${encodeURIComponent(userNm)}`)
                    .catch((error) => {
                        console.error(error);
                    });

                if(res.data.searchSuccess) {
                    console.log(res.data);
                    setBpr(res.data.bestRecordDtoList);
                }
            }
            searchBpr(query);
        }
        else if(chang === 'records'){
            console.log('recordsrecordsrecordsrecordsrecordsrecords');
            async function searchRecords(userNm) {
                const res = await axios.get(SERVER_SEARCH_URL+`/api/search/records?category=${encodeURIComponent(cate)}&userNm=${encodeURIComponent(userNm)}`)
                    .catch((error) => {
                        console.error(error);
                    });

                if(res.data.searchSuccess) {
                    console.log(res.data);
                    setSearchResult(res.data.recordsDtoList);
                }
            }
            searchRecords(query);
        }

        else{
            console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
            async function search(userNm) {
                const res = await axios.get(SERVER_SEARCH_URL+`/api/search?userNm=${encodeURIComponent(userNm)}`)
                    .catch((error) => {
                        console.error(error);
                    });

                if(res.data.searchSuccess) {
                    console.log(res.data);
                    setSearchResult(res.data.recordsDtoList);
                    setProfile(res.data.profileDto);
                    setBpr(res.data.bestRecordDtoList);
                }
            }
            search(query);

        }
    }, [query, cate, bprCate, chang]);

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
                    <h2>{profile.nickname}</h2>
                </div>
                <div className='left_middle'>
                    <h2>sadasd</h2>
                </div>
                <div className='left_low'>
                    <button onClick={()=>changeBpr('4major')}>4대운동</button>
                    <button onClick={()=>changeBpr('free-style')}>자유운동</button>
                    <button onClick={()=>changeBpr('bare-body')}>맨몸운동</button>
                    <Table data={bpr} name={0}/>
                </div>
            </div>

            <div className='right'>
                <button onClick={()=>change('whole')}>전체</button>
                <button onClick={()=>change('4major')}>4대운동</button>
                <button onClick={()=>change('free-style')}>자유운동</button>
                <button onClick={()=>change('bare-body')}>맨몸운동</button>
                <Table data={searchResult} name={1}/>
            </div>
        </div>
    );

}

export default Info;