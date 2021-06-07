package examen_final;

public class Datos_Personales {
    private int codigo;
    private String nombre;
    private String descripcion;
    private String adquisicion;
    private String vencimiento;
    private String compra;
    private String venta;
    
    //crear un constructor vacio
    public Datos_Personales(){
    }
//Creacion de un constuctor
    public Datos_Personales(int codigo, String nombre, String descripcion, String adquisicion, String vencimiento, String compra, String venta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.adquisicion = adquisicion;
        this.vencimiento = vencimiento;
        this.compra = compra;
        this.venta = venta;
    }
   //Set y Get

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAdquisicion() {
        return adquisicion;
    }

    public void setAdquisicion(String adquisicion) {
        this.adquisicion = adquisicion;
    }
    
    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }
    
    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }
    
    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }
    
}
