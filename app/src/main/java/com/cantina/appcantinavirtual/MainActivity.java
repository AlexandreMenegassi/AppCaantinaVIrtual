package com.cantina.appcantinavirtual;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cantina.appcantinavirtual.fragments.ListProductFragment;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private TextView mTextMessage;
    private ListView listaViewCliente;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list_cliente:

                    return true;
                case R.id.navigation_list_product:
                    addFragment(ListProductFragment.class);
                    return true;
                case R.id.navigation_sales:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {
            database = openOrCreateDatabase("cantinaVitual", MODE_PRIVATE, null);
            listaViewCliente = findViewById(R.id.listaClienteId);

            database.execSQL("CREATE TABLE IF NOT EXISTS cliente(idCliente INTEGER PRIMARY KEY AUTOINCREMENT, nome varchar, telefone varchar, saldo real)");
            database.execSQL("CREATE TABLE IF NOT EXISTS produto(idProduto INTEGER PRIMARY KEY AUTOINCREMENT, nome varchar, categoria varchar, preco real)");
            database.execSQL("CREATE TABLE IF NOT EXISTS venda(idVenda INTEGER PRIMARY KEY AUTOINCREMENT, idCliente int, dataVenda date, FOREIGN KEY(idCliente) REFERENCES cliente(idCliente))");
            database.execSQL("CREATE TABLE IF NOT EXISTS vendaProduto(idVendaProduto INTEGER PRIMARY KEY AUTOINCREMENT, idVenda int, idProduto int, quantidade int, valor real, FOREIGN KEY(idVenda) REFERENCES venda(idVenda), FOREIGN KEY(idProduto) REFERENCES produto(idProduto))");

            listaViewCliente.setLongClickable(true);
            listaViewCliente.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    removerCliente(ids.get(position));
                    return true;
                }
            });

            //PRIMEIRA LISTA A APARECER NA TELA INICIAL
            listarCliente();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cadastrarCliente(String nome, String telefone, Float saldo){
        try {
            database.execSQL("INSERT INTO cliente (nome,telefone,saldo) VALUES ('" + nome.toUpperCase() + "','" + telefone + "','" + saldo + "')");
            listarCliente();
            Toast.makeText(MainActivity.this,"CLIENTE CADASTRADO COM SUCESSO!",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cadastrarProduto(String nome, String categoria, Float preco){
        try {
            database.execSQL("INSERT INTO produto (nome,categoria,preco) VALUES ('" + nome.toUpperCase() + "','" + categoria + "','" + preco + "')");
            //listarCliente();
            Toast.makeText(MainActivity.this,"PRODUTO CADASTRADO COM SUCESSO!",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarCliente(){
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM cliente", null);

            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_2,android.R.id.text2, itens);
            listaViewCliente.setAdapter( arrayAdapter );

            cursor.moveToFirst();

            while (cursor != null){
                itens.add(cursor.getString(cursor.getColumnIndex("nome")) + "            " + cursor.getString(cursor.getColumnIndex("telefone"))  + "            " + cursor.getString(cursor.getColumnIndex("saldo")));
                ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idCliente"))));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removerCliente(Integer id){
        try{
            database.execSQL("DELETE FROM cliente WHERE idCliente="+id);
            listarCliente();
            Toast.makeText(MainActivity.this,"Cliente removido com sucesso!",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cliente) {
            startActivity(new Intent(MainActivity.this, AddClientActivity.class));
        } else if (id == R.id.action_product) {
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void addFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        int fragment_container = 0;
        fragmentManager.beginTransaction().add(fragment_container, fragment).commit();
    }
}
