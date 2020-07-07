function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}



jQuery(document).ready(function($){
    $("input[name=mobile]").attr("maxlength", "10");
    $("input[name=mobile]").attr("minlength", "10");
});

