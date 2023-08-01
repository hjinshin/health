import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './UserRank.css';

function Fourmajor(props) {
    const category = '4-major';
    const [userList, setUserList] = useState([]);

    useEffect(() => {
        async function fetchUserList () {
            try {
                const res = await axios.get(`/api/user-ranking?category=${category}&subcategory=`);
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
    }, []);

    return (
        <div className='tier-list-container'>
            <div style={{'height': '50px'}}></div>
            <Table data={userList} />
        </div>
    );
}

export default Fourmajor;