<?php
/**
 * Created by PhpStorm.
 * User: Juha
 * Date: 30/10/15
 * Time: 16:26
 */

/*require_once('MyApi.php');

if (!array_key_exists('HTTP_ORIGIN', $_SERVER)) {
    $_SERVER['HTTP_ORIGIN'] = $_SERVER['SERVER_NAME'];
}

try {
    $API = new MyAPI($_REQUEST['request'], $_SERVER['HTTP_ORIGIN']);
    echo $_REQUEST['request'];
    echo $API->processAPI();
} catch (Exception $e) {
    echo json_encode(Array('error' => $e->getMessage()));
}*/

require_once 'config.php';

$args = explode('/', $_SERVER['REQUEST_URI']);
unset($args[0],$args[1],$args[2],$args[3]);
$endpoint = array_shift($args);


if($_SERVER['REQUEST_METHOD'] === 'GET') {

    switch($endpoint){
        case 'restaurant':
        echo restaurantGetQuery($args);
            break;
        case 'comment':
            echo commentGetQuery($args);
            break;
    }
}

if($_SERVER['REQUEST_METHOD'] === 'POST'){
    switch($endpoint){
        case 'restaurant':
            echo restaurantPostQuery();
            break;
        case 'comment':
            echo commentPostQuery();
            break;
    }
}

function commentGetQuery($args){
    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();
    //$mysqli->real_escape_string($args[0]);

    if(!isset($args[0])){
        $query = "SELECT * FROM comments";
    } else {
        $query = "SELECT * FROM comments WHERE restaurant_id = ('$args[0]')";
    }
    return fetch($mysqli->query($query));

}

function commentPostQuery(){
    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();
    $mysqli->real_escape_string($_POST['text']);

    if(isset($_POST['restaurant_id'])){
        $query = "INSERT INTO comments (restaurant_id, text) VALUES (?,?)";
        $mysqli->query($query);

        $statement = $mysqli->prepare($query);
        $statement->bind_param('is',$_POST['restaurant_id'],$_POST['text']);
        return fetch($statement->execute());
    }

}

function restaurantPostQuery(){

    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();
    $mysqli->real_escape_string($_POST['name']);
    //$mysqli->real_escape_string($_POST['latitude']);
    //$mysqli->real_escape_string($_POST['longitude']);
    $mysqli->real_escape_string($_POST['address']);

    if(isset($_POST['name'])){
        $query = "INSERT INTO restaurants (name, latitude, longitude, address) VALUES (?,?,?,?)";

        $statement = $mysqli->prepare($query);
        $statement->bind_param('sdds',$_POST['name'],$_POST['latitude'],$_POST['longitude'],$_POST['address']);
        return fetch($statement->execute());
    }
}

function restaurantGetQuery($args){

    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();
    //$mysqli->real_escape_string($args[0]);

    if(!isset($args[0])){
        $query = "SELECT * FROM restaurants";
    } else {
        $query = "SELECT * FROM restaurants WHERE name LIKE ('%$args[0]%')";
    }
    return fetch($mysqli->query($query));
}



function fetch($result){
    $rows = array();
    while($r = mysqli_fetch_assoc($result)) {
        $rows[] = $r;
    }
    header('Content-type: application/json');
    return json_encode($rows);
}


/*while (list($var,$value) = each ($_SERVER)) {
    echo $var." Val:".$value."<br />";
}*/
?>