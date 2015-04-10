package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.reportone.r1.common.designercore.gui.common.RadioButtonGroup;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 选择界面暂时公用1个,可选数据由编辑界面扩展获取,并全部从编辑界面传入.再选择界面选择后,将选择后的结果再返回编辑界面
 * 后续如果编辑界面不能满足获取数据的要求,此选择界面也要做扩展
 * */
public class SelectRevListUI extends AbstractSelectRevListUI
{
	//可转出款项的背景色,代码逻辑中有通过此颜色来判断是否为转出行,不太合理 TODO 
	private static final Color TO_TRANSFER_BG_COLOR = SHOW_MESSAGE_FG_DEFAULT;

	public static final Color KEY_LOCKED_ROW = new Color(0xE8E8E3);	
	
	
	private static final Color HAS_SELECTED__BG_COLOR = KEY_LOCKED_ROW;
	//--------------------------- 列名        -----------------------
	private static final String COL_TRANSFER_AMOUNT = "transferAmount";
    private static final String COL_HAS_TRANSFERRED_AMOUNT = "hasTransferredAmount";
	private static final String COL_HAS_REFUNDMENT_AMOUNT = "hasRefundmentAmount";
	private static final String COL_LIMIT_AMOUNT = "limitAmount";
	private static final String COL_REMAIN_AMOUNT = "remainAmount";
	private static final String COL_DESC = "desc";

	private static final String COL_HAS_REV_AMOUNT = "hasRevAmount";
	private static final String COL_ACT_DATE = "actDate";
	private static final String COL_APP_DATE = "appDate";
	private static final String COL_APP_AMOUNT = "appAmount";
	private static final String COL_MONEY_DEFINE = "moneyDefine";
	private static final String COL_IS_SELECTED = "isSelected";
    //----------------------------------------------------
	
	
	//------- 传入的UIContext的key值-----------
	public static final String KEY_PRE_REV_LIST = "preRevList";
	public static final String KEY_DIR_REV_LIST = "dirRevList";
	public static final String KEY_APP_REV_LIST = "appRevList";
	
	public static final String KEY_HAS_SELECTED_IDS = "hasSelectedIDs";
	
	public static final String KEY_APP_REV_REFUNDMENT_LIST = "appRevRefundmentList";
	public static final String KEY_APP_REFUNDMENT_LIST = "appRefundmentList";

	public static final String KEY_TO_TRANSFER_REV_LIST = "toTransferRevList";
	public static final String KEY_MONEYSYSTYPE = "moneySysType";
	public static final String KEY_REV_BIZ_TYPE = "revBillBizType";
	
	public static final String KEY_ADJUST_LIST = "adjustList";
	public static final String KEY_REV_BILL_TYPE = "revBillType";
	
	public static final String KEY_BUILD_REV_LIST_ACTION = "buildRevListAction";
	
	public static final String KEY_IS_SHOW_APP_COL_OF_PREREV = "isShowAppColOfPreRev";
	//---------------------------------------------------
	
	
	//--------返回的UIContext的key值-----------------------
	public static final String KEY_RES_APP_REV_REFUNDMENT_LIST = "resAppRevRefundmentList";
	public static final String KEY_RES_APP_REFUNDMENT_LIST = "resAppRefundmentList";
	public static final String KEY_RES_PRE_REV_LIST = "resPreRevList";
	public static final String KEY_RES_DIR_REV_LIST = "resDirRevList";
	public static final String KEY_RES_APP_REV_LIST = "resAppRevList";
	
	public static final String KEY_RES_APP_REV_TRANSFER_REV_LIST = "resAppRevTransferRevList";
	public static final String KEY_RES_DIR_REV_TRANSFER_REV_LIST = "resDirRevTransferRevList";
	public static final String KEY_RES_PRE_REV_TRANSFER_REV_LIST = "resPreRevTransferRevList";
	
	public static final String KEY_RES_ADJUST_LIST = "resAdjustList";
	
	public static final String KEY_RES_REV_BILL_TYPE = "resRevBillType";
	public static final String KEY_RES_OPT = "YesOrNo";
	//----------------------------------------------------
	
	
	private static final Logger logger = CoreUIObject.getLogger(SelectRevListUI.class);
	
	private IRevAction revAction;
	
	private List toTransferRevList;
	
	private Set hasSelectedIds;
	
    public SelectRevListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	this.btnAdjust.setVisible(false);
    	
    	RadioButtonGroup group = new RadioButtonGroup();
    	group.add(this.btnRev);
    	group.add(this.btnRefundment);
    	group.add(this.btnTransfer);
    	group.add(this.btnAdjust);
    	
    	super.onLoad();
    	
    	boolean isShowAppColOfPreRev = getValue(KEY_IS_SHOW_APP_COL_OF_PREREV) == null ? true : ((Boolean)getValue(KEY_IS_SHOW_APP_COL_OF_PREREV)).booleanValue();
    	
    	initFormaxTable(isShowAppColOfPreRev);
    	initTblDir();
    	RevBillTypeEnum revBillType = (RevBillTypeEnum) getValue(KEY_REV_BILL_TYPE);
    	loadRevBillType(revBillType);
    	
    	revAction = (IRevAction) getValue(KEY_BUILD_REV_LIST_ACTION);
    	
    	Map appRevMap = (Map) getValue(KEY_APP_REV_LIST);
    	List dirRevList = (List) getValue(KEY_DIR_REV_LIST);
    	List preRevList = (List) getValue(KEY_PRE_REV_LIST);
    	List appRevRefundmentList = (List) getValue(KEY_APP_REV_REFUNDMENT_LIST);
    	List appRefundmentList = (List) getValue(KEY_APP_REFUNDMENT_LIST);
    	
