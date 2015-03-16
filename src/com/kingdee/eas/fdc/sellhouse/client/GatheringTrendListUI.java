/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.GatheringTrendFacade;
import com.kingdee.eas.fdc.sellhouse.GatheringTrendFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class GatheringTrendListUI extends AbstractGatheringTrendListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6151753826470018203L;
	private static final Logger logger = CoreUIObject.getLogger(GatheringTrendListUI.class);
	
	Map rowMap = new HashMap();

	private GatheringTrendFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private List dateList = null;
	
	private ItemAction[] hideAction = {this.actionEdit,this.actionAddNew,this.actionRemove,this.actionLocate,this.actionView};
	
	//判断是虚体组织还是实体组织
	private boolean issale=SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
    
    /**
     * output class constructor
     */
    public GatheringTrendListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception
    {
    	super.onLoad();
    	this.menuEdit.setVisible(false);
    	this.tblMain.checkParsed();    	
    	//this.rowMap = SHEHelper.createTreeByBuilding(this.actionOnLoad,this.tblMain);
    	FDCClientHelper.setActionVisible(hideAction,false);
    	this.refresh(null);
    }
    protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
    
    private GatheringTrendFilterUI getFilterUI() throws Exception
	{
		if (this.filterUI == null)
		{
			this.filterUI = new GatheringTrendFilterUI(this,this.actionOnLoad);
		}
		return this.filterUI;
	}
    
    protected void execQuery()
    {
    	try
		{   
    		this.rowMap = SHEHelper.createTreeByBuilding(this.actionOnLoad,this.tblMain,MoneySysTypeEnum.SalehouseSys,issale);
			this.addHeadByDays();
			this.initTable();
			Map dateMap=new HashMap();
	    	dateMap.put("beginDate", this.getBeginQueryDate());
	    	dateMap.put("endDate", this.getEndQueryDate());
	    	dateMap.put("isSelect",Boolean.valueOf(this.filterUI.radioByDay.isSelected()));
	    	
			Map dataMap=(Map)GatheringTrendFacadeFactory.getRemoteInstance().getGatheringData(dateMap);
			
			this.fillData(dataMap);
			this.fillRange((Map)dataMap.get("range"));
			this.setUnionData();
		} catch (Exception e)
		{
			this.handUIException(e);
		}
    }
    /**
     * 填充表格数据
     * @throws Exception
     */
    private void fillData(Map dataMap) throws Exception
	{
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			for (int j = 1; j < this.tblMain.getColumnCount(); j++)
			{
				this.tblMain.getCell(i, j).setValue(new BigDecimal("0.00"));
			}
		}
		
		this.fillSumTotalAmount((Map)dataMap.get("sumTotalAmount"));//应收款
		
		this.fillSumGathering((Map)dataMap.get("sumGathering"));//实收款
		
		this.fillSumReceivedLoan((Map)dataMap.get("sumReceivedLoan"));//非按揭类房款未到帐
		
		this.fillSumUnReceivedLoan((Map)dataMap.get("sumUnReceivedLoan"));//按揭类房款未到账
		
		this.fillCash((Map)dataMap.get("cash"));//非按揭类收款
		
		this.fillFund((Map)dataMap.get("fund"));//按揭类收款
	}
    
    /**
     * 填充区间
     * @throws Exception 
     *
     */
    private void fillRange(Map rangeMap) throws Exception
    {
    	Date beginDate = null;
		Date endDate = null;
		
		beginDate = this.getBeginQueryDate();
		endDate =this.getEndQueryDate();
	
		
		if (beginDate == null && endDate == null)
		{
			this.tblMain.getHeadRow(0).getCell("cash").setValue("查询区间数据(全部显示)");
		}
		else if(beginDate == null && endDate != null)
		{
			Calendar cal = new GregorianCalendar();
			cal.setTime(endDate);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE));
			DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
			String des = "查询区间数据(" 	+ FORMAT_DAY.format(cal.getTime()) + "之前)";
			this.tblMain.getHeadRow(0).getCell("cash").setValue(des);
		}
		else if(beginDate != null && endDate == null)
		{
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE));
			DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
			String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "之后)";
			this.tblMain.getHeadRow(0).getCell("cash").setValue(des);
		}
		else
		{
			Calendar cal = new GregorianCalendar();
			cal.setTime(endDate);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE));
			if(this.filterUI.radioByDay.isSelected()){
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "到"
				+ FORMAT_DAY.format(cal.getTime()) + ")";
				this.tblMain.getHeadRow(0).getCell("cash").setValue(des);
			}else{
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy年MM月");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "到"
				+ FORMAT_DAY.format(cal.getTime()) + ")";
				this.tblMain.getHeadRow(0).getCell("cash").setValue(des);
			}
		}
		//按揭类收款
		if(rangeMap.containsKey("sumRevAmount")){
			Map termReceiveMap=(Map)rangeMap.get("sumRevAmount");
			Iterator it=termReceiveMap.keySet().iterator();
			while (it.hasNext())	{
				
				String buildingId =(String)it.next();
				
				IRow row = (IRow) this.rowMap.get(buildingId);
				if (row == null)	continue;
				ICell cell = row.getCell("fund");
				if(cell == null)	continue;
				
				BigDecimal sumRevAmount =(BigDecimal)termReceiveMap.get(buildingId);
				
				if(sumRevAmount == null) sumRevAmount = FDCHelper.ZERO;
				
				if(sumRevAmount.compareTo(FDCHelper.ZERO) < 0)
					cell.getStyleAttributes().setFontColor(Color.RED);
				cell.setValue(sumRevAmount);
				
				ICell sumCell = row.getCell("sum");
				if(sumCell == null) return; 
				BigDecimal sumValue = (BigDecimal)sumCell.getValue();
				if(sumValue==null) sumValue = FDCHelper.ZERO;
				sumValue = sumValue.add(sumRevAmount);
				sumCell.setValue(sumValue);
				if(sumValue.compareTo(FDCHelper.ZERO) < 0)
					sumCell.getStyleAttributes().setFontColor(Color.RED);
			}
		}
		//非按揭类收款
		if(rangeMap.containsKey("UnsumRevAmount")){
			Map termReceiveMap=(Map)rangeMap.get("UnsumRevAmount");
			Iterator it=termReceiveMap.keySet().iterator();
			while(it.hasNext()){
				String buildingId = (String)it.next();
				IRow row = (IRow) this.rowMap.get(buildingId);
				if (row == null)	continue;
				ICell cell = row.getCell("cash"); 
				if(cell == null)	continue;			
				BigDecimal sumRevAmount = (BigDecimal)termReceiveMap.get(buildingId);
				if(sumRevAmount == null)	sumRevAmount = FDCHelper.ZERO;
				if(sumRevAmount.compareTo(FDCHelper.ZERO) < 0)
					cell.getStyleAttributes().setFontColor(Color.RED);
				cell.setValue(sumRevAmount);
				
				ICell sumCell = row.getCell("sum");
				if(sumCell == null) return; 
				BigDecimal sumValue = (BigDecimal)sumCell.getValue();
				if(sumValue==null) sumValue = FDCHelper.ZERO;
				sumValue = sumValue.add(sumRevAmount);
				sumCell.setValue(sumValue);
				if(sumValue.compareTo(FDCHelper.ZERO) < 0)
					sumCell.getStyleAttributes().setFontColor(Color.RED);
			}
				
		}
    }
    
    /**
     * 填充应收款
     * @throws SQLException
     * @throws BOSException
     * @throws EASBizException 
     */
    private void fillSumTotalAmount(Map sumTotalAmountMap) throws SQLException, BOSException, EASBizException
    {    	
    	Iterator it=sumTotalAmountMap.keySet().iterator();
    	while(it.hasNext()){
    		String buildingId=(String)it.next();
    		IRow row = (IRow) this.rowMap.get(buildingId);
    		if (row == null)	{
				continue;
			}
    		BigDecimal sumTotalAmount=(BigDecimal)sumTotalAmountMap.get(buildingId);
    		row.getCell("SumTotalAmount").setValue(sumTotalAmount);
    		
    	}
    }
    
    /**
     * 填充累计收款 (hq 改为 填充实收款)
     * @throws SQLException
     * @throws BOSException
     * @throws EASBizException 
     */
    private void fillSumGathering(Map sumGatheringMap) throws SQLException, BOSException, EASBizException
    {    	
    	Iterator it=sumGatheringMap.keySet().iterator();
    	while(it.hasNext()){
    		String buildingId=(String)it.next();
    		IRow row = (IRow) this.rowMap.get(buildingId);
    		if (row == null){
				continue;
			}
    		BigDecimal sumRevAmount=(BigDecimal)sumGatheringMap.get(buildingId);
    		row.getCell("sumGathering").setValue(sumRevAmount);
    	}
    }
    /**
     * 填充非按揭类房款未到帐
     * @throws BOSException 
     * @throws Exception 
     *
     */
    private void fillSumReceivedLoan(Map sumReceivedLoanMap) throws Exception
    {
    	Iterator it=sumReceivedLoanMap.keySet().iterator();
		while (it.hasNext())
		{
			String buildingId=(String)it.next();
    		IRow row = (IRow) this.rowMap.get(buildingId);
    		if (row == null){
				continue;
			}
    		BigDecimal receiveAmount=(BigDecimal)sumReceivedLoanMap.get(buildingId);
    		
    		ICell cell = row.getCell("sumReceivedLoan");
			if(cell == null)
				continue;
	
			if(receiveAmount == null)
				receiveAmount = FDCHelper.ZERO;
			if(receiveAmount.compareTo(FDCHelper.ZERO) < 0)
				cell.getStyleAttributes().setFontColor(Color.RED);
			cell.setValue(receiveAmount);
		}
    }
    /**
     * 填充累计按揭未到帐    2010/05/14 改为填充按揭类房款未到账
     * @throws BOSException 
     * @throws Exception 
     *
     */
    private void fillSumUnReceivedLoan(Map sumUnReceivedLoanMap) throws Exception
    {
    	Iterator it=sumUnReceivedLoanMap.keySet().iterator();
		while (it.hasNext())
		{
			String buildingId = (String)it.next();
			
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			BigDecimal sumRevAmount =(BigDecimal)sumUnReceivedLoanMap.get(buildingId);
			
			ICell cell = row.getCell("sumUnReceivedLoan");
			
			if(cell == null)
				continue;
	
			if(sumRevAmount == null)
				sumRevAmount = FDCHelper.ZERO;
			if(sumRevAmount.compareTo(FDCHelper.ZERO) < 0)
				cell.getStyleAttributes().setFontColor(Color.RED);
			cell.setValue(sumRevAmount);
		}
    }
    
    /**
     * 填充数据  非按揭类收款
     * @throws Exception 
     *
     */
    private void fillCash(Map cashMap) throws Exception
    {
    	
    	IRowSet termReceiveSet=(IRowSet)cashMap.get("cashSet");
    	while(termReceiveSet.next()){
    		String buildingId = termReceiveSet.getString("FBuildingId");
		    String date  = getFormatDate((Date)termReceiveSet.getDate("d"));
			BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) continue;
			ICell cell = row.getCell(date+"cash");
			if(cell == null)continue;
			
			if(sumRevAmount == null)
				sumRevAmount = FDCHelper.ZERO;
			BigDecimal srcValue = (BigDecimal) cell.getValue();
			if(srcValue == null) srcValue = FDCHelper.ZERO;
			
			if(sumRevAmount == null)
				sumRevAmount = FDCHelper.ZERO;
			
			BigDecimal resValue = srcValue.add(sumRevAmount);
			
			if(resValue.compareTo(FDCHelper.ZERO) < 0){
				cell.getStyleAttributes().setFontColor(Color.RED);
			}else{
				cell.getStyleAttributes().setFontColor(Color.BLACK);
			}
			cell.setValue(resValue);
			//
			this.fillSum(row,date);	
    	}
    	
