
package Interfaces;

import java.awt.Color;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andy
 */
public class Clientes extends javax.swing.JFrame {

    //VARIABLES GLOBALES
    DefaultTableModel model;
    //hasta aqui variables globales
    public Clientes() {
        initComponents();
       // this.setLocationRelativeTo(null);//para frame en el CENTRO de la pantalla
        getContentPane().setBackground(Color.WHITE);//hace el frame color blanco
        botonesIniciales();
        cargrTipo_Clientes();
        cargrCiu_Clientes();
        cargarTablaClientes1("");
        txtDireccion.setDocument(new SoloMayusculas());
        txtCedula.requestFocus();
        
        tblClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//para seleccionar con el mouse en tiempo de ejecucion Este es un metodo solo.

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(tblClientes.getSelectedRow()!=-1){
                    int fila=tblClientes.getSelectedRow();
                    txtCedula.setText(tblClientes.getValueAt(fila, 0).toString());
                    jcbCiuadadCliente.setSelectedItem(tblClientes.getValueAt(fila, 1).toString());
                    jcbTipoCliente.setSelectedItem(tblClientes.getValueAt(fila, 2).toString());
                    txtNombre.setText(tblClientes.getValueAt(fila, 3).toString());
                    txtApellido.setText(tblClientes.getValueAt(fila, 4).toString());
                    txtDireccion.setText(tblClientes.getValueAt(fila, 5).toString());
                    txtTelefono.setText(tblClientes.getValueAt(fila, 6).toString());
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
        txtBusquedaporCedula.setText("");
    }
   
    public void bloquear(){
        txtCedula.setEnabled(false);
        jcbTipoCliente.setEnabled(false);
        jcbCiuadadCliente.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
    }
    
    public void desbloquear(){
        txtCedula.setEnabled(true);
        jcbTipoCliente.setEnabled(true);
        jcbCiuadadCliente.setEnabled(true);
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
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
    
    public void botonCancelar(){
        botonesIniciales();
        limpiar();
        bloquear();
        pintarLBLnegro();
    }
    
       public void cargrTipo_Clientes(){//PARA CARGAR CB
       Conexion cc = new Conexion();
       Connection cn = cc.conectar();
       String sql="";
       sql="select * from tipo_cliente";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
                String cod_tipo_cli=rs.getString("cod_tipo_cli");
                jcbTipoCliente.addItem(cod_tipo_cli.trim());
            }           
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar cb Tipo Cliente");
        }           
    }
    
