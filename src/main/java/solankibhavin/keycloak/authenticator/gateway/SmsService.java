package solankibhavin.keycloak.authenticator.gateway;

import java.util.Map;

/**
 * @author Bhavin Solanki, @solankibhavin
 */
public interface SmsService {

	void send(String phoneNumber, String message);

}
