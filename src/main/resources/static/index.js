$( document ).ready(function() {
    console.log( "ready!" );
    sendMailEventHandler()
});

function sendMailEventHandler(){
    $('#email-form').on('submit', function(e){
        e.preventDefault();

        const toSend = Object.fromEntries($('#email-form').serializeArray().map(formEntry=> {
            return [formEntry.name, formEntry.value];
        }));

        $.ajax({
            contentType: "application/json; charset=UTF-8",
            url: "/api/mails",
            type: 'POST',
            data: JSON.stringify(toSend),
            success: function(data){
                 alert('successfully submitted')
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(JSON.stringify(xhr));
                console.log(thrownError);
            }
        });
    });
}