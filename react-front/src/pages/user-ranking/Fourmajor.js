import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';
const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;

function Fourmajor(props) {
    const category = '4-major';
    const [subcategory, setSubcategory] = useState("SUM");
    const [userList, setUserList] = useState([]);
    const [buttons, setButtons] = useState([]);

    useEffect(() => {
        async function fetchSubCategories() {
            let tempButton = [{id: 1, label: "합계", type: "SUM"}];
            try {
                const res = await axios.get(SERVER_SEARCH_URL + '/api/subcategory?cid=FOURMAJOR');
                res.data.forEach((subcategory, index) => {
                    tempButton.push({
                        id: tempButton.length + 1,
                        label: subcategory.exerciseName,
                        type: subcategory.eid
                    });
                });
                console.log(tempButton);
                setButtons(tempButton);
            } catch(error) {
                console.error(error);
            };
        }
        if(buttons.length === 0) 
            fetchSubCategories();
    }, [buttons]);

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

export default Fourmajor;