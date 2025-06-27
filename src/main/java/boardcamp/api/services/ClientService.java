package boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boardcamp.api.dtos.ClientDTO;
import boardcamp.api.models.ClientModel;
import boardcamp.api.models.GameModel;
import boardcamp.api.repositories.ClientRepository;

@Service
public class ClientService {
    final ClientRepository clientRepository;

    ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ClientModel> getClients(){
        return clientRepository.findAll();
    }

    public Optional<ClientModel> getClientId(Long id){
        Optional<ClientModel> client = clientRepository.findById(id);

        if(!client.isPresent()){
            return Optional.empty();
        }else{
            return client;
        }
    }

    public Optional<ClientModel> postClient(ClientDTO body){
        if(clientRepository.existsByName(body.getName())){
            return Optional.empty();
        }

        ClientModel client = new ClientModel(body);
        clientRepository.save(client);
        return Optional.of(client);
    }
    
}
