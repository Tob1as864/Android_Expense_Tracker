package at.ac.univie.expensetracker.moneymonkey.utils.proxy;

import com.github.mikephil.charting.components.Legend;

public interface ProxyMPPieChartInterface {
    public void setDrawHoleEnabled(boolean enabled);
    public void setUsePercentValues(boolean enabled);
    public void setEntryLabelTextSize(float size);
    public void setEntryLabelColor(int color);
    public void setCenterText();
    public void setCenterTextSize(float size);
    public ProxyMPPieChartClass getDescription();
    public void setEnabled(boolean enabled);
    public Legend getLegend();
    public boolean create();
}




