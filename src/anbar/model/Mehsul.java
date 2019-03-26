/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anbar.model;

/**
 *
 * @author Mardan Safarov
 */
public class Mehsul {
    private int id;
    private String name;
    private int number;
    
    public Mehsul(int id, String name, int number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Mehsul() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
