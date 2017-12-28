/**
 * Created by huaying on 12/12/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(contactAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );
});

