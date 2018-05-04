package com.yad.vasilii.gallery.data.repositories;

import com.yad.vasilii.gallery.data.network.*;
import com.yad.vasilii.gallery.data.network.models.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.domain.repositories.ImagesRepository;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

public class ImagesRepositoryImpl implements ImagesRepository {

    private final PixabayApiService mPixabayApiService;

    @Inject
    public ImagesRepositoryImpl(PixabayApiService pixabayApiService) {
        mPixabayApiService = pixabayApiService;
    }

    @Override
    public Single<List<Image>> getImages(String query, int page, int perPage) {
        return mPixabayApiService.getImages(query, page, perPage)
                .map(PixabayApiResponse::getHits)
                .map(this::mapHitsToImages);
    }

    private List<Image> mapHitsToImages(List<Hit> hits) {
        List<Image> images = new ArrayList<>();
        for (Hit hit : hits) {
            images.add(new Image.Builder()
                    .withPreviewImageUrl(hit.getWebformatURL())
                    .withLargeImageUrl(hit.getLargeImageURL())
                    .build()
            );
        }
        return images;
    }
}
