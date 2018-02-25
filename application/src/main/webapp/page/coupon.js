/**
 * Created by huaying on 25/02/2018.
 */

$(document).ready(function () {
    setUp(
        indexAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );

    initUI();
    getPoints();
    getCoupons();
});

function initUI() {
    document.getElementById("pointRules").innerHTML = pointRules;
    document.getElementById("couponRules").innerHTML = couponRules;
}

function getCoupons() {
    onLoading();
    $.ajax({
        type : 'GET',
        url : couponEndpoint + "/active",
        headers : {
            Authorization : getToken()
        },
        success : function(response) {
            console.info(response);
            let data = JSON.parse(response);
            let length = data.coupons.length;

            if(length === 0) {
                document.getElementById("coupons").innerHTML =
                    "<div class='couponWrapper'>" +
                    "<table>" +
                    "<tr>" +
                    "<td class='expireDate'>"+ noCoupon + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>";
            } else {
                for (let i = 0; i< length; i++) {
                    let date = new Date(data.coupons[i].expirationTime).toLocaleDateString();
                    document.getElementById("coupons").innerHTML +=
                        "<div class='couponWrapper'>" +
                        "<table>" +
                        "<tr>" +
                            "<th class='title'>"+ couponTitle + "</th>" +
                            "<th class='amount'>" + coupon50 + "</th>" +
                        "</tr>" +
                        "<tr>" +
                            "<td class='expireDate'>" + couponExpireDate + date + "</td> " +
                            "<td class='detail'>" + data.coupons[i].couponActivity + couponDetail + "</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>";
                }
            }
        },
        error : function(xhr, status, error) {
            console.log("error: " + error + ", status: " + status);
        },
        complete : function(jqxhr, status) {
            onLoaded();
        }
    });
}

function getPoints() {
    $.ajax({
        type : 'GET',
        url : pointEndpoint,
        headers : {
            Authorization : getToken()
        },
        success : function(response) {
            console.info(response);
            let data = JSON.parse(response);

        },
        error : function(xhr, status, error) {
            console.log("error: " + error + ", status: " + status + "<br>xhr: " + xhr);
        },
        complete : function(jqxhr, status) {
        }
    });
}

function onLoading() {
    showProgressBar();
    document.getElementById("content").hidden = true;
}

function onLoaded() {
    hideProgressBar();
    document.getElementById("content").hidden = false;
}
