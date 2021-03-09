package solankibhavin.keycloak.authenticator.gateway;

import pl.smsapi.OAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.ClientException;
import pl.smsapi.exception.SmsapiException;
import pl.smsapi.proxy.ProxyNative;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bhavin Solanki, https://www.n-k.de, @solankibhavin
 */

public class SmsApiService  implements SmsService {
    final static String urlForPlSmsapi = "http://api.smsapi.pl/";
    final static String urlForComSmsapi = "http://api.smsapi.com/";
    
    private final String senderId;

    SmsApiService(Map<String, String> config) {
		senderId = config.get("senderId");
	}

    @Override
    public void send(String phoneNumber, String message) {
        try {
            String oauthToken = "00000000000000000000000000000000";//senderId;
            OAuthClient client = new OAuthClient(oauthToken);
	    ProxyNative proxyToPlOrComSmsapi = new ProxyNative(urlForPlSmsapi);

            SmsFactory smsApi = new SmsFactory(client, proxyToPlOrComSmsapi);
            //String phoneNumber = "000000000";
            SMSSend action = smsApi.actionSend()
                    .setText("test")//message
                    .setTo(phoneNumber);

            StatusResponse result = action.execute();

            for (MessageResponse status : result.getList() ) {
                System.out.println(status.getNumber() + " " + status.getStatus());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (SmsapiException e) {
            e.printStackTrace();
        }
    }
}
