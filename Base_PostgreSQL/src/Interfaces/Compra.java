
package Interfaces;


import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class Compra extends javax.swing.JInternalFrame {

    DefaultTableModel modelo;
    DefaultTableModel mode;
    int clicks=0;
    static int finalizado=0;
    static int ventaIniciada=0;
    
    public Compra() {
        initComponents();
        txtCodigo.requestFocus();
        getContentPane().setBackground(Color.white);
        Bloquear();
        llenadoEmpleado();
        llenadoProveedor();
        llenadoProducto();
         String [] titulos={"Producto","Cantidad","Precio","Subtotal"};
        mode=new  DefaultTableModel(null, titulos);
        jtbDetalle.setModel(mode);
        txtCodigo.requestFocus();
         //botonesIniciales();
        jtbDetalle.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jtbDetalle.getSelectedRow()!=-1){
                    int fila=jtbDetalle.getSelectedRow();
                    jcbProducto.setSelectedItem(jtbDetalle.getValueAt(fila, 0).toString());
                    txtCantidad.setText(jtbDetalle.getValueAt(fila, 1).toString());  
                    if(finalizado==0){
                    jbtModificar.setEnabled(true);
                    jbtEliminar.setEnabled(true);
                    }
                }
            }
        });
    }
    
    public void Bloquear(){
         txtCodigo.setEditable(true);
        txtNombreProveedor.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtTotal.setEditable(false);
        txtFecha.setEditable(false);
        txtCantidad.setEditable(false);
    }
    public void limpiar(){
        txtCodigo.setText("");
        txtFecha.setText("");
        jcbEmpleado.setSelectedItem("Seleccione...");
        txtNombreEmpleado.setText("");
        txtApellidoEmpleado.setText("");
        jcbProveedor.setSelectedItem("Seleccione...");
        txtNombreProveedor.setText("");
        String [] titulos={"Producto","Cantidad","Precio","Subtotal"};
        mode=new  DefaultTableModel(null, titulos);
        jtbDetalle.setModel(mode);
        txtCantidad.setText("");
        jcbProducto.setSelectedItem("Seleccione...");     
        txtTotal.setText("");
        jbtConsultar.setEnabled(true);
    }
    
    public void botonesIniciales(){
         jbtContinuar.setEnabled(false);
         jbtIngresar.setEnabled(false);
         jbtModificar.setEnabled(false);
         jbtEliminar.setEnabled(false);
         jcbProveedor.setEnabled(false);
         jcbEmpleado.setEnabled(false);
         jcbProducto.setEnabled(false);
         jbtFinalizar.setEnabled(false);
     }
    
     public void nuevo(){
         finalizado=0;
         limpiar();
         txtCodigo.setEditable(false);
         txtCodigo.setText(String.valueOf(numeroCompra()));
         Date fe= new Date();
         String fecha=new SimpleDateFormat("dd-MM-yyyy").format(fe);
         txtFecha.setText(fecha);
         txtTotal.setText("0");
         jcbProveedor.setEnabled(true);
         jcbEmpleado.setEnabled(true);
         jbtContinuar.setEnabled(true);  
         jbtConsultar.setEnabled(false);
         jbtAnular.setEnabled(false);
     }

    public Compra(GraphicsConfiguration gc) {
//        super(gc);
    }
     public void cancelar(){
         limpiar();
         botonesIniciales();
         txtCodigo.requestFocus();
         txtCodigo.setEditable(true);
         jbtConsultar.setEnabled(true);
         jbtAnular.setEnabled(true);
     }
    
    public void consultarDetalle(){
        String [] titulos={"Producto","Cantidad","Precio","Subtotal"};
        String [] registros= new String[4];
        float total=0,subtotal=0;
        String cantidad, precio="";
        Conexion cc= new Conexion();
        Connection cn=cc.conectar();
        modelo= new DefaultTableModel(null, titulos);
        jtbDetalle.setModel(modelo);
        String sql="",aux="",sql2="";
        sql="SELECT * FROM PUBLIC.DETALLE_COMPRA WHERE NUM_COM="+txtCodigo.getText()+";";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            while(rs.next()){
                cantidad=rs.getString("CANT_COM");
                aux=rs.getString("COD_PRO");
                registros[0]=aux;
                registros[1]=cantidad;
                sql2="SELECT PRE_PRO AS PRECIO FROM PUBLIC.PRODUCTOS WHERE COD_PRO='"+aux+"';";
                Statement psd2=cn.createStatement();
                ResultSet rs2= psd2.executeQuery(sql2);
                while(rs2.next()){
                    precio=rs2.getString("PRECIO");
                    registros[2]=precio;
                }
                subtotal=Float.valueOf(precio)*Integer.valueOf(cantidad);
                registros[3]=String.valueOf(subtotal);
                total=total+subtotal;
                modelo.addRow(registros);
            txtTotal.setText(String.valueOf(total));
            }
            jtbDetalle.setModel(modelo);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "CONSULTA ERRONEA "+e);
        }
    }
    
    public void consultar(){
       finalizado=0;
      if(!txtCodigo.getText().isEmpty()){
        String cod=txtCodigo.getText();
        limpiar();
        txtCodigo.setText(cod);
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String total="",estado="";
        String sql="SELECT * FROM public.venta where num_ven="+txtCodigo.getText()+";";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
                estado=rs.getString("est_ven");
                jcbProveedor.setSelectedItem(rs.getString("id_cli"));
                jcbEmpleado.setSelectedItem(rs.getString("ci_emp"));
                txtFecha.setText(rs.getString("fec_ven"));
                total=rs.getString("total_ven");
                txtTotal.setText(total);   
            }
            if(total.equals("0.00")&&estado.equals("A")){
                finalizado=0;
                jbtIngresar.setEnabled(true);
                jcbProducto.setEnabled(true);  
                jbtFinalizar.setEnabled(true);
                txtCantidad.setEditable(true);
            }  else finalizado=1;
            if(estado.equals("A"))consultarDetalle();
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error de Consulta de venta "+ex);
        } 
      }else{ JOptionPane.showMessageDialog(null, "Ingrese el numero de venta");
      txtCodigo.requestFocus();
      }
      }
    
    public void llenadoEmpleado(){
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="SELECT * FROM PUBLIC.EMPLEADO";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            jcbEmpleado.addItem(rs.getString("CI_EMP"));
        }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error al Cargar Empleados"+ex);
        } 
    }
    
    public void llenadoProveedor(){
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="SELECT * FROM PUBLIC.PROVEEDOR";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            jcbProveedor.addItem(rs.getString("ID_PROV"));
        }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error al Cargar Proveedores"+ex);
        } 
    }
    
    public void llenadoProducto(){
        Conexion cc=new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        sql="SELECT * FROM PUBLIC.PRODUCTOS";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            jcbProducto.addItem(rs.getString("COD_PRO"));
        }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error al Cargar Productos"+ex);
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
            sql="SELECT STOCK FROM PUBLIC.PRODUCTOS WHERE COD_PRO='"+jcbProducto.getSelectedItem().toString()+"';";
        try {
            Statement psd=cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while(rs.next()){
            cantidad=Integer.valueOf(rs.getString("STOCK"));
        }
            if(cantidad > 200){
                 JOptionPane.showMessageDialog(null, "No se Requiere comprar más");
                 txtCantidad.setText("");
                 txtCantidad.requestFocus();
            }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error en Productos"+ex);
        }  
            }
    }
          
    public int numeroCompra(){
        Conexion cc= new Conexion();
        Connection cn=cc.conectar();
        String sql="";
        int val=0;
        sql="SELECT MAX(NUM_COM) AS NUMERO FROM PUBLIC.COMPRA";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("NUMERO")==(null)) val=1;
                else val=Integer.valueOf(rs.getString("NUMERO"))+1;
            }
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Error en Numero de Compra"+ex);
        }  
        return val;
    }
    
    public void llenadodatosproveedores(){
        if(!jcbProveedor.getSelectedItem().toString().equals("Seleccione...")){
            Conexion cc=new Conexion();
            Connection cn=cc.conectar();
            String sql="";
            sql="SELECT NOM_PROV AS NOMBRE FROM PUBLIC.PROVEEDOR WHERE ID_PROV='"+jcbProveedor.getSelectedItem().toString()+"'";
            try {
                Statement psd=cn.createStatement();
                ResultSet rs=psd.executeQuery(sql);
                while(rs.next()){
                    txtNombreProveedor.setText(rs.getString("NOMBRE"));
                }
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de consulta en Proveedor "+e);
        }
        }
    }
    
    public void llenadodatosempleados(){
        if(!jcbEmpleado.getSelectedItem().toString().equals("Seleccione...")){
            Conexion cc=new Conexion();
            Connection cn=cc.conectar();
            String sql="";
            sql="SELECT NOM_EMP AS NOMBRE, APE_EMP AS APELLIDO FROM PUBLIC.EMPLEADO WHERE CI_EMP='"+jcbEmpleado.getSelectedItem().toString()+"'";
            try {
                Statement psd=cn.createStatement();
                ResultSet rs=psd.executeQuery(sql);
                while(rs.next()){
                    txtNombreEmpleado.setText(rs.getString("NOMBRE"));
                    txtApellidoEmpleado.setText(rs.getString("APELLIDO"));
                }    
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de consulta de empleados "+e);
        } 
        }else{
            txtNombreEmpleado.setText("");
            txtApellidoEmpleado.setText("");
        }   
    }
    
    public void anular(){
         if(txtCodigo.getText().isEmpty()){
             txtCodigo.requestFocus();
             JOptionPane.showMessageDialog(null, "Ingrese el codigo");
         }else {
             Conexion cc= new Conexion();
             Connection cn=cc.conectar();
             String sql="",sql2="";
             sql2="UPDATE PUBLIC.COMPRA SET TOTAL_COM=0 WHERE NUM_COM="+txtCodigo.getText()+";";
            try {
                    PreparedStatement psd2=cn.prepareStatement(sql2);
                    int m=psd2.executeUpdate();
                    if(m>0){
                     for (int i=0;i<jtbDetalle.getRowCount();i++){
                         jcbProducto.setSelectedItem(jtbDetalle.getValueAt(i, 0).toString());
                         txtCantidad.setText(jtbDetalle.getValueAt(i, 1).toString());
                         eliminar();
                     }
                    JOptionPane.showMessageDialog(null, "Anulado exitoso");
                    }
     
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de consulta de Anulado "+e);
         }    
         }   
     }
    
    public void guardarCompra(){
         if(jcbEmpleado.getSelectedItem().toString().equals("Seleccione...")){
              JOptionPane.showMessageDialog(null, "Seleccione un Empleado");
              jcbEmpleado.requestFocus();
         }else if (jcbProveedor.getSelectedItem().toString().equals("Seleccione...")){ 
                  JOptionPane.showMessageDialog(null, "Seleccione un Proveedor");
                  jcbProveedor.requestFocus();
         }else{
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="", empleado,cliente,total;
         sql="INSERT INTO PUBLIC.COMPRA VALUES (?, ?, ?, ?, ?);";
         empleado=jcbEmpleado.getSelectedItem().toString();
         cliente=jcbProveedor.getSelectedItem().toString();
         total="0";
         Calendar calendar = Calendar.getInstance();
         java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
        try {
            PreparedStatement psd= cn.prepareStatement(sql);
            psd.setInt(1,Integer.valueOf(txtCodigo.getText())) ;
            psd.setString(2, empleado);
            psd.setString(3, cliente);
            psd.setDate(4, date);
            psd.setInt(5, 0);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Ingreso exitoso");
                txtCantidad.setEditable(true);
                jbtIngresar.setEnabled(true);
                jbtModificar.setEnabled(false);
                jbtEliminar.setEnabled(false);
                jbtContinuar.setEnabled(false);
                jbtFinalizar.setEnabled(true);
                jcbProveedor.setEnabled(false);
                jcbEmpleado.setEnabled(false);
                jcbProducto.setEnabled(true);
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de Ingreso en Compra "+e);
        }  
         }
     }
    
    public void limpiardetalle(){
             jcbProducto.setSelectedItem("Seleccione...");
             txtCantidad.setText("");
         }
     
         public int  cambiarStock(String codigo, int cantidad, String operacion){
            int p=0;
            Conexion cc=new Conexion();
            Connection cn=cc.conectar();
            String sql="",sql2="",sql3="";
            int stock=0;
            sql="SELECT STOCK FROM PUBLIC.PRODUCTOS WHERE COD_PRO='"+codigo+"';";
            try {
                Statement psd=cn.createStatement();
                ResultSet rs= psd.executeQuery(sql);
                while(rs.next()){
                    stock=Integer.valueOf(rs.getString("STOCK"));
                }
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(null, "ERROR!! Consulta de stock"+ex);
            }
             if(operacion.equals("Sumar")){
                sql2="UPDATE PUBLIC.PRODUCTOS SET STOCK=STOCK+"+String.valueOf(cantidad)+"WHERE COD_PRO='"+codigo+"';";    
                try {
                PreparedStatement psd2=cn.prepareStatement(sql2);
                int n=psd2.executeUpdate();
                if(n>0){
                   p=1;
                }
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERROR!! No se puede realizar el cambio de stock "+ex);
                }
             }else{
                 sql3="UPDATE PUBLIC.PRODUCTOS SET STOCK=STOCK-"+String.valueOf(cantidad)+"WHERE COD_PRO='"+codigo+"';";    
                try {
                PreparedStatement psd3=cn.prepareStatement(sql3);
                int m=psd3.executeUpdate();
                if(m>0){
                   p=1;
                }
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERROR!! No se puede realizar el cambio de stock "+ex);
                }
             }
             return p;
         }
    
    public void guardarDetalle(){
         if(!jcbProducto.getSelectedItem().toString().equals("Seleccione...")&& !txtCantidad.getText().isEmpty()){
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         int a=0;
         String SQL="SELECT * FROM PUBLIC.DETALLE_COMPRA WHERE COD_PRO='"+jcbProducto.getSelectedItem().toString()+"' AND NUM_COM="+txtCodigo.getText()+";";
         try {
                Statement psd=cn.createStatement();
                ResultSet rs=psd.executeQuery(SQL);
                while(rs.next()){
                    a++;
                }
         } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, "Error de Producto "+ex);
         }
         if(a==0){              
         String sql="",sql2="", codigo,compra,cantidad,precio="";
         float subtotal;
         sql="INSERT INTO PUBLIC.DETALLE_COMPRA(COD_PRO, NUM_COM, CANT_COM, SUBTOTAL) VALUES (?, ?, ?, ?);";
         codigo=jcbProducto.getSelectedItem().toString();
         compra=txtCodigo.getText();
         cantidad=txtCantidad.getText();
         sql2="SELECT PRE_PRO AS PRECIO FROM PUBLIC.PRODUCTOS WHERE COD_PRO='"+codigo+"';";
         try {
             Statement   psd2 = cn.createStatement();
             ResultSet rs2= psd2.executeQuery(sql2);
         while(rs2.next()){
         precio=rs2.getString("PRECIO");
         }
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Consultar el Precio "+ex);
         }
         subtotal=Integer.valueOf(cantidad)*Float.valueOf(precio);
         try {
            PreparedStatement psd= cn.prepareStatement(sql);
            psd.setString(1, codigo);
            psd.setInt(2, Integer.valueOf(compra));
            psd.setInt(3, Integer.valueOf(cantidad));
            psd.setFloat(4, subtotal);
            int n=psd.executeUpdate();
            if(n>0){
              int p = cambiarStock(codigo, Integer.valueOf(cantidad), "Sumar");
               consultarDetalle();
                limpiardetalle();
                jbtModificar.setEnabled(false);
                jbtEliminar.setEnabled(false);
                if(p==1){
                JOptionPane.showMessageDialog(null, "Ingreso exitoso");
                }
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error de Ingreso de Venta "+e);
        }  
         } else  JOptionPane.showMessageDialog(null, "ERROR! Ya existe el producto en la compra");
         } else  JOptionPane.showMessageDialog(null, "ERROR! Debe Selecionar un producto y una cantidad");
     }
    
    public void eliminar(){
         if(jcbProducto.getSelectedItem().toString().equals("Seleccione...")){
             jcbProducto.requestFocus();
             JOptionPane.showMessageDialog(null, "Seleccione un Producto");
         }else if(txtCantidad.getText().isEmpty()){
             txtCantidad.requestFocus();
             JOptionPane.showMessageDialog(null, "Ingrese una cantidad");
         }else{    
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="",sql2="",codigo,cantidad;
         codigo=jcbProducto.getSelectedItem().toString();
         cantidad=txtCantidad.getText();
         sql="DELETE FROM PUBLIC.DETALLE_COMPRA WHERE COD_PRO='"+jcbProducto.getSelectedItem().toString()+"' AND NUM_COM="+txtCodigo.getText()+"";
        try {
            PreparedStatement psd=cn.prepareStatement(sql);
            int n= psd.executeUpdate();
            if (n>0){
                int p=cambiarStock(codigo,Integer.valueOf(cantidad), "Restar");
                if (p==1) JOptionPane.showMessageDialog(null, "Eliminado exitoso");
                consultarDetalle();
                limpiardetalle();
                jbtModificar.setEnabled(false);
                jbtEliminar.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error de eliminado"+ex);
        }
        }
     }
    
    public void finalizar(){
         float total= Float.valueOf(txtTotal.getText());
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="";
         sql="UPDATE PUBLIC.COMPRA SET TOTAL_COM="+total+"WHERE NUM_COM="+txtCodigo.getText()+";";
        try {
            PreparedStatement psd= cn.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Finalizado Exitoso");
                limpiar();
                limpiardetalle();
                botonesIniciales();
                txtCodigo.setEditable(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error de finalizado "+ex);
        } 
     }
    
    static int cant=0;
     static int nuevaCant;
     public void comprobar(){
         if(jcbProducto.getSelectedItem().toString().equals("Seleccione..")){
         JOptionPane.showMessageDialog(null, "Seleccione un producto");
         jcbProducto.requestFocus();
         }else if(txtCantidad.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Ingrese una cantidad");
         txtCantidad.requestFocus();
         }else{
         String pro=jcbProducto.getSelectedItem().toString();
          cant=0;
         nuevaCant= Integer.valueOf(txtCantidad.getText());
            for (int i=0; i< jtbDetalle.getRowCount();i++){
                if(jtbDetalle.getValueAt(i, 0).toString().equals(pro)){
                    cant=Integer.valueOf(jtbDetalle.getValueAt(i, 1).toString());
                }
            }
         }
     }
         
      public void enter(java.awt.event.KeyEvent evt){
        int fila=jtbDetalle.getSelectedRow();
        int canti=jtbDetalle.getSelectedColumn();
        if(evt.getKeyChar() == KeyEvent.VK_ENTER&& fila!=-1){
            if(clicks==2){
             jcbProducto.setSelectedItem(jtbDetalle.getValueAt(fila, 0));
             txtCantidad.setText(jtbDetalle.getValueAt(fila, 1).toString());
            modificar();
             clicks=0;
            }
        }   
    }
     
    public void mouse( java.awt.event.MouseEvent evt){
          if ( jtbDetalle.getSelectedColumn()!=1) {
                JOptionPane.showMessageDialog(null, "Solo se puede modificar la cantidad");
                String producto=jtbDetalle.getValueAt(jtbDetalle.getSelectedRow(), 0).toString();
                String cantidad=jtbDetalle.getValueAt(jtbDetalle.getSelectedRow(), 1).toString();
                String precio=jtbDetalle.getValueAt(jtbDetalle.getSelectedRow(), 2).toString();
                String subtotal=jtbDetalle.getValueAt(jtbDetalle.getSelectedRow(), 3).toString();
                jtbDetalle.editingStopped(null);
                jtbDetalle.setValueAt(producto, jtbDetalle.getSelectedRow(), 0);
                 jtbDetalle.setValueAt(cantidad, jtbDetalle.getSelectedRow(), 1);
                  jtbDetalle.setValueAt(precio, jtbDetalle.getSelectedRow(), 2);
                   jtbDetalle.setValueAt(subtotal, jtbDetalle.getSelectedRow(), 3);
           }
      }
    
    public void modificar(){
         cant=0;
         nuevaCant=0;
         comprobar();         
         Conexion cc=new Conexion();
         Connection cn=cc.conectar();
         String sql="",sql2="",precio="";
         sql2="SELECT PRE_PRO AS PRECIO FROM PUBLIC.PRODUCTOS WHERE COD_PRO='"+jcbProducto.getSelectedItem().toString()+"';";
         try {
             Statement   psd2 = cn.createStatement();
             ResultSet rs2= psd2.executeQuery(sql2);
         while(rs2.next()){
         precio=rs2.getString("PRECIO");
         }
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error de Consulta de precio "+ex);
         }
         sql="UPDATE PUBLIC.DETALLE_COMPRA SET CANT_COM="+txtCantidad.getText()+",SUBTOTAL="+String.valueOf(Float.valueOf(precio)*nuevaCant)+" WHERE NUM_COM="+txtCodigo.getText()+" AND COD_PRO='"+jcbProducto.getSelectedItem().toString()+"';";
         try {
            PreparedStatement psd= cn.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0){
              int z=0;
                if(cant<nuevaCant){
                  z=  cambiarStock(jcbProducto.getSelectedItem().toString(), (nuevaCant+cant), "Sumar");
                }
                if(cant>nuevaCant){
                  z=  cambiarStock(jcbProducto.getSelectedItem().toString(), (cant+nuevaCant), "Restar");
                }
                if(z==1)   JOptionPane.showMessageDialog(null, "Modificacion Exitosa");
                consultar();
                limpiardetalle();
                jbtModificar.setEnabled(false);
                jbtEliminar.setEnabled(true);
                txtCodigo.setEditable(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error de finalizado "+ex);
        } 
     }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jcbProveedor = new javax.swing.JComboBox();
        jcbEmpleado = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtNombreEmpleado = new javax.swing.JTextField();
        txtApellidoEmpleado = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jbtContinuar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbtAnular = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jbtNuevo = new javax.swing.JButton();
        jbtConsultar = new javax.swing.JButton();
        jbtSalir = new javax.swing.JButton();
        jbtFinalizar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jcbProducto = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtCantidad = new ComponentesPropios.txtEntero();
        jbtIngresar = new javax.swing.JButton();
        jbtModificar = new javax.swing.JButton();
        jbtEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDetalle = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Compra");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Compra"));

        jLabel3.setText("Num. Pedido:");

        jLabel4.setText("Empleado: ");

        jLabel5.setText("Proveedor:");

        jLabel6.setText("Fecha:");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        jcbProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        jcbProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbProveedorItemStateChanged(evt);
            }
        });

        jcbEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        jcbEmpleado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbEmpleadoItemStateChanged(evt);
            }
        });
        jcbEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEmpleadoActionPerformed(evt);
            }
        });

        jLabel7.setText("Nombre:");

        jLabel8.setText("Apellido:");

        jLabel10.setText("Nombre:");

        jbtContinuar.setText("Continuar");
        jbtContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtContinuarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(jbtContinuar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(199, 199, 199))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(txtCodigo))
                            .addComponent(jcbEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(45, 45, 45)
                        .addComponent(txtNombreEmpleado))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreProveedor))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFecha)
                    .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jbtContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones Compra"));

        jbtAnular.setText("Anular");
        jbtAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAnularActionPerformed(evt);
            }
        });

        jbtCancelar.setText("Cancelar");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        jbtNuevo.setText("Nuevo");
        jbtNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtNuevoActionPerformed(evt);
            }
        });

        jbtConsultar.setText("Consultar");
        jbtConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConsultarActionPerformed(evt);
            }
        });

        jbtSalir.setText("Salir");
        jbtSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSalirActionPerformed(evt);
            }
        });

        jbtFinalizar.setText("Finalizar");
        jbtFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtFinalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbtFinalizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtConsultar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtAnular, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtSalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtAnular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtFinalizar)
                .addGap(23, 23, 23))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Compra"));

        jLabel12.setText("Producto:");

        jcbProducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));

        jLabel15.setText("Cantidad:");

        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });

        jbtIngresar.setText("Ingresar");
        jbtIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtIngresarActionPerformed(evt);
            }
        });

        jbtModificar.setText("Modificar");
        jbtModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtModificarActionPerformed(evt);
            }
        });

        jbtEliminar.setText("Eliminar");
        jbtEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addGap(38, 38, 38)
                        .addComponent(jcbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jbtIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(85, 85, 85)
                        .addComponent(jbtModificar)
                        .addGap(69, 69, 69)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jbtEliminar)))
                .addGap(23, 23, 23))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtIngresar)
                    .addComponent(jbtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jtbDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtbDetalleMousePressed(evt);
            }
        });
        jtbDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtbDetalleKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtbDetalle);

        jLabel1.setText("Total:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        txtFecha.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jcbProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbProveedorItemStateChanged
        llenadodatosproveedores();// TODO add your handling code here:
    }//GEN-LAST:event_jcbProveedorItemStateChanged

    private void jcbEmpleadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbEmpleadoItemStateChanged
        llenadodatosempleados();// TODO add your handling code here:
    }//GEN-LAST:event_jcbEmpleadoItemStateChanged

    private void jcbEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEmpleadoActionPerformed
        jcbProveedor.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jcbEmpleadoActionPerformed

    private void jbtContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtContinuarActionPerformed
        guardarCompra(); // TODO add your handling code here:
    }//GEN-LAST:event_jbtContinuarActionPerformed

    private void jbtAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAnularActionPerformed
        anular();// TODO add your handling code here:
    }//GEN-LAST:event_jbtAnularActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtNuevoActionPerformed
        nuevo();// TODO add your handling code here:
    }//GEN-LAST:event_jbtNuevoActionPerformed

    private void jbtConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConsultarActionPerformed
        consultar();// TODO add your handling code here:
    }//GEN-LAST:event_jbtConsultarActionPerformed

    private void jbtSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSalirActionPerformed
        dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jbtSalirActionPerformed

    private void jbtFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtFinalizarActionPerformed
        finalizar();// TODO add your handling code here:
    }//GEN-LAST:event_jbtFinalizarActionPerformed

    private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
        controlCantidadAntes();// TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadFocusGained

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
        controlCantidad();// TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadFocusLost

    private void jbtIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIngresarActionPerformed
        guardarDetalle();// TODO add your handling code here:
    }//GEN-LAST:event_jbtIngresarActionPerformed

    private void jbtModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtModificarActionPerformed
        modificar();// TODO add your handling code here:
    }//GEN-LAST:event_jbtModificarActionPerformed

    private void jbtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEliminarActionPerformed
        eliminar();// TODO add your handling code here:
    }//GEN-LAST:event_jbtEliminarActionPerformed

    private void jtbDetalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbDetalleMousePressed
        if(evt.getClickCount()==2){
            mouse(evt);
            clicks=2;
        }
    }//GEN-LAST:event_jtbDetalleMousePressed

    private void jtbDetalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbDetalleKeyReleased
        enter(evt);// TODO add your handling code here:
    }//GEN-LAST:event_jtbDetalleKeyReleased

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
            java.util.logging.Logger.getLogger(Compra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compra().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtAnular;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConsultar;
    private javax.swing.JButton jbtContinuar;
    private javax.swing.JButton jbtEliminar;
    private javax.swing.JButton jbtFinalizar;
    private javax.swing.JButton jbtIngresar;
    private javax.swing.JButton jbtModificar;
    private javax.swing.JButton jbtNuevo;
    private javax.swing.JButton jbtSalir;
    private javax.swing.JComboBox jcbEmpleado;
    private javax.swing.JComboBox jcbProducto;
    private javax.swing.JComboBox jcbProveedor;
    private javax.swing.JTable jtbDetalle;
    private javax.swing.JTextField txtApellidoEmpleado;
    private ComponentesPropios.txtEntero txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
