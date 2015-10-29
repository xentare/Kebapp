<?php
/**
 * Created by PhpStorm.
 * User: Juha
 * Date: 28.10.2015
 * Time: 18:18
 */

require_once(__DIR__.'/config.php');////PHP commit change test

$conn = $MY_DB->connect();

if(isset($_GET["all"])){
    $get = $conn->real_escape_string($_GET['all']);
    $result = $conn->query("SELECT * FROM restaurants WHERE name = ('$get')");
    while($obj = $result->fetch_object()){
        echo $obj->latitude.',';
        echo $obj->longtitude;
    }
}
?>