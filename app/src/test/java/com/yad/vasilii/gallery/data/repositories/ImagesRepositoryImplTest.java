package com.yad.vasilii.gallery.data.repositories;

import com.yad.vasilii.gallery.data.network.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.test.*;

import org.junit.*;
import org.mockito.*;

import java.util.*;

import io.reactivex.*;
import io.reactivex.observers.*;

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
        List<Image> fakeImages = ImageUtils.getFakeImages(numberOfImages);

        TestObserver<List<Image>> testObserver = TestObserver.create();

        when(mPixabayApiService.getImages("red", 1, numberOfImages))
                .thenReturn(Single.just(ImageUtils.getFakeResponse(fakeImages)));

        mRepository.getImages("red", 1, numberOfImages).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mPixabayApiService, times(1)).getImages("red", 1, numberOfImages);
        testObserver.assertValue(images -> ImageUtils.equalListImage(images, fakeImages));
    }
}