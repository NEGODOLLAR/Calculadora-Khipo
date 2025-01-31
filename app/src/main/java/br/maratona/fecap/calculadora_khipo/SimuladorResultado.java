package br.maratona.fecap.calculadora_khipo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SimuladorResultado extends AppCompatActivity {
    private ConstraintLayout arrowLayout;
    private ConstraintLayout contentLayout;
    private ImageView arrowIcon;
    private int measuredHeight;
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
        measuredHeight = contentLayout.getHeight();
        arrowLayout.setOnClickListener(v -> toggleAccordion());

        contentLayout.setVisibility(View.VISIBLE);
        measuredHeight = contentLayout.getHeight();
//        contentLayout.setVisibility(View.GONE);
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

        // Perform the collapsing or expanding animation on contentLayout
        if (isExpanded) {
            // Collapse (animate height to 0)
            collapseLayout();
        } else {
            // Expand (animate height to full size)
            expandLayout();
        }
    }

    private void expandLayout() {
        // Set the layout to VISIBLE before starting the animation
        contentLayout.setVisibility(View.VISIBLE);

        // Get the initial height (collapsed height)
        int initialHeight = 0;

        // Use ValueAnimator to animate the height expansion
        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 600);
        animator.setDuration(300); // Set your desired duration
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                // Update layout height dynamically during the animation
                ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
                params.height = value;
                contentLayout.setLayoutParams(params);
            }
        });
        animator.start();
    }

    private void collapseLayout() {
        // Get the initial height (current height)
        int initialHeight = contentLayout.getHeight();

        // Use ValueAnimator to animate the height collapse to 0
        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.setDuration(300); // Set your desired duration
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                // Update layout height dynamically during the animation
                ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
                params.height = value;
                contentLayout.setLayoutParams(params);
            }
        });

        // Optionally, set the layout to GONE after collapsing animation ends
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                contentLayout.setVisibility(View.GONE);
            }
        });

        animator.start();
    }
}