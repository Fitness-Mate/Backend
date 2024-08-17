package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.common.configuration.ChatGptConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ChatGptRequestDto {

    private String model;
    private List<Map<String, String>> messages;
    private Double temperature;

    @Builder
    public ChatGptRequestDto(String question){
        this.model = ChatGptConfig.MODEL;
        messages = new ArrayList();
        messages.add(Map.of( "role" , "user", "content" , question));
        this.temperature = ChatGptConfig.TEMPERATURE;
    }
}
