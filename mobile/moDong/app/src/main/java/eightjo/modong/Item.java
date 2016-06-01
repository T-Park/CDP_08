package eightjo.modong;

/**
 * Created by apple on 16. 5. 16..
 */
public class Item {
    private String where;
    private String when;
    private String point;

    public Item(String when, String where, String point)
    {
        this.setWhere(where);
        this.setWhen(when);
        this.setPoint(point);
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
