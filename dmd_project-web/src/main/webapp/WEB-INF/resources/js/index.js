/**
 * @author Timur Kasatkin
 * @date 31.10.15.
 * @email aronwest001@gmail.com
 */
$(document).ready(function () {
    var $input = $('input[name="keyWord"]');
    var $fullsearchForm = $('#fullsearch_form');

    $(window).keydown(preventSubmit);

    $fullsearchForm.on('keyup keypress', preventSubmit);

    $fullsearchForm.submit(function (event) {
        if (!inputValid()) {
            event.preventDefault();
            return false;
        }
    });

    function preventSubmit(event) {
        if (event.keyCode == 13 && !inputValid()) {
            event.preventDefault();
            return false;
        }
    }

    function inputValid() {
        return $input.val().trim().length != 0;
    }
});