let creditCard  = document.querySelector("#credit");
creditCard.addEventListener("click", function () {
    let cardInputs = document.getElementById("cardDisplay");
    let paypalInput = document.getElementById("paypalDisplay")
    cardInputs.style.display = "inline";
    paypalInput.style.display = "none";
})


let paypal = document.querySelector("#paypal");
paypal.addEventListener("click", function(){
    let cardInputs = document.getElementById("cardDisplay");
    let paypalInput = document.getElementById("paypalDisplay")
    cardInputs.style.display = "none";
    paypalInput.style.display = "inline";
})