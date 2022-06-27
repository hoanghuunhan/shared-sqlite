package huunhan.hn.com.myaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.onContactListener {

    RecyclerView rvContact;
    ArrayList<Contact> arrayContact;
    ContactAdapter adapter;
    FloatingActionButton fab;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //tao database
        database = new Database(this, "Contact.sqlite", null, 1);
        database.queryData("create table if not exists Contact( id integer PRIMARY KEY autoincrement, Name text, Phone text )");

        rvContact = findViewById(R.id.rvContact);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddContact();
            }
        });

        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this, arrayContact, this);
        rvContact.setAdapter(adapter);
        rvContact.setLayoutManager(new LinearLayoutManager(this));
        rvContact.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        getData();
        checkPermissionContact();
    }
    //Lay du lieu truyen vao Arraylist
    private void getData() {
        Cursor dataContact = database.getData("select * from Contact");
        arrayContact.clear();
        while (dataContact.moveToNext()) {
            arrayContact.add(new Contact(dataContact.getInt(0), dataContact.getString(1), dataContact.getString(2)));
        }
        adapter.notifyDataSetChanged();
    }

//dialog Them Danh ba
    private void dialogAddContact() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_contact);

        EditText edtAdd = dialog.findViewById(R.id.edtName);
        EditText edtPhone = dialog.findViewById(R.id.edtPhone);
        Button btAdd = dialog.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAdd.getText().toString().equals("") || edtPhone.getText().toString().equals("")) {
                    Toast.makeText(ContactActivity.this, "Vui long nhap day du ten va so dien thoai", Toast.LENGTH_SHORT).show();
                } else {
                    database.queryData("insert into Contact values (null, '"+edtAdd.getText().toString()+"', '"+edtPhone.getText().toString()+"')");
                    getData();
                    Toast.makeText(ContactActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        Button btCancel = dialog.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
//dialog Edit Danh Ba
    private void dialogEditContact(String name, String phone, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_contact);

        EditText edtEdit = dialog.findViewById(R.id.edtNameedit);
        edtEdit.setText(name);
        EditText edtPhoneEdit = dialog.findViewById(R.id.edtPhoneedit);
        edtPhoneEdit.setText(phone);

        Button btEdit = dialog.findViewById(R.id.btEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.queryData("update Contact set Name = '"+edtEdit.getText().toString()+"' , Phone = '"+edtPhoneEdit.getText().toString()+"' " +
                        " Where id = '"+id+"' ");
                getData();
                dialog.dismiss();
            }
        });

        Button btCancel = dialog.findViewById(R.id.btCancel2);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onEditListener(Contact contact) {
        dialogEditContact(contact.getName(), contact.getPhone(), contact.getId());
    }

//delete contact
    @Override
    public void onDeleteListener(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ban co chac muon xoa " + contact.getName() + " khoi danh ba!");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.queryData("delete from Contact where id = '"+ contact.getId() +"'");
                getData();
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//call phone
    @Override
    public void onCallListener(Contact contact, View v) {
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.inflate(R.menu.menu_call);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menuCall) {
                    String phoneNumber = contact.getPhone().toString();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+ phoneNumber));
                    startActivity(intent);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    //permission Call Phone
    public void checkPermissionContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2000);
            }
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2000 && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkPermissionContact();
        } else {
            Toast.makeText(this, "Cannot make a call", Toast.LENGTH_SHORT).show();
        }
    }

}