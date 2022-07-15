package com.rick.thridweekapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by Rick on 2022/7/15 16:28.
 * God bless my code!
 */
public class ContactBean implements Parcelable {
    private String name;
    private String phone;

    public ContactBean(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    protected ContactBean(Parcel in) {
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<ContactBean> CREATOR = new Creator<ContactBean>() {
        @Override
        public ContactBean createFromParcel(Parcel in) {
            return new ContactBean(in);
        }

        @Override
        public ContactBean[] newArray(int size) {
            return new ContactBean[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactBean that = (ContactBean) o;
        return Objects.equals(name, that.name) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
    }
}
