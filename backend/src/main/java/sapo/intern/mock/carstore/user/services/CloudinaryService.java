package sapo.intern.mock.carstore.user.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryService {
    private Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzo94imrh",
                "api_key", "245844245726586",
                "api_secret", "gnMQPlIyKkGQPXXLBl1XqQ-sBq4"));
    }

    public String uploadImage(MultipartFile imageFile) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        return (String) result.get("url");
    }
}
