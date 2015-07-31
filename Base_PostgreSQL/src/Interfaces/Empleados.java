
package Interfaces;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andy
 */
public class Empleados extends javax.swing.JFrame {

    //DECLARACION DE VARIABLES GLOBALES
    DefaultTableModel model;
    
    //
    public Empleados() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);//para centrar el frame
        botonesIniciales();
        cargarTablaEmpleados1("");
        txtCedula.requestFocus();
        
        tblEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                 if(tblEmpleados.getSelectedRow()!=-1){
                    int fila=tblEmpleados.getSelectedRow();
                    txtCedula.setText(tblEmpleados.getValueAt(fila, 0).toString());
                    txtNombre.setText(tblEmpleados.getValueAt(fila, 1).toString());
                    txtApellido.setText(tblEmpleados.getValueAt(fila, 2).toString());
                    txtDireccion.setText(tblEmpleados.getValueAt(fila, 3).toString());
                    txtTelefono.setText(tblEmpleados.getValueAt(fila, 4).toString());
                    txtSueldo.setText(tblEmpleados.getValueAt(fila, 5).toString());
                }
                desbloquear();
                txtCedula.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnBorrar.setEnabled(true);
            }
        });
    }
    
    public void limpiar(){
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtSueldo.setText("");
        txtBusquedaporCedula.setText("");
    }

        public void bloquear(){
        txtCedula.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtSueldo.setEnabled(false);
    }
        
        public void desbloquear(){
        txtCedula.setEnabled(true);
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtSueldo.setEnabled(true);
    }
        
        public void botonesIniciales(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnSalir.setEnabled(true);
        bloquear();
    }
        
        public void botonNuevo(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(true);
        limpiar();
        desbloquear();
        txtCedula.requestFocus();
    }
        public void pintarLBLnegro(){//pinta a negro cuando ya se a introducido valores en los txt
         
          lblcedula.setForeground(Color.BLACK);
          lblnombre.setForeground(Color.BLACK);
          lblapellido.setForeground(Color.BLACK);
          lbldireccion.setForeground(Color.BLACK);
          lbltelefono.setForeground(Color.BLACK);
          lblsueldo.setForeground(Color.BLACK);
     }
        
        public void botonCancelar(){
        botonesIniciales();
        limpiar();
        bloquear();
        pintarLBLnegro();
    }
         public void guardar(){
        
             if(txtCedula.getText().isEmpty()){
            lblcedula.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Cédula del Empleado");
            txtCedula.requestFocus();
        }else if (txtNombre.getText().isEmpty()){
            lblnombre.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Nombre del Empleado");
            txtNombre.requestFocus();
        }else if(txtApellido.getText().isEmpty()){
            lblapellido.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Apellido del Empleado");
            txtApellido.requestFocus();
        }else if(txtDireccion.getText().isEmpty()){
            lbldireccion.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Dirección del Empleado");
            txtDireccion.requestFocus();
        }else if(txtTelefono.getText().isEmpty()){
            lbltelefono.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Teléfono del Empleado");
            txtTelefono.requestFocus();
        }else if(txtSueldo.getText().isEmpty()){
            lblsueldo.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Sueldo del Empleado");
            txtSueldo.requestFocus();
        } else{
          pintarLBLnegro();
      
          
        String emp_cedula,emp_nomrbe,emp_apellido,emp_direccion,emp_telefono, emp_sueldo;
        emp_cedula=txtCedula.getText();
        emp_nomrbe=txtNombre.getText();
        emp_apellido=txtApellido.getText();
        emp_direccion=txtDireccion.getText();
        emp_telefono=txtTelefono.getText();
        emp_sueldo=txtSueldo.getText();
        Double sueldo= Double.parseDouble(emp_sueldo);
              
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="insert into empleado (ci_emp,nom_emp,ape_emp,dir_emp,tel_emp,sue_emp) values(?,?,?,?,?,?)";
        try {
            PreparedStatement psd=cn.prepareStatement(sql);//aqui van las creadas arriba en string
            
            psd.setString(1,emp_cedula);
            psd.setString(2,emp_nomrbe);
            psd.setString(3,emp_apellido);
            psd.setString(4, emp_direccion);
            psd.setString(5, emp_telefono);
            psd.setDouble(6, sueldo);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Se insertó correctamente");
                limpiar();
                bloquear();
                botonesIniciales();
                cargarTablaEmpleados1("");
            }
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    }
         
        public void cargarTablaEmpleados1(String dato){
        String []titulos={"CÉDULA","NOMBRE","APELLIDO","DIRECCIÓN","TELÉFONO","SUELDO ($)"};
        String [] registros=new String [6];
        Conexion cc= new Conexion();
        Connection cn = cc.conectar();
        model = new DefaultTableModel(null,titulos);
        String sql="";
        dato=txtBusquedaporCedula.getText();
        sql="select * from empleado where ci_emp like'%"+dato+"%' order by nom_emp,ape_emp";
        try {
            Statement psd = cn.createStatement();//createStatement se usa para mostrar mas de 1 registro
            ResultSet rs=psd.executeQuery(sql); //devuelve los valores en forma de registros
            while(rs.next()){
                registros[0]=rs.getString("ci_emp");
                registros[1]=rs.getString("nom_emp");
                registros[2]=rs.getString("ape_emp");
                registros[3]=rs.getString("dir_emp");
                registros[4]=rs.getString("tel_emp");
                registros[5]=rs.getString("sue_emp");
               
                model.addRow(registros);
            }
            tblEmpleados.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en la tblEmpleados"+ex);
        }
    }
       
        public void actualizar(){
                
               if(txtCedula.getText().isEmpty()){
            lblcedula.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Cédula del Empleado");
            txtCedula.requestFocus();
        }else if (txtNombre.getText().isEmpty()){
            lblnombre.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Nombre del Empleado");
            txtNombre.requestFocus();
        }else if(txtApellido.getText().isEmpty()){
            lblapellido.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Apellido del Empleado");
            txtApellido.requestFocus();
        }else if(txtDireccion.getText().isEmpty()){
            lbldireccion.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Dirección del Empleado");
            txtDireccion.requestFocus();
        }else if(txtTelefono.getText().isEmpty()){
            lbltelefono.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Teléfono del Empleado");
            txtTelefono.requestFocus();
        }else if(txtSueldo.getText().isEmpty()){
            lblsueldo.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Sueldo del Empleado");
            txtSueldo.requestFocus();
        } else{
            
            pintarLBLnegro();
            
          Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="";
         sql="update empleado set  nom_emp='"+txtNombre.getText()+"', ape_emp='"+txtApellido.getText()+"', dir_emp='"+txtDireccion.getText()+"', tel_emp='"+txtTelefono.getText()+"', sue_emp= '"+txtSueldo.getText()+"' where ci_emp='"+txtCedula.getText()+"'";
               try {
                   PreparedStatement psd= cn.prepareStatement(sql);
                   int n=psd.executeUpdate();
                   if(n>0){
                       JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
                       limpiar();
                       cargarTablaEmpleados1("");
                       bloquear();
                       botonesIniciales();
                   }
               } catch (Exception ex) {
                   JOptionPane.showMessageDialog(null, "Problemas en actualizar btn "+ex);
               }
        }
        }
        
         public void borrar(){//para btnBorrar
         if(JOptionPane.showConfirmDialog(null, "¿Estás seguro(a) que quieres borrar el dato?","Borrar Registro",JOptionPane.YES_NO_OPTION)==(JOptionPane.YES_OPTION)){
             Conexion cc = new Conexion();
             Connection cn= cc.conectar();
             String sql = "";
             sql="delete from empleado where ci_emp='"+txtCedula.getText()+"'";
             try {
                 PreparedStatement psd = cn.prepareStatement(sql);
                 int n = psd.executeUpdate();
                 if(n>0){
                     
                     cargarTablaEmpleados1("");
                     limpiar();
                 }
             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Problemas para borrar el dato "+ ex);
             }
             
         }  
         
     }
         
          public void actualizarTabla(){//actualizar desde la tabla en la tabla evt key presed
        Conexion cc= new Conexion();
        Connection cn =cc.conectar();
        String sql="";
            int fila=tblEmpleados.getSelectedRow();
            int columna=tblEmpleados.getSelectedColumn();
            tblEmpleados.setEditingRow(0);
        sql="update empleado set nom_emp='"+tblEmpleados.getValueAt(fila, 1).toString()+"', ape_emp='"+tblEmpleados.getValueAt(fila, 2).toString()+"', dir_emp='"+tblEmpleados.getValueAt(fila, 3).toString()+"', tel_emp='"+tblEmpleados.getValueAt(fila, 4).toString()+", sue_emp='"+tblEmpleados.getValueAt(fila, 5)+"' where ci_emp='"+tblEmpleados.getValueAt(fila, 0).toString()+"'";
        try {
            PreparedStatement psd= cn.prepareStatement(sql);
            int n= psd.executeUpdate();
            if (n>0){
                JOptionPane.showMessageDialog(null, "Se Actualizó La Base");
                cargarTablaEmpleados1("");
                limpiar();
                bloquear();
            }
            cargarTablaEmpleados1("");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }
          
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblcedula = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        lblapellido = new javax.swing.JLabel();
        lbldireccion = new javax.swing.JLabel();
        lbltelefono = new javax.swing.JLabel();
        lblsueldo = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtSueldo = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtBusquedaporCedula = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ingreso de Empleados");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Empleado"));
        jPanel1.setToolTipText("Ingreso de Datos del Empleado");

        lblcedula.setText("Cédula:");

        lblnombre.setText("Nombre:");

        lblapellido.setText("Apellido:");

        lbldireccion.setText("Dirección:");

        lbltelefono.setText("Teléfono:");

        lblsueldo.setText("Sueldo:");

        txtSueldo.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblsueldo)
                            .addComponent(lbltelefono))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelefono)
                            .addComponent(txtSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblcedula)
                        .addGap(35, 35, 35)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblnombre)
                        .addGap(29, 29, 29)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblapellido)
                            .addComponent(lbldireccion))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblapellido)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbldireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltelefono)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblsueldo))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevo2.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(null);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIconTextGap(-2);
        btnNuevo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevo3.png"))); // NOI18N
        btnNuevo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevo.png"))); // NOI18N
        btnNuevo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnGuardar2.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIconTextGap(-3);
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnGuardar3.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnGuardar.png"))); // NOI18N
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnActualizar2.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(null);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizar.setIconTextGap(-8);
        btnActualizar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnActualizar3.png"))); // NOI18N
        btnActualizar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnActualizar.png"))); // NOI18N
        btnActualizar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnBorrar2.png"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.setBorder(null);
        btnBorrar.setBorderPainted(false);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBorrar.setIconTextGap(-4);
        btnBorrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnBorrar3.png"))); // NOI18N
        btnBorrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnBorrar.png"))); // NOI18N
        btnBorrar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnCancelar2.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setIconTextGap(-4);
        btnCancelar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnCancelar3.png"))); // NOI18N
        btnCancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnCancelar.png"))); // NOI18N
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnSalir2.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(null);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setIconTextGap(-4);
        btnSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnSalir3.png"))); // NOI18N
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnSalir.png"))); // NOI18N
        btnSalir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addComponent(btnCancelar))
                .addGap(13, 13, 13))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda del Empledo por Cédula"));

        txtBusquedaporCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaporCedulaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(txtBusquedaporCedula)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtBusquedaporCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblEmpleados.setToolTipText("");
        jScrollPane2.setViewportView(tblEmpleados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtBusquedaporCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaporCedulaKeyReleased
        cargarTablaEmpleados1(txtBusquedaporCedula.getText().trim());//para buscar por cedula 
    }//GEN-LAST:event_txtBusquedaporCedulaKeyReleased

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
       botonNuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
       actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        botonCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleados().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblapellido;
    private javax.swing.JLabel lblcedula;
    private javax.swing.JLabel lbldireccion;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblsueldo;
    private javax.swing.JLabel lbltelefono;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBusquedaporCedula;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSueldo;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
