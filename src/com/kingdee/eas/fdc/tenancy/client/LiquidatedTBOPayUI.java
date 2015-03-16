/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJBException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageFactory;
import com.kingdee.eas.fdc.tenancy.PaymentAdvicePrintFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LiquidatedTBOPayUI extends AbstractLiquidatedTBOPayUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedTBOPayUI.class);
    private LiquidatedTBOPayFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	private SellProjectInfo sellProject=null;
	//client到server端的SQL参数
	private Map param=new HashMap();
	//服务端传过来的结果集
	private Map result=null;
	
    /**
     * output class constructor
     */
    public LiquidatedTBOPayUI() throws Exception
    {
        super();
    }
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setUiObject(null);
		commonQueryDialog.setShowFieldCompare(false);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		return commonQueryDialog;
	}
    
    private LiquidatedTBOPayFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				if(this.getUIContext().get("sellProject")!=null){
					sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
				}
				this.filterUI = new LiquidatedTBOPayFilterUI(this,
						this.actionOnLoad);
				this.filterUI.isAudit.setVisible(false);
				this.filterUI.sellProject.setValue(sellProject);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionEdit.setVisible(false);
		
		initTable(this.tblMain);
		
		try {
			fillTable();
		}catch(Exception ex){
			
		}
		SimpleKDTSortManager.setTableSortable(tblMain);
		this.tblMain.getColumn("tarea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
	}
	/**
	 * @author eric_wang
	 * @param table
	 * 初始化table
	 */
	private void initTable(KDTable table){
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		table.checkParsed();
		//项目名称
		KDTextField sellProjectName = new KDTextField();
		table.getColumn("sellProjectName").setEditor(new KDTDefaultCellEditor(sellProjectName));
		//合同名称
		KDTextField tenancyBillName = new KDTextField();
		table.getColumn("name").setEditor(new KDTDefaultCellEditor(tenancyBillName));
		//房间名称
		KDTextField room = new KDTextField();
		table.getColumn("room").setEditor(new KDTDefaultCellEditor(room));
		//客户
		KDTextField customer = new KDTextField();
		table.getColumn("customer").setEditor(new KDTDefaultCellEditor(customer));
		//付款类型
		KDTextField moneyDefine = new KDTextField();
		table.getColumn("moneyDefine").setEditor(new KDTDefaultCellEditor(moneyDefine));
		//开始日期
		KDDatePicker startDate = new KDDatePicker();
		table.getColumn("startDate").setEditor(new KDTDefaultCellEditor(startDate));
		//结束日期
		KDDatePicker endDate = new KDDatePicker();
		table.getColumn("endDate").setEditor(new KDTDefaultCellEditor(endDate));
		//应付日期
		KDDatePicker appDate = new KDDatePicker();
		table.getColumn("appDate").setEditor(new KDTDefaultCellEditor(appDate));
		//应付金额
		 KDFormattedTextField appAmount = new KDFormattedTextField();
		 appAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 appAmount.setPrecision(3);
		 appAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		 appAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		 table.getColumn("appAmount").setEditor(new KDTDefaultCellEditor(appAmount));
		 table.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 //实付金额
		 KDFormattedTextField actAmount = new KDFormattedTextField();
		 actAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 actAmount.setPrecision(3);
		 actAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		 actAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		 table.getColumn("actAmount").setEditor(new KDTDefaultCellEditor(actAmount));
		 table.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 //剩余金额
		 KDFormattedTextField balance = new KDFormattedTextField();
		 balance.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 balance.setPrecision(3);
		 balance.setMinimumValue(FDCHelper.MIN_VALUE);
		 balance.setMaximumValue(FDCHelper.MAX_VALUE);
		 table.getColumn("balance").setEditor(new KDTDefaultCellEditor(balance));
		 table.getColumn("balance").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 table.getColumn("tarea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	/**
	 * @author eric_wang
	 * @param result
	 * 填充table
	 */
	private void fillTable(){
		tblMain.checkParsed();
		tblMain.removeRows();
		List paymentAdviceList=(ArrayList)result.get("result");
		for(Iterator it=paymentAdviceList.iterator();it.hasNext(); ){
			Map paymentAdvice=(Map)it.next();
			IRow row=tblMain.addRow();
			row.getCell("id").setValue(paymentAdvice.get("id"));
			row.getCell("sellProjectName").setValue(paymentAdvice.get("projectname"));
			row.getCell("name").setValue(paymentAdvice.get("contractname"));
			row.getCell("room").setValue(paymentAdvice.get("roomname"));
			
			BigDecimal tarea=  ((BigDecimal)paymentAdvice.get("tarea")); 
			if(tarea!=null){
				tarea = FDCHelper.divide(tarea, new BigDecimal("1"));
			}
			row.getCell("tarea").setValue(tarea);
			
			row.getCell("customer").setValue(paymentAdvice.get("customer"));
			row.getCell("moneyDefine").setValue(paymentAdvice.get("moneyname"));
			row.getCell("startDate").setValue(paymentAdvice.get("startdate"));
			row.getCell("endDate").setValue(paymentAdvice.get("enddate"));
			row.getCell("appDate").setValue(paymentAdvice.get("appdate"));
			row.getCell("state").setValue(getTenancyBillState((String)paymentAdvice.get("state")));
			BigDecimal appamount=  ((BigDecimal)paymentAdvice.get("appamount")); 
			appamount = FDCHelper.divide(appamount, new BigDecimal("1"));
			row.getCell("appAmount").setValue(appamount);
			BigDecimal actAmount=  ((BigDecimal)paymentAdvice.get("actamount")); 
			actAmount = FDCHelper.divide(actAmount, new BigDecimal("1"));
			row.getCell("actAmount").setValue(actAmount);
			row.getCell("balance").setValue(FDCHelper.subtract(appamount, actAmount));
		}
	}
	
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	
	private Object getTenancyBillState(Object o){
		if(o.equals("Submitted")){
			return "已提交";
		}else if(o.equals("Auditing")){
			return "审批中";
		}else if(o.equals("Audited")){
			return "已审批";
		}else if(o.equals("Executing")){
			return "执行中";
		}
		return null;
	}
	protected void execQuery() {
		super.execQuery();
		param.clear();
		//当以默认方案直接进入 时，获取默认方案
		if(mainQuery!=null){
			//取保存的分配方案
			if(mainQuery.getFilter()!=null){
				FilterInfo filter=mainQuery.getFilter();
				
				if(this.getUIContext().get("sellProject")!=null){
					Set sellProjectSet=new HashSet();
					sellProjectSet.add(((SellProjectInfo) this.getUIContext().get("sellProject")).getId().toString()) ;
					param.put("sellProjectId",sellProjectSet);
				}
				for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
					FilterItemInfo item=(FilterItemInfo)iter.next();
					//项目
//					if("sellProject.name".equalsIgnoreCase(item.getPropertyName())){
//						param.put("sellProject",item.getCompareValue().toString());
//					}
					if("sellProject.id".equalsIgnoreCase(item.getPropertyName())){
						String str1=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
						String[] sellprojectid=str1.split(",");
						Set sellProject=new HashSet();
						for(int i=0;i<sellprojectid.length;i++){
							sellProject.add(sellprojectid[i].trim());
						}
						param.put("sellProjectId",sellProject);
					}
					//客户
					if ("customer.id".equalsIgnoreCase(item.getPropertyName())) {
						if (item.getCompareValue() != null) {
							String str1=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
							String[] customerid=str1.split(",");
							for(int i=0;i<customerid.length;i++){
								customerid[i]=customerid[i].trim();
							}
							List customeridList=Arrays.asList(customerid);
							param.put("customer",customeridList);
						}
					}
					//合同
					if("contract".equalsIgnoreCase(item.getPropertyName())){
						param.put("contract",item.getCompareValue().toString());
					}
					 //款项类型
					if("moneyDefine".equalsIgnoreCase(item.getPropertyName())){
						String str2=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
						String[] moneyDefine1=str2.split(",");
						for(int i=0;i<moneyDefine1.length;i++){
							moneyDefine1[i]=moneyDefine1[i].trim();
						}
						List moneyDefineList=Arrays.asList(moneyDefine1);
						param.put("moneyDefine",moneyDefineList);
					}
					//起始截止日期
					if("dateFrom".equalsIgnoreCase(item.getPropertyName())){
						param.put("dateFrom",item.getCompareValue());
					}
					if("dateTo".equalsIgnoreCase(item.getPropertyName())){
						param.put("dateTo",item.getCompareValue());
					}
					//是否
					if("isAudit".equalsIgnoreCase(item.getPropertyName())){
						param.put("isAudit",item.getCompareValue());
					}
					
				}
			}
		}
//		else{
//			param = this.getFilterUI().getParam();
//		}
		try {
			result=PaymentAdvicePrintFacadeFactory.getRemoteInstance().getValue(param);
		} catch (BOSException e) {
//			this.handUIException(e);
		}
	}
	protected void refresh(ActionEvent e) throws Exception {
		try {
			super.refresh(e);
			fillTable();
		}catch(Exception ex){
			
		}
	}
	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showWarning(this, "请先选中行！");
			return;
		}
		if(getSelectedIdValues() != null){
			ArrayList id=getSelectedIdValues();
			int sus=0;
			boolean bool=false;
			for(int i=0;i<id.size();i++){
				if(id.get(i).toString().contains("acterPay")){
					bool=LiquidatedManageFactory.getRemoteInstance().account(BOSUuid.read(id.get(i).toString().replace("acterPay","")));
				}else if(id.get(i).toString().contains("otherPay")){
					bool=LiquidatedManageFactory.getRemoteInstance().account(BOSUuid.read(id.get(i).toString().replace("otherPay","")));
				}
				if(bool){
					sus=sus+1;
				}
			}
			MsgBox.showInfo(this, "成功计算"+sus+"条应收费用违约金！");
		}
		this.destroyWindow();
		super.actionSelect_actionPerformed(e);
	}
}