/**
 * Created by huaying on 09/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(indexAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );
});

