/*
 * VentasTodas.java
 *
 * Created on 23-jul-2015, 20:21:38
 */
package Interfaces;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Erika
 */
public class Venta extends javax.swing.JFrame {
DefaultTableModel modelo;
    /** Creates new form VentasTodas */
    public Venta() {
        initComponents();
        getContentPane().setBackground(Color.white);
        txtCodigo.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        llenadoEmpleado();
        llenadoCliente();
        llenadoProducto();
    }
    
    public void llenadoEmpleado(){
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="SELECT * FROM public.empleado";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            jcbEmpleado.addItem(rs.getString("ci_emp"));
        }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error de Llenado de Empleado"+ex);
        } 
    }
    public void llenadoCliente(){
          Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="SELECT * FROM public.clientes";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            jcbCliente.addItem(rs.getString("id_cli"));
        }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error de Llenado de Cliente"+ex);
        } 
    }
    public void llenadoProducto(){
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="SELECT * FROM public.productos";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            jcbProducto.addItem(rs.getString("cod_pro"));
        }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error de Llenado de Productos"+ex);
        } 
    }
       public void controlCantidadAntes(){
        if(jcbProducto.getSelectedItem().equals("Seleccione...")){
            jcbProducto.requestFocus();
            JOptionPane.showMessageDialog(null, "Seleccione primero un producto");
        }
    }
          public void controlCantidad(){
            if(!jcbProducto.getSelectedItem().toString().equals("Seleccione...")){
               Conexion cc=new Conexion();
            Connection cn=cc.conectar();
            String sql=""; int cantidad=0;
            sql="SELECT stock FROM public.productos where cod_pro='"+jcbProducto.getSelectedItem().toString()+"';";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            cantidad=Integer.valueOf(rs.getString("stock"));
        }
            if(Integer.valueOf(txtCantidad.getText().trim())>cantidad){
                 JOptionPane.showMessageDialog(null, "Producto Insuficiente");
                 txtCantidad.setText("");
                 txtCantidad.requestFocus();
            }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error de Llenado de Productos"+ex);
        }  
            }
    }
    
    public void consultar(){
        String [] titulos={"Producto","Cantidad","Precio","Subtotal"};
        String [] registros= new String[4];
        float total=0,subtotal=0;
        Conexion cc= new Conexion();
        Connection cn=cc.conectar();
        modelo= new DefaultTableModel(null, titulos);
        jtbDetalle.setModel(modelo);
        String sql="",aux="";
        sql="SELECT * FROM public.detalle_venta where num_ven="+txtCodigo.getText()+";";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            while(rs.next()){
                registros[0]=rs.getString("COD_PRO");
                registros[1]=rs.getString("CANT_VEN");
                registros[2]=rs.getString(sql);
                registros[3]=rs.getString(sql);
                total=total+subtotal;
                modelo.addRow(registros);
            }
            jtbDetalle.setModel(modelo);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "CONSULTA ERRONEA "+e);
        }
        
    }
    public void llenadodatosclientes(){
        if(!jcbCliente.getSelectedItem().toString().equals("Seleccione...")){
            Conexion cc=new Conexion();
            Connection cn=cc.conectar();
            String sql="";
            sql="SELECT nom_cli as nombre, ape_cli as apellido from public.clientes where id_cli='"+jcbCliente.getSelectedItem().toString()+"'";
            try {
                Statement psd=cn.createStatement();
                ResultSet rs=psd.executeQuery(sql);
                while(rs.next()){
                    txtNombreCliente.setText(rs.getString("nombre"));
                    txtApellidoCliente.setText(rs.getString("apellido"));
                }
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de consulta de clientes "+e);
        }
        }
    }
      public void llenadodatosempleados(){
        if(!jcbEmpleado.getSelectedItem().toString().equals("Seleccione...")){
            Conexion cc=new Conexion();
            Connection cn=cc.conectar();
            String sql="";
            sql="SELECT nom_emp as nombre, ape_emp as apellido from public.empleado where ci_emp='"+jcbEmpleado.getSelectedItem().toString()+"'";
            try {
                Statement psd=cn.createStatement();
                ResultSet rs=psd.executeQuery(sql);
                while(rs.next()){
                    txtNombreEmpleado.setText(rs.getString("nombre"));
                    txtApellidoEmpleado.setText(rs.getString("apellido"));
                }    
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de consulta de empleados "+e);
        } 
        }else{
            txtNombreEmpleado.setText("");
            txtApellidoEmpleado.setText("");
        }   
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        txtTotal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jcbCliente = new javax.swing.JComboBox();
        jcbEmpleado = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtNombreEmpleado = new javax.swing.JTextField();
        txtApellidoEmpleado = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtApellidoCliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDetalle = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jcbProducto = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtCantidad = new ComponentesPropios.txtEntero();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Total:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Venta"));

        jLabel3.setText("Num. Venta:");

        jLabel4.setText("Empleado: ");

        jLabel5.setText("Cliente:");

        jLabel6.setText("Fecha:");

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jcbCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        jcbCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbClienteItemStateChanged(evt);
            }
        });
        jcbCliente.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jcbClientePropertyChange(evt);
            }
        });

        jcbEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        jcbEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbEmpleadoMouseClicked(evt);
            }
        });
        jcbEmpleado.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jcbEmpleadoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jcbEmpleado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbEmpleadoItemStateChanged(evt);
            }
        });
        jcbEmpleado.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jcbEmpleadoPropertyChange(evt);
            }
        });

        jLabel7.setText("Nombre:");

        txtNombreEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEmpleadoKeyTyped(evt);
            }
        });

        txtApellidoEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoEmpleadoKeyTyped(evt);
            }
        });

        jLabel8.setText("Apellido:");

        jLabel9.setText("Apellido:");

        txtApellidoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoClienteKeyTyped(evt);
            }
        });

        jLabel10.setText("Nombre:");

        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbCliente, 0, 159, Short.MAX_VALUE)
                                    .addComponent(jcbEmpleado, 0, 159, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addGap(45, 45, 45)
                        .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addGap(45, 45, 45)
                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(txtApellidoCliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jtbDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtbDetalle);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Venta"));

        jLabel12.setText("Producto:");

        jcbProducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        jcbProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbProductoItemStateChanged(evt);
            }
        });

        jLabel15.setText("Cantidad:");

        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(38, 38, 38)
                .addComponent(jcbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(218, 218, 218)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
// TODO add your handling code here:
}//GEN-LAST:event_txtCodigoKeyTyped

