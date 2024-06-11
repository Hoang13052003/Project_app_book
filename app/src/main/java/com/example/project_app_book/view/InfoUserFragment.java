package com.example.project_app_book.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.Book;
import com.example.project_app_book.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoUserFragment extends Fragment {

    private User user;
    private EditText edtInfoEmail, edtInfoName, edtInfoAddress, edtInfoPhone, edtInfoDOB;
    private Button btnUpdate;
    private TextView tvUpdatePass;
    private DatabaseReference userLoggedIn;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("loggedInUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_user, container, false);
        addControls(view);

        if (user != null) {
            userLoggedIn = FirebaseDatabase.getInstance().getReference("user").child(user.getUserID());
            setUser();
        }
        addEvent();
        return view;
    }

    public void addControls(View view) {
        edtInfoAddress = view.findViewById(R.id.edtInfoAddress);
        edtInfoDOB = view.findViewById(R.id.edtInfoDOB);
        edtInfoEmail = view.findViewById(R.id.edtInfoEmail);
        edtInfoName = view.findViewById(R.id.edtInfoName);
        edtInfoPhone = view.findViewById(R.id.edtInfoPhone);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        tvUpdatePass = view.findViewById(R.id.tvUpdatePass);
    }
    public void addEvent(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edtInfoName.getText().toString().trim();
                String newEmail = edtInfoEmail.getText().toString().trim();
                String newDOB = edtInfoDOB.getText().toString().trim();
                String newPhone = edtInfoPhone.getText().toString().trim();
                String newAddress = edtInfoAddress.getText().toString().trim();
                User updatedUser = new User(user.getUserID(), newAddress, user.getCollection(), newDOB, newEmail, newName, user.getPassword(), newPhone);

                userLoggedIn.setValue(updatedUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                setUser();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        tvUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), tvUpdatePass);
                UpdatePassFragment updatePassFragment = new UpdatePassFragment();
                Intent intent = getActivity().getIntent();
                user = (User) intent.getSerializableExtra("loggedInUser");
                Bundle bundle = new Bundle();
                bundle.putSerializable("loggedInUser", user);
                updatePassFragment.setArguments(bundle);
                loadFragment(updatePassFragment);
            }
        });
    }

    public void setUser() {
        userLoggedIn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User updatedUser = snapshot.getValue(User.class);
                if (updatedUser != null) {
                    user = updatedUser;
                    edtInfoName.setText(user.getName());
                    edtInfoEmail.setText(user.getEmail());
                    edtInfoDOB.setText(user.getDateOfBirth());
                    edtInfoPhone.setText(user.getPhone());
                    edtInfoAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragLayoutLoad, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
