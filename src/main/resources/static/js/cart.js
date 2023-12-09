const url = "edit-cart-item-quantity";

function addToCart(button, productId) {
    if(productId != null && !isNaN(productId) && productId>0) {
        const url = "add-to-cart?id="+productId;

        fetch(url, {
            method: 'GET'
        }).then((response) => {
            let errorAlertBox = $("#error-alert-box");
            if(response.ok) {
                response.json().then((data) => {
                    // Disable the button if the product has been added to the cart
                    if(button != null) {
                        button.setAttribute("disabled",true);
                        button.classList.add("btn-success");
                    }
                });
            } else {
                if(errorAlertBox.length) {
                    errorAlertBox.removeClass("invisible").addClass("visible");
                }
            }
        });
    }
}