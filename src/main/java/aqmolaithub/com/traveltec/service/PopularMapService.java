package aqmolaithub.com.traveltec.service;

import aqmolaithub.com.traveltec.dto.PopularMapDto;
import aqmolaithub.com.traveltec.payload.PopularMapPayload;

import java.util.List;

public interface PopularMapService {

    List<PopularMapDto> getPopularMaps();

    PopularMapDto getPopularMap(int id);

    PopularMapDto createPopularMap(PopularMapPayload payload);

}
