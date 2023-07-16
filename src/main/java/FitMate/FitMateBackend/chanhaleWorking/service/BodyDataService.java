package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.form.bodyData.BodyDataForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.BodyDataRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BodyDataService {
    private final BodyDataRepository bodyDataRepository;
    private final UserRepository userRepository;
    private final BodyDataDateComparator bodyDataDateComparator = new BodyDataDateComparator();

    @Transactional
    public String createBodyData(Long userId, BodyDataForm bodyDataForm) {
        User user = userRepository.findOne(userId);
        List<BodyData> bodyDataList = user.getBodyDataHistory();
        for (BodyData bodyData : bodyDataList) {
            log.info("{} : {}", bodyData.getDate());
            if (bodyData.getDate().compareTo(bodyDataForm.getDate()) == 0) {
                return "중복된 날짜 (하루에 한 건만 등록 가능)";
            }
        }

        BodyData bodyData = BodyData.createBodyData(bodyDataForm);
        bodyDataRepository.save(bodyData);
        user.addBodyDataHistory(bodyData);
        bodyData.setUser(user);
        return bodyData.getId().toString();
    }

    @Transactional(readOnly = true)
    public BodyData getBodyData(Long bodyDataId) {
        return bodyDataRepository.findById(bodyDataId);
    }

    @Transactional(readOnly = true)
    public List<BodyData> getBodyDataBatch(Long userId, Long pageNum) {
        User user = userRepository.findOne(userId);
        List<BodyData> result = new ArrayList<>();
        List<BodyData> list = user.getBodyDataHistory();
        list.sort(bodyDataDateComparator);
        if (list.size() > (pageNum - 1) * ServiceConst.PAGE_BATCH_SIZE) {
            Long idx = (pageNum - 1) * ServiceConst.PAGE_BATCH_SIZE;
            while (idx < list.size() && idx < pageNum * ServiceConst.PAGE_BATCH_SIZE) {
                result.add(list.get(idx.intValue()));
                idx++;
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public BodyData getRecentBodyData(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return null;
        }
        List<BodyData> list = user.getBodyDataHistory();
        list.sort(bodyDataDateComparator);
        return list.get(0);
    }

    @Transactional
    public void deleteBodyData(Long bodyDataId) {
        bodyDataRepository.deleteBodyData(bodyDataId);
    }
}

class BodyDataDateComparator implements Comparator<BodyData> {
    // 최근 데이터가 앞(낮은 인덱스)에 오도록 하는 정렬
    @Override
    public int compare(BodyData bd1, BodyData bd2) {
        return bd2.getDate().compareTo(bd1.getDate());
    }
}