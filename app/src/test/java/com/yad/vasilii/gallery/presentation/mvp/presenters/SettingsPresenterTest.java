package com.yad.vasilii.gallery.presentation.mvp.presenters;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.presentation.mvp.settings.*;
import com.yad.vasilii.gallery.test.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import java.util.*;

import io.reactivex.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SettingsPresenterTest {

    @Mock
    private SettingsInteractor mInteractor;

    @Mock
    private SettingsView mView;

    private SettingsPresenter mPresenter;

    private List<Category> mCategories = CategoryUtils.categories();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SettingsPresenter(new TestScheduleProviders(), mInteractor);
        mockInteractorAndAttachView(mCategories);
    }

    @Test
    public void onFirstAttach_shouldShowCategoriesOnFirstAttach() {
        verify(mInteractor, times(1)).getCategories();
        verify(mView, times(1)).showCategories(mCategories);
    }

    @Test
    public void onClickAddButton_shouldShowInputDialogWhenClickOnAddButton() {
        mPresenter.onClickAddButton();
        verify(mView, times(1)).showAddDialog();
    }

    @Test
    public void onAddCategory_shouldShowCategoriesWhenAddCategory() {
        String categoryTitle = "android";
        List<Category> savedCategories = new ArrayList<>(mCategories);
        savedCategories.add(CategoryUtils.createCategory(4, categoryTitle));

        when(mInteractor.addCategory(categoryTitle)).thenReturn(Single.just(savedCategories));

        mPresenter.onAddCategory(categoryTitle);

        verify(mInteractor, times(1)).addCategory(categoryTitle);
        verify(mInteractor, times(1)).getCategories();
        verify(mView, times(1)).showCategories(savedCategories);
    }

    @Test
    public void deleteCategory_shouldShowCategoriesWhenDeleteCategory() {
        List<Category> savedCategories = new ArrayList<>(mCategories);
        Category deletedCategory = savedCategories.remove(1);

        when(mInteractor.deleteCategory(deletedCategory)).thenReturn(Single.just(savedCategories));

        mPresenter.onDeleteCategory(deletedCategory);

        verify(mInteractor, times(1)).deleteCategory(deletedCategory);
        verify(mInteractor, times(1)).getCategories();
        verify(mView, times(1)).showCategories(savedCategories);
    }

    private void mockInteractorAndAttachView() {
        mockInteractorAndAttachView(CategoryUtils.categories());
    }

    private void mockInteractorAndAttachView(List<Category> categories) {
        Single<List<Category>> categorySingle = Single.just(categories);

        when(mInteractor.getCategories()).thenReturn(categorySingle);

        mPresenter.attachView(mView);
    }

}