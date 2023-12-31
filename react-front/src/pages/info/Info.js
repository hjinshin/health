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
    const [img, setImg] = useState();
    const [usrExist, setUsrExist] = useState();

    useEffect(() => {
        async function loadImg(userNm) {
            await axios({
                method: "GET",
                url: SERVER_SEARCH_URL + `/api/image`,
                params: {
                    "nickname": `${userNm}`,
                },
                responseType: 'arraybuffer',
            }).then((res) => {
                const blob = new Blob([res.data], { type: 'image/png' });
                const imageUrl = URL.createObjectURL(blob);
                setImg(imageUrl);
            });
        }
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
        async function searchRecords(userNm) {
            const res = await axios.get(SERVER_SEARCH_URL+`/api/search/records?category=${encodeURIComponent(cate)}&userNm=${encodeURIComponent(userNm)}`)
                .catch((error) => {
                    console.error(error);
                });

            if(res.data.searchSuccess) {
                console.log(res.data);
                setSearchResult(parseDate(res.data.recordsDtoList, 'searchResult'));
            }
        }
        async function search(userNm) {
            const res = await axios.get(SERVER_SEARCH_URL+`/api/search?userNm=${encodeURIComponent(userNm)}`)
                .catch((error) => {
                    console.error(error);
                });

            if(res.data.searchSuccess) {
                console.log(res.data);
                setUsrExist(true);
                setSearchResult(parseDate(res.data.recordsDtoList, 'searchResult'));
                setProfile(res.data.profileDto);
                setBpr(parseDate(res.data.bestRecordDtoList, 'bpr'));
                loadImg(query);
            } else {
                setUsrExist(false);
            }
        }            
        if(chang === 'bpr'){
            searchBpr(query);
        } else if(chang === 'records'){
            searchRecords(query);
        } else{
            search(query);
        }
    }, [query, cate, bprCate, chang, usrExist]);

    function parseDate(data, id) {
        return data.map(item => {
            // 기존 객체 복사
            const newItem = {...item};
            // 특정 key의 값을 변경
            if (id === 'searchResult') {
                const date = new Date(newItem.recordDate);
                newItem.recordDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
            }
            else{
                const date = new Date(newItem.bestRecordDate);
                newItem.bestRecordDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
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

    function changeContents() {
        if(usrExist === true) {
            return <div className='search-result-container'>
                <div className='left'>
                    <div className='left_high'>
                        <h1>{profile.nickname}</h1>
                    </div>
                    <div className='left_middle'>
                        <img src={img} className='profile-img' alt='프로필 이미지' width={'80px'} height={'80px'}/>
                        <div className='profile-info'>
                            <p>랭킹: {profile.ranking}</p>
                            <p>분류: 4대운동</p>
                            <p>4대: {profile.b_sum}</p>
                        </div>
                    </div>
                    <div className='left_low'>
                        <div className='left_low_button'>
                            <button className={`${bprCate === '4major' ? 'selected':'unselected'}`} onClick={()=>changeBpr('4major')}>4대운동</button>
                            <button className={`${bprCate === 'free-style' ? 'selected':'unselected'}`} onClick={()=>changeBpr('free-style')}>자유운동</button>
                            <button className={`${bprCate === 'bare-body' ? 'selected':'unselected'}`} onClick={()=>changeBpr('bare-body')}>맨몸운동</button>
                        </div>
                        <Table data={bpr} name={0}/>
                    </div>
                </div>
                <div className='right'>
                    <div className='right_button'>
                        <button className={`${cate === 'whole' ? 'selected':'unselected'}`} onClick={()=>change('whole')}>전체</button>
                        <button className={`${cate === '4major' ? 'selected':'unselected'}`} onClick={()=>change('4major')}>4대운동</button>
                        <button className={`${cate === 'free-style' ? 'selected':'unselected'}`} onClick={()=>change('free-style')}>자유운동</button>
                        <button className={`${cate === 'bare-body' ? 'selected':'unselected'}`} onClick={()=>change('bare-body')}>맨몸운동</button>
                    </div>
                    <Table data={searchResult} name={1}/>
                </div>
            </div>
        } else if(usrExist === false) {
            return <h2 className='banner'>HEALTH.GG에 등록되지 않은 사용자입니다. 오타를 확인 후 다시 검색해주세요.</h2>
        }
    }

    return (
        <div className='info-container'>
            {changeContents()}
        </div>
    );

}

export default Info;