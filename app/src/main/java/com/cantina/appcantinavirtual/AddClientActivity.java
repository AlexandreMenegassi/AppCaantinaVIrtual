package com.cantina.appcantinavirtual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddClientActivity extends MainActivity {

    private EditText nome;
    private EditText fone;
    private EditText saldo;
    private Button cadastrar;
    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        nome = findViewById(R.id.addClientNameId);
        fone = findViewById(R.id.addClientTelephoneId);
        saldo = findViewById(R.id.addClientMoneyId);
        cadastrar = findViewById(R.id.addBtnClientId);
        voltar = findViewById(R.id.btnVoltarId);

        cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String money = "0";
                String name = nome.getText().toString();
                String phone = fone.getText().toString();
                money += saldo.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddClientActivity.this, "INFORME O NOME DO CLIENTE", Toast.LENGTH_LONG).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(AddClientActivity.this, "INFORME O TELEFONE DO CLIENTE", Toast.LENGTH_LONG).show();
                } else if (!verificarNumeroTelefone(phone)) {
                    Toast.makeText(AddClientActivity.this, "NÚMERO DE TELEFONE ESTÁ INCORRETO", Toast.LENGTH_LONG).show();
                } else {
                    Float diners = Float.parseFloat(money);
                    cadastrarCliente(name,phone,diners);
                    startActivity(new Intent(AddClientActivity.this, MainActivity.class));
                }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddClientActivity.this, MainActivity.class));
            }
        });
    }

    public boolean verificarNumeroTelefone(String num) {
        return num.length() > 8;
    }
}
