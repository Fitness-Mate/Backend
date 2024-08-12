package FitMate.FitMateBackend.admin.supplement;

import FitMate.FitMateBackend.supplement.dto.SupplementListResponse;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSupplementService {

    private final AdminSupplementRepository adminSupplementRepository;

    public PageImpl<SupplementListResponse> readAll(Pageable pageable) {
        Page<Supplement> supplementList = adminSupplementRepository.findAll(pageable);
        return new PageImpl<>(
            supplementList.stream().map(SupplementListResponse::new).toList(),
            pageable,
            supplementList.getTotalElements()
        );
    }

}
