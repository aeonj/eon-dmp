package eon.hg.fap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("clients")
public class ClientsController {
    @Autowired
    private JdbcClientDetailsService clientsDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value="/form",method= RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String showEditForm(@RequestParam(value="client",required=false)String clientId, Model model){

        ClientDetails clientDetails;
        if(clientId !=null){
            clientDetails=clientsDetailsService.loadClientByClientId(clientId);
        }
        else{
            clientDetails =new BaseClientDetails();
        }

        model.addAttribute("clientDetails",clientDetails);
        return "form";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String editClient(
            @ModelAttribute BaseClientDetails clientDetails,
            @RequestParam(value = "newClient", required = false) String newClient) {
        if (newClient == null) {
            String pwdEncode = passwordEncoder.encode(clientDetails.getClientSecret());
            clientDetails.setClientSecret(pwdEncode);
            clientsDetailsService.updateClientDetails(clientDetails);
        } else {
            String pwdEncode = passwordEncoder.encode(clientDetails.getClientSecret());
            clientDetails.setClientSecret(pwdEncode);
            clientsDetailsService.addClientDetails(clientDetails);
        }

        if (!clientDetails.getClientSecret().isEmpty()) {
            clientsDetailsService.updateClientSecret(clientDetails.getClientId(), clientDetails.getClientSecret());
        }
        return "redirect:/";
    }

    @RequestMapping(value="{client.clientId}/delete",method = RequestMethod.POST)
    public String deleteClient(@ModelAttribute BaseClientDetails ClientDetails, @PathVariable("client.clientId") String id){
        clientsDetailsService.removeClientDetails(clientsDetailsService.loadClientByClientId(id).toString());
        return "redirect:/";
    }
}
