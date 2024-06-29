<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "uts_anmp";

$conn = new mysqli($servername, $username, $password, $database);


if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * FROM users WHERE username='$username' AND password='$password'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();
    echo json_encode(array("status" => "success", "user" => $user));
} else {
    echo json_encode(array("status" => "error", "message" => "Username atau password salah"));
}

$conn->close();
?>
