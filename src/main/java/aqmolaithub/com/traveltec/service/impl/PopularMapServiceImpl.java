package aqmolaithub.com.traveltec.service.impl;

import aqmolaithub.com.traveltec.dto.PopularMapDto;
import aqmolaithub.com.traveltec.entity.PopularMap;
import aqmolaithub.com.traveltec.payload.PopularMapPayload;
import aqmolaithub.com.traveltec.repo.PopularMapRepository;
import aqmolaithub.com.traveltec.service.PopularMapService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PopularMapServiceImpl implements PopularMapService {

    private final PopularMapRepository popularMapRepository;

    @Override
    public List<PopularMapDto> getPopularMaps() {
        return this.popularMapRepository.findAll()
                .stream()
                .map(this::getPopularMapDto)
                .toList();
    }

    @Override
    public PopularMapDto getPopularMap(int id) {
        return this.popularMapRepository.findById(id)
                .map(this::getPopularMapDto)
                .orElseThrow(() -> new NoSuchElementException("Популярного места с таким id не существует!"));
    }

    @Override
    @Transactional
    public PopularMapDto createPopularMap(PopularMapPayload payload) {
        PopularMap popularMap = PopularMap.builder()
                .title(payload.getTitle())
                .description(payload.getDescription())
                .mapUrl(payload.getMapUrl())
                .build();
        PopularMap saved = this.popularMapRepository.save(popularMap);
        return getPopularMapDto(saved);
    }

    private PopularMapDto getPopularMapDto(PopularMap map) {
        return PopularMapDto.builder()
                .id(map.getId())
                .title(map.getTitle())
                .description(map.getDescription())
                .mapUrl(map.getMapUrl())
                .build();
    }

}
