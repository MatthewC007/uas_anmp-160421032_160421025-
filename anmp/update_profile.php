<?php

$servername = "localhost";
$username = "root";
$password = "";
$database = "uts_anmp";

$conn = new mysqli($servername, $username, $password, $database);

if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

$userID = $_POST['id'];
$firstName = $_POST['first_name'];
$lastName = $_POST['last_name'];

if (empty($userID) || empty($firstName) || empty($lastName)) {
    echo json_encode(array("status" => "error", "message" => "Harap lengkapi semua field"));
    exit();
}


$checkQuery = "SELECT COUNT(*) AS count FROM users WHERE id = '$userID'";
$result = $conn->query($checkQuery);

if ($result && $result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $count = $row['count'];

    if ($count > 0) {
      
        $sql = "UPDATE users 
                SET first_name = '$firstName', last_name = '$lastName'
                WHERE id = '$userID'";

        if ($conn->query($sql) === TRUE) {
            echo json_encode(array("status" => "success", "message" => "Update berhasil"));
        } else {
            echo json_encode(array("status" => "error", "message" => "Gagal melakukan update: " . $conn->error));
        }
    } else {
        echo json_encode(array("status" => "error", "message" => "ID tidak ditemukan"));
    }
} else {
  
    echo json_encode(array("status" => "error", "message" => "Gagal memeriksa ID"));
}

$conn->close();

?>
