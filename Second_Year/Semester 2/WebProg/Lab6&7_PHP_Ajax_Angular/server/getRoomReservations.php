<?php
include "dbConnection.php";
header("Access-Control-Allow-Origin: *");

    if(!isset($_GET['roomID']))
        $_GET['roomID'] = 1;
    $roomID = $_GET['roomID'];
    $query = "SELECT * FROM reservations WHERE roomID=$roomID";

    $result = mysqli_query($con, $query);

    $echoArray = Array();

    if($result->num_rows > 0){
		while($row = mysqli_fetch_assoc($result)){
			array_push($echoArray, $row);
		}
	} else {
		echo "0 results";
        return;
	}

	echo json_encode($echoArray);
    mysqli_close($con);
