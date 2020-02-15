$( document ).ready(function() {
   // getAndPopulateMailTable()
   populateTable("/api/mails");
});

function populateTable(endpoint){
    $('#mailTable').bootstrapTable({
        url: endpoint,
    })
}

function getAndPopulateMailTable(){
        $.ajax({
            contentType: "application/json; charset=UTF-8",
            url: "/api/mails",
            type: 'GET',
            success: function(mailsData){
                $('#mailTable').bootstrapTable({
                    data: mailsData
                });
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr);
                console.log(thrownError);
            }
        });
    }
