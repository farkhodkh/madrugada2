package ru.petroplus.pos.mainscreen.ui.debit.http;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;

import ru.petroplus.pos.util.ResourceHelper;

public class SSLConnectionExample {
    private static final String TAG = SSLConnectionExample.class.getSimpleName();

    private Api exampleApi;
    String clientCertificatePassword = "1234";
    String caCertificateName = "master-cacert.pem";
    String exampleUrl = "https://91.240.172.195:6567";

    @SuppressLint("StaticFieldLeak")
    public void doRequest() {
        try {
            AuthenticationParameters authParams = new AuthenticationParameters();
            authParams.setClientCertificate(getClientCertFile());
            authParams.setClientCertificatePassword(clientCertificatePassword);
            authParams.setCaCertificate(readCaCert());

            exampleApi = new Api(authParams);
            updateOutput("Connecting to " + exampleUrl);

            AsyncTask task = new AsyncTask() {
                @Override
                protected Object doInBackground(Object... objects) {

                    try {
                        String result = exampleApi.doPostOkHttp();

//                        String result = exampleApi.doPost(exampleUrl);
                        int responseCode = exampleApi.getLastResponseCode();
                        if (responseCode == 200) {
                            publishProgress(result);
                        } else {
                            publishProgress("HTTP Response Code: " + responseCode);
                        }

                    } catch (Throwable ex) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        PrintWriter writer = new PrintWriter(baos);
                        ex.printStackTrace(writer);
                        writer.flush();
                        writer.close();
                        publishProgress(ex.toString() + " : " + baos.toString());
                    }

                    return null;
                }

                @Override
                protected void onProgressUpdate(final Object... values) {
                    StringBuilder buf = new StringBuilder();
                    for (final Object value : values) {
                        buf.append(value.toString());
                    }
                    updateOutput(buf.toString());
                }

                @Override
                protected void onPostExecute(final Object result) {
                    updateOutput("Done!");
                }
            };

            task.execute();

        } catch (Exception ex) {
            //error constructing MAC: java.security.InvalidKeyException:
            // No installed provider supports this key: com.android.org.bouncycastle.jcajce.PKCS12Key
            Log.e(TAG, "failed to create timeApi", ex);
            updateOutput(ex.toString());
        }
    }

    private void updateOutput(String text) {
        //mainTextView.setText(mainTextView.getText() + "\n\n" + text);
    }

    private File getClientCertFile() {
        return ResourceHelper.INSTANCE.getClientCertAssetFile();
    }

    private String readCaCert() throws Exception {
        AssetManager assetManager = ResourceHelper.INSTANCE.getContext().getAssets();
        InputStream inputStream = assetManager.open(caCertificateName);
        return IOUtil.readFully(inputStream);
    }

}