    	toTransferRevList = (List) getValue(KEY_TO_TRANSFER_REV_LIST);
    	hasSelectedIds = (Set) getValue(KEY_HAS_SELECTED_IDS);
    	loadAppRevListTable(appRevMap, tblAppRev);
    	
    	String[] fields=new String[tblAppRev.getColumnCount()];
		for(int i=0;i<tblAppRev.getColumnCount();i++){
			fields[i]=tblAppRev.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblAppRev,fields);
    	
    	loadAppRevListTable(dirRevList, tblDirRev);
    	loadAppRevListTable(preRevList, tblPreRev);
    	
    	loadAppRevRefundmentListTable(appRevRefundmentList, tblAppRevRefundment);
    	loadAppRefundmentListTable(appRefundmentList, tblAppRefundment);
    	
    	loadAppRevTransferListTable(appRevMap, toTransferRevList, tblAppRevTransfer);
    	//TODO 直收,预收转款暂不实现
    	loadAppRevTransferListTable(dirRevList, toTransferRevList, tblDirRevTransfer);
    	loadAppRevTransferListTable(preRevList, toTransferRevList, tblPreRevTransfer);
    	
    	setTabbedPanelItemsByRevBillType(revBillType);
    	
    	Boolean obj = (Boolean) getValue(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE);
    	
    	if(obj != null  &&  obj.booleanValue()){
    		
    		
    		this.btnRev.setEnabled(false);
    		this.btnRefundment.setEnabled(false);
    		this.btnTransfer.setEnabled(false);
    		this.btnAdjust.setEnabled(false);
    		
    		if(RevBillTypeEnum.gathering.equals(revBillType)  ||  RevBillTypeEnum.transfer.equals(revBillType)){
    			this.btnRev.setEnabled(true);
    			this.btnTransfer.setEnabled(true);
    		}
    	}
    	
    	//
    	if(this.getUIContext().get("isGathering")!=null){
    		String isGathering = this.getUIContext().get("isGathering").toString();
        	if(isGathering.equals("true")){
        		this.btnTransfer.setEnabled(false);
        		this.btnRefundment.setEnabled(false);
        	}
    	}
    	
