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
            echo restaurantPostQuery($args);
            break;
        case 'comment':
            echo commentPostQuery($args);
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

function commentPostQuery($args)
{
    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();
    $mysqli->real_escape_string($_POST['text']);

    if (isset($_POST['restaurant_id'])) {
        $query = "INSERT INTO comments (restaurant_id, text) VALUES (?,?)";
        $mysqli->query($query);

        $statement = $mysqli->prepare($query);
        $statement->bind_param('is', $_POST['restaurant_id'], $_POST['text']);
        return fetch($statement->execute());
    } else if(isset($args[0])){
        $query = "UPDATE comments SET up_votes=up_votes+1 WHERE comment_id='$args[0]'";
        return fetch($mysqli->query($query));
    }
}

function restaurantPostQuery($args){

    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();

    if($args[0] == 'rate'){
        $mysqli->real_escape_string($_POST['android_id']);
        $query = "SELECT * FROM restaurants_ratings WHERE restaurant_id = '".$_POST['restaurant_id']."' AND android_id = '".$_POST['android_id']."'";
        $query = $mysqli->query($query);
        if(mysqli_num_rows($query) == 0){
            $query = "INSERT INTO restaurants_ratings (restaurant_id, rating, android_id) VALUES (?,?,?)";
            $statement = $mysqli->prepare($query);
            $statement->bind_param('ids', $_POST['restaurant_id'], $_POST['rating'], $_POST['android_id']);
            return fetch($statement->execute());
        } else {
            return error(json_last_error_msg());
        }
    } else {
        $query = "INSERT INTO restaurants (name, latitude, longitude, address, rating) VALUES (?,?,?,?,0)";
        $statement = $mysqli->prepare($query);
        $statement->bind_param('sdds', $_POST['name'], $_POST['latitude'], $_POST['longitude'], $_POST['address']);
        $statement->execute();

        $id = $statement->insert_id;
        $query = "INSERT INTO restaurants_ratings (rating, restaurant_id) VALUES (?, $id)";
        $statement = $mysqli->prepare($query);
        $statement->bind_param('d',$_POST['rating']);
        $statement->execute();

    }
}

function restaurantGetQuery($args){

    $MY_DB = new DATABASE_CONNECT();
    $mysqli = $MY_DB->connect();
    //$mysqli->real_escape_string($args[0]);

    if(!isset($args[0])){
        $query = "select r.restaurant_id, r.name, r.latitude, r.longitude, r.address,
                 AVG(rr.rating) as rating
                 from restaurants r
                 inner join restaurants_ratings rr
                 on r.restaurant_id = rr.restaurant_id
                 group by r.restaurant_id";
    } else if($args[0] == "search" && isset($args[1])){
        $query = "SELECT * FROM restaurants WHERE name LIKE ('%$args[1]%') OR address LIKE ('%$args[1]%')";
    } else if($args[0] == "openings" && isset($args[1])){
        $day = date('N', date('W'));
        $query = "SELECT start_hour, end_hour  FROM opening_hours WHERE weekday = $day AND restaurant_id = $args[1]";
    }
    return fetch($mysqli->query($query));
}

function error($error){
    header('Content-type: application/json');
    return json_encode($error);
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

/*
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '1', '10:00:00', '22:00:00');
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '2', '10:00:00', '22:00:00');
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '3', '10:00:00', '22:00:00');
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '4', '10:00:00', '22:00:00');
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '5', '10:00:00', '22:00:00');
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '6', '11:00:00', '22:00:00');
INSERT INTO `H4113`.`opening_hours` (`restaurant_id`, `weekday`, `start_hour`, `end_hour`) VALUES ('1', '7', '11:00:00', '22:00:00');
*/

?>
