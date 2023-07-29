import React, { useEffect, useState } from 'react';
import './Search.css'

function Search() {

    const [message, setMessage] = useState([]);

    useEffect(() => {
        fetch("/hello")
            .then((response) => {
                return response.json();
            })
            .then(function (data) {
                setMessage(data);
            });
    }, []);

    return (
        <div className='search-container'>
            <div>
                <form className='search'>
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
    );

}

export default Search;