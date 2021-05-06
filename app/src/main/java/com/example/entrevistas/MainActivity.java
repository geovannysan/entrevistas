package com.example.entrevistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Switch;

import com.example.entrevistas.Api.PokemonApi;
import com.example.entrevistas.Api.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    PokemonApi pokemonApi;
    private RecyclerView recy;
    private Switch   udn;
    private PokemonRecycler  listaPokemonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recy=(RecyclerView)findViewById( R.id.lista );
        recy.setLayoutManager( new LinearLayoutManager( this ) );
        pokemonApi = RetrofitClient.getRetrofit("https://pokeapi.co/api/v2/pokedex/kanto/").create(PokemonApi.class);
        dato();

    }
    public void dato(){
        pokemonApi.getpokemon("https://pokeapi.co/api/v2/pokedex/kanto/").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    final List<PokemonSpecies> models1 = new ArrayList<>(  );

                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("pokemon_entries");
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject route = jsonArray.getJSONObject( i );
                        JSONObject especie = route.getJSONObject( "pokemon_species" );
                        models1.add(new PokemonSpecies(especie.getString("name"),
                                especie.getString("url")));
                    }

                    listaPokemonAdapter = new PokemonRecycler(models1);
                    listaPokemonAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this,show.class);
                            intent.putExtra("id",models1.get(recy.getChildAdapterPosition(view)).getUrl() );
                            intent.putExtra("name",models1.get(recy.getChildAdapterPosition(view)).getName() );
                            startActivity(intent);                        }
                    });
                    recy.setAdapter(listaPokemonAdapter);
                    recy.setHasFixedSize(true);
                    GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,3);
                    recy.setLayoutManager(layoutManager);


                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.busqueda, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (listaPokemonAdapter !=null) {
                    listaPokemonAdapter.getFilter().filter( newText );
                }
                return false;


            }
        } );

        return true;
    }

}
