package br.maratona.fecap.calculadora_khipo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.TextViewKt;

import com.google.android.material.textfield.TextInputEditText;

import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;

public class SimuladorResultado extends AppCompatActivity {
    double qtHora, vHora, vSal, qtMes, auxFatMensal, auxCReal, auxCMlk, auxVml, auxComPm, auxCAdm, auxCOp, auxMop, auxPercMop, auxMlk, auxPercMlk;

    TextInputEditText inputValorMensal, inputValorHora, inputQuantidadeHora, inputHora, inputMes;
    TextView txtValorLiquido, txtComissao, txtMOP, txtMLK;
    TextView txtSalarioMensal, txtMargemMOP,txtMargemMLK;
    TextView txtCustoComissao, txtCustoAdministrativo,txtCustoOperacional;
    TextView txtTotal;
    ConstraintLayout arrowOpcoes, arrowCustoAnual, arrowMargem, arrowCusto, arrowTotal;
    ConstraintLayout contentLayout, contentLayoutCustoAnual, contentLayoutMargem, contentLayoutCusto, contentLayoutTotal;
    ImageView arrowIcon, arrowIconCustoAnual, arrowIconMargem, arrowIconCusto, arrowIconTotal;
    Intent intent;

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
        intent = getIntent();
        inicializarVariaveis();


        inputValorMensal.setText(formatarEmReais(auxFatMensal));
        inputValorHora.setText(formatarEmReais(vHora));

        inputQuantidadeHora.setText((int) qtHora + "");
        inputHora.setText(formatarEmReais(vHora));
        inputMes.setText(formatarEmReais(auxFatMensal));

        txtValorLiquido.setText(formatarEmReais(auxVml));
        txtComissao.setText(formatarEmReais(auxComPm ));
        txtMOP.setText(formatarEmReais(auxMop * -1));
        txtMLK.setText(formatarEmReais(auxMlk * -1));

        txtSalarioMensal.setText(formatarEmReais(vSal));

        txtMargemMOP.setText(formatarEmReais(auxMop * -1) + " | " + formatPercentage(auxPercMop * -1));
        txtMargemMLK.setText(formatarEmReais(auxMlk * -1) + " | " + formatPercentage(auxPercMlk * -1));

        txtComissao.setText(formatarEmReais(auxComPm));
        txtCustoAdministrativo.setText(formatarEmReais(auxCAdm));
        txtCustoOperacional.setText(formatarEmReais(auxCOp));

        txtTotal.setText(formatarEmReais(auxCAdm + auxCOp));

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

    private void inicializarVariaveis(){
        qtHora = intent.getIntExtra("QtHora", 0);
        vHora = intent.getDoubleExtra("VlrHora", 0);
        vSal = intent.getDoubleExtra("Salario", 0);
        qtMes = intent.getIntExtra("MesesConsiderar", 0);

        auxFatMensal = intent.getDoubleExtra("FaturacaoCli", 0.0);
        auxCReal = intent.getDoubleExtra("CustoReal", 0.0);
        auxCMlk = intent.getDoubleExtra("CustoMLK", 0.0);
        auxVml = intent.getDoubleExtra("MargemLiquida", 0.0);
        auxComPm = intent.getDoubleExtra("Comissao", 0.0);
        auxCAdm = intent.getDoubleExtra("CustoAdministrativo", 0.0);
        auxCOp = intent.getDoubleExtra("CustoOperacional", 0.0);
        auxMop = intent.getDoubleExtra("MOP", 0.0);
        auxPercMop = intent.getDoubleExtra("PorcentagemMOP", 0.0);
        auxMlk = intent.getDoubleExtra("MLK", 0.0);
        auxPercMlk = intent.getDoubleExtra("PorcentagemMLK", 0.0);

        // Imprimindo no Logcat para debug
        Log.d("Debug", "QtHora: " + qtHora);
        Log.d("Debug", "VlrHora: " + vHora);
        Log.d("Debug", "Salario: " + vSal);
        Log.d("Debug", "MesesConsiderar: " + qtMes);

        Log.d("Debug", "FaturacaoCli: " + auxFatMensal);
        Log.d("Debug", "CustoReal: " + auxCReal);
        Log.d("Debug", "CustoMLK: " + auxCMlk);
        Log.d("Debug", "MargemLiquida: " + auxVml);
        Log.d("Debug", "Comissao: " + auxComPm);
        Log.d("Debug", "CustoAdministrativo: " + auxCAdm);
        Log.d("Debug", "CustoOperacional: " + auxCOp);
        Log.d("Debug", "MOP: " + auxMop);
        Log.d("Debug", "PorcentagemMOP: " + auxPercMop);
        Log.d("Debug", "MLK: " + auxMlk);
        Log.d("Debug", "PorcentagemMLK: " + auxPercMlk);

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

        inputValorMensal = findViewById(R.id.inputValorMensal);
        inputValorHora = findViewById(R.id.inputValorHora);
        inputQuantidadeHora = findViewById(R.id.inputQuantidadeHora);
        inputHora = findViewById(R.id.inputHora);
        inputMes = findViewById(R.id.inputMes);

        txtValorLiquido = findViewById(R.id.txtValorLiquido);
        txtComissao = findViewById(R.id.txtComissao);
        txtMOP = findViewById(R.id.txtMOP);
        txtMLK = findViewById(R.id.txtMLK);

        txtSalarioMensal = findViewById(R.id.txtSalarioMensal);
        txtMargemMOP = findViewById(R.id.txtMargemMOP);
        txtMargemMLK = findViewById(R.id.txtMargemMLK);

        txtCustoComissao = findViewById(R.id.txtCustoComissao);
        txtCustoAdministrativo = findViewById(R.id.txtCustoAdministrativo);
        txtCustoOperacional = findViewById(R.id.txtCustoOperacional);

        txtTotal = findViewById(R.id.txtTotal);
    }
    public String formatarEmReais(double valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }
    public String formatPercentage(double decimalPercentage) {
        return String.format("%.1f%%", decimalPercentage * 100);
    }
}