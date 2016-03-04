<?php
 require_once('dB-connection.php');
$password  = $_POST['password'];
$userName = $_POST['username']; 
$stmt = $conn->prepare("SELECT * FROM `users` WHERE `username` =? and `password` =?");
$stmt->bind_param("ii", $userName,$password);
/* execute query */
    $stmt->execute();
$result = $stmt->get_result();
if ($result->num_rows > 0) {
    /* now you can fetch the results into an array - NICE */
    while ($row = $result->fetch_assoc()) {

        $respon["username"] = $row["username"];
		$respon["password"]=$row["password"];
		$respon["isAdmin"]=$row["isAdmin"];
		}
		echo json_encode($respon);
} else {
    echo "0 results";
}
$conn->close();
?>