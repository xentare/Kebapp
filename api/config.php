<?php
/**
 * Created by PhpStorm.
 * User: Juha
 * Date: 28.10.2015
 * Time: 17:44
 */

class DATABASE_CONNECT
{
    function connect()
    {
        $servername = "mysql.labranet.jamk.fi";
        $username = "H4113";
        $password = "1sDqEaz3PrVqSIc17MA0ulBiKM7mpUuv";
        $database = "H4113";

        $conn = new mysqli($servername, $username, $password);
        $conn->select_db($database);

        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

        return $conn;
    }
}

$MY_DB = new DATABASE_CONNECT();
?>