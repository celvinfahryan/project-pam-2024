package dvs.mobile.filkomfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dvs.mobile.filkomfood.firebase.PaymentModel;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentHistoryViewHolder> {
    private List<PaymentModel> paymentList;
    // Tambahkan variable untuk tracking radio button yang aktif
    private int selectedPosition = -1;

    public PaymentHistoryAdapter(List<PaymentModel> paymentList) {
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new PaymentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentHistoryViewHolder holder, int position) {
        PaymentModel paymentItem = paymentList.get(position);
        holder.title.setText(paymentItem.getMethod());
        holder.serialNumber.setText("SN: " + paymentItem.getTransactionNo());
        holder.time.setText(paymentItem.getTimeStamp());
        int resId;
        switch (paymentItem.getMethod()) {
            case "GoPay":
                resId = R.drawable.ic_gopay;
                break;
            case "Dana":
                resId = R.drawable.ic_dana;
                break;
            case "OVO":
                resId = R.drawable.ic_ovo;
                break;
            case "ShopeePay":
                resId = R.drawable.ic_shopeepay;
                break;
            case "Transfer Bank Mandiri":
                resId = R.drawable.ic_mandiri;
                break;
            case "Transfer Bank BCA":
                resId = R.drawable.ic_bca;
                break;
            case "Transfer Bank BNI":
                resId = R.drawable.ic_bni;
                break;
            case "Transfer Bank BRI":
                resId = R.drawable.ic_bri;
                break;
            case "Transfer bank lainnya":
                resId = R.drawable.ic_bank;
                break;
            default:
                resId = 0;
        }

        holder.imageView.setImageResource(resId);

    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }


    public static class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public TextView serialNumber;
        public TextView time;

        public PaymentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.paymentMethod);
            serialNumber = itemView.findViewById(R.id.paymentNo);
            time = itemView.findViewById(R.id.time);
        }
    }
}