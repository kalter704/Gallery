package com.yad.vasilii.gallery.data.repositories;

import com.yad.vasilii.gallery.data.*;
import com.yad.vasilii.gallery.data.repositories.mappers.*;
import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.data.roomdatabase.dao.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.domain.repositories.*;
import com.yad.vasilii.gallery.test.*;

import org.junit.*;
import org.mockito.*;

import java.util.*;

import io.reactivex.*;
import io.reactivex.observers.*;

import static org.mockito.Mockito.*;

public class ImagesWithCacheRepositoryImplTest {

    @Mock
    private ImagesRepository mImagesRepository;

    @Mock
    private GalleryDatabase mDB;

    @Mock
    private CalendarManager mCalendarManager;

    private ImagesWithCacheRepositoryImpl mImagesWithCacheRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mImagesWithCacheRepository = new ImagesWithCacheRepositoryImpl(mImagesRepository, mDB,
                mCalendarManager);

        CachedImageDao cachedImageDao = mock(CachedImageDao.class);
        TimeWhenCachedDao timeWhenCachedDao = mock(TimeWhenCachedDao.class);
        when(mDB.cachedImageDao()).thenReturn(cachedImageDao);
        when(mDB.timeWhenCached()).thenReturn(timeWhenCachedDao);
    }

    @Test
    public void getImages_shouldReturnImagesFromImageRepositoryWhenCacheEmpty() {
        String query = "test_query";
        int page = 1;
        int perPage = 5;
        List<Image> preparedResult = ImageUtils.getFakeImages(perPage);
        Calendar calendar = Calendar.getInstance();

        when(mDB.cachedImageDao().getImagesByQueryAndPageAndPerPage(query, page, perPage))
                .thenReturn(new ArrayList<>());

        when(mImagesRepository.getImages(query, page, perPage))
                .thenReturn(Single.just(preparedResult));

        when(mCalendarManager.getCurrentCalender()).thenReturn(calendar);

        TestObserver<List<Image>> testObserver = TestObserver.create();

        mImagesWithCacheRepository.getImages(query, page, perPage).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mDB.timeWhenCached()).getAll();
        verify(mImagesRepository).getImages(query, page, perPage);
        verify(mDB.cachedImageDao(), never())
                .getImagesByQueryAndPageAndPerPage(query, page, perPage);

        testObserver.assertValue(images -> ImageUtils.equalListImage(images, preparedResult));
    }

    @Test
    public void getImages_shouldReturnImagesFromImageRepositoryWhenCacheExpired() {
        String query = "test_query";
        int page = 1;
        int perPage = 5;

        List<Image> preparedResult = ImageUtils.getFakeImages(perPage);
        Calendar calendar = Calendar.getInstance();

        int year = 2017;
        int month = 3;
        int day = 7;
        Calendar cacheCalendar = Calendar.getInstance();
        cacheCalendar.set(year, month, day);
        long cacheTimeInMillis = cacheCalendar.getTimeInMillis();

        TimeWhenCached preparedTimeWhenCached = new TimeWhenCached();
        preparedTimeWhenCached.setTime(cacheTimeInMillis);

        when(mDB.timeWhenCached().getAll()).thenReturn(Arrays.asList(preparedTimeWhenCached));

        when(mCalendarManager.getCurrentCalender()).thenReturn(calendar);
        when(mCalendarManager.getCalender(cacheTimeInMillis)).thenReturn(cacheCalendar);

        when(mDB.cachedImageDao().getImagesByQueryAndPageAndPerPage(query, page, perPage))
                .thenReturn(new ArrayList<>());

        when(mImagesRepository.getImages(query, page, perPage))
                .thenReturn(Single.just(preparedResult));

        TestObserver<List<Image>> testObserver = TestObserver.create();

        mImagesWithCacheRepository.getImages(query, page, perPage).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mDB.timeWhenCached()).getAll();
        verify(mImagesRepository).getImages(query, page, perPage);
        verify(mDB.cachedImageDao(), never())
                .getImagesByQueryAndPageAndPerPage(query, page, perPage);
        verify(mCalendarManager, times(2)).getCurrentCalender();
        verify(mCalendarManager).getCalender(cacheTimeInMillis);
        verify(mDB.cachedImageDao()).deleteAll();

        testObserver.assertValue(images -> ImageUtils.equalListImage(images, preparedResult));
    }

    @Test
    public void getImages_shouldReturnImagesFromCachedImages() {
        String query = "test_query";
        int page = 1;
        int perPage = 5;

        List<Image> preparedResult = ImageUtils.getFakeImages(perPage);
        List<CachedImage> preparedCachedImages = ImageMapper
                .mapImagesToCachedImages(preparedResult, query, page, perPage);

        Calendar calendar = Calendar.getInstance();
        long cacheTimeInMillis = calendar.getTimeInMillis();

        TimeWhenCached preparedTimeWhenCached = new TimeWhenCached();
        preparedTimeWhenCached.setTime(cacheTimeInMillis);

        when(mDB.timeWhenCached().getAll()).thenReturn(Arrays.asList(preparedTimeWhenCached));

        when(mCalendarManager.getCurrentCalender()).thenReturn(calendar);
        when(mCalendarManager.getCalender(cacheTimeInMillis)).thenReturn(calendar);

        when(mDB.cachedImageDao().getImagesByQueryAndPageAndPerPage(query, page, perPage))
                .thenReturn(preparedCachedImages);

        when(mImagesRepository.getImages(query, page, perPage))
                .thenReturn(Single.just(preparedResult));

        TestObserver<List<Image>> testObserver = TestObserver.create();

        mImagesWithCacheRepository.getImages(query, page, perPage).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mDB.timeWhenCached()).getAll();
        verify(mImagesRepository, never()).getImages(query, page, perPage);
        verify(mDB.cachedImageDao()).getImagesByQueryAndPageAndPerPage(query, page, perPage);
        verify(mCalendarManager).getCurrentCalender();
        verify(mCalendarManager).getCalender(cacheTimeInMillis);
        verify(mDB.cachedImageDao(), never()).deleteAll();

        testObserver.assertValue(images -> ImageUtils.equalListImage(images, preparedResult));
    }

}