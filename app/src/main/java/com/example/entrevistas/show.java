package com.example.entrevistas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.entrevistas.Api.PokemonApi;
import com.example.entrevistas.Api.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class show extends AppCompatActivity {
    String dato,datos,nombres;
    PokemonApi pokemonApi,pokemonApir;
    ImageView fot;
    TextView deta,un,dos,texno;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);
        pokemonApi = RetrofitClient.getRetrofit("https://pokeapi.co").create(PokemonApi.class);
        pokemonApir = RetrofitClient.getRetrofit("https://pokeapi.co").create(PokemonApi.class);
        deta =(TextView)findViewById(R.id.detalle);
        un =(TextView)findViewById(R.id.text1);
        dos =(TextView)findViewById(R.id.text2);
        fot=(ImageView)findViewById(R.id.deti) ;
        texno=(TextView) findViewById(R.id.pokemo);

        Intent intent= getIntent();
        nombres = intent.getStringExtra("name");
        if(nombres !=null){
           // texno.setText(nombres);
        }
        datos= intent.getStringExtra( "id" );
        if (datos != null) {
            String[] urlPartes = datos.split("/");
            dato = urlPartes[urlPartes.length - 1];

            data();
            Glide.with(this)
                    .load("https://firebasestorage.googleapis.com/v0/b/giodrive-75b7b.appspot.com/o/pokemon%2F"+String.format("%03d", Integer.parseInt(dato))+".png?alt=media&token=d59e57ca-2b13-40dd-806d-42da0c89d7fc")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(fot);
            habili();
        }
    }

    private void data() {
        pokemonApir.getpokemon("https://pokeapi.co/api/v2/pokemon-species/"+dato+"/").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("flavor_text_entries");
                    StringBuffer cadena = new StringBuffer();
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject route = jsonArray.getJSONObject( i );
                        JSONObject especie = route.getJSONObject( "language" );
                        if (especie.getString("name").equals("es")) {
                            //String res =route.getString("flavor_text");
                            cadena.append(route.getString("flavor_text"));


                        }
                    }
                    deta.setSingleLine(false);
                    deta.setText(cadena);

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private  void habili(){
        pokemonApi.getpokemon("https://pokeapi.co/api/v2/pokemon/"+dato+"/").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray =jsonObject.getJSONArray("abilities");
                    for (int i = 0 ; i< jsonArray.length(); i ++){
                        JSONObject route = jsonArray.getJSONObject( 0 );
                        JSONObject especie = route.getJSONObject( "ability" );
                        String re = especie.getString("name");
                        un.setText(re);
                        JSONObject route1 = jsonArray.getJSONObject( 1 );
                        JSONObject especie1 = route1.getJSONObject( "ability" );
                        String res = especie1.getString("name");
                        dos.setText(res);


                    }

                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
