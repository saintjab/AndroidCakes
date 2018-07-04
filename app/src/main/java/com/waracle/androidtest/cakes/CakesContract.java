
package com.waracle.androidtest.cakes;

import android.support.annotation.NonNull;

import com.waracle.androidtest.Cake;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CakesContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showMessage(String message);

        void showCakes(List<Cake> cakes);

        void showCakeDetailUi(Cake cake);
    }

    interface UserActionsListener {

        void reloadCakes(boolean forceUpdate);

        void showCakeDetails(@NonNull Cake requestedCake);
    }
}
