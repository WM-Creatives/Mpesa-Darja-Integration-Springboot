package co.ke.willsprojects.daraja.Components;

import co.ke.willsprojects.daraja.JsonSchemas.AuthorizationResponse;
import co.ke.willsprojects.daraja.JsonSchemas.C2BRegisterUrlRequest;
import co.ke.willsprojects.daraja.JsonSchemas.C2BRegisterUrlResponse;
import co.ke.willsprojects.daraja.Utils.CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@Slf4j
public class B2CUrlRegistration {
    @Autowired
    private RestTemplate template;

    @Autowired
    private MpesaAuthentication authentication;

    public C2BRegisterUrlResponse simulate(C2BRegisterUrlRequest request) {
        AuthorizationResponse response = authentication.authenticate();
        if (!Objects.isNull(response)) {
            log.error("M-PESA Authorization could not be done");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + response.getAccessToken());
        HttpEntity<C2BRegisterUrlRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<C2BRegisterUrlResponse> responseEntity = template.exchange(CONSTANTS.c2bUrlRegistrationUrl, HttpMethod.POST, requestEntity, C2BRegisterUrlResponse.class);
        return responseEntity.getBody();
    }
}
