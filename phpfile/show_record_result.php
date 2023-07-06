<?php
error_reporting(E_ALL); 
ini_set('display_errors',1); 
// 변수명, 파일명 등은 각자 사정에 맞게 바꿔야 한다
$con = mysqli_connect("localhost", "root", "dlruddyd12", "yongpago");
mysqli_query($con,'SET NAMES utf8');
// ↓ 이 부분을 "쿼리에서 사용할 변수명 = $_GET['안드로이드에서 정의한 변수명']" 형태로 맞춰줘야 작동한다

$sql=mysqli_query($con,"SELECT count(*) FROM `record` WHERE `result` LIKE '승'");

$response = array();

while($row = mysqli_fetch_assoc($sql))
   {
       extract($row);
       array_push($response, array(
           'win'=>$row["count(*)"]
       ));
   }

header('Content-Type: application/json; charset=utf8');
echo json_encode($response);
?>