package com.app.agreement.controller;

import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.service.OwnerService;
import com.app.agreement.util.JWTToken;
import com.app.agreement.vo.OwnerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class Owner {

    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerProfile>> getAllOwners(){
        try {
            List<OwnerProfile> ownerProfiles = ownerService.getAllOwner();
            return new ResponseEntity<>(ownerProfiles, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/by-name")
    public ResponseEntity<OwnerProfile> getOwnerByName(@RequestBody OwnerVo ownerVo){
        try {
            return new ResponseEntity<>(ownerService.getOwnerByName(ownerVo.getName()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> setOwnerDetails(@RequestBody OwnerVo ownerVo){
        try{
            OwnerDto ownerDto = new OwnerDto();
            BeanUtils.copyProperties(ownerVo, ownerDto);
            Boolean result = ownerService.setOwnerDetails(ownerDto);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Boolean> updateOwner(@RequestBody OwnerVo ownerVo){
        try {
            OwnerDto ownerDto = new OwnerDto();
            BeanUtils.copyProperties(ownerVo, ownerDto);
            Boolean result = ownerService.updateOwnerDetails(ownerDto);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOwner(@PathVariable(name = "id") Integer id){
        try {
            Boolean result = ownerService.deleteOwnerExist(id);
            if (result == true){
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody OwnerVo ownerVo) {
        OwnerDto ownerDto = new OwnerDto();
        BeanUtils.copyProperties(ownerVo, ownerDto);
        return new ResponseEntity<>(ownerService.verify(ownerDto), HttpStatus.OK);
    }

    @PostMapping("/agreement/approve/{id}")
    public ResponseEntity<Boolean> setAgreementApprove(@PathVariable(value = "id") Integer id){
        try {
            return new ResponseEntity<>(ownerService.setAgreementApprove(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

}
