package com.jay.nixsolutionstest.di;

import com.jay.nixsolutionstest.view.mainscreen.CompletedPurchasesFragment;
import com.jay.nixsolutionstest.view.mainscreen.CurrentPurchasesFragment;
import com.jay.nixsolutionstest.view.newpurchasescreen.NewPurchaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class})
public interface AppComponent {

    void inject(NewPurchaseActivity activity);

    void inject(CompletedPurchasesFragment fragment);

    void inject(CurrentPurchasesFragment fragment);
}
