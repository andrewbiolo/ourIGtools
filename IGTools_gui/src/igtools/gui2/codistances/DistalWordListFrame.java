/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package igtools.gui2.codistances;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.INELSA;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
//import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author vbonnici
 */
public class DistalWordListFrame extends javax.swing.JFrame {
    
    
    private final DistalWordListTableModel tableModel = new DistalWordListTableModel();
    private final DecimalFormat df = new DecimalFormat("###,###,###,###");
    
    private String title = "";

    /**
     * Creates new form DistalWordList
     */
    public DistalWordListFrame(String title) {
        initComponents();
        this.setTitle(title);
        this.title = title;
        
        kmersTable.getColumnModel().getColumn(0).setMinWidth(300);
        kmersTable.getColumnModel().getColumn(1).setMaxWidth(75);
        
        kmersTable.setDefaultRenderer(String.class, new KmerLabel());
        
        
        class intrenderer extends DefaultTableCellRenderer{
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, df.format(value), hasFocus, hasFocus, row, row);
               // int cv = 255 - ((Integer)value < 256 ? (int)value : 255);
                if((Integer)value > 1)
                    c.setBackground(Color.ORANGE);
                    //c.setBackground(new Color(cv,cv,cv));
                return c;
            }
        }
        
        kmersTable.setDefaultRenderer(Integer.class, new intrenderer());
    }
    
     public DistalWordListFrame(INELSA nelsa, IELSAIterator it_1, IELSAIterator it_2, int distance, String ttitle) {
         this(ttitle);
         if(it_1!=null && it_2!=null && distance >0)
            fillTable(nelsa, it_1, it_2, distance);
         
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
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 550));

        kmersTable.setFont(new java.awt.Font("Ubuntu Mono", 0, 15)); // NOI18N
        kmersTable.setModel(tableModel);
        jScrollPane1.setViewportView(kmersTable);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("flat view");
        jButton1.setMaximumSize(new java.awt.Dimension(74, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(74, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(74, 20));
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
            java.util.logging.Logger.getLogger(DistalWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DistalWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DistalWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DistalWordListFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DistalWordListFrame("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable kmersTable;
    // End of variables declaration//GEN-END:variables


    private void fillTable(INELSA nelsa, IELSAIterator it_1, IELSAIterator it_2, int distance){
     
        
        Map<String, Integer> olist = new TreeMap<>();
        int tcount = 0;
        
        if(it_1.compare(it_2) == 0){
            int[] poss = it_1.sortedPositions();
            Integer m_count;
            String kmer;
            B3Nucleotide[] ns = new B3Nucleotide[distance + (it_1.k() >= it_2.k() ? it_1.k() : it_2.k())];

            for(int i=1; i<poss.length; i++){
                if(distance == (poss[i] - poss[i-1])){
                    //kmer = B3Nucleotide.toString(it_1.kmer());
                    nelsa.b3seq().getB3(poss[i-1], ns);
                    kmer = B3Nucleotide.toString(ns);
                    m_count = olist.get(kmer);
                    if(m_count == null){
                            olist.put(kmer, 1);
                    }
                    else{
                            olist.put(kmer, m_count + 1);
                    }
                    tcount++;
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
            Integer m_count;
            String kmer;
            B3Nucleotide[] ns = new B3Nucleotide[distance + (it_1.k() >= it_2.k() ? it_1.k() : it_2.k())];

            while(poss_i[0] < poss[0].length && poss_i[1] < poss[1].length){

                while(poss_i[a_i] < poss[a_i].length &&  
                        poss[a_i][poss_i[a_i]] <= poss[a_j][poss_i[a_j]]
                        ){
                    poss_i[a_i]++;
                }

                if((poss[a_j][poss_i[a_j]]) - (poss[a_i][poss_i[a_i] - 1]) == distance){
                    nelsa.b3seq().getB3((poss[a_i][poss_i[a_i] - 1]), ns);
                    kmer = B3Nucleotide.toString(ns);
                    m_count = olist.get(kmer);
                    if(m_count == null){
                            olist.put(kmer, 1);
                    }
                    else{
                            olist.put(kmer, m_count + 1);
                    }
                    tcount++;
                }

                a_i = a_i == 0 ? 1 : 0;
                a_j = a_j == 0 ? 1 : 0;

                if(poss_i[0] >= poss[0].length || poss_i[1] >= poss[1].length){
                    break;
                }
            }
        }
        
//        String[] kmers = new String[olist.size()];
//        int[] counts = new int[olist.size()];
//        
//        int i=0;
//        for(Map.Entry<String, Integer> entry : olist.entrySet()){
//            kmers[i] = entry.getKey();
//            //kmers[i] = coloredKmer(entry.getKey());
//            counts[i] = entry.getValue();
//            i++;
//        }
        
        String[] kmers = new String[tcount];
        int[] counts = new int[tcount];
        
        int i=0; int j; 
        int vv; String s;
        for(Map.Entry<String, Integer> entry : olist.entrySet()){
            vv = entry.getValue();
            s = kmers[i] = entry.getKey();
            
            for(j=0; j<vv;j++){
                kmers[i] = s;
                counts[i] = vv;
                i++;
            }
        }
        
        tableModel.setData(kmers, counts);
       
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
