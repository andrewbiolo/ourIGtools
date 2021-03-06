/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package igtools.gui2.dictionaries;

import igtools.analyses.Trends;
import igtools.common.sequence.B3LLSequence;
import igtools.common.sequence.B3Sequence;
import igtools.common.util.Timer;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.INELSA;
import igtools.dictionaries.elsa.NELSA;
import igtools.gui2.WSSequence;
import igtools.gui2.codistances.*;
import igtools.gui2.mults.CoMultFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author vbonnici
 */
public class GeneralTrendsFrame extends javax.swing.JFrame {
    
    
    
    private DecimalFormat df = new DecimalFormat("###,###,###,###");
    
    private WSSequence wsseq;
    private NELSA nelsa;
    private int pickedK = 0;
    
    private int nofPanels = 4;

    /**
     * Creates new form ProperCoDistancesFrame
     */
    public GeneralTrendsFrame(WSSequence wsseq) {
        this.wsseq = wsseq;
        this.nelsa = wsseq.getNELSA();
        
        initComponents();
        this.setTitle("General trends: "+wsseq.getName());
       
        //center_panel.setLayout(new BorderLayout());
        //center_panel.setLayout(new SpringLayout());
        center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));
        
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
        fromKField = new javax.swing.JTextField();
        toKField = new javax.swing.JTextField();
        drawButton = new javax.swing.JButton();
        center_panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 350));

        fromKField.setColumns(4);
        fromKField.setText("6");

        toKField.setColumns(4);
        toKField.setText("20");

        drawButton.setText("draw");
        drawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fromKField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toKField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawButton)
                .addContainerGap(404, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fromKField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toKField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drawButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        center_panel.setBackground(new java.awt.Color(254, 254, 254));
        center_panel.setPreferredSize(new java.awt.Dimension(600, 900));

        javax.swing.GroupLayout center_panelLayout = new javax.swing.GroupLayout(center_panel);
        center_panel.setLayout(center_panelLayout);
        center_panelLayout.setHorizontalGroup(
            center_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        center_panelLayout.setVerticalGroup(
            center_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 736, Short.MAX_VALUE)
        );

        getContentPane().add(center_panel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void drawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawButtonActionPerformed
        makeChart();
    }//GEN-LAST:event_drawButtonActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel center_panel;
    private javax.swing.JButton drawButton;
    private javax.swing.JTextField fromKField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField toKField;
    // End of variables declaration//GEN-END:variables

    
    
    
    
    private void makeChart(){
        if(this.nelsa != null){
            center_panel.removeAll();
            center_panel.setBackground(Color.RED);
            center_panel.invalidate();
            center_panel.repaint();
            
           try{
        
                final int from_k = Integer.parseInt(fromKField.getText());
                final int to_k = Integer.parseInt(toKField.getText());

                
                TreeMap<Integer,Integer> dsizes = new TreeMap<Integer,Integer>();
                TreeMap<Integer,Integer> msizes = new TreeMap<Integer,Integer>();
                
                TreeMap<Integer,Integer> nof_hapaxes = new TreeMap<Integer,Integer>();
                TreeMap<Integer,Integer> nof_repeats = new TreeMap<Integer,Integer>();
                
                TreeMap<Integer,Double> avg_rep = new TreeMap<Integer,Double>();
                TreeMap<Integer,Double> sd_rep = new TreeMap<Integer,Double>();
                
                
                
                for(int i=from_k; i<= to_k; i++){
                    Trends.TrendInfoRet ret = new Trends.TrendInfoRet();
                    Trends.trendInfo(nelsa, i, ret);
                    
                    dsizes.put(i, ret.d_size);
                    msizes.put(i, ret.m_size);
                    nof_hapaxes.put(i, ret.nof_hapaxes);
                    nof_repeats.put(i, ret.nof_repeates);
                    avg_rep.put(i, ret.avg_repeatenes);
                    sd_rep.put(i, ret.sd_repeatenes);
                }
        
                final ChartPanel dsizePanel = createDSizeChart(dsizes);
                final ChartPanel reppPanel = createRepeatnessChart(avg_rep, sd_rep);
                final ChartPanel reppRatioPanel = createRepeatnessRatioChart(msizes, avg_rep, sd_rep);
                final ChartPanel hapRepPanel = createHapRepChart(dsizes, nof_hapaxes, nof_repeats);
                
                //center_panel.add(dsizePanel, BorderLayout.NORTH);
                //center_panel.add(reppPanel, BorderLayout.CENTER);
                //center_panel.add(hapRepPanel, BorderLayout.SOUTH);
                
                center_panel.add(dsizePanel);
                center_panel.add(reppPanel);
                center_panel.add(reppRatioPanel);
                center_panel.add(hapRepPanel);
                
                center_panel.revalidate();
                center_panel.repaint();
        
           }catch(Exception e){
               
           }
        }
    }
    
    
    
    
    private ChartPanel createDSizeChart(TreeMap<Integer,Integer> dsizes){
        
        final XYSeries series = new XYSeries("");
        for(Map.Entry<Integer, Integer> entry : dsizes.entrySet()){
            System.out.println(entry.getKey() +"\t"+ entry.getValue());
            series.add(entry.getKey(), entry.getValue());
        }

//        System.out.println("["+it.istart()+","+it.iend()+"]");
        final XYSeriesCollection dataset = new XYSeriesCollection(series);
      
        final JFreeChart chart = ChartFactory.createXYBarChart(
                                        null, 
                                        "k", 
                                        false, 
                                        "Dictionary size", 
                                        dataset, 
                                        PlotOrientation.VERTICAL, 
                                        false, 
                                        true, 
                                        false);

        
        final ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(center_panel.getPreferredSize());
        chartPanel.setPreferredSize(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height/nofPanels));
        chart.setBackgroundPaint(Color.white);
        //chartPanel.setMouseZoomable(false);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setMinimumDrawWidth( 0 );
        chartPanel.setMinimumDrawHeight( 0 );
        chartPanel.setMaximumDrawWidth( 1920 );
        chartPanel.setMaximumDrawHeight( 1200 );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        ((XYBarRenderer) plot.getRenderer()).setBarPainter(new StandardXYBarPainter());
        plot.getRenderer().setSeriesPaint( 0, Color.BLUE);

        ((XYBarRenderer) plot.getRenderer()).setMargin(0.1);


        plot.setDomainPannable(true);
        plot.setRangePannable(true);

        plot.setDomainGridlinesVisible(true);  
        plot.setRangeGridlinesVisible(true);  
        plot.setRangeGridlinePaint(Color.gray);  
        plot.setDomainGridlinePaint(Color.gray);



        chartPanel.addChartMouseListener(new ChartMouseListener() {

            @Override
            public void chartMouseClicked(ChartMouseEvent cme) {

                Plot p = cme.getChart().getPlot();
                if(p instanceof XYPlot){                    
                    if(cme.getEntity() instanceof XYItemEntity){                        
                        int seriesIndex = ((XYItemEntity)cme.getEntity()).getSeriesIndex();
                        int item = ((XYItemEntity)cme.getEntity()).getItem();
                        XYSeries series = ((XYSeriesCollection)dataset).getSeries(seriesIndex);
                        XYDataItem xyItem = series.getDataItem(item);
                        System.out.println(xyItem);

                        pickedK = xyItem.getX().intValue();

                        return;
                    }
                }

                pickedK = 0;
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent cme) {
            }

        });
        
        return chartPanel;
    }
    
    
    
    private ChartPanel createRepeatnessChart(TreeMap<Integer,Double> avg, TreeMap<Integer,Double> sd){
         DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
         Integer zero = new Integer(0);
         for(Integer i : avg.keySet()){
             dataset.add(avg.get(i), sd.get(i), zero, i);
         }
         
         StatisticalBarRenderer renderer = new StatisticalBarRenderer();
         
         CategoryPlot plot = new CategoryPlot(dataset,
                   new CategoryAxis("K"), new NumberAxis("Repeatness"),
                   renderer);
        JFreeChart chart = new JFreeChart(plot);
        chart.getLegend().visible = false;
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        //chartPanel.setPreferredSize(center_panel.getPreferredSize());
        chartPanel.setPreferredSize(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height/nofPanels));
        chart.setBackgroundPaint(Color.white);
        //chartPanel.setMouseZoomable(false);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setMinimumDrawWidth( 0 );
        chartPanel.setMinimumDrawHeight( 0 );
        chartPanel.setMaximumDrawWidth( 1920 );
        chartPanel.setMaximumDrawHeight( 1200 );
        
        
        return chartPanel;
    }
    
    
    private ChartPanel createRepeatnessRatioChart(TreeMap<Integer,Integer> msize, TreeMap<Integer,Double> avg, TreeMap<Integer,Double> sd){
         DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
         Integer zero = new Integer(0);
         for(Integer i : avg.keySet()){
             dataset.add(new Double(avg.get(i) /  (double)msize.get(i)), new Double(sd.get(i) / (double)msize.get(i)), zero, i);
         }
         
         StatisticalBarRenderer renderer = new StatisticalBarRenderer();
         
         CategoryPlot plot = new CategoryPlot(dataset,
                   new CategoryAxis("K"), new NumberAxis("Repeatness ratio"),
                   renderer);
        JFreeChart chart = new JFreeChart(plot);
        chart.getLegend().visible = false;
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        //chartPanel.setPreferredSize(center_panel.getPreferredSize());
        chartPanel.setPreferredSize(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height/nofPanels));
        chart.setBackgroundPaint(Color.white);
        //chartPanel.setMouseZoomable(false);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setMinimumDrawWidth( 0 );
        chartPanel.setMinimumDrawHeight( 0 );
        chartPanel.setMaximumDrawWidth( 1920 );
        chartPanel.setMaximumDrawHeight( 1200 );
        
        
        return chartPanel;
    }
    
    
    private ChartPanel createHapRepChart(TreeMap<Integer,Integer> nof, TreeMap<Integer,Integer> hap, TreeMap<Integer,Integer> rep){
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Integer zero = new Integer(0);
        for(Integer i : hap.keySet()){
            dataset.addValue( ((double)hap.get(i)   /   (double)nof.get(i) )  , "hapax", i);
            dataset.addValue( ((double)rep.get(i)   /   (double)nof.get(i) )  , "repeat", i);
        }
        
        
        
        
        
        final JFreeChart chart = ChartFactory.createStackedBarChart(
            "",  // chart title
            "K",                  // domain axis label
            "Ratio",                     // range axis label
            dataset,                     // data
            PlotOrientation.VERTICAL,    // the plot orientation
            true,                        // legend
            true,                        // tooltips
            false                        // urls
        );
        
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setPreferredSize(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height/nofPanels));
        chart.setBackgroundPaint(Color.white);
        //chartPanel.setMouseZoomable(false);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setMinimumDrawWidth( 0 );
        chartPanel.setMinimumDrawHeight( 0 );
        chartPanel.setMaximumDrawWidth( 1920 );
        chartPanel.setMaximumDrawHeight( 1200 );
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        renderer.setGradientPaintTransformer(null);
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setDefaultBarPainter(new StandardBarPainter());
        
        plot.setRenderer(renderer);
        
        plot.setBackgroundPaint(Color.WHITE);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        
        return chartPanel;
    }
}
