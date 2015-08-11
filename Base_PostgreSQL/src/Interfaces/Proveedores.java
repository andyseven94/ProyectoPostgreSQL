/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import fondo.FondoInter;
import java.awt.Color;
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
public class Proveedores extends javax.swing.JInternalFrame {


    DefaultTableModel modelo;
    int fila;
    public Proveedores() {
        initComponents();
//        Proveedores.setBorder(new FondoInter());
        botonesiniciales();
        getContentPane().setBackground(Color.white);
        bloquear();
        cargartabla("");
        cargar_TipoProveedor();
        cargar_ciudad_proveedor();
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
               if(tabla.getSelectedRow()!=-1){
                   int fila =tabla.getSelectedRow();
                   txtci.setText(tabla.getValueAt(fila, 0).toString());
                   jcbcodciudprov.addItem(tabla.getValueAt(fila, 1).toString());
                    jcbcodtippro.addItem(tabla.getValueAt(fila, 2).toString());
                    txtNombre.setText(tabla.getValueAt(fila, 3).toString());
                    txtdir.setText(tabla.getValueAt(fila, 4).toString());
                    txttel.setText(tabla.getValueAt(fila, 5).toString());
                   desbloquear();
                    txtci.setEnabled(false);
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
     public void cargar_TipoProveedor(){
        Conexion cc= new Conexion();
        Connection cn= cc.conectar();
        String sql="";
        sql="select * from public.tipo_proveedor";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
                String cod_tipo=rs.getString("cod_tipo");
                jcbcodtippro.addItem(cod_tipo.trim());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar cb Tipo proveedor "+ex);
        }
        }
        public void cargar_ciudad_proveedor(){
        Conexion cc= new Conexion();
        Connection cn= cc.conectar();
        String sql="";
        sql="select * from public.ciudades_proveedores";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
                String cod_ciu_prov=rs.getString("cod_ciu_prov");
                jcbcodciudprov.addItem(cod_ciu_prov.trim());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar cb ciudad del proveedor "+ex);
        }
        
    }
        
        
        
        
        
        
     public void limpiar(){
        txtci.setText("");
        jcbcodciudprov.setSelectedIndex(0);
        jcbcodtippro.setSelectedIndex(0);
        txtNombre.setText("");
        txtdir.setText("");
        txttel.setText("");
       
    }
    
    public void bloquear(){
        txtci.setEnabled(false);
        jcbcodciudprov.setEnabled(false);
        jcbcodtippro.setEnabled(false);
        txtNombre.setEnabled(false);
        txtdir.setEnabled(false);
        txttel.setEnabled(false);
        
    }
    
