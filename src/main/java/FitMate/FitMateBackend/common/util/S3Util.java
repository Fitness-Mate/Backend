package FitMate.FitMateBackend.common.util;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.exception.ApiErrorCode;
import FitMate.FitMateBackend.common.exception.ApiException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
@RequiredArgsConstructor
@Slf4j(topic = "S3Util")
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(String classification, MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            String ext = extracExt(Objects.requireNonNull(file.getOriginalFilename()));
            String imageName = uuid + "." + ext;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket + "/images/" + classification, imageName, file.getInputStream(), metadata);

            log.info("uploadImage: {}", imageName);
            return imageName;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ApiException(ApiErrorCode.S3_IMAGE_UPLOAD_EXCEPTION);
        }
    }

    public void deleteImage(String classification, String fileName) {
        amazonS3Client.deleteObject(bucket + "/images/" + classification, fileName);
    }

    public String extracExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    /**
     * 수정 필요
     */
    public static String getAccessURL(String classification, String fileName) {
        if (classification == ServiceConst.S3_DIR_SUPPLEMENT) {
            return ServiceConst.S3_URL + ServiceConst.S3_DIR_SUPPLEMENT+"/" + fileName;
        }
        else if (classification == ServiceConst.S3_DIR_WORKOUT) {
            return ServiceConst.S3_URL + ServiceConst.S3_DIR_WORKOUT+"/" + fileName;

        }
        else
            return ServiceConst.S3_URL + ServiceConst.S3_DIR_WORKOUT + "/" + ServiceConst.DEFAULT_IMAGE_NAME;
    }
}
