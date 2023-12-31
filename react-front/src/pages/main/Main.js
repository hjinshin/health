import React from 'react';
import { Route, Routes } from 'react-router-dom';

import Search from '../../services/search/Search';
import Info from '../info/Info'
import './Main.css'
import UserRank from "../user-ranking/UserRank";
import FitnessRank from "../fitness-ranking/FitnessRank";
import Mypage from "../mypage/Mypage";
import ChangeInfo from "../mypage/ChangeInfo";
import ChangeSite from "../mypage/ChangeSite";

function Home() {
    async function handleSearchSubmit(searchTerm) {
        if(searchTerm !== "" && searchTerm !==null) {
            window.location.href = '/search/' + searchTerm;
        }
    }

    return (
        <div className='main-container'>
            <Routes>
                <Route path='/' element={<Search onSubmit={handleSearchSubmit}/>} />
                <Route path='/userRank/*' element={<UserRank onSubmit={handleSearchSubmit}/>}/>
                <Route path='/fitnessRank/*' element={<FitnessRank />}/>
                <Route path='/mypage/*' element={<Mypage/>}/>
                <Route path='/search/:query' element={<Info/>} />
                <Route path='/mypage/changeInfo' element={<ChangeInfo/>} />
                <Route path='/mypage/changeSite' element={<ChangeSite />} />
            </Routes>
        </div>
    );
    
}

export default Home;