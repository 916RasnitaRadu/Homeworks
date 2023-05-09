<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Credentials');
header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

if (!extension_loaded('mysqli')) {
    die('mysqli extension not loaded');
}

// to prevent sql injections
function clean($data)
{
    $data = htmlspecialchars($data); //  function converts some predefined characters to HTML entities. ex: & (ampersand) becomes &amp;
    $data = stripslashes($data); // removes backslashes
    $data = trim($data);  // removes whitespace and other predefined characters from both sides of a string.
    return $data;
}

function cleanUserInput($userinput, $con)
{
    if (empty($userinput)) {
        return;
    }
    $userinput = clean($userinput);

    $userinput = mysqli_real_escape_string($con, $userinput);

    return $userinput;
}


$con = mysqli_connect("localhost", "root", "", "reservationsdb");

// if (!$con) {
//     echo "Could not connect..";
// }
// else {echo "Connected to the database!";}

$columns = ["capacity", "type", "hotel"];
$table_name = 'rooms';