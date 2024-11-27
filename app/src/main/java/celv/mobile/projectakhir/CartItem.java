package celv.mobile.projectakhir;

public class CartItem {
    private String menuName;
    private int hargaSatuan;
    private int jumlah;
    private String sambalLevel;
    private String catatan;

    public CartItem() {
    }

    public CartItem(String menuName, int hargaSatuan, int jumlah, String sambalLevel, String catatan) {
        this.menuName = menuName;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
        this.sambalLevel = sambalLevel;
        this.catatan = catatan;
    }

    // Getters and Setters
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(int hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getSambalLevel() {
        return sambalLevel;
    }

    public void setSambalLevel(String sambalLevel) {
        this.sambalLevel = sambalLevel;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}