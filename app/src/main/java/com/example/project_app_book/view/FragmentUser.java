package com.example.project_app_book.view;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.User;

public class FragmentUser extends Fragment {
    private User user;
    private LinearLayout linearLayoutCircleIn, linearLayoutCircleOut, linearLayout_QR, linearLayout_ThongTinVeChungToi, linearLayout_DieuKhoanSuDung, linearLayout_ChinhSachBaoMat, linearLayout_LogOut, linearLayout_Delete_Account;
    private TextView tvThongTinCaNhan, tvNameUser;


    public FragmentUser() {
    }
    public FragmentUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("loggedInUser");
        }
        addControls(view);

        addEvents();

        return view;
    }

    private void addControls(View view){
        tvNameUser = view.findViewById(R.id.tvNameUser);
        tvThongTinCaNhan = view.findViewById(R.id.tvThongTinCaNhan);
        linearLayout_QR = view.findViewById(R.id.linearLayout_QR);
        linearLayoutCircleIn = view.findViewById(R.id.linearLayout_circle_in);
        linearLayoutCircleOut = view.findViewById(R.id.linearLayout_circle_out);

        linearLayout_ThongTinVeChungToi = view.findViewById(R.id.linearLayout_ThongTinVeChungToi);
        linearLayout_DieuKhoanSuDung = view.findViewById(R.id.linearLayout_DieuKhoanSuDung);
        linearLayout_ChinhSachBaoMat = view.findViewById(R.id.linearLayout_ChinhSachBaoMat);
        linearLayout_LogOut = view.findViewById(R.id.linearLayout_LogOut);
        linearLayout_Delete_Account = view.findViewById(R.id.linearLayout_Delete_Account);
        if (user != null) {
            tvNameUser.setText(user.getName());
        }


    }
    private void addEvents(){

        AnimationUtil.applyScaleAnimation_fade(getContext(), linearLayoutCircleIn);
        AnimationUtil.applyScaleAnimation_fade(getContext(), linearLayoutCircleOut);

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
                FragmentDanhSachYeuThich frgYeuThich = new FragmentDanhSachYeuThich();
                Intent intent = getActivity().getIntent(); // Get intent from hosting activity
                user = (User) intent.getSerializableExtra("loggedInUser");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("loggedInUser", user);
                frgYeuThich.setArguments(bundle);
                transaction.replace(R.id.fragLayoutLoad, frgYeuThich);
                transaction.addToBackStack(null);
                transaction.commit();
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