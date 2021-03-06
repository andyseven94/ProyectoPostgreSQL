/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReporteInterno.java
 *
 * Created on 30-jun-2015, 19:13:08
 */
package Reportes;

/**
 
 * @author Erika
 */
import Interfaces.Conexion;
import java.awt.BorderLayout; 
import java.awt.Container; 
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.util.HashMap; 
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
public class ReporteInterno extends javax.swing.JInternalFrame {

    /** Creates new form ReporteInterno */
    public ReporteInterno() {
        super("Reporteador Interno",true,true,true,true); 
        initComponents(); 
        setBounds(10,10,600,500); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
    }
    public ReporteInterno(String fileName){ 
        this(fileName,null); 
    }
       public ReporteInterno(String fileName, Map parameter) { 
        this(); 
        try{ 
            Conexion cc=new Conexion();
            Connection cn= cc.conectar();
            JasperReport reporte=JasperCompileManager.compileReport(fileName);
            JasperPrint print=JasperFillManager.fillReport(reporte, parameter,cn); 
            JRViewer viewer =new JRViewer(print); 
            Container c=getContentPane(); 
            c.setLayout(new BorderLayout()); 
            c.add(viewer);  
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null,"No se puede mostrar el Reporte");
        } 
        
    }
       public ReporteInterno(JasperPrint print){ 
          JRViewer viewer =new JRViewer(print); 
            Container c=getContentPane(); 
            c.setLayout(new BorderLayout()); 
            c.add(viewer);  
       }   
     
   
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ReporteInterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteInterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteInterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteInterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ReporteInterno().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
