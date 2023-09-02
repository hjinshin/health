import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';
const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;
let buttons = [];

function FreeStyle(props) {
    const category = 'freestyle';
    const [subcategory, setSubcategory] = useState("SUM");
    const [userList, setUserList] = useState([]);

    useEffect(() => {
        async function fetchSubCategories() {
            try {
                const res = await axios.get(SERVER_SEARCH_URL + '/api/subcategory?cid=FREESTYLE');
                buttons.push({id: 1, label: "합계", type: "SUM"});
                res.data.forEach((subcategory, index) => {
                    buttons.push({
                        id: buttons.length + 1,
                        label: subcategory.exerciseName,
                        type: subcategory.eid
                    });
                });
            } catch(error) {
                console.error(error);
            };
        }
        if(buttons.length === 0)
            fetchSubCategories();
    }, []);

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
            <div className='box'>
                <div className='tier-font'>
                    티어 순위표
                </div>
                <div className='subcategory-button-container'>
                    <div className='subcategory-bottons'>
                        {buttons.map((button) => (
                            <button className={`subcategory-button ${subcategory === button.type ? 'selected' : 'unselected'}`} key={button.id} onClick={()=>setSubcategory(button.type)}>{button.label}</button>        
                        ))}
                    </div>
                </div>
            </div>
            <Table data={userList} onSubmit={props.onSubmit}/>
        </div>
    );
}

export default FreeStyle;