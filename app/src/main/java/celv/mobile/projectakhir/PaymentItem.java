package  celv.mobile.projectakhir;

public class PaymentItem {
    private String paymentMethod;
    private int imageResId;

    public PaymentItem(String paymentMethod, int imageResId) {
        this.paymentMethod = paymentMethod;
        this.imageResId = imageResId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getImageResId() {
        return imageResId;
    }
}
