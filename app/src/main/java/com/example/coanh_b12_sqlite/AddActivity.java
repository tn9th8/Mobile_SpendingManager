package com.example.coanh_b12_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coanh_b12_sqlite.dal.SQLiteHelper;
import com.example.coanh_b12_sqlite.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    public Spinner spCategory;
    public EditText etTitle, etPrice, etDate;
    public Button btSaveAdd, btCancel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btSaveAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        etDate.setOnClickListener(this);
    }

    private void initView() {
        // anh xa
        spCategory = findViewById(R.id.spCategory);
        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etDate = findViewById(R.id.etDate);
        btSaveAdd = findViewById(R.id.btSaveAdd);
        btCancel = findViewById(R.id.btCancel);
        spCategory.setAdapter(new ArrayAdapter<String>(
                this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)
        ));
    }

    @Override
    public void onClick(View view) {
        if (view == etDate) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(
                    AddActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String date = "";
                            if (month > 9-1) {
                                date = day + "/" + (month+1) + "/" + year;
                            } else {
                                date = day + "/0" + (month+1) + "/" + year;
                            }
                            etDate.setText(date);
                        }
                    },
                    year, month, day
            );
            dialog.show();
        }

        if (view == btCancel) {
            finish();
        }

        if (view == btSaveAdd) {
            String title = etTitle.getText().toString();
            String price = etPrice.getText().toString();
            String date = etDate.getText().toString();
            String category = spCategory.getSelectedItem().toString();

            if (!title.isEmpty() && price.matches("\\d+")) { // validate: price là số
                Item item = new Item(title, category, price, date);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(item);
                finish();
            } else {
                Toast.makeText(AddActivity.this, "Thông tin nhập không phù hợp", Toast.LENGTH_SHORT).show();
            }
        }
    }
}