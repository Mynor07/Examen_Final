/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen_final;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mynor
 */


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */


public class JFPrincipal extends javax.swing.JDialog {

    Limpiar_Archivo l= new Limpiar_Archivo();
    
    private String ruta_txt="UMG.txt";
    
    Datos_Personales p;
    ProcesoDatos pd;
     int clic_tabla;
    private Object llblruta;
    
    
    
    public JFPrincipal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pd = new ProcesoDatos();
      //  txtruta.setEditable(true);
        
        try{
            mostrar_txt();
            listarRegistro();
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }
    }

    JFPrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void limpiar (){
        txtcodigo.setText("");
        txtnombres.setText("");
        txtdescripcion.setText("");
        txtadquisicion.setText("");
        txtvencimiento.setText("");
        txtcompra.setText("");
        txtventa.setText("");
    }
    public void mostrar_txt(){
        File ruta = new File(ruta_txt);
        try{
            
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                p = new Datos_Personales();
                p.setCodigo(Integer.parseInt(st.nextToken()));
                p.setNombre(st.nextToken());
                p.setDescripcion(st.nextToken());
                p.setAdquisicion(st.nextToken());
                p.setVencimiento(st.nextToken());
                p.setCompra(st.nextToken());
                p.setVenta(st.nextToken());
                pd.crearRegistro(p);
            }
            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
    
    public void guardar_txt(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(ruta_txt);
            pw = new PrintWriter(fw);
            
            for(int i = 0; i < pd.cantidadRegistros(); i++){
                p = pd.obtenerRegistro(i);
                pw.println(String.valueOf(p.getCodigo()+", "+p.getNombre()+", "+p.getDescripcion()+", "+p.getAdquisicion()+", "+p.getVencimiento()+", "+p.getCompra()+", "+p.getVenta()));
            }
             pw.close();
            
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
       
    }
    public void ingresarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerDescripcion() == null) mensaje("Ingresar Descripcion");
            else if(leerAdquisicion() == null)mensaje("Ingresar Adquisicion");
            else if(leerVencimiento() == null) mensaje("Ingresar Vencimiento");
            else if(leerCompra() == null)mensaje("Ingresar Compra");
            else if(leerVenta() == null) mensaje("Ingresar Venta");
            else{
                p = new Datos_Personales(leerCodigo(), leerNombre(), leerDescripcion(), leerAdquisicion(), leerVencimiento(), leerCompra(), (String) leerVenta());
                if(pd.buscarCodigo(p.getCodigo())!= -1)mensaje("Este codigo ya existe");
                else pd.crearRegistro(p);
                
                guardar_txt();
                listarRegistro();
                l.Limpiar_Archivo(Panel);
                
                
                
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    public void modificarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerDescripcion() == null) mensaje("Ingresar Descripcion");
            else if(leerAdquisicion() == null)mensaje("Ingresar Adquisicion");
            else if(leerVencimiento() == null) mensaje("Ingresar Vencimiento");
            else if(leerCompra() == null)mensaje("Ingresar Compra");
            else if(leerVenta() == null) mensaje("Ingresar Venta");
            else{
                int codigo = pd.buscarCodigo(leerCodigo());
                p = new Datos_Personales(leerCodigo(), leerNombre(), leerDescripcion(), leerAdquisicion(), leerVencimiento(), leerCompra(), (String) leerVenta());
                
                if(codigo == -1)pd.crearRegistro(p);
                else pd.modificarRegistro(codigo, p);
                
                guardar_txt();
                listarRegistro();
             l.Limpiar_Archivo(Panel);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void eliminarRegistro(){
        try{
            if(leerCodigo() == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = pd.buscarCodigo(leerCodigo());
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar a la Persona","Si/No",0);
                    if(s == 0){
                        pd.eliminarRegistros(codigo);
                        
                        guardar_txt();
                        listarRegistro();
                          l.Limpiar_Archivo(Panel);
                    }
                }
                
                
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Descripcion");
        dt.addColumn("Adquisicion");
        dt.addColumn("Vencimiento");
        dt.addColumn("Compra");
        dt.addColumn("Venta");
        tbldatos.setDefaultRenderer(Object.class, new IngresoTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < pd.cantidadRegistros(); i++){
            p = pd.obtenerRegistro(i);
            fila[0] = p.getCodigo();
            fila[1] = p.getNombre();
            fila[2] = p.getDescripcion();
            fila[3] = p.getAdquisicion();
            fila[4] = p.getVencimiento();
            fila[5] = p.getCompra();
            fila[6] = p.getVenta();
            dt.addRow(fila);
        }
        tbldatos.setModel(dt);
        tbldatos.setRowHeight(60);
    }
    
    public int leerCodigo(){
        try{
            int codigo = Integer.parseInt(txtcodigo.getText().trim());
            return codigo;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leerNombre(){
        try{
            String nombre = txtnombres.getText().trim().replace(" ", "_");
            return nombre;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String leerDescripcion(){
        try{
            String descripcion = txtdescripcion.getText().trim().replace(" ", "_");
            return descripcion;
        }catch(Exception ex){
            return null;
        }
    }
    
    public Object leerAdquisicion(){
        try{
            String adquisicion = txtadquisicion.getText().trim().replace(" ", "_");
            return adquisicion;
        }catch(Exception ex){
            return null;
        }
    }
    
    public Object leerVencimiento(){
        try{
            String vencimiento = txtvencimiento.getText().trim().replace(" ", "_");
            return vencimiento;
        }catch(Exception ex){
            return null;
        }
    }
    
    public Object leerCompra(){
        try{
            String compra = txtcompra.getText().trim().replace(" ", "_");
            return compra;
        }catch(Exception ex){
            return null;
        }
    }
    
    public Object leerVenta(){
        try{
            String venta = txtventa.getText().trim().replace(" ", "_");
            return venta;
        }catch(Exception ex){
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtnombres = new javax.swing.JTextField();
        txtdescripcion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcompra = new javax.swing.JTextField();
        txtventa = new javax.swing.JTextField();
        btnguardar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldatos = new javax.swing.JTable();
        txtvencimiento = new javax.swing.JTextField();
        txtadquisicion = new javax.swing.JTextField();
        btnmodificar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("ID Producto:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Nombre de Producto:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Fecha de Vencimiento:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Descripci??n del Producto:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Fecha de Adquisici??n:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Precio de Venta:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Precio de Compra:");

        txtdescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdescripcionActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel9.setText("Productos");

        txtventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtventaActionPerformed(evt);
            }
        });

        btnguardar.setBackground(new java.awt.Color(51, 51, 255));
        btnguardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnguardar.setForeground(new java.awt.Color(255, 255, 255));
        btnguardar.setText("Agregar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneliminar.setBackground(new java.awt.Color(255, 0, 0));
        btneliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btneliminar.setForeground(new java.awt.Color(255, 255, 255));
        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tbldatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Camisas", "Camisas Blancas", "04/04/2021", "Sin Fecha de Vencimiento", "Q 50.00", "Q. 99.00"},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre Producto", "Descripci??n", "Fecha de Adquisici??n", "Fecha de Vencimineto", "Precio Compra", "Precio Venta"
            }
        ));
        jScrollPane1.setViewportView(tbldatos);

        btnmodificar.setBackground(new java.awt.Color(255, 153, 102));
        btnmodificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnmodificar.setForeground(new java.awt.Color(255, 255, 255));
        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        jMenu1.setBackground(new java.awt.Color(51, 51, 255));
        jMenu1.setForeground(new java.awt.Color(51, 51, 255));
        jMenu1.setText("Proveedores");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setForeground(new java.awt.Color(51, 51, 255));
        jMenuItem1.setText("Comprar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1085, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnmodificar)
                                .addGap(30, 30, 30)
                                .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtventa, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtnombres, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtcodigo)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtdescripcion)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtvencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtcodigo)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtnombres)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtvencimiento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtventa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(jLabel7))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtdescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdescripcionActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        this.eliminarRegistro();
        limpiar();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        JDVenta jdventa= new JDVenta(new JFPrincipal(), true);
        jdventa.setLocationRelativeTo(null);
        jdventa.show();
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JDVenta jdventa= new JDVenta(new JFPrincipal(), true);
        jdventa.setLocationRelativeTo(null);
        jdventa.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        File ruta = new File(llblruta.getText());
        this.ingresarRegistro(ruta);
        limpiar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void txtventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtventaActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        // TODO add your handling code here:
        File ruta = new File(llblruta.getText());
        this.modificarRegistro(ruta);
        limpiar();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void tbldatosMouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        //Se crea esta validaci??n para que al momento de hacer clic sobre los datos seleccionados se autorellenen
        clic_tabla = tbldatos.rowAtPoint(evt.getPoint());
        
        int codigo = (int)tbldatos.getValueAt(clic_tabla, 0);
        String nombre = ""+tbldatos.getValueAt(clic_tabla, 1);
        String descripcion = ""+tbldatos.getValueAt(clic_tabla, 2);
        String adquisicion = ""+tbldatos.getValueAt(clic_tabla, 3);
        String vencimiento = ""+tbldatos.getValueAt(clic_tabla, 4);
        String compra = ""+tbldatos.getValueAt(clic_tabla, 5);
        String venta = ""+tbldatos.getValueAt(clic_tabla, 6);

        txtcodigo.setText(String.valueOf(codigo));
        txtnombres.setText(nombre);
        txtdescripcion.setText(descripcion);
        txtadquisicion.setText(adquisicion);
        txtvencimiento.setText(vencimiento);
        txtcompra.setText(compra);
        txtventa.setText(venta);
        
        try{
            JLabel lbl = (JLabel)tbldatos.getValueAt(clic_tabla, 7);
           
        }catch(Exception ex){
        }
    }       
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFPrincipal dialog = new JFPrincipal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbldatos;
    private javax.swing.JTextField txtadquisicion;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtcompra;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtnombres;
    private javax.swing.JTextField txtvencimiento;
    private javax.swing.JTextField txtventa;
    // End of variables declaration//GEN-END:variables

}
