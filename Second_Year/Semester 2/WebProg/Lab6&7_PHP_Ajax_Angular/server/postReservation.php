<?php
include 'dbConnection.php';
header("Access-Control-Allow-Origin: *");


$check_in = $_POST['check-in'];
$check_out = $_POST['check-out'];
$room_id = cleanUserInput($_POST['roomID'], $con); // we clean the user input of the room_id because here we have the possibility that the input may cause problems

echo $check_in, $check_out, $room_id, "\n";
$query = "INSERT INTO reservations(`roomID`,`check_in`,`check_out`) VALUES ('$room_id', '$check_in', '$check_out')";

$result = mysqli_query($con, $query);

if ($result === TRUE) {
    echo "New record created successfully";
} else {
    echo "ERROR: " . "<br>" . $con->error;
}

mysqli_close($con);
