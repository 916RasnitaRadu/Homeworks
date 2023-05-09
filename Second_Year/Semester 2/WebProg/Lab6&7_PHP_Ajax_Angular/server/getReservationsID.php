<?php
    include 'dbConnection.php';
    header("Access-Control-Allow-Origin: *");

    $query = "SELECT id FROM reservations";
    $result = mysqli_query($con, $query);

    $echoArray=Array();

    if ($result->num_rows > 0) {
        while ($row = mysqli_fetch_assoc($result))
        {
            array_push($echoArray, $row["id"]);
        }
    }
    else {
        echo "0 results"; return;
    }
    echo json_encode($echoArray);
    mysqli_close($con);
