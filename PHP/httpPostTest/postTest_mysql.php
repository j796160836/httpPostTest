<?php
// Dreamweaver的函式庫
if (!function_exists("GetSQLValueString")) {
function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
{
  if (PHP_VERSION < 6) {
    $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
  }

  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

  switch ($theType) {
    case "text":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;    
    case "long":
    case "int":
      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
      break;
    case "double":
      $theValue = ($theValue != "") ? doubleval($theValue) : "NULL";
      break;
    case "date":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;
    case "defined":
      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
      break;
  }
  return $theValue;
}
}

$database_dblink = "httpPostTest";
$username_dblink = "httpPostTest";
$password_dblink = "YOUR_MYSQL_PASSWORD";
// 建立資料庫連線
$dblink = mysql_pconnect("localhost", $username_dblink, $password_dblink) or trigger_error(mysql_error(),E_USER_ERROR); 
mysql_query("SET NAMES utf8",$dblink); 
mysql_query("SET CHARACTER_SET_CLIENT=utf8",$dblink); 
mysql_query("SET CHARACTER_SET_RESULTS=utf8",$dblink); 
mysql_select_db($database_dblink, $dblink);

// 宣告utf-8的編碼header("Content-Type:text/html; charset=utf-8");
// 接收POST/GET的資料$data=@$_REQUEST['data'];// 如果有資料if (strcmp(trim($data), "")!=0)
{	
	// 將資料輸入進資料庫
	$insertSQL = sprintf("INSERT INTO `weblog` (`data`) VALUES ('%s');", $data);
    mysql_query($insertSQL, $dblink) or die(mysql_error());
}

// 從資料庫撈出來最後一筆資料
$query_rs = "SELECT * FROM `weblog` order by log_id desc limit 0,1";
$rs = mysql_query($query_rs, $dblink) or die(mysql_error());
while ($row = mysql_fetch_assoc($rs))
{
	echo "data=".$row['data']."\n"."time=".$row['post_time'];
}
?>