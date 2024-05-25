package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;

public class FragmentUser extends Fragment {

    private LinearLayout linearLayout_QR, linearLayout_ThongTinVeChungToi, linearLayout_DieuKhoanSuDung, linearLayout_ChinhSachBaoMat, linearLayout_LogOut, linearLayout_Delete_Account;
    private Animation scaleDown, scaleUp;
    private TextView tvThongTinCaNhan;


    public FragmentUser() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentUser newInstance(String param1, String param2) {
        FragmentUser fragment = new FragmentUser();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);



        addControls(view);

        addEvents();

        return view;
    }

    private void addControls(View view){
        tvThongTinCaNhan = view.findViewById(R.id.tvThongTinCaNhan);
        linearLayout_QR = view.findViewById(R.id.linearLayout_QR);
        linearLayout_ThongTinVeChungToi = view.findViewById(R.id.linearLayout_ThongTinVeChungToi);
        linearLayout_DieuKhoanSuDung = view.findViewById(R.id.linearLayout_DieuKhoanSuDung);
        linearLayout_ChinhSachBaoMat = view.findViewById(R.id.linearLayout_ChinhSachBaoMat);
        linearLayout_LogOut = view.findViewById(R.id.linearLayout_LogOut);
        linearLayout_Delete_Account = view.findViewById(R.id.linearLayout_Delete_Account);
        scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);



    }
    private void addEvents(){
        tvThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), tvThongTinCaNhan);
            }
        });
        linearLayout_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_QR);
            }
        });
        linearLayout_ThongTinVeChungToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_ThongTinVeChungToi);
            }
        });
        linearLayout_DieuKhoanSuDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_DieuKhoanSuDung);
            }
        });
        linearLayout_ChinhSachBaoMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_ChinhSachBaoMat);
            }
        });
        linearLayout_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_LogOut);
            }
        });
        linearLayout_Delete_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_Delete_Account);
            }
        });
    }
}