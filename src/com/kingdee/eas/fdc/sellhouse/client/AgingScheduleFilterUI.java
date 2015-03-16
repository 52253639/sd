/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class AgingScheduleFilterUI extends AbstractAgingScheduleFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AgingScheduleFilterUI.class);
    
    protected ListUI listUI;
	
    protected ItemAction actionListOnLoad;
    
    private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";
	
	private static final String DATE_TYPE = "dateType";
	
	private static final String IS_INCLUDE_ATTACH = "isIncludeAttach";
	
	private static final String FRONDDATE="fronddate";
	
	public boolean isUseSpecialChk = true;
    /**
     * output class constructor
     */
    public AgingScheduleFilterUI() throws Exception
    {
        super();
    }

    public AgingScheduleFilterUI(ListUI listUI, ItemAction actionListOnLoad)
	throws Exception {
    	this(listUI, actionListOnLoad, true);
    }
   
	public AgingScheduleFilterUI(ListUI listUI, ItemAction actionListOnLoad, boolean isUseSpecialChk) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
		this.isUseSpecialChk = isUseSpecialChk;
	}
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 新增行
     */
    public void actionBtnSave_actionPerformed(ActionEvent e) throws Exception
    {
        this.kDTable1.addRow();
    }

    /**
     * 删除行
     */
    public void actionBtnDel_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = KDTableUtil.getSelectedRow(kDTable1);
		if (row != null){
			kDTable1.removeRow(row.getRowIndex());
		}
    }
    public void clear() {
    	this.dpBeginDate.setValue(new Date());
		this.dpEndDate.setValue(new Date());
		this.radDay.setSelected(true);
		 this.isAttach.setSelected(false);
		kDTable1.removeRows();
		
    	
    }
    public void onLoad() throws Exception
	{
		super.onLoad();
		this.btnSave.setEnabled(true);
		this.btnDel.setEnabled(true);
		kDTable1.checkParsed();
		/*IRow row1=kDTable1.addRow();
		row1.getCell("caption").setValue("1-30");
		row1.getCell("fate").setValue("30");
		IRow row2=kDTable1.addRow();
		row2.getCell("caption").setValue("31-70");
		row2.getCell("fate").setValue("40");*/

		this.contYearFrom.setVisible(false);
		this.contYearTo.setVisible(false);
		this.lblMonthFrom.setVisible(false);
		this.lblMonthTo.setVisible(false);
		this.lblQuarterFrom.setVisible(false);
		this.lblQuarterTo.setVisible(false);
		this.spiMonthFrom.setVisible(false);
		this.spiMonthTo.setVisible(false);
		kDTable1.getColumn("caption").getStyleAttributes().setLocked(true);
		
		KDTextField formattedTextField = new KDTextField();
		formattedTextField.setMaxLength(4);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		kDTable1.getColumn("fate").setEditor(numberEditor);
		isUseSpecialChk=false;
		
	    KDFormattedTextField kdtEntrys_fate_TextField = new KDFormattedTextField();
	    kdtEntrys_fate_TextField.setName("kdtEntrys_fate_TextField");
	    kdtEntrys_fate_TextField.setVisible(true);
	    kdtEntrys_fate_TextField.setEditable(true);
	    kdtEntrys_fate_TextField.setHorizontalAlignment(2);
	    kdtEntrys_fate_TextField.setDataType(0);
	    	kdtEntrys_fate_TextField.setMinimumValue(new java.math.BigDecimal("1"));
	    	kdtEntrys_fate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
	    kdtEntrys_fate_TextField.setPrecision(0);
	    KDTDefaultCellEditor kdtEntrys_fate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_fate_TextField);
	    this.kDTable1.getColumn("fate").setEditor(kdtEntrys_fate_CellEditor);
	}
	
    
	protected void dpEndDate_dataChanged(DataChangeEvent e) throws Exception
	{
		Date beginDate=(Date) this.dpBeginDate.getValue();
		Date endDate =(Date)this.dpEndDate.getValue();
		if(endDate ==null){
			MsgBox.showWarning(this,"结束日期不能为空");
			this.dpEndDate.setValue(e.getOldValue());
		}
		if(beginDate!=null && endDate!=null){
			if(FMHelper.dateCompare(beginDate, endDate)>0){
				MsgBox.showWarning(this,"结束日期不能小于起始日期");
				this.dpEndDate.setValue(e.getOldValue());
			}
		}
	}

	protected void dpBeginDate_dataChanged(DataChangeEvent e) throws Exception
	{
		Date beginDate=(Date) this.dpBeginDate.getValue();
		Date endDate =(Date)this.dpEndDate.getValue();
		if(beginDate ==null){
			MsgBox.showWarning(this,"起始日期不能为空");
			this.dpBeginDate.setValue(e.getOldValue());
		}
		if(beginDate!=null && endDate!=null){
			if(FMHelper.dateCompare(beginDate, endDate)>0){
				MsgBox.showWarning(this,"起始日期不能大于结束日期");
				this.dpBeginDate.setValue(e.getOldValue());
			}
		}
		
	}

	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception
	{
//		for(int i = 0 ; i < kDTable1.getRowCount(); i ++){
//			if(kDTable1.getRow(i).getCell("fate").getValue()==null){
//				MsgBox.showWarning(this,"天数不能为空！");
//				return;
//			}
//			if(kDTable1.getRow(i).getCell("fate").getValue().toString().trim().equals("")){
//				MsgBox.showWarning(this,"天数不能为空！");
//				return;
//			}
//			try{
//				Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString());
//			}catch (Exception e1) {
//				MsgBox.showWarning(this,"天数非数字！");
//				return;
//			}
//		}
		IRow row= KDTableUtil.getSelectedRow(kDTable1);
				if(row.getCell("fate").getValue()!=null && !row.getCell("fate").getValue().toString().trim().equals("")){
					int fate =Integer.parseInt(row.getCell("fate").getValue().toString());
					if(fate>0){
						if(row.getRowIndex()==(kDTable1.getRowCount()-1)){
							int frontSum=0;
							for(int i = 0 ;i<row.getRowIndex();i++){
								if(kDTable1.getRow(i).getCell("fate").getValue()!=null )
								{
								    frontSum +=Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString());
								}
							}
							row.getCell("caption").setValue("逾期"+(frontSum+1)+"-"+(frontSum+fate)+"天");
						}else if(row.getRowIndex()<(kDTable1.getRowCount()-1)){
							int frontSumS=0;
							for(int i = 0 ;i<row.getRowIndex();i++){
								if(kDTable1.getRow(i).getCell("fate").getValue()!=null )
								{
								  frontSumS +=Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString());
								}
							}
							row.getCell("caption").setValue("逾期"+(frontSumS+1)+"-"+(frontSumS+fate)+"天");
							for(int i =(row.getRowIndex()+1) ; i<kDTable1.getRowCount();i++){
								int frontSum = 0;
								int fateS= 0 ;
								if(kDTable1.getRow(i).getCell("fate").getValue()!=null )
								{
									for(int j = 0 ;j<i;j++){
										if(kDTable1.getRow(j).getCell("fate").getValue()!=null){
											frontSum +=Integer.parseInt(kDTable1.getRow(j).getCell("fate").getValue().toString());
										
									}
									fateS =Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString());
									kDTable1.getRow(i).getCell("caption").setValue("逾期"+(frontSum+1)+"-"+(frontSum+fateS)+"天");
								}
							}
						}
					}	
				}
		}
				
