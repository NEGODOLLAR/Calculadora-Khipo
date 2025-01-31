package br.maratona.fecap.calculadora_khipo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SimuladorResultado extends AppCompatActivity {
    private LinearLayout arrowLayout;
    private LinearLayout contentLayout;
    private ImageView arrowIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simulador_resultado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        arrowLayout = findViewById(R.id.arrowLayout);
        contentLayout = findViewById(R.id.contentLayout);
        arrowIcon = findViewById(R.id.arrowIcon);

        arrowLayout.setOnClickListener(v -> toggleAccordion());
    }
    private void toggleAccordion() {
        boolean isExpanded = contentLayout.getVisibility() == View.VISIBLE;

        // Rotate the arrow icon
        RotateAnimation rotateAnimation = new RotateAnimation(
                isExpanded ? 180 : 0, isExpanded ? 0 : 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true); // Retain the rotation after the animation ends
        arrowIcon.startAnimation(rotateAnimation);

        // Toggle content visibility
        contentLayout.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
    }
}