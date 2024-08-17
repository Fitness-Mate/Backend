package FitMate.FitMateBackend.common.util;

import static org.hibernate.query.sqm.tree.SqmNode.log;

import com.deepl.api.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeepLTranslateUtil {
    @Value("${deepl-api-key}")
    private String key;

    /**
     * DeepL Public GitHub Documentation
     * https://github.com/DeepLcom/deepl-java
     * */
    public String sendRequest(String source) throws Exception {
        Translator translator = new Translator(key);
        String target = translator.translateText(source, "EN", "KO").getText();

        log.info("[ 번역 완료 ]");
        log.info("source: " + source);
        log.info("target: " + target);

        return target; //번역문 반환
    }
}