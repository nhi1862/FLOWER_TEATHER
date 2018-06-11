package com.java.mju_come;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class NonMemberRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = NonMemberRegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputEditText textInputEditTextName;

    private AppCompatButton appCompatButtonRegister;

    private InputValidation inputValidation;
    private NonMemberDBHelper nonMemberDBHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        nonMemberDBHelper = new NonMemberDBHelper(activity);
        user = new User();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                Intent intent = new Intent(NonMemberRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }

        user.setName(textInputEditTextName.getText().toString().trim());
        nonMemberDBHelper.addUser(user);

        emptyInputEditText();

    }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
    }


}

