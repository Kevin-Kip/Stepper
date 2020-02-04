<?php

require 'vendor/autoload.php'

$gateway = new Braintree_Gateway([
    'environment' => 'sandbox',
    'merchantId' => 'fyxw23mh27qmbh5k',
    'publicKey' => 'wrqc5v73wh5x5gnw',
    'privateKey' => '231093f2e57273e31d49a8e010fcd8e4'
]);

echo $gateway->clientToken()->generate();

?>
