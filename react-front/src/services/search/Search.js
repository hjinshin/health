import React, {useState, useEffect} from 'react';

import './Search.css'
import bodybuildingIcon from '../../images/Bodybuilding-icon.png'
import searchIcon from '../../images/search.png'

function Search({onSubmit}) {
    const [searchTerm, setSearchTerm] = useState('');

    // 브라우저의 뒤로가기 이벤트 감지
    useEffect(() => {
        const handlePopstate = () => {
            setSearchTerm('');
        };

        window.addEventListener('popstate', handlePopstate);

        return () => {
            window.removeEventListener('popstate', handlePopstate);
        };
    }, []);

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
                        <label className='label' htmlFor='searchHome'>HEALTH.GG</label>
                        <div className='  '>
                            <input 
                                className='search-bar'
                                type='text'
                                placeholder='사용자명...'
                                name='search'
                                id='searchHome'
                                value={searchTerm}
                                onChange={(e)=>setSearchTerm(e.target.value)}
                                />                                    
                        </div>
                        <button className='button' type='submit'>
                            <img className='search-icon' src={searchIcon} alt='BodyBUilding-Icon' />
                        </button>
                    </form>
                </div>
            </div>            
        </div>

    );

}

export default Search;