<?php 

include "koneksi.php";

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $mode = $_POST['mode'];
        $respon = array();
        $respon['respon']= '0';
        switch($mode){
            case 'insert':
                $nama = $_POST['nama'];
                $hotel = $_POST['hotel'];
                $no_hp = $_POST['no_hp'];
                $pelanggan = $_POST['pelanggan'];
                $kelas = $_POST['kelas'];
                $tgl = $_POST['tgl'];
                $alamat = $_POST['alamat'];
                $total = $_POST['total'];
                $imstr = $_POST['image'];
                $file = $_POST['file'];
                $path = "ktp/";

                $sql = "INSERT INTO hotel(nama, hotel, no_hp, pelanggan, kelas, alamat, tgl, total, ktp)
                    VALUES('$nama','$hotel','$no_hp','$pelanggan','$kelas','$alamat','$tgl','$total','$file')";
                $result = mysqli_query($conn,$sql);
                if ($result) {
                    if(file_put_contents($path.$file, base64_decode($imstr))==false){
                        $respon['respon']= "0";
                        echo json_encode($respon);
                        exit();
                    } else {
                        $respon['respon']= "1";
                        echo json_encode($respon);
                        exit();
                    }
                }
                break;
            case 'delete':
                $id_hotel = $_POST['id_hotel'];

                $sql = "DELETE FROM hotel WHERE id_hotel = '$id_hotel'";
                $result = mysqli_query($conn,$sql);
                
                $respon['respon']= "1";
                echo json_encode($respon);
                exit();
                break;
        }
    }

?>