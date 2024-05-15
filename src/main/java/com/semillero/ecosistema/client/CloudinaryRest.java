package com.semillero.ecosistema.client;

import com.cloudinary.Cloudinary;
import com.semillero.ecosistema.configs.CloudinaryConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Data
@Service
public class CloudinaryRest {


    private static Map<String, File> hashToImagePathMap  = new HashMap<>();
    @Autowired
    private  Cloudinary cloudinary;
    public Map<String, String> postImageCloudinary(String body) throws IOException {

            try {
                byte[] imageData = Base64.getDecoder().decode(body);
                Map<String, String> result = new HashMap<>();

                // Subir la imagen a Cloudinary
                Map<String, Object> uploadResult = cloudinary.uploader().upload(imageData, ObjectUtils.asMap( "upload_preset", "ecosistema"));
                System.out.println(uploadResult);
                result.put("url", (String) uploadResult.get("url"));
                result.put("public_id", (String) uploadResult.get("public_id"));
                return result;
            } catch (IOException e) {
                throw e;
            }
        }

    public Boolean destroyImage(String public_id) throws IOException {
        try{
            Map<String, Object> uploadResult = cloudinary.uploader().destroy(public_id, ObjectUtils.asMap());
            return true;
        }catch (IOException e){
            throw e;
        }
    }


}




