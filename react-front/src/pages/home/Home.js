import React, { useState } from 'react';

import Search from '../../services/Search';
import Info from '../info/Info'
import './Home.css' 

function Home() {

    const [mode, setMode] = useState('HOME');
    const [userNm, setUserNm] = useState('');
    
    let content = null;

    function handleNameSearch(name) {
        setUserNm(name);
        setMode("INFO");
    }

    if(mode === 'HOME') {
        content = <Search handleNameSearch={handleNameSearch} />
    } else if(mode === 'INFO') {
        content = <Info userNm={userNm} />
    }

    return (
        <div>
            {content}
        </div>
    );
    
}

export default Home;