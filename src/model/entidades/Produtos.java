
package model.entidades;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;

/**
 *
 * @author Leonardo Veloso
 */

public class Produtos {
    
    private int id;
    private String nome;
    private double preco;
    private String codigo;
    private double quantidade;
    LocalDate validade;
    
    public Produtos(int id, String n, double p, String c, double q, LocalDate v){
        setId(id);
        setNome(n);
        setPreco(p);
        setCodigo(c);
        setQuantidade(q);
        setValidade(v);
        
    }
    
     public Produtos(int id, String n, String p, String c, String q, LocalDate v) throws ParseException{
        setId(id);
        setNome(n);
        setPreco(p);
        setCodigo(c);
        setQuantidade(q);
        setValidade(v);
     }
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setPreco(String preco) throws ParseException{
        NumberFormat nf = NumberFormat.getInstance();
        this.preco = nf.parse(preco).doubleValue();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    
    public void setQuantidade(String quantidade) throws ParseException{
         NumberFormat nf = NumberFormat.getInstance();
        this.quantidade = nf.parse(quantidade).doubleValue();
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
    
    
}
