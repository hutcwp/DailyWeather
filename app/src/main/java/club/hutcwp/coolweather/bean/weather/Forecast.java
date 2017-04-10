/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.bean.weather;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class Forecast {


    /**
     * cond : {"txt_d":"小雨","txt_n":"小雨"}
     * date : 2017-04-09
     * tmp : {"max":"19","min":"11"}
     */

    private CondBean cond;
    private String date;
    private TmpBean tmp;

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TmpBean getTmp() {
        return tmp;
    }

    public void setTmp(TmpBean tmp) {
        this.tmp = tmp;
    }

    public static class CondBean {
        /**
         * txt_d : 小雨
         * txt_n : 小雨
         */

        private String txt_d;
        private String txt_n;

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }
    }

    public static class TmpBean {
        /**
         * max : 19
         * min : 11
         */

        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }
}
