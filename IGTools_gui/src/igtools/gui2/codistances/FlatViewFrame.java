/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package igtools.gui2.codistances;

import igtools.common.alignment.StringSmithWaterman;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import opal.OpalMSAAPI;

/**
 *
 * @author vbonnici
 */
public class FlatViewFrame extends javax.swing.JFrame {

    
    private String[] kmers = null;
    private FlatViewPanel fvPanel = null;
    
    /**
     * Creates new form FlatViewFrame
     */
    public FlatViewFrame(String title, String[] kmers) {
        this.setTitle(title);
        this.kmers = kmers;
        this.fvPanel = new FlatViewPanel(kmers);
        fvPanel.prefSize();
        this.add(fvPanel, BorderLayout.CENTER);
        initComponents();
        
        setScales();
        fvPanel.prefSize();
        
        
        this.fvPanel.setFocusable(true);
        this.fvPanel.addMouseWheelListener(
                new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                    if(e.getUnitsToScroll()<0){
                        prev((int)(fvPanel.rowCount() *0.25));
                    }
                    else{
                        next((int)(fvPanel.rowCount() *0.25));
                    }
                }
             }
        });
    }
    
    
    private void setScales(){
        try{
            int nW = Integer.parseInt(this.nwField.getText());
            int nH = Integer.parseInt(this.nhField.getText());
            fvPanel .setData(nW, nH);
            
        }catch(Exception e){
        }
    }
    
    private void prev(int step){
        this.setEnabled(false);
        fvPanel.up(step);
        this.setEnabled(true);
        
    }
    
    private void next(int step){
        this.setEnabled(false);
        fvPanel.down(step);
        this.setEnabled(true);
        
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
        jLabel1 = new javax.swing.JLabel();
        nwField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nhField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        remNsField = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        jLabel1.setText("w");

        nwField.setText("4");

        jLabel2.setText("h");

        nhField.setText("1");

        jButton1.setText("refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nwField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nhField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nwField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(nhField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jButton3.setText("MSA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        remNsField.setText("Remove Gaps");
        remNsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remNsFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remNsField)
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(remNsField))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setEnabled(false);
        setScales();
        fvPanel.prefSize();
        fvPanel.repaint();
        this.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void remNsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remNsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_remNsFieldActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       this.setEnabled(false);
       this.msa();
       this.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nhField;
    private javax.swing.JTextField nwField;
    private javax.swing.JCheckBox remNsField;
    // End of variables declaration//GEN-END:variables

    private void msa(){
        final String[] aligned = OpalMSAAPI.align(kmers, new String[]{});

        boolean remNs = this.remNsField.isSelected();
        
        if(remNs){
            double[] scores = new double[aligned[0].length()];
            int gapsCount = 0;
            int totCount = aligned[0].length();

            for(int i=0; i<aligned.length; i++){
                for(int j=0; j<aligned[i].length(); j++){
                    if(aligned[i].charAt(j) == '-'){
                        scores[j]++;
                    }
                }
            }

            for(int i=0; i<scores.length;i++){
                if(scores[i] / (double)aligned.length > 0.60){
                    scores[i] = 1;
                    gapsCount++;
                }
                else{
                    scores[i] = 0;
                }
            }

            if(gapsCount > 0){
                for(int i=0; i<aligned.length; i++){
                    char[] newS = new char[totCount - gapsCount];
                    for(int j=0, k=0; j<totCount; j++){
                        if(scores[j] == 0){
                            newS[k] = aligned[i].charAt(j);
                            k++;
                        }
                    }
                    aligned[i] = new String(newS);
                }
            }
        }
        
        this.fvPanel.setKmers(aligned);
        this.fvPanel.repaint();
    }
}
