package com.example.cse110lab5;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ListItemDao_Impl implements ListItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ListItem> __insertionAdapterOfListItem;

  private final EntityDeletionOrUpdateAdapter<ListItem> __deletionAdapterOfListItem;

  private final EntityDeletionOrUpdateAdapter<ListItem> __updateAdapterOfListItem;

  public ListItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfListItem = new EntityInsertionAdapter<ListItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `list_items` (`id`,`text`,`order`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListItem value) {
        stmt.bindLong(1, value.id);
        if (value.text == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.text);
        }
        stmt.bindLong(3, value.order);
      }
    };
    this.__deletionAdapterOfListItem = new EntityDeletionOrUpdateAdapter<ListItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `list_items` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListItem value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfListItem = new EntityDeletionOrUpdateAdapter<ListItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `list_items` SET `id` = ?,`text` = ?,`order` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListItem value) {
        stmt.bindLong(1, value.id);
        if (value.text == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.text);
        }
        stmt.bindLong(3, value.order);
        stmt.bindLong(4, value.id);
      }
    };
  }

  @Override
  public long insert(final ListItem listItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfListItem.insertAndReturnId(listItem);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final List<ListItem> listItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfListItem.insertAndReturnIdsList(listItem);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final ListItem listItem) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfListItem.handle(listItem);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final ListItem listItem) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfListItem.handle(listItem);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public ListItem get(final long id) {
    final String _sql = "SELECT * FROM `list_items` WHERE `id`=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
      final ListItem _result;
      if(_cursor.moveToFirst()) {
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final int _tmpOrder;
        _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
        _result = new ListItem(_tmpText,_tmpOrder);
        _result.id = _cursor.getLong(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ListItem> getAll() {
    final String _sql = "SELECT * FROM `list_items` ORDER BY `order`";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
      final List<ListItem> _result = new ArrayList<ListItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ListItem _item;
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final int _tmpOrder;
        _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
        _item = new ListItem(_tmpText,_tmpOrder);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ListItem>> getAllLive() {
    final String _sql = "SELECT * FROM `list_items` ORDER BY `order`";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"list_items"}, false, new Callable<List<ListItem>>() {
      @Override
      public List<ListItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final List<ListItem> _result = new ArrayList<ListItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ListItem _item;
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            _item = new ListItem(_tmpText,_tmpOrder);
            _item.id = _cursor.getLong(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getOrderForAppend() {
    final String _sql = "SELECT `order` + 1 FROM `list_items` ORDER BY `order` DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
