package com.yad.vasilii.gallery.data.repositories;

import com.yad.vasilii.gallery.data.network.*;
import com.yad.vasilii.gallery.data.network.models.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.test.*;

import org.junit.*;
import org.mockito.*;

import java.util.*;

import io.reactivex.*;
import io.reactivex.observers.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ImagesRepositoryImplTest {

    @Mock
    private PixabayApiService mPixabayApiService;

    private ImagesRepositoryImpl mRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mRepository = new ImagesRepositoryImpl(mPixabayApiService);
    }

    @Test
    public void getImages_shouldReturn10ImagesWhenRequest10Image() {
        int numberOfImages = 10;
        List<Image> fakeImages = getFakeImages(numberOfImages);

        TestObserver<List<Image>> testObserver = TestObserver.create();

        when(mPixabayApiService.getImages("red", 1, numberOfImages))
                .thenReturn(Single.just(getFakeResponse(fakeImages)));

        mRepository.getImages("red", 1, numberOfImages).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mPixabayApiService, times(1)).getImages("red", 1, numberOfImages);
        testObserver.assertValue(images -> equalListImage(images, fakeImages));
    }

    private boolean equalListImage(List<Image> first, List<Image> second) {
        assertEquals(first.size(), second.size());
        for (int i = 0; i < first.size(); i++) {
            assertEquals(first.get(i).getPreviewImageUrl(), second.get(i).getPreviewImageUrl());
            assertEquals(first.get(i).getLargeImageUrl(), second.get(i).getLargeImageUrl());
        }
        return true;
    }

    private List<Image> getFakeImages(int number) {
        List<Image> images = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            images.add(new Image.Builder()
                    .withPreviewImageUrl(StringUtils.getRandomString())
                    .withLargeImageUrl(StringUtils.getRandomString())
                    .build()
            );
        }
        return images;
    }

    private PixabayApiResponse getFakeResponse(List<Image> images) {
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
}