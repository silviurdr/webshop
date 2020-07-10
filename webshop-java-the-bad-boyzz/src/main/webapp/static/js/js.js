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

// hide and show cat filters
let plusCat = document.querySelector(".categoryp");

let catFilters = document.getElementById("categories")
plusCat.addEventListener("click", function () {
    catFilters.style.display = "inline";
})



//hide and show supplier filters
let plusSup = document.querySelector(".supplierp");
let supFilters = document.getElementById("suppliers")
plusSup.addEventListener("click", function () {
    supFilters.style.display = "block";
})


const inputs = document.getElementsByTagName("input");
for(let i = 0; i < inputs.length; i++) {
    inputs[i].addEventListener("click" ,function() {

        if (inputs[i].checked === false) {
            inputs[i].checked = true;
        } else {
            if (inputs[i].checked === true) {
                inputs[i].checked = false;
            }
        }
    })
}

document.getElementById('search-bar').readOnly = true;