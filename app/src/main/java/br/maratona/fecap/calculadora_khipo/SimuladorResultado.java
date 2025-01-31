package br.maratona.fecap.calculadora_khipo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SimuladorResultado extends AppCompatActivity {

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
        ConstraintLayout arrowOpcoes, arrowCustoAnual, arrowMargem, arrowCusto, arrowTotal;
        ConstraintLayout contentLayout, contentLayoutCustoAnual, contentLayoutMargem, contentLayoutCusto, contentLayoutTotal;
        ImageView arrowIcon, arrowIconCustoAnual, arrowIconMargem, arrowIconCusto, arrowIconTotal;

        arrowOpcoes = findViewById(R.id.arrowLayout);
        arrowCustoAnual = findViewById(R.id.arrowLayoutCustoAnual);
        arrowMargem = findViewById(R.id.arrowLayoutMargem);
        arrowCusto = findViewById(R.id.arrowLayoutCusto);
        arrowTotal = findViewById(R.id.arrowLayoutTotal);


        contentLayout = findViewById(R.id.contentLayout);
        contentLayoutCustoAnual = findViewById(R.id.contentLayoutCustoAnual);
        contentLayoutMargem = findViewById(R.id.contentLayoutMargem);
        contentLayoutCusto = findViewById(R.id.contentLayoutCusto);
        contentLayoutTotal = findViewById(R.id.contentLayoutTotal);

        arrowIcon = findViewById(R.id.arrowIcon);
        arrowIconCustoAnual = findViewById(R.id.arrowIconCustoAnual);
        arrowIconMargem = findViewById(R.id.arrowIconMargem);
        arrowIconCusto = findViewById(R.id.arrowIconCusto);
        arrowIconTotal = findViewById(R.id.arrowIconTotal);

        arrowOpcoes.setOnClickListener(v -> toggleAccordion(contentLayout, arrowIcon));
        arrowCustoAnual.setOnClickListener(v -> toggleAccordion(contentLayoutCustoAnual, arrowIconCustoAnual));
        arrowMargem.setOnClickListener(v -> toggleAccordion(contentLayoutMargem, arrowIconMargem));
        arrowCusto.setOnClickListener(v -> toggleAccordion(contentLayoutCusto, arrowIconCusto));
        arrowTotal.setOnClickListener(v -> toggleAccordion(contentLayoutTotal, arrowIconTotal));


    }
    private void toggleAccordion(ConstraintLayout contentLayout, ImageView arrowIcon) {
        boolean isExpanded = contentLayout.getVisibility() == View.VISIBLE;

        // Rotate the arrow icon
        RotateAnimation rotateAnimation = new RotateAnimation(
                isExpanded ? 180 : 0, isExpanded ? 0 : 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true); // Retain the rotation after the animation ends
        arrowIcon.startAnimation(rotateAnimation);

        // Perform the collapsing or expanding animation on contentLayout
        if (isExpanded) {
            // Collapse (animate height to 0)
            contentLayout.setVisibility(View.GONE);
        } else {
            // Expand (animate height to full size)
            contentLayout.setVisibility(View.VISIBLE);
        }
    }

}