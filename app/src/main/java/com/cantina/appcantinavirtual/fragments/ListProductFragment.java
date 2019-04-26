package com.cantina.appcantinavirtual.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cantina.appcantinavirtual.R;

import java.util.ArrayList;

public class ListProductFragment extends Fragment {

    private SQLiteDatabase database;
    private ArrayAdapter<String> arrayAdapterProduto;
    private ListView listaViewProduto;
    private ArrayList<String> itensProduto;
    private ArrayList<Integer> idsProduto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_product, container, false);
        listarProduto();
        listaViewProduto = view.findViewById(R.id.listaProdutoId);
        listaViewProduto.setLongClickable(true);
        listaViewProduto.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removerProduto(idsProduto.get(position));
                return true;
            }
        });
        return view;
    }

    public void listarProduto(){
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM produto", null);

            itensProduto = new ArrayList<String>();
            idsProduto = new ArrayList<Integer>();
            arrayAdapterProduto = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_2,android.R.id.text2, itensProduto);
            listaViewProduto.setAdapter( arrayAdapterProduto );

            cursor.moveToFirst();

            while (cursor != null){
                itensProduto.add(cursor.getString(cursor.getColumnIndex("nome")) + "            " + cursor.getString(cursor.getColumnIndex("categoria"))  + "            " + cursor.getString(cursor.getColumnIndex("preco")));
                idsProduto.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idProduto"))));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removerProduto(Integer id){
        try{
            database.execSQL("DELETE FROM venda WHERE idVenda="+id);
            listarProduto();
            Toast.makeText(getContext().getApplicationContext(),"Produto removido com sucesso!",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
