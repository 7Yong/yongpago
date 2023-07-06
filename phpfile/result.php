<?php  

$link=mysqli_connect("localhost","root","dlruddyd12", "yongpago" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 


$sql="select * from progress";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('idProgress'=>$row['idProgress'],
            'Word'=>$row['Word'],
            'Inputplayer'=>$row['Inputplayer']
        ));
    }

header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("root"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>