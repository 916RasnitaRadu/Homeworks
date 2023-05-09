<?php
include 'dbConnection.php';
header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, X-Requested-With");

$id = file_get_contents("php://input");

$query = "DELETE FROM reservations WHERE id=?";

$stmt = $con->prepare($query);
$stmt->bind_param("i",$id);

$result = $stmt->execute();

if ($result === true) {
    echo json_encode("New record deleted successfully");
} else {
    echo json_encode("Oops...something went wrong. Here is the sql: " . $query);
}

mysqli_close($con);