      public void cargrCiu_Clientes(){//PARA CARGAR CB
       Conexion cc = new Conexion();
       Connection cn = cc.conectar();
       String sql="";
       sql="select * from ciudades_clientes";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
                String cod_ciu_cli=rs.getString("cod_ciu_cli");
                jcbCiuadadCliente.addItem(cod_ciu_cli.trim());
            }           
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar cb Ciudades Cliente");
        }           
    }
    
      
    public void guardar(){
        if(txtCedula.getText().isEmpty()){
            lblcedula.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Cédula del Cliente");
            txtCedula.requestFocus();
        }else if (txtNombre.getText().isEmpty()){
            lblnombre.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Nombre del Cliente");
            txtNombre.requestFocus();
        }else if(txtApellido.getText().isEmpty()){
            lblapellido.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Apellido del Cliente");
            txtApellido.requestFocus();
        }else if(txtDireccion.getText().isEmpty()){
            lbldireccion.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Dirección del Cliente");
            txtDireccion.requestFocus();
        }else if(txtTelefono.getText().isEmpty()){
            lbltelefono.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Teléfono del Cliente");
            txtTelefono.requestFocus();
        }else{
          pintarLBLnegro();
      
          
        String cli_cedula,tip_cli_codigo,ciu_cli_codigo,cli_nomrbe,cli_apellido,cli_direccion,cli_telefono,auto_capacidad;
        cli_cedula=txtCedula.getText();
        tip_cli_codigo=jcbTipoCliente.getSelectedItem().toString().substring(0,1).trim();// (0,1) devuelve indice -1 
        ciu_cli_codigo=jcbCiuadadCliente.getSelectedItem().toString().substring(0,5).trim();
        cli_nomrbe=txtNombre.getText();
        cli_apellido=txtApellido.getText();
        cli_direccion=txtDireccion.getText();
        cli_telefono=txtTelefono.getText();
        
        
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="insert into clientes (id_cli,cod_ciu_cli,cod_tipo_cli,nom_cli,ape_cli,dir_cli,tel_cli) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement psd=cn.prepareStatement(sql);//aqui van las creadas arriba en string
            
            psd.setString(1,cli_cedula);
            psd.setString(2,ciu_cli_codigo);
            psd.setString(3,tip_cli_codigo);
            psd.setString(4, cli_nomrbe);
            psd.setString(5, cli_apellido);
            psd.setString(6, cli_direccion);
            psd.setString(7, cli_telefono);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Se insertó correctamente");
                limpiar();
                bloquear();
                botonesIniciales();
                cargarTablaClientes1("");
            }
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    }
    
     public void cargarTablaClientes1(String dato){
        String []titulos={"CÉDULA","TIPO CLIENTE","CIUDAD","NOMBRE","APELLIDO","DIRECCIÓN","TELÉFONO"};
        String [] registros=new String [7];
        Conexion cc= new Conexion();
        Connection cn = cc.conectar();
        model = new DefaultTableModel(null,titulos);
        String sql="";
        dato=txtBusquedaporCedula.getText();
        sql="select * from clientes where id_cli like'%"+dato+"%' order by nom_cli,ape_cli";
        try {
            Statement psd = cn.createStatement();//createStatement se usa para mostrar msa de 1 registro
            ResultSet rs=psd.executeQuery(sql); //devuelve los valores en forma de registros
            while(rs.next()){
                registros[0]=rs.getString("id_cli");
                registros[1]=rs.getString("cod_ciu_cli");
                registros[2]=rs.getString("cod_tipo_cli");
                registros[3]=rs.getString("nom_cli");
                registros[4]=rs.getString("ape_cli");
                registros[5]=rs.getString("dir_cli");
                registros[6]=rs.getString("tel_cli");
                model.addRow(registros);
            }
            tblClientes.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en la tblClientes"+ex);
        }
    }
     
     public void pintarLBLnegro(){//pinta a negro cuando ya se a introducido valores en los txt
         
          lblcedula.setForeground(Color.BLACK);
          lblnombre.setForeground(Color.BLACK);
          lblapellido.setForeground(Color.BLACK);
          lbldireccion.setForeground(Color.BLACK);
          lbltelefono.setForeground(Color.BLACK);
     }
     
     public void actualizar(){//para btnActualizar
           if(txtCedula.getText().isEmpty()){
            lblcedula.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Cédula del Cliente");
            txtCedula.requestFocus();
        }else if (txtNombre.getText().isEmpty()){
            lblnombre.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Nombre del Cliente");
            txtNombre.requestFocus();
        }else if(txtApellido.getText().isEmpty()){
            lblapellido.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Apellido del Cliente");
            txtApellido.requestFocus();
        }else if(txtDireccion.getText().isEmpty()){
            lbldireccion.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Dirección del Cliente");
            txtDireccion.requestFocus();
        }else if(txtTelefono.getText().isEmpty()){
            lbltelefono.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Teléfono del Cliente");
            txtTelefono.requestFocus();
        }else{
            pintarLBLnegro();
            
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="";
         sql="update clientes set cod_ciu_cli='"+jcbCiuadadCliente.getSelectedItem().toString().substring(0,5).trim()+"', cod_tipo_cli='"+jcbTipoCliente.getSelectedItem().toString().substring(0,1).trim()+"', nom_cli='"+txtNombre.getText()+"', ape_cli='"+txtApellido.getText()+"', dir_cli='"+txtDireccion.getText()+"', tel_cli='"+txtTelefono.getText()+"' where id_cli='"+txtCedula.getText()+"'";
               try {
                   PreparedStatement psd= cn.prepareStatement(sql);
                   int n=psd.executeUpdate();
                   if(n>0){
                       JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
                       limpiar();
                       cargarTablaClientes1("");
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
             sql="delete from clientes where id_cli='"+txtCedula.getText()+"'";
             try {
                 PreparedStatement psd = cn.prepareStatement(sql);
                 int n = psd.executeUpdate();
                 if(n>0){
                     
                     cargarTablaClientes1("");
                     limpiar();
                     botonesIniciales();
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
            int fila=tblClientes.getSelectedRow();
            int columna=tblClientes.getSelectedColumn();
            tblClientes.setEditingRow(0);
        sql="update clientes set cod_ciu_cli='"+tblClientes.getValueAt(fila, 1).toString()+"',cod_tipo_cli='"+tblClientes.getValueAt(fila, 2).toString()+"',nom_cli='"+tblClientes.getValueAt(fila, 3).toString()+"', ape_cli='"+tblClientes.getValueAt(fila, 4).toString()+"', dir_cli='"+tblClientes.getValueAt(fila, 5).toString()+"', tel_cli='"+tblClientes.getValueAt(fila, 6).toString()+"'where id_cli='"+tblClientes.getValueAt(fila, 0).toString()+"'";
        try {
            PreparedStatement psd= cn.prepareStatement(sql);
            int n= psd.executeUpdate();
            if (n>0){
                JOptionPane.showMessageDialog(null, "Se Actualizó La Base");
                cargarTablaClientes1("");
                limpiar();
                bloquear();
            }
            cargarTablaClientes1("");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }

     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblnombre = new javax.swing.JLabel();
        lblapellido = new javax.swing.JLabel();
        lbltelefono = new javax.swing.JLabel();
        lbldireccion = new javax.swing.JLabel();
        lblcedula = new javax.swing.JLabel();
        lbltipocliente = new javax.swing.JLabel();
        lblciudad = new javax.swing.JLabel();
        jcbTipoCliente = new javax.swing.JComboBox();
        jcbCiuadadCliente = new javax.swing.JComboBox();
        txtCedula = new ComponentesPropios.txtEntero();
        txtNombre = new ComponentesPropios.txtLetrasMayusculas();
        txtApellido = new ComponentesPropios.txtLetrasMayusculas();
        txtTelefono = new ComponentesPropios.txtEntero();
        txtDireccion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtBusquedaporCedula = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ingreso de Clientes");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Clientes"));

        lblnombre.setText("Nombre:");

        lblapellido.setText("Apellido:");

        lbltelefono.setText("Teléfono:");

        lbldireccion.setText("Dirección:");

        lblcedula.setText("Cédula:");

        lbltipocliente.setText("Tipo Cliente:");

        lblciudad.setText("Ciudad Cliente:");

        jcbTipoCliente.setToolTipText("a = bueno, b = regular, c = malo");

        jcbCiuadadCliente.setToolTipText("código de las ciudades de los clientes");

        txtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblcedula)
                            .addComponent(lblciudad)
                            .addComponent(lbltipocliente))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbCiuadadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcbTipoCliente, 0, 162, Short.MAX_VALUE)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblnombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblapellido))
                                    .addGap(49, 49, 49))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lbldireccion)
                                    .addGap(42, 42, 42)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbltelefono)
                                .addGap(44, 44, 44)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDireccion))))
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
                    .addComponent(lbltipocliente)
                    .addComponent(jcbTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblciudad)
                    .addComponent(jcbCiuadadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbltelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevo2.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(null);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblClientesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por Cédula"));

        txtBusquedaporCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaporCedulaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(txtBusquedaporCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBusquedaporCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        botonNuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        botonCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtBusquedaporCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaporCedulaKeyReleased
         cargarTablaClientes1(txtBusquedaporCedula.getText().trim());//para buscar por cedula 

    }//GEN-LAST:event_txtBusquedaporCedulaKeyReleased

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void tblClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesKeyPressed
        actualizarTabla();
    }//GEN-LAST:event_tblClientesKeyPressed

    private void txtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusGained
       
    }//GEN-LAST:event_txtTelefonoFocusGained

    private void txtTelefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusLost
        btnGuardar.requestFocus();
    }//GEN-LAST:event_txtTelefonoFocusLost

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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes().setVisible(true);
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
    private javax.swing.JComboBox jcbCiuadadCliente;
    private javax.swing.JComboBox jcbTipoCliente;
    private javax.swing.JLabel lblapellido;
    private javax.swing.JLabel lblcedula;
    private javax.swing.JLabel lblciudad;
    private javax.swing.JLabel lbldireccion;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lbltelefono;
    private javax.swing.JLabel lbltipocliente;
    private javax.swing.JTable tblClientes;
    private ComponentesPropios.txtLetrasMayusculas txtApellido;
    private javax.swing.JTextField txtBusquedaporCedula;
    private ComponentesPropios.txtEntero txtCedula;
    private javax.swing.JTextField txtDireccion;
    private ComponentesPropios.txtLetrasMayusculas txtNombre;
    private ComponentesPropios.txtEntero txtTelefono;
    // End of variables declaration//GEN-END:variables

   
}
