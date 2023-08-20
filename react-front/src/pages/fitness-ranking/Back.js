import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from './table/Table';
import './FitnessRank.css';

function Back(props) {
    const category = 'back';
    const [FitnessList, setFitnessList] = useState([]);

    useEffect(() => {
        async function fetchFitnessList () {
            try {
                const res = await axios.get(`/api/fitness-ranking?category=${category}&subcategory=`);
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
            <div style={{'height': '50px'}}></div>
            <Table data={FitnessList} />

        </div>
    );
}

export default Back;