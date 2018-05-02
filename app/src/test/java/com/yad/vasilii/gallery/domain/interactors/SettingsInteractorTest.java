package com.yad.vasilii.gallery.domain.interactors;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.domain.repositories.*;
import com.yad.vasilii.gallery.test.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import java.util.*;

import io.reactivex.*;
import io.reactivex.observers.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SettingsInteractorTest {

    @Mock
    private CategoriesRepository mRepository;

    private SettingsInteractor mInteractor;

    private List<Category> mCategories = CategoryUtils.categories();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mInteractor = new SettingsInteractor(mRepository);
    }

    @Test
    public void getCategories_shouldReturnListCategoriesWhenGetCategories() {
        TestObserver<List<Category>> testObserver = TestObserver.create();

        when(mRepository.getCategories()).thenReturn(Single.just(mCategories));

        mInteractor.getCategories().subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mRepository, times(1)).getCategories();
        testObserver.assertValue(mCategories);
    }

    @Test
    public void addCategories_shouldReturnListCategoriesWithNewCategoryWhenAddCategory() {
        String categoryName = "JUnitTest;)";
        List<Category> newCategories = new ArrayList<>(mCategories);
        newCategories.add(CategoryUtils.createCategory(4, categoryName));
        Single<List<Category>> singleNewCategories = Single.just(newCategories);

        TestObserver<List<Category>> testObserver = TestObserver.create();

        when(mRepository.addCategory(categoryName)).thenReturn(Completable.complete());
        when(mRepository.getCategories()).thenReturn(singleNewCategories);

        mInteractor.addCategory(categoryName).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mRepository, times(1)).addCategory(categoryName);
        verify(mRepository, times(1)).getCategories();
        testObserver.assertValue(newCategories);
    }

    @Test
    public void addCategories_shouldReturnListCategoriesWithOldCategoriesWhenAddNullCategoryTitle() {
        String categoryName = null;

        TestObserver<List<Category>> testObserver = TestObserver.create();

        when(mRepository.getCategories()).thenReturn(Single.just(mCategories));

        mInteractor.addCategory(categoryName).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mRepository, never()).addCategory(categoryName);
        verify(mRepository, times(1)).getCategories();
        testObserver.assertValue(mCategories);
    }

    @Test
    public void addCategories_shouldReturnListCategoriesWithOldCategoriesWhenAddEmptyCategoryTitle() {
        String categoryName = "";

        TestObserver<List<Category>> testObserver = TestObserver.create();

        when(mRepository.getCategories()).thenReturn(Single.just(mCategories));

        mInteractor.addCategory(categoryName).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mRepository, never()).addCategory(categoryName);
        verify(mRepository, times(1)).getCategories();
        testObserver.assertValue(mCategories);
    }

    @Test
    public void deleteCategory_shouldReturnListCategoriesWithoutDeletedCategory() {
        List<Category> categories = new ArrayList<>(mCategories);
        Category deletedCategory = categories.remove(1);

        TestObserver<List<Category>> testObserver = TestObserver.create();

        when(mRepository.deleteCategory(deletedCategory)).thenReturn(Completable.complete());
        when(mRepository.getCategories()).thenReturn(Single.just(categories));

        mInteractor.deleteCategory(deletedCategory).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        verify(mRepository, times(1)).deleteCategory(deletedCategory);
        verify(mRepository, times(1)).getCategories();
        testObserver.assertValue(categories);
    }

}