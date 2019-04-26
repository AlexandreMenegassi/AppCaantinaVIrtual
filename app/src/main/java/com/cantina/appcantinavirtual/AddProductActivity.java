package com.cantina.appcantinavirtual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddProductActivity extends MainActivity {

    private EditText name;
    private EditText valor;
    private RadioGroup rdGroup;
    private RadioButton rbSalgado;
    private RadioButton rbDoce;
    private RadioButton rbBebida;
    private Button btnCadastrarProduto;
    private ImageView voltarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.addProductNameId);
        valor = findViewById(R.id.addProductMoneyId);
        rdGroup = findViewById(R.id.rdCategoryProductId);
        rbSalgado = findViewById(R.id.rdCategorySalgadoId);
        rbDoce = findViewById(R.id.rdCategoryDoceId);
        rbBebida = findViewById(R.id.rdCategoryBebidaId);
        btnCadastrarProduto = findViewById(R.id.addBtnProductId);
        voltarMain = findViewById(R.id.btnVoltarId);

        voltarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductActivity.this, MainActivity.class));
            }
        });

        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = name.getText().toString();
                String total = valor.getText().toString();
                String cat = "";

                if (nome.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "INFORME O NOME DO PRODUTO", Toast.LENGTH_LONG).show();
                } else if (total.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "INFORME O VALOR DO PRODUTO", Toast.LENGTH_LONG).show();
                } else if (!rbSalgado.isChecked() && !rbDoce.isChecked() && !rbBebida.isChecked()) {
                    Toast.makeText(AddProductActivity.this, "INFORME UMA CATEGORIA", Toast.LENGTH_LONG).show();
                } else {
                    switch (R.id.rdCategoryProductId) {
                        case R.id.rdCategorySalgadoId:
                            cat = "SALGADO";
                            break;
                        case R.id.rdCategoryDoceId:
                            cat = "DOCE";
                            break;
                        case R.id.rdCategoryBebidaId:
                            cat = "BEBIDA";
                            break;
                    }
                    Float money = Float.parseFloat(total);
                    cadastrarProduto(nome, cat, money);
                    startActivity(new Intent(AddProductActivity.this, MainActivity.class));
                }
            }
        });
    }
}
