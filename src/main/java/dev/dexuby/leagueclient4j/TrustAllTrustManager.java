package dev.dexuby.leagueclient4j;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import java.net.Socket;
import java.security.cert.X509Certificate;

public final class TrustAllTrustManager extends X509ExtendedTrustManager {

    private static class TrustAllTrustManagerSingleton {

        private static final TrustAllTrustManager INSTANCE = new TrustAllTrustManager();

    }

    private TrustAllTrustManager() {

    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) {

        //

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) {

        //

    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) {

        //

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) {

        //

    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

        //

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

        //

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {

        return new X509Certificate[0];

    }

    public static TrustAllTrustManager getInstance() {

        return TrustAllTrustManagerSingleton.INSTANCE;

    }

}
