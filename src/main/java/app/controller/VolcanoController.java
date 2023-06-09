package app.controller;

import app.exception.NotFoundException;
import app.model.VolcanoDTO;
import app.service.VolcanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class VolcanoController {
    public static final String VOLCANO_PATH = "/api/v1/volcano";
    public static final String VOLCANO_PATH_ID = VOLCANO_PATH + "/{id}";
    public static final String VOLCANO_ADD = VOLCANO_PATH + "/add";
    private final VolcanoService volcanoService;


    @GetMapping({VOLCANO_PATH, VOLCANO_PATH + "/"})
    public Page<VolcanoDTO> volcanoList(@RequestParam(required = false) String country,
                                        @RequestParam(required = false) String region,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String sortName) {
        return volcanoService.volcanoList(country, region, pageNumber, pageSize, sortName);
    }

    @GetMapping(VOLCANO_PATH_ID)
    public VolcanoDTO getVolcanoById(@PathVariable("id") UUID id) {
        return volcanoService.getVolcanoById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(VOLCANO_ADD)
    public ResponseEntity<Void> addVolcano(@Validated @RequestBody VolcanoDTO volcanoDTO) {
        VolcanoDTO saveVolcanoDTO = volcanoService.addVolcano(volcanoDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", VOLCANO_PATH + "/" + saveVolcanoDTO.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(VOLCANO_PATH_ID)
    public ResponseEntity<Void> updateVolcanoById(@PathVariable("id") UUID id, @Validated @RequestBody VolcanoDTO volcanoDTO) {
        if (volcanoService.updateVolcanoById(id, volcanoDTO).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(VOLCANO_PATH_ID)
    public ResponseEntity<Void> deleteVolcanoById(@PathVariable("id") UUID id) {
        if (volcanoService.deleteVolcanoById(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        throw new NotFoundException();
    }

}
