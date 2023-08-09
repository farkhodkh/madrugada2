package ru.petroplus.pos.mainscreen.ui.debit.http;

import com.google.android.gms.common.util.IOUtils;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.petroplus.pos.util.ResourceHelperJava;


/**
 * client-side interface to the back-end application.
 */
public class Api {

    private final SSLContext sslContext;
    private int lastResponseCode;

    public int getLastResponseCode() {
        return lastResponseCode;
    }

    public Api(AuthenticationParameters authParams) throws Exception {
        File clientCertFile = authParams.getClientCertificate();

        sslContext = SSLContextFactory
                .getInstance()
                .makeContext(
                        clientCertFile,
                        authParams.getClientCertificatePassword(),
                        authParams.getCaCertificate()
                );
        CookieHandler.setDefault(new CookieManager());
    }

    public String doPostOkHttp() throws Exception {
        String result = null;

        X509TrustManager[] trustAllCerts = new X509TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        OkHttpClient.Builder clientBuilder = new OkHttpClient
                .Builder();
        clientBuilder.setHostnameVerifier$okhttp((hostname, session) -> true);
        clientBuilder.addInterceptor(new OkHttpProfilerInterceptor());
        clientBuilder.callTimeout(3000L, TimeUnit.MILLISECONDS);
        clientBuilder.connectTimeout(3000L, TimeUnit.MILLISECONDS);
        clientBuilder.writeTimeout(3000L, TimeUnit.MILLISECONDS);

        clientBuilder.sslSocketFactory(new NoSSLv3SocketFactory(sslContext.getSocketFactory()), trustAllCerts[0]);

        OkHttpClient client = clientBuilder.build();

        byte[] bytes = IOUtils.toByteArray(ResourceHelperJava.context.getAssets().open("PingP7.bin"));
        RequestBody body = RequestBody.create(bytes);

        Request request = new Request.Builder()
                .url("https://91.240.172.195:6567")
                .post(body)
                .addHeader("SECURITY_FLAGS", "SECURITY_FLAG_IGNORE_UNKNOWN_CA, SECURITY_FLAG_IGNORE_CERT_WRONG_USAGE, SECURITY_FLAG_IGNORE_CERT_CN_INVALID, SECURITY_FLAG_IGNORE_CERT_DATE_INVALID")
                .addHeader("Accept", "application/octet-stream")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "P7_client")
                .addHeader("X-SSL-Client-CN", "POS-4000-00001-123456789")
                .addHeader("X-Terminal-IP", "127.0.0.1")
                .addHeader("Content-Type", "application/octet-stream")
                .build();

        try {
            Response response = client.newCall(request).execute();
            lastResponseCode = response.code();
            result = String.valueOf(response.code());
            ResponseBody responseBody = response.body();
            String b = "";
        } catch (Exception ex) {
            String df = ex.toString();
            String f = "";
        }
        return result;
    }

    public String doGet(String url) throws Exception {
        String result = null;

        HttpURLConnection urlConnection = null;
        try {
            URL requestedUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestedUrl.openConnection();
            if (urlConnection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) urlConnection).setSSLSocketFactory(sslContext.getSocketFactory());
            }
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1500);
            urlConnection.setReadTimeout(1500);

            lastResponseCode = urlConnection.getResponseCode();
            result = IOUtil.readFully(urlConnection.getInputStream());

        } catch (Exception ex) {
            result = ex.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }
}
