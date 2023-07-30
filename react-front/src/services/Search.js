import React, {useState} from 'react';

import './Search.css'
import bodybuildingIcon from '../images/Bodybuilding-icon.png'

function Search({onSubmit}) {
    const [searchTerm, setSearchTerm] = useState('');

    function handleSubmit(e) {
        e.preventDefault();
        onSubmit(searchTerm);
    }

    return (
        <div>
            <div className='banner'>
                <img className='bodybuilding-icon' src={bodybuildingIcon} alt='BodyBUilding-Icon' />
            </div>
            <div className='search-container'>
                <div>
                    <form className='search' onSubmit={handleSubmit}>
                        <label className='label' htmlFor='searchHome'>Search</label>
                        <div className='  '>
                            <input 
                                className='search-bar'
                                type='text'
                                placeholder='사용자명...'
                                name='search'
                                id='searchHome'
                                onChange={(e)=>setSearchTerm(e.target.value)}
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