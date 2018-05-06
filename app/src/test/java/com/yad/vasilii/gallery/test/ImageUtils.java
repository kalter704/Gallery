package com.yad.vasilii.gallery.test;

import com.yad.vasilii.gallery.data.network.models.*;
import com.yad.vasilii.gallery.domain.models.*;

import java.util.*;

import static org.junit.Assert.*;

public class ImageUtils {

    public static List<Image> getFakeImages(int number) {
        List<Image> images = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            images.add(new Image.Builder().withPreviewImageUrl(StringUtils.getRandomString())
                    .withLargeImageUrl(StringUtils.getRandomString()).build());
        }
        return images;
    }

    public static PixabayApiResponse getFakeResponse(List<Image> images) {
        List<Hit> hits = new ArrayList<>();
        for (Image image : images) {
            Hit hit = new Hit();
            hit.setWebformatURL(image.getPreviewImageUrl());
            hit.setLargeImageURL(image.getLargeImageUrl());
            hits.add(hit);
        }
        PixabayApiResponse response = new PixabayApiResponse();
        response.setHits(hits);
        return response;
    }

    public static boolean equalListImage(List<Image> first, List<Image> second) {
        assertEquals(first.size(), second.size());
        for (int i = 0; i < first.size(); i++) {
            assertEquals(first.get(i).getPreviewImageUrl(), second.get(i).getPreviewImageUrl());
            assertEquals(first.get(i).getLargeImageUrl(), second.get(i).getLargeImageUrl());
        }
        return true;
    }

}
