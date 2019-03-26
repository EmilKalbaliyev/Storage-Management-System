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
public class Hesabat {
    private String date;
    private String name;
    private int number;
    private String action;
    private String reason;

    public Hesabat(String date, String name, int number, String action, String reason) {
        this.date = date;
        this.name = name;
        this.number = number;
        this.action = action;
        this.reason = reason;
    }

    public Hesabat() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
   
    
}
