import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

function Info(props) {
    const { query } = useParams();
    const [searchResult, setSearchResult] = useState('');

    useEffect(() => {
        async function axiosData(e) {
            try {
                const res = await axios.get(`/api/search?query=${encodeURIComponent(query)}`);
                const data = res.data.userNm;
                setSearchResult(data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }

        axiosData();
    }, [query]);

    return (
        <div>
            <h2>검색결과</h2>
            <p>{searchResult}</p>
        </div>
    );

}

export default Info;