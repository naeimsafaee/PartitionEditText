package ir.naeimsafaee.partitionedittext;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PartitionEditText partitionEditText = findViewById(R.id.partition);

        partitionEditText.setOnCompleteListener(new PartitionEditText.OnCompleteListener() {
            @Override
            public void onCompleteListener(String text) {
                Toast.makeText(MainActivity.this, "All steps completed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleteOneStepListener(String text, int position) {
                Toast.makeText(MainActivity.this, "Step " + position + " completed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}