    	//Add by zhiyuan_tang 2010/10/14 物业收款时，灰显转款按钮
		if (RevBillTypeEnum.gathering.equals(revBillType) && MoneySysTypeEnum.ManageSys.equals(((MoneySysTypeEnum)getValue(KEY_MONEYSYSTYPE)))) {
			this.btnTransfer.setEnabled(false);
		}
		if(this.getUIContext().get("isInvoice")!=null){
			String isInvoice = this.getUIContext().get("isInvoice").toString();
        	if(isInvoice.equals("true")){
        		this.tabbedPanel.removeAll();
        		this.tabbedPanel.add(tblAppRev,"应收明细");
        		this.btnRev.setVisible(false);
            	this.btnRefundment.setVisible(false);
            	this.btnTransfer.setVisible(false);
            	this.btnAdjust.setVisible(false);
        	}
		}
    }
    
    private void initFormaxTable(boolean isShowAppColOfPreRev)
    {
		this.tblAppRev.checkParsed();
		this.tblAppRev.getColumn(COL_APP_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRev.getColumn(COL_APP_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRev.getColumn(COL_HAS_REV_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRev.getColumn(COL_HAS_REV_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAppRev.getColumn("invoiceAmount").setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRev.getColumn("invoiceAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblPreRev.checkParsed();
		this.tblPreRev.getColumn(COL_APP_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRev.getColumn(COL_APP_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRev.getColumn(COL_HAS_REV_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRev.getColumn(COL_HAS_REV_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRev.getColumn(COL_HAS_REFUNDMENT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRev.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRev.getColumn(COL_HAS_TRANSFERRED_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRev.getColumn(COL_HAS_TRANSFERRED_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRev.getColumn(COL_REMAIN_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRev.getColumn(COL_REMAIN_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		if(!isShowAppColOfPreRev){//物业那边，预收不能显示应收金额和应收日期列
			this.tblPreRev.getColumn(COL_APP_AMOUNT).getStyleAttributes().setHided(true);
			this.tblPreRev.getColumn(COL_APP_DATE).getStyleAttributes().setHided(true);
		}
		
		this.tblAppRevRefundment.checkParsed();
		this.tblAppRevRefundment.getColumn(COL_REMAIN_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevRefundment.getColumn(COL_REMAIN_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRevRefundment.getColumn(COL_LIMIT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevRefundment.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRevRefundment.getColumn(COL_HAS_REFUNDMENT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevRefundment.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		//TODO 
		this.tblAppRevRefundment.getColumn(COL_REMAIN_AMOUNT).getStyleAttributes().setHided(true);
		
		
		this.tblAppRefundment.checkParsed();
		this.tblAppRefundment.getColumn(COL_LIMIT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRefundment.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRefundment.getColumn(COL_HAS_REFUNDMENT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRefundment.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblAppRevTransfer.checkParsed();
		this.tblAppRevTransfer.getColumn(COL_IS_SELECTED).getStyleAttributes().setLocked(true);
		this.tblAppRevTransfer.getColumn(COL_LIMIT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevTransfer.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRevTransfer.getColumn(COL_HAS_TRANSFERRED_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevTransfer.getColumn(COL_HAS_TRANSFERRED_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRevTransfer.getColumn(COL_TRANSFER_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevTransfer.getColumn(COL_TRANSFER_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRevTransfer.getColumn(COL_APP_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevTransfer.getColumn(COL_APP_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblAppRevTransfer.getColumn(COL_HAS_REV_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAppRevTransfer.getColumn(COL_HAS_REV_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblDirRevTransfer.checkParsed();
		this.tblDirRevTransfer.getColumn(COL_LIMIT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblDirRevTransfer.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblDirRevTransfer.getColumn(COL_HAS_TRANSFERRED_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblDirRevTransfer.getColumn(COL_HAS_TRANSFERRED_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblDirRevTransfer.getColumn(COL_TRANSFER_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblDirRevTransfer.getColumn(COL_TRANSFER_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblDirRevTransfer.getColumn(COL_APP_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblDirRevTransfer.getColumn(COL_APP_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblDirRevTransfer.getColumn(COL_HAS_REV_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblDirRevTransfer.getColumn(COL_HAS_REV_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblPreRevTransfer.checkParsed();
		this.tblPreRevTransfer.getColumn(COL_LIMIT_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRevTransfer.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRevTransfer.getColumn(COL_HAS_TRANSFERRED_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRevTransfer.getColumn(COL_HAS_TRANSFERRED_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRevTransfer.getColumn(COL_TRANSFER_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRevTransfer.getColumn(COL_TRANSFER_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPreRevTransfer.getColumn(COL_HAS_REV_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblPreRevTransfer.getColumn(COL_HAS_REV_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblAdjust.checkParsed();
		this.tblAdjust.getColumn(COL_REMAIN_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblAdjust.getColumn(COL_REMAIN_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		FDCTableHelper.disableDelete(this.tblAppRev);
		FDCTableHelper.disableDelete(this.tblPreRev);
		FDCTableHelper.disableDelete(this.tblAppRevRefundment);
		FDCTableHelper.disableDelete(this.tblAppRevTransfer);
    }

	private void loadAppRevTransferListTable(Map appRevList, List toTransferRevList, KDTable table) {
		if(appRevList == null  ||  appRevList.isEmpty() ||  toTransferRevList == null  ||  toTransferRevList.isEmpty()){
			return;
		}
		table.checkParsed();
		List list = new ArrayList();
		for(Iterator itor = appRevList.keySet().iterator(); itor.hasNext(); ){
			List tlist = (List) appRevList.get(itor.next());
			list.addAll(tlist);
		}
		
		loadAppRevTransferListTable(list, toTransferRevList, table);
	}

	private void loadRevBillType(RevBillTypeEnum revBillType) {
		if(RevBillTypeEnum.gathering.equals(revBillType)  ||  revBillType == null){
			this.btnRev.setSelected(true);
			//灰显退款模式 -jiadong
			this.btnRefundment.setEnabled(false);
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			this.btnRefundment.setSelected(true);
			//灰显收款 转款模式
			this.btnRev.setEnabled(false);
			this.btnTransfer.setEnabled(false);
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			this.btnTransfer.setSelected(true);
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			this.btnAdjust.setSelected(true);
		}
	}
	
	private void setLockActRevAmount(IRevListInfo revListInfo,IRow row)
	{
		if(this.getUIContext().get("isInvoice")!=null){
			String isInvoice = this.getUIContext().get("isInvoice").toString();
        	if(isInvoice.equals("true")){
        		BigDecimal appAmount = CRMHelper.getBigDecimal(revListInfo.getAppAmount());
				BigDecimal invoiceAmount = CRMHelper.getBigDecimal(revListInfo.getInvoiceAmount());
				if(invoiceAmount.compareTo(appAmount) >= 0)	{
					row.getStyleAttributes().setBackground(KEY_LOCKED_ROW);
					row.getStyleAttributes().setLocked(true);
				}
        	}
		}else{
			if(!revListInfo.isIsCanRevBeyond()){
				BigDecimal remainAppAmount = CRMHelper.getBigDecimal(revListInfo.getFinalAppAmount());
				BigDecimal allRemainAMount = CRMHelper.getBigDecimal(revListInfo.getAllRemainAmount());
				if(allRemainAMount.compareTo(remainAppAmount) >= 0)	{
					row.getStyleAttributes().setBackground(KEY_LOCKED_ROW);
					row.getStyleAttributes().setLocked(true);
				}
			}
		}
	}
	
	private void loadAppRevTransferListTable(List revList, List toTransferRevList, KDTable table) {
		if(revList == null  ||  revList.isEmpty() ||  toTransferRevList == null  ||  toTransferRevList.isEmpty()){
			return;
		}
		for(int i=0; i<revList.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) revList.get(i);
			IRow row = table.addRow();
			row.setUserObject(revListInfo);
			setLockActRevAmount(revListInfo,row);
			setColValue(row, COL_IS_SELECTED, Boolean.FALSE);
			setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
			setColValue(row, COL_APP_AMOUNT, revListInfo.getAppAmount());
			setColValue(row, COL_APP_DATE, revListInfo.getAppDate());
	
			setColValue(row, COL_HAS_REV_AMOUNT, revListInfo.getActRevAmount());
			
			setColValue(row, COL_DESC, revListInfo.getDesc());
			
			setExpandColsValue(revListInfo, table, row);
		}
	}

    //增加扩展列并设置扩展列的值
	private void setExpandColsValue(IRevListInfo revList, KDTable table, IRow row) {
		if (revAction != null) {
			List expandCols = revAction.getExpandCols(revList.getRevListTypeEnum());
			if(expandCols == null){
				return;
			}
			for (int j = 0; j < expandCols.size(); j++) {
				Object[] tmps = (Object[]) expandCols.get(j);
				String tmpsColKey = (String) tmps[0];
				if (table.getColumn(tmpsColKey) == null) {
					int tmpColIndex = ((Integer) tmps[2]).intValue();
					IColumn tmpCol = table.addColumn(tmpColIndex);
					tmpCol.getStyleAttributes().setLocked(true);
					tmpCol.setKey(tmpsColKey);
					table.getHeadRow(0).getCell(tmpsColKey).setValue(tmps[1]);
				}
				setColValue(row, tmpsColKey, CRMHelper.getValue(revList, (String) tmps[3]));
			}
		}
	}
	
	private void loadAppRefundmentListTable(List appRefundmentList, KDTable table) {
		if(appRefundmentList == null){
			return;
		}
		for(int i=0; i<appRefundmentList.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) appRefundmentList.get(i);
			IRow row = table.addRow();
			row.setUserObject(revListInfo);
			setColValue(row, COL_IS_SELECTED, Boolean.FALSE);
			setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
			setColValue(row, COL_LIMIT_AMOUNT, revListInfo.getRemainLimitAmount());
			setColValue(row, COL_HAS_REFUNDMENT_AMOUNT, revListInfo.getHasRefundmentAmount());
			setColValue(row, COL_DESC, revListInfo.getDesc());
		}
	}

	private void loadAppRevRefundmentListTable(List appRevRefundmentList, KDTable table) {
		if(appRevRefundmentList == null){
			return;
		}
		for(int i=0; i<appRevRefundmentList.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) appRevRefundmentList.get(i);
			IRow row = table.addRow();
			row.setUserObject(revListInfo);
			setColValue(row, COL_IS_SELECTED, Boolean.FALSE);
			setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
//			setColValue(row, COL_REMAIN_AMOUNT, revListInfo.getAppAmount());
			setColValue(row, COL_LIMIT_AMOUNT, revListInfo.getRemainLimitAmount());
			setColValue(row, COL_HAS_REFUNDMENT_AMOUNT, revListInfo.getHasRefundmentAmount());
			
			setColValue(row, COL_DESC, revListInfo.getDesc());
			
			setExpandColsValue(revListInfo, table, row);
		}
	}
	
	private void loadAppRevListTable(List revList,KDTable table)
	{
		loadAppRevListTable(revList,table,Color.WHITE);
	}
	
	private void loadAppRevListTable(List revList,KDTable table,Color color)
	{
		if(revList==null)return;
		table.checkParsed();
		for(int i=0; i<revList.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) revList.get(i);
			IRow row = table.addRow();
			row.getStyleAttributes().setBackground(color);
			row.setUserObject(revListInfo);
			setLockActRevAmount(revListInfo,row);
			if(revListInfo instanceof ITenancyPayListInfo){
				setColValue(row, "leaseSeq", ((ITenancyPayListInfo)revListInfo).getLeaseSeq());
			}else if(revListInfo instanceof TenBillOtherPayInfo){
				setColValue(row, "leaseSeq", ((TenBillOtherPayInfo)revListInfo).getLeaseSeq()==0?null:((TenBillOtherPayInfo)revListInfo).getLeaseSeq());
			}
			setColValue(row, "invoiceAmount", revListInfo.getInvoiceAmount());
			setColValue(row, COL_IS_SELECTED, Boolean.FALSE);
			setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
			setColValue(row, COL_APP_AMOUNT, revListInfo.getAppAmount());
			setColValue(row, COL_APP_DATE, revListInfo.getAppDate());
			//setColValue(row, COL_HAS_REV_AMOUNT, revListInfo.getAllRemainAmount());	
			setColValue(row, COL_HAS_REV_AMOUNT, revListInfo.getActRevAmount());	
			setColValue(row, COL_REMAIN_AMOUNT, revListInfo.getAllRemainAmount());
			setColValue(row, COL_HAS_REFUNDMENT_AMOUNT, revListInfo.getHasRefundmentAmount());
			setColValue(row, COL_HAS_TRANSFERRED_AMOUNT, revListInfo.getHasTransferredAmount());
			setColValue(row, COL_DESC, revListInfo.getDesc());
			
			setExpandColsValue(revListInfo, table, row);
			
			lockHasSelectedRows(row, revListInfo);
		}
	}
	
	private void lockHasSelectedRows(IRow row, IRevListInfo revListInfo) {
		if(hasSelectedIds == null  ||  hasSelectedIds.isEmpty()){
			return;
		}
		
		if(revListInfo == null  ||  revListInfo.getId() == null){
			return;
		}
		
		if(hasSelectedIds.contains(revListInfo.getId().toString())){
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(HAS_SELECTED__BG_COLOR);
		}
	}

	private void loadAppRevListTable(Map revMap, KDTable table){
		if(revMap == null){
			return;
		}		
		for(Iterator itor = revMap.keySet().iterator(); itor.hasNext();)
		{
			String str = (String)itor.next();
			List revList = (List)revMap.get(str);
			if(FDCReceivingBillEditUI.KEY_TENPAYLIST.equals(str))
			{
				loadAppRevListTable(revList,table,Color.WHITE);
			}else if(FDCReceivingBillEditUI.KEY_TENOTHERPAYLIST.equals(str))
			{
				loadAppRevListTable(revList,table,Color.WHITE);
			}else{
				loadAppRevListTable(revList,table, Color.WHITE);
			}
//			else if(FDCReceivingBillEditUI.KEY_OBLIGATE.equals(str))
//			{
//				loadAppRevListTable(revList,table,Color.WHITE);
//			}
		}		
	}
	
	private void setColValue(IRow row, String colKey, Object value) {
		CRMClientHelper.setColValue(row, colKey, value);
	}

	private Object getValue(String key) {
    	return this.getUIContext().get(key);
    }
	
    protected void btnRev_actionPerformed(ActionEvent e) throws Exception {
    	setTabbedPanelVisiable();
    }
    
    private void setTabbedPanelVisiable() {
    	RevBillTypeEnum revBillType = null;
    	if(this.btnRev.isSelected()){
    		revBillType = RevBillTypeEnum.gathering;
    	}else if(this.btnRefundment.isSelected()){
    		revBillType = RevBillTypeEnum.refundment;
    	}else if(this.btnTransfer.isSelected()){
    		revBillType = RevBillTypeEnum.transfer;
    	}else if(this.btnAdjust.isSelected()){
    		revBillType = RevBillTypeEnum.adjust;
    	}else{
    		logger.error("my god.error impossible.");
    		return;
    	}
    	
    	setTabbedPanelItemsByRevBillType(revBillType);
	}

	private void setTabbedPanelItemsByRevBillType(RevBillTypeEnum revBillType) {
		this.tabbedPanel.removeAll();
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			this.tabbedPanel.add(tblAppRev,"应收收款");
			//by tim_gao 根据客户收款不显示直收
			if(this.getUIContext().get("ISBYCUSTOMER")!=null){
				
		    		String isByCustomer = this.getUIContext().get("ISBYCUSTOMER").toString();
		        	if(isByCustomer.equals("false")){
		        		this.tabbedPanel.add(pnlDirRev,"直收收款");
		        	}
		     }else{
				this.tabbedPanel.add(pnlDirRev,"直收收款");
			}
			this.tabbedPanel.add(tblPreRev,"预收收款");
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			this.tabbedPanel.add(tblAppRevRefundment,"退款");
//			this.tabbedPanel.add(tblAppRefundment,"应退退款");
		}if(RevBillTypeEnum.transfer.equals(revBillType)){
			this.tabbedPanel.add(tblAppRevTransfer,"应收转款");
			//TODO 直收转款和预收转款暂不支持
//			this.tabbedPanel.add(tblDirRevTransfer,"直收转款");
//			this.tabbedPanel.add(tblPreRevTransfer,"预收转款");
		}if(RevBillTypeEnum.adjust.equals(revBillType)){
			this.tabbedPanel.add(tblAdjust,"调整");
		}
	}

	protected void btnRefundment_actionPerformed(ActionEvent e) throws Exception {
		setTabbedPanelVisiable();
    }
    
    protected void btnTransfer_actionPerformed(ActionEvent e) throws Exception {
    	setTabbedPanelVisiable();
    }
    
    protected void btnAdjust_actionPerformed(ActionEvent e) throws Exception {
    	setTabbedPanelVisiable();
    }
    
    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	RevBillTypeEnum resRevBillType = getResRevBillType();
    	
    	if(RevBillTypeEnum.gathering.equals(resRevBillType)){
    		this.getUIContext().put(KEY_RES_APP_REV_LIST, getSelectedRevList(tblAppRev));
        	this.getUIContext().put(KEY_RES_DIR_REV_LIST, getDirRevList(tblDirRev));
        	this.getUIContext().put(KEY_RES_PRE_REV_LIST, getSelectedRevList(tblPreRev));	
    	}else if(RevBillTypeEnum.refundment.equals(resRevBillType)){
    		this.getUIContext().put(KEY_RES_APP_REV_REFUNDMENT_LIST, getSelectedRevList(tblAppRevRefundment));
        	this.getUIContext().put(KEY_RES_APP_REFUNDMENT_LIST, getSelectedRevList(tblAppRefundment));
    	}else if(RevBillTypeEnum.transfer.equals(resRevBillType)){
    		//校验 转款金额是否大于可转金额
    		vifyTransAmount(getSelectedTransferList(tblAppRevTransfer));
    		this.getUIContext().put(KEY_RES_APP_REV_TRANSFER_REV_LIST, getSelectedTransferList(tblAppRevTransfer));
    		//TODO 以下2条可以忽略
//    		this.getUIContext().put(KEY_RES_DIR_REV_TRANSFER_REV_LIST, getSelectedTransferList(tblDirRevTransfer));
//    		this.getUIContext().put(KEY_RES_PRE_REV_TRANSFER_REV_LIST, getSelectedTransferList(tblPreRevTransfer));
    	}else if(RevBillTypeEnum.adjust.equals(resRevBillType)){
    		this.getUIContext().put(KEY_RES_ADJUST_LIST, getSelectedRevList(tblAdjust));
    	}
    	this.getUIContext().put(KEY_RES_REV_BILL_TYPE, resRevBillType);
    	this.getUIContext().put(KEY_RES_OPT, Boolean.TRUE);
    	
    	this.destroyWindow();
    }
    
    /*
     * 校验转款金额是否大于可转金额
     * map存储格式就是转入款项行对应的转出款项行。key
     * 如转入款项租金(key)对应转出款项预留定金和预收款(value),而value是一个list,存的
     * 时具体的预留定金和预收款信息
     * 如有多个转入款项，map则存入每个转入款项对应的转出款项行
     */
    private void vifyTransAmount(Map map)
    {
    	if(map == null){
			return;
		}    
		Set keys = map.keySet();	
		Map kezhuanMap = new HashMap();
		Map yizhuanMap = new HashMap();
		//最大可转金额
		for (int j = 0; j < toTransferRevList.size(); j++) {
			IRevListInfo toTransRevListInfo = (IRevListInfo) toTransferRevList.get(j);
			BigDecimal transferAmount = toTransRevListInfo.getRemainLimitAmount().divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
			kezhuanMap.put(toTransRevListInfo.getId().toString(), transferAmount);
		}
		//合计转款金额--针对明细校验
		for(Iterator itor = keys.iterator(); itor.hasNext(); ){	
			BigDecimal sumToAmount = new BigDecimal(0);
            Object[] keyObjs = (Object[]) itor.next();		
			List list = (List) map.get(keyObjs);
				for(int j=0; j<list.size(); j++){
				    Object[] objs = (Object[]) list.get(j);
					IRevListInfo tmpRevListInfo = (IRevListInfo) objs[0];
					BigDecimal transAmount = (BigDecimal)objs[1];
					if(yizhuanMap.get(tmpRevListInfo.getId().toString())==null)
					{
						sumToAmount = transAmount;
						yizhuanMap.put(tmpRevListInfo.getId().toString(),sumToAmount);
					}else
					{
						sumToAmount = (BigDecimal)yizhuanMap.get(tmpRevListInfo.getId().toString());
						sumToAmount = sumToAmount.add(transAmount);
						yizhuanMap.put(tmpRevListInfo.getId().toString(),sumToAmount);
					}
					if(kezhuanMap.get(tmpRevListInfo.getId().toString())!=null)
					{
						BigDecimal transferAmount = (BigDecimal)kezhuanMap.get(tmpRevListInfo.getId().toString());
						if(sumToAmount.compareTo(transferAmount)==1)
						{
							MsgBox.showInfo(//tmpRevListInfo.getDesc()+
									tmpRevListInfo.getMoneyDefine().getName()+" "+"转款金额"+sumToAmount+"不能大于可转金额"+transferAmount);
							this.abort();
						}
					}
				}				
		}
//		
//		if(sumToAmount.compareTo(sumTransferAmount)==1)
//		{
//			MsgBox.showInfo("转款金额"+sumToAmount+"不能大于可转金额"+sumTransferAmount);
//			this.abort();
//		}
    }
    
    protected void tblAppRevTransfer_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IColumn col = this.tblAppRevTransfer.getColumn(colIndex);
		if(col == null){
			return;
		}
		
		int rowIndex = e.getRowIndex();
		IRow row = this.tblAppRevTransfer.getRow(rowIndex);
		if(row == null){
			return;
		}
		
		//如果该行已经被锁定，则不允许再转入
		if(row.getStyleAttributes().isLocked()){
			return;
		}
		
		String colKey = col.getKey();
		if(COL_IS_SELECTED.equals(colKey)){
			boolean isOutRow = TO_TRANSFER_BG_COLOR.equals(row.getCell(COL_TRANSFER_AMOUNT).getStyleAttributes().getBackground());//是否是转出行
			if(isOutRow) return;
			
			Object nowValue = row.getCell(COL_IS_SELECTED).getValue();			
			boolean isSelected = nowValue == null ? false : ((Boolean)nowValue).booleanValue();
			row.getCell(COL_IS_SELECTED).setValue(new Boolean(!isSelected));
			isSelected = !isSelected;	

			if (isSelected) {
				List tmpList = new ArrayList();
				for (int j = 0; j < toTransferRevList.size(); j++) {
					IRevListInfo toTransRevListInfo = (IRevListInfo) toTransferRevList.get(j);
					IRow tmpRow = tblAppRevTransfer.addRow(rowIndex + j + 1);
//					tmpRow.getStyleAttributes().setBackground(TO_TRANSFER_BG_COLOR);
					tmpRow.setUserObject(toTransRevListInfo);

//					setColValue(tmpRow, COL_IS_SELECTED, Boolean.FALSE);
					setColValue(tmpRow, COL_MONEY_DEFINE, "    " + (toTransRevListInfo.getMoneyDefine() == null ? "" : toTransRevListInfo.getMoneyDefine().getName()));
					setColValue(tmpRow, COL_LIMIT_AMOUNT, toTransRevListInfo.getRemainLimitAmount());
					setColValue(tmpRow, COL_HAS_TRANSFERRED_AMOUNT, toTransRevListInfo.getHasTransferredAmount());
					setColValue(tmpRow, COL_DESC, toTransRevListInfo.getDesc());
					tmpRow.getCell(COL_TRANSFER_AMOUNT).getStyleAttributes().setBackground(TO_TRANSFER_BG_COLOR);
					tmpRow.getCell(COL_TRANSFER_AMOUNT).getStyleAttributes().setLocked(false);
					tmpList.add(new Object[]{toTransRevListInfo, tmpRow});
				}
				
				rowsMap.put(row.getUserObject(), new Object[]{row, tmpList});
			}else{
				Object obj = rowsMap.get(row.getUserObject());
				if(obj == null){
					return;
				}
				
				Object[] objs = (Object[]) obj;
				
				List list = (List)objs[1];
				if(list == null  ||  list.isEmpty()){
					return;
				}
				
				for(int i=0; i<list.size(); i++){
					this.tblAppRevTransfer.removeRow(rowIndex + 1);
				}
				row.getCell(COL_TRANSFER_AMOUNT).setValue(null);
				rowsMap.remove(row.getUserObject());
			}
		
		}
    }
    
    private IRow findKey(Map map, IRow row){
		Set keys = map.keySet();
		for(Iterator itor = keys.iterator(); itor.hasNext(); ){
			Object keyObj = (IRow) itor.next();
			Object[] values = (Object[]) map.get(keyObj);
			
			List tmpList = (List)values[1];
			for(int i=0; i<tmpList.size(); i++){
				Object[] tmpValues = (Object[]) tmpList.get(i);
				
				IRow tmpRow = (IRow) tmpList.get(i);
				if(tmpRow != null  &&  tmpRow.equals(row)){
					return tmpRow;
				}
			}
		}
		return null;
    }
    
    protected void tblAppRevTransfer_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IColumn col = this.tblAppRevTransfer.getColumn(colIndex);
		if(col == null){
			return;
		}
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(!CRMClientHelper.isValueChanged(e)){
			return;
		}
		
		int rowIndex = e.getRowIndex();
		IRow row = this.tblAppRevTransfer.getRow(rowIndex);
		if(row == null){
			return;
		}
		IRevListInfo revListInfo = (IRevListInfo)row.getUserObject();
		String colKey = col.getKey();
		if(COL_TRANSFER_AMOUNT.equals(colKey)){
			//如果修改了转出明细的转出金额,汇总转出金额到对应转入明细的转入金额上
			IRow toRow = null;
			for(int i=rowIndex - 1; i>=0; i--){
				IRow tmpRow = this.tblAppRevTransfer.getRow(i);				
				if(tmpRow != null  &&  !TO_TRANSFER_BG_COLOR.equals(tmpRow.getCell(COL_TRANSFER_AMOUNT).getStyleAttributes().getBackground())){
					toRow = tmpRow;
					break;
				}
			}
			
			if(toRow == null){
				return;
			}
			IRevListInfo revInfo = (IRevListInfo)toRow.getUserObject();
			if(revListInfo.getId().toString().equals(revInfo.getId().toString()))
			{
				MsgBox.showInfo("同一房间的"+revInfo.getMoneyDefine().getName()+"不能转款");
				row.getCell(COL_TRANSFER_AMOUNT).setValue(null);
				return;
			}
			BigDecimal oldAmount = CRMHelper.getBigDecimal((BigDecimal) oldValue);
			BigDecimal newAmount = CRMHelper.getBigDecimal((BigDecimal) newValue);
			
			BigDecimal limitAmount = (BigDecimal) row.getCell(COL_LIMIT_AMOUNT).getValue();
			if(newAmount.compareTo(limitAmount)==1)
			{
				MsgBox.showInfo("转款金额不能大于可转金额");
				row.getCell(COL_TRANSFER_AMOUNT).setValue(null);
				BigDecimal srcAmount = (BigDecimal) toRow.getCell(COL_TRANSFER_AMOUNT).getValue();
				toRow.getCell(COL_TRANSFER_AMOUNT).setValue(CRMHelper.getBigDecimal(srcAmount).subtract(oldAmount));
				this.abort();
			}
			
			BigDecimal srcAmount = (BigDecimal) toRow.getCell(COL_TRANSFER_AMOUNT).getValue();
			
			toRow.getCell(COL_TRANSFER_AMOUNT).setValue(CRMHelper.getBigDecimal(srcAmount).add(newAmount.subtract(oldAmount)));
		}
    }
    
    //结构为Map<IRevListInfo, Object[]{IRow, List<Object[]{IRevListInfo, IRow}>}>
    private Map rowsMap = new HashMap();
    
  //Map<Object{IRevList, Amount}, List<Object{IRevList, Amount}>>
    private Map getSelectedTransferList(KDTable tbl) {
    	Map map = new HashMap();
    	for(int i=0; i<tbl.getRowCount(); i++){
    		IRow row = tbl.getRow(i);
    		Boolean b = (Boolean) row.getCell(COL_IS_SELECTED).getValue();
    		if(b == null  ||  !b.booleanValue()){
    			continue;
    		}
    		
    		BigDecimal toAmount = (BigDecimal) row.getCell(COL_TRANSFER_AMOUNT).getValue();
    		if(CRMHelper.isZeroOrNull(toAmount)){
    			continue;
    		}
    		
    		IRevListInfo toRevList = (IRevListInfo) row.getUserObject();
    		Object[] toObjsDes = new Object[]{toRevList, toAmount}; 
    		
    		//Map<IRevListInfo, Object[]{IRow, List<Object[]{IRevListInfo, IRow}>}>
    		Object obj = rowsMap.get(row.getUserObject());
    		if(obj == null){
    			continue;
    		}
    		
    		Object[] tt = (Object[]) obj;
    		
    		List froms1 = (List) tt[1];
    		List froms = new ArrayList();
    		for(int j=0; j<froms1.size(); j++){
    			Object[] objst = (Object[]) froms1.get(j);
    			IRow fromRow = (IRow) objst[1];
    			IRevListInfo fromRevList = (IRevListInfo) objst[0];
    			BigDecimal fromAmount = CRMHelper.getBigDecimal((BigDecimal) fromRow.getCell(COL_TRANSFER_AMOUNT).getValue());
    			if(CRMHelper.isZeroOrNull(fromAmount)){
    				continue;
    			}
    			Object[] tmpObjs = new Object[]{fromRevList, fromAmount};
    			froms.add(tmpObjs);
    		}
    		map.put(toObjsDes, froms);
    	}
		return map;
	}
    
    protected void btnAddDir_actionPerformed(ActionEvent e) throws Exception {
    	if(revAction!=null) {
    		IRevListInfo revListInfo = revAction.createRevListInfo();
    		if(revListInfo!=null) {
	    		IRow row = this.tblDirRev.addRow();
		    	row.getCell(COL_DESC).setValue(revListInfo.getDesc());
		    	row.setUserObject(revListInfo);
    		}else{
        		MsgBox.showInfo((RevBizTypeEnum)getValue(KEY_REV_BIZ_TYPE)+"不支持直收收款！");
        		return;
    		}
    	}else{
    		MsgBox.showInfo((RevBizTypeEnum)getValue(KEY_REV_BIZ_TYPE)+"不支持直收收款！");
    		return;
    	}
    }
    
    protected void btnDelDir_actionPerformed(ActionEvent e) throws Exception {
    	super.btnDelDir_actionPerformed(e);
    	int activeRowIndex = this.tblDirRev.getSelectManager()
		 .getActiveRowIndex();
		IRow row = this.tblDirRev.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		this.tblDirRev.removeRow(activeRowIndex);
    }
    
    private void initTblDir()
    {
    	this.tblDirRev.checkParsed();
		this.tblDirRev
		.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDBizPromptBox f7MoenyDefine = new KDBizPromptBox();
		f7MoenyDefine
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", ((MoneySysTypeEnum)getValue(KEY_MONEYSYSTYPE)).getValue()));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("moneyType",
		// MoneyTypeEnum.TAXFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.BREACHFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.TRADEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5 or #6 or #7 or #8)");
		view.setFilter(filter);
		f7MoenyDefine.setEntityViewInfo(view);
		f7MoenyDefine.setEditable(true);
		f7MoenyDefine.setDisplayFormat("$name$");
		f7MoenyDefine.setEditFormat("$number$");
		f7MoenyDefine.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7MoenyDefine);
		//Update by zhiyuan_tang 2010/10/11 Start
		if (MoneySysTypeEnum.ManageSys.equals(((MoneySysTypeEnum)getValue(KEY_MONEYSYSTYPE)))) {
			//如果是物业系统，则取非集团的款项类型
			f7MoenyDefine = new KDBizPromptBox();
			f7MoenyDefine
					.setQueryInfo("com.kingdee.eas.fdc.propertymgmt.app.SellOrgMoneyDefineF7Query");
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sysType", ((MoneySysTypeEnum)getValue(KEY_MONEYSYSTYPE)).getValue()));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId(), CompareType.EQUALS));
			//isGroup为true时
			filter.getFilterItems().add(new FilterItemInfo("isGroup", Boolean.TRUE));
			view.setFilter(filter);
			f7MoenyDefine.setEntityViewInfo(view);
			f7MoenyDefine.setEditable(true);
			f7MoenyDefine.setDisplayFormat("$name$");
			f7MoenyDefine.setEditFormat("$number$");
			f7MoenyDefine.setCommitFormat("$number$");
			f7Editor = new KDTDefaultCellEditor(f7MoenyDefine);
		}
		//Update by zhiyuan_tang 2010/10/11 End
		this.tblDirRev.getColumn("moneyDefine").setEditor(f7Editor);
		this.tblDirRev.getColumn("moneyDefine").getStyleAttributes()
		.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblDirRev.getColumn(COL_APP_AMOUNT).setEditor(numberEditor);
		this.tblDirRev.getColumn(COL_APP_AMOUNT).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblDirRev.getColumn(COL_APP_AMOUNT).getStyleAttributes()
		.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.tblDirRev.getColumn(COL_APP_DATE).setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblDirRev.getColumn(COL_APP_DATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblDirRev.getColumn(COL_APP_DATE).getStyleAttributes();
		this.tblDirRev.getColumn(COL_APP_DATE).getStyleAttributes()
		.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		//物业测试时提的bug，直收收款，没有已退，已转，剩余金额列。
		this.tblDirRev.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setHided(true);
		this.tblDirRev.getColumn(COL_HAS_TRANSFERRED_AMOUNT).getStyleAttributes().setHided(true);
		this.tblDirRev.getColumn(COL_REMAIN_AMOUNT).getStyleAttributes().setHided(true);
		this.tblDirRev.getColumn(COL_HAS_REV_AMOUNT).getStyleAttributes().setHided(true);
		this.tblDirRev.getColumn(COL_ACT_DATE).getStyleAttributes().setHided(true);
		
		this.tblDirRev.getColumn(COL_DESC).getStyleAttributes().setLocked(true);
		
    }
    
    private List getDirRevList(KDTable tbl)
    {
    	List list = new ArrayList();
    	for(int i=0;i<tbl.getRowCount();i++)
    	{
    		IRow row = tbl.getRow(i);
    		if(row.getCell(COL_MONEY_DEFINE).getValue()==null)
    		{
    			MsgBox.showInfo("直收明细款项类型不能为空!");
    			this.abort();
    		}else if(row.getCell(COL_APP_AMOUNT).getValue()==null)
    		{
    			MsgBox.showInfo("直收明细应收金额不能为空!");
    			this.abort();
    		}else if(row.getCell(COL_APP_DATE).getValue()==null)
    		{
    			MsgBox.showInfo("直收明细应收日期不能为空!");
    			this.abort();
    		}
    		if(row.getUserObject()!=null)
    		{
    			IRevListInfo irevInfo = (IRevListInfo)row.getUserObject();
    			irevInfo.setMoneyDefine((MoneyDefineInfo)row.getCell(COL_MONEY_DEFINE).getValue());
    			irevInfo.setAppAmount((BigDecimal)row.getCell(COL_APP_AMOUNT).getValue());
    			irevInfo.setAppDate((Date)row.getCell(COL_APP_DATE).getValue());
    			irevInfo.setRevMoneyType(RevMoneyTypeEnum.DirectRev);
    			//直收中实付日期就等于应收日期
    			//Delete by zhyuan_tang 2010/10/12 还是等登陆完之后再更新这个字段吧，还没有收款就显示一个上次收款日期实在是很怪
//    			irevInfo.setActRevDate((Date)row.getCell(COL_APP_DATE).getValue());
    			list.add(row.getUserObject());
    		}
    	}
    	return list;
    }

     protected void tblDirRev_editStopped(KDTEditEvent e) throws Exception {
    	int colIndex = e.getColIndex();
 		String colKey = this.tblDirRev.getColumnKey(colIndex);
 		IRow row = this.tblDirRev.getRow(e.getRowIndex());
 		if(COL_APP_AMOUNT.equals(colKey))
 		{
 			if(row.getCell(COL_APP_AMOUNT).getValue()!=null)
 			{
 				BigDecimal appAmount = (BigDecimal)row.getCell(COL_APP_AMOUNT).getValue();
 				row.getCell(COL_HAS_REV_AMOUNT).setValue(appAmount);
 			}
 		}
    }
     
	private List getSelectedRevList(KDTable tbl) {
    	List list = new ArrayList();
    	for(int i=0; i<tbl.getRowCount(); i++){
    		IRow row = tbl.getRow(i);
    		Boolean tmp = (Boolean) row.getCell(COL_IS_SELECTED).getValue();
    		if(tmp != null  &&  tmp.booleanValue()){
    			list.add(row.getUserObject());
    		}
    	}
    	return list;
    }

	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
    }
    
    private RevBillTypeEnum getResRevBillType(){
    	if(this.btnRev.isSelected()){
    		return RevBillTypeEnum.gathering;
    	} else if(this.btnRefundment.isSelected()){
    		return RevBillTypeEnum.refundment;
    	} else if(this.btnTransfer.isSelected()){
    		return RevBillTypeEnum.transfer;
    	} else if(this.btnAdjust.isSelected()){
    		return RevBillTypeEnum.adjust;
    	}else{
    		logger.error("error impossible.");
    		return null;
    	}
    }
    
}