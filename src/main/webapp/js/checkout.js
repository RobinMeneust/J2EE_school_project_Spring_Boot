let elements;
let globalClientSecret = null;
let stripe = null;
let amountToBePaid= null;
let doAbort = false;
let customerData = null;

let publishableStripeKeyPromise = fetch("get-stripe-publishable-key").then((response) => {
    return response.json();
}).then((data) => {
    if("stripe-publishable-key" in data) {
        return data["stripe-publishable-key"];
    } else {
        return "";
    }
});

let customerDataPromise = fetch("session/customer").then((response) => {
    return response.json();
}).then((data) => {
    let result = {"first-name":"","last-name":"","email":""}
    if(!("user" in data)) {
        return result;
    }

    for(let key in ["first-name","last-name","email"]) {
        if(key in data["user"]) {
            result[key] = data["user"][key].trim();
        }
    }
    return result;
});

const urlParams = new URLSearchParams(window.location.search);

if(!urlParams.has("order-id")) {
    showMessage("Invalid order ID");
    doAbort = true;
}

let amountToBePaidPromise = fetch("get-order-total?order-id="+urlParams.get("order-id")).then((response) => {
    return response.json();
}).then((data) => {
    if("total" in data) {
        return data["total"];
    } else {
        return "";
    }
});


initialize();


// Fetches a payment intent and captures the client secret
async function initialize() {
    try {
        if (doAbort) return;
        stripe = Stripe(await publishableStripeKeyPromise);
        amountToBePaid = await amountToBePaidPromise;
        customerData = await customerDataPromise;

        $("#amount-to-be-paid").text(amountToBePaid.toFixed(2));

        const response = await fetch(`create-payment-intent?order-id=` + urlParams.get("order-id"));
        const {clientSecret} = await response.json();
        globalClientSecret = clientSecret;

        const appearance = {
            theme: 'stripe',
        };
        elements = stripe.elements({appearance, clientSecret});

        const paymentElement = elements.create("card", {
            hidePostalCode: true
        });
        paymentElement.mount("#payment-element");
        checkStatus();
        document.querySelector("#payment-form").addEventListener("submit", handleSubmit);

        $('#main-content').removeClass('d-none');
    } catch (e) {
        console.error("Can't connect to Stripe");
        $('#error-alert-box').addClass('show').addClass('d-none');

    }
}

async function handleSubmit(e) {
    console.log("event")
    e.preventDefault();
    if(doAbort) return;
    setLoading(true);

    const { paymentIntent, error } = await stripe.confirmCardPayment(globalClientSecret, {
        payment_method: {
            card: elements.getElement('card'),
            billing_details: {
                name: customerData["first-name"] + customerData["last-name"],
                email: customerData.email,
            },
        },
    });


    if (error) {
        console.error(error);
        if (error.type === "card_error" || error.type === "validation_error") {
            showMessage(error.message);
        } else {
            showMessage("An unexpected error occurred.");
        }
    } else if (paymentIntent.status === 'succeeded') {
        window.location.href = `confirm-payment?order-id=`+urlParams.get("order-id");
    } else {
        console.log('Payment is not yet confirmed');
    }
    setLoading(false);
}

// Get payment intent status after submit
async function checkStatus() {
    if(doAbort) return;
    const clientSecret = new URLSearchParams(window.location.search).get(
        "payment_intent_client_secret"
    );

    if (!clientSecret) {
        return;
    }

    const { paymentIntent } = await stripe.retrievePaymentIntent(clientSecret);

    switch (paymentIntent.status) {
        case "succeeded":
            showMessage("Payment succeeded!");
            break;
        case "processing":
            showMessage("Your payment is processing.");
            break;
        case "requires_payment_method":
            showMessage("Your payment was not successful, please try again.");
            break;
        default:
            showMessage("Something went wrong.");
            break;
    }
}

// Display message or spinner

function showMessage(messageText) {
    const messageContainer = document.querySelector("#payment-message");

    messageContainer.classList.remove("visually-hidden");
    messageContainer.textContent = messageText;

    setTimeout(function () {
        messageContainer.classList.add("visually-hidden");
        messageContainer.textContent = "";
    }, 4000);
}

// Show a spinner on payment submission
function setLoading(isLoading) {
    if(doAbort) return;
    if (isLoading) {
        $(".spinner-pay-btn").each(function() {
            $(this).removeClass("visually-hidden");
        });
        $("#pay-btn").prop("disabled",true);
        $("#pay-btn-text").addClass("visually-hidden");
    } else {
        $(".spinner-pay-btn").each(function() {
            $(this).addClass("visually-hidden");
        });
        $("#pay-btn").prop("disabled",false);
        $("#pay-btn-text").removeClass("visually-hidden");
    }
}