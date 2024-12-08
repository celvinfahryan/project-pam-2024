package celv.mobile.projectakhir;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class RangkumanHistory1 extends AppCompatActivity implements View.OnClickListener {

    private ImageButton inikeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangkuman_history1);

        this.inikeluar = this.findViewById(R.id.imageButton3);
        this.inikeluar.setOnClickListener(RangkumanHistory1.this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}