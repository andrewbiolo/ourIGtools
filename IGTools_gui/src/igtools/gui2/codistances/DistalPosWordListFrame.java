/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package igtools.gui2.codistances;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.INELSA;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
//import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author vbonnici
 */
public class DistalPosWordListFrame extends javax.swing.JFrame {
    
    
    private DistalWordListTableModel tableModel = new DistalWordListTableModel();
    private final DecimalFormat df = new DecimalFormat("###,###,###,###");
    
    private String title = "";

    /**
     * Creates new form DistalWordList
     */
    public DistalPosWordListFrame(String title) {
        initComponents();
        this.setTitle(title);
        this.title = title;
        
        kmersTable.getColumnModel().getColumn(0).setMinWidth(300);
        kmersTable.getColumnModel().getColumn(1).setMaxWidth(75);
        
        kmersTable.setDefaultRenderer(String.class, new KmerLabel());
        
        class intrenderer extends DefaultTableCellRenderer{
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return super.getTableCellRendererComponent(table, df.format(value), hasFocus, hasFocus, row, row);
            }
        }
        
        kmersTable.setDefaultRenderer(Integer.class, new intrenderer());
    }
    
     public DistalPosWordListFrame(INELSA nelsa, IELSAIterator it_1, IELSAIterator it_2, int distance, int count, String ttitle) {
         this(ttitle);
         if(it_1!=null && it_2!=null && distance >0)
            fillTable(nelsa, it_1, it_2, distance, count);
         
         String title = ttitle+" "+distance+" ";
         B3Nucleotide[] kmer = it_1.kmer();
         if(kmer.length > 6){
             title += B3Nucleotide.toString(kmer).substring(0, 6)+"...";
         }
         else{
             title +=  B3Nucleotide.toString(kmer);
         }
         title += "-";
         kmer = it_2.kmer();
         if(kmer.length > 6){
             title += B3Nucleotide.toString(kmer).substring(0, 6)+"...";
         }
         else{
             title +=  B3Nucleotide.toString(kmer);
         }
         setTitle(title);
         this.title = title;
         
         pack();
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
        kmersTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(java.awt.Color.white);
        setMinimumSize(new java.awt.Dimension(400, 200));

        jScrollPane1.setBackground(java.awt.Color.white);

        kmersTable.setFont(new java.awt.Font("Ubuntu Mono", 0, 15)); // NOI18N
        kmersTable.setModel(tableModel);
        jScrollPane1.setViewportView(kmersTable);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("flat view");
        jButton1.setMaximumSize(new java.awt.Dimension(76, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(76, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(76, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FlatViewFrame(title, tableModel.kmers()).setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(DistalPosWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DistalPosWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DistalPosWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DistalPosWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DistalPosWordListFrame("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable kmersTable;
    // End of variables declaration//GEN-END:variables


    private void fillTable(INELSA nelsa, IELSAIterator it_1, IELSAIterator it_2, int distance, int count){
     
        
        //Map<String, Integer> olist = new TreeMap<>();
        //Map<Integer, String> olist = new TreeMap<>();
        int[] wordspos = new int[count];
        int wordspos_i = 0;
        
        if(it_1.compare(it_2) == 0){
            int[] poss = it_1.sortedPositions();
//            Integer m_count;
//            String kmer;
//            B3Nucleotide[] ns = new B3Nucleotide[distance + (it_1.k() >= it_2.k() ? it_1.k() : it_2.k())];

            for(int i=1; i<poss.length; i++){
                if(distance == (poss[i] - poss[i-1])){
                    wordspos[wordspos_i] = poss[i-1];
                    wordspos_i++;
//                    nelsa.b3seq().getB3(poss[i-1], ns);
//                    kmer = B3Nucleotide.toString(ns);
//                    m_count = olist.get(kmer);
//                    if(m_count == null){
//                            olist.put(kmer, 1);
//                    }
//                    else{
//                            olist.put(kmer, m_count + 1);
//                    }
                }
            }
        }
        else{
            int[][] poss = new int[2][];
            poss[0] = it_1.sortedPositions();
            poss[1] = it_2.sortedPositions();

            int[] poss_i = new int[2];

            int a_i = poss[0][0] <= poss[1][0] ? 0 : 1;
            int a_j = poss[1][0] < poss[0][0] ? 0 : 1;

            int dist;
//            Integer m_count;
//            String kmer;
//            B3Nucleotide[] ns = new B3Nucleotide[distance + (it_1.k() >= it_2.k() ? it_1.k() : it_2.k())];

            while(poss_i[0] < poss[0].length && poss_i[1] < poss[1].length){

                while(poss_i[a_i] < poss[a_i].length &&  
                        poss[a_i][poss_i[a_i]] <= poss[a_j][poss_i[a_j]]
                        ){
                    poss_i[a_i]++;
                }

                if((poss[a_j][poss_i[a_j]]) - (poss[a_i][poss_i[a_i] - 1]) == distance){
                        wordspos[wordspos_i] = (poss[a_i][poss_i[a_i] - 1]);
                        wordspos_i++;
//                    nelsa.b3seq().getB3((poss[a_i][poss_i[a_i] - 1]), ns);
//                    kmer = B3Nucleotide.toString(ns);
//                    m_count = olist.get(kmer);
//                    if(m_count == null){
//                            olist.put(kmer, 1);
//                    }
//                    else{
//                            olist.put(kmer, m_count + 1);
//                    }
                }

                a_i = a_i == 0 ? 1 : 0;
                a_j = a_j == 0 ? 1 : 0;

                if(poss_i[0] >= poss[0].length || poss_i[1] >= poss[1].length){
                    break;
                }
            }
        }
        
        Arrays.sort(wordspos);
        String[] kmers = new String[wordspos.length];
        
        B3Nucleotide[] ns = new B3Nucleotide[distance + (it_1.k() >= it_2.k() ? it_1.k() : it_2.k())];
        for(int i=0; i<wordspos.length;i++){
            nelsa.b3seq().getB3(wordspos[i], ns);
            kmers[i] = B3Nucleotide.toString( ns );
        }
        
        
        
        tableModel.setData(kmers, wordspos);
       
    }
    
    
    
    private String coloredKmer(String s){
        String c = "<html>";
        
        char cc;
        for(int i=0; i<s.length(); i++){
            switch(cc = s.charAt(i)){
                case 'A':
                    s += "<span style=\"background-color=BLU\">"+cc+"</span>";
                   break;
                case 'C':
                    s += "<span style=\"background-color=RED\">"+cc+"</span>";
                    break;
                case 'G':
                    s += "<span style=\"background-color=GREEN\">"+cc+"</span>";
                    break;
                case 'T':
                    s += "<span style=\"background-color=YELLOW\">"+cc+"</span>";
                    break;
                default:
                    s += cc;
            }
        }
        
        c += "</html>";
        return c;
    }
}
