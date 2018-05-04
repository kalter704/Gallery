package com.yad.vasilii.gallery.domain;


import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

public class GalleryInteractor {

    private final ImagesRepository mImagesRepository;

    @Inject
    public GalleryInteractor(ImagesRepository imagesRepository) {
        mImagesRepository = imagesRepository;
    }

    public Single<List<Image>> getImages(String query, int page, int perPage) {
        return mImagesRepository.getImages(query, page, perPage);
    }
}
