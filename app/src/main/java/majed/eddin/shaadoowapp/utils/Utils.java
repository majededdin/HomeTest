package majed.eddin.shaadoowapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.util.Map;

import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.consts.AppConst;

import static majed.eddin.shaadoowapp.data.consts.Params.suffixes;

public class Utils {

    public static String getImagePath(String lastPath) {
        return AppConst.getInstance().getMediaRootUrl().concat(lastPath);
    }

    public static int getAttrColor(Context context, int colorID) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        switch (colorID) {
            case 1: // colorID = 1
                theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
                break;

            case 2: // colorID = 2
                theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                break;

            case 3: // colorID = 3
                theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
                break;
        }
        return typedValue.resourceId;
    }


    public static String formatNumber(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumber(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumber(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

}