<?php
// ==========================================
// Ideamart : Sample PHP SMS API
// ==========================================
// Author : Pasindu De Silva
// Licence : MIT License
// http://opensource.org/licenses/MIT
// ==========================================

//Edits Choxmi Sathsara

ini_set('error_log', 'sms-app-error.log');
require_once 'Log.php';
require_once 'SMSReceiver.php';
require_once 'SMSSender.php';

define('SERVER_URL', 'http://localhost:7000/sms/send');	
define('APP_ID', 'APPID');
define('APP_PASSWORD', 'password');

$logger = new Logger();

try{

	// Creating a receiver and intialze it with the incomming data
	$receiver = new SMSReceiver(file_get_contents('php://input'));
	
	$number = $_GET['number'];
	
	//Creating a sender
	$sender = new SMSSender( SERVER_URL, APP_ID, APP_PASSWORD);
	
	$message = $receiver->getMessage(); // Get the message sent to the app
	$address = $receiver->getAddress();	// Get the phone no from which the message was sent 

	$logger->WriteLog($receiver->getAddress());
	
	//$number = array('tel:94779729257','tel:94779729258');

		// Send a SMS to a particular user
		echo $address;
		$response=$sender->sms('This message is sent only to one user', $number);

}catch(SMSServiceException $e){
	$logger->WriteLog($e->getErrorCode().' '.$e->getErrorMessage());
}

?>