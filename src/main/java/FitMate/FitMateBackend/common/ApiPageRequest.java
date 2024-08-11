package FitMate.FitMateBackend.common;

import FitMate.FitMateBackend.consts.ServiceConst;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @NotNull(message = "sort must not be null (sort)")
    private String sort;

    @Pattern(regexp = "ASC|DESC", message = "direction must be either 'ASC' or 'DESC'")
    @NotNull(message = "direction must not be null (direction)")
    private String direction;

    public PageRequest makePageable() {
        return PageRequest.of(page-1, size, Sort.by(Sort.Direction.valueOf(direction), sort));
    }
}
