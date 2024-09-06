package com.app.agreement.controller;

import com.app.agreement.dto.ClientDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.entity.ClientProfile;
import com.app.agreement.service.ClientService;
import com.app.agreement.vo.ClientOwnerAgreementVoIDs;
import com.app.agreement.vo.ClientVo;
import com.cloudinary.Cloudinary;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/client")
public class Client {

    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<List<ClientProfile>> getAllClient() {
        try{
            List<ClientProfile> clientProfiles = clientService.getAllClient();
            return new ResponseEntity<>(clientProfiles, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @PutMapping
    public ResponseEntity<Boolean> updateClient(@RequestBody ClientVo clientVo) {
        try{
            ClientDto clientDto = new ClientDto();
            BeanUtils.copyProperties(clientVo,clientDto);
            Boolean result = clientService.updateClientDetails(clientDto);
            if (result) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        }
    }

    @PostMapping
    public ResponseEntity<?> setClient(@RequestBody ClientVo clientVo){
        try{
            ClientDto clientDto = new ClientDto();
            BeanUtils.copyProperties(clientVo, clientDto);
            Boolean result = clientService.setClientDetails(clientDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(name = "id") Integer id){
        try {
            Boolean result = clientService.deleteClientExist(id);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/agreement-between-client-owner")
    public ResponseEntity<?> setAgreement(@RequestBody ClientOwnerAgreementVoIDs clientOwnerAgreementVoIDs){
        try{
            ClientOwnerAgreementDtoIDs clientOwnerAgreementDtoIDs = new ClientOwnerAgreementDtoIDs();
            BeanUtils.copyProperties(clientOwnerAgreementVoIDs, clientOwnerAgreementDtoIDs);
            Boolean result = clientService.setClientOwnerAgreement(clientOwnerAgreementDtoIDs);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }
}
