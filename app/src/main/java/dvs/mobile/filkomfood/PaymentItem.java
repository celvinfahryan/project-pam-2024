package dvs.mobile.filkomfood;

public class PaymentItem {
    private String name;     // Replacing paymentMethod with name
    private String icon;     // Replacing imageResId with icon
    private int imageResId;  // Keep the imageResId if you want to support both methods

    // Constructor for PaymentItem
    public PaymentItem(String name, String icon, int imageResId) {
        this.name = name;
        this.icon = icon;
        this.imageResId = imageResId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
