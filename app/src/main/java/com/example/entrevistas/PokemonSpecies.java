package com.example.entrevistas;

public class PokemonSpecies {
    String name;
    String url;
    public int numero;

    public PokemonSpecies(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PokemonSpecies() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
