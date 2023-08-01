import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

function Barebody(props) {
    const category = 'bare-body';
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
                <button className='subcategory-button' onClick={()=>setSubcategory('종목1')}>맨몸1</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('종목2')}>맨몸2</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('종목3')}>맨몸3</button>
            </div>
            <Table data={userList} />
        </div>
    );
}

export default Barebody;