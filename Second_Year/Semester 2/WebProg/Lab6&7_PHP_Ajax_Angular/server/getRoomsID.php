<?php
include 'dbConnection.php';
header("Access-Control-Allow-Origin: *");

$query = "SELECT id from rooms";
$result = mysqli_query($con, $query);

$echoArray = array();
if ($result->num_rows > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($echoArray, $row["id"]);
    }
} else {
    echo "0 results";
    return;
}
echo json_encode($echoArray);
mysqli_close($con);
