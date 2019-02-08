package com.example.hossein.sensortest;


import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class FingerPrintFragment extends Fragment {


    private static final String KEY_NAME = "fingerprintKey";
    private int REQ_PERMISSION_FINGER = 0;
    private Cipher mCipher;

    public FingerPrintFragment() {
        // Required empty public constructor
    }

    public static FingerPrintFragment newInstance() {

        Bundle args = new Bundle();

        FingerPrintFragment fragment = new FingerPrintFragment();
        fragment.setArguments(args);
        return fragment;
    }

    KeyStore mKeyStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_finger_print, container, false);

        checkPermission();

        KeyguardManager keyguardManager = (KeyguardManager) getActivity()
                .getSystemService(Context.KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getActivity()
                .getSystemService(Context.FINGERPRINT_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if(fingerprintManager.isHardwareDetected()){
                //ایا دستگاه فینکر پرینت دارد یا نه
                if(!fingerprintManager.hasEnrolledFingerprints()){
                  //آیا کاربر فینگر پرینت را ادد کرده یا نه
                    Toast.makeText(getActivity(), "Please Enroll an fingerPrint", Toast.LENGTH_SHORT).show();
                }else if(!keyguardManager.isDeviceSecure()){
                    Toast.makeText(getActivity(), "Device Lock screen is open", Toast.LENGTH_SHORT).show();
                }else{
                    generateKey();
                    Log.i("<><>" , "generatekey");
                    if (initCipher()){
                        Log.i("<><><><>" , "init cipher");
                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(mCipher);
                        CancellationSignal cancellationSignal = new CancellationSignal();
                        fingerprintManager.authenticate(null, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                Toast.makeText(getActivity(), errString.toString() , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                Toast.makeText(getActivity(), helpString, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                Toast.makeText(getActivity(), "Succeeded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } , null);
                    }
                }
            }else{
                Toast.makeText(getActivity(), "Don't have finger Print", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "No FingerPrint", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }


        return view;
    }

    private void generateKey() {

        KeyGenerator keyGenerator = null;
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES , "AndroidKeyStore");

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            mKeyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME ,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }


    }

    private void checkPermission() {
        Log.i("<><><>" , "permision");
        if(ActivityCompat.checkSelfPermission(getActivity() , Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("<><><>" , "permision1");
            ActivityCompat.requestPermissions(getActivity() ,
                    new String[]{Manifest.permission.USE_FINGERPRINT} , REQ_PERMISSION_FINGER);

        }
    }

    private boolean initCipher(){
        try {
            Log.i("<><><>", "initCipher: ");
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES +
                    "/" + KeyProperties.BLOCK_MODE_CBC +
                    "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            Log.i("<><><>", "initCipher:1 ");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            Log.i("<><><>" , "key store");
            mKeyStore.load(null);
            SecretKey secretKey = (SecretKey) mKeyStore.getKey(KEY_NAME , null);
            Log.i("<><><>" , "key store1");
            mCipher.init(Cipher.ENCRYPT_MODE , secretKey);
            return true;

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQ_PERMISSION_FINGER){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                getActivity().finish();
            }
        }
    }
}
