package aqmolaithub.com.traveltec.controller;

import aqmolaithub.com.traveltec.dto.PopularMapDto;
import aqmolaithub.com.traveltec.payload.PopularMapPayload;
import aqmolaithub.com.traveltec.service.PopularMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel-assistant-api/v1/popularMaps")
@RequiredArgsConstructor
public class PopularMapController {

    private final PopularMapService popularMapService;

    @GetMapping
    public ResponseEntity<List<PopularMapDto>> getPopularMaps() {
        List<PopularMapDto> dtos = this.popularMapService.getPopularMaps();
        if(dtos == null || dtos.isEmpty()) {
            return ResponseEntity.noContent()
                    .build();
        }
        return ResponseEntity.ok()
                .body(dtos);
    }

    @GetMapping("/{popularMapId}")
    public ResponseEntity<PopularMapDto> getPopularMapById(@PathVariable("popularMapId") int popularMapId) {
        PopularMapDto dto = this.popularMapService.getPopularMap(popularMapId);
        if(dto == null) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.ok()
                .body(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPopularMap(@RequestBody PopularMapPayload payload) {
        PopularMapDto dto = this.popularMapService.createPopularMap(payload);
        if(dto == null) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
