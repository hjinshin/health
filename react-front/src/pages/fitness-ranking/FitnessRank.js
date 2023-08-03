import React from 'react';
import { Link, Routes, Route  } from 'react-router-dom';

import './FitnessRank.css';
import Chest from './Chest'
import Back from './Back';
import Lower from './Lower';
import Shoulder from './Shoulder';
import Stomach from './Stomach';

function FitnessRank() {

    return (
        <div className='fitness-ranking-container'>
            <div>
                <Link to={'/fitnessRank/chest'}>
                    <button className='details_style' >가슴</button>
                </Link>
                <Link to={'/fitnessRank/back'}>
                    <button className='details_style'>등</button>
                </Link>
                <Link to={'/fitnessRank/lower'}>
                    <button className='details_style'>하체</button>
                </Link>
                <Link to={'/fitnessRank/shoulder'}>
                    <button className='details_style'>어깨</button>
                </Link>
                <Link to={'/fitnessRank/stomach'}>
                    <button className='details_style'>복부</button>
                </Link>
            </div>
            <Routes>
                <Route path='/chest' element={<Chest />}/>
                <Route path='/back' element={<Back />}/>
                <Route path='/lower' element={<Lower />}/>
                <Route path='/shoulder' element={<Shoulder />}/>
                <Route path='/stomach' element={<Stomach />}/>
            </Routes>
        </div>
    );
}

export default FitnessRank;