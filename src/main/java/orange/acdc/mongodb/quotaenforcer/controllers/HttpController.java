package orange.acdc.mongodb.quotaenforcer.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import orange.acdc.mongodb.quotaenforcer.model.Database;
import org.cloudfoundry.client.CloudFoundryClient;

import org.cloudfoundry.client.v2.serviceinstances.ListServiceInstancesRequest;
import org.cloudfoundry.client.v2.serviceinstances.ListServiceInstancesResponse;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HttpController {

    @Value("${quota-enforcer.plansize}")
    int planSizeLimit;

    @Autowired
    CoreController coreController;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String indexPage(Model model){
        return homePage(model);
    }


    @GetMapping("/view")
    public String homePage(Model model){
        model.addAttribute("planSizeLimit", planSizeLimit);
        //model.addAttribute("all_databases",getAllDatabases());
        model.addAttribute("quotaEnforcerLogs",coreController.getQuotaEnforcerLogs());
        return "view";
    }
}
