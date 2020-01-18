package com.example.stepper;

public class PaypalSample {

    public static final String SERVER = "http://payment.krzysztofabram.pl";
    public static final String CLIENT_TOKEN = "clientToken";
    public static final String TRANSACTION = "transaction";

//    private static final int PAYPAL_REQUEST_CODE=7171;
//    String amount="";
//    private static PayPalConfiguration config=new PayPalConfiguration()
//            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//            .clientId(Config.PAYPAL_CLIENT_ID);
//    Button btnPayNow;
//    EditText edtAmount;
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopService(new Intent(this,PayPalService.class));
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode==PAYPAL_REQUEST_CODE){
//            if(resultCode==RESULT_OK){
//                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if(confirmation!=null){
//                    String paymentDetails=confirmation.toJSONObject().toString();
//                    startActivity(new Intent(this,PaymentDetails.class)
//                            .putExtra("PaymentDetails",paymentDetails)
//                            .putExtra("PaymentAmount",amount));
//
//
//
//                }
//            }else if (resultCode== Activity.RESULT_CANCELED){
//                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
//            }
//        }else if (resultCode==PaymentActivity.RESULT_EXTRAS_INVALID){
//            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Intent intent=new Intent(this,PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//        startService(intent);
//
//
//        btnPayNow=(Button)findViewById(R.id.btnPayNow);
//        edtAmount=(EditText)findViewById(R.id.edtAmount);
//
//        btnPayNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                processPayment();
//
//            }
//        });
//
//    }
//
//    private void processPayment() {
//        amount=edtAmount.getText().toString();
//        PayPalPayment payPalPayment= new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","Pay the bill",PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent=new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
//        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
//
//    }
}
