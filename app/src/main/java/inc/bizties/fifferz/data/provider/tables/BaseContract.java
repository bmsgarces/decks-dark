package inc.bizties.fifferz.data.provider.tables;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseContract implements BaseColumns {

    public static final String TAG = BaseContract.class.getSimpleName();

    private final Map<String, Integer> cursorIndexes;

    public BaseContract() {
        this.cursorIndexes = new HashMap<>();
    }

    public int getIndex(Cursor cursor, String columnName) {
        if (cursorIndexes.containsKey(columnName)) {
            return cursorIndexes.get(columnName);
        }

        final int index = cursor.getColumnIndexOrThrow(columnName);
        cursorIndexes.put(columnName, index);

        return index;
    }

}