//    	Iterator it=cashMap.keySet().iterator();
//		while (it.hasNext())
//		{
//			String buildingId = (String)it.next();
//			
//			Map map=(Map)cashMap.get(buildingId);
//			
//			
//			String date =(String)map.get("date");
//			
//			IRow row = (IRow) this.rowMap.get(buildingId);
//			if (row == null)
//			{
//				continue;
//			}
//			BigDecimal sumRevAmount =(BigDecimal) map.get("sumRevAmount");
//			
//			ICell cell = row.getCell(date+"cash");
//			if(cell == null)
//				continue;
//			
//			if(sumRevAmount == null)
//				sumRevAmount = FDCHelper.ZERO;
//			BigDecimal srcValue = (BigDecimal) cell.getValue();
//			if(srcValue == null) srcValue = FDCHelper.ZERO;
//			
//			if(sumRevAmount == null)
//				sumRevAmount = FDCHelper.ZERO;
//			
//			BigDecimal resValue = srcValue.add(sumRevAmount);
//			
//			if(resValue.compareTo(FDCHelper.ZERO) < 0){
//				cell.getStyleAttributes().setFontColor(Color.RED);
//			}else{
//				cell.getStyleAttributes().setFontColor(Color.BLACK);
//			}
//			cell.setValue(resValue);
//			//
//			this.fillSum(row,date);			
//		}
    }
    /**
     * 填充按揭公积金 （hq 改为填充按揭类收款）
     * @throws Exception 
     *
     */
    private void fillFund(Map fundMap) throws Exception
    {
		IRowSet termReceiveSet=(IRowSet)fundMap.get("fundeSet");
		while (termReceiveSet.next()){
			String buildingId = termReceiveSet.getString("FBuildingId");
			String date  = getFormatDate((Date)termReceiveSet.getDate("d"));
			BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			
			ICell cell = row.getCell(date+"fund");
			if(cell == null)
				continue;
			
			BigDecimal srcValue = (BigDecimal) cell.getValue();
			if(srcValue == null) srcValue = FDCHelper.ZERO;
			
			if(sumRevAmount == null)
				sumRevAmount = FDCHelper.ZERO;
			
			BigDecimal resValue = srcValue.add(sumRevAmount);
			
			if(resValue.compareTo(FDCHelper.ZERO) < 0){
				cell.getStyleAttributes().setFontColor(Color.RED);
			}else{
				cell.getStyleAttributes().setFontColor(Color.BLACK);
			}
			cell.setValue(resValue);
			
			this.fillSum(row,date);
    	}
			
//		Iterator it=fundMap.keySet().iterator();
//		while (it.hasNext())
//		{
//			String buildingId = (String)it.next();
//			
//			Map map=(Map)fundMap.get(buildingId);
//			
//			String date = (String)map.get("date");
//			
//			IRow row = (IRow) this.rowMap.get(buildingId);
//			if (row == null)
//			{
//				continue;
//			}
//			BigDecimal sumRevAmount = (BigDecimal)map.get("sumRevAmount");
//			
//			ICell cell = row.getCell(date+"fund");
//			if(cell == null)
//				continue;
//			
//			BigDecimal srcValue = (BigDecimal) cell.getValue();
//			if(srcValue == null) srcValue = FDCHelper.ZERO;
//			
//			if(sumRevAmount == null)
//				sumRevAmount = FDCHelper.ZERO;
//			
//			BigDecimal resValue = srcValue.add(sumRevAmount);
//			
//			if(resValue.compareTo(FDCHelper.ZERO) < 0){
//				cell.getStyleAttributes().setFontColor(Color.RED);
//			}else{
//				cell.getStyleAttributes().setFontColor(Color.BLACK);
//			}
//			cell.setValue(resValue);
//			
//			this.fillSum(row,date);
//		}
    }
    /**
     * 填充小计
     * @param row
     * @param date
     */	
    private void fillSum(IRow row,String date)
    {
    	ICell fundCell = row.getCell(date+"fund");
		if(fundCell == null)
			return;
		BigDecimal fund = FDCHelper.ZERO;
		if(fundCell.getValue() != null)
		{
			fund = new BigDecimal(fundCell.getValue().toString());
		}
		ICell sumCell = row.getCell(date+"sum");
		if(sumCell == null)
			return;

		ICell cashCell = row.getCell(date+"cash");
		if(cashCell == null)
			return;
		BigDecimal cash = FDCHelper.ZERO;
		if(cashCell.getValue() != null)
		{
			cash = new BigDecimal(cashCell.getValue().toString());
		}
		
		BigDecimal temp = cash.add(fund);
		if(temp == null)
			temp = FDCHelper.ZERO;
		if(temp.compareTo(FDCHelper.ZERO) < 0){
			sumCell.getStyleAttributes().setFontColor(Color.RED);
		}else{
			sumCell.getStyleAttributes().setFontColor(Color.BLACK);
		}
		
		sumCell.setValue(temp);
    }
    	
   
    
    private void initTable()
	{
		for (int i = 1; i < this.tblMain.getColumnCount(); i++)
		{
			tblMain.getColumn(i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
	}
    
    
    /**
     * 根据某天来生成一个表头
     * @param date
     */
    private void addHeadByOneDay(Date date)
    {
    	String temp =getFormatDate(date);
    	int count = this.tblMain.getColumnCount();
    	
    	IColumn col1 = this.tblMain.addColumn();
    	IColumn col2 = this.tblMain.addColumn();
    	IColumn col3 = this.tblMain.addColumn();
    	col1.setKey(temp+"cash");
    	col2.setKey(temp+"fund");
    	col3.setKey(temp+"sum");
    	IRow row0 = this.tblMain.getHeadRow(0);
    	row0.getCell(temp+"cash").setValue(temp);
    	
    	IRow row1 = this.tblMain.getHeadRow(1);
    	
    	row1.getCell(temp+"cash").setValue("非按揭类收款");
    	row1.getCell(temp+"fund").setValue("按揭类收款");
    	row1.getCell(temp+"sum").setValue("小计");
    	
    	this.tblMain.getHeadMergeManager().mergeBlock(0,count,0,count+2);
    	
    }
    
    private void addHeadByDays() throws Exception
    {
    	int total = this.tblMain.getColumnCount();
    	int offSet = 0 ;
    	for(int i = 8; i < total; i ++)
    	{
    		this.tblMain.removeColumn(i - offSet);
    		offSet ++;
    	}
    	
		Date beginDate = this.getBeginQueryDate();
		Date endDate = this.getEndQueryDate();
		
		if(beginDate == null || endDate == null)
			return;
		List list = new ArrayList();
		Date temp = beginDate;
		do
		{
			list.add(temp);
			temp = this.addDays(temp,1);
		}
		while(temp.before(endDate));
		if(beginDate.before(endDate))
		{
			list.add(endDate);
		}
		
		for(int i = 0; i < list.size(); i ++)
		{
			Date date = (Date) list.get(i);
			this.addHeadByOneDay(date);
		}
    }
    public static Date truncateDateTest(Date dt)
    {
        if (dt==null)
            return null;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(cal.get(Calendar.YEAR), 
                cal.get(Calendar.MONTH), 
                0, 
                0, 
                0);        
        return new Date((cal.getTimeInMillis() / 1000) * 1000);
    }
    
    private Date addDays(Date date,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	if(this.filterUI.radioByDay.isSelected()){
    		calendar.add(Calendar.DATE,day);
    	}else{
    		calendar.add(Calendar.MONTH,day);
    	}
    	return calendar.getTime();
    }
    
 /**
  * 按月，按日 过滤表头显示
  * @param date
  * @return
  */
    private String getFormatDate(Date date){
    	 String newDate=null;    	 
    	 if(this.filterUI.radioByDay.isSelected()){
    		 newDate=FDCClientHelper.formateDate(DateTimeUtils.truncateDate(date));
    	 }else{
    		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月");
    		 newDate=sdf.format(date);
    	 }
    	 return newDate;
    }
    
    protected String getKeyFieldName()
	{
		return "name";
	}
	private Date getBeginQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getBeginQueryDate(para);
	}
	private Date getEndQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getEndQueryDate(para);
	}
	protected void initTableParams()
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
    
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		
	}
	
	public void setUnionData()
	{
		for (int i = 0; i < tblMain.getRowCount(); i++)
		{
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null)
			{
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++)
				{
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level)
					{
						break;
					}
					if (rowAfter.getUserObject() != null)
					{
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++)
				{
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++)
					{
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null)
						{
							if (value instanceof BigDecimal)
							{
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer)
							{
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							}
							else if(value instanceof Float)
							{
								aimCost = aimCost.add(new BigDecimal(((Float) value).toString()));
							}
						}
					}
					if(aimCost == null)
						aimCost = FDCHelper.ZERO;
					if(aimCost.compareTo(FDCHelper.ZERO) < 0)
						row.getCell(j).getStyleAttributes().setFontColor(Color.RED);
					row.getCell(j).setValue(aimCost);
				}
			}
		}
	}	
	
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}
	protected boolean initDefaultFilter()
	{
		return true;
	}
	protected boolean isAllowDefaultSolutionNull()
	{
		return true;
	}

    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }


	protected String getEditUIName()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return null;
	}

}