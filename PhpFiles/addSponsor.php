<?php
 require_once('dB-connection.php');
$password  = $_POST['password'];
$userName = $_POST['username']; 
$email = $_POST['email']; 
$phoneNumber = $_POST['phoneNumber']; 
$company = $_POST['company']; 

$stmt = $conn->prepare("INSERT INTO `registeredusers` (`userName`, `password`, `isAdmin`) VALUES (?, ?, ?);");
$stmt->bind_param("isi", $userName,$password,0);
/* execute query */
    $stmt->execute();
	
$stmt2 = $conn->prepare("INSERT INTO `sponsors` (`userName`, `Email`, `company`, `PhoneNumber`) VALUES (?, ?, ?, ?);");
$stmt2->bind_param("isss", $userName,$email,$company,$phoneNumber);
/* execute query */
 $stmt2->execute();	
 
 
$conn->close();
?>