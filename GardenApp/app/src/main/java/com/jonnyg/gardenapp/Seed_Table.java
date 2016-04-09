package com.jonnyg.gardenapp;

import android.widget.Toast;

/**
 * Created by reiko_000 on 03/02/2016.
 */
public class Seed_Table {

    private int _id;
    private String _seedName;
    private String _seedType;
    //private String _amount;
    private int _amount;

    public Seed_Table(){

    }
    public Seed_Table(String sname, String stype, int amount) {
        this._seedName = sname;
        this._seedType = stype;
        this._amount = amount;
    }

    public Seed_Table(String name){
        this._seedName = name;
    }



    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_seedName(String _seedName) {
        this._seedName = _seedName;
    }

    public void set_seedType(String _seedType) {
        this._seedType = _seedType;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }
/////////////////////////////////GETS///////////////////////////
    public int get_id() {

        return _id;
    }

    public String get_seedName() {
        return _seedName;
    }

    public int get_amount() {
        return _amount;
    }

    public String get_seedType() {
        return _seedType;
    }
}
