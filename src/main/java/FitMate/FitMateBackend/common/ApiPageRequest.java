package FitMate.FitMateBackend.common;

import FitMate.FitMateBackend.consts.ServiceConst;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class ApiPageRequest {

    @Max(value = ServiceConst.MAX_PAGE_OFFSET_SIZE, message = "page must be less than " + ServiceConst.MAX_PAGE_OFFSET_SIZE)
    @Min(value = ServiceConst.MIN_PAGE_OFFSET_SIZE, message = "page must be greater than " + ServiceConst.MIN_PAGE_OFFSET_SIZE)
    @NotNull(message = "page must not be null (page)")
    private Integer page;

    @Max(value = ServiceConst.MAX_PAGE_LIMIT_SIZE, message = "size must be less than " + ServiceConst.MAX_PAGE_LIMIT_SIZE)
    @Min(value = ServiceConst.MIN_PAGE_LIMIT_SIZE, message = "size must be greater than " + ServiceConst.MIN_PAGE_LIMIT_SIZE)
    @NotNull(message = "size must not be null (size)")
    private Integer size;

    public PageRequest makePageable() {
        return PageRequest.of(page-1, size);
    }
}
