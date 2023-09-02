import './Mypage.css'

function adminSection(){
    if(sessionStorage.getItem('auth') === "ADMIN")  {
        return <a className='Site' href='/mypage/changeSite'>사이트 내 데이터 변경</a>
    }
    else{
        return <a className='auth' href='*'>관리자 권한 받기</a>
    }
}
function Mypage(props) {
    return(
        <div>
            <a className='Info' href='/mypage/changeInfo'>내 정보 변경</a>
            {adminSection()}
        </div>
    );
}

export default Mypage;