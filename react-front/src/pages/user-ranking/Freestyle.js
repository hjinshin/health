import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from '../../services/table/Table';
import './UserRank.css';

function FreeStyle(props) {
    const category = 'freestyle';
    const [subcategory, setSubcategory] = useState('');
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
                <button className='subcategory-button' onClick={()=>setSubcategory('종목1')}>자유1</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('종목2')}>자유2</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('종목3')}>자유3</button>
            </div>
            <Table data={userList} />
        </div>
    );
}

export default FreeStyle;