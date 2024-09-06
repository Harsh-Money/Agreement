package com.app.agreement.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageUpload {

    public Map upload(MultipartFile file)throws Exception;
}
