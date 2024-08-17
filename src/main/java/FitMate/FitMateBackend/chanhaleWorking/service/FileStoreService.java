package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 저장과 관련된 업무를 처리하는 객체
 */
@Component
public class FileStoreService {

    public static String getFullPath(String filename) {
        return ServiceConst.IMAGE_DIRECTORY + filename;
    }


    /**
     *  Multipart 파일을 받고 실제 저장을 한 뒤 UploadFile 으로 반환
     */
    public static String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        /**
         *  originalFilename에서 확장자를 추출하여
         *  내부적으로 저장할때 확장자를 남겨놓는다.
         *  ex) qwe-qwe-123-qwe-qwe.jpg
         *
         */
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return storeFileName;
    }



    private static String createStoreFileName(String originalFilename) {
        String ext = extracExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private static String extracExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
