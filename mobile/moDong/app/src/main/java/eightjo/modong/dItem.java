package eightjo.modong;

/**
 * Created by apple on 16. 5. 21..
 */
public class dItem {

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDname() {
        return dname;
    }

    private String dname;

    public dItem(String dname)
    {
        this.setDname(dname);
    }

}
