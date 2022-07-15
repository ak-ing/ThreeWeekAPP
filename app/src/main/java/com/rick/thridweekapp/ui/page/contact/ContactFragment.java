package com.rick.thridweekapp.ui.page.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.rick.thridweekapp.bean.ContactBean;
import com.rick.thridweekapp.databinding.FragmentContactBinding;
import com.rick.thridweekapp.ui.adapter.ContactAdapter;

import java.util.ArrayList;

/**
 * Created by Rick on 2022/7/15 16:00.
 * God bless my code!
 */
public class ContactFragment extends Fragment {
    private FragmentContactBinding binding;
    private ContactViewModel mState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState = new ViewModelProvider(this).get(ContactViewModel.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                    if (result) {
                        initData();
                    }
                }).launch(Manifest.permission.READ_CONTACTS);
            } else {
                initData();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        ContactAdapter adapter = new ContactAdapter();
        binding.rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvContact.setAdapter(adapter);
        mState.contactsLd.observe(getViewLifecycleOwner(), adapter::submitList);
        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setPositiveButton("发短信", (dialog1, which) -> {

                }).setNegativeButton("打电话", (dialog12, which) -> {

                })
                .create();
        adapter.setOnItemClickListener((contact -> {
            dialog.setTitle(contact.getName());
            dialog.setMessage("号码：" + contact.getPhone());
            dialog.show();
        }));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ArrayList<ContactBean> contacts = new ArrayList<>();
        ContentResolver resolver = requireContext().getContentResolver();
        Cursor query = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = query.getString(query.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new ContactBean(name, phone));
        }
        mState.contactsLd.setValue(contacts);
        query.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