//		try{
//			if(row.getCell("caption").getValue()!=null){
//				String caption=row.getCell("caption").getValue().toString();
//				if(caption.trim().equals("")){
//					MsgBox.showWarning(this,"列标题不能为空");
//					kDTable1.removeRow(row.getRowIndex());
//				}
//			}else{
//				MsgBox.showWarning(this,"列标题不能为空");
//				kDTable1.removeRow(row.getRowIndex());
//			}
//			int date= Integer.parseInt(row.getCell("fate").getValue().toString());
//			if(date<=0){
//				MsgBox.showWarning(this,"天数必须大于0");
//				kDTable1.removeRow(row.getRowIndex());
//			}
//		}catch (Exception e1) {
//			MsgBox.showWarning(this,"天数必须输入数字");
//			kDTable1.removeRow(row.getRowIndex());
//		}
	}

	protected void radDay_actionPerformed(ActionEvent e) throws Exception
	{
		super.radDay_actionPerformed(e);
		initControlByDateType();
	}

	protected void radMonth_actionPerformed(ActionEvent e) throws Exception
	{
		super.radMonth_actionPerformed(e);
		initControlByDateType();
	}

	protected void radQuarter_actionPerformed(ActionEvent e) throws Exception
	{
		super.radQuarter_actionPerformed(e);
		initControlByDateType();
	}

	protected void radYear_actionPerformed(ActionEvent e) throws Exception
	{
		super.radYear_actionPerformed(e);
		initControlByDateType();
	}
	
	private void initControlByDateType() {
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		if (this.radDay.isSelected()) {
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.conBeginDate.setVisible(true);
			this.conEndDate.setVisible(true);

		} else if (this.radMonth.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.conBeginDate.setVisible(false);
			this.conEndDate.setVisible(false);
			SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthFrom.setModel(monthMo1);
			SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthTo.setModel(monthMo2);
		} else if (this.radQuarter.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(true);
			this.lblQuarterTo.setVisible(true);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.conBeginDate.setVisible(false);
			this.conEndDate.setVisible(false);
			int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
			SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthFrom.setModel(quarterMo1);
			SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthTo.setModel(quarterMo2);

		} else if (this.radYear.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.conBeginDate.setVisible(false);
			this.conEndDate.setVisible(false);
		}
	}
	public FilterInfo getFilterInfo()
	{
		
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams param = new FDCCustomerParams(getCustomerParams());
		Date agingBeginDate =getBeginQueryDate(param);
		Date agingEndDate = getEndQueryDate(param);	
		boolean includeAttach= isIncludeAttach(param);
		filter.getFilterItems().add(new FilterItemInfo("agingBeginDate",agingBeginDate==null?null:new Timestamp(agingBeginDate.getTime()),CompareType.GREATER_EQUALS) );
		filter.getFilterItems().add(new FilterItemInfo("agingEndDate",agingBeginDate==null?null:new Timestamp(agingEndDate.getTime()),CompareType.GREATER_EQUALS) );
		filter.getFilterItems().add(new FilterItemInfo("includeAttach",new Integer(includeAttach?1:0)));
		
		return filter;
	}
	
	public void setCustomerParams(CustomerParams cp)
	{
		kDTable1.removeRows();
		if(cp==null){
			return ;
		}
		
//		if(para.getInt(DATE_TYPE)==0){
//			dpBeginDate.setValue(para.getDate(DATE_FROM));
//			dpEndDate.setValue(para.getDate(DATE_TO));
//		}else if(para.getInt(DATE_TYPE)==1){
//			spiYearFrom.setValue(para.getDate(YEAR_FROM));
//			spiYearTo.setValue(para.getDate(YEAR_TO));
//			spiMonthFrom.setValue(para.getDate(MONTH_FROM));
//			spiMonthTo.setValue(para.getDate(MONTH_TO));
//		}else if(para.getInt(DATE_TYPE)==2){
//			spiYearFrom.setValue(para.getDate(YEAR_FROM));
//			spiYearTo.setValue(para.getDate(YEAR_TO));
//			spiMonthFrom.setValue(para.getDate(MONTH_FROM));
//			spiMonthTo.setValue(para.getDate(MONTH_TO));
//		}else if(para.getInt(DATE_TYPE)==3){
//			spiYearFrom.setValue(para.getDate(YEAR_FROM));
//			spiYearTo.setValue(para.getDate(YEAR_TO));
//		}
		FDCCustomerParams para = new FDCCustomerParams(cp);
		
		if(para.getInt(DATE_TYPE)==0){
			this.radDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.radMonth.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 2) {
			this.radQuarter.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 3) {
			this.radYear.setSelected(true);
		}
		
		initControlByDateType();
		
		if(para.getInt(DATE_TYPE)==0){
			dpBeginDate.setValue(para.getDate(DATE_FROM));
			dpEndDate.setValue(para.getDate(DATE_TO));
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
			
		} else if (para.getInt(DATE_TYPE) == 3) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			
		}
	
		
		
		 this.isAttach.setSelected(para.getBoolean(IS_INCLUDE_ATTACH));
		 
		//逾期
		String[] frontDateStr =para.getStringArray(FRONDDATE);
		if(frontDateStr!=null){
			for(int i=0,n=frontDateStr.length;i<n;i++){
				String[] str=frontDateStr[i].split(";");
				IRow row=kDTable1.addRow();
				if(str.length>1){
					row.getCell(0).setValue(str[0]);
					row.getCell(1).setValue(str[1]);
				}else{
					row.getCell(0).setValue(frontDateStr[0]);
					row.getCell(1).setValue(frontDateStr[1]);
					break;
				}
				
			
			}
		}
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	public boolean isIncludeAttach(FDCCustomerParams para) {
		return para.getBoolean(IS_INCLUDE_ATTACH);
	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		if (this.radDay.isSelected()) {
			param.add(DATE_FROM, (Date) this.dpBeginDate.getValue());
			param.add(DATE_TO, (Date) this.dpEndDate.getValue());
			param.add(DATE_TYPE, 0);
		} else if (this.radMonth.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 1);
		} else if (this.radQuarter.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 2);
		} else if (this.radYear.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(DATE_TYPE, 3);
		}
		
		
		param.add(IS_INCLUDE_ATTACH, this.isAttach.isSelected());
		
		//增加销售状态过
		if(this.kDTable1.getRowCount()>0) {
			ArrayList frontdateSet = new ArrayList(); 
			for(int i=0,n=this.kDTable1.getRowCount();i<n;i++){
				IRow row = this.kDTable1.getRow(i);
				StringBuffer rowValue=new StringBuffer();
				for(int j=0;j<2;j++){
					ICell cell = row.getCell(j);
					rowValue.append(cell.getValue());
					if(j==0){
						rowValue.append(";");
					}
				}
				frontdateSet.add(rowValue.toString());
			}
			String[] frontdateArr = new String[frontdateSet.size()];
			if(frontdateSet.size()>0){
				frontdateSet.toArray(frontdateArr);
			}
			param.add(FRONDDATE, frontdateArr);
		}
		
		return param.getCustomerParams();
	}
	
	public Date getBeginQueryDate(FDCCustomerParams para) {
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0) {
			date = para.getDate(DATE_FROM);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, (para.getInt(MONTH_FROM) - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	public Date getEndQueryDate(FDCCustomerParams para) {
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0) {
			date = para.getDate(DATE_TO);

		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO) * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		Date d =  DateTimeUtils.truncateDate(date);
		d.setHours(23);
		d.setMinutes(59);
		d.setSeconds(59);
		return d;
	}	
	
	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		Date beginQueryDate = this.getBeginQueryDate(para);
		Date endQueryDate = this.getEndQueryDate(para);
		if (beginQueryDate != null && endQueryDate != null) {
			if (endQueryDate.before(beginQueryDate)) {
				MsgBox.showWarning(this, "开始日期不能大于结束日期!");
				return false;
			}
		}
		int fateSum=0;
		for(int i = 0 ; i < kDTable1.getRowCount(); i ++){
			if(kDTable1.getRow(i).getCell("fate").getValue()==null){
				MsgBox.showWarning(this,"天数不能为空！");
				return false;
			}
			if(kDTable1.getRow(i).getCell("fate").getValue().toString().trim().equals("")){
				MsgBox.showWarning(this,"天数不能为空！");
				return false;
			}
			try{
				Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString());
			}catch (Exception e1) {
				MsgBox.showWarning(this,"天数非数字！");
				return false;
			}
			if(Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString()) ==0){
				MsgBox.showWarning(this,"天数不能为0！");
				return false;
			}
			fateSum+=Integer.parseInt(kDTable1.getRow(i).getCell("fate").getValue().toString());
		}
		if(fateSum>10000){
			MsgBox.showWarning(this,"天数合计不能大于10000！");
			return false;
		}
		return true;
	}
	
	
}