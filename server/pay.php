<?php

require 'vendor/autoload.php'

$nonceFromTheClient = $_POST["nonce"];
$result = $gateway->transaction()->sale([
  'amount' => '10.00',
  'paymentMethodNonce' => $nonceFromTheClient,
  'options' => [
    'submitForSettlement' => True
  ]
]);

echo $gateway->clientToken()->generate();

?>
