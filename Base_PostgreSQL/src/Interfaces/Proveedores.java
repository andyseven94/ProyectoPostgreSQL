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
public class Proveedores extends javax.swing.JFrame {


    DefaultTableModel modelo;
    int fila;
    public Proveedores() {
        initComponents();
//        Proveedores.setBorder(new FondoInter());
        botonesiniciales();
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

        Proveedores = new javax.swing.JDesktopPane();
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
        txtci = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jcbcodciudprov = new javax.swing.JComboBox();
        jcbcodtippro = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtdir = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jScrollPane1.setBounds(20, 420, 422, 100);
        Proveedores.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
                .addContainerGap(84, Short.MAX_VALUE)
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
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel2.setBounds(470, 20, 189, 438);
        Proveedores.add(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        jLabel8.setText("Busqueda por cedula");

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

        jLabel4.setText(" PROVEEDORES");

        jLabel5.setText("NOMBRE PROVEEDOR");

        jLabel6.setText("DIRECCION PROVEEDOR");

        jLabel7.setText("TELEFONO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(jLabel4)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtci)
                                    .addComponent(jcbcodciudprov, 0, 113, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(100, 100, 100)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbcodtippro, 0, 104, Short.MAX_VALUE)
                                    .addComponent(txtNombre)
                                    .addComponent(txtdir)
                                    .addComponent(txttel))))
                        .addGap(67, 67, 67))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 102, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );

        jPanel1.setBounds(10, 20, 422, 380);
        Proveedores.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Proveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Proveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
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

    private void tablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaKeyReleased
        // TODO add your handling code here:
        fila=tabla.getSelectedRow();
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
            actualizarTabla(fila);
        }
    }//GEN-LAST:event_tablaKeyReleased

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
    private javax.swing.JDesktopPane Proveedores;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
