/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.market.AreaSetFactory;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.market.MeasurePlanTargetCollection;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
import com.kingdee.eas.fdc.market.MonthFactValueFacadeFactory;
import com.kingdee.eas.fdc.market.MonthPlanCollection;
import com.kingdee.eas.fdc.market.MonthPlanEntryCollection;
import com.kingdee.eas.fdc.market.MonthPlanEntryInfo;
import com.kingdee.eas.fdc.market.MonthPlanFactory;
import com.kingdee.eas.fdc.market.MonthPlanInfo;
import com.kingdee.eas.fdc.market.ProReferenceInfo;
import com.kingdee.eas.fdc.market.ValueBreakCollection;
import com.kingdee.eas.fdc.market.ValueBreakEntryCollection;
import com.kingdee.eas.fdc.market.ValueBreakEntryFactory;
import com.kingdee.eas.fdc.market.ValueBreakEntryInfo;
import com.kingdee.eas.fdc.market.ValueBreakFactory;
import com.kingdee.eas.fdc.market.ValueBreakInfo;
import com.kingdee.eas.fdc.market.VersionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class MonthPlanEditUI extends AbstractMonthPlanEditUI
{

    private static final Logger logger = CoreUIObject.getLogger(MonthPlanEditUI.class);
    String valueVersoin = null;
    int year = 0;
    int cycle = 0;
    int gapColumn = 10;
    //当前年、月
    int y,m;
    private IRowSet setProYear = null;
    private IRowSet setProNew = null;
    
    public MonthPlanEditUI() throws Exception
    {
    	super();
    	//获取当前年月
    	Calendar cal=Calendar.getInstance();
    	y=cal.get(Calendar.YEAR);
    	m=cal.get(Calendar.MONTH)+1;
    }
	//构建分录产品类型为f7控件
	private KDBizPromptBox f7productType = null; 
	private KDBizPromptBox f7AreaRange = null; 
	public KDBizPromptBox  getF7productType(int columnIndex){
		if(f7productType==null){
			f7productType = new KDBizPromptBox();
			f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			view.setFilter(filter);
			f7productType.setEntityViewInfo(view);
			f7productType.setEditable(true);
			f7productType.setDisplayFormat("$name$");
			f7productType.setEditFormat("$number$");
			f7productType.setCommitFormat("$number$");	
		}
		if(columnIndex == 1){
			return f7productType;
		}else{
			return null;
		}
	}
	
	public void onShow() throws Exception {
		super.onShow();
		KDTableHelper.autoFitColumnWidth(this.kdtProReference, kdtProReference.getColumnIndex("year"));
	}



	public KDBizPromptBox getF7Area(int columnIndex){
		if(f7AreaRange==null){
			f7AreaRange = new KDBizPromptBox();
			f7AreaRange.setQueryInfo("com.kingdee.eas.fdc.market.app.AreaSetQuery");
	    	EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			view.setFilter(filter);
			f7AreaRange.setEntityViewInfo(view);
			f7AreaRange.setEditable(true);
			f7AreaRange.setDisplayFormat("$description$");
			f7AreaRange.setEditFormat("$number$");
			f7AreaRange.setCommitFormat("$number$");
		}
		if(columnIndex == 1){
			return f7AreaRange;
		}else{
			return null;
		}
	}
	void initTable(int year,int cycle){
		kdtEntrys = new KDTable[cycle];
		kDTabbedPane1.removeAll();
		for(int i = 0;i<cycle;i++){
			kdtEntrys[i] = new KDTable();
			kdtEntrys[i].setName("kdtEntry"+i);
			kdtEntrys[i].setFormatXml(resHelper.translateString("kdtEntry",TableUtils.getTableXml()));
			kdtEntrys[i].checkParsed();
			kdtEntrys[i].setToolTipText(String.valueOf(year+i));
			kdtEntrys[i].getViewManager().setFreezeView(-1, 3);
			kdtEntrys[i].getColumn(0).setEditor(CommerceHelper.getKDComboColorEditor());			
			kdtEntrys[i].getColumn(1).setEditor(new KDTDefaultCellEditor(getF7productType(1)));
			kdtEntrys[i].getColumn(2).setEditor(new KDTDefaultCellEditor(getF7Area(1)));
			
			kdtEntrys[i].getColumn(12).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.SINGLE_LINE);
			kdtEntrys[i].getColumn(17).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.SINGLE_LINE);
			for(int j=0;j<kdtEntrys[i].getColumnCount();j++){
				if(j>18){
					if(j%10==7&&j!=kdtEntrys[i].getColumnCount()-1){
						kdtEntrys[i].getColumn(j).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.SINGLE_LINE);
					}
					if(j%gapColumn==count_mode||j%gapColumn==pcount_mode){ 
						kdtEntrys[i].getColumn(j).setEditor(getCellIntegerNumberEdit());
					}else{
						kdtEntrys[i].getColumn(j).setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
					}
					if(j%10==area_mode||j%10==count_mode||j%10==price_mode||j%10==pprice_mode ||j%10==amount_mode ||j%10==recover_mode){
						kdtEntrys[i].getColumn(j).getStyleAttributes().setLocked(true);
						kdtEntrys[i].getColumn(j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					}
				}else{
					if(j==5 || j==6 || j==precount_cell){ 
						kdtEntrys[i].getColumn(j).setEditor(getCellIntegerNumberEdit());
					}else{
						kdtEntrys[i].getColumn(j).setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
					}
					if((j>=0&&j<13)||j==preprice_cell){
						kdtEntrys[i].getColumn(j).getStyleAttributes().setLocked(true);
						kdtEntrys[i].getColumn(j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					}
				}
				if(j!=1&&j!=0&&j!=2)
					kdtEntrys[i].getColumn(j).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				
			}
			kDTabbedPane1.add(kdtEntrys[i], year+i+"年销售计划");
			kdtEntrys[i].addKDTEditListener(new KDTEditAdapter() {
				public void editStopped(KDTEditEvent e) {
					table_editStopped(e);
				}

			});
		}
	}

	public  KDTDefaultCellEditor getCellIntegerNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.INTEGER_TYPE);
        kdc.setPrecision(0);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	/**
	 * 表格数据编辑
	 * 
	 * */
	private void table_editStopped(KDTEditEvent e) {
		int planMonth = ((Integer)txtPlanMonth.getValue()).intValue();
		KDTable table = (KDTable) e.getSource();
    	int colIndex = e.getColIndex();
    	int rowIndex = e.getRowIndex();
    	IRow row = table.getRow(rowIndex);
    	//如果是预估面积,预估套数,预估均价,预估金额,预估回款发生改变
		if(colIndex==prearea_cell || colIndex==precount_cell 
				||colIndex==preprice_cell || colIndex==preamount_cell || colIndex==prerecover_cell ){
			setColValue(row,true,planMonth,colIndex);
		}
		else{
			int month = 0;
			if(colIndex%10 == parea_mode){
				month = (colIndex - parea_cell)/10;
			}
			else if(colIndex%10 == pcount_mode){
				month = (colIndex - pcount_cell)/10;
			}
			else if(colIndex%10 == pamount_mode){
				month = (colIndex - pamount_cell)/10;
			}
			else if(colIndex%10 == precover_mode){
				month = (colIndex - precover_cell)/10;
			}
			setColValue(row,false,month,colIndex);
		}
		addTotalValue(row);
		//计算总计行
		setMonthFootRow();
		for(int j=0;j<kdtEntrys.length;j++){
			if(kdtEntrys[j].getName().equals(table.getName())){
				setkdtProReferenceValue(kdtProReference.getRow(j),kdtEntrys[j]);
			}
		}
	}
	
	/**
	 * 设置编辑列对应的月列货值预估列的值
	 * @row 编辑行
	 * @isPrep 是否是本月预估值
	 * @planMonth 计划月份
	 * @colIndex 列号
	 * 
	 * */
	public void setColValue(IRow row,boolean isPrep,int planMonth,int colIndex){
		BigDecimal area = BigDecimal.ZERO;
		BigDecimal count = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal recover = BigDecimal.ZERO;
		//均价
		BigDecimal price = BigDecimal.ZERO;
    	if(isPrep){
    		area = row.getCell(prearea_cell).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(prearea_cell).getValue().toString());
    		count = row.getCell(precount_cell).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(precount_cell).getValue().toString());
    		amount = row.getCell(preamount_cell).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(preamount_cell).getValue().toString());
    		recover = row.getCell(prerecover_cell).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(prerecover_cell).getValue().toString());
    		//重新计算本月预估的数据
    		if(PlanIndexTypeEnum.PARKING_VALUE.equals(row.getCell(0))){
    			if(count.compareTo(BigDecimal.ZERO) != 0){
    				price = amount.divide(count,2,BigDecimal.ROUND_HALF_UP);
    			}
    		}
    		else{
    			if(area.compareTo(BigDecimal.ZERO) != 0){
    				price = amount.divide(area,2,BigDecimal.ROUND_HALF_UP);
    			}
    		}
    		row.getCell(preprice_cell).setValue(price);
    		//设置对应的月份的值
    		row.getCell(parea_cell+planMonth*gapColumn).setValue(area);
    		row.getCell(pcount_cell+planMonth*gapColumn).setValue(new Integer(count.intValue()));
    		row.getCell(pprice_cell+planMonth*gapColumn).setValue(price);
    		row.getCell(pamount_cell+planMonth*gapColumn).setValue(amount);
    		row.getCell(precover_cell+planMonth*gapColumn).setValue(recover);
    	}
    	else{
    		area = row.getCell(parea_cell+planMonth*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(parea_cell+planMonth*gapColumn).getValue().toString());
    		count = row.getCell(pcount_cell+planMonth*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(pcount_cell+planMonth*gapColumn).getValue().toString());
    		amount = row.getCell(pamount_cell+planMonth*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(pamount_cell+planMonth*gapColumn).getValue().toString());
    		recover = row.getCell(precover_cell+planMonth*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(precover_cell+planMonth*gapColumn).getValue().toString());
    		//重新编辑月的计划数据
    		if(PlanIndexTypeEnum.PARKING_VALUE.equals(row.getCell(0))){
    			if(count.compareTo(BigDecimal.ZERO) != 0){
    				price = amount.divide(count,2,BigDecimal.ROUND_HALF_UP);
    			}
    		}
    		else{
    			if(area.compareTo(BigDecimal.ZERO) != 0){
    				price = amount.divide(area,2,BigDecimal.ROUND_HALF_UP);
    			}
    		}
    		row.getCell(pprice_cell+planMonth*gapColumn).setValue(price);
    		//如果是当月，则进行反写，如果不是，则仅计算对应的月度计划
    		if(colIndex == parea_cell+planMonth*gapColumn || colIndex == pcount_cell+planMonth*gapColumn || colIndex == pamount_cell+planMonth*gapColumn
    				|| colIndex == precover_cell+planMonth*gapColumn){
    			row.getCell(prearea_cell).setValue(area); //预估面积
    			row.getCell(precount_cell).setValue(new Integer(count.intValue())); //预估套数
    			row.getCell(preprice_cell).setValue(price); //预估均价
    			row.getCell(preamount_cell).setValue(amount); //预估金额
    			row.getCell(prerecover_cell).setValue(recover); //预估回款
    		}
    	}		
	}
	
	/**
	 * 年初至今累计计划数=之前月份合计+当前月份预估+未来月份计划；
	 * 年初至今累计实际数=实际数据和；
	 * 
	 * @row 当前编辑行
	 * @isPrep 是否是本月预估编辑行
	 * 
	 * */
	public void addTotalValue(IRow row){
		int planYear = ((Integer)txtPlanYear.getValue()).intValue();
		int planMonth = ((Integer)txtPlanMonth.getValue()).intValue();
    	//年初到现在的实际数
		BigDecimal addarea = row.getCell(4).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(4).getValue().toString());
		BigDecimal addcount = row.getCell(6).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(6).getValue().toString());
		BigDecimal addamount = row.getCell(10).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(10).getValue().toString());
		BigDecimal addrecover = row.getCell(12).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(12).getValue().toString());
		
		BigDecimal lastArea = BigDecimal.ZERO;
		BigDecimal lastCount = BigDecimal.ZERO;
		BigDecimal lastAmount = BigDecimal.ZERO;
		BigDecimal lastRecover = BigDecimal.ZERO;
		if(planYear <= y){
			//年初至上月底实际销售面积
			lastArea = addarea.subtract(row.getCell(area_cell+m*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(area_cell+m*gapColumn).getValue().toString()));
			//年初至上月底实际销售套数
			lastCount = addcount.subtract(row.getCell(count_cell+m*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(count_cell+m*gapColumn).getValue().toString()));
			//年初至上月底实际销售金额
			lastAmount = addamount.subtract(row.getCell(amount_cell+m*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(amount_cell+m*gapColumn).getValue().toString()));
			//年初至上月底销售回款金额
			lastRecover = addrecover.subtract(row.getCell(recover_cell+m*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(recover_cell+m*gapColumn).getValue().toString()));
			
			/**
			 * 停车位均价 = 销售金额 / 套数
			 * 非停车位 = 销售金额 / 销售面积
			 * 
			 * 年初至今累计计划数=之前月份合计+当前月份预估+未来月份计划；
			 * m 为当前月份
			 * */
			//累计计划值
			for(int i = m;i<13;i++){
				lastArea = lastArea.add(row.getCell(parea_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(parea_cell+i*gapColumn).getValue().toString()));
				lastCount = lastCount.add(row.getCell(pcount_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(pcount_cell+i*gapColumn).getValue().toString()));
				lastAmount = lastAmount.add(row.getCell(pamount_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(pamount_cell+i*gapColumn).getValue().toString()));
				lastRecover = lastRecover.add(row.getCell(precover_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(precover_cell+i*gapColumn).getValue().toString()));
			}
		}
		else{
			/**
			 * 停车位均价 = 销售金额 / 套数
			 * 非停车位 = 销售金额 / 销售面积
			 * 
			 * 年初至今累计计划数=之前月份合计+当前月份预估+未来月份计划；
			 * m 为当前月份
			 * */
			//累计计划值
			for(int i = 1;i<13;i++){
				lastArea = lastArea.add(row.getCell(parea_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(parea_cell+i*gapColumn).getValue().toString()));
				lastCount = lastCount.add(row.getCell(pcount_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(pcount_cell+i*gapColumn).getValue().toString()));
				lastAmount = lastAmount.add(row.getCell(pamount_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(pamount_cell+i*gapColumn).getValue().toString()));
				lastRecover = lastRecover.add(row.getCell(precover_cell+i*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(precover_cell+i*gapColumn).getValue().toString()));
			}
		}
		
		//设置累计计划值
		row.getCell(3).setValue(lastArea);
		row.getCell(5).setValue(new Integer(lastCount.intValue()));
		row.getCell(9).setValue(lastAmount);
		row.getCell(11).setValue(lastRecover);
		if(PlanIndexTypeEnum.PARKING_VALUE.equals(row.getCell(0))){
			if(lastCount.compareTo(BigDecimal.ZERO) != 0){
				row.getCell(7).setValue(lastAmount.divide(lastCount,2,BigDecimal.ROUND_HALF_UP));
			}else{ //FIXME ADD BY YLW 20121129
				row.getCell(7).setValue(BigDecimal.ZERO);
			}
		}
		else{
			if(lastArea.compareTo(BigDecimal.ZERO) != 0){
				row.getCell(7).setValue(lastAmount.divide(lastArea,2,BigDecimal.ROUND_HALF_UP));
			}else{ //FIXME ADD BY YLW 20121129
				row.getCell(7).setValue(BigDecimal.ZERO);
			}
		}
	}
	
	/**
	 * 加载时，计算各产品构成合计行的均价
	 * */
	public void onLoadPrice(){
		for(int j=0;j<kdtEntrys.length;j++){
			setAddPrice(kdtEntrys[j]);
			setPrePrice(kdtEntrys[j]);
			setMonthPrice(kdtEntrys[j]);
		}
	}
	/**
	 * 合计行：计算累计货值的均价
	 * @param divideIndex 除数列
	 * @param dividedIndex 被除数列
	 * @param priceIndex 均价列
	 * */
	public void setAddPrice(KDTable table){
		for(int i = 0 ;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			if(row.getCell(1).getValue() instanceof String){
				PlanIndexTypeEnum type = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();//产品构成
				Number pdivideValue =(Number)row.getCell(paddamount_cell).getValue(); //累计计划销售金额
				Number divideValue =(Number)row.getCell(addamount_cell).getValue(); //累计实际销售金额
				if(PlanIndexTypeEnum.parking.equals(type)){//车位  均价=金额/套数
					Number pdividedValue =  (Number)row.getCell(paddcount_cell).getValue(); 
					if(pdivideValue!=null&&pdividedValue!=null && pdividedValue.doubleValue() != 0){
						row.getCell(paddprice_cell).setValue(FDCHelper.divide(pdivideValue,pdividedValue ));
					}else{ //FIXME ADD BY YLW 20121129
						row.getCell(paddprice_cell).setValue(BigDecimal.ZERO);
					}
					Number dividedValue =  (Number)row.getCell(addcount_cell).getValue();
					if(divideValue!=null&&dividedValue!=null && dividedValue.doubleValue() != 0){
						row.getCell(addprice_cell).setValue(FDCHelper.divide(divideValue,dividedValue ));
					}else{ //FIXME ADD BY YLW 20121129
						row.getCell(addprice_cell).setValue(BigDecimal.ZERO);
					}
				}else{
					Number pdividedValue =  (Number)row.getCell(paddarea_cell).getValue(); 
					if(pdivideValue!=null&&pdividedValue!=null && pdividedValue.doubleValue() != 0){
						row.getCell(paddprice_cell).setValue(FDCHelper.divide(pdivideValue,pdividedValue ));
					}else{ //FIXME ADD BY YLW 20121129
						row.getCell(paddprice_cell).setValue(BigDecimal.ZERO);
					}
					Number dividedValue =  (Number)row.getCell(addarea_cell).getValue();
					if(divideValue!=null&&dividedValue!=null && dividedValue.doubleValue() != 0){
						row.getCell(addprice_cell).setValue(FDCHelper.divide(divideValue,dividedValue ));
					}else{ //FIXME ADD BY YLW 20121129
						row.getCell(addprice_cell).setValue(BigDecimal.ZERO);
					}
				}
			}
		}
	}
	/**
	 * 合计行：计算预估货值的均价
	 * @param divideIndex 除数列
	 * @param dividedIndex 被除数列
	 * @param priceIndex 单价列
	 * */
	public void setPrePrice(KDTable table){
		for(int i = 0 ;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			if(row.getCell(1).getValue() instanceof String){
				PlanIndexTypeEnum type = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();//产品构成
				Number divideValue =(Number)row.getCell(preamount_cell).getValue();
				if(PlanIndexTypeEnum.parking.equals(type)){//车位  均价=金额/套数
					Number dividedValue =  (Number)row.getCell(precount_cell).getValue();
					if(divideValue!=null&&dividedValue!=null && dividedValue.doubleValue() != 0){
						row.getCell(preprice_cell).setValue(FDCHelper.divide(divideValue,dividedValue ));
					}else{ //FIXME ADD BY YLW 20121129
						row.getCell(preprice_cell).setValue(BigDecimal.ZERO);
					}
				}else{
					Number dividedValue =  (Number)row.getCell(prearea_cell).getValue();
					if(divideValue!=null&&dividedValue!=null && dividedValue.doubleValue() != 0){
						row.getCell(preprice_cell).setValue(FDCHelper.divide(divideValue,dividedValue ));
					}else{ //FIXME ADD BY YLW 20121129
						row.getCell(preprice_cell).setValue(BigDecimal.ZERO);
					}
				}
			}
		}
	}
	/**
	 * 合计行：计算月度货值的均价
	 * @param divideIndex 除数列
	 * @param dividedIndex 被除数列
	 * @param priceIndex 均价列
	 * */
	public void setMonthPrice(KDTable table){
		for(int i = 0 ;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			if(row.getCell(1).getValue() instanceof String){
				PlanIndexTypeEnum type = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();//产品构成
				if(PlanIndexTypeEnum.parking.equals(type)){//车位  均价=金额/套数
					for(int k=1;k<13;k++){
						// 计划均价
						Number pdivideValue =(Number)row.getCell(pamount_cell+k*gapColumn).getValue();
						Number pdividedValue =  (Number)row.getCell(pcount_cell+k*gapColumn).getValue(); 
						if(pdivideValue!=null&&pdividedValue!=null && pdividedValue.doubleValue() != 0){
							row.getCell(pprice_cell+k*gapColumn).setValue(FDCHelper.divide(pdivideValue,pdividedValue ));
						}else{ //FIXME ADD BY YLW 20121129
							row.getCell(pprice_cell+k*gapColumn).setValue(BigDecimal.ZERO);
						}
						// 实际均价
						Number divideValue =(Number)row.getCell(amount_cell+k*gapColumn).getValue();
						Number dividedValue =  (Number)row.getCell(count_cell+k*gapColumn).getValue(); 
						if(divideValue!=null && dividedValue!=null && dividedValue.doubleValue() !=0){
							row.getCell(price_cell+k*gapColumn).setValue(FDCHelper.divide(divideValue,dividedValue ));
						}else{ //FIXME ADD BY YLW 20121129
							row.getCell(price_cell+k*gapColumn).setValue(BigDecimal.ZERO);
						}
					}
				}
				else{
					for(int k=1;k<13;k++){
						// 计划均价
						Number pdivideValue =(Number)row.getCell(pamount_cell+k*gapColumn).getValue();
						Number pdividedValue =  (Number)row.getCell(parea_cell+k*gapColumn).getValue(); 
						if(pdivideValue!=null && pdividedValue!=null && pdividedValue.doubleValue() != 0){
							row.getCell(pprice_cell+k*gapColumn).setValue(FDCHelper.divide(pdivideValue,pdividedValue ));
						}else{ //FIXME ADD BY YLW 20121129
							row.getCell(pprice_cell+k*gapColumn).setValue(BigDecimal.ZERO);
						}
						// 实际均价
						Number divideValue =(Number)row.getCell(amount_cell+k*gapColumn).getValue();
						Number dividedValue =  (Number)row.getCell(area_cell+k*gapColumn).getValue(); 
						if(divideValue!=null&&dividedValue!=null && dividedValue.doubleValue() !=0){
							row.getCell(price_cell+k*gapColumn).setValue(FDCHelper.divide(divideValue,dividedValue ));
						}else{ //FIXME ADD BY YLW 20121129
							row.getCell(price_cell+k*gapColumn).setValue(BigDecimal.ZERO);
						}
					}
				}
			}
		}
	}
	
	protected void txtPlanYear_stateChanged(ChangeEvent e) throws Exception {
		if(sellProjectID!=null){
			int planYear = ((Integer)txtPlanYear.getValue()).intValue();
			if(planYear>year+cycle-1){
				txtPlanYear.setValue(Integer.valueOf(year+cycle-1));
			}
			if(planYear<year){
				txtPlanYear.setValue(Integer.valueOf(year));
			}
			if(planYear < y){
				txtPlanYear.setValue(Integer.valueOf(y));
			}
			else{
				setKDTEntrysLock();
			}
			if(planYear > y){
				txtPlanMonth.setValue(Integer.valueOf(1));
			}
			if(planYear == y){
				txtPlanMonth.setValue(Integer.valueOf(m));
			}
//			if(kdtEntrys!=null){
//				setYearMonthControl();
//			}
			int setYear = ((Integer)txtPlanYear.getValue()).intValue();
			int setMonth = ((Integer)txtPlanMonth.getValue()).intValue();
			setPreValue(setYear,setMonth);
		}
	}
	
	protected void txtPlanMonth_stateChanged(ChangeEvent e) throws Exception {
		if(sellProjectID!=null){
			int planMonth = ((Integer)txtPlanMonth.getValue()).intValue();
			int planYear = ((Integer)txtPlanYear.getValue()).intValue();
			if(planMonth>12){
				txtPlanMonth.setValue(Integer.valueOf(12));
			}
			if(planMonth<1){
				txtPlanMonth.setValue(Integer.valueOf(1));
			}
			if(planYear <= y && planMonth < m){
				txtPlanYear.setValue(Integer.valueOf(y));
				txtPlanMonth.setValue(Integer.valueOf(m));
			}
			else{
				setKDTEntrysLock();
			}
//			if(kdtEntrys!=null){
//				setYearMonthControl();
//			}
			int setYear = ((Integer)txtPlanYear.getValue()).intValue();
			int setMonth = ((Integer)txtPlanMonth.getValue()).intValue();
			setPreValue(setYear,setMonth);
		}
	}
	
	/**
	 * 将对应的值写入本月预估数据中 wyh
	 * */
	public void setPreValue(int year,int month){
		for(int i=0;i<kdtEntrys.length;i++){
			if(kdtEntrys[i].getToolTipText().equals(String.valueOf(year))){
				for(int j=0;j<kdtEntrys[i].getRowCount();j++){
					IRow row = kdtEntrys[i].getRow(j);
					row.getCell(prearea_cell).setValue(row.getCell(parea_cell+month*gapColumn).getValue());
					row.getCell(precount_cell).setValue(row.getCell(pcount_cell+month*gapColumn).getValue());
					row.getCell(preprice_cell).setValue(row.getCell(pprice_cell+month*gapColumn).getValue());
					row.getCell(preamount_cell).setValue(row.getCell(pamount_cell+month*gapColumn).getValue());
					row.getCell(prerecover_cell).setValue(row.getCell(precover_cell+month*gapColumn).getValue());
				}
			}
		}
	}
	
	/**
	 * 计划只能做当前月之后的计划，之前完成的计划不可进行变动。
	 * */
	public void setYearMonthControl(){
		int year = 0;
		int month = 0;
		if(txtPlanYear.getValue() instanceof Integer)
			year = ((Integer)txtPlanYear.getValue()).intValue();
		if(txtPlanYear.getValue() instanceof BigDecimal)
			year = ((BigDecimal)txtPlanYear.getValue()).intValue();
		if(txtPlanMonth.getValue() instanceof Integer)
			month = ((Integer)txtPlanMonth.getValue()).intValue();
		if(txtPlanMonth.getValue() instanceof BigDecimal)
			month = ((BigDecimal)txtPlanMonth.getValue()).intValue(); 
		valueBreakInfo = getValueBreakInfo();//最新年度分解
        valueVersoin = valueBreakInfo.getVersion();
		MonthPlanInfo info = getMonthPlanInfo(valueVersoin,sellProjectID);//最新通过审批的销售月度计划
		if(info!=null && !info.getId().equals(editData.getId()) && month!=0){
			if(info.getPlanYear()>year 
					|| (info.getPlanYear()==year && info.getPlanMonth()>month)){
				txtPlanMonth.setValue(new Integer(info.getPlanMonth()));
				txtPlanYear.setValue(new Integer(info.getPlanYear()));
				MsgBox.showInfo("当前年月或之后已经生产计划，不能在此年月新增销售月度计划");
				SysUtil.abort();
			}else{
				if(info.getPlanYear()==year && info.getPlanMonth()==month){//当前月版本
					
					MonthPlanCollection MonthPlanColl = new MonthPlanCollection();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectID));
					filter.getFilterItems().add(new FilterItemInfo("versoin",valueVersoin+"%",CompareType.LIKE));
					view.setFilter(filter);
					try {
						MonthPlanColl = MonthPlanFactory.getRemoteInstance().getMonthPlanCollection(view);
					} catch (BOSException e) {
						e.printStackTrace();
					}
					if(MonthPlanColl.size()==0){
						txtVersoin.setText(valueVersoin+".0");
					}else{
						txtVersoin.setText(getVersion(monthPlanInfo.getVersoin()));
					}
				}
				else{
					txtVersoin.setText(valueVersoin+".0");
				}
			}
		}
	}
	/**
	 * 根据当前年，月锁定表格，根据实际日期来锁表格
	 * */
	public void setKDTEntrysLock(){
		int planMonth = ((Integer)txtPlanMonth.getValue()).intValue();
//		int year = ((Integer)txtPlanYear.getValue()).intValue();
		//wyh
		int year = y;
		int index = cycle;
		if(cycle!=0){
			for(int i=0;i<index;i++){
				if(kdtEntrys.length>0 && kdtEntrys.length>=i && kdtEntrys[i]!=null){
					if(Integer.parseInt(kdtEntrys[i].getToolTipText())<year){
						for(int k=1;k<13;k++){
							kdtEntrys[i].getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(prearea_cell).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(precount_cell).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(preprice_cell).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(preamount_cell).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(prerecover_cell).getStyleAttributes().setLocked(true);
							
							kdtEntrys[i].getColumn(prearea_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(precount_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(preprice_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(preamount_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(prerecover_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							
							
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setLocked(true);
						}
					}else if(Integer.parseInt(kdtEntrys[i].getToolTipText())==year){
						kdtEntrys[i].getStyleAttributes().setLocked(true);
						kdtEntrys[i].getColumn(prearea_cell).getStyleAttributes().setLocked(false);
						kdtEntrys[i].getColumn(precount_cell).getStyleAttributes().setLocked(false);
						kdtEntrys[i].getColumn(preprice_cell).getStyleAttributes().setLocked(true);
						kdtEntrys[i].getColumn(preamount_cell).getStyleAttributes().setLocked(false);
						kdtEntrys[i].getColumn(prerecover_cell).getStyleAttributes().setLocked(false);
						
						kdtEntrys[i].getColumn(prearea_cell).getStyleAttributes().setBackground(Color.WHITE);
						kdtEntrys[i].getColumn(precount_cell).getStyleAttributes().setBackground(Color.WHITE);
						kdtEntrys[i].getColumn(preprice_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
						kdtEntrys[i].getColumn(preamount_cell).getStyleAttributes().setBackground(Color.WHITE);
						kdtEntrys[i].getColumn(prerecover_cell).getStyleAttributes().setBackground(Color.WHITE);
						
						for(int k=1;k<planMonth;k++){
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setLocked(true);
							
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
						}
						for(int k=planMonth;k<13;k++){
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setLocked(false);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setLocked(false);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setLocked(false);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setLocked(false);
						}
					}else{
						kdtEntrys[i].getStyleAttributes().setLocked(true);
						kdtEntrys[i].getColumn(prearea_cell).getStyleAttributes().setLocked(false);
						kdtEntrys[i].getColumn(precount_cell).getStyleAttributes().setLocked(false);
						kdtEntrys[i].getColumn(preprice_cell).getStyleAttributes().setLocked(true);
						kdtEntrys[i].getColumn(preamount_cell).getStyleAttributes().setLocked(false);
						kdtEntrys[i].getColumn(prerecover_cell).getStyleAttributes().setLocked(false);
						
						kdtEntrys[i].getColumn(prearea_cell).getStyleAttributes().setBackground(Color.WHITE);
						kdtEntrys[i].getColumn(precount_cell).getStyleAttributes().setBackground(Color.WHITE);
						kdtEntrys[i].getColumn(preprice_cell).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
						kdtEntrys[i].getColumn(preamount_cell).getStyleAttributes().setBackground(Color.WHITE);
						kdtEntrys[i].getColumn(prerecover_cell).getStyleAttributes().setBackground(Color.WHITE);
						
						for(int k=1;k<13;k++){
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setBackground(Color.WHITE);
							
							kdtEntrys[i].getColumn(parea_cell+k*gapColumn).getStyleAttributes().setLocked(false);
							kdtEntrys[i].getColumn(pcount_cell+k*gapColumn).getStyleAttributes().setLocked(false);
							kdtEntrys[i].getColumn(pamount_cell+k*gapColumn).getStyleAttributes().setLocked(false);
							kdtEntrys[i].getColumn(precover_cell+k*gapColumn).getStyleAttributes().setLocked(false);
						}
					}
				}
			}
			for(int n = 0; n<kdtEntrys.length ;n++){ // FDCHelper.KDTABLE_TOTAL_BG_COLOR
	    		for(int i=0; i<kdtEntrys[n].getRowCount();i++){
	    			IRow row = kdtEntrys[n].getRow(i);
	    			if(row.getCell(1).getValue() instanceof String || row.getCell(2).getValue() != null){
	    				String hj = "";
	    				String xj = "";
	    				if(row.getCell(1).getValue() instanceof String){
	    					hj = (String)row.getCell(1).getValue();
	    				}
	    				if(row.getCell(2).getValue() != null){
	    					xj = row.getCell(2).getValue().toString();
	    				}
	        			if("合计".equals(hj) || "小计".equals(xj)){
	        				for(int k=1;k<kdtEntrys[n].getColumnCount();k++){
	        					row.getCell(k).getStyleAttributes().setLocked(true);
	        					row.getCell(k).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	        					row.getCell(parea_cell+k*gapColumn).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	        					row.getCell(pcount_cell+k*gapColumn).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	        					row.getCell(pamount_cell+k*gapColumn).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	        					row.getCell(precover_cell+k*gapColumn).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	        					
//	        					row.getCell(parea_cell+k*gapColumn).getStyleAttributes().setLocked(true);
//	        					row.getCell(pcount_cell+k*gapColumn).getStyleAttributes().setLocked(true);
//	        					row.getCell(pamount_cell+k*gapColumn).getStyleAttributes().setLocked(true);
//	        					row.getCell(precover_cell+k*gapColumn).getStyleAttributes().setLocked(true);
	        				}
	        			}
	    			}
	    		}
	    	}
		}
	}
	
    public void storeFields()
    {
    	editData.setStartYear(year);
    	editData.setCycle(cycle);
    	editData.getEntrys().clear();
    	editData.getProReference().clear();
    	for(int i =0; i<kdtProReference.getRowCount();i++){
    		ProReferenceInfo proReferenceInfo = new ProReferenceInfo();
    		IRow row = kdtProReference.getRow(i);
    		if(row.getCell(0).getValue()!=null)
    			proReferenceInfo.setYear(Integer.parseInt(row.getCell("year").getValue().toString()));
    		proReferenceInfo.setTargetValue((BigDecimal)row.getCell("targetValue").getValue());
    		proReferenceInfo.setYearValue((BigDecimal)row.getCell("yearValue").getValue());
    		proReferenceInfo.setPlanAmount((BigDecimal)row.getCell("planAmount").getValue());
    		proReferenceInfo.setActulAmount((BigDecimal)row.getCell("actulAmount").getValue());
    		editData.getProReference().add(proReferenceInfo);
    	}
    	MonthPlanEntryInfo monthPlanEntryInfo = null;
    	for(int i=0;i<kdtEntrys.length;i++){
    		int n = 0;
    		for(int j=0;j<kdtEntrys[i].getRowCount();j++){
    			IRow row = kdtEntrys[i].getRow(j);
    			if(row.getCell(1).getValue() instanceof String){
    				continue;
    			}
    			if(row.getCell(2).getValue() != null){
			    	if(row.getCell(2).getValue() instanceof String && row.getCell(2).getValue().equals("小计")){
			    		continue;
			    	}
			    }
    			for(int k=0;k<14;k++){
    				monthPlanEntryInfo = new MonthPlanEntryInfo();
    				n++;
    			    monthPlanEntryInfo.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
    			    monthPlanEntryInfo.setProductType((ProductTypeInfo)row.getCell(1).getValue());
    			    if(row.getCell(2).getValue() instanceof AreaSetInfo){
    			    	monthPlanEntryInfo.setNewAreaRange((AreaSetInfo)row.getCell(2).getValue());
	    			}
    			    if(row.getCell(2).getValue() != null){
    			    	if(row.getCell(2).getValue() instanceof String){
    			    		monthPlanEntryInfo.setAreaRange(row.getCell(2).getValue().toString());
    			    	}
    			    }
    			    if(k==1){ //本月预估货值
    			    	monthPlanEntryInfo.setAreaPlan((BigDecimal)row.getCell(3+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setAreaActual((BigDecimal)row.getCell(3+k*gapColumn).getValue());
        			    if(row.getCell(4+k*gapColumn).getValue()!=null)
        			    {
        			    	monthPlanEntryInfo.setPloidyPlan(((Integer)row.getCell(4+k*gapColumn).getValue()).intValue());
        			    	monthPlanEntryInfo.setPloidyActual(((Integer)row.getCell(4+k*gapColumn).getValue()).intValue());
        			    }
        			    monthPlanEntryInfo.setPricePlan((BigDecimal)row.getCell(5+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setPriceActual((BigDecimal)row.getCell(5+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setAmountPlan((BigDecimal)row.getCell(6+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setAmountActual((BigDecimal)row.getCell(6+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setRecoverPlan((BigDecimal)row.getCell(7+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setRecoverActual((BigDecimal)row.getCell(7+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setMonth(13);
    			    }else if(k==0){
    			    	monthPlanEntryInfo.setAreaPlan((BigDecimal)row.getCell(3+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setAreaActual((BigDecimal)row.getCell(4+k*gapColumn).getValue());
        			    if(row.getCell(5+k*gapColumn).getValue()!=null)
        			    {
        			    	monthPlanEntryInfo.setPloidyPlan(((Integer)row.getCell(5+k*gapColumn).getValue()).intValue());
        			    }
        			    if(row.getCell(6+k*gapColumn).getValue()!=null)
        			    {
        			    	monthPlanEntryInfo.setPloidyActual(((Integer)row.getCell(6+k*gapColumn).getValue()).intValue());
        			    }
        			    monthPlanEntryInfo.setPricePlan((BigDecimal)row.getCell(7+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setPriceActual((BigDecimal)row.getCell(8+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setAmountPlan((BigDecimal)row.getCell(9+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setAmountActual((BigDecimal)row.getCell(10+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setRecoverPlan((BigDecimal)row.getCell(11+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setRecoverActual((BigDecimal)row.getCell(12+k*gapColumn).getValue());
        			    monthPlanEntryInfo.setMonth(k);
    			    }else {
    			    	monthPlanEntryInfo.setAreaPlan((BigDecimal)row.getCell(parea_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setAreaActual((BigDecimal)row.getCell(area_cell+(k-1)*gapColumn).getValue());
        			    if(row.getCell(pcount_cell+(k-1)*gapColumn).getValue()!=null)
        			    {
        			    	monthPlanEntryInfo.setPloidyPlan(((Integer)row.getCell(pcount_cell+(k-1)*gapColumn).getValue()).intValue());
        			    }
        			    if(row.getCell(count_cell+(k-1)*gapColumn).getValue()!=null)
        			    {
        			    	monthPlanEntryInfo.setPloidyActual(((Integer)row.getCell(count_cell+(k-1)*gapColumn).getValue()).intValue());
        			    }
        			    monthPlanEntryInfo.setPricePlan((BigDecimal)row.getCell(pprice_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setPriceActual((BigDecimal)row.getCell(price_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setAmountPlan((BigDecimal)row.getCell(pamount_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setAmountActual((BigDecimal)row.getCell(amount_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setRecoverPlan((BigDecimal)row.getCell(precover_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setRecoverActual((BigDecimal)row.getCell(recover_cell+(k-1)*gapColumn).getValue());
        			    monthPlanEntryInfo.setMonth(k-1);
    			    }
    			    monthPlanEntryInfo.setYear(year+i);
    			    monthPlanEntryInfo.setSeq(n);
    			    editData.getEntrys().add(monthPlanEntryInfo);
    			}
    		}
    		
    	}
    	super.storeFields();
        
    }
    
    public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		setAuditButtonStatus(this.getOprtState());
		
    	kdtProReference.checkParsed();
    	kdtProReference.removeRows();
    	if(editData.getSellProject()!=null)
    		sellProjectID = editData.getSellProject().getId().toString();
    	for(int i = 0;i<editData.getProReference().size();i++){
    		ProReferenceInfo proReferenceInfo = editData.getProReference().get(i);
    		IRow row = kdtProReference.addRow();
    		row.getCell("year").setValue(new Integer(proReferenceInfo.getYear()));
    		row.getCell("targetValue").setValue(proReferenceInfo.getTargetValue());
    		row.getCell("yearValue").setValue(proReferenceInfo.getYearValue());
    		row.getCell("planAmount").setValue(proReferenceInfo.getPlanAmount());
    		row.getCell("actulAmount").setValue(proReferenceInfo.getActulAmount());
    		row.getCell("finishRate").setValue(proReferenceInfo.getTargetValue()==null||proReferenceInfo.getActulAmount()==null||proReferenceInfo.getTargetValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO
    				:proReferenceInfo.getActulAmount().multiply(new BigDecimal(100)).divide(proReferenceInfo.getTargetValue(),2,2));
    		if(proReferenceInfo.getYearValue()!=null && proReferenceInfo.getActulAmount()!=null)
    			row.getCell("unfinished").setValue(proReferenceInfo.getYearValue().subtract(proReferenceInfo.getActulAmount()));
    	}
		this.txtPlanYear.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtPlanYear,""));
		this.txtPlanMonth.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtPlanMonth,""));
		txtPlanYear.setValue(new Integer(editData.getPlanYear()));
		txtPlanMonth.setValue(new Integer(editData.getPlanMonth()));
		
    	try {
			loadEntry();
			//FIXME onload中已经调用 setKDTEntrysLock()wyh
			//setKDTEntrysLock();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	attachListeners();
    }
    
    public void loadEntry() throws EASBizException, BOSException{
    	//初始化table
    	int startYear = editData.getStartYear();
    	int cycle = editData.getCycle();
    	initTable(startYear,cycle);   	
    	//初始化table中的值
    	MonthPlanEntryCollection monthPlanEntryColl = editData.getEntrys();
    	CRMHelper.sortCollection(monthPlanEntryColl, "seq", true);
    	
    	IRow row = null;
    	for(int i=0;i<monthPlanEntryColl.size();i++){
    		MonthPlanEntryInfo monthPlanEntryInfo = monthPlanEntryColl.get(i);
    		for(int j=0;j<editData.getCycle();j++){
    			if(monthPlanEntryInfo.getYear()==startYear+j){
					if(monthPlanEntryInfo.getMonth()==0){
						row = kdtEntrys[j].addRow();
						row.getCell(0).setValue((PlanIndexTypeEnum)monthPlanEntryInfo.getType());
						if(monthPlanEntryInfo.getProductType()!=null){
							String productTypeID =monthPlanEntryInfo.getProductType().getId().toString();
							ProductTypeInfo productTypeInfo = ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productTypeID));
							row.getCell(1).setValue(productTypeInfo);
						}
						if(monthPlanEntryInfo.getNewAreaRange() != null){
							String newAreaId = monthPlanEntryInfo.getNewAreaRange().getId().toString();
							AreaSetInfo newArea = AreaSetFactory.getRemoteInstance().getAreaSetInfo(new ObjectUuidPK(newAreaId));
							row.getCell(2).setValue(newArea);
						}
						
						row.getCell(3).setValue((BigDecimal)monthPlanEntryInfo.getAreaPlan());
						row.getCell(4).setValue((BigDecimal)monthPlanEntryInfo.getAreaActual());
						row.getCell(5).setValue(new Integer(monthPlanEntryInfo.getPloidyPlan()));
						row.getCell(6).setValue(new Integer(monthPlanEntryInfo.getPloidyActual()));
						row.getCell(7).setValue((BigDecimal)monthPlanEntryInfo.getPricePlan());
						row.getCell(8).setValue((BigDecimal)monthPlanEntryInfo.getPriceActual());
						row.getCell(9).setValue((BigDecimal)monthPlanEntryInfo.getAmountPlan());
						row.getCell(10).setValue((BigDecimal)monthPlanEntryInfo.getAmountActual());
						row.getCell(11).setValue((BigDecimal)monthPlanEntryInfo.getRecoverPlan());
						row.getCell(12).setValue((BigDecimal)monthPlanEntryInfo.getRecoverActual());
					}
					if(monthPlanEntryInfo.getMonth()==13){ //本月预估货值
						row.getCell(prearea_cell).setValue((BigDecimal)monthPlanEntryInfo.getAreaActual());
						row.getCell(precount_cell).setValue(new Integer(monthPlanEntryInfo.getPloidyActual()));
						row.getCell(preprice_cell).setValue(monthPlanEntryInfo.getPriceActual());
						row.getCell(preamount_cell).setValue(monthPlanEntryInfo.getAmountActual());
						row.getCell(prerecover_cell).setValue(monthPlanEntryInfo.getRecoverActual());
					}
    				for(int k=1;k<13;k++){
    					if(monthPlanEntryInfo.getMonth()==k){
							row.getCell(parea_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getAreaPlan());
							row.getCell(area_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getAreaActual());
							row.getCell(pcount_cell+k*gapColumn).setValue(new Integer(monthPlanEntryInfo.getPloidyPlan()));
							row.getCell(count_cell+k*gapColumn).setValue(new Integer(monthPlanEntryInfo.getPloidyActual()));
							row.getCell(pprice_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getPricePlan());
							row.getCell(price_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getPriceActual());
							row.getCell(pamount_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getAmountPlan());
							row.getCell(amount_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getAmountActual());
							row.getCell(precover_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getRecoverPlan());
							row.getCell(recover_cell+k*gapColumn).setValue((BigDecimal)monthPlanEntryInfo.getRecoverActual());
    					}
				   }
    			}
    		}
    	}

        for(int n = 0; n<kdtEntrys.length;n++){
        	TableUtils.setTotalRow2(kdtEntrys[n],false); 
        }
        setMonthFootRow();
    }
    
    public void loadFactValue() throws EASBizException, BOSException, SQLException{
    	int year = ((Integer)txtPlanYear.getValue()).intValue();
    	int emonth = ((Integer)txtPlanMonth.getValue()).intValue();
    	MonthPlanEntryInfo mpeInfo = new MonthPlanEntryInfo();
    	HashMap map = new HashMap();
    	
    	for(int i=0;i<kdtEntrys.length;i++){
    		String cy = kdtEntrys[i].getToolTipText();
    		String sql = this.getSql(kdtEntrys[i]);
    		map.put("sql",sql );
    		IRowSet set = MonthFactValueFacadeFactory.getRemoteInstance().getFactValue(map);
    		
    		while(set.next()){
    			String type = set.getString("types");//产品构成
    			String productType = set.getString("typename");//业态
    			String areaSet = set.getString("areaSet");
    			int month = Integer.parseInt(set.getString("busDate").substring(4,6));
    			mpeInfo.setAreaActual(set.getBigDecimal("area"));//面积实际数
				mpeInfo.setPloidyActual(set.getInt("count"));//套数实际数
				mpeInfo.setPriceActual(set.getBigDecimal("unitprice"));//均价实际数
				mpeInfo.setAmountActual(set.getBigDecimal("amount"));//金额实际数
				mpeInfo.setRecoverActual(set.getBigDecimal("recamount"));//实际回款
				for(int j=0;j<kdtEntrys[i].getRowCount();j++){
	    			IRow row = kdtEntrys[i].getRow(j);
					
					BigDecimal val_1 = new BigDecimal(row.getCell(4).getValue()==null?"0":row.getCell(4).getValue().toString());//年初累计至今实际面积
        			int val_2 = Integer.parseInt(row.getCell(6).getValue()==null?"0":row.getCell(6).getValue().toString());//年初累计至今实际套数
        			BigDecimal val_3 = new BigDecimal(row.getCell(10).getValue()==null?"0":row.getCell(4).getValue().toString());//年初累计至今实际销售金额
        			BigDecimal val_4 = new BigDecimal(row.getCell(12).getValue()==null?"0":row.getCell(4).getValue().toString());//年初累计至今实际回款金额
					
	        		if(row.getCell(0).getValue().equals(PlanIndexTypeEnum.house) ){//住宅 instanceof AreaSetInfo
	        			if(row.getCell(0).getValue().toString().equals(type) && ((ProductTypeInfo)row.getCell(1).getValue()).getName().equals(productType)
	        					&& ((AreaSetInfo)row.getCell(2).getValue()).getDescription().equals(areaSet)){
		    				row.getCell(area_cell+month*gapColumn).setValue(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAreaActual().setScale(2, BigDecimal.ROUND_HALF_UP));
	        				row.getCell(count_cell+month*gapColumn).setValue(new Integer(mpeInfo.getPloidyActual()));
	        				row.getCell(price_cell+month*gapColumn).setValue(mpeInfo.getPriceActual()==null?BigDecimal.ZERO:mpeInfo.getPriceActual().setScale(2, BigDecimal.ROUND_HALF_UP));
	        				row.getCell(amount_cell+month*gapColumn).setValue(mpeInfo.getAmountActual()==null?BigDecimal.ZERO:mpeInfo.getAmountActual().setScale(2, BigDecimal.ROUND_HALF_UP));
	        				row.getCell(recover_cell+month*gapColumn).setValue(mpeInfo.getRecoverActual()==null?BigDecimal.ZERO:mpeInfo.getRecoverActual().setScale(2, BigDecimal.ROUND_HALF_UP));
			        		if(month==emonth-1){
		    					row.getCell(prearea_cell).setValue(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAreaActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估回款金额
		            			row.getCell(precount_cell).setValue(new Integer(mpeInfo.getPloidyActual()));//本月预估套数
		            			row.getCell(preprice_cell).setValue(mpeInfo.getPriceActual()==null?BigDecimal.ZERO:mpeInfo.getPriceActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估均价
		            			row.getCell(preamount_cell).setValue(mpeInfo.getAmountActual()==null?BigDecimal.ZERO:mpeInfo.getAmountActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估销售金额
		            			row.getCell(prerecover_cell).setValue(mpeInfo.getRecoverActual()==null?BigDecimal.ZERO:mpeInfo.getRecoverActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估回款金额
		    				}
			        		//年初累计至今实际面积
			        		row.getCell(4).setValue(val_1.add(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAreaActual()).setScale(2, BigDecimal.ROUND_HALF_UP));
			        		//年初累计至今实际套数
			        		row.getCell(6).setValue(new Integer(val_2 + mpeInfo.getPloidyActual()));
			        		//年初累计至今实际销售金额
			        		row.getCell(10).setValue(val_3.add(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAmountActual()).setScale(2, BigDecimal.ROUND_HALF_UP));
			        		//年初累计至今实际回款金额
			        		row.getCell(12).setValue(val_4.add(mpeInfo.getRecoverActual()==null?BigDecimal.ZERO:mpeInfo.getRecoverActual()).setScale(2, BigDecimal.ROUND_HALF_UP));
                			if(new BigDecimal(row.getCell(4).getValue().toString()).compareTo(BigDecimal.ZERO) != 0){
                				row.getCell(8).setValue(new BigDecimal(row.getCell(10).getValue().toString()).divide(new BigDecimal(row.getCell(4).getValue().toString()),2,BigDecimal.ROUND_HALF_UP));//年初累计至今实际均价
                			}else{ //FIXME ADD BY YLW 20121129
                				row.getCell(8).setValue(BigDecimal.ZERO);
                			}
		    			}
	        		}
	        		else{//商业、车位、公共配套建筑，其中车位均价为 金额/套数
	        			if(row.getCell(0).getValue().equals(type) && ((ProductTypeInfo)row.getCell(1).getValue()).getName().equals(productType)){
	        				row.getCell(area_cell+month*gapColumn).setValue(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAreaActual().setScale(2, BigDecimal.ROUND_HALF_UP));
	        				row.getCell(count_cell+month*gapColumn).setValue(new Integer(mpeInfo.getPloidyActual()));
	        				row.getCell(price_cell+month*gapColumn).setValue(mpeInfo.getPriceActual()==null?BigDecimal.ZERO:mpeInfo.getPriceActual().setScale(2, BigDecimal.ROUND_HALF_UP));
	        				row.getCell(amount_cell+month*gapColumn).setValue(mpeInfo.getAmountActual()==null?BigDecimal.ZERO:mpeInfo.getAmountActual().setScale(2, BigDecimal.ROUND_HALF_UP));
	        				row.getCell(recover_cell+month*gapColumn).setValue(mpeInfo.getRecoverActual()==null?BigDecimal.ZERO:mpeInfo.getRecoverActual().setScale(2, BigDecimal.ROUND_HALF_UP));
			        		if(month==emonth-1){
		    					row.getCell(prearea_cell).setValue(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAreaActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估回款金额
		            			row.getCell(precount_cell).setValue(new Integer(mpeInfo.getPloidyActual()));//本月预估套数
		            			row.getCell(preprice_cell).setValue(mpeInfo.getPriceActual()==null?BigDecimal.ZERO:mpeInfo.getPriceActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估均价
		            			row.getCell(preamount_cell).setValue(mpeInfo.getAmountActual()==null?BigDecimal.ZERO:mpeInfo.getAmountActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估销售金额
		            			row.getCell(prerecover_cell).setValue(mpeInfo.getRecoverActual()==null?BigDecimal.ZERO:mpeInfo.getRecoverActual().setScale(2, BigDecimal.ROUND_HALF_UP));//本月预估回款金额
		    				}
			        		row.getCell(4).setValue(val_1.add(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAreaActual()).setScale(2, BigDecimal.ROUND_HALF_UP));//年初累计至今实际面积
                			row.getCell(6).setValue(new Integer(val_2 + mpeInfo.getPloidyActual()));//年初累计至今实际套数
                			row.getCell(10).setValue(val_3.add(mpeInfo.getAreaActual()==null?BigDecimal.ZERO:mpeInfo.getAmountActual()).setScale(2, BigDecimal.ROUND_HALF_UP));//年初累计至今实际销售金额
                			row.getCell(12).setValue(val_4.add(mpeInfo.getRecoverActual()==null?BigDecimal.ZERO:mpeInfo.getRecoverActual()).setScale(2, BigDecimal.ROUND_HALF_UP));//年初累计至今实际回款金额
                			if(PlanIndexTypeEnum.PARKING_VALUE.equals(row.getCell(0))){
                				if((val_2 + mpeInfo.getPloidyActual()) != 0){
                					if(new BigDecimal(row.getCell(6).getValue().toString()).compareTo(BigDecimal.ZERO) != 0){
                						row.getCell(8).setValue(new BigDecimal(row.getCell(10).getValue().toString()).divide(new BigDecimal(row.getCell(6).getValue().toString()),2,BigDecimal.ROUND_HALF_UP));//年初累计至今实际均价
                					}else{ //FIXME ADD BY YLW 20121129
                						row.getCell(8).setValue(BigDecimal.ZERO);
                					}
                				}
                			}
                			else{
                				if(new BigDecimal(row.getCell(4).getValue().toString()).compareTo(BigDecimal.ZERO) != 0){
                    				row.getCell(8).setValue(new BigDecimal(row.getCell(10).getValue().toString()).divide(new BigDecimal(row.getCell(4).getValue().toString()),2,BigDecimal.ROUND_HALF_UP));//年初累计至今实际均价
                    			}else{ //FIXME ADD BY YLW 20121129
                    				row.getCell(8).setValue(BigDecimal.ZERO);
                    			}
                			}
		    			}
	        		}
	        		//当新增计划的时候，当前月之前的实际值等于计划值
        			if(Integer.parseInt(cy) <= y && oprtState.equals(OprtState.ADDNEW)){
        				if(month < m){
        					row.getCell(parea_cell+month*gapColumn).setValue(row.getCell(area_cell+month*gapColumn).getValue());
	        				row.getCell(pcount_cell+month*gapColumn).setValue(row.getCell(count_cell+month*gapColumn).getValue());
	        				row.getCell(pprice_cell+month*gapColumn).setValue(row.getCell(price_cell+month*gapColumn).getValue());
	        				row.getCell(pamount_cell+month*gapColumn).setValue(row.getCell(amount_cell+month*gapColumn).getValue());
	        				row.getCell(precover_cell+month*gapColumn).setValue(row.getCell(recover_cell+month*gapColumn).getValue());
	        				row.getCell(3).setValue(row.getCell(4).getValue());//年初累计至今计划面积
                			row.getCell(5).setValue(row.getCell(6).getValue());//年初累计至今计划套数
                			row.getCell(7).setValue(row.getCell(8).getValue());//年初累计至今计划均价
                			row.getCell(9).setValue(row.getCell(10).getValue());//年初累计至今计划销售金额
                			row.getCell(11).setValue(row.getCell(12).getValue());//年初累计至今计划回款金额
        				}
        			}
	    		}
    		}
    		setMonthFootRow();
//    		setkdtProReferenceValue(kdtProReference.getRow(i),kdtEntrys[i]);
    	}
    }
    //获取实际值SQL
    protected String getSql(KDTable tbl){
    	StringBuffer sb = new StringBuffer();
    	String year = tbl.getToolTipText();
    	for(int i=0;i<tbl.getRowCount();i++){
    		IRow row = tbl.getRow(i);
    		String type = "";
    		ProductTypeInfo productInfo = new ProductTypeInfo();
    		AreaSetInfo areaSetInfo = new AreaSetInfo();
    		//产品构成
    		if(row.getCell(0).getValue() instanceof PlanIndexTypeEnum){
    			type = row.getCell(0).getValue().toString();
    		}
			//业态
    		if(row.getCell(1).getValue() instanceof ProductTypeInfo){
    			productInfo = (ProductTypeInfo)row.getCell(1).getValue();
    		}
    		//面积段
    		if(row.getCell(2).getValue() instanceof AreaSetInfo){
    			areaSetInfo = (AreaSetInfo)row.getCell(2).getValue();
    		}
    		
    		/**
    		 *  project T_SHE_SellProject  销售项目 
    		 *  signman T_SHE_SignManage   签约单
    		 *  type    T_FDC_ProductType  产品类型
    		 *  build   T_SHE_Building     楼栋
    		 *  room    T_SHE_Room         房间
    		 *  
    		 **/
    		String signAreaSql="case signman.fsellType when 'PlanningSell' then signman.fstrdPlanBuildingArea " +
    				"when 'PreSell' then signman.fbulidingArea else signman.fstrdActualBuildingArea end ";
    		if(areaSetInfo == null){
    			sb.append(" select '"+type+"' as types,'' as areaSet ,project.fid as projectid,");
    		}
    		else{
    			sb.append(" select '"+type+"' as types,'"+areaSetInfo.getDescription()+"' as areaSet ,project.fid as projectid,");
    		}
    		sb.append(" type.fname_l2 as typename,TO_CHAR(signman.FBusAdscriptionDate,'yyyymm') as busDate, ");
    		sb.append(" sum("+signAreaSql+") as area,count(*) as count,");
    		sb.append(" round(sum(signman.FStrdTotalAmount)/sum("+signAreaSql+"),2) as unitprice,");
    		sb.append(" sum(signman.FStrdTotalAmount) as amount , sum(shoukuan.recamount) as recamount");
    		sb.append(" from T_SHE_Room room");
    		sb.append(" left join T_SHE_Building build on build.fid=room.FBuildingID ");
    		sb.append(" left join T_FDC_ProductType type on type.fid=build.fproducttypeid ");
    		sb.append(" left join T_SHE_SellProject project on build.FSellProjectID=project.fid");
    		sb.append(" left join T_SHE_SignManage signman on signman.froomid=room.fid ");
    		
    		sb.append(" left outer join ( ");
    		sb.append(" select sum(entry.famount+entry.frevAmount) recamount,revBill.Froomid roomId, ");
    		sb.append(" to_char(revBill.fbizdate,'yyyymm') dates from T_BDC_SHERevBillEntry entry ");
    		sb.append(" left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid ");
    		sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    		sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') ");
    		sb.append(" and to_char(revBill.fbizdate,'yyyy') = "+year);
    		sb.append(" group by revBill.Froomid,to_char(revBill.fbizdate,'yyyymm') ");
    		sb.append(" )shoukuan on shoukuan.roomId = room.fid and TO_CHAR(signman.FBusAdscriptionDate, 'yyyymm') = shoukuan.dates ");
    		
    		sb.append(" where room.fsellstate='Sign' and signman.fbizState in('SignApple','SignAudit')");
    		sb.append(" and TO_CHAR(signman.FBusAdscriptionDate,'yyyy') = "+year);
    		sb.append(" and project.fid='"+sellProjectID+"'");
    		if(productInfo.getId() != null)
    		{
    			sb.append(" and type.fid='"+productInfo.getId().toString()+"'");
    		}
    		if(areaSetInfo.getId() != null){
    			if(!areaSetInfo.getMinAreaVal().equals("")){
    				sb.append(" and ("+signAreaSql+")"+areaSetInfo.getMinAreaVal());
    			}
    			if(!areaSetInfo.getMaxAreaVal().equals("")){
    				sb.append(" and ("+signAreaSql+")"+areaSetInfo.getMaxAreaVal());
    			}
    		}
    		if(areaSetInfo == null){
    			sb.append(" group by project.fid,type.fname_l2,TO_CHAR(signman.FBusAdscriptionDate,'yyyymm'),'"+type+"',''");
    		}
    		else{
    			sb.append(" group by project.fid,type.fname_l2,TO_CHAR(signman.FBusAdscriptionDate,'yyyymm'),'"+type+"','"+areaSetInfo.getDescription()+"'");
    		}
    		if(i != tbl.getRowCount() - 1){
        		sb.append(" union all ");  
    		}
    	}
//    	File f = new File("c:\\"+tbl.getToolTipText()+".txt");
//    	BufferedWriter output;
//		try {
//			output = new BufferedWriter(new FileWriter(f));
//	    	output.write(sb.toString());
//	    	output.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

    	return sb.toString();
    } 
    
    /**
     * 1,目标完成率=实际数/年度版测算货值；
	 * 2,未完成=年度版测算货值-年初至今累计销售金额实际数；
     * */
    public void setkdtProReferenceValue(IRow row,KDTable kdtEntry){
    	if(row!=null){
			BigDecimal targetValue = FDCHelper.ZERO;
			BigDecimal yearValue = FDCHelper.ZERO;
			BigDecimal actulAmount = FDCHelper.ZERO;
			if(row.getCell("yearValue").getValue()!=null)
				yearValue = (BigDecimal)row.getCell("yearValue").getValue();
			if(row.getCell("targetValue").getValue()!=null)
				targetValue = (BigDecimal)row.getCell("targetValue").getValue();
			if(row.getCell("actulAmount").getValue()!=null)
				actulAmount = (BigDecimal)row.getCell("actulAmount").getValue();
			if(targetValue.intValue()!=0){
				row.getCell("finishRate").setValue(actulAmount.multiply(new BigDecimal(100)).divide(targetValue,2,2));
			}
			row.getCell("unfinished").setValue(yearValue.subtract(actulAmount));
		}
    }
    /**
     * 表格合计行,并将合计行传入到整盘表
     * */
    public void setMonthFootRow(){
    	for(int n = 0; n<kdtEntrys.length;n++){
    		//把需要合计的列加到set里面
    		Set columnName = new HashSet();
    		for(int i=3;i<kdtEntrys[n].getColumnCount();i++){
    			columnName.add(String.valueOf(i));
    		}
    		TableUtils.getMonthFootRow(kdtEntrys[n], columnName);//合计
    		KDTFootManager footRowManager = kdtEntrys[n].getFootManager();
    		IRow footRow = footRowManager.getFootRow(0);
    		if(kdtProReference.getRowCount() > 0){
            	kdtProReference.getRow(n).getCell("planAmount").setValue(footRow.getCell(paddamount_cell).getValue());
            	kdtProReference.getRow(n).getCell("actulAmount").setValue(footRow.getCell(addamount_cell).getValue());
            	setkdtProReferenceValue(kdtProReference.getRow(n), null);
    		}
    	}
    }
    public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("proReference.year"));
        sic.add(new SelectorItemInfo("proReference.targetValue"));
        sic.add(new SelectorItemInfo("proReference.planAmount"));
        sic.add(new SelectorItemInfo("proReference.actulAmount"));
        sic.add(new SelectorItemInfo("proReference.*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("planYear"));
        sic.add(new SelectorItemInfo("planMonth"));
        sic.add(new SelectorItemInfo("versoin"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("startYear"));
        sic.add(new SelectorItemInfo("cycle"));
        sic.add(new SelectorItemInfo("entrys.*"));
        sic.add(new SelectorItemInfo("entrys.productType.*"));
        
        sic.add(new SelectorItemInfo("versionType"));
        sic.add(new SelectorItemInfo("preArea"));
        sic.add(new SelectorItemInfo("preCount"));
        sic.add(new SelectorItemInfo("prePrice"));
        sic.add(new SelectorItemInfo("preAmount"));
        sic.add(new SelectorItemInfo("preRecover"));
        sic.add(new SelectorItemInfo("state"));
        sic.add("CU.*");
        return sic;
    }

	protected void attachListeners() {
		addDataChangeListener(this.txtPlanYear);
		addDataChangeListener(this.txtPlanMonth);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.txtPlanYear);
		removeDataChangeListener(this.txtPlanMonth);
	}
	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	}else if(com instanceof KDSpinner){
    		listeners = com.getListeners(ChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDSpinner)com).removeChangeListener((ChangeListener)listeners[i]);
    		}
    	} 
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
	 protected void addDataChangeListener(JComponent com) {
	    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDSpinner){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDSpinner)com).addChangeListener((ChangeListener)listeners[i]);
	    		}
	    	}
    	}
    }
	protected ICoreBase getBizInterface() throws Exception {
		return MonthPlanFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	MonthPlanInfo monthPlanInfo = null;
	SellProjectInfo sellProjectInfo =null;
	ValueBreakInfo valueBreakInfo = null;
	ValueBreakEntryCollection valueBreakEntryCol = null;
	
	protected IObjectValue createNewData() {
		sellProjectInfo = (SellProjectInfo)getUIContext().get("sellProject");
		if(sellProjectInfo!=null)
		{
			sellProjectID = sellProjectInfo.getId().toString();
		}	
		//获取目标预测值  isNew = boolean
//        MeasurePlanTargetInfo measurePlanInfo = getMeasurePlanTargetInfo();
        //最新版货值年度分解 isNewVersoin = 1
        valueBreakInfo = getValueBreakInfo();
        if(valueBreakInfo != null){
        	//年度分解的分录
        	valueBreakEntryCol = getValueBreakEntryCollection(valueBreakInfo.getId().toString());
        }
        //最新目标预测：  版本号
//        String measureVersoin = measurePlanInfo.getVersions();
        //最新版 年度分解：   版本号
        String valueBreakVersion = valueBreakInfo.getVersion();
        try {
        	//获取年度版值与最新值
        	setProNew = MonthFactValueFacadeFactory.getRemoteInstance().getLastValue(valueBreakInfo.getId().toString());
        	setProYear = MonthFactValueFacadeFactory.getRemoteInstance().getYearValue(sellProjectID, String.valueOf(y));
		} catch (Exception e2) {
			logger.equals(e2.getMessage());
		}
        valueVersoin = valueBreakInfo.getVersion();
        //当前项目最新的月度销售计划 cratetime 是最后的
        monthPlanInfo = getMonthPlanInfo(valueBreakVersion,sellProjectID);
        
    	year = valueBreakInfo.getYear();
        cycle = valueBreakInfo.getCycle();
        //如果非空则认为是修订
        if(monthPlanInfo!=null){
			MonthPlanCollection MonthPlanColl = new MonthPlanCollection();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectID));
			//过滤最新版 年度分解的valueBreakVersion(版本号)
			filter.getFilterItems().add(new FilterItemInfo("versoin",valueBreakVersion+"%",CompareType.LIKE));
			view.setFilter(filter);
			try {
				MonthPlanColl = MonthPlanFactory.getRemoteInstance().getMonthPlanCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			//TODO 有最新的年度版本，但是没有与之对应的月度销售计划
			if(MonthPlanColl.size()==0){
				monthPlanInfo.setVersoin(valueBreakVersion+".0");
				Date now=null;
				try {
					now = SysUtil.getAppServerTime(null);
				} catch (EASBizException e1) {
					e1.printStackTrace();
				}
				//年度分解最新的分录
				ValueBreakEntryCollection valueBreakEntryColl = valueBreakInfo.getEntrys();
		        CRMHelper.sortCollection(valueBreakEntryColl, "seq", true);
		        
				Map typeMap=new HashMap();
				//最新版月度销售分录记录
				MonthPlanEntryCollection monthPlanEntryCollection = monthPlanInfo.getEntrys();
				CRMHelper.sortCollection(monthPlanEntryCollection, "seq", true);
				
				MonthPlanEntryCollection nowMonthPlanEntryCollection =new MonthPlanEntryCollection();
				
				for(int i=0;i<monthPlanEntryCollection.size();i++){
					MonthPlanEntryInfo monthPlanEntryInfo = monthPlanEntryCollection.get(i);
					if(monthPlanEntryInfo.getProductType() == null || monthPlanEntryInfo.getType() == null){continue;}
					//产品构成 1house、2publicBuild、3parking、2business
					PlanIndexTypeEnum planIndexType = monthPlanEntryInfo.getType();
					//业态
		        	ProductTypeInfo productTypeInfo = monthPlanEntryInfo.getProductType();
		        	if(typeMap.get(planIndexType.getValue()+","+productTypeInfo.getId().toString())!=null){
		        		//将 monthPlanEntryInfo 加到MAP里 对应的产品构成planIndexType
		        		((MonthPlanEntryCollection)typeMap.get(planIndexType.getValue()+","+productTypeInfo.getId().toString())).add(monthPlanEntryInfo);
		        	}else{
		        		//MAP里新建 对应的产品构成planIndexType
		        		MonthPlanEntryCollection addmonthPlanEntryCollection =new MonthPlanEntryCollection();
		        		addmonthPlanEntryCollection.add(monthPlanEntryInfo);
		        		typeMap.put(planIndexType.getValue()+","+productTypeInfo.getId().toString(), addmonthPlanEntryCollection);
		        	}
				}
				for(int i=0;i<valueBreakEntryColl.size();i++){
					ValueBreakEntryInfo valueBreakEntryInfo = valueBreakEntryColl.get(i);
					if(valueBreakEntryInfo.getProductType()==null||valueBreakEntryInfo.getType()==null||valueBreakEntryInfo.getYear()!=0) continue;
					PlanIndexTypeEnum planIndexType = valueBreakEntryInfo.getType();
		        	ProductTypeInfo productTypeInfo = valueBreakEntryInfo.getProductType();
		        	if(typeMap.get(planIndexType.getValue()+","+productTypeInfo.getId().toString())!=null){
		        		nowMonthPlanEntryCollection.addCollection((MonthPlanEntryCollection)typeMap.get(planIndexType.getValue()+","+productTypeInfo.getId().toString()));
		        	}else{
		        		for(int j=valueBreakInfo.getYear();j<valueBreakInfo.getYear()+valueBreakInfo.getCycle();j++){
		        			for(int k=0;k<14;k++){
			        			MonthPlanEntryInfo monthPlanEntryInfo = new MonthPlanEntryInfo();
					        	monthPlanEntryInfo.setProductType(productTypeInfo);
					        	monthPlanEntryInfo.setAreaRange(valueBreakEntryInfo.getAreaRange());
					        	monthPlanEntryInfo.setNewAreaRange(valueBreakEntryInfo.getNewAreaRange());
					        	monthPlanEntryInfo.setType(planIndexType);
					        	monthPlanEntryInfo.setYear(j);
					        	monthPlanEntryInfo.setMonth(k);
					        	nowMonthPlanEntryCollection.add(monthPlanEntryInfo);
			        		}
		        		}
		        	}
				}
				monthPlanInfo.getEntrys().clear();
				monthPlanInfo.getEntrys().addCollection(nowMonthPlanEntryCollection);
				
				monthPlanInfo.getProReference().clear();
				ValueBreakEntryInfo valueBreakEntryInfo=null;
				monthPlanInfo.setCycle(cycle);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				if(cal.get(Calendar.MONTH)==11){
					monthPlanInfo.setPlanMonth(1);
					monthPlanInfo.setPlanYear(year+1);
				}else{
					monthPlanInfo.setPlanMonth(cal.get(Calendar.MONTH)+2);
					monthPlanInfo.setPlanYear(year);
				}
			}
			else{
				monthPlanInfo.setVersoin(getVersion(monthPlanInfo.getVersoin()));
			}
			
			monthPlanInfo.setSellProject(sellProjectInfo);
        	monthPlanInfo.setId(null);
        	monthPlanInfo.setCreateTime(null);
        	monthPlanInfo.setCreator(null);
        	monthPlanInfo.setLastUpdateTime(null);
        	monthPlanInfo.setLastUpdateTime(null);
        	monthPlanInfo.setAuditor(null);
        	monthPlanInfo.setAuditTime(null);
        	monthPlanInfo.setState(FDCBillStateEnum.SAVED);
        	monthPlanInfo.setNumber(null);
        	monthPlanInfo.setVersionType(valueBreakInfo.getVersionType());
        	monthPlanInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
        	monthPlanInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        	return monthPlanInfo;
        }
        MonthPlanInfo mp=new MonthPlanInfo();
//        if(VersionTypeEnum.year.equals((VersionTypeEnum)valueBreakInfo.getVersionType())){
//            mp.setVersionType(valueBreakInfo.getVersionType());
//        }
        mp.setVersionType(valueBreakInfo.getVersionType());
        mp.setState(FDCBillStateEnum.SAVED);
        mp.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
        mp.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return mp;
	}

	//获取目标测算的最新版本
	public MeasurePlanTargetInfo getMeasurePlanTargetInfo(){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projectAgin.id",sellProjectID));
		filter.getFilterItems().add(new FilterItemInfo("isNew",Boolean.TRUE));
		view.setFilter(filter);
		MeasurePlanTargetCollection coll = new MeasurePlanTargetCollection();
		try {
			coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(view);
			if(coll.size()==0){
				MsgBox.showInfo("未有生效的目标测算！");
				SysUtil.abort();
			}
			CRMHelper.sortCollection(coll, "createTime", false);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return coll.get(0);
	}

	//获取总货值年度分解的最新版本
	public ValueBreakInfo getValueBreakInfo(){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectID));
		filter.getFilterItems().add(new FilterItemInfo("isNewVersoin","1"));
		view.setFilter(filter);
		view.setSelector(getValueBreak());
		ValueBreakCollection coll = new ValueBreakCollection();
		try {
			coll = ValueBreakFactory.getRemoteInstance().getValueBreakCollection(view);
			if(coll.size()==0){
				MsgBox.showInfo("未有生效的货值年度分解！");
				SysUtil.abort();
			}
			CRMHelper.sortCollection(coll, "createTime", false);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return coll.get(0);
	}
	
	//获取年度分解的分录
	public ValueBreakEntryCollection getValueBreakEntryCollection(String Id){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SorterItemCollection src = new SorterItemCollection();
		src.add(new SorterItemInfo("seq"));
		filter.getFilterItems().add(new FilterItemInfo("head.id",Id,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("year","0",CompareType.EQUALS));
		view.setFilter(filter);
		view.setSelector(getValueBreakEntry());
		view.setSorter(src);
		ValueBreakEntryCollection coll = new ValueBreakEntryCollection();
		try {
			coll = ValueBreakEntryFactory.getRemoteInstance().getValueBreakEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return coll;
	}
	
	public SelectorItemCollection getValueBreak(){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("measureType"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("versionName"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("measureStage.*"));
        sic.add(new SelectorItemInfo("totalAmount.*"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("cycle"));
        sic.add(new SelectorItemInfo("versionType"));
        sic.add(new SelectorItemInfo("number"));
		return sic;
	}
	
	public SelectorItemCollection getValueBreakEntry(){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("type"));
		sic.add(new SelectorItemInfo("productType.*"));
        sic.add(new SelectorItemInfo("newAreaRange.*"));
		return sic;
	}
	
	/**
	 * 获取总货值年度分解最新版本的年度版
	 * @return 当前年度货值
	 */
	public BigDecimal getYearVersionValue(int year){
		BigDecimal amount = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectID));
		filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.YEAR_VALUE));
		view.setFilter(filter);
		ValueBreakCollection coll = new ValueBreakCollection();
		ValueBreakEntryCollection entryColl = new ValueBreakEntryCollection();
		try {
			coll = ValueBreakFactory.getRemoteInstance().getValueBreakCollection(view);
			CRMHelper.sortCollection(coll, "version", false);
			if(coll.size()>0){
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id",coll.get(0).getId()));
				filter.getFilterItems().add(new FilterItemInfo("year",year+""));
				view.setFilter(filter);
				entryColl = ValueBreakEntryFactory.getRemoteInstance().getValueBreakEntryCollection(view);
				for(int i=0;i<entryColl.size();i++){
					ValueBreakEntryInfo valueBreakEntryInfo = entryColl.get(i);
					if(valueBreakEntryInfo.getAmount()!=null)
        				amount = amount.add(valueBreakEntryInfo.getAmount());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		this.afterActionDo();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.afterActionDo();
	}
	
	public void afterActionDo(){
		//默认选择页签为当前计划年份页签 wyh
		int year = ((Integer)txtPlanYear.getValue()).intValue();
		int index = cycle;
		if(cycle!=0){
			for(int i=0;i<index;i++){
				if(kdtEntrys.length>0 && kdtEntrys.length>=i && kdtEntrys[i]!=null){
					if(Integer.parseInt(kdtEntrys[i].getToolTipText())==year){
						kDTabbedPane1.setSelectedIndex(i);
					}
				}
			}
		}
		this.setKDTEntrysLock();
	}
	
	protected void afterSubmitAddNew(){
		
	}
	protected boolean isContinueAddNew() {
		return false;
	}
	/**
	 * 当前与年度分解版本相符的月度计划
	 * */
	public static MonthPlanInfo getMonthPlanInfo(String versoin,String sellProjectId){
		MonthPlanCollection MonthPlanColl = new MonthPlanCollection();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectId));
//		filter.getFilterItems().add(new FilterItemInfo("versoin",versoin+"%",CompareType.LIKE));
		view.setFilter(filter);
		try {
			MonthPlanColl = MonthPlanFactory.getRemoteInstance().getMonthPlanCollection(view);
			CRMHelper.sortCollection(MonthPlanColl, "createTime", false);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return MonthPlanColl.get(0);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.setCom();
		kDTabbedPane1.remove(kdtEntry);
		//TODO
		if(this.oprtState.equals(OprtState.ADDNEW)){//新增状态 需要去判断是新增还是修订
	        //if(monthPlanInfo==null){//如果monthPlanInfo为空则为新增数据，不为空则为修订数据
				initTable(year, cycle);
		        ValueBreakEntryInfo valueBreakEntryInfo = null;
		        

	        	//如果需要显示面积段，请将下面这段注销
		        //ValueBreakEntryInfo oldValueBreakEntryInfo = new ValueBreakEntryInfo();
	        	//去除面积段完成
		        int isNewest = 0;
		        for(int i = 0;i<valueBreakEntryCol.size();i++){
		        	valueBreakEntryInfo = valueBreakEntryCol.get(i);
		        	//如果需要显示面积段，请将下面这段注销
		        	/*if(valueBreakEntryInfo.getProductType() == null){
		        		isNewest ++;
		        		continue;
		        	}
		        	if(i == 0){
		        		oldValueBreakEntryInfo = valueBreakEntryCol.get(0);
		        		isNewest = 2;
		        	}
		        	else if(i != 0 && isNewest == 1){
		        		oldValueBreakEntryInfo = valueBreakEntryCol.get(i);
		        	}
		        	if(i != 0 && isNewest != 1){
		        		if(oldValueBreakEntryInfo.getProductType().equals(valueBreakEntryInfo.getProductType()) && oldValueBreakEntryInfo.getType().equals(valueBreakEntryInfo.getType())){
		        			continue;
		        		}
		        		else{
		        			oldValueBreakEntryInfo = valueBreakEntryCol.get(i);
		        		}
		        	}*/
		        	//去除面积段完成
		        	
		        	if(valueBreakEntryInfo.getYear()==0){
			        	PlanIndexTypeEnum type = valueBreakEntryInfo.getType();
			        	ProductTypeInfo productTypeInfo = valueBreakEntryInfo.getProductType();
			        	for(int j=0;j<kdtEntrys.length;j++){
			        		IRow row = kdtEntrys[j].addRow();
			        		row.getCell(0).setValue(type);
			        		row.getCell(1).setValue(productTypeInfo);
			        		row.getCell(2).setValue(valueBreakEntryInfo.getNewAreaRange());
			        	}
		        	}
		        }
		        Date now = SysUtil.getAppServerTime(null);
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				detachListeners();
				this.txtPlanYear.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtPlanYear,""));
				this.txtPlanMonth.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtPlanMonth,""));
				if(cal.get(Calendar.MONTH)==11){//如果12月份做跨年计划，月份为下一年的一月份
					txtPlanMonth.setValue(new Integer(1));
					txtPlanYear.setValue(Integer.valueOf(year+1));
				}
				else{
					txtPlanMonth.setValue(new Integer(cal.get(Calendar.MONTH)+2));
					txtPlanYear.setValue(Integer.valueOf(year));
				}
				attachListeners();
//				txtVersoin.setText(valueVersoin+".0");
				txtVersoin.setText(valueVersoin);
				prmtSellProject.setValue(sellProjectInfo);
				prmtOrgUnit.setValue(SysContext.getSysContext().getCurrentOrgUnit());
				prmtCreator.setValue(SysContext.getSysContext().getCurrentUserInfo());
				kdtProReference.removeRows();
	        	kdtProReference.checkParsed();
	        	int count = 0;
	        	for(int j =0;j<cycle;j++){
	        		IRow row = kdtProReference.addRow();
	        		if(setProYear != null){
	        			while(setProYear.next()){
		        			if(j == count){
		        				row.getCell("year").setValue(setProYear.getString("year"));
		        				row.getCell("yearValue").setValue(setProYear.getBigDecimal("amount"));
		        			}
		        			count ++;
		        		}
	        			setProYear.beforeFirst();
	        		}
	        		count = 0;
	        		if(setProNew != null){
		        		while(setProNew.next()){
		        			if(j == count){
		        				row.getCell("year").setValue(setProNew.getString("year"));
		        				row.getCell("targetValue").setValue(setProNew.getBigDecimal("amount"));
		        			}
		        			count ++;
		        		}
		        		setProNew.beforeFirst();
	        		}
	        		count = 0;
	        	}
	        //}
		}else{
			sellProjectID = ((SellProjectInfo)prmtSellProject.getValue()).getId().toString();
			detachListeners();
			txtPlanYear.setValue(new Integer(editData.getPlanYear()));
			txtPlanMonth.setValue(new Integer(editData.getPlanMonth()));
			attachListeners();
			if(editData.getId()!=null){
				MonthPlanInfo info = MonthPlanFactory.getRemoteInstance().getMonthPlanInfo(new ObjectUuidPK(editData.getId()));
				year = info.getStartYear();
				cycle = info.getCycle();
			}
		}
		if(this.oprtState.equals(OprtState.ADDNEW)){//新增状态 需要去判断是新增还是修订
			MonthPlanInfo monthPlanInfo=getMonthPlanInfo(valueVersoin,sellProjectID);
			//if(monthPlanInfo==null){
				loadFactValue();
				for(int i=0;i<kdtEntrys.length;i++){
		    		//把需要合计的列加到set里面
		    		Set columnName = new HashSet();
		    		for(int j=3;j<kdtEntrys[i].getColumnCount();j++){
		    			columnName.add(String.valueOf(j));
		    		}
		    		TableUtils.setTotalRow2(kdtEntrys[i],false);//小计、分类合计
		    		TableUtils.getMonthFootRow(kdtEntrys[i], columnName);//总合计
		        	
		        }
			//}
    	}
		kdtProReference.getStyleAttributes().setLocked(false);
		kdtProReference.getColumn("year").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtProReference.getColumn("targetValue").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtProReference.getColumn("planAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtProReference.getColumn("actulAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtProReference.getColumn("yearValue").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtProReference.getColumn("unfinished").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtProReference.getColumn("finishRate").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		setKDTEntrysLock();
		TableUtils.getFootRow(kdtProReference, new String[]{"yearValue","targetValue","planAmount","actulAmount","unfinished"});
		
		for(int j=0;j<kdtEntrys.length;j++){
			FDCTableHelper.disableCopy(kdtEntrys[j]);
			FDCTableHelper.disableDelete(kdtEntrys[j]);
		}
		onLoadPrice();
		versionType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		versionType.setEnabled(false);
		//新增记录业务日期默认为当天
		if(this.oprtState.equals(OprtState.ADDNEW)){
			this.pkBookedDate.setValue(new Date());
		}
		//默认选择页签为当前计划年份页签
		int year = ((Integer)txtPlanYear.getValue()).intValue();
		int index = cycle;
		if(cycle!=0){
			for(int i=0;i<index;i++){
				if(kdtEntrys.length>0 && kdtEntrys.length>=i && kdtEntrys[i]!=null){
					if(Integer.parseInt(kdtEntrys[i].getToolTipText())==year){
						kDTabbedPane1.setSelectedIndex(i);
					}
				}
			}
		}
		/*this.kdtProReference.getColumn("year").setWidth(30);
		this.kdtProReference.getColumn("year").setWidth(500);*/
		
	}
	
	//设置控件状态
	private void setCom(){
//		this.setPreferredSize(getMaximumSize());
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.actionAddNew.setVisible(false);
		txtNumber.setRequired(true);
		txtName.setRequired(true);
	}
	
	//修订时变成下一个版本
	private String getVersion (String oldVersion){
		int last = oldVersion.lastIndexOf(".");
		String newVersion = oldVersion.substring(0,last);
		String lastField = oldVersion.substring(last+1);
		int Version = Integer.parseInt(lastField)+1;
		return newVersion+"."+Version;
	}
		
	protected void verifyInputForSave() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			VerifyInputUtil.verifyNull(this, txtNumber,"计划编号" );
		}
		VerifyInputUtil.verifyNull(this, txtName,"计划名称" );
		VerifyInputUtil.verifyNull(this, versionType,"版本类型" );
		sellProjectInfo = (SellProjectInfo)prmtSellProject.getValue();
		int last = txtVersoin.getText().lastIndexOf(".");
		String version = txtVersoin.getText().substring(0,last);
		if(VersionTypeEnum.check.equals(versionType.getSelectedItem())){
			if(checkVersionType(editData.getId(),sellProjectInfo.getId().toString(),version,new MonthPlanInfo())){
				MsgBox.showWarning(this,"当前阶段的考核版已经存在，不能再新增考核版！");
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * 货值测算/货值年度分解/销售计划 :一个阶段只能有一个考核版
	 * */
	public static boolean checkVersionType(BOSUuid  bosid,String projectId,String version,Object obj){
		String id = OrgConstants.DEF_CU_ID;
		if(bosid!=null)
			id = bosid.toString();
		boolean flag = false;
		if(obj instanceof MonthPlanInfo){
			MonthPlanCollection MonthPlanColl = new MonthPlanCollection();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",projectId));
			filter.getFilterItems().add(new FilterItemInfo("versoin",version+"%",CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.CHECK_VALUE));
			view.setFilter(filter);
			try {
				MonthPlanColl = MonthPlanFactory.getRemoteInstance().getMonthPlanCollection(view);
				if(MonthPlanColl.size()>0)
					flag = true;
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		else if(obj instanceof MeasurePlanTargetInfo){
			MeasurePlanTargetCollection coll = new MeasurePlanTargetCollection();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("projectAgin.id",projectId));
			filter.getFilterItems().add(new FilterItemInfo("versions",version+"%",CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.CHECK_VALUE));
			view.setFilter(filter);
			try {
				coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(view);
				if(coll.size()>0)
					flag = true;
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		else if(obj instanceof ValueBreakInfo){
			ValueBreakCollection valueBreakColl = new ValueBreakCollection();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",projectId));
			filter.getFilterItems().add(new FilterItemInfo("version",version+"%",CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.CHECK_VALUE));
			view.setFilter(filter);
			try {
				valueBreakColl = ValueBreakFactory.getRemoteInstance().getValueBreakCollection(view);
				if(valueBreakColl.size()>0)
					flag = true;
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		BigDecimal planAmount = FDCHelper.ZERO;
		BigDecimal targetValue = FDCHelper.ZERO;
		
		for(int j = 0;j<kdtProReference.getRowCount();j++){
			if(!kdtProReference.getRow(j).getCell("year").getValue().toString().equals(String.valueOf(y))){
				continue;
			}
			if(txtPlanYear.getValue().toString().equals(kdtProReference.getRow(j).getCell("year").getValue().toString())){
				if(kdtProReference.getRow(j).getCell("planAmount").getValue()!=null)
					planAmount = (BigDecimal)kdtProReference.getRow(j).getCell("planAmount").getValue();
				if(kdtProReference.getRow(j).getCell("targetValue").getValue()!=null)
					targetValue = (BigDecimal)kdtProReference.getRow(j).getCell("targetValue").getValue();
				if(planAmount.compareTo(targetValue)<0){
//					MsgBox.showWarning(this,txtPlanYear.getValue().toString()+"年度计划总和必须大于等于目标总货值，若达不到，请调整年度货值测算！");
					MsgBox.showWarning(this,txtPlanYear.getValue().toString()+"本年月度计划总额不能小于该年最新测算货值！");
					SysUtil.abort();
				}
			}
		}
		verifyPreValue();
	}
	
	/**
	 * 预估值必须大于等于当月已发生实际数
	 * */
	public void verifyPreValue(){
		int year = ((Integer)txtPlanYear.getValue()).intValue();
    	int emonth = ((Integer)txtPlanMonth.getValue()).intValue();
    	for(int j=0;j<kdtEntrys.length;j++){
    		if(kdtEntrys[j].getToolTipText().equals(""+year)){
    			for(int i=0;i<kdtEntrys[j].getRowCount();i++){
        			IRow row = kdtEntrys[j].getRow(i);
        			if(row.getCell(2).getValue() != null){
        				if(row.getCell(2).getValue().equals("小计")){
        					continue;
        				}
        			}
            		if(row.getCell(1).getValue() instanceof ProductTypeInfo){
            			for(int k=1;k<13;k++){
            				if(k==emonth){
            					BigDecimal prearea = FDCHelper.ZERO;//本月预估销售面积
                				BigDecimal precount = FDCHelper.ZERO;//本月预估销售套数
                				BigDecimal preprice = FDCHelper.ZERO;//本月预估销售均价
                				BigDecimal preamount = FDCHelper.ZERO;//本月预估销售金额
                				BigDecimal prerecover = FDCHelper.ZERO;//本月预估销售回款金额
                				
                				BigDecimal area = FDCHelper.ZERO;
                				BigDecimal count = FDCHelper.ZERO;
                				BigDecimal price = FDCHelper.ZERO;
                				BigDecimal amount = FDCHelper.ZERO;
                				BigDecimal recover = FDCHelper.ZERO;
            					if(row.getCell(prearea_cell).getValue()!=null)
                					prearea = (BigDecimal)row.getCell(prearea_cell).getValue();
                				if(row.getCell(precount_cell).getValue()!=null)
                					precount = new BigDecimal(row.getCell(precount_cell).getValue().toString());
                				if(row.getCell(preprice_cell).getValue()!=null)
                					preprice = (BigDecimal)row.getCell(preprice_cell).getValue();
                				if(row.getCell(preamount_cell).getValue()!=null)
                					preamount = (BigDecimal)row.getCell(preamount_cell).getValue();
                				if(row.getCell(prerecover_cell).getValue()!=null)
                					prerecover = (BigDecimal)row.getCell(prerecover_cell).getValue();
                				
            					if(row.getCell(area_cell+k*gapColumn).getValue()!=null)
                					area = (BigDecimal)row.getCell(area_cell+k*gapColumn).getValue();
                				if(row.getCell(count_cell+k*gapColumn).getValue()!=null)
                					count = new BigDecimal(row.getCell(count_cell+k*gapColumn).getValue().toString());
                				if(row.getCell(price_cell+k*gapColumn).getValue()!=null)
                					price = (BigDecimal)row.getCell(price_cell+k*gapColumn).getValue();
                				if(row.getCell(amount_cell+k*gapColumn).getValue()!=null)
                					amount = (BigDecimal)row.getCell(amount_cell+k*gapColumn).getValue();
                				if(row.getCell(recover_cell+k*gapColumn).getValue()!=null)
                					recover = (BigDecimal)row.getCell(recover_cell+k*gapColumn).getValue();
                				if(prearea.compareTo(area)<0){
                					MsgBox.showWarning(this,"本月预估销售面积必须大于等于当月已发生实际数！");
                					SysUtil.abort();
                				}
                				if(precount.compareTo(count)<0){
                					MsgBox.showWarning(this,"本月预估销售套数必须大于等于当月已发生实际数！");
                					SysUtil.abort();
                				}
                				if(preprice.compareTo(price)<0){
                					MsgBox.showWarning(this,"本月预估销售均价必须大于等于当月已发生实际数！");
                					SysUtil.abort();
                				}
                				if(preamount.compareTo(amount)<0){
                					MsgBox.showWarning(this,"本月预估销售金额必须大于等于当月已发生实际数！");
                					SysUtil.abort();
                				}
                				if(prerecover.compareTo(recover)<0){
                					MsgBox.showWarning(this,"本月预估销售回款金额必须大于等于当月已发生实际数！");
//                					SysUtil.abort();
                				}
            				}
            		   }
            		}
        		}
    		}
    	}
	}
	KDTable kdtEntrys []= null;
	String sellProjectID = null;
	int paddarea_cell = 3; //累计面积计划数所在列
	int addarea_cell = 4; //累计面积实际数所在行列
	int paddcount_cell = 5; //累计套数计划数所在列
	int addcount_cell = 6; //累计套数实际数所在列
	int paddprice_cell = 7; //累计均价计划数所在列
	int addprice_cell = 8; //累计均价实际数所在列
	int paddamount_cell = 9; //累计金额计划数所在列
	int addamount_cell = 10; //累计金额实际数所在列
	int paddrecover_cell = 11; //累计回款计划数所在列
	int addrecover_cell = 12; //累计回款实际数所在列
	
	int parea_cell = 8; //面积计划数所在行号  ，从10开始算的
	int area_cell = 9; //面积实际数所在行号 ，从10开始算的
	int pcount_cell = 10; //套数计划数所在行号 ，从10开始算的
	int count_cell = 11; //套数实际数所在行号 ，从10开始算的
	int pprice_cell = 12; //均价计划数所在行号 ，从10开始算的
	int price_cell = 13; //均价实际数所在行号 ，从10开始算的
	int pamount_cell = 14; //金额计划数所在行号 ，从10开始算的
	int amount_cell = 15; //金额实际数所在行号 ，从10开始算的
	int precover_cell = 16; //回款计划数所在行号 ，从10开始算的
	int recover_cell = 17; //回款实际数所在行号 ，从10开始算的
	
	int parea_mode = 8; //面积计划数所在行号%10 余数
	int area_mode = 9; //面积实际数所在行号 %10 余数
	int pcount_mode = 0; //套数计划数所在行号 %10 余数
	int count_mode = 1; //套数实际数所在行号 %10 余数
	int pprice_mode = 2; //均价计划数所在行号 %10 余数
	int price_mode = 3; //均价实际数所在行号 %10 余数
	int pamount_mode = 4; //金额计划数所在行号 %10 余数
	int amount_mode = 5; //金额实际数所在行号 %10 余数
	int precover_mode = 6; //回款计划数所在行号%10 余数
	int recover_mode = 7; //回款实际数所在行号 %10 余数
	
	int prearea_cell = 13; //预估面积所在列号
	int precount_cell = 14; //预估套数所在列号
	int preprice_cell = 15; //预估均价所在列号
	int preamount_cell = 16; //预估金额所在列号
	int prerecover_cell = 17; //预估回款所在列号
}