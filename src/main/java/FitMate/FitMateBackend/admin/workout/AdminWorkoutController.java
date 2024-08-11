package FitMate.FitMateBackend.admin.workout;

import static FitMate.FitMateBackend.common.ApiResponseUtil.success;

import FitMate.FitMateBackend.common.ApiPageRequest;
import FitMate.FitMateBackend.workout.dto.WorkoutRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/workout")
@Slf4j(topic = "AdminWorkoutController")
public class AdminWorkoutController {

    private final AdminWorkoutService adminWorkoutService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String create(@ModelAttribute WorkoutRequest request) {
        adminWorkoutService.create(request);
        return success();
    }

    @GetMapping("/{id}")
    public String read(@PathVariable("id") Long id) {
        return success(adminWorkoutService.read(id));
    }

    @GetMapping("")
    public String readAll(@ModelAttribute ApiPageRequest request) {
        return success(adminWorkoutService.readList(request.makePageable()));
    }

    @PutMapping("/{id}")
    public String update(@Validated @RequestBody WorkoutRequest request, @PathVariable("id") Long id) {
        adminWorkoutService.update(request, id);
        return success();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminWorkoutService.delete(id);
        return success();
    }
}
