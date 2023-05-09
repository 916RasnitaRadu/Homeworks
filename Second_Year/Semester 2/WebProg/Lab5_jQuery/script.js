let tableColumns = []; // table with {column name, order} pairs

(() => {
    // create event listener on click for column header
    $("#myTable").find("thead").find("tr").children().each((_index, elem) => {
        $(elem).on("click", headerClicked);
        tableColumns.push({text: $(elem).html(), order: "", id: _index + 1});
    });

    //create event listener on click for column footer
    $("#myTable").find("tfoot").find("tr").children().each((_index, elem) => {
        $(elem).on("click", footerClicked);
    });
})();

function headerClicked(event) {
    console.log(event.target.innerHTML);
    columnIndex = tableColumns.findIndex((elem) => elem.text == event.target.innerHTML);
    if (tableColumns[columnIndex].order == "asc")
        tableColumns[columnIndex].order = "desc";
    else
        tableColumns[columnIndex].order = "asc";
    sortTable(columnIndex, tableColumns[columnIndex].order, tableColumns[columnIndex].id);
}

function footerClicked(event) {
    columnIndex = $(event.target).index();
    switchColumns(columnIndex);
}

function isNumeric(n) {
    if (typeof n != "string") {
        return false;
    } else {
        return !isNaN(n) && !isNaN(parseFloat(n));
    }
}

function sortTable(columnIndex, order, id_index) {
    let table, rows, switching, i, x, y;
    table = $("#myTable").find("tbody");
    switching = true;
    while (switching) {
        switching = false;
        rows = $(table).find("tr");
        for (i = 0; i < rows.length - 1; i++) {
            x = $(rows[i]).find("td")[columnIndex];
            y = $(rows[i + 1]).find("td")[columnIndex];
            // if the values of a column are numeric, order them as numbers
            if (isNumeric($(x).html()) && isNumeric($(y).html())) {
                if ( Number($(x).html()) > Number($(y).html())) {
                        $(rows[i + 1]).after($(rows[i]));
                        switching = true;
                    }
            } else {
                if ($(x).html().toLowerCase() > $(y).html().toLowerCase()) {
                        $(rows[i + 1]).after($(rows[i]));
                        switching = true;
                    }
            }
        }
    }

}

function switchColumns(columnIndex) {
    let swapPosition;
    if (columnIndex === tableColumns.length - 2) {
        swapPosition = 0;
    } else {
        swapPosition = columnIndex + 2;
    }

    $("#myTable").find("tr").each((_index, elem) => {
        $(elem).children("th, td").eq(swapPosition)
            .replaceWith($(elem)
            .children("th, td")
            .eq(columnIndex)
            .after($(elem).children("th, td").eq(swapPosition).clone(true)));
    });

    [tableColumns[columnIndex], tableColumns[swapPosition]] = [tableColumns[swapPosition], tableColumns[columnIndex]];
}