private void txtNombreEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEmpleadoKeyTyped
// TODO add your handling code here:
}//GEN-LAST:event_txtNombreEmpleadoKeyTyped

private void txtApellidoEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoEmpleadoKeyTyped
// TODO add your handling code here:
}//GEN-LAST:event_txtApellidoEmpleadoKeyTyped

private void txtApellidoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoClienteKeyTyped
// TODO add your handling code here:
}//GEN-LAST:event_txtApellidoClienteKeyTyped

private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
// TODO add your handling code here:
}//GEN-LAST:event_txtNombreClienteKeyTyped

private void jcbEmpleadoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jcbEmpleadoPropertyChange
/// TODO add your handling code here:
}//GEN-LAST:event_jcbEmpleadoPropertyChange

private void jcbClientePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jcbClientePropertyChange
// TODO add your handling code here:
}//GEN-LAST:event_jcbClientePropertyChange

private void jcbProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbProductoItemStateChanged

}//GEN-LAST:event_jcbProductoItemStateChanged

private void jcbEmpleadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbEmpleadoItemStateChanged
llenadodatosempleados();// TODO add your handling code here:
}//GEN-LAST:event_jcbEmpleadoItemStateChanged

private void jcbClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbClienteItemStateChanged
llenadodatosclientes();// TODO add your handling code here:
}//GEN-LAST:event_jcbClienteItemStateChanged

private void jcbEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbEmpleadoMouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jcbEmpleadoMouseClicked

private void jcbEmpleadoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jcbEmpleadoPopupMenuWillBecomeInvisible
// TODO add your handling code here:
}//GEN-LAST:event_jcbEmpleadoPopupMenuWillBecomeInvisible

private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
controlCantidadAntes();// TODO add your handling code here:
}//GEN-LAST:event_txtCantidadFocusGained

private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
controlCantidad();// TODO add your handling code here:
}//GEN-LAST:event_txtCantidadFocusLost

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
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Venta().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbCliente;
    private javax.swing.JComboBox jcbEmpleado;
    private javax.swing.JComboBox jcbProducto;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JTable jtbDetalle;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextField txtApellidoEmpleado;
    private ComponentesPropios.txtEntero txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
