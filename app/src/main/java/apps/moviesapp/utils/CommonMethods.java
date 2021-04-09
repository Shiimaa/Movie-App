package apps.moviesapp.utils;

import android.util.TypedValue;

public class CommonMethods {
    public static int dp2pixels(int dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                App.context.getResources().getDisplayMetrics()
        );
        return (int) px;
    }

}
