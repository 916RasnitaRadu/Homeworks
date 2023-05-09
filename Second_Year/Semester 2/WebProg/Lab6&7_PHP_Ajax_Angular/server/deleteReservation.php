<?php
include 'dbConnection.php';
header("Access-Control-Allow-Origin: *");

//   $reservation_id = cleanUserInput($_POST['id'], $con);

$reservation_id = cleanUserInput($_POST['Reservation-id'], $con);
$query = "DELETE FROM reservations WHERE id='$reservation_id'";

$result = mysqli_query($con, $query);

if ($result === TRUE) {
  echo "New record deleted successfully";
} else {
  echo "Error: " . $con->error;
}
mysqli_close($con);
