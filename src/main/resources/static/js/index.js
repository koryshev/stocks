$(document).ready(function () {

    function showMessage(message) {
        document.querySelector('.mdl-js-snackbar').MaterialSnackbar.showSnackbar({
            message: message,
            timeout: 3000
        });
    }

    function loadStocks() {
        $.ajax({
            url: "api/stocks",
            method: "GET",
            contentType: "application/json"
        }).done(function (data) {
            $(".mdl-data-table tbody tr").remove();
            $.each(data, function (i, item) {
                $("<tr id=" + item.id + ">").append(
                    $("<td class='mdl-data-table__cell--non-numeric cell-stock-name'>").text(item.name),
                    $("<td>").text(item.currentPrice),
                    $("<td class='mdl-data-table__cell--non-numeric'>").text(item.createdDate),
                    $("<td class='mdl-data-table__cell--non-numeric'>").text(item.lastUpdate),
                    $("<td class='mdl-data-table__cell--non-numeric'>").html(
                        "<button class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent update-button'>" +
                        "<i class='material-icons'>edit</i></button>")
                ).appendTo(".mdl-data-table");
            });
            showMessage("The stock list has been successfully fetched from the server (" + data.length + " in total)");
        }).fail(function (data) {
            showMessage(JSON.stringify(data.responseJSON, null, 4));
        });
    }

    $(".add-button").click(function () {
        $("#add-form")[0].reset();
        $(".add-dialog, .overlay").show();
    });

    $(".add-button-submit").click(function () {
        $.ajax({
            url: "api/stocks",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "name": $("#add-name").val(),
                "currentPrice": $("#add-price").val()
            })
        }).done(function () {
            showMessage("Stock has been successfully created");
            $(".dialog, .overlay").hide();
            loadStocks();
        }).fail(function (data) {
            showMessage(JSON.stringify(data.responseJSON, null, 4));
        });
    });

    $(".mdl-data-table tbody").on("click", ".update-button", function () {
        $("#update-form")[0].reset();

        var tr = $(this).parents("tr");
        $("#update-name").val(tr.children('td.cell-stock-name').text());
        $("#update-form").data("id", tr[0].id);

        $(".update-dialog, .overlay").show();
    });

    $(".update-button-submit").click(function () {
        $.ajax({
            url: "api/stocks/" + $("#update-form").data("id"),
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify({
                "currentPrice": $("#update-price").val()
            })
        }).done(function () {
            showMessage("Stock has been successfully updated");
            $(".dialog, .overlay").hide();
            loadStocks();
        }).fail(function (data) {
            showMessage(JSON.stringify(data.responseJSON, null, 4));
        });
    });

    $(".add-button-cancel, .update-button-cancel").click(function () {
        $(".dialog, .overlay").hide();
    });

    loadStocks();
});