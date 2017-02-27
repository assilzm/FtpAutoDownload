package utils
/**
 * 时间工具类
 */
class TimerUtils {
    private long _start
    private long _end
    private long _cost

    void start() {
        _start = System.currentTimeMillis()
    }

    void end() {
        _end = System.currentTimeMillis()
        _cost = _end - _start
    }

    String cost() {
        end()
        return converLongTimeToStr(_cost)
    }

    public static String converLongTimeToStr(long time) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;

        long hour = (time) / hh;
        long minute = (time - hour * hh) / mi;
        long second = (time - hour * hh - minute * mi) / ss;
        long ms = (time - hour * hh - minute * mi) % ss

        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMs = ms.toString();

        String retString = strMs + "ms"
        if (second > 0) {
            retString = strSecond + "s " + retString
            if (minute > 0) {
                retString = strMinute + "m " + retString
                if (hour > 0)
                    retString = strHour + "h " + retString
            }
        }
        return retString
    }
}
