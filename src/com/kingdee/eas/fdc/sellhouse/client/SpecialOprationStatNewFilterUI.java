/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class SpecialOprationStatNewFilterUI extends AbstractSpecialOprationStatNewFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductTypeSellStatFilterUI.class);
    
    /**
     * 与日期有关的字段
     */
    private  final String MONTH_TO = "monthTo";
	private  final String MONTH_FROM = "monthFrom";
	private  final String YEAR_TO = "yearTo";
	private  final String YEAR_FROM = "yearFrom";
	private  final String DATE_TO = "dateTo";
	private  final String DATE_FROM = "dateFrom";
	private  final String DATE_TYPE = "dateType";
	private  final String IS_SHOW_ALL = "isShowAll";
	
	/**
	 * 其他过滤条件
	 */
	private  final String IS_PRE_INTO_SALE = "isPreIntoSale";
	private  final String IS_INCLUDE_ATTACH = "isIncludeAttach";
	
	
	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;
    
	public SpecialOprationStatNewFilterUI(ListUI paramListUI,ItemAction paramActionListOnLoad)throws Exception
	{
		super();
		listUI = paramListUI;
		actionListOnLoad = paramActionListOnLoad;
		isLoaded = false;
	}
    /**
     * output class constructor
     */
    public SpecialOprationStatNewFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	if(!isLoaded)
    	{
    		this.clear();
    	}
    	isLoaded = true;
    }
  
    public void clear()
    {
    	this.beginDate.setValue(new Date());
		this.pkDateTo.setValue(new Date());
		this.radioByDay.setSelected(true);
		
		initControlByDateType();
    }
    
    public Date getBeginQueryDate(FDCCustomerParams para)
	{
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0)
		{
			date = para.getDate(DATE_FROM);
		} else if (dateType == 1)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, (para.getInt(MONTH_FROM) - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	public Date getEndQueryDate(FDCCustomerParams para)
	{
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0)
		{
			date = para.getDate(DATE_TO);
		} else if (dateType == 1)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO) * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output radioByDay_actionPerformed method
     */
    protected void radioByDay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	super.radioByDay_actionPerformed(e);
        this.initControlByDateType();
    }

    /**
     * output radioByMonth_actionPerformed method
     */
    protected void radioByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	super.radioByMonth_actionPerformed(e);
        this.initControlByDateType();
    }

    /**
     * output radioByQuarter_actionPerformed method
     */
    protected void radioByQuarter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	super.radioByQuarter_actionPerformed(e);
        this.initControlByDateType();
    }

    /**
     * output radioByYear_actionPerformed method
     */
    protected void radioByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	super.radioByYear_actionPerformed(e);
       this.initControlByDateType();
    }

    /**
     * 根据日期过滤类型来改变界面
     *
     */
	protected void initControlByDateType()
	{
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		if (this.radioByDay.isSelected()) 
		{
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.pkBeginDate.setVisible(true);
			this.contDateTo.setVisible(true);

		} else if (this.radioByMonth.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.pkBeginDate.setVisible(false);
			this.contDateTo.setVisible(false);
			SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthFrom.setModel(monthMo1);
			SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthTo.setModel(monthMo2);
		} else if (this.radioByQuarter.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(true);
			this.lblQuarterTo.setVisible(true);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.pkBeginDate.setVisible(false);
			this.contDateTo.setVisible(false);
			int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
			SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthFrom.setModel(quarterMo1);
			SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthTo.setModel(quarterMo2);

		} else if (this.radioByYear.isSelected()) 
		{
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.pkBeginDate.setVisible(false);
			this.contDateTo.setVisible(false);
		}if (this.chkIsShowAll.isSelected()) {
			this.radioByDay.setEnabled(false);
			this.radioByMonth.setEnabled(false);
			this.radioByQuarter.setEnabled(false);
			this.radioByYear.setEnabled(false);
			this.spiYearFrom.setEnabled(false);
			this.spiYearTo.setEnabled(false);
			this.spiMonthFrom.setEnabled(false);
			this.spiMonthTo.setEnabled(false);
			this.pkBeginDate.setEnabled(false);
			this.pkDateTo.setEnabled(false);
		}
		else
		{
			this.radioByDay.setEnabled(true);
			this.radioByMonth.setEnabled(true);
			this.radioByQuarter.setEnabled(true);
			this.radioByYear.setEnabled(true);
			this.plDateType.setEnabled(true);
			this.spiYearFrom.setEnabled(true);
			this.spiYearTo.setEnabled(true);
			this.spiMonthFrom.setEnabled(true);
			this.spiMonthTo.setEnabled(true);
			this.pkBeginDate.setEnabled(true);
			this.pkDateTo.setEnabled(true);
		}
	}
	
	public CustomerParams getCustomerParams()
	{

		FDCCustomerParams param = new FDCCustomerParams();

		if (this.radioByDay.isSelected())
		{
			param.add(DATE_FROM, (Date) this.beginDate.getValue());
			param.add(DATE_TO, (Date) this.pkDateTo.getValue());
			param.add(DATE_TYPE, 0);
		} else if (this.radioByMonth.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 1);
		} else if (this.radioByQuarter.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 2);
		} else if (this.radioByYear.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(DATE_TYPE, 3);
		}		
		param.add(IS_SHOW_ALL, this.chkIsShowAll.isSelected());	
		param.add(IS_INCLUDE_ATTACH, this.chkIsIncludeAttach.isSelected());
		param.add(IS_PRE_INTO_SALE, this.chkIsPrePurchaseIntoSaleStat.isSelected());
		
		return param.getCustomerParams();
	}
	
	public boolean isIncludeAttach(FDCCustomerParams para) 
	{
		return para.getBoolean(IS_INCLUDE_ATTACH);
	}

	public boolean isPreIntoSale(FDCCustomerParams para) 
	{
		return para.getBoolean(IS_PRE_INTO_SALE);
	}	
	
	public void setCustomerParams(CustomerParams param)
	{
		if (param == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(param);
		if (para.getInt(DATE_TYPE) == 0)
		{
			this.radioByDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1)
		{
			this.radioByMonth.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 2)
		{
			this.radioByQuarter.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 3)
		{
			this.radioByYear.setSelected(true);
		}
		
		this.initControlByDateType();

		if (para.getInt(DATE_TYPE) == 0)
		{
			this.beginDate.setValue(para.getDate(DATE_FROM));
			this.pkDateTo.setValue(para.getDate(DATE_TO));
		} else if (para.getInt(DATE_TYPE) == 1)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 3)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		}
		
		this.chkIsShowAll.setSelected(para.getBoolean(IS_SHOW_ALL));
		this.chkIsIncludeAttach.setSelected(para.getBoolean(IS_INCLUDE_ATTACH));
		this.chkIsPrePurchaseIntoSaleStat.setSelected(para.getBoolean(IS_PRE_INTO_SALE));
		
		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() 
	{
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		Date beginQueryDate = this.getBeginQueryDate(para);
		Date endQueryDate = this.getEndQueryDate(para);
		if(beginQueryDate == null)
		{
			MsgBox.showWarning(this, "开始时间不能为空！");
			return false;
		}
		if(endQueryDate == null)
		{
			MsgBox.showWarning(this, "结束时间不能为空！");
			return false;
		}
		if (beginQueryDate != null && endQueryDate != null)
		{
			if (endQueryDate.before(beginQueryDate))
			{
				MsgBox.showWarning(this, "开始时间不能晚于结束时间！");
				return false;
			}
		}
		return true;
	}
	
	protected void chkIsShowAll_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsShowAll_actionPerformed(e);
		this.initControlByDateType();
	}
}