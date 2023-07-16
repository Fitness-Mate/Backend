package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.config.ChatGptConfig;
import FitMate.FitMateBackend.chanhaleWorking.dto.ChatGptRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.ChatGptResponseDto;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRecommendationRepository;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutRecommendationService;
import FitMate.FitMateBackend.domain.recommendation.SupplementRecommendation;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${api-key}")
    private String apiKey;
    private final SupplementRecommendationService supplementRecommendationService;
    private final WorkoutRecommendationService workoutRecommendationService;

    private static RestTemplate restTemplate = new RestTemplate();

/**
 * input으로 recommendationID와 chat GPT에게 넘길 질문을 넘긴다.
 */
    @Async("threadPoolTaskExecutor")
    public void sendRequest(Long userId, Long recommendationId, String question) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        HttpEntity<ChatGptRequestDto> httpEntity = new HttpEntity<>(new ChatGptRequestDto(question), headers);

        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(ChatGptConfig.URL, httpEntity, ChatGptResponseDto.class);
        log.info("\n======================\n{} 에 대한 response 도착! \n======================\n{}", question, responseEntity.getBody().getChoices().get(0).getMessage().get("content"));


        String gptResponse = responseEntity.getBody().getChoices().get(0).getMessage().get("content");
        // recommend 서비스에게 recommendation에 gpt추천문을 업데이트 하라고 요청
        supplementRecommendationService.updateGptResponse(userId, recommendationId, gptResponse);
    }

    @Async("threadPoolTaskExecutor")
    public void sendWorkoutRequest(Long userId, Long recommendationId, String question) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        HttpEntity<ChatGptRequestDto> httpEntity = new HttpEntity<>(new ChatGptRequestDto(question), headers);

        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(ChatGptConfig.URL, httpEntity, ChatGptResponseDto.class);
        log.info("\n======================\n{} 에 대한 response 도착! \n======================\n{}", question, responseEntity.getBody().getChoices().get(0).getMessage().get("content"));

        String gptResponse = responseEntity.getBody().getChoices().get(0).getMessage().get("content");
        workoutRecommendationService.updateResponse(userId, recommendationId, gptResponse);
    }
}
