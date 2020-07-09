function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}


window.onload = function () {
    let emailValidation = document.getElementById("emailValidation");
    let emailError = document.getElementById("email-Error");
    let emailField = document.getElementById("email");

    if (emailValidation.textContent == "open") {

        $('#myModalRegister').modal('show');
        $("#email").focus();
        emailField.scrollIntoView();
        emailError.style.display = "block";
    }
};
