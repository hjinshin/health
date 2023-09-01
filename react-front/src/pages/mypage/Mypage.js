import './Mypage.css'
function Mypage(props) {
    return(
        <div>
            <a className='Info' href='/mypage/changeInfo'>내 정보 변경</a>
            <a className='Site' href='/mypage/changeSite'>사이트 내 데이터 변경</a>
        </div>
    );
}

export default Mypage;