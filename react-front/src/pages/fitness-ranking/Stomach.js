import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './FitnessRank.css';

const SERVER_SEARCH_URL = process.env.REACT_APP_SPRINGBOOT_BACK_URL;

function Stomach(props) {
    const category = 'stomach';
    const [FitnessList, setFitnessList] = useState([]);

    useEffect(() => {
        async function fetchFitnessList () {
            try {
                const res = await axios.get(SERVER_SEARCH_URL + `/api/fitness-ranking?category=${category}&subcategory=`);
                // 데이터에 순위 부여
                const dataWithRanking = res.data.map((data, rank) =>(
                    {...data, rank: rank+1}
                ));
                setFitnessList(dataWithRanking);
            } catch(error) {
                console.error(error);
            };
        }

        fetchFitnessList();
    }, []);

    return (
        <div className='fi-tier-list-container'>
            <div className='fi-head'>티어순위표</div>
            <div style={{'height': '20px'}}></div>
            <Table data={FitnessList} />
        </div>
    );
}

export default Stomach;