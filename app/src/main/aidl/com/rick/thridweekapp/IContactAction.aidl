// IContactAction.aidl
package com.rick.thridweekapp;

import com.rick.thridweekapp.bean.ContactBean;

interface IContactAction {

    void sendMessage(in ContactBean contact);

    void callUp(in ContactBean contact);

}