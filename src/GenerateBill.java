
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ajay
 */
public class GenerateBill extends javax.swing.JFrame {

    /**
     * Creates new form GenerateBill
     */
    DefaultTableModel model;
    public GenerateBill() {
        initComponents();
        this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        FindBill();
        StoreBill();
    }
    public static int c=1;
    public static double cost = 0;
    public void FindBill(){
        CustName.setText(Sales.Cust_Name);
        CustID.setText(Integer.toString(Sales.cid));
        try{
            String Driver = "com.mysql.jdbc.Driver";
            Class.forName(Driver);
            String sql = null;
            int nr;
            PreparedStatement stmt = null;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MSAS","root","");
            sql = "select *from Sales where CID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Sales.cid);
            ResultSet rs = stmt.executeQuery();
            ResultSet rs1 = null;
            int delete = 0;
            while(rs.next()){
                //required
                String med = rs.getString("Name");
                int Rqty = rs.getInt("Qnty");
                System.out.println("Req" + med + " " + Rqty);
                //Total
                sql = "select *from Medicine where Name = ?";                
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, med);
                rs1 = stmt.executeQuery();
                if(rs1.next()){
                    System.out.println(rs1.getString("Name"));
                    double price = rs1.getDouble("Price");
                    int Tqnty = rs1.getInt("Qnty");
                    int id = rs1.getInt("MID");
                     System.out.println(price + " " + Tqnty + " " + id);
                    price = price *Rqty;
                    sql = "update Medicine set Qnty = ? where  MID = ?";
                    stmt = conn.prepareStatement(sql);
                    Tqnty = Tqnty-Rqty;
                    if(Tqnty == 0)
                        delete = 1;
                    stmt.setInt(1,Tqnty);
                    stmt.setInt(2, id);
                   nr = stmt.executeUpdate();
                   if(nr != 0){
                       System.out.println("Count Decremented");
                   }
                   if(delete == 1){
                       sql = "Delete from Medicine where Qnty = 0";
                       stmt = conn.prepareStatement(sql);
                       int n = stmt.executeUpdate();
                   }
                    Display(med,Rqty,price);
                }
            }
            Cost.setText(Double.toString(cost));
            
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    public void StoreBill(){
        try{
            String Driver = "com.mysql.jdbc.Driver";
            Class.forName(Driver);
            String sql = null;
            int nr;
            PreparedStatement stmt = null;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MSAS","root","");
            sql = "insert into Bill(CID,Total_Price,Date) values (?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,Integer.parseInt(CustID.getText()));
            stmt.setDouble(2,Double.parseDouble(Cost.getText()));
            
            Date dt = new Date();
            String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(dt);
            java.sql.Date date = java.sql.Date.valueOf(modifiedDate);
            stmt.setDate(3, date);
            nr = stmt.executeUpdate();
            if(nr != 0)
                System.out.println("Bill Genarated");
            else
                System.out.println("Something is Wrong to Create Bill");
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    public void Display(String med,int Rqty,double price){
        model=(DefaultTableModel)jTable1.getModel();
        Object obj[] = {c,med,Rqty,price};
        cost = cost + price;
        model.addRow(obj);
        
        
        
        
        
        
        
        
        
        c++;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        CustID = new javax.swing.JTextField();
        Cost = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CustName = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 77, 224));
        jLabel1.setText("Medical Shop Automation Software");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(350, 100, 0, 0));

        jLabel2.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(52, 77, 224));
        jLabel2.setText("Medical Shop Automation Software");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(18, 174, 167), 3, true));

        jLabel3.setFont(new java.awt.Font("Serif", 0, 15)); // NOI18N
        jLabel3.setText("Customer Name:");

        jLabel4.setFont(new java.awt.Font("Serif", 0, 15)); // NOI18N
        jLabel4.setText("Customer Id      : ");

        jLabel6.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel6.setText("       Generate Bill");

        jTable1.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Name", "No.items", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(25);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        CustID.setEditable(false);
        CustID.setFont(new java.awt.Font("Serif", 0, 15)); // NOI18N

        Cost.setEditable(false);
        Cost.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Serif", 0, 15)); // NOI18N
        jLabel5.setText("Total Price    :");

        CustName.setEditable(false);
        CustName.setFont(new java.awt.Font("Serif", 0, 15)); // NOI18N
        CustName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Cost, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CustID, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CustName))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CustName)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CustID)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Cost)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CustNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CustNameActionPerformed

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
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerateBill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Cost;
    private javax.swing.JTextField CustID;
    private javax.swing.JTextField CustName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
