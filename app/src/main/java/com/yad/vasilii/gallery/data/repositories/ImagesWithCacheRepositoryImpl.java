package com.yad.vasilii.gallery.data.repositories;

import com.yad.vasilii.gallery.data.*;
import com.yad.vasilii.gallery.data.repositories.mappers.*;
import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

public class ImagesWithCacheRepositoryImpl implements ImagesRepository {

    private ImagesRepository mImagesRepository;

    private final GalleryDatabase mDB;

    private final CalendarManager mCalendarManager;

    @Inject
    public ImagesWithCacheRepositoryImpl(ImagesRepository imagesRepository, GalleryDatabase db,
            CalendarManager cm) {
        mImagesRepository = imagesRepository;
        mDB = db;
        mCalendarManager = cm;
    }

    @Override
    public Single<List<Image>> getImages(String query, int page, int perPage) {
        return Single.fromCallable(this::checkCache).flatMap(isUseCache -> {
            if (isUseCache) {
                return getImagesFromCache(query, page, perPage);
            } else {
                return getImagesFromImagesRepository(query, page, perPage);
            }
        });
    }

    private Single<List<Image>> getImagesFromCache(String query, int page, int perPage) {
        return Single
                .fromCallable(() -> mDB.cachedImageDao().getImagesByQueryAndPageAndPerPage(query, page, perPage))
                .flatMap(cachedImages -> {
                    if (cachedImages.size() == 0) {
                        return getImagesFromImagesRepository(query, page, perPage);
                    } else {
                        return Single.fromCallable(() -> ImageMapper.mapCachedImagesToImages(cachedImages));
                    }
                });
    }

    private Single<List<Image>> getImagesFromImagesRepository(String query, int page, int perPage) {
        return mImagesRepository.getImages(query, page, perPage)
                .doOnSuccess(images -> saveCache(images, query, page, perPage));
    }

    private void saveCache(List<Image> images, String query, int page, int perPage) {
        mDB.cachedImageDao().insertAll(ImageMapper.mapImagesToCachedImages(images, query, page, perPage));
        TimeWhenCached time = new TimeWhenCached();
        time.setTime(mCalendarManager.getCurrentCalender().getTimeInMillis());
        mDB.timeWhenCached().insert(time);
    }

    private boolean checkCache() {
        List<TimeWhenCached> times = mDB.timeWhenCached().getAll();

        if (times.size() == 0) {
            return false;
        }

        Calendar calendar = mCalendarManager.getCurrentCalender();
        Calendar cacheCalendar = mCalendarManager
                .getCalender(times.get(times.size() - 1).getTime());

        if (!(calendar.get(Calendar.DAY_OF_MONTH) == cacheCalendar.get(Calendar.DAY_OF_MONTH)
                && calendar.get(Calendar.MONTH) == cacheCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.YEAR) == cacheCalendar.get(Calendar.YEAR))) {

            mDB.cachedImageDao().deleteAll();

            return false;
        }

        return true;
    }


}
