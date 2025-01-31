package br.maratona.fecap.calculadora_khipo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Simulador extends AppCompatActivity {


    TextInputEditText textVlMensal, textVHora, textInputQtHora, textInputRHora, textRMes,
            textInputSal, textInputQtMes;
    Double auxVHora, auxFatMensal, auxSalMensal, auxCReal,auxCMlk, auxVml, auxComPm,
            auxCAdm, auxCOp, auxMop, auxMlk, auxPercMop, auxPercMlk;
    int auxQtHora, auxQtMes;
    Button btnSimular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simulador);

        textInputQtHora = findViewById(R.id.textInputQtHora);
        textInputRHora = findViewById(R.id.textInputRHoras);
        textInputSal = findViewById(R.id.textInputSal);
        textInputQtMes = findViewById(R.id.textInputQtMes);

        textVlMensal = findViewById(R.id.textVlMensal);
        textVHora = findViewById(R.id.textVHora);
        textRMes = findViewById(R.id.textRMes);

        btnSimular = findViewById(R.id.btnSimular);

        // Listener
        btnSimular.setOnClickListener(v ->{
            String qtHora = textInputQtHora.getText().toString().trim();
            String vHora = textInputRHora.getText().toString().trim();
            String vSal = textInputSal.getText().toString().trim();
            String qtMes = textInputQtMes.getText().toString().trim();

            if(qtHora.isEmpty() || vHora.isEmpty() || vSal.isEmpty() || qtMes.isEmpty()){
                Toast.makeText(this, "Todos os campos devem ser preenchido.", Toast.LENGTH_SHORT).show();
            }
            else{
                //converção para cálculos
                auxQtHora = Integer.parseInt(qtHora);
                auxVHora = Double.parseDouble(vHora);
                auxSalMensal = Double.parseDouble(vSal);
                auxQtMes = Integer.parseInt(qtMes);
                //Calculo Faturação Mensal/ Valor Consultor
                auxFatMensal = auxQtHora * auxVHora;
                //Calculo Custo Real E MLK
                auxCReal = (auxSalMensal * 12) / auxQtMes;
                auxCMlk = (auxCReal * 0.1) + auxSalMensal;


                // Calculo Valor Margem Liquida
                auxVml = auxFatMensal - (auxFatMensal * 0.2);


                // Calculo Comissão_Prêmio Mensal | Custo Administrativo | Custo Operacional
                auxComPm = auxVml * 0.01;

                auxCAdm = auxFatMensal * 0.15;
                auxCOp = auxComPm + auxCAdm + auxCReal;
                // Calculo e porcentagem MOP e MLK
                auxMop = auxVml - auxCOp;
                auxPercMop = auxMop / auxVml;
                auxMlk = auxVml - (auxComPm + auxCAdm + auxCMlk);
                auxPercMlk = auxMlk / auxVml;

                // Levar informações para outra tela
                Intent intent = new Intent(Simulador.this, SimuladorResultado.class);

                intent.putExtra("QtHora", auxQtHora);
                intent.putExtra("VlrHora", auxVHora);
                intent.putExtra("Salario", auxSalMensal);
                intent.putExtra("MesesConsiderar", auxQtMes);

                intent.putExtra("FaturacaoCli", auxFatMensal);
                intent.putExtra("CustoReal", auxCReal);
                intent.putExtra("CustoMLK", auxCMlk);
                intent.putExtra("MargemLiquida", auxVml);
                intent.putExtra("Comissao", auxComPm);
                intent.putExtra("CustoAdministrativo", auxCAdm);
                intent.putExtra("CustoOperacional", auxCOp);
                intent.putExtra("MOP", auxMop);
                intent.putExtra("PorcentagemMOP", auxPercMop);
                intent.putExtra("MLK", auxMlk);
                intent.putExtra("PorcentagemMLK", auxPercMlk);

                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}