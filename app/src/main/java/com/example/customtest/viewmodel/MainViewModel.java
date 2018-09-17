package com.example.customtest.viewmodel;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import com.example.customtest.vo.ActivityItem;
import com.hivescm.commonbusiness.viewmodel.BaseViewModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZQiong on 2018/9/1.
 */
public class MainViewModel extends BaseViewModel {
    private MutableLiveData<ActivityItem> activityItems;

    private ArrayList<String> activityOther = new ArrayList<>();

    public MutableLiveData getUsers(Context context) {
        if (activityItems == null) {
            activityItems = new MutableLiveData<>();
            loadUsers(context);
        }

        activityOther.add("com.hivescm.commonbusiness.ui.SimpleLoginActivity");
        activityOther.add("com.hivescm.commonbusiness.ui.dict.StreetDictActivity");
        activityOther.add("com.hivescm.commonbusiness.ui.dict.CityDictActivity");
        activityOther.add("com.example.customtest.MainActivity");
        return activityItems;
    }


    private void loadUsers(Context context) {
        Observable.create((ObservableOnSubscribe<ActivityItem>) e -> {
            PackageManager packageManager = context.getPackageManager();
            try {
                ActivityInfo[] activities = packageManager.getPackageInfo(
                        context.getPackageName(),
                        PackageManager.GET_ACTIVITIES).activities;
                for (ActivityInfo activityInfo : activities) {
                    if (!activityOther.contains(activityInfo.name)) {
                        ActivityItem activityItem = new ActivityItem();
                        activityItem.name = activityInfo.name;
                        e.onNext(activityItem);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> activityItems.setValue(s));
    }
}
