package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.dto.GeneralResponseDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.CodeVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.PurgeRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.SendNewPasswordDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.VerificationRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.CodeVerifyingRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.UuidVerifyingRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.VerificationRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
public class MailVerificationController {

    private static final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;

    @PostMapping("/register/verify/mail")
    @ResponseBody
    public String mailVerificationRequest(@RequestBody VerificationRequestForm verificationRequestForm, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<VerificationRequestDto> httpEntity = new HttpEntity<>(VerificationRequestDto.createVerificationRequestDto(verificationRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/register/verify/mail"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /register/verify/mail request [{}]",responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
    @PostMapping("/register/verify/code")
    @ResponseBody
    public UuidDto codeVerificationRequest(@RequestBody CodeVerifyingRequestForm codeVerifyingRequestForm, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<CodeVerifyingRequestDto> httpEntity = new HttpEntity<>(CodeVerifyingRequestDto.createCodeVerifyingRequestDto(codeVerifyingRequestForm), headers);

        ResponseEntity<UuidDto> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/register/verify/code"), httpEntity, UuidDto.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /register/verify/code request [{}]",responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
    @PostMapping("/register/verify/uuid")
    @ResponseBody
    public String uuidVerificationRequest(@RequestBody UuidVerifyingRequestForm uuidVerifyingRequestForm, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<UuidVerifyingRequestDto> httpEntity = new HttpEntity<>(UuidVerifyingRequestDto.createUuidVerifyingRequestDto(uuidVerifyingRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/register/verify/uuid"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /register/verify/uuid request [{}]",responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

    @PostMapping("/password/verify/mail")
    public GeneralResponseDto findPasswordVerifyMailRequest(@RequestBody VerificationRequestForm verificationRequestForm, HttpServletResponse response){
        GeneralResponseDto result = new GeneralResponseDto();
        if (!userService.checkDuplicatedLoginEmail(verificationRequestForm.getMailAddress())) {
            result.setStatus("ERR");
            result.setMessage("no such e-mail registered!");
            return result;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<VerificationRequestDto> httpEntity = new HttpEntity<>(VerificationRequestDto.createVerificationRequestDto(verificationRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/password/verify/mail"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /password/verify/mail request [{}]",responseEntity.getStatusCode());
            result.setStatus("ERR");
            result.setMessage(responseEntity.getBody());
            return result;
        }
        if (responseEntity.getBody() == null || !responseEntity.getBody().equals("ok")) {
            log.info("error status code responded for /register/verify/code request [{}]",responseEntity.getStatusCode());
            result.setStatus("ERR");
            result.setMessage(responseEntity.getBody());
            return result;
        }
        result.setStatus("ok");
        result.setMessage(responseEntity.getBody());
        return result;
    }
    @PostMapping("/password/verify/code")
    @ResponseBody
    public GeneralResponseDto findPasswordCodeVerifyRequest(@RequestBody CodeVerifyingRequestForm codeVerifyingRequestForm, HttpServletResponse response) {
        GeneralResponseDto result = new GeneralResponseDto();
        if (!userService.checkDuplicatedLoginEmail(codeVerifyingRequestForm.getMailAddress())) {
            result.setStatus("ERR");
            result.setMessage("no such e-mail registered!");
            return result;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<CodeVerifyingRequestDto> httpEntity = new HttpEntity<>(CodeVerifyingRequestDto.createCodeVerifyingRequestDto(codeVerifyingRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/password/verify/code"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /register/verify/code request [{}]",responseEntity.getStatusCode());
            result.setStatus("ERR");
            result.setMessage(responseEntity.getBody());
            return result;
        }
        if (responseEntity.getBody() == null || !responseEntity.getBody().equals("ok")) {
            log.info("error status code responded for /register/verify/code request [{}]",responseEntity.getStatusCode());
            result.setStatus("ERR");
            result.setMessage(responseEntity.getBody());
            return result;
        }

        result = userService.updateUserPassword(codeVerifyingRequestForm.getMailAddress());
        if (result.getStatus().equals("ok")){
            log.info("new password creation request accepted! mail=[{}], newPassword=[{}]", codeVerifyingRequestForm.getMailAddress(), result.getMessage());
            HttpEntity<SendNewPasswordDto> newPasswordDtoHttpEntity = new HttpEntity<>(new SendNewPasswordDto(codeVerifyingRequestForm.getMailAddress(), result.getMessage()), headers);
            // TODO
            // 전송 실패시 패스워드 롤백 처리 또는 전송 재시도하는 코드 구현해야 함
            ResponseEntity<String> responseEntity2 = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/password/send/new"), newPasswordDtoHttpEntity, String.class);
            result.setMessage("new password sent. check your email!");
        }
        result.setMessage("new password sent. check your email!");
        return result;
    }
    @PostMapping("/register/verify/purge")
    @ResponseBody
    public String mailServerPurgeRequest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<PurgeRequestDto> httpEntity = new HttpEntity<>(new PurgeRequestDto(), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/purge"), httpEntity, String.class);
        log.info("purge mail server request");
        return responseEntity.getBody();
    }
}
