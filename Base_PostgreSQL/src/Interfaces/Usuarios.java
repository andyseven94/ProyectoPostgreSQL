
package Interfaces;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * @author Erika
 */
public class Usuarios extends javax.swing.JFrame {
DefaultTableModel modelo=new DefaultTableModel();
int sw=0;
int fila;
    /**
     * Creates new form Usuarios
     */
    public Usuarios() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
      //  setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      //  setIconifiable(true);
      //  setMaximizable(false);
        setResizable(false);
         txtCodigo.requestFocus();
        botonesIniciales();
        bloquear();
        consultarUsuario("");
        llenarPerfil();
     jtbUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jtbUsuarios.getSelectedRow()!=-1){
                    int fila=jtbUsuarios.getSelectedRow();
                   txtCodigo.setText(jtbUsuarios.getValueAt(fila, 0).toString());
                    txtNombre.setText(jtbUsuarios.getValueAt(fila, 1).toString());
                    txtClave.setText(jtbUsuarios.getValueAt(fila, 2).toString());
                    jcbPerfil.setSelectedItem(jtbUsuarios.getValueAt(fila, 3).toString());
                    txtDescripcion.setText(jtbUsuarios.getValueAt(fila, 4).toString());
                    desbloquear();
                      txtCodigo.setEditable(false);
                    btnActualizar.setEnabled(true);
                    btnBorrar.setEnabled(true);
                    btnCancelar.setEnabled(true);   
                    btnGuardar.setEnabled(false);
                    btnNuevo.setEnabled(true);
                }
            }
        });
      
       
    }
   
   public void llenarPerfil(){
       
         jcbPerfil.addItem("Seleccione...");
          jcbPerfil.addItem("administrador");
           jcbPerfil.addItem("secretaria");
        
    }
    
    
    public void limpiar(){
            txtCodigo.setText("");
            txtNombre.setText("");
            txtClave.setText("");
           jcbPerfil.setSelectedItem("Seleccione...");
            txtDescripcion.setText("");
    }
    public void bloquear(){
            txtCodigo.setEnabled(false);
            txtNombre.setEnabled(false);
            txtClave.setEnabled(false);
            jcbPerfil.setEnabled(false);
            txtDescripcion.setEnabled(false);
    }
     public void desbloquear(){
           txtCodigo.setEditable(true);
           txtCodigo.setEnabled(true);
            txtNombre.setEnabled(true);
            txtClave.setEnabled(true);
             jcbPerfil.setEnabled(true);
            txtDescripcion.setEnabled(true);
            txtCodigo.requestFocus();
    }        
    public void botonesIniciales(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
     public void botonNuevo(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
        limpiar();
        desbloquear();
        
    }
     public void botonCancelar(){
       botonesIniciales();
       limpiar();
       bloquear();
    }
     
     
     public int buscarClavePrimaria(){ 
         sw=0;
        Conexion cc=new Conexion();
        Connection cn=cc.conectar(); 
        String sql="";
        sql="SELECT count(*)  AS contar FROM public.USUARIOS WHERE USUCODIGO='"+txtCodigo.getText()+"';";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql); // devuelve un numero entero de acuerdo al numjero de filas afectadas en la BD
               while (rs.next()){
                   int contar1=rs.getInt("contar");
                   if (contar1>0){
                        JOptionPane.showMessageDialog(null, "EL DATO YA EXISTE");
                        limpiar();
                        sw=1;
                        txtCodigo.requestFocus();
                   }
               }      
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR DE CONSULTA CLAVE PRIMARIA"+ex);
        }
        return sw;
      } 
     
     public void borrar(){
          if(JOptionPane.showConfirmDialog(null, "Estas seguro que deseas borar el dato","Borrar registro",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {      
        Conexion cc= new Conexion();
        Connection cn=cc.conectar();// TODO add your handling code here:
        String sql="";
        sql="DELETE FROM public.USUARIOS WHERE USUCODIGO='"+txtCodigo.getText()+"'";
        try {
            PreparedStatement psd=cn.prepareStatement(sql);
            int n=psd.executeUpdate();
            if (n>0){
                consultarUsuario("");
                limpiar();
                bloquear();
                botonesIniciales();
            }
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null,"ERROR EN LA ELIMINACION"+ ex);
        }
        }
      }
public void consultarUsuario(String Dato){
         String [] titulos= {"Codigo","Nombre","Clave","Perfil","Descripcion"};
         String [] registros=new String[5];
         Conexion cc=new Conexion(); 
         Connection cn=cc.conectar(); 
         String sql=""; 
         sql="SELECT * FROM public.USUARIOS WHERE USUCODIGO LIKE '%"+Dato+"%'ORDER BY USUCODIGO;";
         modelo=new DefaultTableModel(null,titulos);
        jtbUsuarios.setModel(modelo);
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            String desencriptado="";
            while(rs.next())
            {
                registros[0]=rs.getString("USUCODIGO"); 
                registros[1]=rs.getString("USUNOMBRE");
                desencriptado = Encriptacion.Desencriptar(rs.getString("USUCLAVE"));
                registros[2]=desencriptado;
                registros[3]=rs.getString("USUPERFIL");
                registros[4]=rs.getString("USUDESCRIPCION");
                modelo.addRow(registros);
            }
            jtbUsuarios.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error de consulta  "+ex);
        }
     }     


  public void comprobarGuardar(){
          if (!txtCodigo.getText().isEmpty()){
            if(buscarClavePrimaria()!=1){
                guardar();
            }
          }else{
            JOptionPane.showMessageDialog(this, "Debe ingresar la placa");
            txtCodigo.requestFocus();
          }
     }
  public void guardar(){
    if (txtCodigo.getText().isEmpty()){
    JOptionPane.showMessageDialog(this, "Debe ingresar la cedula");
    txtCodigo.requestFocus();
        
    } else if(txtNombre.getText().isEmpty()){ 
         JOptionPane.showMessageDialog(this, "Debe ingresar el Nombre");
        txtNombre.requestFocus();
        }
        else if(txtClave.getText().isEmpty()){ 
            JOptionPane.showMessageDialog(this, "Debe ingresar la clave");
            txtClave.requestFocus();
            }
            else if(jcbPerfil.getSelectedItem().toString().equals("")||jcbPerfil.getSelectedItem().toString().equals("Seleccione...")){ 
                   JOptionPane.showMessageDialog(this, "Debe ingresar el Perfil");
                    jcbPerfil.requestFocus();
                 }
                 else{
                    Conexion cc=new Conexion(); 
                    Connection cn=cc.conectar();            
                    String sql="";
                    String codigo,nombre,clave,perfil,descipcion;
                    codigo=txtCodigo.getText();
                    nombre=txtNombre.getText();
                    clave=Encriptacion.Encriptar(txtClave.getText());
                    perfil=jcbPerfil.getSelectedItem().toString();                    
                    if ("".equals(txtDescripcion.getText()))
                        descipcion="Sin Informacion";
                    else
                    descipcion=txtDescripcion.getText();
                    sql="INSERT INTO public.USUARIOS(USUCODIGO,USUNOMBRE,USUCLAVE,USUPERFIL,USUDESCRIPCION)VALUES(?,?,?,?,?);"; 
                    try {
                    PreparedStatement psd= cn.prepareStatement(sql); 
                    psd.setString(1, codigo);
                    psd.setString(2, nombre);
                    psd.setString(3, clave);
                    psd.setString(4, perfil);
                    psd.setString(5, descipcion);
                    int n=psd.executeUpdate();
                    if (n>0){
                        JOptionPane.showMessageDialog(null,"Se inserto correctamente");
                        limpiar();
                        bloquear();
                        botonesIniciales();
                         consultarUsuario("");
                    }
                    } catch (Exception ex) {
                         JOptionPane.showMessageDialog(null, "No se pudo insertar la informacion "+ex);
                    }
                    }
     }
   public void actualizar(){
     if (txtCodigo.getText().isEmpty()){
    JOptionPane.showMessageDialog(this, "Debe ingresar la cedula");
    txtCodigo.requestFocus();
        
    } else if(txtNombre.getText().isEmpty()){ 
         JOptionPane.showMessageDialog(this, "Debe ingresar el Nombre");
        txtNombre.requestFocus();
        }
        else if(txtClave.getText().isEmpty()){ 
            JOptionPane.showMessageDialog(this, "Debe ingresar la clave");
            txtClave.requestFocus();
            }
            else if(jcbPerfil.getSelectedItem().toString().equals("")||jcbPerfil.getSelectedItem().toString().equals("Seleccione...")){ 
                   JOptionPane.showMessageDialog(this, "Debe ingresar el Perfil");
                    jcbPerfil.requestFocus();
                 }        
                 else{     
                    Conexion cc= new Conexion();
                    Connection cn=cc.conectar();
                  
                    String sql="";
                    sql="UPDATE public.USUARIOS SET USUNOMBRE='"+txtNombre.getText()+"', USUCLAVE='"+Encriptacion.Encriptar(txtClave.getText())+"', USUPERFIL='"+ jcbPerfil.getSelectedItem().toString()+"', USUDESCRIPCION='"+ txtDescripcion.getText()+"' WHERE USUCODIGO='"+txtCodigo.getText()+"'";
                    try {
                    PreparedStatement psd= cn.prepareStatement(sql);
                    int n=psd.executeUpdate(); 
                    if (n>0){
                        JOptionPane.showMessageDialog(null, "ACTUALIZACION EXITOSA");
                        consultarUsuario("");
                        limpiar();
                        bloquear();
                        botonesIniciales();
                        limpiar();
                        bloquear();
                    }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "ERROR DE ACTUALIZACION"+ ex);
                    }
                 }    
      }
     
   public void actualizarTabla(int fila){
     if (jtbUsuarios.getValueAt(fila, 0).toString().isEmpty()){
    JOptionPane.showMessageDialog(this, "Debe ingresar la cedula"); 
    consultarUsuario(""); limpiar(); bloquear();botonesIniciales();
    } else if(jtbUsuarios.getValueAt(fila,1).toString().isEmpty()){ 
         JOptionPane.showMessageDialog(this, "Debe ingresar el Nombre"); 
         consultarUsuario(""); limpiar(); bloquear();botonesIniciales();
        }else if(jtbUsuarios.getValueAt(fila, 2).toString().isEmpty()){ 
            JOptionPane.showMessageDialog(this, "Debe ingresar la clave"); 
            consultarUsuario(""); limpiar(); bloquear();botonesIniciales();
            }else if(jtbUsuarios.getValueAt(fila, 3).toString().isEmpty()){ 
                   JOptionPane.showMessageDialog(this, "Debe ingresar el Perfil");
                  consultarUsuario(""); limpiar(); bloquear();botonesIniciales();
                 }        
                 else{ 
                    if(jtbUsuarios.getValueAt(fila, 4).toString().isEmpty()){
                        jtbUsuarios.setValueAt("Sin Informacion",fila, 4);
                    }
                    Conexion cc= new Conexion();
                    Connection cn=cc.conectar();
                    String sql="";
                    sql="UPDATE public.USUARIOS SET USUNOMBRE='"+jtbUsuarios.getValueAt(fila, 1).toString()+"', USUCLAVE='"+Encriptacion.Encriptar(jtbUsuarios.getValueAt(fila, 2).toString())+"', USUPERFIL='"+ jtbUsuarios.getValueAt(fila, 3).toString()+"', USUDESCRIPCION='"+ jtbUsuarios.getValueAt(fila, 4).toString()+"' WHERE USUCODIGO='"+jtbUsuarios.getValueAt(fila, 0).toString()+"'";
                    try {
                    PreparedStatement psd= cn.prepareStatement(sql);
                    int n=psd.executeUpdate(); 
                    if (n>0){
                        JOptionPane.showMessageDialog(null, "ACTUALIZACION EXITOSA");                
                    }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "ERROR DE ACTUALIZACION"+ ex);
                    }
                     consultarUsuario("");
                        limpiar();
                        bloquear();
                        botonesIniciales();
                 }    
      }
   
    public void enter(java.awt.event.KeyEvent evt){
        fila=jtbUsuarios.getSelectedRow();
        if(evt.getKeyChar() == KeyEvent.VK_ENTER&& fila!=-1){           
             if(clicks==2){
             actualizarTabla(fila); 
             clicks=0;
            }
            
        }   
    }
    int clicks=0;
    public void mouse(java.awt.event.MouseEvent evt){
        if(evt.getClickCount()==2){
           if ( jtbUsuarios.getSelectedColumn()==0) {
                JOptionPane.showMessageDialog(null, "No se puede modificar el codigo");
                String aux=jtbUsuarios.getValueAt(jtbUsuarios.getSelectedRow(), 0).toString();
                jtbUsuarios.editingStopped(null);
                jtbUsuarios.setValueAt(aux, jtbUsuarios.getSelectedRow(), 0);
           }
           if ( jtbUsuarios.getSelectedColumn()==3) {
                JOptionPane.showMessageDialog(null, "No se puede modificar el perfil");
                String aux=jtbUsuarios.getValueAt(jtbUsuarios.getSelectedRow(), 3).toString();
                jtbUsuarios.editingStopped(null);
                jtbUsuarios.setValueAt(aux, jtbUsuarios.getSelectedRow(), 3);
           }
            clicks=2;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcbPerfil = new javax.swing.JComboBox();
        txtDescripcion = new ComponentesPropios.txtLetrasMinusculas();
        txtClave = new ComponentesPropios.txtCuatro();
        txtCodigo = new ComponentesPropios.txtEntero();
        txtNombre = new ComponentesPropios.txtLetrasMayusculas();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbUsuarios = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtBusqueda = new ComponentesPropios.txtEntero();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setTitle("USUARIOS");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Uusarios"));

        jLabel2.setText("Cedula: ");

        jLabel3.setText("Nombre: ");

        jLabel4.setText("Clave:");

        jLabel5.setText("Perfil:");

        jLabel6.setText("Descripcion: ");

        jcbPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPerfilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jcbPerfil, 0, 171, Short.MAX_VALUE)
                    .addComponent(txtClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jtbUsuarios.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtbUsuariosMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbUsuariosMouseClicked(evt);
            }
        });
        jtbUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtbUsuariosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtbUsuarios);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por Cedula"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(null);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIconTextGap(-2);
        btnNuevo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIconTextGap(-3);
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(null);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizar.setIconTextGap(-8);
        btnActualizar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnBorrar.setText("Borrar");
        btnBorrar.setBorder(null);
        btnBorrar.setBorderPainted(false);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBorrar.setIconTextGap(-4);
        btnBorrar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setIconTextGap(-4);
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.setBorder(null);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setIconTextGap(-4);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
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
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.getAccessibleContext().setAccessibleName("Búsqueda por codigo");

        getAccessibleContext().setAccessibleName("Ingreso Usuarios");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtbUsuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbUsuariosKeyReleased
        enter(evt);    
    }//GEN-LAST:event_jtbUsuariosKeyReleased

    private void jtbUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbUsuariosMouseClicked

    }//GEN-LAST:event_jtbUsuariosMouseClicked

    private void jtbUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbUsuariosMousePressed
    mouse(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jtbUsuariosMousePressed

private void jcbPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPerfilActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jcbPerfilActionPerformed

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

private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
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
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbPerfil;
    private javax.swing.JTable jtbUsuarios;
    private ComponentesPropios.txtEntero txtBusqueda;
    private ComponentesPropios.txtCuatro txtClave;
    private ComponentesPropios.txtEntero txtCodigo;
    private ComponentesPropios.txtLetrasMinusculas txtDescripcion;
    private ComponentesPropios.txtLetrasMayusculas txtNombre;
    // End of variables declaration//GEN-END:variables
}
