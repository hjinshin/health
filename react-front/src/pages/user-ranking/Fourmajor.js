import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

const SC_TYPE  = {
    SUM: "SUM", BENCH: "BENCH", DEAD: "DEAD", 
    SQUAT: "SQUAT", MILPRESS: "MILPRESS"
};

const buttons = [
    {id: 1, label: "합계", type: SC_TYPE.SUM}, 
    {id: 2, label: "벤치프레스", type: SC_TYPE.BENCH},
    {id: 3, label: "데드리프트", type: SC_TYPE.DEAD}, 
    {id: 4, label: "스쿼트", type: SC_TYPE.SQUAT},
    {id: 5, label: "밀리터리프레스", type: SC_TYPE.MILPRESS}
]

function Fourmajor(props) {
    const category = '4-major';
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

        fetchUserList();       
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

export default Fourmajor;