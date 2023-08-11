import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const SERVER_SEARCH_URL = 'http://localhost:8080';

function Info(props) {
    const { query } = useParams();
    const [searchResult, setSearchResult] = useState("");

    useEffect(() => {
        async function search(userNm) {
            const res = await axios.get(SERVER_SEARCH_URL+`/api/search?userNm=${encodeURIComponent(userNm)}`)
                                .catch((error) => {
                                    console.error(error);
                                });

            if(res.data.searchSuccess)
                setSearchResult(res.data.nickname);
            else
                setSearchResult("검색결과가 없습니다.");
        }
        search(query);

    }, [query, setSearchResult]);

    return (
        <div>
            <h2>검색결과</h2>
            {searchResult}
        </div>
    );

}

export default Info;