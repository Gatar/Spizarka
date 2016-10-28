package com.gatar.Spizarka.BarcodeScanner.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerMVP;
import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerPresenter;
import com.gatar.Spizarka.ItemFiller.View.ItemFillerActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Class which is responsible for get barcode by phone camera.
 * Using ZXing Scanner
 * @see <a href="https://github.com/dm77/barcodescanner">ZXing Scanner repository on Git</a>
 */

public class BarcodeScannerActivity extends Activity implements ZXingScannerView.ResultHandler, BarcodeScannerMVP.RequiredViewOperations {


    private BarcodeScannerMVP.PresenterOperations mPresenter;

    private ZXingScannerView mScannerView;
    private final FragmentManager fragmentManager = getFragmentManager();


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mPresenter = new BarcodeScannerPresenter(this);

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

    /**
     *  Get result from barcode scanner.
     *
     * Options:
     * resume scanning:          mScannerView.resumeCameraPreview(this);
     * getting barcode text:     rawResult.getText()
     * getting barcode format:   rawResult.getBarcodeFormat().toString()
     */
    @Override
    public void handleResult(Result rawResult) {
        String barcode = rawResult.getText();
        mPresenter.handleScannedBarcode(barcode);
    }


    @Override
    public void toItemFillerAcitivity() {
        Intent intent = new Intent(this, ItemFillerActivity.class);
        startActivity(intent);
    }

    /**
     * Show dialog box with question how handle new barcode:
     * 1. Add it to existing item in database
     * 2. Add new item to database
     * {@link ChangeDialogNewBarcode}
     */
    @Override
    public void showNewBarcodeDialogBox(){
        ChangeDialogNewBarcode checkAddType = new ChangeDialogNewBarcode();
        checkAddType.setPresenter(mPresenter);
        checkAddType.show(fragmentManager,"addType");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

}
