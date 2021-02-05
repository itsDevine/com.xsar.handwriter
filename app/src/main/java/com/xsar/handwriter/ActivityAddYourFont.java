package com.xsar.handwriter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.common.api.internal.LifecycleCallback;

import java.util.Arrays;
import java.util.List;

public class ActivityAddYourFont extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    BillingProcessor billingProcessor;
    private Button payment, afterPayment;
    static final String ITEM_SKU = "add_your_handwriting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_font);

       // String LicenseKey =
         //       "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA80p0Rw5hdEAtFTnX9Qmwxte5oOI+gjhTfiK2kqwrBOFE75erUJ9OJxa6e31yIvbjRsJc/9LrlMaYBOnDno9lqlRpDjDq6Ij/L2WhH3NIOqmR+XQXISY9dpMg3La/DzpDQAJCoqoDimayc4JM9tKWSivdGS7LNBVNkuepK9qi+EgA3U8sB0ahqYUa0ukYZVMH4fqvuikNhYPRD1lR3vSnFnEJlP97EwUwUpx1odhNHTGTx6Onk0z5xNQjiIxBi0jw8o4Ij+PU6LTSdKSlkEpioCK03Z1cJjx6bzSLpc0MCZ4I5WFmDnihGIdEcx3OPMafuxrSN9kkdtVz7FVZN9tn3QIDAQAB";

        Toolbar toolbar = findViewById(R.id.AYFtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add your Handwriting here");

        payment = (Button) findViewById(R.id.paymentButton);
        afterPayment = (Button) findViewById(R.id.AfterpaymentButton);

        afterPayment.setVisibility(View.INVISIBLE);

        //billingProcessor = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA80p0Rw5hdEAtFTnX9Qmwxte5oOI+gjhTfiK2kqwrBOFE75erUJ9OJxa6e31yIvbjRsJc/9LrlMaYBOnDno9lqlRpDjDq6Ij/L2WhH3NIOqmR+XQXISY9dpMg3La/DzpDQAJCoqoDimayc4JM9tKWSivdGS7LNBVNkuepK9qi+EgA3U8sB0ahqYUa0ukYZVMH4fqvuikNhYPRD1lR3vSnFnEJlP97EwUwUpx1odhNHTGTx6Onk0z5xNQjiIxBi0jw8o4Ij+PU6LTSdKSlkEpioCK03Z1cJjx6bzSLpc0MCZ4I5WFmDnihGIdEcx3OPMafuxrSN9kkdtVz7FVZN9tn3QIDAQAB", this);
        billingProcessor = new BillingProcessor(this, null,this);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(ActivityAddYourFont.this, "android.test.purchased");

            }
        });

        afterPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAddYourFont.this, ActivityPurchased.class);
                startActivity(intent);
            }
        });

        /*BillingClientSetUp();

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (billingClient.isReady()){
                    SkuDetailsParams params = SkuDetailsParams.newBuilder().setSkusList(Arrays.asList("add_your_handwriting")).
                            setType(BillingClient.SkuType.INAPP).build();
                    billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                            int responseCode = billingResult.getResponseCode();
                            if (responseCode==BillingClient.BillingResponseCode.OK){
                                loadActivity();
                            }else{
                                Toast.makeText(ActivityAddYourFont.this, "Cannot Open", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else{
                    Toast.makeText(ActivityAddYourFont.this, "Billing Client is not ready", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadActivity() {
        Intent intent3 = new Intent(ActivityAddYourFont.this, activity_fab4.class);
        startActivity(intent3);
    }


    private void BillingClientSetUp() {
        billingClient = BillingClient.newBuilder(this).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                int responseCode = billingResult.getResponseCode();
                if (responseCode==BillingClient.BillingResponseCode.OK){
                    Toast.makeText(ActivityAddYourFont.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(ActivityAddYourFont.this, ""+responseCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(ActivityAddYourFont.this, "Disconnected from Service", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }
   */

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }






    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

        payment.setVisibility(View.INVISIBLE);
        afterPayment.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onDestroy() {
        if (billingProcessor != null){
            billingProcessor.release();
        }
        super.onDestroy();
    }
}