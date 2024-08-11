package FitMate.FitMateBackend.admin.machine;

import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.machine.dto.MachineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMachineService {

    private final AdminMachineRepository adminMachineRepository;

    public PageImpl<MachineResponse> readAll(Pageable pageable) {
        Page<Machine> machineList = adminMachineRepository.findAll(pageable);
        return new PageImpl<>(
            machineList.stream().map(MachineResponse::new).toList(),
            pageable,
            machineList.getTotalElements()
        );
    }
}
