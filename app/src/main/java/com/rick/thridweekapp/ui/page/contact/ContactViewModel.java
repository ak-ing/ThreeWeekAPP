package com.rick.thridweekapp.ui.page.contact;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rick.thridweekapp.bean.ContactBean;

import java.util.List;

/**
 * Created by Rick on 2022/7/15 16:04.
 * God bless my code!
 */
public class ContactViewModel extends ViewModel {

    /**
     * 联系人
     */
    MutableLiveData<List<ContactBean>> contactsLd = new MutableLiveData<>();

}
