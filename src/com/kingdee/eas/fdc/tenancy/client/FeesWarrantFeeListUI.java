/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysInfo;
import com.kingdee.eas.fdc.tenancy.PaymentAdvicePrintFacadeFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FeesWarrantFeeListUI extends AbstractFeesWarrantFeeListUI
{
	 private FeesWarrantFeeListFilterUI filterUI = null;
		private CommonQueryDialog commonQueryDialog = null;
		private SellProjectInfo sellProject=null;
		//client到server端的SQL参数
		private Map param=new HashMap();
		//服务端传过来的结果集
		private Map result=null;
		KDTable entry=null;
		
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
	    
	    private FeesWarrantFeeListFilterUI getFilterUI() {
			if (this.filterUI == null) {
				try {
					if(this.getUIContext().get("sellProject")!=null){
						sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
					}
					this.filterUI = new FeesWarrantFeeListFilterUI(this,
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
	    public void onLoad() throws Exception {
			super.onLoad();
			this.tblMain.getColumn("tarea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
			this.tblMain.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
			this.tblMain.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
			this.tblMain.getColumn("balance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
			this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
			this.actionPrint.setVisible(false);
			this.actionPrintPreview.setVisible(false);
			this.actionRemove.setVisible(false);
			this.actionLocate.setVisible(false);
			this.actionAddNew.setVisible(false);
			this.actionView.setVisible(false);
			this.actionEdit.setVisible(false);
			entry=(KDTable) this.getUIContext().get("entry");
			initTable(this.tblMain);
			
			try {
				fillTable();
			}catch(Exception ex){
				
			}
			SimpleKDTSortManager.setTableSortable(tblMain);
			this.tblMain.getColumn("tarea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
			super.actionRefresh_actionPerformed(null);
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
//						if("sellProject.name".equalsIgnoreCase(item.getPropertyName())){
//							param.put("sellProject",item.getCompareValue().toString());
//						}
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
			try {
				result=PaymentAdvicePrintFacadeFactory.getRemoteInstance().getValue(param);
			} catch (BOSException e) {
//				this.handUIException(e);
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
				entry.removeRows();
				ArrayList ids=getSelectedIdValues();
				for(int i=0;i<ids.size();i++){
					entry_addRow(ids.get(i).toString());
				}
			}
			this.destroyWindow();
			super.actionSelect_actionPerformed(e);
		}
    private void entry_addRow(String id) throws EASBizException, BOSException {
    	BOSUuid bosid=null;
    	if(id.contains("acterPay")){//收款明细--T_TEN_TenancyRoomPayListEntry t1 
    		Set customId=new HashSet();
    		bosid=BOSUuid.read(id.replace("acterPay",""));
			TenancyRoomPayListEntryInfo objectValue=TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(bosid));
			TenancyRoomEntryInfo tre=TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(objectValue.getTenRoom().getId()));
			TenancyBillInfo tb=TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(tre.getTenancy().getId()));
//			MoneyDefineInfo mdinfo=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(objectValue.getMoneyDefine().getId()));
			RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(tre.getRoom().getId()));
			IRow row=entry.addRow();
			FeesWarrantEntrysInfo entryinfo=new FeesWarrantEntrysInfo();
			row.setUserObject(entryinfo);
			MoneyDefineInfo mdfine=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(((TenancyRoomPayListEntryInfo)objectValue).getMoneyDefine().getId()));
			row.getCell(FeesWarrantEditUI.COL_TENANCY).setValue(tb);//合同
			row.getCell(FeesWarrantEditUI.COL_TENANCYNAME).setValue(tb.getTenancyName());
			//客户
			TenancyCustomerEntryCollection tcec=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection("select * where tenancyBill ='"+tb.getId()+"'");
			for(int i=0;i<tcec.size();i++){
				customId.add(tcec.get(i).getFdcCustomer().getId());
			}
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",customId,CompareType.INCLUDE));
			evi.setFilter(filter);
			row.getCell(FeesWarrantEditUI.COL_CUSTOMER).setValue(FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(tcec.get(0).getFdcCustomer().getId())));
			row.getCell(FeesWarrantEditUI.COL_ROOM).setValue(room);//房间
			row.getCell(FeesWarrantEditUI.COL_MONEYDEFINE).setValue(mdfine);//款项
			row.getCell(FeesWarrantEditUI.COL_STARTDATE).setValue(objectValue.getStartDate());//开始日期
			row.getCell(FeesWarrantEditUI.COL_ENDDATE).setValue(objectValue.getEndDate());//结束日期
			row.getCell(FeesWarrantEditUI.COL_APPDATE).setValue(objectValue.getAppDate());//应付日期
			row.getCell(FeesWarrantEditUI.COL_ACTREVDATE).setValue(objectValue.getActRevDate());//实收日期
			row.getCell(FeesWarrantEditUI.COL_APPAMOUNT).setValue(objectValue.getAppAmount());//应收金额
			row.getCell(FeesWarrantEditUI.COL_ACTREVAMOUNT).setValue(objectValue.getActAmount());///实收金额
			
		}else if(id.contains("otherPay")){//其他费用---T_TEN_TenBillOtherPay t1 	
			Set customId=new HashSet();
			bosid=BOSUuid.read(id.replace("otherPay",""));
			TenBillOtherPayInfo objectValue=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayInfo(new ObjectUuidPK(bosid));
			TenancyBillInfo tb=TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(objectValue.getHead().getId()));
			IRow row=entry.addRow();
			FeesWarrantEntrysInfo entryinfo=new FeesWarrantEntrysInfo();
			row.setUserObject(entryinfo);
			MoneyDefineInfo mdfine=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(objectValue.getMoneyDefine().getId()));
			row.getCell(FeesWarrantEditUI.COL_TENANCY).setValue(tb);//合同
			row.getCell(FeesWarrantEditUI.COL_TENANCYNAME).setValue(tb.getTenancyName());
			//客户
			TenancyCustomerEntryCollection tcec=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection("select * where tenancyBill ='"+tb.getId()+"'");
			for(int i=0;i<tcec.size();i++){
				customId.add(tcec.get(i).getFdcCustomer().getId());
			}
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",customId,CompareType.INCLUDE));
			evi.setFilter(filter);
			row.getCell(FeesWarrantEditUI.COL_CUSTOMER).setValue(FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(tcec.get(0).getFdcCustomer().getId())));
			row.getCell(FeesWarrantEditUI.COL_MONEYDEFINE).setValue(mdfine);//款项
			row.getCell(FeesWarrantEditUI.COL_STARTDATE).setValue(objectValue.getStartDate());//开始日期
			row.getCell(FeesWarrantEditUI.COL_ENDDATE).setValue(objectValue.getEndDate());//结束日期
			row.getCell(FeesWarrantEditUI.COL_APPDATE).setValue(objectValue.getAppDate());//应付日期
			row.getCell(FeesWarrantEditUI.COL_ACTREVDATE).setValue(objectValue.getActRevDate());//实收日期
			row.getCell(FeesWarrantEditUI.COL_APPAMOUNT).setValue(objectValue.getAppAmount());//应收金额
			row.getCell(FeesWarrantEditUI.COL_ACTREVAMOUNT).setValue(objectValue.getActRevAmount());///实收金额
		}
	}
	private static final Logger logger = CoreUIObject.getLogger(FeesWarrantFeeListUI.class);
    
    /**
     * output class constructor
     */
    public FeesWarrantFeeListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showWarning(this, "请先选中行！");
			return;
		}
		if(getSelectedIdValues() != null){
			entry.removeRows();
			ArrayList ids=getSelectedIdValues();
			for(int i=0;i<ids.size();i++){
				entry_addRow(ids.get(i).toString());
			}
		}
		this.destroyWindow();
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}