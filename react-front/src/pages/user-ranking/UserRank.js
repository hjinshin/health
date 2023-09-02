import React, { useState } from 'react';
import { Link, Routes, Route  } from 'react-router-dom';

import Fourmajor from './Fourmajor'
import Freestyle from './Freestyle';
import Barebody from './Barebody';
import './UserRank.css';


function UserRank({onSubmit}) {
    const [category, setCateogry] = useState('4-major');

    return (
        <div className='user-ranking-container'>
             <div className='category-button-container'>
                <Link className='caterogy-link' to={'/userRank/4-major'}>
                    <button className={`category-button ${category === '4-major' ? 'selected' : 'unselected'}`} onClick={()=>{setCateogry('4-major')}} value={true}>4대운동</button>
                </Link>
                <Link className='caterogy-link' to={'/userRank/freestyle'}>
                    <button className={`category-button ${category === 'freestyle' ? 'selected' : 'unselected'}`} onClick={()=>{setCateogry('freestyle')}}>자유운동</button>    
                </Link>
                <Link className='caterogy-link' to={'/userRank/bare-body'}>
                    <button className={`category-button ${category === 'bare-body' ? 'selected' : 'unselected'}`} onClick={()=>{setCateogry('bare-body')}}>맨몸운동</button>
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