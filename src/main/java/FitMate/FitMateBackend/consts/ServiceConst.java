package FitMate.FitMateBackend.consts;

public interface ServiceConst {
    public static final Integer PAGE_BATCH_SIZE = 10;
    // 파일 저장 디렉터리
    public static final String IMAGE_DIRECTORY_LCH = "/fitmate/img/lch";
    public static final String IMAGE_DIRECTORY_JJS = "/fitmate/img/jjs";

    // 디폴트 이미지 파일 (상대)경로: 이미지 저장 경로
    public static final String DEFAULT_IMAGE_PATH = "Untitled_Artwork.png";
    // recommend 에서 보충제를 식별하기 위해 두르는 접두사, 접미사
    // ex) 53번 보충제: RECOMMEND_PREFIX+53+RECOMMEND_SUFFIX
    public static final String RECOMMEND_PREFIX = "<<";
    public static final String RECOMMEND_SUFFIX = ">>";
}