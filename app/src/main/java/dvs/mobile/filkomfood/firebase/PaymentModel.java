package dvs.mobile.filkomfood.firebase;

public class PaymentModel {
    String uid;
    String method;
    String timeStamp;
    String transactionNo;

    public PaymentModel() {
    }

    public PaymentModel(String uid, String method, String timeStamp, String transactionNo) {
        this.uid = uid;
        this.method = method;
        this.timeStamp = timeStamp;
        this.transactionNo = transactionNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
}
