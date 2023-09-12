<?php 

include "koneksi.php";

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $mode = $_POST['mode'];
        $respon = array();
        $respon['respon']= '0';
        switch($mode){
            case 'show_data_tiket':
                $tujuan = $_POST['tujuan'];

                $sql = "SELECT * FROM tiket WHERE tujuan LIKE '%$tujuan%' ORDER BY tgl DESC";
                $result = mysqli_query($conn,$sql);

                if(mysqli_num_rows($result)>0){
                    header("Access-Control-Allow-Origin: *");
                    header("Content-Type: application/json");
                    $data_tiket = array();
                    while ($data = mysqli_fetch_assoc($result)) {
                        array_push($data_tiket, $data);
                    }
                    echo json_encode($data_tiket);
                    exit();
                } else {
                    $data_tiket = array();
                    echo json_encode($data_tiket);
                }
                break;
            case 'show_data_hotel':
                $hotel = $_POST['hotel'];

                $sql = "SELECT id_hotel, nama, tgl, hotel, kelas, no_hp, pelanggan, total, alamat, CONCAT('$http_k', ktp) AS ktp FROM hotel
                    WHERE hotel LIKE '%$hotel%' ORDER BY tgl DESC";
                $result = mysqli_query($conn,$sql);

                if(mysqli_num_rows($result)>0){
                    header("Access-Control-Allow-Origin: *");
                    header("Content-Type: application/json");
                    $data_hotel = array();
                    while ($data = mysqli_fetch_assoc($result)) {
                        array_push($data_hotel, $data);
                    }
                    echo json_encode($data_hotel);
                    exit();
                } else {
                    $data_hotel = array();
                    echo json_encode($data_hotel);
                }
                break;
            case 'detail':
                $id_tiket = $_POST['id_tiket'];

                $sql = "SELECT * FROM tiket WHERE id_tiket = '$id_tiket'";
                $result = mysqli_query($conn,$sql);

                if(mysqli_num_rows($result)>0){
                    header("Access-Control-Allow-Origin: *");
                    header("Content-Type: application/json");
                    $data = mysqli_fetch_assoc($result);
                    
                    echo json_encode($data); 
                    exit();
                }else{
                    $respon['kode']= "0";
                    echo json_encode($respon);
                    exit();
                }
                break;
            case 'get_kategori':
                $sql = "SELECT kategori FROM kategori";
                $result = mysqli_query($conn,$sql);
                if (mysqli_num_rows($result)>0) {
                    header("Access-Control-Allow-Origin: *");
                    header("Content-type: application/json; charset=UTF-8");

                    $nama_kategori = array();
                        while($nama = mysqli_fetch_assoc($result)){
                            array_push($nama_kategori, $nama);
                        }
                    echo json_encode($nama_kategori);
                }
                break;
        }
    }

?>