import React from 'react';

import bodybuildingIcon from '../../images/Bodybuilding-icon.png'
import Search from '../../services/Search';
import './Home.css' 

function Home() {
    return (
        <div>
            <div className='banner'>
                <img className='bodybuilding-icon' src={bodybuildingIcon} alt='BodyBUilding-Icon' />
            </div>
            <Search></Search>
        </div>
    );
    
}

export default Home;