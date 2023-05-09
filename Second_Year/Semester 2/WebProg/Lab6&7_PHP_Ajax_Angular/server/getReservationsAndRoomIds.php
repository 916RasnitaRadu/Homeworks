<?php

include 'dbConnection.php';
header("Access-Control-Allow-Origin: *");

$query = "SELECT * FROM reservations";
$result = mysqli_query($con, $query);


$echoArray = array();
$echoArray["reservations"] = array();
$echoArray["roomIds"] = array();

$reservations = array();
$roomIds = array();

if ($result->num_rows > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($echoArray["reservations"], $row);
    }
}

$query = "SELECT ID FROM rooms";
$result = mysqli_query($con, $query);
$nr_rows = mysqli_num_rows($result);


if ($result->num_rows > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($echoArray["roomIds"], $row["ID"]);
    }
} else {
    return;
}

echo json_encode($echoArray);
mysqli_close($con);
