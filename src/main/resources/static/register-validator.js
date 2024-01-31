function validate() {
    var name = document.getElementById("name");
    var surname = document.getElementById("surname");
    var login = document.getElementById("login");
    var password = document.getElementById("password");
    var password2 = document.getElementById("password2");
    var info = document.getElementById("info");

    var loginRegex = /^[a-z0-9]{5,}$/;
    var nameRegex = /^[A-Z]{1}[a-z]{2,}$/;
    var surnameRegex = /^[A-Z]{1}[a-z]+$/;
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

    if(!nameRegex.test(name.value)) {
        name.style.background = "#a8beff";
        infoText = infoText + "Niepoprawne imie (min 3 znaki, wielka litera na początku)<br>";
        result = false;
    } else {
        name.style.background = null;
    }
if(!surnameRegex.test(surname.value)) {
        surname.style.background = "#a8beff";
        infoText = infoText + "Niepoprawne nazwisko (min 2 znaki, wielka litera na początku)<br>";
        result = false;
    } else {
        surname.style.background = null;
    }

    if(!passwordRegex.test(password.value)) {
        password.style.background = "#a8beff";
        infoText = infoText + "Niepoprawne haslo (min 5 znaków)<br>";
        result = false;
    } else {
        password.style.background = null;
    }

    if(password.value != password2.value) {
        password2.style.background = "#a8beff";
        infoText = infoText + "Niepoprawnie powtórzone hasło<br>";
        result = false;
    } else {
        password2.style.background = null;
    }

    info.innerHTML = infoText;

    return result;
}
