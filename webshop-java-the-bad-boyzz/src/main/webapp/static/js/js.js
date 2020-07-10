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
document.getElementById('search-bar').readOnly = true;

jQuery(function(){
    if (localStorage.input) {
        showTable()
        let checks = JSON.parse(localStorage.input);
        jQuery(':checkbox').prop('checked', function(i) {
            return checks[i];
        });
    }
});

jQuery(':checkbox').on('change', function() {
    localStorage.input = JSON.stringify(jQuery(':checkbox').map(function() {
        return this.checked;
    }).get());
});

function showTable() {
    document.getElementById('categories').style.display = "block";
    document.getElementById('suppliers').style.display = "block";
    localStorage.setItem('show', 'true'); //store state in localStorage
}


