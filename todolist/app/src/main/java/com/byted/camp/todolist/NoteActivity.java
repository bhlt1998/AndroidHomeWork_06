package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;
    SQLiteDatabase db;
    TodoDbHelper todoDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add
        todoDbHelper = new TodoDbHelper(this);
        db = todoDbHelper.getWritableDatabase();

        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);
        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.check(R.id.radioButton4);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }

                //pro
                int priority = 0;
                int radioCheck = radioGroup.getCheckedRadioButtonId();
                switch (radioCheck){
                    case R.id.radioButton4:
                        priority = 0;
                        break;
                    case R.id.radioButton5:
                        priority = 1;
                        break;
                    case R.id.radioButton6:
                        priority = 2;
                        break;
                    default:
                        break;
                }
                boolean succeed = saveNote2Database(content.toString().trim(), priority);
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        todoDbHelper.close();
        db.close();
        super.onDestroy();
    }

    private boolean saveNote2Database(String content, int priority) {
        // TODO 插入一条新数据，返回是否插入成功
        try{
            ContentValues values = new ContentValues();
            values.put(TodoContract.TodoEntry.COLUMN_NAME_TIME, System.currentTimeMillis());
            values.put(TodoContract.TodoEntry.COLUMN_NAME_FINISH, 0);//0为未完成 1为已完成
            values.put(TodoContract.TodoEntry.COLUMN_NAME_NOTE, content);
            //pro
            values.put(TodoContract.TodoEntry.COLUMN_NAME_PRIORITY, priority);
            //insert new row
            long newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
            Log.d("TAG","add new data "+newRowId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
