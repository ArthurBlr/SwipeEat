package com.example.tests.ui.eat;

import com.example.tests.R;

import com.google.gson.Gson;


public class Jour {
    private int numeroJour;
    private Plat platMatin;
    private Plat platMidi;
    private Plat platSoir;

    public Jour(int numeroJour) {
        this.numeroJour = numeroJour;
        this.platMatin = new Plat(0,R.string.txt_butt_add);
        this.platMidi = new Plat(0,R.string.txt_butt_add);
        this.platSoir = new Plat(0,R.string.txt_butt_add);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public Jour fromJson(String json) {
        Gson gson = new Gson();
        Jour jour = gson.fromJson(json, Jour.class);
        return jour;
    }

    public int getNumeroJour() {
        return numeroJour;
    }

    public int getPlatMatin() {
        return platMatin.id;
    }

    public int getPlatMidi() {
        return platMidi.id;
    }

    public int getPlatSoir() {
        return platSoir.id;
    }

    public int getNomPlatMatin() {
        return platMatin.nom;
    }

    public int getNomPlatMidi() {
        return platMidi.nom;
    }


    public int getNomPlatSoir() {
        return platSoir.nom;
    }

    private class Plat {
        private int id;
        private int nom;

        public Plat(int id, int nom) {
            this.id = id;
            this.nom = nom;
        }

        public int getId() {
            return id;
        }

        public int getNom() {
            return nom;
        }
    }
}

