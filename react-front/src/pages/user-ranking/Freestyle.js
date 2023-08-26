import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

const SC_TYPE  = {
        SUM: "SUM", DUMBPRESS: "DUMBPRESS", CHESTPRESS: "CHESTPRESS", 
        LATPULLDOWN: "LATPULLDOWN", SEATEDROW: "SEATEDROW", BARCURL: "BARCURL", 
        DUMBCURL: "DUMBCURL", LYINGEXTENSION: "LYINGEXTENSION", 
        CABLEPUSHDOWN: "CABLEPUSHDOWN", LEGRAISE: "LEGRAISE"
};

const buttons = [
    {id: 1, label: "합계", type: SC_TYPE.SUM}, 
    {id: 2, label: "덤벨프레스", type: SC_TYPE.DUMBPRESS},
    {id: 3, label: "체스트프레스", type: SC_TYPE.CHESTPRESS}, 
    {id: 4, label: "렛풀다운", type: SC_TYPE.LATPULLDOWN},
    {id: 5, label: "시티드다운", type: SC_TYPE.SEATEDROW},
    {id: 6, label: "바벨컬", type: SC_TYPE.BARCURL},
    {id: 7, label: "덤벨컬", type: SC_TYPE.DUMBCURL},
    {id: 8, label: "라잉익스텐션", type: SC_TYPE.LYINGEXTENSION},
    {id: 9, label: "케이블 푸쉬다운", type: SC_TYPE.CABLEPUSHDOWN},
    {id: 10, label: "레그프레스", type: SC_TYPE.LEGRAISE},
]

function FreeStyle(props) {
    const category = 'freestyle';
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
            <Table data={userList} onSubmit={props.onSubmit}/>
        </div>
    );
}

export default FreeStyle;