package com.wvs.camundaprocessorigination.client;

import com.wvs.camundaprocessorigination.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientClient {

    @Value("${client.client.url}")
    private String clientUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ClientDTO saveClient(final ClientDTO client) {
        ResponseEntity<ClientDTO> response = restTemplate.exchange(clientUrl, HttpMethod.POST, new HttpEntity<>(client), ClientDTO.class);
        return response.getBody();
    }

}
