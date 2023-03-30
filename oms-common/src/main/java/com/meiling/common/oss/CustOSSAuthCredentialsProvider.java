package com.meiling.common.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xiaofan
 */
public class CustOSSAuthCredentialsProvider extends OSSFederationCredentialProvider {

    private String authServerUrl;

    public CustOSSAuthCredentialsProvider(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }


    @Override
    public OSSFederationToken getFederationToken() throws ClientException {
        OSSFederationToken authToken;
        String authData;
        try {
            URL stsUrl = new URL(authServerUrl);
            HttpURLConnection conn = (HttpURLConnection) stsUrl.openConnection();
            conn.setConnectTimeout(10000);
            InputStream input = conn.getInputStream();
            authData = IOUtils.readStreamAsString(input, OSSConstants.DEFAULT_CHARSET_NAME);
            JSONObject jsonObj = new JSONObject(authData);
            int statusCode = jsonObj.getInt("code");
            if (statusCode == 0) {
                JSONObject data =  new JSONObject(jsonObj.getString("data"));
                String ak = data.getString("accessKeyId");
                String sk = data.getString("accessKeySecret");
                String token = data.getString("securityToken");
                String expiration = data.getString("expiration");
                authToken = new OSSFederationToken(ak, sk, token, expiration);
            } else {
                String errorCode = jsonObj.getString("code");
                String errorMessage = jsonObj.getString("message");
                throw new ClientException("ErrorCode: " + errorCode + "| ErrorMessage: " + errorMessage);
            }
            return authToken;
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }
}
