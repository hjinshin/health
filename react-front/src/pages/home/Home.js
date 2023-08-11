import React from 'react';
import { Route, Routes } from 'react-router-dom';

import Search from '../../services/search/Search';
import Info from '../info/Info'
import './Home.css' 


function Home() {
    async function handleSearchSubmit(searchTerm) {
        if(searchTerm !== "" && searchTerm !==null) {

            window.location.href = '/search/' + searchTerm;
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