    public void desbloquear(){
        txtci.setEnabled(true);
        jcbcodciudprov.setEnabled(true);
        jcbcodtippro.setEnabled(true);
        txtNombre.setEnabled(true);
        txtdir.setEnabled(true);
        txttel.setEnabled(true);
        
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
        desbloquear();
        limpiar();
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
        String sql, ci, codigociudad,tipoprov, nombre,dir,tel;
        int se = jcbcodciudprov.getSelectedIndex(),se1=jcbcodtippro.getSelectedIndex();
        sql="";
        ci=txtci.getText();
        codigociudad=(String) jcbcodciudprov.getItemAt(se);
        tipoprov=(String) jcbcodtippro.getItemAt(se1);
        nombre=txtNombre.getText();
        dir=txtdir.getText();
        tel=txttel.getText();
        
        sql="insert into public.proveedor(id_prov,cod_ciu_prov,cod_tipo,nom_prov,dir_prov,tel_prov) values(?,?,?,?,?,?)";
        try{
            
            PreparedStatement psd= cn.prepareStatement(sql);
            psd.setString(1, ci);
            psd.setString(2, codigociudad);
            psd.setString(3, tipoprov);
            psd.setString(4, nombre);
            psd.setString(5, dir);
            psd.setString(6, tel);
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
        String  ci, codigociudad,tipoprov, nombre,dir,tel;
        int se = jcbcodciudprov.getSelectedIndex(),se1=jcbcodtippro.getSelectedIndex();
        
        ci=txtci.getText();
        codigociudad=(String) jcbcodciudprov.getItemAt(se);
        tipoprov=(String) jcbcodtippro.getItemAt(se1);
        nombre=txtNombre.getText();
        dir=txtdir.getText();
        tel=txttel.getText();
        
        sql="update public.proveedor set cod_ciu_prov='"+codigociudad+"'"+
              ",cod_tipo='"+tipoprov+"'"+
              ",nom_prov='"+txtNombre.getText()+"'"+
              ",dir_prov='"+txtdir.getText()+"'"+
              ",tel_prov='"+txttel.getText()+"'"+
              "WHERE id_prov='"+txtci.getText()+"'";
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
        String[]titulos={"CEDULA","COD. CIUDAD","COD. TIPO","NOMBRE","DIRECCION","TELEFONO"};
        String[]registros= new String[6];
        Conexion conect= new Conexion();
        Connection cn= conect.conectar();
        modelo= new DefaultTableModel(null,titulos);
        String sql=""; 
        sql="SELECT * FROM public.proveedor where id_prov like '%"+dato+"%' order by nom_prov,id_prov";
        try{
            Statement psd= cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            while(rs.next()){
                registros [0]=rs.getString("id_prov");
                registros [1]=rs.getString("cod_ciu_prov");
                registros [2]=rs.getString("cod_tipo");
                registros [3]=rs.getString("nom_prov");
                registros [4]=rs.getString("dir_prov");
                registros [5]=rs.getString("tel_prov");
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
                sql="DELETE FROM public.proveedor where id_prov='"+txtci.getText()+"'";
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
    public void actualizarTabla(int fila){
            String sql1="";
            
              sql1="SELECT * FROM public.proveedor WHERE id_prov LIKE '"+txtci.getText()+"'";
            btnActualizar.setEnabled(true);
            System.out.println(sql1);
            if(tabla.getSelectedRow()!=-1){
            if(tabla.getValueAt(fila,0).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar el codigo");
                tabla.editCellAt(fila, 0);
//                txtAutoMarca.requestFocus();
            }
            else if(tabla.getValueAt(fila,1).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar la ciudad");
                tabla.editCellAt(fila, 1);
//                txtAutoColor.requestFocus();
            }
            else if(tabla.getValueAt(fila,2).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar el tipo");
                tabla.editCellAt(fila, 2);
//                txtAutoColor.requestFocus();
            }
            else if(tabla.getValueAt(fila,3).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar el nombre");
                tabla.editCellAt(fila, 3);
//                txtAutoColor.requestFocus();
            }
            else if(tabla.getValueAt(fila,4).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar la direccion");
                tabla.editCellAt(fila, 4);
//                txtAutoColor.requestFocus();
            }
            else if(tabla.getValueAt(fila,5).toString().isEmpty()){
                JOptionPane.showMessageDialog(null,"Debe ingresar el telefono");
                tabla.editCellAt(fila, 5);
//                txtAutoColor.requestFocus();
            }
            else{
                Conexion cc=new Conexion();
                Connection cn=cc.conectar();
                String sql="";
                sql="UPDATE public.proveedor SET cod_ciu_prov='"+tabla.getValueAt(fila, 1).toString()+"', cod_tipo='"+tabla.getValueAt(fila, 2).toString()+"', nom_prov='"+ tabla.getValueAt(fila, 3).toString()+"', dir_prov='"+ tabla.getValueAt(fila, 4).toString()+"', tel_prov='"+tabla.getValueAt(fila, 5).toString()+"' WHERE id_prov='"+tabla.getValueAt(fila, 0).toString()+"'";
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
        String sql="select count(*) as contar from public.proveedor where id_prov ='"+txtci.getText()+"'";;
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
                    txtci.setText("");
                    txtci.requestFocus();
              
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtci = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jcbcodciudprov = new javax.swing.JComboBox();
        jcbcodtippro = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtdir = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proveedores");

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Proveedores"));

        txtci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtciActionPerformed(evt);
            }
        });
        txtci.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtciFocusLost(evt);
            }
        });

        jLabel1.setText("C.I. PROVEEDOR");

        jLabel2.setText("COD. CIUDAD PROVEEDOR");

        jLabel3.setText("COD. TIPO PROVEEDOR");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel8.setText("Búsqueda por Cédula");

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

        jLabel5.setText("NOMBRE PROVEEDOR");

        jLabel6.setText("DIRECCION PROVEEDOR");

        jLabel7.setText("TELEFONO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtci)
                                    .addComponent(jcbcodciudprov, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtdir, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbcodtippro, javax.swing.GroupLayout.Alignment.LEADING, 0, 132, Short.MAX_VALUE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txttel))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbcodciudprov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcbcodtippro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargartabla(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        // TODO add your handling code here:
        botonesiniciales();
        bloquear();
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtciFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtciFocusLost
        // TODO add your handling code here:
        buscar_clave_primaria();
    }//GEN-LAST:event_txtciFocusLost

    private void txtciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtciActionPerformed
        // TODO add your handling code here:
        jcbcodciudprov.requestFocus();
    }//GEN-LAST:event_txtciActionPerformed

    private void tablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaKeyReleased
        // TODO add your handling code here:
        fila=tabla.getSelectedRow();
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
            actualizarTabla(fila);
        }
    }//GEN-LAST:event_tablaKeyReleased

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        botonnuevo();
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
        botoncancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

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
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proveedores().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbcodciudprov;
    private javax.swing.JComboBox jcbcodtippro;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtci;
    private javax.swing.JTextField txtdir;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
}
