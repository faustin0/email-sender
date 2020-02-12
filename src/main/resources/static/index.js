$(function(){
    $('#email-form').on('submit', function(e){
        e.preventDefault();
        $.ajax({
            url: "/mails/new",
            type: 'POST',
            data: $('#email-form').serialize(),
            success: function(data){
                 alert('successfully submitted')
            },
            function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    });
});