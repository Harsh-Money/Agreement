package com.app.agreement.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class ImageUploadImpl implements ImageUpload{

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file)throws Exception {
            Map data = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
    }
}
