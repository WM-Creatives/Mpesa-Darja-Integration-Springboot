package co.ke.willsprojects.daraja.Components;

import co.ke.willsprojects.daraja.JsonSchemas.AuthorizationResponse;
import co.ke.willsprojects.daraja.JsonSchemas.C2BSimulationRequest;
import co.ke.willsprojects.daraja.JsonSchemas.C2BSimulationResponse;
import co.ke.willsprojects.daraja.Utils.CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@Slf4j
public class B2CSimulation {
    @Autowired
    private RestTemplate template;

    @Autowired
    private MpesaAuthentication authentication;

    public C2BSimulationResponse simulate(C2BSimulationRequest request) {
        AuthorizationResponse response = authentication.authenticate();
        if (!Objects.isNull(response)) {
            log.error("M-PESA Authorization could not be done");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + response.getAccessToken());
        HttpEntity<C2BSimulationRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<C2BSimulationResponse> responseEntity = template.exchange(CONSTANTS.b2cSimulationUrl, HttpMethod.POST, requestEntity, C2BSimulationResponse.class);
        return responseEntity.getBody();
    }
}
