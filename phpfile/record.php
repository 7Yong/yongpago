<?php
error_reporting(E_ALL); 
ini_set('display_errors',1); 
// 변수명, 파일명 등은 각자 사정에 맞게 바꿔야 한다
$con = mysqli_connect("localhost", "root", "dlruddyd12", "yongpago");
mysqli_query($con,'SET NAMES utf8');
// ↓ 이 부분을 "쿼리에서 사용할 변수명 = $_GET['안드로이드에서 정의한 변수명']" 형태로 맞춰줘야 작동한다
$result = $_GET['result'];
$score = $_GET['score'];

$sql=mysqli_query($con,"INSERT INTO `record` (`result`, `score`) VALUES ('$result', '$score');");

$response = array();

?>