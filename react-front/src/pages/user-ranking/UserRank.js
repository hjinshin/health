import React from 'react';
import { Link, Routes, Route  } from 'react-router-dom';

import Fourmajor from './Fourmajor'
import Freestyle from './Freestyle';
import Barebody from './Barebody';
import './UserRank.css';

function UserRank({onSubmit}) {

    return (
        <div className='user-ranking-container'>
             <div className='category-button-container'>
                <Link className='caterogy-link' to={'/userRank/4-major'}>
                    <button className='category-button'>4대운동</button>
                </Link>
                <Link className='caterogy-link' to={'/userRank/freestyle'}>
                    <button className='category-button'>자유운동</button>    
                </Link>
                <Link className='caterogy-link' to={'/userRank/bare-body'}>
                    <button className='category-button'>맨몸운동</button>
                </Link>
            </div>
            <Routes>
                <Route path='/4-major' element={<Fourmajor onSubmit={onSubmit}/>} />
                <Route path='/freestyle' element={<Freestyle onSubmit={onSubmit}/>}/>
                <Route path='/bare-body' element={<Barebody onSubmit={onSubmit}/>}/>
            </Routes>
        </div>
    );
}

export default UserRank;