import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

const SC_TYPE = {
    SUM: "SUM", PUSHUP: "PUSHUP", PULLUP: "PULLUP", DIPS: "DIPS",
    SITUP: "SITUP", BARESQUAT: "BARESQUAT", BURPEE: "BURPEE", 
    PLANK: "PLANK", BRIDGE: "BRIDGE"
};

const buttons = [
    {id: 1, label: "합계", type: SC_TYPE.SUM}, 
    {id: 2, label: "푸쉬업", type: SC_TYPE.PUSHUP},
    {id: 3, label: "풀업", type: SC_TYPE.PULLUP}, 
    {id: 4, label: "딥스", type: SC_TYPE.DIPS},
    {id: 5, label: "윗몸일으키기", type: SC_TYPE.SITUP},
    {id: 6, label: "맨몸스쿼트", type: SC_TYPE.BARESQUAT},
    {id: 7, label: "버피", type: SC_TYPE.BURPEE},
    {id: 8, label: "플랭크", type: SC_TYPE.PLANK},
    {id: 9, label: "브릿지", type: SC_TYPE.BRIDGE},
]

function Barebody(props) {
    const category = 'bare-body';
    const [subcategory, setSubcategory] = useState(SC_TYPE.SUM);
    const [userList, setUserList] = useState([]);

    useEffect(() => {
        async function fetchUserList () {
            try {
                const res = await axios.get(`/api/user-ranking?category=${category}&subcategory=${subcategory}`);
                // 데이터에 순위 부여
                const dataWithRanking = res.data.map((data, rank) =>(
                    {...data, rank: rank+1}
                ));
                setUserList(dataWithRanking);
            } catch(error) {
                console.error(error);
            };
        } 

        if(category && subcategory) {
            fetchUserList();  
        }
    }, [category, subcategory]);

    return (
        <div className='tier-list-container'>
            <div className='subcategory-button-container'>
                {buttons.map((button) => (
                    <button className='subcategory-button' key={button.id} onClick={()=>setSubcategory(button.type)}>{button.label}</button>    
                ))}
            </div>
            <Table data={userList} />
        </div>
    );
}

export default Barebody;