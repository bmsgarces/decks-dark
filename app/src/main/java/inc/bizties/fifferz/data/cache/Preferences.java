package inc.bizties.fifferz.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Preferences {

    public static final String DB_KEY = "dbkey";

    private static final String NAME = "prefs";

    public static void clear(Context context) {
        final SharedPreferences prefs = getSharedPreferences(context);
        prefs.edit().clear().apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static boolean hasDBKeySaved(Context context) {
        ensureHawkIsBuilt(context);
        return Hawk.get(DB_KEY, null) != null;
    }

    public static String getDBKeyAndCreateIfMissing(Context context) {
        ensureHawkIsBuilt(context);

        String dbKey = Hawk.get(DB_KEY, null);
        if (dbKey == null) {
            SecureRandom random = new SecureRandom();
            dbKey = new BigInteger(130, random).toString();
            Hawk.put(DB_KEY, dbKey);
        }
        return dbKey;
    }

    private static void ensureHawkIsBuilt(Context context) {
        if (!Hawk.isBuilt()) {
            Hawk.init(context)
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                    .setStorage(HawkBuilder.newSharedPrefStorage(context))
                    .build();
        }
    }
}
