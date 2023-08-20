import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

function Fourmajor(props) {
    const category = '4-major';
    const [subcategory, setSubcategory] = useState('sum');
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
                <button className='subcategory-button' onClick={()=>setSubcategory('sum')}>합계</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('bench')}>벤치</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('dead')}>데드</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('squat')}>스쿼트</button>
                <button className='subcategory-button' onClick={()=>setSubcategory('milpress')}>밀프</button>
            </div>
            <div style={{'height': '50px'}}></div>
            <Table data={userList} />
        </div>
    );
}

export default Fourmajor;