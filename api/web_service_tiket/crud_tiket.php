<?php 

include "koneksi.php";

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $mode = $_POST['mode'];
        $respon = array();
        $respon['respon']= '0';
        switch($mode){
            case 'insert':
                $nama = $_POST['nama'];
                $asal = $_POST['asal'];
                $jenis = $_POST['jenis'];
                $tujuan = $_POST['tujuan'];
                $kelas = $_POST['kelas'];
                $tgl = $_POST['tgl'];
                $no_hp = $_POST['no_hp'];
                $penumpang = $_POST['penumpang'];
                $total = $_POST['total'];

                $sql = "INSERT INTO tiket(nama, asal, jenis, tujuan, kelas, tgl, no_hp, penumpang, total, status_bayar)
                    VALUES('$nama','$asal', '$jenis','$tujuan','$kelas','$tgl','$no_hp','$penumpang','$total','Belum bayar')";
                $result = mysqli_query($conn,$sql);
                if ($result) {
                    $respon['respon']= "1";
                    echo json_encode($respon);
                    exit();
                }
                break;
            case 'delete':
                $id_tiket = $_POST['id_tiket'];

                $sql = "DELETE FROM tiket WHERE id_tiket = '$id_tiket'";
                $result = mysqli_query($conn,$sql);
                
                $respon['respon']= "1";
                echo json_encode($respon);
                exit();
                break;
            case 'edit':
                $id_tiket = $_POST['id_tiket'];
                $nama = $_POST['nama'];
                $asal = $_POST['asal'];
                $tujuan = $_POST['tujuan'];
                $kelas = $_POST['kelas'];
                $tgl = $_POST['tgl'];
                $no_hp = $_POST['no_hp'];
                $penumpang = $_POST['penumpang'];
                $total = $_POST['total'];

                $sql = "UPDATE tiket SET nama = '$nama', asal = '$asal', tujuan = '$tujuan', kelas = '$kelas',
                    tgl = '$tgl', no_hp = '$no_hp', penumpang = '$penumpang', total = '$total' WHERE id_tiket = '$id_tiket'";
                $result = mysqli_query($conn,$sql);
                if ($result) {
                    $respon['respon']= "1";
                    echo json_encode($respon);
                    exit();
                }
                break;
            case 'bayar':
                $id_tiket = $_POST['id_tiket'];
                $imstr = $_POST['image'];
                $file = $_POST['file'];
                $path = "bukti/";

                $sql = "UPDATE tiket SET foto_bayar = '$file', status_bayar = 'Sudah bayar' WHERE id_tiket = '$id_tiket'";
                $result = mysqli_query($conn,$sql);

                if(file_put_contents($path.$file, base64_decode($imstr))==false){
                    $respon['respon']= "0";
                    echo json_encode($respon);
                    exit();
                } else {
                    $respon['respon']= "1";
                    echo json_encode($respon);
                    exit();
                }
                break;
        }
    }

?>