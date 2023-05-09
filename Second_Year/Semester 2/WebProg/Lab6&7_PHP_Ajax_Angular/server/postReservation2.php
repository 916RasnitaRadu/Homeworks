<?php
include 'dbConnection.php';
header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, X-Requested-With");

$reservation = json_decode(file_get_contents("php://input"), true);
$from = $reservation['check_in'];
$to = $reservation['check_out'];
$room_id = $reservation['roomID'];



$query = "INSERT INTO Reservations (`roomID`, `check_in`, `check_out`)
              VALUES (?, ?, ?)";

$stmt = $con->prepare($query);
$stmt->bind_param("iss", $room_id,$from,$to);

$result = $stmt->execute();


if ($result === true) {
    echo json_encode("New record created successfully");
} else {
    echo json_encode("Oops...something went wrong. Here is the sql: " . $query);
}

mysqli_close($con);
