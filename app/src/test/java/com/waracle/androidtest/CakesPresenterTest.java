
package com.waracle.androidtest;


import com.waracle.androidtest.cakes.CakesContract;
import com.waracle.androidtest.cakes.CakesPresenter;
import com.waracle.androidtest.cakes.DownloadCakes;
import com.waracle.androidtest.cakes.DownloadCakesInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CakesPresenterTest {

    private static List<Cake> cakeList = new ArrayList<>();
    private DownloadCakes downloadCakes;

    @Mock
    private CakesContract.View mCakesView;

    private CakesPresenter cakesPresenter;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);

        downloadCakes = new DownloadCakes();
        cakesPresenter = new CakesPresenter(mCakesView);

        cakeList.add(new Cake("Title1", "Description1", "ImageURL1"));
        cakeList.add(new Cake("Title2", "Description2",  "ImageURL2"));
    }

    @Test
    public void loadCakesIntoView() {
        mCakesView.setProgressIndicator(false);
        mCakesView.showCakes(cakeList);
        InOrder inOrder = Mockito.inOrder(mCakesView);
        inOrder.verify(mCakesView).setProgressIndicator(true);
        inOrder.verify(mCakesView).setProgressIndicator(false);
        verify(mCakesView).showCakes(cakeList);
    }

    @Test
    public void clickOnCake_ShowsDetailUi() {
        Cake requestedCake = new Cake("Details Requested", "For this note", "Image URL");
        cakesPresenter.showCakeDetails(requestedCake);
        verify(mCakesView).showCakeDetailUi(any(Cake.class));
    }
}
