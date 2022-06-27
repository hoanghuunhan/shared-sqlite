package huunhan.hn.com.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Database databaseAcc;
    TextView tvNewAccount;
    TextInputEditText edtPasswork, edtEmail;
    Button btLogin;
    CheckBox cbRemember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        sharedPreferences = getSharedPreferences("email", MODE_PRIVATE);

        edtEmail.setText(sharedPreferences.getString("account", ""));
        edtPasswork.setText(sharedPreferences.getString("passwork", ""));
        cbRemember.setChecked(sharedPreferences.getBoolean("remember", false));

        databaseAcc = new Database(this, "Account.sqlite", null, 1);
        databaseAcc.queryData("create table if not exists Account(id integer PRIMARY KEY autoincrement, Email text, Passwork text)");

//su kien tao email moi
        tvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRegister();
            }
        });
        edtPasswork.setTransformationMethod(new PasswordTransformationMethod());
//su kien dang nhap
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkRemember(cbRemember);

                SharedPreferences preferences = getSharedPreferences("getEmail", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putString("email", edtEmail.getText().toString());
                editor1.commit();

                int numcheck = 0;

                Cursor dataAccount = databaseAcc.getData("select * from Account");
                while (dataAccount.moveToNext()) {
                    if (edtEmail.getText().toString().equals(dataAccount.getString(1))
                     && edtPasswork.getText().toString().equals(dataAccount.getString(2))) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        numcheck += 1;
                        startActivity(intent);
                    }
                }
                if (numcheck < 1) {
                    Toast.makeText(LoginActivity.this, "Sai Email hoac mat khau", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void checkRemember(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("account", edtEmail.getText().toString());
            editor.putString("passwork", edtPasswork.getText().toString());
            editor.putBoolean("remember", cbRemember.isChecked());
            editor.commit();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("account");
            editor.remove("passwork");
            editor.remove("remember");
            editor.commit();
        }


    }

    //dialog tao moi email
    public void dialogRegister() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_register_account);

        EditText edtEmailreg = dialog.findViewById(R.id.edtEmailreg);
        EditText edtPassworkreg = dialog.findViewById(R.id.edtPassworkreg);
        Button btConfirm = dialog.findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                Cursor dataAccount = databaseAcc.getData("select * from Account");
                while (dataAccount.moveToNext()) {
                        if (edtEmailreg.getText().toString().equals(dataAccount.getString(1))) {
                            Toast.makeText(LoginActivity.this, "Email dang ky da co nguoi su dung", Toast.LENGTH_SHORT).show();
                            check = false;
                        }
                }

                if (check == true) {
                    if (edtEmailreg.getText().toString().equals("") || edtPassworkreg.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "Vui long nhap day du Email va Passwork", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseAcc.queryData("insert into Account values(null, '"+edtEmailreg.getText().toString()+"'," +
                                " '"+edtPassworkreg.getText().toString()+"' )");
                        Toast.makeText(LoginActivity.this, "Tao Email thanh cong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void anhXa() {
        tvNewAccount = findViewById(R.id.tvNewAccount);
        edtEmail = findViewById(R.id.edtEmail);
        edtPasswork = findViewById(R.id.edtPasswork);
        btLogin = findViewById(R.id.btLogin);
        cbRemember = findViewById(R.id.cbRemember);
    }

}