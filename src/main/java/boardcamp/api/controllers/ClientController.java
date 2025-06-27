package boardcamp.api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boardcamp.api.models.ClientModel;
import boardcamp.api.models.GameModel;
import boardcamp.api.services.ClientService;

@RestController
@RequestMapping("/customers")
public class ClientController {
    final ClientService clientService;

    ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping()
        public ResponseEntity<Object> getClients(){
            return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
        }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientId(@PathVariable("id") Long id){
        Optional<ClientModel> client = clientService.getClientId(id);

        if(!client.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo com esse ID não encontrado");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(client.get());
        }
    }
    
}
