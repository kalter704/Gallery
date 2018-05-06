package com.yad.vasilii.gallery.data.repositories.mappers;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.models.*;

import java.util.*;

public class ImageMapper {

    public static List<Image> mapCachedImagesToImages(List<CachedImage> cachedImages) {
        List<Image> images = new ArrayList<>();
        for (CachedImage cachedImage : cachedImages) {
            images.add(new Image.Builder()
                    .withPreviewImageUrl(cachedImage.getPreviewImageUrl())
                    .withLargeImageUrl(cachedImage.getLargeImageUrl())
                    .build());
        }
        return images;
    }

    public static List<CachedImage> mapImagesToCachedImages(List<Image> images, String query, int page,
            int perPage) {
        List<CachedImage> cachedImages = new ArrayList<>();
        for (Image image : images) {
            CachedImage ci = new CachedImage();
            ci.setPreviewImageUrl(image.getPreviewImageUrl());
            ci.setLargeImageUrl(image.getLargeImageUrl());
            ci.setQuery(query);
            ci.setPage(page);
            ci.setPerPage(perPage);
            cachedImages.add(ci);
        }
        return cachedImages;
    }

}
