package dvs.mobile.filkomfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<PaymentItem> paymentList;

    private int selectedPosition = -1;

    public PaymentAdapter(List<PaymentItem> paymentList) {
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        PaymentItem paymentItem = paymentList.get(position);
        holder.textPaymentMethod.setText(paymentItem.getPaymentMethod());
        holder.imageView.setImageResource(paymentItem.getImageResId());

        holder.radioButton.setChecked(position == selectedPosition);

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousSelected = selectedPosition;
                selectedPosition = holder.getAdapterPosition();

                if (previousSelected != -1) {
                    notifyItemChanged(previousSelected);
                }
                notifyItemChanged(selectedPosition);

                Toast.makeText(v.getContext(),
                        "Metode pembayaran dipilih: " + paymentItem.getPaymentMethod(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.radioButton.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    // Tambahkan method untuk mendapatkan item yang dipilih
    public PaymentItem getSelectedItem() {
        if (selectedPosition != -1) {
            return paymentList.get(selectedPosition);
        }
        return null;
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        public TextView textPaymentMethod;
        public ImageView imageView;
        public RadioButton radioButton;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            textPaymentMethod = itemView.findViewById(R.id.textView1);
            imageView = itemView.findViewById(R.id.imageView);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}