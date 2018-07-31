<?php
$eventname = filter_input(INPUT POST, 'field1');
$elocation= filter_input(INPUT POST, 'field2');
$edate = filter_input(INPUT POST, 'field3');
$etime = filter_input(INPUT POST, 'field4');
$eorganizers = filter_input(INPUT POST, 'field5');

if(!empty($eventname)){
if(!empty($elocation)){
$host = "localhost";
$dbusername = "root";
$dbpassword = "";
$dbname = "madDiscovery";


//creating a connection
$conn = new mysqli ($host, $dbusername, $dbpassword, $dbname);

if (mysqli_connect_error()){
die('Connect Error ('.mysqli_connect_errno() .') '
    .mysqli_connect_error());
    }
    else {
    $sql = "INSERT INTO events (field1, field2. field3, field4, field5)

    values('$eventname', '$elocation', '$edate', '$etime', '$eorganizers')";
    if($conn->query($sql)){
    echo "Event added successfully";
    }
    else{
    echo "Error: ".$sql."<br>".$conn->error;

    }
    $conn->close();

    }

}
else{
echo "Location cannot be empty"
die();
}
}
else{
echo "Event name cannot be empty";
die();


}

?>
