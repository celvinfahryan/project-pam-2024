package celv.mobile.projectakhir;

public class Menu {
    public String pic;
    public String time;
    public String shop;
    public String menu;
    public int price;

    public Menu(String picAddress, String time, String shop, String menu) {
        this.pic = pic;
        this.time = time;
        this.shop = shop;
        this.menu = menu;
    }
    public Menu(String picAddress, String time, String menu, int price) {
        this.pic = pic;
        this.time = time;
        this.menu = menu;
        this.price = price;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setShop(String title) {
        this.shop = title;
    }

    public void setMenu(String subtitle) {
        this.menu = subtitle;
    }

    public String getPic() {
        return pic;
    }

    public String getTime() {
        return time;
    }

    public String getShop() {
        return shop;
    }

    public String getMenu() {
        return menu;
    }
    public int getPrice(){
        return price;
    }
}
