package com.app.agreement.controller;

import com.app.agreement.dto.AgreementDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.dto.ClientOwnerIDsDto;
import com.app.agreement.entity.AgreementEntity;
import com.app.agreement.service.AgreementService;
import com.app.agreement.service.ImageUpload;
import com.app.agreement.service.WebSocketServiceImpl;
import com.app.agreement.vo.AgreementVo;
import com.app.agreement.vo.ClientOwnerAgreementVoIDs;
import com.app.agreement.vo.ClientOwnerIDsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agreement")
public class Agreements {

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private WebSocketServiceImpl webSocketService;

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
    public ResponseEntity<?> setAgreementDetails(@RequestBody AgreementVo agreementVo){
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

    @PostMapping("/image-upload")
    public ResponseEntity<Map> setAgreementImageToCloudinary(@RequestParam("image") MultipartFile file) {
        try{
            Map imageRespose = imageUpload.upload(file);
            if (imageRespose != null){
                return new ResponseEntity<>(imageRespose, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(imageRespose, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/sign")
    public ResponseEntity<String> getAgreementSigned(@RequestBody ClientOwnerIDsVo clientOwnerIDsVo){
        try {
            ClientOwnerIDsDto clientOwnerIDsDto = new ClientOwnerIDsDto();
            BeanUtils.copyProperties(clientOwnerIDsVo,clientOwnerIDsDto);
            Boolean result = agreementService.getAgreementSigned(clientOwnerIDsDto);
            if (result == true){
                return new ResponseEntity<>("Correct Person", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong person", HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all-agreement-of-ownerid/{id}")
    public ResponseEntity<?> getAllAgreementByOwnerId(@PathVariable(value = "id") Integer id){
        try {
            return new ResponseEntity<>(agreementService.getAllAgreementByOwnerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all-agreement-of-clientid/{id}")
    public ResponseEntity<?> getAllAgreementByClientId(@PathVariable(value = "id") Integer id) {
        try {
            Object allAgreement = agreementService.getAllAgreementByClientId(id);
//            System.out.println("allAgreement: "+allAgreement);
                Thread.sleep(2000);
            List<AgreementEntity> sourceAgreementEntity = (List<AgreementEntity>) allAgreement;
            List<AgreementEntity> targetAgreement = sourceAgreementEntity.stream()
                    .map(agreementEntity -> {
                        AgreementEntity ae = new AgreementEntity();
                        ae.setId(agreementEntity.getId());
                        ae.setStatus(agreementEntity.getStatus());
                        ae.setOwnerProfile(agreementEntity.getOwnerProfile());
                        return ae;
                    }).collect(Collectors.toList());
            webSocketService.sendAllAgreementFoundByClientId(targetAgreement);
            return new ResponseEntity<>(allAgreement, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
