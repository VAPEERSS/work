
		const signUpBtn = document.getElementById("signUp");
		const signInBtn = document.getElementById("signIn");
		const container = document.querySelector(".container");

		signUpBtn.addEventListener("click", () => {
			container.classList.add("right-panel-active");
		});
		signInBtn.addEventListener("click", () => {
			container.classList.remove("right-panel-active");
		});

	

function joinForm() {
    var name = document.forms[0]["name"].value;
    var email = document.forms[0]["email"].value;
    var pw = document.forms[0]["pw"].value;
    var cpw = document.forms[0]["cpw"].value;
    var gender = document.querySelector('input[name="gender"]:checked');
    var birthDate = document.forms[0]["birthDate"].value;
    var mobile = document.forms[0]["mobile"].value;

    if (name === "" || email === "" || pw === "" || cpw === "" || !gender || birthDate === "" || mobile === "") {
        alert("모든 입력란을 작성해주세요.");
        return false; // 양식 제출 방지
    }
    
    if(pw !== cpw){
       document.forms[0]["pw"].value = "";
        document.forms[0]["cpw"].value = "";
       alert("비밀번호를 정확히 입력해주세요.");
       document.forms[0]["pw"].focus(); // pw input으로 포커스 이동
       return false;
    }
    
    
    // 특수문자 포함 여부 검증
    var specialChars = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
    if (!specialChars.test(pw)) {
        alert("비밀번호에 특수문자를 포함해야 합니다.");
        document.forms[0]["pw"].focus(); // pw input으로 포커스 이동
        return false;
    }
    
 
    // 이메일 형식 검증
    var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    if (!emailRegex.test(email)) {
        alert("유효한 이메일 주소를 입력해주세요.");
        document.forms[0]["email"].focus(); // email input으로 포커스 이동
        return false;
    }
    
    alert("가입이 완료되었습니다. 환영합니다!");

    return true;
}

function checkDuplicateEmail(){
   var email = document.forms[0]["email"].value;
    var emailResult = document.getElementById("emailResult");

    if (email === "") {
        alert("이메일을 입력하세요.");
        return;
    }

    // 이메일 형식 검증
    var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    if (!emailRegex.test(email)) {
        alert("유효한 이메일 주소를 입력해주세요.");
        document.forms[0]["email"].focus(); // email input으로 포커스 이동
        return;
    }
    
    // 서버로 Ajax 요청을 보냅니다.
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/checkEmailDuplicate", true); // 서버의 API 엔드포인트를 지정하세요.

    // 서버 응답을 처리합니다.
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // 서버에서 받은 응답을 파싱하여 처리합니다.
                var response = JSON.parse(xhr.responseText);
                if (response.duplicate) {
                    // 중복된 이메일이 존재하는 경우
                    emailResult.innerHTML = "중복된 이메일입니다.";
                } else {
                    // 중복된 이메일이 없는 경우
                    emailResult.innerHTML = "사용 가능한 이메일입니다.";
                }
            } else {
                // 서버 요청에 실패한 경우
                emailResult.innerHTML = "서버 요청에 실패했습니다.";
            }
        }
    };

    // 요청을 보냅니다.
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify({ email: email }));

}