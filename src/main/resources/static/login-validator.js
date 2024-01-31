function validate() {
    var login = document.getElementById("login");
    var password = document.getElementById("password");
    var info = document.getElementById("info");

    var loginRegex = /^[a-z0-9]{5,}$/;
    var passwordRegex = /^([\w]{4,}\d[\w]*)?([\w]{3,}\d[\w]+)?([\w]{2,}\d[\w]{2,})?([\w]+\d[\w]{3,})?([\w]*\d[\w]{4,})?$/;


    var result = true;
    var infoText = "";

    if(!loginRegex.test(login.value)) {
        login.style.background = "#a8beff";
        infoText = infoText + "Niepoprawny login (min 5 znaków)<br>";
        result = false;
    } else {
        login.style.background = null;
    }
    if(!passwordRegex.test(password.value)) {
        password.style.background = "#a8beff";
        infoText = infoText + "Niepoprawne haslo (min 5 znaków)<br>";
        result = false;
    } else {
        password.style.background = null;
    }
    info.innerHTML = infoText;

    return result;
}
