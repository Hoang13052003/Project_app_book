package com.example.project_app_book.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentUser extends Fragment {
    private User user;
    private LinearLayout linearLayoutCircleIn, linearLayoutCircleOut, linearLayout_QR, linearLayout_LogOut, linearLayout_Delete_Account;
    private TextView tvThongTinCaNhan, tvNameUser;

    private SharedPreferences sharedPreferences;
    public FragmentUser() {
    }
    public FragmentUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
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
                InfoUserFragment infoUserFragment = new InfoUserFragment();
                Intent intent = getActivity().getIntent();
                user = (User) intent.getSerializableExtra("loggedInUser");
                Bundle bundle = new Bundle();
                bundle.putSerializable("loggedInUser", user);
                infoUserFragment.setArguments(bundle);
                loadFragment(infoUserFragment);
            }
        });
        linearLayout_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_QR);
                FragmentDanhSachYeuThich frgYeuThich = new FragmentDanhSachYeuThich();
                Intent intent = getActivity().getIntent();
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
        linearLayout_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_LogOut);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.remove("userEmail");
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

                Toast.makeText(getActivity(), "Đăng xuất thành công.", Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout_Delete_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), linearLayout_Delete_Account);
                showDeleteAccountConfirmationDialog();
            }
        });
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragLayoutLoad, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void showDeleteAccountConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa tài khoản")
                .setMessage("Bạn có chắc chắn muốn xóa tài khoản của mình không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUserAccount();
                    }
                })
                .setNegativeButton("Hủy", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteUserAccount() {
        if (user == null || user.getUserID() == null) {
            Toast.makeText(getActivity(), "User data is not available. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUserID());
        userRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.remove("userEmail");
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

                Toast.makeText(getActivity(), "Tài khoản đã được xóa thành công.", Toast.LENGTH_SHORT).show();
            } else {
                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                Toast.makeText(getActivity(), "Xóa tài khoản thất bại. Vui lòng thử lại. Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getActivity(), "Xóa tài khoản thất bại. Vui lòng thử lại. Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}