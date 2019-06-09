package club.hutcwp.coolweather.bean;


import org.litepal.crud.DataSupport;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class Province extends DataSupport{


    /**
     * id : 省id
     * name : 省名称
     */

    private int no;
    private String name;

    public int getNo() {
        return no;
    }

    public void setNo(int id) {
        this.no = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
