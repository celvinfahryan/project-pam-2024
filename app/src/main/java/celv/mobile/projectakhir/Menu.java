package celv.mobile.projectakhir;

public class Menu {
    public String pic;
    public String time;
    public String menu;
    public int price;

    public Menu(){}

    public int getPrice(){
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String subtitle) {
        this.menu = subtitle;
    }
}