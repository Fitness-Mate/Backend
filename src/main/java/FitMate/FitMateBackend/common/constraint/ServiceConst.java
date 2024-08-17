package FitMate.FitMateBackend.common.constraint;

public interface ServiceConst {
    public static final Integer PAGE_BATCH_SIZE = 12;
    // 파일 저장 디렉터리
    public static final String IMAGE_DIRECTORY = "/images/";

    // 디폴트 이미지 파일 (상대)경로: 이미지 저장 경로
    public static final String DEFAULT_WORKOUT_IMAGE_NAME = "default_workout_image.png";
    public static final String DEFAULT_IMAGE_NAME = "default_image.png";

    // recommend 에서 보충제를 식별하기 위해 두르는 접두사, 접미사
    // ex) 53번 보충제: RECOMMEND_PREFIX+53+RECOMMEND_SUFFIX
    public static final String RECOMMEND_PREFIX = "<<";
    public static final String RECOMMEND_SUFFIX = ">>";
    public static final String EN_REASON_PREFIX = "Reasons:";
    public static final String EN_REASON_SUFFIX = "\n\n";
    public static final String KOR_REASON_PREFIX = "%%";
    public static final String KOR_REASON_SUFFIX = "$$";

    //s3 directory constraint
//    public static final String S3_URL = "https://fitmate-bucket.s3.ap-northeast-2.amazonaws.com/images/";
    public static final String S3_URL = "https://fitness-mate.s3.ap-northeast-2.amazonaws.com/images/";
    public static final String S3_DIR_WORKOUT = "workout";
    public static final String S3_DIR_SUPPLEMENT = "supplement";

    // mail service config
//    public static final String MAIL_SERVER_ADDRESS = "http://fitmate-mail-svc-clusterip:8081";
    public static final String MAIL_SERVER_ADDRESS = "http://43.200.20.25:8081";
    public static final String MAIL_SERVER_MEDIA_TYPE = "application/json";


    public static final String SEX_MALE = "남성";
    public static final String SEX_FEMALE = "여성";

    //routine constraint
    public static final int ROUTINE_MAX_SIZE = 5;
    public static final int MY_WORKOUT_MAX_SIZE = 7;
    public static final int MY_SUPPLEMENT_MAX_SIZE = 7;

    // page request constraint
    public static final int MAX_PAGE_OFFSET_SIZE = 100;
    public static final int MIN_PAGE_OFFSET_SIZE = 1;
    public static final int MAX_PAGE_LIMIT_SIZE = 20;
    public static final int MIN_PAGE_LIMIT_SIZE = 1;
}