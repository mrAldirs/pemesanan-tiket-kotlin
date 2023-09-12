<?php 

    $DB_NAME = "tiket";
    $DB_USER = "root";
    $DB_PASS = "";
    $DB_SERVER_LOC = "localhost";

    $conn = mysqli_connect($DB_SERVER_LOC, $DB_USER, $DB_PASS, $DB_NAME);

    $local = 'http://192.168.137.1/web_service_tiket/';

    $http_k = $local . 'ktp/';
    $http_b = $local . 'bukti/';
?>