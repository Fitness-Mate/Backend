package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.dto.RecommendedSupplementDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementFlavorDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementRecommendationDto;
import FitMate.FitMateBackend.chanhaleWorking.form.recommendation.SupplementRecommendationForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.RecommendedSupplementRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRecommendationRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.exception.errorcodes.RecommendErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.RecommendException;
import FitMate.FitMateBackend.common.util.DeepLTranslateUtil;
import FitMate.FitMateBackend.recommend.entity.RecommendedSupplement;
import FitMate.FitMateBackend.recommend.entity.SupplementRecommendation;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j(topic = "SupplementRecommendationService")
public class SupplementRecommendationService {
    private final SupplementRecommendationRepository supplementRecommendationRepository;
    private final UserRepository userRepository;
    private final SupplementRepository supplementRepository;
    private final RecommendedSupplementRepository recommendedSupplementRepository;
    private final DeepLTranslateUtil deepLTranslateUtil;

    @Transactional
    public Long createSupplementRecommendation(Long userId, SupplementRecommendationForm supplementRecommendationForm) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return null;
        }

        SupplementRecommendation supplementRecommendation = SupplementRecommendation
                .createSupplementRecommendation(user.getBodyDataHistory().get(0), user, supplementRecommendationForm.getPurpose(), supplementRecommendationForm.getMonthlyBudget());
        supplementRecommendationRepository.save(supplementRecommendation);
        user.addRecommendationHistory(supplementRecommendation);
        return supplementRecommendation.getId();
    }

    public SupplementRecommendation findById(Long userId, Long supplementRecommendationId) {
        return supplementRecommendationRepository.findById(userId, supplementRecommendationId);
    }

  /*  @Transactional
    public void updateGptResponse(Long userId, Long recommendationId, String response) throws Exception {
        SupplementRecommendation supplementRecommendation = supplementRecommendationRepository.findById(userId, recommendationId);
        if (supplementRecommendation == null)
            return;

        int numStart = -1;
        int numEnd = -1;
        while (true) {
            numStart = response.indexOf(ServiceConst.RECOMMEND_PREFIX, numStart+1);
            if (numStart == -1) {
                break;
            }
            numEnd = response.indexOf(ServiceConst.RECOMMEND_SUFFIX, numEnd+1);
            Long number = Long.parseLong(response.substring(numStart + ServiceConst.RECOMMEND_PREFIX.length(), numEnd));
            int strEnd = response.indexOf(ServiceConst.RECOMMEND_PREFIX, numStart + 1);
            if (strEnd == -1) {
                strEnd = response.length()-1;
            }
            String str = response.substring(numEnd + ServiceConst.RECOMMEND_SUFFIX.length(), strEnd);
            String strKor = deepLTranslateService.sendRequest(str);
            Supplement supplement = supplementRepository.findById(number);
            if (supplement == null) {
                continue;
            }
            RecommendedSupplement recommendedSupplement = RecommendedSupplement.createRecommendedSupplement(supplement, str, strKor);
            recommendedSupplementRepository.save(recommendedSupplement);
            supplementRecommendation.addRecommendSupplements(recommendedSupplement);
        }
    }*/
       @Transactional
        public void updateGptResponse(Long userId, Long recommendationId, String response) throws Exception {
            SupplementRecommendation supplementRecommendation = supplementRecommendationRepository.findById(userId, recommendationId);
            if (supplementRecommendation == null)
                return;

            int numStart = -1;
            int numEnd = -1;
            int numStartKor = -1;
            int prevReasonEnd = 0;
            int reasonStart;
            StringBuilder sb = new StringBuilder();
           response = response.replace("   ", "");
           log.info(response);
           while (prevReasonEnd > -1) {
               //EN_REASON_PREFIX("Reason:") 시작 index 탐색
               reasonStart = response.indexOf(ServiceConst.EN_REASON_PREFIX, prevReasonEnd + 1);
               sb.append(ServiceConst.KOR_REASON_PREFIX);

               //EN_REASON_SUFFIX("\n\n") 끝 index 탐색
               prevReasonEnd = response.indexOf(ServiceConst.EN_REASON_SUFFIX, reasonStart + 1);
               log.info("len=[{}] resS=[{}] resE[{}]",response.length(), reasonStart, prevReasonEnd);

               //ChatGPT 보조제 상세 답변 데이터 [reasonStart-prevReason] substring
               if (prevReasonEnd == -1) sb.append(response.substring(reasonStart + 1 + ServiceConst.EN_REASON_PREFIX.length()));
               else sb.append(response.substring(reasonStart + 1 + ServiceConst.EN_REASON_PREFIX.length(), prevReasonEnd));

               sb.append(ServiceConst.KOR_REASON_SUFFIX);
           }
            String responseKor = deepLTranslateUtil.sendRequest(sb.toString());
           prevReasonEnd = -1;
            while (true) {
                numStart = response.indexOf(ServiceConst.RECOMMEND_PREFIX, numStart+1);
                numStartKor = responseKor.indexOf(ServiceConst.RECOMMEND_PREFIX, numStartKor+1);
                if (numStart == -1) {
                    break;
                }
                numEnd = response.indexOf(ServiceConst.RECOMMEND_SUFFIX, numEnd+1);
                Long number = Long.parseLong(response.substring(numStart + ServiceConst.RECOMMEND_PREFIX.length(), numEnd));
                Supplement supplement = supplementRepository.findById(number);
                if (supplement == null) {
                    continue;
                }
                reasonStart = responseKor.indexOf(ServiceConst.KOR_REASON_PREFIX, prevReasonEnd + 1) + ServiceConst.KOR_REASON_PREFIX.length();
                prevReasonEnd = responseKor.indexOf(ServiceConst.KOR_REASON_SUFFIX, reasonStart + 1);
                String strKor = responseKor.substring(reasonStart+1, prevReasonEnd);
                log.info(strKor);
                RecommendedSupplement recommendedSupplement = RecommendedSupplement.createRecommendedSupplement(supplement, strKor);
                recommendedSupplementRepository.save(recommendedSupplement);
                supplementRecommendation.addRecommendSupplements(recommendedSupplement);
            }
        }
    public SupplementRecommendation getSupplementRecommendation(Long userId, Long supplementRecommendationId) {
        SupplementRecommendation recommendation = supplementRecommendationRepository.findById(userId, supplementRecommendationId);
        if(recommendation.getRecommendedSupplements().isEmpty()){
            throw new RecommendException(RecommendErrorCode.RECOMMEND_NOT_FOUND_EXCEPTION, 404);
        }
        return recommendation;
    }

    public List<SupplementRecommendation> getSupplementRecommendationBatch(Long userId, Long pageNumber) {
        return supplementRecommendationRepository.getBatch(userId, pageNumber);
    }

    public SupplementRecommendationDto createSupplementRecommendationDto(SupplementRecommendation sr){
        SupplementRecommendationDto srd = SupplementRecommendationDto.createSupplementRecommendationDto(sr);
        for (RecommendedSupplement recommendedSupplement : sr.getRecommendedSupplements()) {
            List<SupplementFlavorDto> supplementLineup = supplementRepository.getSupplementLineup(recommendedSupplement.getSupplement());
            srd.addRecommendedSupplement(new RecommendedSupplementDto(recommendedSupplement.getSupplement(), supplementLineup, recommendedSupplement.getKoreanRecommendationString()));

        }
        return srd;
    }
}
