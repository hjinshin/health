import React, { Component } from 'react';

import bodybuildingIcon from '../../images/Bodybuilding-icon.png'
import Search from '../../services/Search';
import './Home.css' 

class Home extends Component {
    render() {
        return (
            <div>
                <div className='banner'>
                    <img className='bodybuilding-icon' src={bodybuildingIcon} alt='BodyBUilding-Icon' />
                </div>
                <Search></Search>
            </div>
        );
    }
}

export default Home;