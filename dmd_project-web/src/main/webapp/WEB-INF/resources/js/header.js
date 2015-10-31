/**
 * @author Timur Kasatkin
 * @date 29.10.15.
 * @email aronwest001@gmail.com
 */
$(document).ready(function () {
    $('.standard-logo img').attr('src', '/resources/images/logo1.png');
    $('.retina-logo img').attr('src', '/resources/images/logo1.png');

    $(".js-exit").click(function (event) {
        event.preventDefault();
        $('#js-exitForm').submit();
    });
});