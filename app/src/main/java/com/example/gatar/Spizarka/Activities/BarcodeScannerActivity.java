package com.example.gatar.Spizarka.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;

import com.example.gatar.Spizarka.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Class which is responsible for get barcode by phone camera.
 * Using ZXing Scanner
 * @see <a href="https://github.com/dm77/barcodescanner">ZXing Scanner repository on Git</a>
 */

public class BarcodeScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";
    private final FragmentManager fragmentManager = getFragmentManager();
    private String barcode;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        preferences = getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        barcode = rawResult.getText();
        preferencesEditor.putString(EXTRA_BARCODE,barcode);
        preferencesEditor.commit();

        Intent intent = new Intent(this, ChangeActivity.class);
        startActivity(intent);

        /* Options:
        * resume scanning:          mScannerView.resumeCameraPreview(this);
        * getting barcode text:     rawResult.getText()
        * getting barcode format:   rawResult.getBarcodeFormat().toString()
        */
    }

}
