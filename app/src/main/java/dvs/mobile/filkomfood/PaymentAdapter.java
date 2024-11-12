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

        // Set the name
        holder.textPaymentMethod.setText(paymentItem.getName());

        // Dynamically load the drawable resource by its name
        int iconResId = holder.itemView.getContext().getResources()
                .getIdentifier(paymentItem.getIcon(), "drawable", holder.itemView.getContext().getPackageName());

        if (iconResId != 0) { // If resource ID is valid
            holder.imageView.setImageResource(iconResId);
        } else {
            // Set a default icon if resource is not found
            holder.imageView.setImageResource(R.drawable.ic_ovo);
        }

        // Set up the RadioButton with a listener
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Display Toast message
                    Toast.makeText(buttonView.getContext(), "Metode pembayaran dipilih: " + paymentItem.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
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
