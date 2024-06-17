package com.mycompany.dukemarket.javafx.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aluno
 */
public class Produto {
    
    int Id;
    String codBarras;
    String descricao;
    Calendar DataCadastro;
    double Qtd;
    double valorCompra;
    double valorVenda;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataCadastro() {
        if (this.DataCadastro != null ){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(this.DataCadastro.getTime());
        } else {
            return "";
        }
        
    }

    public void setDataCadastro(String strDataCadastro) {
        try{
            this.DataCadastro = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.DataCadastro.setTime(sdf.parse(strDataCadastro));
        }catch (ParseException ex){
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public double getQtd() {
        return Qtd;
    }

    public void setQtd(double Qtd) {
        this.Qtd = Qtd;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }
    
    @Override
    public String toString(){
        
        return String.format("%3d | %15s | %-30s | %10.2f | %10.2f | %10.2f | %10s", this.Id, 
                this.codBarras, this.descricao, this.Qtd, this.valorCompra, 
                this.valorVenda, this.getDataCadastro());
         
    }

    
    
    
}
