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
$firstName = $_POST['first_name'];
$lastName = $_POST['last_name'];
$email = $_POST['email'];
$password = $_POST['password'];

$sql = "INSERT INTO users (username, first_name, last_name, email, password) 
        VALUES ('$username', '$firstName', '$lastName', '$email', '$password')";

if ($conn->query($sql) === TRUE) {
    echo json_encode(array("status" => "success", "message" => "Registrasi berhasil"));
} else {
    echo json_encode(array("status" => "error", "message" => "Gagal melakukan registrasi: " . $conn->error));
}

$conn->close();
?>
