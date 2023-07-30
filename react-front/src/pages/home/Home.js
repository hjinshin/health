import React from 'react';
import { Route, Routes } from 'react-router-dom';
import axios from 'axios';

import Search from '../../services/Search';
import Info from '../info/Info'
import './Home.css' 


const SERVER_SEARCH_URL = 'http://localhost:8080';

function Home() {
    async function handleSearchSubmit(searchTerm) {
        if(searchTerm !== "" && searchTerm !==null) {
            const res = await axios.get(SERVER_SEARCH_URL+`/api/search?query=${encodeURIComponent(searchTerm)}`)
            
            window.location.href = '/search/' + res.data.userNm;
        }
    }

    return (
        <div>
            <Routes>
                <Route path='/' element={<Search onSubmit={handleSearchSubmit} />} />
                <Route path='/search/:query' element={<Info/>} />
            </Routes>
        </div>
    );
    
}

export default Home;