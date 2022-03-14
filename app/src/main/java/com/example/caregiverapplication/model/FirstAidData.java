package com.example.caregiverapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URL;

@Entity(tableName = "tbl_first_aid")
public class FirstAidData {

    @PrimaryKey(autoGenerate = true)
    private int faid;

    private String name;

    private String instruction;

    private String caution;

    private String photo;

    public FirstAidData(String name, String instruction, String caution, String photo) {

        this.name = name;
        this.instruction = instruction;
        this.caution = caution;
        this.photo = photo;
    }

    public int getFaid() {
        return faid;
    }

    public void setFaid(int faid) {
        this.faid = faid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
