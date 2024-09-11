package FitMate.FitMateBackend;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // DB 생성 및 등록 날짜,시간 기록 가능하도록 추가
public class FitMateBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitMateBackendApplication.class, args);
	}
}