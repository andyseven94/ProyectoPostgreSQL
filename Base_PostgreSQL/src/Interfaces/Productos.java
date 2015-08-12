
package Interfaces;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andy
 */
public class Productos extends javax.swing.JInternalFrame {

    //DECLARACION DE VARIABLES LOCALES
    DefaultTableModel model;
    //
    public Productos() {
        initComponents();
    //    this.setLocationRelativeTo(null);//para frame en el CENTRO de la pantalla
        this.getContentPane().setBackground(Color.WHITE);//cambio color de fram a blanco
        cargar_TipoPro();
        cargarTablaProductos1("");
        botonesIniciales();
        txtCodigo.setDocument(new SoloMayusculas());
        txtNombre.setDocument(new SoloMayusculas());
        txtMarca.setDocument(new SoloMayusculas());
        
        tblProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
               if(tblProductos.getSelectedRow()!=-1){
                    int fila=tblProductos.getSelectedRow();
                    txtCodigo.setText(tblProductos.getValueAt(fila, 0).toString());
                    jcbTipoPro.setSelectedItem(tblProductos.getValueAt(fila, 1).toString());
                    txtNombre.setText(tblProductos.getValueAt(fila, 2).toString());
                    txtMarca.setText(tblProductos.getValueAt(fila, 3).toString());
                    txtPrecio.setText(tblProductos.getValueAt(fila, 4).toString());
                    txtStock.setText(tblProductos.getValueAt(fila, 5).toString());
                }
                desbloquear();
                txtCodigo.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnBorrar.setEnabled(true);
            }
        });
    }

    public void cargar_TipoPro(){
        Conexion cc= new Conexion();
        Connection cn= cc.conectar();
        String sql="";
        sql="select * from tipo_producto";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
                String cod_tipo_pro=rs.getString("cod_tipo_pro");
                jcbTipoPro.addItem(cod_tipo_pro.trim());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar cb Tipo producto "+ex);
        }
        
    }
      public void pintarLBLnegro(){//pinta a negro cuando ya se a introducido valores en los txt
         
          lblcod.setForeground(Color.BLACK);
          lblNOmbre.setForeground(Color.BLACK);
          lblmarca.setForeground(Color.BLACK);
          lblprecio.setForeground(Color.BLACK);
          lblprecio.setForeground(Color.BLACK);
     }
    public void limpiar(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtMarca.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }
    
    public void bloquear(){
        txtCodigo.setEnabled(false);
        jcbTipoPro.setEnabled(false);
        txtNombre.setEnabled(false);
        txtMarca.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtStock.setEnabled(false);
    }
    
    public void desbloquear(){
        txtCodigo.setEnabled(true);
        jcbTipoPro.setEnabled(true);
        txtNombre.setEnabled(true);
        txtMarca.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtStock.setEnabled(true);
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
        desbloquear();
        limpiar();
    }
     
     public void botonCancelar(){
        botonesIniciales();
        limpiar();
        bloquear();
    }
     
     public void guardar(){
           if(txtCodigo.getText().isEmpty()){
            lblcod.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Código del producto");
            txtCodigo.requestFocus();
        }else if (txtNombre.getText().isEmpty()){
            lblNOmbre.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Nombre del Producto");
            txtNombre.requestFocus();
        }else if(txtMarca.getText().isEmpty()){
            lblmarca.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Marca del Producto");
            txtMarca.requestFocus();
        }else if(txtPrecio.getText().isEmpty()){
            lblprecio.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el precio del producto");
            txtPrecio.requestFocus();
        }else if(txtStock.getText().isEmpty()){
            lblstock.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la cantidad de productos disponibles");
            txtStock.requestFocus();
        }else{
            
            pintarLBLnegro();
        String codigo_pro,cod_tipo_pro,nombre_pro,marca_pro,precio_pro,stock_pro;
        Double precio; Integer stock;
        codigo_pro=txtCodigo.getText();
        cod_tipo_pro=jcbTipoPro.getSelectedItem().toString().substring(0,5).trim();// (0,1) devuelve indice -1 
        nombre_pro=txtNombre.getText();
        marca_pro=txtNombre.getText();
        precio_pro=txtPrecio.getText();
        precio=Double.parseDouble(precio_pro);
        stock_pro=txtStock.getText();
        stock=Integer.parseInt(stock_pro);
                
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="insert into productos (cod_pro,cod_tipo_pro,nom_pro,mar_pro,pre_pro,stock) values(?,?,?,?,?,?)";
        try {
            PreparedStatement psd=cn.prepareStatement(sql);//aqui van las creadas arriba en string
            
            psd.setString(1,codigo_pro);
            psd.setString(2,cod_tipo_pro);
            psd.setString(3,nombre_pro);
            psd.setString(4, marca_pro);
            psd.setDouble(5, precio);
            psd.setInt(6, stock);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                limpiar();
                bloquear();
                botonesIniciales();
                cargarTablaProductos1("");
            }
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        }
    }
    
     
     public void cargarTablaProductos1(String dato){
        String []titulos={"CÓDIGO","TIPO PROD.","NOMBRE","MARCA","PRECIO","STOCK"};
        String [] registros=new String [6];
        Conexion cc= new Conexion();
        Connection cn = cc.conectar();
        model = new DefaultTableModel(null,titulos);
        String sql="";
        dato=txtBusquedaporCod.getText();
        sql="select * from productos where cod_pro like'%"+dato+"%' order by nom_pro";
        try {
            Statement psd = cn.createStatement();//createStatement se usa para mostrar msa de 1 registro
            ResultSet rs=psd.executeQuery(sql); //devuelve los valores en forma de registros
            while(rs.next()){
                registros[0]=rs.getString("cod_pro");
                registros[1]=rs.getString("cod_tipo_pro");
                registros[2]=rs.getString("nom_pro");
                registros[3]=rs.getString("mar_pro");
                registros[4]=rs.getString("pre_pro");
                registros[5]=rs.getString("stock");
                model.addRow(registros);
            }
            tblProductos.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en la tblProductos"+ex);
        }
    }
     
     public void actualizar(){
             if(txtCodigo.getText().isEmpty()){
            lblcod.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Código del producto");
            txtCodigo.requestFocus();
        }else if (txtNombre.getText().isEmpty()){
            lblNOmbre.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el Nombre del Producto");
            txtNombre.requestFocus();
        }else if(txtMarca.getText().isEmpty()){
            lblmarca.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la Marca del Producto");
            txtMarca.requestFocus();
        }else if(txtPrecio.getText().isEmpty()){
            lblprecio.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar el precio del producto");
            txtPrecio.requestFocus();
        }else if(txtStock.getText().isEmpty()){
            lblstock.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Debes Ingresar la cantidad de productos disponibles");
            txtStock.requestFocus();
        }else{
              pintarLBLnegro();
            
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="";
         sql="update productos set cod_tipo_pro='"+jcbTipoPro.getSelectedItem().toString().substring(0,5).trim()+"', nom_pro='"+txtNombre.getText()+"', mar_pro='"+txtMarca.getText()+"', pre_pro='"+txtPrecio.getText()+"', stock='"+txtStock.getText()+"' where cod_pro='"+txtCodigo.getText()+"'";
               try {
                   PreparedStatement psd= cn.prepareStatement(sql);
                   int n=psd.executeUpdate();
                   if(n>0){
                       JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
                       limpiar();
                       cargarTablaProductos1("");
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
             sql="delete from productos where cod_pro='"+txtCodigo.getText()+"'";
             try {
                 PreparedStatement psd = cn.prepareStatement(sql);
                 int n = psd.executeUpdate();
                 if(n>0){
                     
                     cargarTablaProductos1("");
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
            int fila=tblProductos.getSelectedRow();
            int columna=tblProductos.getSelectedColumn();
            tblProductos.setEditingRow(0);
        sql="update productos set cod_tipo_pro='"+tblProductos.getValueAt(fila, 1).toString()+"',nom_pro='"+tblProductos.getValueAt(fila, 2).toString()+"',mar_pro='"+tblProductos.getValueAt(fila, 3).toString()+"', pre_pro='"+tblProductos.getValueAt(fila, 4).toString()+"', stock='"+tblProductos.getValueAt(fila, 5).toString()+"'where cod_pro='"+tblProductos.getValueAt(fila, 0).toString()+"'";
        try {
            PreparedStatement psd= cn.prepareStatement(sql);
            int n= psd.executeUpdate();
            if (n>0){
                JOptionPane.showMessageDialog(null, "Se Actualizó La Base");
                cargarTablaProductos1("");
                limpiar();
                bloquear();
            }
            cargarTablaProductos1("");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblcod = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNOmbre = new javax.swing.JLabel();
        lblmarca = new javax.swing.JLabel();
        lblprecio = new javax.swing.JLabel();
        lblstock = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jcbTipoPro = new javax.swing.JComboBox();
        txtNombre = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtPrecio = new ComponentesPropios.txtDosDeciales();
        txtStock = new ComponentesPropios.txtEntero();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtBusquedaporCod = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ingreso de Productos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Productos"));

        lblcod.setText("Código:");

        jLabel2.setText("Tipo Producto:");

        lblNOmbre.setText("Nombre:");

        lblmarca.setText("Marca:");

        lblprecio.setText("Precio:");

        lblstock.setText("Stock:");

        txtNombre.setToolTipText("Ingresar Nombre del Producto");

        txtPrecio.setToolTipText("Ingrese el precio en dólares");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblcod)
                    .addComponent(jLabel2)
                    .addComponent(lblNOmbre)
                    .addComponent(lblmarca)
                    .addComponent(lblprecio)
                    .addComponent(lblstock))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCodigo)
                        .addComponent(jcbTipoPro, 0, 147, Short.MAX_VALUE)
                        .addComponent(txtNombre)
                        .addComponent(txtMarca)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcod)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbTipoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNOmbre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblmarca)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblprecio)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblstock)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProductosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevoProd2.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(null);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIconTextGap(-2);
        btnNuevo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevoProd3.png"))); // NOI18N
        btnNuevo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesDinamicos/btnNuevoProd.png"))); // NOI18N
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda del Producto por Código"));

        txtBusquedaporCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaporCodKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(txtBusquedaporCod, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtBusquedaporCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        botonNuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       botonCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tblProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProductosKeyPressed
        actualizarTabla();
    }//GEN-LAST:event_tblProductosKeyPressed

    private void txtBusquedaporCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaporCodKeyReleased
      cargarTablaProductos1(txtBusquedaporCod.getText().trim());//para buscar por codigo
    }//GEN-LAST:event_txtBusquedaporCodKeyReleased

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
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Productos().setVisible(true);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbTipoPro;
    private javax.swing.JLabel lblNOmbre;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblmarca;
    private javax.swing.JLabel lblprecio;
    private javax.swing.JLabel lblstock;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBusquedaporCod;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtNombre;
    private ComponentesPropios.txtDosDeciales txtPrecio;
    private ComponentesPropios.txtEntero txtStock;
    // End of variables declaration//GEN-END:variables
}
