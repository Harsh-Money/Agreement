package com.app.agreement.controller;

import com.app.agreement.dto.AgreementDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.entity.AgreementEntity;
import com.app.agreement.service.AgreementService;
import com.app.agreement.vo.AgreementVo;
import com.app.agreement.vo.ClientOwnerAgreementVoIDs;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agreement")
public class Agreements {

    @Autowired
    private AgreementService agreementService;

    @GetMapping
    public ResponseEntity<List<AgreementEntity>> getAllAgreement() {
        try{
            List<AgreementEntity> agreementEntities = agreementService.getAllAgreement();
            return new ResponseEntity<>(agreementEntities, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @PutMapping
    public ResponseEntity<Boolean> updateAgreement(@RequestBody AgreementVo agreementVo) {
        try{
            AgreementDto agreementDto = new AgreementDto();
            BeanUtils.copyProperties(agreementVo,agreementDto);
            Boolean result = agreementService.updateAgreementDetails(agreementDto);
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
    public ResponseEntity<?> setAgreement(@RequestBody AgreementVo agreementVo){
        try{
            AgreementDto agreementDto = new AgreementDto();
            BeanUtils.copyProperties(agreementVo,agreementDto);
            Boolean result = agreementService.setAgreementDetails(agreementDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAgreement(@PathVariable(name = "id") Integer id){
        try {
            Boolean result = agreementService.deleteAgreementExist(id);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/new-agreement")
    public ResponseEntity<?> setNewAgreement(@RequestBody ClientOwnerAgreementVoIDs clientOwnerAgreementVoIDs){
        try {
            ClientOwnerAgreementDtoIDs clientOwnerAgreementDtoIDs = new ClientOwnerAgreementDtoIDs();
            BeanUtils.copyProperties(clientOwnerAgreementVoIDs, clientOwnerAgreementDtoIDs);
            Boolean result = agreementService.setNewAgreement(clientOwnerAgreementDtoIDs);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
}
