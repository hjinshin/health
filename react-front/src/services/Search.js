import React from 'react';
import axios from 'axios';

import './Search.css'
import bodybuildingIcon from '../images/Bodybuilding-icon.png'

const SERVER_SEARCH_URL = 'http://localhost:8080/api/search';

function Search({handleNameSearch}) {

    async function onSubmit(e) {
        e.preventDefault();
        
        const name = e.target.search.value;
        if(name !== "" && name !== null) {
            const res = await axios.get(SERVER_SEARCH_URL + "/" + name);
            handleNameSearch(res.data);
        }
        
    }

    return (
        <div>
            <div className='banner'>
                <img className='bodybuilding-icon' src={bodybuildingIcon} alt='BodyBUilding-Icon' />
            </div>
            <div className='search-container'>
                <div>
                    <form className='search' onSubmit={onSubmit}>
                        <label className='label' htmlFor='searchHome'>Search</label>
                        <div className='  '>
                            <input 
                                className='search-bar'
                                type='text'
                                placeholder='사용자명...'
                                name='search'
                                id='searchHome'
                                />                                    
                        </div>
                        <button className='button' type='submit'>검색</button>
                    </form>
                </div>
            </div>            
        </div>

    );

}

export default Search;