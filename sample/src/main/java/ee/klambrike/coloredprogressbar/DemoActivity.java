package ee.klambrike.coloredprogressbar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ee.klambrike.library.ColoredProgress;
import ee.klambrike.library.ProgressElement;

public class DemoActivity extends AppCompatActivity {
    private boolean isGreyScale = false;
    private int max = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final ColoredProgress coloredProgress = findViewById(R.id.colored_progress);

        Button btnAnimate = findViewById(R.id.btnAnimate1);
        btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coloredProgress.toggleElementsToFillBar();
            }
        });

        Button btnToggleGreyScale = findViewById(R.id.btnToggleGreyScale);
        btnToggleGreyScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGreyScale = !isGreyScale;
                coloredProgress.setProgressGreyScale(isGreyScale);
            }
        });

        Button btnPopulate = findViewById(R.id.btnPopulate);
        btnPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coloredProgress.addProgressElement(new ProgressElement(
                        ContextCompat.getColor(DemoActivity.this, R.color.blue),
                        (int) (Math.random()*30)));
                coloredProgress.addProgressElement(new ProgressElement(
                        ContextCompat.getColor(DemoActivity.this, R.color.pink),
                        (int) (Math.random()*30)));
                coloredProgress.addProgressElement(new ProgressElement(
                        ContextCompat.getColor(DemoActivity.this, R.color.green),
                        (int) (Math.random()*30)));
                coloredProgress.addProgressElement(new ProgressElement(
                        ContextCompat.getColor(DemoActivity.this, R.color.yellow),
                        (int) (Math.random()*30)));
            }
        });

        Button reset = findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coloredProgress.clearProgress();
            }
        });
    }
}