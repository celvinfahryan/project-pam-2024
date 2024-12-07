package celv.mobile.projectakhir;

import java.util.Map;

public class Shop {
    public String id;
    public String pic;
    private String time;
    private String shopName;
    private String description;
    private Map<String, Menu> Menu;

    public Shop() {}

    public Shop(String time, String shopName, String description, Map<String, Menu> menu) {
        this.time = time;
        this.shopName = shopName;
        this.description = description;
        this.Menu = menu;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Menu> getMenu() {
        return Menu;
    }

    public void setMenu(Map<String, Menu> menu) {
        Menu = menu;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}