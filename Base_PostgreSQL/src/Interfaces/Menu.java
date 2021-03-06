
package Interfaces;

import Reportes.NumeroVenta;
import Reportes.ReporteInterno;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


public class Menu extends javax.swing.JFrame {

 
    public Menu() {
        initComponents();
        this.setLocationRelativeTo(null);//para frame en el CENTRO de la pantalla
        jDesktopPane1.setBorder(new ImagenFondo());
        this.setExtendedState(Menu.MAXIMIZED_BOTH);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiClientes = new javax.swing.JMenuItem();
        jmiProductos = new javax.swing.JMenuItem();
        jmiVenta = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Principal");

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnArchivom.png"))); // NOI18N
        jMenu1.setText("Archivo");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnClientes.png"))); // NOI18N
        jMenuItem1.setText("Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnProductosm.png"))); // NOI18N
        jMenuItem4.setText("Productos");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnTipoProducto.png"))); // NOI18N
        jMenuItem6.setText("Tipo Producto");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEmpleados.png"))); // NOI18N
        jMenuItem7.setText("Empleados");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnNuevosUsuarios.png"))); // NOI18N
        jMenuItem3.setText("Nuevos Usuarios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCiudadesProve.png"))); // NOI18N
        jMenuItem5.setText("Ciudad Proveedor");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnSalirm.png"))); // NOI18N
        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCompraVenta.png"))); // NOI18N
        jMenu3.setText("Compra y Venta");

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCompraVenta.png"))); // NOI18N
        jMenuItem8.setText("Compra");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnVenta.png"))); // NOI18N
        jMenuItem9.setText("Venta");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnVentaDetalle.png"))); // NOI18N
        jMenuItem10.setText("Venta Detalle");
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnReportem.png"))); // NOI18N
        jMenu2.setText("Reportes");

        jmiClientes.setText("Clientes");
        jmiClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClientesActionPerformed(evt);
            }
        });
        jMenu2.add(jmiClientes);

        jmiProductos.setText("Productos");
        jmiProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductosActionPerformed(evt);
            }
        });
        jMenu2.add(jmiProductos);

        jmiVenta.setText("Venta");
        jmiVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiVentaActionPerformed(evt);
            }
        });
        jMenu2.add(jmiVenta);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Clientes cli = new Clientes();
        jDesktopPane1.add(cli);
        cli.setVisible(true);
        cli.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Usuarios usu=new Usuarios();
        jDesktopPane1.add(usu);
        usu.setVisible(true);
        usu.show();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Empleados emp=new Empleados();
        jDesktopPane1.add(emp);
        emp.setVisible(true);
        emp.show();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        tipo_producto_1 tipPro=new tipo_producto_1();
        jDesktopPane1.add(tipPro);
        tipPro.setVisible(true);
        tipPro.show();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Ciudad_proveedor ciuprov=new Ciudad_proveedor();
        jDesktopPane1.add(ciuprov);
        ciuprov.setVisible(true);
        ciuprov.show();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
//        Compra comp=new Compra();
//        jDesktopPane1.add(comp);
//        comp.setVisible(true);
//        comp.show();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Productos prod=new Productos();
        jDesktopPane1.add(prod);
        prod.setVisible(true);
        prod.show();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jmiProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiProductosActionPerformed
       try {
            Conexion cc=new Conexion();
            Connection cn= cc.conectar();
           JasperReport reporte=JasperCompileManager.compileReport("C:/Reportes/Productos.jrxml");
            JasperPrint print= JasperFillManager.fillReport(reporte, null,cn);
            ReporteInterno rp=new ReporteInterno("C:/Reportes/Productos.jrxml");
            jDesktopPane1.add(rp);
            rp.setVisible(true);
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null,"No se puede mostrar el Reporte");
        }  
    }//GEN-LAST:event_jmiProductosActionPerformed

    private void jmiClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClientesActionPerformed
        try {
            Conexion cc=new Conexion();
            Connection cn= cc.conectar();
           JasperReport reporte=JasperCompileManager.compileReport("C:/Reportes/Clientes.jrxml");
            JasperPrint print= JasperFillManager.fillReport(reporte, null,cn);
            ReporteInterno rp=new ReporteInterno("C:/Reportes/Clientes.jrxml");
            jDesktopPane1.add(rp);
            rp.setVisible(true);
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null,"No se puede mostrar el Reporte");
        }  
    }//GEN-LAST:event_jmiClientesActionPerformed

    private void jmiVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiVentaActionPerformed
         NumeroVenta nv=new  NumeroVenta();
 nv.setVisible(true);
 jDesktopPane1.add(nv);
    }//GEN-LAST:event_jmiVentaActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    public javax.swing.JMenuItem jMenuItem5;
    public javax.swing.JMenuItem jMenuItem6;
    public javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jmiClientes;
    private javax.swing.JMenuItem jmiProductos;
    private javax.swing.JMenuItem jmiVenta;
    // End of variables declaration//GEN-END:variables
}
