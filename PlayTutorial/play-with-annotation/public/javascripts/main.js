jQuery(document).ready(function () {
    $('.btnDel').click(function () {
        var urlToDelete = $(this).attr("attrUrl");
        del(urlToDelete);
    });
});


function del(urlToDelete) {

    $.ajax({
        url: urlToDelete,
        type: 'DELETE',
        beforeSend : function(jqXHR, settings) {
            jqXHR.setRequestHeader("x-csrftoken", $('input[name="csrfToken"]').val());
        },

        success: function (result) {
            location.reload();
        }
    });
}