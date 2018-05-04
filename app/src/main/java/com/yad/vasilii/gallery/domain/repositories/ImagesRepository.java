package com.yad.vasilii.gallery.domain.repositories;

import com.yad.vasilii.gallery.domain.models.*;

import java.util.*;

import io.reactivex.*;

public interface ImagesRepository {

    Single<List<Image>> getImages(String query, int page, int perPage);

}
