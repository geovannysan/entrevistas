package com.example.entrevistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonRecycler extends RecyclerView.Adapter<PokemonRecycler.ViewHolder>
        implements View.OnClickListener , Filterable {

    List<PokemonSpecies> pokemonSpecies;
    private View.OnClickListener listener;

    public Context mContext;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick( view );
        }
    }
    public PokemonRecycler(List<PokemonSpecies> models1) {
        this.modellist = models1;
        notifyDataSetChanged();
        nodelisfil = new ArrayList<>( modellist );

    }


    public List<PokemonSpecies> modellist;
    public List<PokemonSpecies> nodelisfil;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.items,parent,false );
        PokemonRecycler.ViewHolder viewHolder = new   PokemonRecycler.ViewHolder( view );

        view.setOnClickListener( listener );


        mContext = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.primero.setText(modellist.get(position).getName());

        Glide.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/giodrive-75b7b.appspot.com/o/pokemon%2F"+String.format("%03d", modellist.get(position).getNumber())+".png?alt=media&token=d59e57ca-2b13-40dd-806d-42da0c89d7fc")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.segundo);

    }



    @Override
    public int getItemCount() {
        return modellist  == null ? 0 : modellist.size();
    }

    @Override
    public Filter getFilter() {
        return filtto;
    }
    public Filter filtto = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PokemonSpecies> filterlist = new ArrayList<>(  );
            if (charSequence == null || charSequence.length() ==0){
                filterlist.addAll( nodelisfil );
            }else {
                String filterPa = charSequence.toString().toLowerCase().trim();
                for(PokemonSpecies item : nodelisfil){
                    if (item.getName().toLowerCase(  ).contains( filterPa ) ){
                        filterlist.add( item );
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values= filterlist;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            modellist.clear();
            modellist.addAll( (List)filterResults.values );
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView primero;
        private ImageView segundo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            primero = (TextView)itemView.findViewById(R.id.nombre);
            segundo = (ImageView)itemView.findViewById(R.id.imagen);

        }
    }
}
