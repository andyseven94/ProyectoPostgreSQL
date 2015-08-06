/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import fondo.FondoInter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carrera
 */
public class Ciudad_proveedor extends javax.swing.JFrame {
    DefaultTableModel modelo;
    public Ciudad_proveedor() {
        initComponents();
       // fondociudadesproveedores.setBorder(new FondoInter());
        botonesiniciales();
        bloquear();
        cargartabla("");
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
               if(tabla.getSelectedRow()!=-1){
                   int fila =tabla.getSelectedRow();
                   txtcod.setText(tabla.getValueAt(fila, 0).toString());
                   txtciudadorigen.setText(tabla.getValueAt(fila, 1).toString());
                   txtciudaddestino.setText(tabla.getValueAt(fila, 2).toString());
                   desbloquear();
                    txtcod.setEnabled(false);
                    btnActualizar.setEnabled(true);
                    btnNuevo.setEnabled(false);
                    btnGuardar.setEnabled(false);
                    btnBorrar.setEnabled(true);
                    btnCancelar.setEnabled(true);  
               }
//                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    public void limpiar(){
        txtcod.setText("");
        txtciudadorigen.setText("");
        txtciudaddestino.setText("");
    }
   
    public void bloquear(){
        txtcod.setEnabled(false);
        txtciudadorigen.setEnabled(false);
        txtciudaddestino.setEnabled(false);
    }
        public void desbloquear(){
        txtcod.setEnabled(true);
        txtciudadorigen.setEnabled(true);
        txtciudaddestino.setEnabled(true);
    }
    public void botonesiniciales(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
        
    }
     public void botonnuevo(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
    
     }
     public void botoncancelar(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
        limpiar();
        bloquear();
     }
    public void guardar(){
        Conexion conect= new Conexion();
        Connection cn= conect.conectar();
        String sql, codigo, nombre, ubicacion;
        sql="";
        codigo=txtcod.getText();
        nombre=txtciudadorigen.getText();
        ubicacion=txtciudaddestino.getText();
        sql="insert into public.ciudades_proveedores(cod_ciu_prov,nom_ciu_prov,des_ciu_prov) values(?,?,?)";
        try{
            PreparedStatement psd= cn.prepareStatement(sql);
            psd.setString(1, codigo);
            psd.setString(2, nombre);
            psd.setString(3, ubicacion);
            int n= psd.executeUpdate();
            if(n>0){
              JOptionPane.showMessageDialog(null, "Se inserto");
              limpiar();
                    bloquear();
                    botonesiniciales();
                    cargartabla("");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void actualizar(){
        Conexion conect= new Conexion();
        Connection cn= conect.conectar();
        String sql="";
        
        sql="update public.ciudades_proveedores set nom_ciu_prov='"+txtciudadorigen.getText()+"'"+
              ",des_ciu_prov='"+txtciudaddestino.getText()+"'"+
              "WHERE cod_ciu_prov='"+txtcod.getText()+"'";
        try{
            PreparedStatement psd= cn.prepareStatement(sql);
            int n= psd.executeUpdate();
            if(n>0){
              JOptionPane.showMessageDialog(null, "SE ACTUALIZO");
              cargartabla("");
              limpiar();
              bloquear();
              botonesiniciales();
              txtBuscar.setText("");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void cargartabla(String dato){
        String[]titulos={"CODIGO","NOMBRE","UBICACION"};
        String[]registros= new String[3];
        Conexion conect= new Conexion();
        Connection cn= conect.conectar();
        modelo= new DefaultTableModel(null,titulos);
        String sql=""; 
        sql="SELECT * FROM public.ciudades_proveedores where cod_ciu_prov like '%"+dato+"%' order by nom_ciu_prov,cod_ciu_prov";
        try{
            Statement psd= cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            while(rs.next()){
                registros [0]=rs.getString("cod_ciu_prov");
                registros [1]=rs.getString("nom_ciu_prov");
                registros [2]=rs.getString("des_ciu_prov");
                modelo.addRow(registros);
            }
            tabla.setModel(modelo);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void borrar(){
        if (JOptionPane.showConfirmDialog(null,"Esta seguro que deseas borrar el dato","Borrar Registro",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                Conexion cc=new Conexion();
                Connection cn=cc.conectar();
                String sql="";
                sql="DELETE FROM public.ciudades_proveedores where cod_ciu_prov='"+txtcod.getText()+"'";
        try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n=psd.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null,"Se borro correctamente");
                    cargartabla("");
                    limpiar();
                    bloquear();
                    botonesiniciales();
                }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al borrar"+ex);
        }           
        }
    }
    public void actualizarTabla(){
            String sql1="";
            int fila=tabla.getSelectedRow();
              sql1="SELECT * FROM public.ciudades_proveedores WHERE cod_ciu_prov LIKE '"+txtcod.getText()+"'";
            btnActualizar.setEnabled(true);
            System.out.println(sql1);
            if(tabla.getSelectedRow()!=-1){
            if(tabla.getValueAt(fila,1).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar la ciudad origen del proveedor");
                tabla.editCellAt(fila, 1);
//                txtAutoMarca.requestFocus();
            }
            else if(tabla.getValueAt(fila,2).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar la ciudad destino del proveedor");
                tabla.editCellAt(fila, 2);
//                txtAutoColor.requestFocus();
            }
            else{
                Conexion cc=new Conexion();
                Connection cn=cc.conectar();
                String sql="";
                sql="UPDATE public.ciudades_proveedores SET nom_ciu_prov='"+tabla.getValueAt(fila,1).toString()+"', des_ciu_prov='"+tabla.getValueAt(fila,2).toString()+"'"
                        + " where cod_ciu_prov ='"+txtcod.getText()+"'";
                try{
                    PreparedStatement psd = cn.prepareStatement(sql);
                    int n=psd.executeUpdate();
                    if(n>0){
                        JOptionPane.showMessageDialog(null,"Actualizacion Correcta");
                        cargartabla("");
                        limpiar();
                        bloquear();
                        botonesiniciales();
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"No se pudo actualizar"+ex);
                }
            }
        btnActualizar.setEnabled(true);
          }
            }
public void buscar_clave_primaria(){
        Conexion conect= new Conexion();
        Connection cn= conect.conectar();
        String sql="select count(*) as contar from public.ciudades_proveedores where cod_ciu_prov ='"+txtcod.getText()+"'";;
        try {
            Statement psd=cn.createStatement();
//            int n=psd.executeUpdate(sql);
            ResultSet rs=psd.executeQuery(sql);
             int contar1=0;
             while(rs.next()){
                 contar1=rs.getInt("contar");
             }
                //contar1=rs.getInt("contar");
                System.out.println(contar1);
             if(contar1>0){
                    JOptionPane.showMessageDialog(null,"El codigo ya existe");
                    txtcod.setText("");
                    txtcod.requestFocus();
              
            }
             
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al insertar  "+ex);
        }
        
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondociudadesproveedores = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtcod = new javax.swing.JTextField();
        txtciudaddestino = new javax.swing.JTextField();
        txtciudadorigen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fondociudadesproveedores.setVisible(true);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnBorrar.setText("BORRAR");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        txtcod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodActionPerformed(evt);
            }
        });
        txtcod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodFocusLost(evt);
            }
        });

        txtciudaddestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtciudaddestinoActionPerformed(evt);
            }
        });

        txtciudadorigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtciudadorigenActionPerformed(evt);
            }
        });

        jLabel1.setText("CODIGO CIUDAD");

        jLabel2.setText("CIUDAD DEL PROVEEDOR");

        jLabel3.setText("CIUDAD DESTINO DEL PROVEEDOR");

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel8.setText("Busqueda por codigo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("CIUDADES PROVEEDORES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtciudaddestino, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addComponent(txtciudadorigen)
                                .addComponent(txtcod)))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtciudadorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtciudaddestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout fondociudadesproveedoresLayout = new javax.swing.GroupLayout(fondociudadesproveedores.getContentPane());
        fondociudadesproveedores.getContentPane().setLayout(fondociudadesproveedoresLayout);
        fondociudadesproveedoresLayout.setHorizontalGroup(
            fondociudadesproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondociudadesproveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondociudadesproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondociudadesproveedoresLayout.setVerticalGroup(
            fondociudadesproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondociudadesproveedoresLayout.createSequentialGroup()
                .addGroup(fondociudadesproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondociudadesproveedoresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fondociudadesproveedoresLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondociudadesproveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fondociudadesproveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        // TODO add your handling code here:
        botonesiniciales();
        bloquear();
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargartabla(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
        actualizarTabla();
    }
    }//GEN-LAST:event_tablaKeyReleased

    private void txtcodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodFocusLost
        // TODO add your handling code here:
        buscar_clave_primaria();
    }//GEN-LAST:event_txtcodFocusLost

    private void txtcodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodActionPerformed
        // TODO add your handling code here:
        txtciudadorigen.requestFocus();
    }//GEN-LAST:event_txtcodActionPerformed

    private void txtciudadorigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtciudadorigenActionPerformed
        // TODO add your handling code here:
        txtciudaddestino.requestFocus();
    }//GEN-LAST:event_txtciudadorigenActionPerformed

    private void txtciudaddestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtciudaddestinoActionPerformed
        // TODO add your handling code here:
        btnNuevo.requestFocus();
    }//GEN-LAST:event_txtciudaddestinoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        botoncancelar();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        borrar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardar();

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquear();
        botonnuevo();

    }//GEN-LAST:event_btnNuevoActionPerformed

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
            java.util.logging.Logger.getLogger(Ciudad_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ciudad_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ciudad_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ciudad_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ciudad_proveedor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JInternalFrame fondociudadesproveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtciudaddestino;
    private javax.swing.JTextField txtciudadorigen;
    private javax.swing.JTextField txtcod;
    // End of variables declaration//GEN-END:variables
}
