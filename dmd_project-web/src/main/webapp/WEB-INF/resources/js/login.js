/**
 * @author Timur Kasatkin
 * @date 29.10.15.
 * @email aronwest001@gmail.com
 */
//TODO fix
$(document).ready(function () {
    if ($("label[id$='-error-label']").filter(function (i, e) {
            return e.textContent.trim().length > 0
        }).length > 0) {
        $('#tab-register').click()
    }
});