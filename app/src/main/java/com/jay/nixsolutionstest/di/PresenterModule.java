package com.jay.nixsolutionstest.di;

import com.jay.nixsolutionstest.contract.CompletedPurchasesContract;
import com.jay.nixsolutionstest.contract.CurrentPurchasesContract;
import com.jay.nixsolutionstest.contract.NewPurchaseContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    private NewPurchaseContract.View newPurchaseView;

    private CompletedPurchasesContract.View completedPurchaseView;

    private CurrentPurchasesContract.View currentPurchaseView;


    public PresenterModule(NewPurchaseContract.View newPurchaseView) {
        this.newPurchaseView = newPurchaseView;
    }


    public PresenterModule(CompletedPurchasesContract.View completedPurchaseView) {
        this.completedPurchaseView = completedPurchaseView;
    }


    public PresenterModule(CurrentPurchasesContract.View currentPurchaseView) {
        this.currentPurchaseView = currentPurchaseView;
    }


    @Provides
    NewPurchaseContract.View provideNewPurchaseView(){
        return newPurchaseView;
    }


    @Provides
    CompletedPurchasesContract.View provideCompletedPurchaseView(){
        return completedPurchaseView;
    }


    @Provides
    CurrentPurchasesContract.View provideCurrentPurchaseView(){
        return currentPurchaseView;
    }
}
