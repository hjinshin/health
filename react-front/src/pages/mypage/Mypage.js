import './Mypage.css'

function adminSection(){
    if(sessionStorage.getItem('auth') === "ADMIN")  {
        return <a className='Site' href='/mypage/changeSite'>사이트 내 데이터 변경</a>
    }
}
function Mypage(props) {
    return(
        <div>
            <div className='infohello'> 안녕하세요. HEALTH.GG에 자신의 정보를 등록하세요</div>
            <a className='Info' href='/mypage/changeInfo'>내 정보 등록/변경</a>
            {adminSection()}
        </div>
    );
}

export default Mypage;