package com.example.eatitv2.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatitv2.Callback.IBestDealsCallbackListener;
import com.example.eatitv2.Callback.IPopularCallbackListener;
import com.example.eatitv2.Common.Common;
import com.example.eatitv2.Model.BestDealModel;
import com.example.eatitv2.Model.PopularCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IPopularCallbackListener, IBestDealsCallbackListener {

    private MutableLiveData<List<PopularCategoryModel>> popularList;
    private MutableLiveData<List<BestDealModel>> bestDealList;
    private MutableLiveData<String> messageError;
    private IPopularCallbackListener popularCallbackListener;
    private IBestDealsCallbackListener bestDealsCallbackListener;

    public HomeViewModel() {
        popularCallbackListener= this;
        bestDealsCallbackListener=this;

    }

    public MutableLiveData<List<BestDealModel>> getBestDealList() {
        if(bestDealList==null)
        {
            bestDealList= new MutableLiveData<>();
            messageError= new MutableLiveData<>();
            loadBestDealList();
        }
        return bestDealList;
    }

    private void loadBestDealList() {
        List<BestDealModel>tempList= new ArrayList<>();
        DatabaseReference bestDealRef= FirebaseDatabase.getInstance().getReference(Common.BEST_DEAL_REF);
        bestDealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnaphot:dataSnapshot.getChildren())
                {
                    BestDealModel model= itemSnaphot.getValue(BestDealModel.class);
                    tempList.add(model);
                }
                bestDealsCallbackListener.onBestDealLoadSuccess(tempList);

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                bestDealsCallbackListener.onBestDealLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<List<PopularCategoryModel>> getPopularList() {
        if(popularList==null)
        {
            popularList= new MutableLiveData<>();
            messageError= new MutableLiveData<>();
            loadPopularList();
        }
        return popularList;
    }

    private void loadPopularList() {
        List<PopularCategoryModel>tempList= new ArrayList<>();
        DatabaseReference popularRef= FirebaseDatabase.getInstance().getReference(Common.POPULAR_CATEGORY_REF);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnaphot:dataSnapshot.getChildren())
                {
                    PopularCategoryModel model= itemSnaphot.getValue(PopularCategoryModel.class);
                    tempList.add(model);
                }
                popularCallbackListener.onPopularLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularCallbackListener.onPopularLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels) {
        popularList.setValue(popularCategoryModels);
    }

    @Override
    public void onPopularLoadFailed(String message) {
        messageError.setValue(message);

    }

    @Override
    public void onBestDealLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealList.setValue(bestDealModels);
    }

    @Override
    public void onBestDealLoadFailed(String message) {
        messageError.setValue(message);
    }
}