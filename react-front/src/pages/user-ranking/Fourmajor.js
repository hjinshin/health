import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;
const SC_TYPE  = {
    SUM: "SUM", BENCH: "BENCH", DEAD: "DEAD", 
    SQUAT: "SQUAT", MILPRESS: "MILPRESS"
};

const buttons = [
    {id: 1, label: "합계", type: SC_TYPE.SUM}, 
    {id: 2, label: "벤치", type: SC_TYPE.BENCH},
    {id: 3, label: "데드", type: SC_TYPE.DEAD}, 
    {id: 4, label: "스쿼트", type: SC_TYPE.SQUAT},
    {id: 5, label: "밀프", type: SC_TYPE.MILPRESS}
]

function Fourmajor(props) {

    const category = '4-major';
    const [subcategory, setSubcategory] = useState(SC_TYPE.SUM);
    const [userList, setUserList] = useState([]);

    useEffect(() => {
        async function fetchUserList () {
            try {
                const res = await axios.get(SERVER_SEARCH_URL + `/api/user-ranking?category=${category}&subcategory=${subcategory}`);
                // 데이터에 순위 부여
                const dataWithRanking = res.data.map((data, rank) =>(
                    {...data, rank: rank+1}
                ));
                setUserList(dataWithRanking);
            } catch(error) {
                console.error(error);
            };
        } 

        fetchUserList();       
    }, [category, subcategory]);

    return (
        <div className='tier-list-container'>
            <div className='tier-font'>
                티어 순위표
            </div>
            <div className='subcategory-button-container'>
                {buttons.map((button) => (
                    <button className={`subcategory-button ${subcategory === button.type ? 'selected' : 'unselected'}`} key={button.id} onClick={()=>setSubcategory(button.type)}>{button.label}</button>    
                ))}
            </div>
            <Table data={userList} onSubmit={props.onSubmit}/>
        </div>
    );
}

export default Fourmajor;