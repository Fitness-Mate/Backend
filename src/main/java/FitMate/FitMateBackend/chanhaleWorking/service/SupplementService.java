package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementSearchForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRepository;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.supplement.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SupplementService {
    private final SupplementRepository supplementRepository;

    private String supplementString = "";

    @Transactional
    public Long createSupplement(SupplementForm supplementForm) throws IOException {
        Supplement supplement;
        if (supplementForm.getSupplementType() == SupplementType.BCAA) {
            supplement = new BCAA(supplementForm);
        } else if (supplementForm.getSupplementType() == SupplementType.Gainer) {
            supplement = new Gainer(supplementForm);
        } else {
            supplement = new Protein(supplementForm);
        }
        // 이미지 등록 절차
        if (!supplementForm.getImage().isEmpty()) {// 이미지를 업로드 했다면,
            String newImage = FileStoreService.storeFile(supplementForm.getImage()); //  업로드 한 이미지 저장
            supplement.setImagePath(newImage); // 이미지 경로 등록
        }
        else  // 이미지 업로드 안했다면 디폴트 이미지로 등록
            supplement.setImagePath(ServiceConst.DEFAULT_IMAGE_PATH);

        supplementRepository.save(supplement);
        updateSupplementString();
        return supplement.getId();
    }

    public Supplement findSupplementById(Long id) {
        return supplementRepository.findById(id);
    }
    @Transactional
    public Long updateSupplement(Long id, SupplementForm supplementForm) throws IOException {
        Supplement supplement = supplementRepository.findById(id);
        if (supplement == null) { // 바꾸고자 하는 대상이 없는 비정상 요청
            return id;

        } else { // 바꾸고자 하는 대상이 entity가 있는 정상상태

            if (supplement.getType() == supplementForm.getSupplementType()) { // 같은 타입의 경우 (entity 신규 생성 불필요)

                // 기존 이미지와 다른 이미지일 경우 이미지 업데이트
                if (!supplementForm.getImage().getOriginalFilename().equals(supplement.getImagePath())) {
                    String oldImage = supplement.getImagePath();
                    if (!oldImage.equals(ServiceConst.DEFAULT_IMAGE_PATH)) {
                        File file = new File(ServiceConst.IMAGE_DIRECTORY_LCH + oldImage);
                        log.info(file.getAbsolutePath());
                        if (file.exists()) {
                            log.info(oldImage);

                            if (file.delete()) {
                                log.info("{} 파일이 삭제되었습니다.", ServiceConst.IMAGE_DIRECTORY_LCH + oldImage);
                            }
                        }
                    }
                    String newImage = FileStoreService.storeFile(supplementForm.getImage());
                    supplement.setImagePath(newImage);
                }
                // 텍스트 정보 업데이트
                if (supplement.getType() == SupplementType.BCAA) {
                    BCAA sp = (BCAA)supplement;
                    sp.updateFields(supplementForm);
                    updateSupplementString();
                    return id;
                } else if (supplement.getType() == SupplementType.Gainer) {
                    Gainer sp = (Gainer) supplement;
                    sp.updateFields(supplementForm);
                    updateSupplementString();
                    return id;
                } else if (supplement.getType() == SupplementType.Protein) {
                    Protein sp = (Protein) supplement;
                    sp.updateFields(supplementForm);
                    updateSupplementString();
                    return id;
                }
                log.error("update할 대상의 SupplementType 필드와 매칭되는 SupplementType이 없음");
                return id;


            }else { // 타입이 다른 경우 (기존 entity 삭제와 신규 Entity 생성 필요)

                supplementRepository.deleteSupplement(id);
                return createSupplement(supplementForm);

            }
        }
    }
//
//    @Transactional
//    public void updateSupplementImage(Long id, MultipartFile multipartFile) throws IOException {
//        Supplement supplement = supplementRepository.findById(id);
//        if (supplement == null)
//            return;
//        String oldImage = supplement.getImagePath();
//        if (!oldImage.equals(ServiceConst.DEFAULT_IMAGE_PATH)) {
//            File file = new File(ServiceConst.DEFAULT_IMAGE_PATH + oldImage);
//            if (file.exists()) {
//
//                if (file.delete()) {
//                    log.info("{} 파일이 삭제되었습니다.", ServiceConst.DEFAULT_IMAGE_PATH+oldImage);
//                }
//            }
//        }
//        String newImage = FileStoreService.storeFile(multipartFile);
//        supplement.setImagePath(newImage);
//    }

    @Transactional
    public void deleteSupplement(Long id) {
        Supplement supplement = supplementRepository.findById(id);
        if (supplement == null)
            return;
        String supImg = supplement.getImagePath();
        if (!supImg.equals(ServiceConst.DEFAULT_IMAGE_PATH)) {
            File file = new File(ServiceConst.DEFAULT_IMAGE_PATH + supImg);
            if (file.exists()) {
                if (file.delete()) {
                    log.info("{} 파일이 삭제되었습니다.", ServiceConst.DEFAULT_IMAGE_PATH+supImg);
                }
            }
        }
        supplementRepository.deleteSupplement(id);
    }

    public List<Supplement> getSupplementBatch(Long page) {
        return supplementRepository.getSupplementBatch(page);
    }

    public String getSupplementString() {
        if (supplementString.equals("")) {
            for (Supplement s : supplementRepository.findAll()) {
                supplementString = supplementString.concat(s.createIntroduction());
            }
        }
        return supplementString;
    }

    private void updateSupplementString(){
        supplementString = "";
        for (Supplement s : supplementRepository.findAll()) {
            supplementString = supplementString.concat(s.createIntroduction());
        }
    }
    public List<Supplement> searchSupplementBatch(Long page, SupplementSearchForm form){
        return supplementRepository.searchSupplement(page, form.getSupplementType(), form.getSearchKeyword());
    }
}
