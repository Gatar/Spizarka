package com.gatar.Spizarka.BarcodeScanner.View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerMVP;
import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerPresenter;
import com.gatar.Spizarka.ItemFiller.View.ItemFillerActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

/**
 * Class which is responsible for get barcode by phone camera.
 * Using ZXing Scanner
 * @see <a href="https://github.com/dm77/barcodescanner">ZXing Scanner repository on Git</a>
 */

public class BarcodeScannerActivity extends Activity implements ZXingScannerView.ResultHandler, BarcodeScannerMVP.RequiredViewOperations {


    private BarcodeScannerMVP.PresenterOperations mPresenter;

    private ZXingScannerView mScannerView;
    private final FragmentManager fragmentManager = getFragmentManager();

    /**
     * Id to identity CAMERA permission request.
     */
    private static final int REQUEST_CAMERA = 0;
    private View view;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mPresenter = new BarcodeScannerPresenter(this);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        view = mScannerView.getRootView();

        if(!mayRequestCamera()) return;
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
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mayRequestCamera();
            }
        }
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
     * {@link DialogBoxNewBarcode}
     */
    @Override
    public void showNewBarcodeDialogBox(){
        DialogBoxNewBarcode checkAddType = new DialogBoxNewBarcode();
        checkAddType.setPresenter(mPresenter);
        checkAddType.show(fragmentManager,"addType");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    private boolean mayRequestCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(CAMERA)) {
            Snackbar.make(view, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                        }
                    });
        } else {
            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
        }
        return false;
    }

}
