package com.byted.camp.todolist.db;

import android.provider.BaseColumns;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量

    private TodoContract() {
    }
    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "tododata";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_FINISH = "finish"; //0 未完成 1 已完成
        public static final String COLUMN_NAME_NOTE = "note";

        //pro
        public static final String COLUMN_NAME_PRIORITY = "priority";//优先级
    }
    //定义建表的SQL代码
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoEntry.TABLE_NAME + "(" +
                    TodoEntry._ID + " INTEGER PRIMARY KEY," +
                    TodoEntry.COLUMN_NAME_TIME + " INTEGER," +
                    TodoEntry.COLUMN_NAME_FINISH + " INTEGER," +
                    TodoEntry.COLUMN_NAME_NOTE + " TEXT" + ")";

    //删表代码
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;
}
