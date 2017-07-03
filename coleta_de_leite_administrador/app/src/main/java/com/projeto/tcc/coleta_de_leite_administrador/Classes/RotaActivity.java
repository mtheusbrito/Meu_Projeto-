package com.projeto.tcc.coleta_de_leite_administrador.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite_administrador.Adapter.RotaAdapter;
import com.projeto.tcc.coleta_de_leite_administrador.Dao.RotaDao;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Coletas;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Rota;
import com.projeto.tcc.coleta_de_leite_administrador.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RotaActivity extends AppCompatActivity {
    public static final String ROTA_ID="com.projeto.tcc.coleta_de_leite.rotaid";
    public static final String motoristaId="com.projeto.tcc.coleta_de_leite.motoristaId";
    ListView listViewRota;
    LinearLayout linearLayout;
    TextView nome_motorista;
    FrameLayout framevazio,frameLista;
    DatabaseReference databaseRotas;
    List<Rota> rotaList;
    int aux;
    String stringBuscas;
    long strData;
    String idmotorista;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    View coordinator;
    Motorista motorista;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);
        Intent intent=getIntent();
        listarIds();
        idmotorista=intent.getStringExtra(MotoristaActivity.motoristaId);
        motorista  =(Motorista) getIntent().getSerializableExtra("dados_motorista");
        databaseRotas= FirebaseDatabase.getInstance().getReference("rotas").child(motorista.getMatricula());
        auth = FirebaseAuth.getInstance();





    }
    private void listarIds() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rota);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando rotas...");
        progressDialog.show();
        listViewRota=(ListView) findViewById(R.id.list_rota);
        listViewRota.setDivider(this.getResources().getDrawable(R.drawable.transperent_color));
        listViewRota.setDividerHeight(20);
        rotaList= new ArrayList<>();
        frameLista=(FrameLayout)findViewById(R.id.frame_lista);
        framevazio=(FrameLayout)findViewById(R.id.frame_vazio);
        nome_motorista=(TextView)findViewById(R.id.text_motorista);
        coordinator= findViewById(R.id.coodinator);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desejos,menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actio_sair) {
            auth.signOut();
            Intent intent=new Intent(RotaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.action_settings){
            buscaDialog();
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onStart() {
        super.onStart();

        databaseRotas.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                aux=0;
                rotaList.clear();
                for(DataSnapshot rotaSnapShot:dataSnapshot.getChildren()){

                    Rota rota =rotaSnapShot.getValue(Rota.class);
                    rotaList.add(rota);
                }
                RotaAdapter rotaAdapter = new RotaAdapter(RotaActivity.this,rotaList);

                listViewRota.setAdapter(rotaAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        nome_motorista.setText("Transportador "+motorista.getNome());
        aux=0;


    }
    private void buscaDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_busca, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);



        final    CalendarView data=(CalendarView) findViewById(R.id.calendar_busca);
        final Button buttonAceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button buttonCancelar = (Button) dialogView.findViewById(R.id.button_cancelar);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strData=data.getDate();
                Toast.makeText(RotaActivity.this,""+ strData,Toast.LENGTH_LONG).show();
                //buscaPorData(""+strData);
                b.dismiss();



            }


        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();


            }
        });
    }
    private void buscaPorData(final String data) {

        databaseRotas.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                rotaList.clear();
                aux=0;
                for(DataSnapshot rotaSnapShot:dataSnapshot.getChildren()){

                    Rota rota =rotaSnapShot.getValue(Rota.class);
                    if (data.equals(rota.getHoraRota())){
                        aux=aux+1;
                        rotaList.add(rota);
                    }

                }

                RotaAdapter rotaAdapter = new RotaAdapter(RotaActivity.this,rotaList);

                listViewRota.setAdapter(rotaAdapter);
                if (aux>0){
                     stringBuscas="Buscas foi encontrada";}
                if (aux==0){
                        aux= Integer.parseInt(null);
                         stringBuscas="Nenhuma busca foi encontrada";
                    }
                    Snackbar snackbar=   Snackbar.make(coordinator, aux+stringBuscas+"", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPadrao));

                    snackbar.setAction("RECARREGAR ROTAS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           onStart();


                        }
                    })
                            .show();

                    progressDialog.dismiss();
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }


}
