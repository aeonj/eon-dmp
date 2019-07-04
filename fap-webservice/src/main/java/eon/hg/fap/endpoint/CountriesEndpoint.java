package eon.hg.fap.endpoint;

import eon.hg.fap.webservice.GetCountryRequest;
import eon.hg.fap.webservice.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountriesEndpoint {
    private static final String NAMESPACE_URI="http://127.0.0.1:8080/ws";

    //配置对外接口
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        //response.setCountry(调用的接口);
        return response;
    }
}
