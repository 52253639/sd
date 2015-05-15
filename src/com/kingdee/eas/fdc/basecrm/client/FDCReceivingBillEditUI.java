package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.propertymgmt.client.PPMNewReceiveBillEditUI;
import com.kingdee.eas.fdc.propertymgmt.client.PPMNewReceiveBillListUI;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringCollection;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringFactory;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEReceivingBillEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEReceivingBillListUI;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.client.TENReceivingBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TENReceivingBillListUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class FDCReceivingBillEditUI extends AbstractFDCReceivingBillEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(FDCReceivingBillEditUI.class);
	
	//区分收款明细中是租金还是其他应收
	public static final String KEY_TENPAYLIST = "1tenPayList";
	public static final String KEY_TENOTHERPAYLIST = "2tenOtherPayList";
	public static final String KEY_OBLIGATE= "obligate";
	//------------------------ 传入收款单的参数-------------
	//是否需要锁定部分控件.场景如诚意认购序时簿点收款进来此界面,不再需要修改收款单据类型和收款业务类型,可以将这2个控件锁定
	public static final String KEY_IS_LOCK_BILL_TYPE = "isLockBillType";
	
    public static final String KEY_REV_BIZ_TYPE = "revBizType";
    public static final String KEY_REV_BILL_TYPE = "revBillType";
    public static final String KEY_SELL_PROJECT = "sellProject";
    public static final String KEY_TENANCY_BILL = "tenancyBill";
    public static final String KEY_SINCER_OBLIGATE = "sincerObligate";
    
    //-------------------------------------------------
	
	//--------------------- 分录上的列名 --------------------
	protected static final String COL_MONEY_DEFINE = "moneyDefine";
	private static final String COL_FROM_MONEY_DEFINE = "fromMoneyDefine";
	private static final String COL_FROM_ACCOUNT = "fromAccount";
	private static final String COL_STLE_COUNT = "stleCount";
	private static final String COL_STLE_TYPE = "stleType";
	
	private static final String COL_STLE_NUMBER = "stleNumber";
	private static final String COL_REV_ACCOUNT = "revAccount";
	private static final String COL_REV_BANK_ACCOUNT = "revBankAccount";
	private static final String COL_CUS_ACCOUNT = "custAccount";
	protected static final String COL_REV_AMOUNT = "revAmount";
	
	private static final String COL_LOC_AMOUNT = "locAmount";
	private static final String COL_APP_AMOUNT = "appAmount";
	private static final String COL_ACT_REV_AMOUNT = "actRevAmount";
	private static final String COL_REMISSION_AMOUNT = "remissionAmount";
	protected static final String COL_LIMIT_AMOUNT = "limitAmount";
	
	private static final String COL_HAS_REFUNDMENT_AMOUNT = "hasRefundmentAmount";
	private static final String COL_OPP_ACCOUNT = "oppAccount";
	
	private static final String COL_SUPPLY_ORG = "supplyOrg";
	private static final String COL_SUPPLY_DES = "supplyDes";
	//-------------------------------------------------

    String[] s0 = {"moneyType", "",              "",   "settlementCount", "结算方式", "收款科目", "收款帐户", "客户账号", "收款金额", "折算金额", "应收金额", "已收金额", "减免金额",  ""       , ""       };
    String[] s1 = {"收款款项",  "",              "",   "结算笔数", "结算方式", "收款科目", "收款帐户", "客户账号", "收款金额", "折算金额", "应收金额", "已收金额", "减免金额",  ""       , ""       };
    String[] s2 = {"退款款项",  "",              "",   "结算笔数", "结算方式", "退款科目", "退款帐户", "客户账号", "退款金额", "折算金额", "",         "",        ""       ,  "可退金额", "已退金额"};
    String[] s3 = {"转入款项",  "转出款项", "转出科目", "",         ""        , "",       "",         "",         "转款金额", "折算金额", "应收金额", "已收金额", "减免金额", "",         ""       };
    private Set oldColKeySet = new HashSet();

    public FDCReceivingBillEditUI() throws Exception
    {
        super();
    }
    
    protected boolean isLoadFields = false; 

    //继承的界面类将此函数重写了,这里写什么都没用了。。框架缺陷哎
    public void storeFields() {
    	super.storeFields();
//    	this.storeEntry();
    }
    
  //及其郁闷的函数,抽出来只是为了供子类调用
    protected void storeFields_forChildren() {
    	FDCReceivingBillInfo fdcRev = this.editData;
//    	fdcRev.setRoomLongNumber(this.txtRoomLongNumber.getText());
    	if(RevBillTypeEnum.refundment.equals(fdcRev.getRevBillType())){
    		fdcRev.setAmount(FDCHelper.ZERO.subtract(getBigDecimal(fdcRev.getAmount())));
    		fdcRev.setOriginalAmount(fdcRev.getAmount());//TODO 不考虑汇率
    	}
    	
    	//收款系统客户
//    	fdcRev.setCustomer((CustomerInfo) this.f7Customer.getValue());
    	
    	//交费房地产客户
    	fdcRev.getFdcCustomers().clear();
    	Object obj = this.f7FdcCustomers.getValue();
    	if(obj instanceof Object[]){
    		Object[] os = (Object[]) obj;
    		if (os != null && os.length > 0) {
				for (int i = 0; i < os.length; i++) {
					if (os[i] != null  &&  os[i] instanceof FDCCustomerInfo) {
						RevFDCCustomerEntryInfo revFdcCusEntry = new RevFDCCustomerEntryInfo();
						revFdcCusEntry.setFdcCustomer((FDCCustomerInfo) os[i]);
						
						//收款系统客户取交费房地产客户的第一个
						fdcRev.setCustomer(((FDCCustomerInfo) os[0]).getSysCustomer());
						fdcRev.getFdcCustomers().add(revFdcCusEntry);
					}
				}
			}
    	}else{
    		FDCCustomerInfo fdcCus = (FDCCustomerInfo) this.f7FdcCustomers.getValue();
    		if(fdcCus != null){
				RevFDCCustomerEntryInfo revFdcCusEntry = new RevFDCCustomerEntryInfo();
				revFdcCusEntry.setFdcCustomer(fdcCus);
				
				fdcRev.setCustomer(fdcCus.getSysCustomer());
				fdcRev.getFdcCustomers().add(revFdcCusEntry);
    		}
    	}
    	
    	this.storeEntry();
    }    
    
    //及其郁闷的函数,抽出来只是为了供子类调用
    protected void loadFields_forChildren() throws EASBizException, BOSException {
    	initBizTypeCombo();//移到loadFields中,防止触发事件
    	
//    	this.f7Customer.setValue(this.editData.getCustomer());
    	RevFDCCustomerEntryCollection revFdcCusEntrys = this.editData.getFdcCustomers();
    	if(revFdcCusEntrys != null  &&  revFdcCusEntrys.size() > 0){
    		FDCCustomerInfo[] fdcCuses = new FDCCustomerInfo[revFdcCusEntrys.size()];
        	for(int i=0; i<revFdcCusEntrys.size(); i++){
        		RevFDCCustomerEntryInfo revFdcCusEntry = revFdcCusEntrys.get(i);
        		FDCCustomerInfo fdcCus = revFdcCusEntry.getFdcCustomer();
        		fdcCuses[i] = fdcCus;
        	}
        	this.f7FdcCustomers.setValue(fdcCuses);
    	}
    	
    	if(RevBillTypeEnum.refundment.equals(this.editData.getRevBillType())){
    		this.txtRecAmount.setValue(getBigDecimal(this.editData.getAmount()).abs());
    		this.txtRecLocAmount.setValue(getBigDecimal(this.editData.getOriginalAmount()).abs());
    	}
    	
    	loadEntry();
    	
    	initCompentByBizType(this.editData.getRevBizType());//移到loadFields中,防止触发事件
	}
    
    protected IObjectValue getValue(IObjectPK pk) throws Exception {
    	FDCReceivingBillInfo fdcRev = (FDCReceivingBillInfo) super.getValue(pk);
    	FDCReceivingBillEntryCollection fdcRevEntrys = fdcRev.getEntries();
    	CRMHelper.sortCollection(fdcRevEntrys, "seq", true);
    	return fdcRev;
    }
    
    private void storeEntry() {
    	FDCReceivingBillEntryCollection revEntrys = new FDCReceivingBillEntryCollection();
    	
    	boolean isRefundment = RevBillTypeEnum.refundment.equals(this.editData.getRevBillType());
    	for(int i=0; i<this.tblEntry.getRowCount(); i++){
    		IRow row = this.tblEntry.getRow(i);
    		Object obj = row.getUserObject();
    		if(obj == null  ||  !(obj instanceof FDCReceivingBillEntryInfo)){
    			continue;
    		}
    		FDCReceivingBillEntryInfo revEntry = (FDCReceivingBillEntryInfo) obj;
    		revEntry.setMoneyDefine((MoneyDefineInfo) row.getCell(COL_MONEY_DEFINE).getValue());
    		revEntry.setSettlementType((SettlementTypeInfo) row.getCell(COL_STLE_TYPE).getValue());
    		revEntry.setSettlementNumber((String) row.getCell(COL_STLE_NUMBER).getValue());
    		revEntry.setRevAccount((AccountViewInfo) row.getCell(COL_REV_ACCOUNT).getValue());
    		
    		revEntry.setRevAccountBank((AccountBankInfo) row.getCell(COL_REV_BANK_ACCOUNT).getValue());
    		revEntry.setBankNumber((String) row.getCell(COL_CUS_ACCOUNT).getValue());
    		
    		BigDecimal amount = (BigDecimal) row.getCell(COL_REV_AMOUNT).getValue();
    		if(isRefundment){
    			amount = FDCHelper.ZERO.subtract(getBigDecimal(amount));
    		}
    		revEntry.setRevAmount(amount);
    		revEntry.setOppAccount((AccountViewInfo) row.getCell(COL_OPP_ACCOUNT).getValue());
    		revEntry.setRoom(getAction() == null ? null : getAction().getRoomInfoByRevList(revEntry.getRevListInfo()));
    		
//    		revEntry.setOrgUnit((FullOrgUnitInfo) row.getCell(COL_SUPPLY_ORG).getValue());
    		revEntry.setSupplyDes((String) row.getCell(COL_SUPPLY_DES).getValue());
    		
    		if(RevBillTypeEnum.transfer.equals(this.editData.getRevBillType())){
    			TransferSourceEntryCollection transSrcs = new TransferSourceEntryCollection();
    			//转款时,需要将转出的明细也记录下来
    			for(int j=i+1; j<this.tblEntry.getRowCount(); j++ ){
    				IRow transRow = this.tblEntry.getRow(j);
    				if(transRow == null){
    					logger.error("error....");
    				}
    				IRevListInfo transferRevList = (IRevListInfo) transRow.getUserObject();
    				
    				TransferSourceEntryInfo transferSrc = new TransferSourceEntryInfo();
    				transferSrc.setFromRevListId(transferRevList.getId().toString());
    				transferSrc.setFromRevListType(transferRevList.getRevListTypeEnum());
    				transferSrc.setAmount((BigDecimal) transRow.getCell(COL_REV_AMOUNT).getValue());
    				transferSrc.setFromMoneyDefine(transferRevList.getMoneyDefine());
    				transSrcs.add(transferSrc);
    				
    				if(true) break;//TODO 待修改,转出明细目前只会有一条,暂时这样
    			}
    			revEntry.getSourceEntries().clear();
    			revEntry.getSourceEntries().addCollection(transSrcs);
    		}
    		
    		revEntrys.add(revEntry);
    	}
    	this.editData.getEntries().clear();
    	this.editData.getEntries().addCollection(revEntrys);
    }
    /**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
    protected IRevAction getAction(){
    	return null;
    };
    
    //郁闷同上
    protected void loadEntry() throws EASBizException, BOSException {
    	this.tblEntry.checkParsed();
    	this.tblEntry.removeRows(false);
    	
    	//这里可能需要先对收款单分录进行分录或数据预处理,以便于后面的加载 TODO
    	FDCReceivingBillEntryCollection revEntrys = this.editData.getEntries();
    	RevBillTypeEnum revBillType = this.editData.getRevBillType();
    	
    	//这2个字段用于控制行合并
    	String lastRevListId = null;
    	IRow lastRow = null;  	
    	
    	for(int i=0; i<revEntrys.size(); i++){
    		FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
    		String curRevListId = revEntry.getRevListId();

    		IRow row = null;
    		if(RevBillTypeEnum.gathering.equals(revBillType)){
    			row = loadEntryOfGathering(revEntry);
    		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
    			row = loadEntryOfRefundment(revEntry);
    		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
    			loadEntryOfTransfer(revEntry);
    		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
    			String desc = this.editData.getDescription();
    			if(this.editData.getId()!=null && desc!=null){
    				if(desc.startsWith("针对转款"))
    					loadEntryOfTransfer(revEntry);
    				else if(desc.startsWith("针对退款"))
    					row = loadEntryOfRefundment(revEntry);
    				else
    					row = loadEntryOfGathering(revEntry);
    			}	
    		}
    		
    		if(row == null){
    			continue;
    		}
    		
    		//
    		if(lastRevListId != null  &&  lastRevListId.equals(curRevListId)){
				mergeColumns(lastRow.getRowIndex(), row.getRowIndex());
    		}
    		
    		if(lastRevListId == null  ||  !lastRevListId.equals(curRevListId)){
				lastRevListId = curRevListId;
				lastRow = row;
			}
    	}
	}
    
    private IRow loadEntryOfTransfer(FDCReceivingBillEntryInfo revEntry) throws EASBizException, BOSException {
    	IRevListInfo revList = getRevListInfo(revEntry.getRevListType(), revEntry.getRevListId());
    	if(revList == null){
    		logger.error("是不是没实现 getRevListInfo(),或者是数据错误:" + revEntry.getRevListType() + " :: " + revEntry.getRevListId());
    	}
    	
    	IRow row = this.tblEntry.addRow();
    	revEntry.setRevListInfo(revList);
    	row.setUserObject(revEntry);

    	setColValue(row, COL_MONEY_DEFINE, revEntry.getMoneyDefine());
    	setColValue(row, COL_STLE_TYPE, revEntry.getSettlementType());
    	setColValue(row, COL_STLE_NUMBER, revEntry.getSettlementNumber());
    	
    	setColValue(row, COL_REV_ACCOUNT, revEntry.getRevAccount());
    	setColValue(row, COL_REV_BANK_ACCOUNT, revEntry.getRevAccountBank());
    	setColValue(row, COL_CUS_ACCOUNT, revEntry.getBankNumber());
    	setColValue(row, COL_REV_AMOUNT, revEntry.getRevAmount());
    	
    	//TODO 减免金额是放在收款明细基类中呢,还是租赁系统继承去扩展呢？
//    	setColValue(row, COL_REMISSION_AMOUNT, revList.getre);
    	setColValue(row, COL_REMISSION_AMOUNT,this.getAction() == null ? null : this.getAction().getRemissionAmountRevList(revList));
    	
    	setColValue(row, COL_OPP_ACCOUNT, revEntry.getOppAccount());
    	   	
    	TransferSourceEntryCollection transferSrcs = revEntry.getSourceEntries();
    	//TODO 按目前的规则，这里只会有1条分录
    	for(int i=0; i<transferSrcs.size(); i++){
    		TransferSourceEntryInfo transfer = transferSrcs.get(i);
    		IRevListInfo fromRevList = null;
    		if(transfer.getFromRevListId()!=null && transfer.getFromMoneyDefine()==null) 
    			fromRevList = getRevListInfo(transfer.getFromRevListType(), transfer.getFromRevListId());
    		
    		IRow fromRow = this.tblEntry.addRow();
    		fromRow.setUserObject(fromRevList);
    		fromRow.getStyleAttributes().setBackground(Color.WHITE);
    		fromRow.getStyleAttributes().setLocked(true);
			
    		fromRow.getCell(COL_FROM_ACCOUNT).getStyleAttributes().setLocked(false);
			fromRow.getCell(COL_FROM_ACCOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			
			fromRow.getCell(COL_REV_ACCOUNT).getStyleAttributes().setLocked(true);
//			tmpRow.getCell(COL_REV_ACCOUNT).getStyleAttributes().setBackground(Color.WHITE);

			fromRow.getCell(COL_OPP_ACCOUNT).getStyleAttributes().setLocked(true);
			
			if(fromRevList != null)
				setColValue(fromRow, COL_FROM_MONEY_DEFINE, fromRevList.getMoneyDefine());
			else if(transfer.getFromMoneyDefine()!=null) {
				setColValue(fromRow, COL_FROM_MONEY_DEFINE, transfer.getFromMoneyDefine());
			}
			
			setColValue(fromRow, COL_FROM_ACCOUNT, revEntry.getRevAccount());//转出科目直接取收款单分录上的收款科目
			setColValue(fromRow, COL_REV_AMOUNT, transfer.getAmount());					
    	}
    	
    	
    	if(revList!=null) {
	    	setColValue(row, COL_APP_AMOUNT, revList.getAppAmount());
	    	//实收金额取剩余金额而不是已收金额
	    	setColValue(row, COL_ACT_REV_AMOUNT, revList.getAllRemainAmount());
	    	
	    	setExpandColsValue(revList, row);
    	}
    	
    	return row;
	}

    //增加扩展列并设置扩展列的值
	protected void setExpandColsValue(IRevListInfo revList, IRow row) {
		if (this.getAction() != null) {
			List expandCols = this.getAction().getExpandCols(revList.getRevListTypeEnum());
			if(expandCols == null){
				return;
			}
			for (int j = 0; j < expandCols.size(); j++) {
				Object[] tmps = (Object[]) expandCols.get(j);
				String tmpsColKey = (String) tmps[0];
				if (tblEntry.getColumn(tmpsColKey) == null) {
					int tmpColIndex = ((Integer) tmps[2]).intValue();
					IColumn tmpCol = tblEntry.addColumn(tmpColIndex);
					tmpCol.getStyleAttributes().setLocked(true);
					tmpCol.setKey(tmpsColKey);
					tblEntry.getHeadRow(0).getCell(tmpsColKey).setValue(tmps[1]);

					oldColKeySet.add(tmpsColKey);
				}
				setColValue(row, tmpsColKey, CRMHelper.getValue(revList, (String) tmps[3]));
			}
		}
	}

	private IRow loadEntryOfRefundment(FDCReceivingBillEntryInfo revEntry) throws EASBizException, BOSException {
    	IRevListInfo revList = getRevListInfo(revEntry.getRevListType(), revEntry.getRevListId());
    	if(revList == null){
    		logger.error("是不是没实现 getRevListInfo(),或者是数据错误:" + revEntry.getRevListType() + " :: " + revEntry.getRevListId());
    	}
    	
    	IRow row = this.tblEntry.addRow();
    	
    	row.setUserObject(revEntry);

    	setColValue(row, COL_MONEY_DEFINE, revEntry.getMoneyDefine());
    	setColValue(row, COL_STLE_TYPE, revEntry.getSettlementType());
    	setColValue(row, COL_STLE_NUMBER, revEntry.getSettlementNumber());
    	
    	setColValue(row, COL_REV_ACCOUNT, revEntry.getRevAccount());
    	setColValue(row, COL_REV_BANK_ACCOUNT, revEntry.getRevAccountBank());
    	setColValue(row, COL_CUS_ACCOUNT, revEntry.getBankNumber());
    	setColValue(row, COL_REV_AMOUNT, getBigDecimal(revEntry.getRevAmount()).abs());
    	setColValue(row, COL_OPP_ACCOUNT, revEntry.getOppAccount());
    	
    	if(revList == null){
    		return row;
    	}
    	revEntry.setRevListInfo(revList);
    	setColValue(row, COL_APP_AMOUNT, revList.getAppAmount());
    	//实收金额取剩余金额而不是已收金额
    	setColValue(row, COL_ACT_REV_AMOUNT, revList.getAllRemainAmount());
    	
    	setColValue(row, COL_LIMIT_AMOUNT, revList.getRemainLimitAmount());
    	setColValue(row, COL_HAS_REFUNDMENT_AMOUNT, revList.getHasRefundmentAmount());
    	
    	//TODO 减免金额是放在收款明细基类中呢,还是租赁系统继承去扩展呢？
//    	setColValue(row, COL_REMISSION_AMOUNT, revList.getre);
    	
    	setExpandColsValue(revList, row);
    	
    	return row;
	}

	private static final String[] notMergeCols = new String[]{COL_STLE_TYPE, COL_STLE_NUMBER, COL_REV_ACCOUNT, COL_REV_BANK_ACCOUNT, COL_CUS_ACCOUNT, COL_REV_AMOUNT, COL_LOC_AMOUNT, COL_OPP_ACCOUNT};
    //对分录上指定的行进行列合并
    private void mergeColumns(int sRowIndex, int eRowIndex){
		for(int j=0; j<tblEntry.getColumnCount(); j++){
			String tmpKey = this.tblEntry.getColumnKey(j);
			
			if(Arrays.asList(notMergeCols).contains(tmpKey)){
				continue;
			}
			this.tblEntry.getMergeManager().mergeBlock(sRowIndex, j, eRowIndex, j);
		}
    }
    
    private IRow loadEntryOfGathering(FDCReceivingBillEntryInfo revEntry) throws EASBizException, BOSException {
    	IRevListInfo revList = getRevListInfo(revEntry.getRevListType(), revEntry.getRevListId());
    	if(revList == null){
    		logger.error("是不是没实现 getRevListInfo(),或者是数据错误:" + revEntry.getRevListType() + " :: " + revEntry.getRevListId());
    	}
    	
    	IRow row = this.tblEntry.addRow();
    	
    	row.setUserObject(revEntry);

    	if(revList instanceof TenancyRoomPayListEntryInfo){
			setColValue(row, "startDate", ((TenancyRoomPayListEntryInfo)revList).getStartDate());
			setColValue(row, "endDate", ((TenancyRoomPayListEntryInfo)revList).getEndDate());
		}else if(revList instanceof TenBillOtherPayInfo){
			setColValue(row, "startDate", ((TenBillOtherPayInfo)revList).getStartDate());
			setColValue(row, "endDate", ((TenBillOtherPayInfo)revList).getEndDate());
		}
    	setColValue(row, "appDate", revList.getAppDate());
    	setColValue(row, COL_MONEY_DEFINE, revEntry.getMoneyDefine());
    	setColValue(row, COL_STLE_TYPE, revEntry.getSettlementType());
    	setColValue(row, COL_STLE_NUMBER, revEntry.getSettlementNumber());
    	
    	setColValue(row, COL_REV_ACCOUNT, revEntry.getRevAccount());
    	setColValue(row, COL_REV_BANK_ACCOUNT, revEntry.getRevAccountBank());
    	setColValue(row, COL_CUS_ACCOUNT, revEntry.getBankNumber());
    	setColValue(row, COL_REV_AMOUNT, revEntry.getRevAmount());
    	
    	//TODO 
    	if(revEntry.getOrgUnit() != null){
    		//TODO 这里取数再修改
    		setColValue(row, COL_SUPPLY_ORG, FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(revEntry.getOrgUnit().getId())));
    	}
    	setColValue(row, COL_SUPPLY_DES, revEntry.getSupplyDes());

    	//TODO 减免金额是放在收款明细基类中呢,还是租赁系统继承去扩展呢？
//    	setColValue(row, COL_REMISSION_AMOUNT, revList.getre);
    	
    	setColValue(row, COL_OPP_ACCOUNT, revEntry.getOppAccount());
    	
    	if(revList == null){
    		return row;
    	}
    	
    	revEntry.setRevListInfo(revList);
    	setColValue(row, COL_APP_AMOUNT, revList.getAppAmount());
    	//实收金额取剩余金额而不是已收金额
    	setColValue(row, COL_ACT_REV_AMOUNT, revList.getAllRemainAmount());
    	setColValue(row, COL_REMISSION_AMOUNT,this.getAction() == null ? null : this.getAction().getRemissionAmountRevList(revList));    	
    	
    	setExpandColsValue(revList, row);
    	return row;
	}
  //缓存款项 科目 对应表
	protected Map moneyAccountMapping = null;
	//初始化款项 科目 对应表
	protected void initMoneyAccountMapping() throws BOSException{
		moneyAccountMapping = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(company != null){
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", company.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", null));
		}
		
		//TODO *处理
		view.getSelector().add("accountView.*");
		view.getSelector().add("moneyDefine.id");
		view.getSelector().add("isChanged");
		
		MoneySubjectContrastCollection moneySubjects = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(view);
		for(int i=0; i<moneySubjects.size(); i++){
			MoneySubjectContrastInfo moneySubject = moneySubjects.get(i);
			MoneyDefineInfo moneyDefine = moneySubject.getMoneyDefine();
			if(moneyDefine != null){
				moneyAccountMapping.put(moneyDefine.getId().toString(), moneySubject);
			}
		}
	}
	
    /**
	 * 根据款项类型 和 款项科目对照表。带出科目设置到 对方科目列上
	 * 并根据对照表设置的是否允许修改，设置是否锁定对方科目列
	 * */
	protected void setAccountByMoneyDefine(MoneyDefineInfo moneyDefine, IRow row,String collName) {
		if(moneyDefine == null  ||  row == null  ||  moneyAccountMapping == null){
			return;
		}
		MoneySubjectContrastInfo moneySubject = (MoneySubjectContrastInfo) moneyAccountMapping.get(moneyDefine.getId().toString());
		if(moneySubject == null){
			return;
		}
		
		AccountViewInfo account = moneySubject.getAccountView();
		boolean isChanged = moneySubject.isIsChanged();//是否允许修改
		if(account != null){
			//带出对照表中设置的 科目 设置到对方科目上
			ICell cell = row.getCell(collName);
			cell.setValue(account);
			if(!isChanged){
				cell.getStyleAttributes().setLocked(true);
			}
		}
	}

  
	 /**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	  /**
     * 根据类型和ID获得收款明细值对象
     * */
	protected IRevListInfo getRevListInfo(RevListTypeEnum revListType, String revListId) throws EASBizException, BOSException{
		return null;
	};

	//继续郁闷,抽出来只是为了供子类调用
    protected SelectorItemCollection getSelectors_forChildren() {
    	SelectorItemCollection sels = super.getSelectors();
    	/*
    	//sels.add("*");
    	sels.add("customer.*");
    	sels.add("fdcCustomers.*");
    	sels.add("fdcCustomers.fdcCustomer.name");
    	sels.add("fdcCustomers.fdcCustomer.number");
    	sels.add("fdcCustomers.fdcCustomer.sysCustomer.id");
    	*/
    	sels.add("billStatus");
    	sels.add("revBillType");
    	sels.add("revBizType");
    	sels.add("fiVouchered");
    	sels.add("customer.name");
    	sels.add("customer.number");
    	sels.add("fdcCustomers.fdcCustomer.name");
    	sels.add("fdcCustomers.fdcCustomer.number");
    	sels.add("fdcCustomers.fdcCustomer.sysCustomer.id");
    	
    	return this.addEntrySelectors(sels);
	}
    
    /**
     * 主要是分录中的对象
     * */
    private SelectorItemCollection addEntrySelectors(SelectorItemCollection srcSels) {
    	srcSels.add("entries.*");
    	srcSels.add("entries.settlementType.name");
    	srcSels.add("entries.settlementType.number");
    	srcSels.add("entries.revAccount.name");
    	srcSels.add("entries.revAccount.number");
    	srcSels.add("entries.oppAccount.name");
    	srcSels.add("entries.oppAccount.number");
    	srcSels.add("entries.moneyDefine.name");
    	srcSels.add("entries.moneyDefine.number");
    	srcSels.add("entries.moneyDefine.moneyType");
    	
    	srcSels.add("entries.orgUnit.*");
//    	srcSels.add("entries.orgUnit.number");
//    	srcSels.add("entries.orgUnit.name");
    	
    	srcSels.add("entries.revAccountBank.name");
    	srcSels.add("entries.revAccountBank.number");
    	
    	srcSels.add("entries.sourceEntries.*");
    	srcSels.add("entries.sourceEntries.fromMoneyDefine.name");
    	return srcSels;
    }
    
	protected IObjectValue createNewData() {
		this.btnSelectRevList.setEnabled(true);	//查看是点新增居然不能选择，因而加在这里 
		
    	FDCReceivingBillInfo fdcRev = new FDCReceivingBillInfo();
    	
    	Timestamp curTime = null;
		try {
			curTime = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e1) {
			e1.printStackTrace();
			curTime = new Timestamp(System.currentTimeMillis());
		}
		
    	fdcRev.setCreateTime(curTime);
    	fdcRev.setCreator(SysContext.getSysContext().getCurrentUserInfo());
    	fdcRev.setCompany(company);
    	fdcRev.setOrgUnit(orgUnitInfo);
    	
    	SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get(KEY_SELL_PROJECT);
    	fdcRev.setSellProject(sellProject);
    	
    	RevBillTypeEnum revBillType = (RevBillTypeEnum)this.getUIContext().get(KEY_REV_BILL_TYPE);
    	if(revBillType == null){
    		revBillType = RevBillTypeEnum.gathering;
    	}
    	fdcRev.setRevBillType(revBillType);

    	RevBizTypeEnum revBizType = (RevBizTypeEnum)this.getUIContext().get(KEY_REV_BIZ_TYPE);
    	if(revBizType == null){
    		revBizType = getBizTypes()[0];
    	}
    	fdcRev.setRevBizType(revBizType);
    	
		fdcRev.setCurrency(baseCurrency);
		//TODO 目前暂不支持外币,全部取出的全部为人民币,汇率设置为1
		fdcRev.setExchangeRate(FDCHelper.ONE);
		
		//默认收款日期为当前服务器日期
		fdcRev.setBizDate(curTime);
		fdcRev.setReceiptState(ReceiptStatusEnum.UnMake);
		/**
		 * 根据提单R110105-105来修复凭证是否生成字段默认为null的bug
		 * update by renliang at 2011-1-11
		 */
		fdcRev.setFiVouchered(false);
		
		/**
		 * 根据提单R110324-297来修复【是否生成出纳收款单】字段默认为空的bug
		 * update by liuye at 2011-4-7
		 */
		fdcRev.setIsCreateBill(false);
		
    	return fdcRev;
    }
    
	protected void attachListeners() {
		// TODO Auto-generated method stub
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCReceivingBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	/** 
	 * 重新选择时是否清除原有的明细
	 * */
	protected boolean isRmRowsAfterSelectRevList(){
		return true;
	}
	
	protected void btnSelectRevList_actionPerformed(ActionEvent e) throws Exception {
		//TODO 放到实用的时候初始化还好点
		if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
		
		UIContext uiContext = prepareSelectRevListContext();
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectRevListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		Map resContext = uiWindow.getUIObject().getUIContext();
		
		if (resContext.get(SelectRevListUI.KEY_RES_OPT) == null ||
				!((Boolean)resContext.get(SelectRevListUI.KEY_RES_OPT)).booleanValue()){
			return;
		}

		addEntryTableData(resContext);
	}
	
	
	/** 把填充分录的数据的方法提出来，以便子类可以调用  子类可以在界面新增打开时直接调用，以显示收款明细**/
	protected void addEntryTableData(Map resContext)	{
		RevBillTypeEnum revBillType = (RevBillTypeEnum) resContext.get(SelectRevListUI.KEY_RES_REV_BILL_TYPE);
		//重新选择应收明细后，原来的明细是否清除。先还是只针对收款做处理吧 TODO
		if(!revBillType.equals(this.comboRevBillType.getSelectedItem())  ||  !RevBillTypeEnum.gathering.equals(revBillType) || isRmRowsAfterSelectRevList()){
			this.tblEntry.removeRows(false);
		}
		initEntryTableByRevBillType(revBillType);
		
		boolean hasRows = this.tblEntry.getRowCount() > 0;
		//为防止2次选择明细时重复添加扩展列,先将原添加的扩展列删除
		if(!hasRows){
			for(Iterator itor = oldColKeySet.iterator(); itor.hasNext(); ){
				String tmpsColKey = (String) itor.next();
				IColumn col = this.tblEntry.getColumn(tmpsColKey);
				if(col != null){
					tblEntry.removeColumn(col.getColumnIndex());
				}
			}
			oldColKeySet.clear();	
		}		
		if (RevBillTypeEnum.gathering.equals(revBillType)) {
			List l1 = (List) resContext.get(SelectRevListUI.KEY_RES_APP_REV_LIST);
			List l2 = (List) resContext.get(SelectRevListUI.KEY_RES_DIR_REV_LIST);
			List l3 = (List) resContext.get(SelectRevListUI.KEY_RES_PRE_REV_LIST);
			try{
				addGatheringEntryTableData(l1);
				addGatheringEntryTableData(l2);
				addGatheringEntryTableData(l3);
			}catch(Exception e){ 
				this.handleException(e);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			List l4 = (List) resContext.get(SelectRevListUI.KEY_RES_APP_REV_REFUNDMENT_LIST);
			List l5 = (List) resContext.get(SelectRevListUI.KEY_RES_APP_REFUNDMENT_LIST);			
			addRefundmentEntryTableData(l4);
			addRefundmentEntryTableData(l5);
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			Map l6 = (Map) resContext.get(SelectRevListUI.KEY_RES_APP_REV_TRANSFER_REV_LIST);
			Map l7 = (Map) resContext.get(SelectRevListUI.KEY_RES_DIR_REV_TRANSFER_REV_LIST);
			Map l8 = (Map) resContext.get(SelectRevListUI.KEY_RES_PRE_REV_TRANSFER_REV_LIST);			
			addTransferEntryTableData(l6);
			addTransferEntryTableData(l7);
			addTransferEntryTableData(l8);
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			List l9 = (List) resContext.get(SelectRevListUI.KEY_RES_ADJUST_LIST);		
			addAdjustEntryTableData(l9);
		}		
//		this.txtRoomLongNumber.setText(getRoomLongNumber(roomNumberSet));
//		roomNumberSet.clear();
		
		setUnionAmount();
	}
	
	
	
	private void addRefundmentEntryTableData(List list) {
		if(list == null){
			return;
		}
		for(int i=0; i<list.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) list.get(i);
			IRow row = this.tblEntry.addRow();
			
			FDCReceivingBillEntryInfo revEntry = new FDCReceivingBillEntryInfo();
			revEntry.setRevListInfo(revListInfo);
			revEntry.setRevListType(revListInfo.getRevListTypeEnum());
			revEntry.setRevListId(revListInfo.getId() == null ? null : revListInfo.getId().toString());
			row.setUserObject(revEntry);

			setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
			//TODO 不在这里处理 
			//row.getCell(COL_STLE_COUNT).getStyleAttributes().setHided(false);
			setColValue(row, COL_STLE_COUNT, new Integer(1));
			//自动带出结算方式
			setColValue(row, COL_APP_AMOUNT, revListInfo.getAppAmount());
			//实收金额取剩余金额而不是已收金额
	    	setColValue(row, COL_ACT_REV_AMOUNT, revListInfo.getAllRemainAmount());
			
			//TODO 这里要检查下
	    	//自动带出可退金额
	    	setColValue(row, COL_REV_AMOUNT, revListInfo.getRemainLimitAmount());
//			setColValue(row, COL_REMISSION_AMOUNT, revListInfo.getRemainAmount());
			setColValue(row, COL_LIMIT_AMOUNT, revListInfo.getRemainLimitAmount());
			setColValue(row, COL_HAS_REFUNDMENT_AMOUNT, revListInfo.getHasRefundmentAmount());
			
			try{
				//结算方式取款项类型中默认结算方式
				setSettlementByMoneyDefine(revListInfo.getMoneyDefine(),row);
				//对方科目需要根据款项科目对照表带出
				setAccountByMoneyDefine(revListInfo.getMoneyDefine(),row,COL_OPP_ACCOUNT);
			}catch(Exception e){
				e.printStackTrace();
				this.abort();
			}

			
			//处理退款扩展列--by jun_li
			setExpandColsValue(revListInfo, row);
		}
	}

	private void addTransferEntryTableData(Map map) {
		if(map == null){
			return;
		}
		Set keys = map.keySet();
		
		for(Iterator itor = keys.iterator(); itor.hasNext(); ){
			Object[] keyObjs = (Object[]) itor.next();
			
			IRevListInfo revListInfo = (IRevListInfo)keyObjs[0];
			BigDecimal toAmount = (BigDecimal) keyObjs[1];
			
			List list = (List) map.get(keyObjs);
//			BigDecimal tolTransferAmount = FDCHelper.ZERO;
			//TODO 这里还有问题,如果有多个转出的话,收款单分录也应该对应多条,因为收款单分录的收款科目需要与转出款项的对方科目相同
			for(int j=0; j<list.size(); j++){
				FDCReceivingBillEntryInfo revEntry = new FDCReceivingBillEntryInfo();
				revEntry.setRevListInfo(revListInfo);
				revEntry.setRevListType(revListInfo.getRevListTypeEnum());
				revEntry.setRevListId(revListInfo.getId() == null ? null : revListInfo.getId().toString());
				
				TransferSourceEntryCollection transferSrcCol = new TransferSourceEntryCollection();
				//转出明细以前如果有多个收款科目
				Object[] objs = (Object[]) list.get(j);
				IRevListInfo tmpRevListInfo = (IRevListInfo) objs[0];
				BigDecimal transferAmount = (BigDecimal) objs[1];
				TransferSourceEntryInfo transferSrcEntry = new TransferSourceEntryInfo();
				transferSrcEntry.setHeadEntry(revEntry);
				//TODO 需要验证
				transferSrcEntry.setFromRevListType(tmpRevListInfo.getRevListTypeEnum());
//				transferSrcEntry.setFromRevListType(transferSrcEntry.getFromRevListType());
				transferSrcEntry.setFromRevListId(tmpRevListInfo.getId() == null ? null : tmpRevListInfo.getId().toString());
				transferSrcEntry.setAmount(transferAmount);
				
				transferSrcCol.add(transferSrcEntry);
				
				//----------------------增加转入行-------------
				IRow row = this.tblEntry.addRow();
				row.setUserObject(revEntry);
				setColValue(row, COL_REV_AMOUNT, transferAmount);
				setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
				setColValue(row, COL_STLE_COUNT, new Integer(1));
				
				setColValue(row, COL_APP_AMOUNT, revListInfo.getAppAmount());
				//实收金额取剩余金额而不是已收金额
				setColValue(row, COL_ACT_REV_AMOUNT, revListInfo.getAllRemainAmount());
				
				row.getStyleAttributes().setLocked(true);
				row.getCell(COL_OPP_ACCOUNT).getStyleAttributes().setLocked(false);
				row.getCell(COL_STLE_TYPE).getStyleAttributes().setLocked(false);
				setColValue(row, COL_REMISSION_AMOUNT,this.getAction() == null ? null : this.getAction().getRemissionAmountRevList(revListInfo));
				//TODO 对方科目需要根据款项科目对照表带出
				//--------------------------------------
				setAccountByMoneyDefine(revListInfo.getMoneyDefine(),row,COL_OPP_ACCOUNT);
				
				setExpandColsValue(revListInfo, row);
				
				IRow tmpRow = this.tblEntry.addRow();
				tmpRow.setUserObject(tmpRevListInfo);
				tmpRow.getStyleAttributes().setBackground(Color.WHITE);
				tmpRow.getStyleAttributes().setLocked(true);
				
				tmpRow.getCell(COL_FROM_ACCOUNT).getStyleAttributes().setLocked(false);
				tmpRow.getCell(COL_FROM_ACCOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				
				tmpRow.getCell(COL_REV_ACCOUNT).getStyleAttributes().setLocked(true);
//				tmpRow.getCell(COL_REV_ACCOUNT).getStyleAttributes().setBackground(Color.WHITE);

				tmpRow.getCell(COL_OPP_ACCOUNT).getStyleAttributes().setLocked(true);
				
				setColValue(tmpRow, COL_FROM_MONEY_DEFINE, tmpRevListInfo.getMoneyDefine());
				//设置转出科目,默认从款项科目对照表中取
				setAccountByMoneyDefine(tmpRevListInfo.getMoneyDefine(),tmpRow,COL_FROM_ACCOUNT);
				setAccountByMoneyDefine(tmpRevListInfo.getMoneyDefine(),row,COL_REV_ACCOUNT);
				setColValue(tmpRow, COL_REV_AMOUNT, transferAmount);
				//TODO
				
//				tolTransferAmount = tolTransferAmount.add(getBigDecimal(transferAmount));
				revEntry.getSourceEntries().addCollection(transferSrcCol);
			}
			
//			revEntry.setRevAmount(tolTransferAmount);
//			setColValue(row, COL_REV_AMOUNT, tolTransferAmount);
		}
	}

	private BigDecimal getBigDecimal(BigDecimal big){
		return big == null ? FDCHelper.ZERO : big;
	}
	
	private void addAdjustEntryTableData(List list) {
		if(list == null){
			return;
		}
		// TODO Auto-generated method stub
	}

	/*
	 * 加载收款明细到收款单编辑界面
	 */
	private void addGatheringEntryTableData(List list) throws EASBizException, BOSException {
		if(list == null){
			return;
		}
		for(int i=0; i<list.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) list.get(i);
			IRow row = this.tblEntry.addRow();

			FDCReceivingBillEntryInfo revEntry = buildRevEntry(revListInfo);
			row.setUserObject(revEntry);

			if(revListInfo instanceof ITenancyPayListInfo){
				setColValue(row, "startDate", ((ITenancyPayListInfo)revListInfo).getStartDate());
				setColValue(row, "endDate", ((ITenancyPayListInfo)revListInfo).getEndDate());
			}else if(revListInfo instanceof TenBillOtherPayInfo){
				setColValue(row, "startDate", ((TenBillOtherPayInfo)revListInfo).getStartDate());
				setColValue(row, "endDate", ((TenBillOtherPayInfo)revListInfo).getEndDate());
			}
			setColValue(row, "appDate", revListInfo.getAppDate());
			setColValue(row, COL_MONEY_DEFINE, revListInfo.getMoneyDefine());
			setColValue(row, COL_STLE_COUNT, new Integer(1));
			
			
			setColValue(row, COL_APP_AMOUNT, revListInfo.getAppAmount());
			//实收金额取剩余金额而不是已收金额
			//Update by zhiyuan_tang 2010/10/12
			//如果是物业期初预收，则还是取已收金额
			if (RevListTypeEnum.advancerev.equals(revEntry.getRevListType())) {
				setColValue(row, COL_ACT_REV_AMOUNT, revListInfo.getActRevAmount());
			} else {
				setColValue(row, COL_ACT_REV_AMOUNT, revListInfo.getAllRemainAmount());
			}
//			setColValue(row, COL_ACT_REV_AMOUNT, revListInfo.getAllRemainAmount());
			
			BigDecimal finalAppAmount = getBigDecimal(revListInfo.getFinalAppAmount());
			BigDecimal allRemainAmount = getBigDecimal(revListInfo.getAllRemainAmount());
			//Update by zhiyuan_tang 2010/10/12
			//如果是物业期初预收，则还是取已收金额
			if (RevListTypeEnum.advancerev.equals(revEntry.getRevListType())) {
				allRemainAmount = getBigDecimal(revListInfo.getActRevAmount());
			}
			BigDecimal remissionAmount = getBigDecimal(this.getAction() == null ? null : this.getAction().getRemissionAmountRevList(revListInfo));
			BigDecimal rev_Amount = finalAppAmount.subtract(allRemainAmount);
			if(rev_Amount.compareTo(FDCHelper.ZERO)<0) rev_Amount = FDCHelper.ZERO;
			setColValue(row, COL_REV_AMOUNT, rev_Amount);
			setColValue(row, COL_REMISSION_AMOUNT, remissionAmount);

//			//结算方式取款项类型中默认结算方式
			setSettlementByMoneyDefine(revListInfo.getMoneyDefine(),row);
			//对方科目需要根据款项科目对照表带出
			//by tim_gao 加载款项科目对照表  2011-2-22
			if(moneyAccountMapping == null){
				initMoneyAccountMapping();
			}
			setAccountByMoneyDefine(revListInfo.getMoneyDefine(),row,COL_OPP_ACCOUNT);
			
			setExpandColsValue(revListInfo, row);
		}
	}
	
	protected FDCReceivingBillEntryInfo buildRevEntry(IRevListInfo revListInfo){
		FDCReceivingBillEntryInfo revEntry = new FDCReceivingBillEntryInfo();
		revEntry.setRevListInfo(revListInfo);
		revEntry.setRevListType(revListInfo.getRevListTypeEnum());
		revEntry.setRevListId(revListInfo.getId() == null ? null : revListInfo.getId().toString());
		return revEntry;
	}

/*
	 * 根据款项找出结算方式
	 */
	private void setSettlementByMoneyDefine(MoneyDefineInfo moneyDefine,IRow row) throws EASBizException, BOSException
	{
		if(moneyDefine == null  ||  row == null){
			return;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("settlementTypeEntry.*");
		sels.add("settlementTypeEntry.settlementType.*");

		moneyDefine = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(moneyDefine.getId()), sels);

		SettlementTypeEntryCollection settEntryColl =  moneyDefine.getSettlementTypeEntry();
		for(int i=0;i<settEntryColl.size();i++)
		{
			SettlementTypeEntryInfo settEntryInfo = settEntryColl.get(i);
			if(settEntryInfo.isIsBrought())
			{
				SettlementTypeInfo settType = settEntryInfo.getSettlementType();
				row.getCell(COL_STLE_TYPE).setValue(settEntryInfo.getSettlementType());
				//设置收款科目和收款账户
				setAccountAndGathing(settType,row);
			}
		}
	}
	
	/*
	 * 根据结算方式和收款科目对照表找出收款科目和账户
	 */
	private void setAccountAndGathing(SettlementTypeInfo settType,IRow row) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(company != null){
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", company.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", null));
		}
		filter.getFilterItems().add(new FilterItemInfo("balance.id",settType.getId().toString()));
		view.getSelector().add("balance.*");
		view.getSelector().add("bankAccount.*");
		view.getSelector().add("Gathering.*");
		
		SettlementgGatheringCollection settGatColl = SettlementgGatheringFactory.getRemoteInstance().getSettlementgGatheringCollection(view);
		if(settGatColl.size()>0)
		{
			SettlementgGatheringInfo settGatInfo = settGatColl.get(0);
			row.getCell(COL_REV_ACCOUNT).setValue(settGatInfo.getGathering());
			row.getCell(COL_REV_BANK_ACCOUNT).setValue(settGatInfo.getBankAccount());
		}
	}

	private void setColValue(IRow row, String colKey, Object value) {
		CRMClientHelper.setColValue(row, colKey, value);
	}
	
	protected void initEntryTableByRevBillType(RevBillTypeEnum revBillType){
		this.comboRevBillType.setSelectedItem(revBillType);
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			initEntryTableOfGathering();
			this.contRecAmount.setBoundLabelText("收款金额");
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			initEntryTableOfRefundment();
			this.contRecAmount.setBoundLabelText("退款金额");
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			initEntryTableOfTransfer();
			this.contRecAmount.setBoundLabelText("转款金额");
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			initEntryTableOfAdjust();
		}
	}
	
	protected void initEntryTableOfAdjust() {
		if(this.editData.getId()!=null) {
			String desc = this.editData.getDescription();
			if(desc!=null){
				if(desc.startsWith("针对转款"))
					initEntryTableOfTransfer();
				else if(desc.startsWith("针对退款"))
					initEntryTableOfRefundment();
				else
					initEntryTableOfGathering();
			}
		}
	}

	protected void initEntryTableOfTransfer() {
		this.tblEntry.getColumn(COL_FROM_MONEY_DEFINE).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_FROM_ACCOUNT).getStyleAttributes().setHided(false);
		
		this.tblEntry.getColumn(COL_STLE_NUMBER).getStyleAttributes().setHided(true);
		
		this.tblEntry.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setHided(true);
		
		this.tblEntry.getColumn(COL_STLE_COUNT).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_STLE_TYPE).getStyleAttributes().setHided(false);
		
		this.tblEntry.getColumn(COL_REV_BANK_ACCOUNT).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_CUS_ACCOUNT).getStyleAttributes().setHided(true);
		
		this.tblEntry.getColumn(COL_REV_ACCOUNT).getStyleAttributes().setHided(true);
		
		this.tblEntry.getHeadRow(0).getCell(COL_MONEY_DEFINE).setValue("转入款项");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_ACCOUNT).setValue("科目");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_AMOUNT).setValue("转款金额");
		
		this.tblEntry.getHeadRow(0).getCell(COL_OPP_ACCOUNT).setValue("转入科目");
	}

	protected void initEntryTableOfRefundment() {
		this.tblEntry.getColumn(COL_FROM_MONEY_DEFINE).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_FROM_ACCOUNT).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setHided(false);
		
		this.tblEntry.getColumn(COL_REV_ACCOUNT).getStyleAttributes().setHided(false);
		
		this.tblEntry.getColumn(COL_STLE_COUNT).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_STLE_TYPE).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_STLE_NUMBER).getStyleAttributes().setHided(false);
		
		this.tblEntry.getHeadRow(0).getCell(COL_MONEY_DEFINE).setValue("退款款项");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_ACCOUNT).setValue("退款科目");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_BANK_ACCOUNT).setValue("退款帐户");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_AMOUNT).setValue("退款金额");
		
		this.tblEntry.getHeadRow(0).getCell(COL_OPP_ACCOUNT).setValue("对方科目");
	}

	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得待应收的收款明细集合
	 * 考虑到应收明细可能有多类，所以用Map进行一下分组
	 * */
	protected  Map getAppRevList() throws BOSException,EASBizException{
		return null;
	};
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得待直收的收款明细集合
	 * */
	protected  List getDirRevList() throws BOSException,EASBizException{
		return null;
	};
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得待预收的收款明细集合
	 * */
	protected  List getPreRevList() throws BOSException,EASBizException{
		return null;
	};
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得待退款的应收收款明细集合
	 * */
	protected  List getAppRevRefundmentList() throws BOSException,EASBizException{
		return null;
	};
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得应退退款的明细集合
	 * */
	protected List getAppRefundmentList() throws BOSException,EASBizException{
		return null;
	};
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得可供冲转的收款明细集合
	 * TODO 目前没有转款规则的要求，所有可供冲转的收款明细可以转到任意应收上
	 * 后续如果要支持转款规则，这里可能需要用Map结构来描述可转关系
	 * */
	protected List getToTransferRevList() throws BOSException,EASBizException{
		return null;
	};
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 获得所属系统
	 * */
	protected MoneySysTypeEnum getMoneySysType(){
		return null;
	};
	
	protected boolean isModifySave() {
		return false;
	}	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.actionSubmit_actionPerformed(e);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		//TODO 缺少权限
		setSaveAction(false);
//		super.actionSubmit_actionPerformed(e);
		this.storeFields();
		this.editData.setBillStatus(RevBillStatusEnum.SUBMIT);
		String id = FDCReceivingBillFactory.getRemoteInstance().submitRev(this.editData, getHandleClazzName());
		showSubmitSuccess();
		this.setOprtState(OprtState.VIEW);
		this.getUIContext().put("ID", id);
		this.onLoad();
	}
	
	public void actionReceive_actionPerformed(ActionEvent e) throws Exception {
		FDCReceivingBillInfo fdcrecBill = (FDCReceivingBillInfo) this.editData;

		if (!RevBillStatusEnum.AUDITED.equals(fdcrecBill.getBillStatus()))
		{
			MsgBox.showInfo("只有已审批状态的收款单才能收款!");
			this.abort();
		}
		ArrayList idList = new ArrayList();
		idList.add(fdcrecBill.getId().toString());
		((IFDCReceivingBill) getBizInterface()).receive(idList);
		fdcrecBill.setBillStatus(RevBillStatusEnum.RECED);
		
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
	}
	
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		FDCReceivingBillInfo rev = this.editData;

		if (!RevBillStatusEnum.RECED.equals(rev.getBillStatus())) {
			MsgBox.showInfo(this, "只有已收款状态的收款单才能能生成凭证!");
			this.abort();
		}

		if (rev.isFiVouchered()) {
			MsgBox.showInfo(this, "已经生成凭证的不能再生成!");
			this.abort();
		}
		
		if(rev.getEntries()!=null){
			for(int i = 0;i<rev.getEntries().size();i++){
				FDCReceivingBillEntryInfo entry = rev.getEntries().get(i);
				if(entry.getRevAccount()==null||entry.getOppAccount()==null){
					MsgBox.showInfo(this, "分录科目为空时不能生成凭证!");
					this.abort();
				}
			}
		}
		
		super.actionVoucher_actionPerformed(e);
		this.editData.setFiVouchered(true);
		this.setOprtState("VIEW");
		this.onLoad();
		
	}
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 实现IRevHandle接口的类的名称
	 * */
	protected String getHandleClazzName(){
		return null;
	};

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(this.txtNumber.isEditable()  &&  this.txtNumber.isEnabled()  &&  StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showInfo(this, "编码不能为空！");
			this.abort();
		}
		
		if(this.f7SellProject.getData() == null){
			MsgBox.showInfo(this, "销售项目不能为空！");
			this.abort();
		}
		
		if(this.pkBizDate.getValue() == null){
			MsgBox.showInfo(this, "业务日期不能为空！");
			this.abort();
		}

		if(this.txtRecAmount.getBigDecimalValue() == null || ((BigDecimal)this.txtRecAmount.getBigDecimalValue()).compareTo(new BigDecimal(0))==0 ){
			MsgBox.showInfo(this, this.comboRevBillType.getSelectedItem()+ "金额不能为0！");
			this.abort();
		}

		/**
		 * 为了区别收款和退款提示问题 update by renliang at 2010-11-24
		 */
		RevBillTypeEnum revType = (RevBillTypeEnum) this.comboRevBillType
				.getSelectedItem();
		
		boolean result = true;

		if (revType != null && revType.equals(RevBillTypeEnum.refundment)) {
			result = false;
		}
		Map entryMap = new HashMap();	//考虑多结算方式后的明细的映射  <款项id,收款分录明细> 
		for(int i=0;i<tblEntry.getRowCount();i++)
		{
			IRow row = tblEntry.getRow(i);
			Object obj = row.getUserObject();
			if(obj==null || !(obj instanceof FDCReceivingBillEntryInfo)) {
				continue;
			}
			//UPdate by zhiyuan_tang 2010/09/25
			if (isSettleTypeNecessary()) {
				//如果结算方式必须录入 ，则还是原来的逻辑
//				if(row.getCell(COL_STLE_TYPE).getValue()==null)
//				{
//						MsgBox.showInfo("第"+(i+1)+"行结算方式不能为空!");
//						this.abort();
//				}
//				if(row.getCell(COL_REV_ACCOUNT).getValue()==null)
//				{
//					if (result) {
//						MsgBox.showInfo("第" + (i + 1) + "行收款科目不能为空!");
//					} else {
//						MsgBox.showInfo("第" + (i + 1) + "行退款科目不能为空!");
//					}
//					
//					this.abort();
//				}
//				if(row.getCell(COL_OPP_ACCOUNT).getValue()==null)
//				{
//					MsgBox.showInfo("第"+(i+1)+"行对方科目不能为空!");
//					this.abort();
//				}
			}
			
			Object revObject = row.getCell(COL_REV_AMOUNT).getValue();
			if(RevBillTypeEnum.adjust.equals(this.editData.getRevBillType())){
				
			}else{
				if(revObject==null || !(revObject instanceof BigDecimal) || ((BigDecimal)revObject).compareTo(FDCHelper.ZERO)<=0) {
					MsgBox.showInfo("第"+(i+1)+"行收/退款金额不正确!");
					this.abort();
				}
			}
			BigDecimal curRevAmount = (BigDecimal)row.getCell(COL_REV_AMOUNT).getValue();
			//同一款项的多结算方式的的收款金额要合计起来，而且只算一条
			//原则上同一款项都是合并在一起的，不会分开的，所以可以考虑多结算方式的情况
			//利用RevLocAmount存储当前输入状态下的收款金额			
			FDCReceivingBillEntryInfo revEntry = (FDCReceivingBillEntryInfo)obj;
			if(entryMap.containsKey(revEntry.getRevListId())){
				FDCReceivingBillEntryInfo existInfo = (FDCReceivingBillEntryInfo)entryMap.get(revEntry.getRevListId());
				existInfo.setRevLocAmount(existInfo.getRevLocAmount().add(curRevAmount));
				//为了修改是多结算方式情况下比较金额用的
				BigDecimal lsRevAmount = existInfo.getRevAmount()==null?new BigDecimal(0):existInfo.getRevAmount();
				lsRevAmount = lsRevAmount.add(revEntry.getRevAmount()==null?new BigDecimal(0):revEntry.getRevAmount());
				existInfo.setRevAmount(lsRevAmount);
			}else{
				FDCReceivingBillEntryInfo newInfo = (FDCReceivingBillEntryInfo)revEntry.clone();
				newInfo.setRevLocAmount(curRevAmount);
				entryMap.put(revEntry.getRevListId(), newInfo);
			}			
		}
		
		
		Iterator iterator = entryMap.keySet().iterator();
		while(iterator.hasNext()) {
			FDCReceivingBillEntryInfo billEntryInfo = (FDCReceivingBillEntryInfo)entryMap.get(iterator.next());
			IRevListInfo revListInfo = billEntryInfo.getRevListInfo();
			if(revListInfo==null)return;
			
			//如果是修改状态，这明细id和收款金额都不可能为空的
			if(this.getOprtState().equals(OprtState.EDIT)){
				if(billEntryInfo.getId()==null || billEntryInfo.getRevAmount()==null)	{
					MsgBox.showInfo("修改时不支持更换明细的修改!");
					this.abort();
				}	
			}
			
			//判断超收的问题			
			if(RevBillTypeEnum.refundment.equals(this.comboRevBillType.getSelectedItem())){				
				BigDecimal limitAmount = revListInfo.getRemainLimitAmount();
				BigDecimal refAmount = billEntryInfo.getRevLocAmount();
				if(this.getOprtState().equals(OprtState.ADDNEW)) {  //新增单据时
					if(limitAmount.compareTo(refAmount) < 0){
						MsgBox.showInfo("款项"+revListInfo.getMoneyDefine()+"的退款金额不能大于可退金额!");
						this.abort();
					}
				}else if(this.getOprtState().equals(OprtState.EDIT)) {	
					BigDecimal modifyAmount = refAmount.subtract(billEntryInfo.getRevAmount().negate());  //修改的差额
					if(limitAmount.compareTo(modifyAmount) < 0){
						MsgBox.showInfo("款项"+revListInfo.getMoneyDefine()+"的退款金额不能大于可退金额!");
						this.abort();
					}
				}
			}
			
			
			//收款，转入不能超收
			if(RevBillTypeEnum.gathering.equals(this.comboRevBillType.getSelectedItem())  
					||  RevBillTypeEnum.transfer.equals(this.comboRevBillType.getSelectedItem())){
				if(!revListInfo.isIsCanRevBeyond()){
					BigDecimal revAmount = billEntryInfo.getRevLocAmount();
					revAmount = revAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal appAmount = revListInfo.getAppAmount();
					BigDecimal actRevAmount = revListInfo.getActRevAmount();
					if(actRevAmount != null){
						actRevAmount = actRevAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					}
					BigDecimal allRemainAmount = revListInfo.getAllRemainAmount();
					
					//款项类型的类别为空。。重新查一下
					MoneyDefineInfo moenyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(revListInfo.getMoneyDefine().getId()));
					BigDecimal remissionAmount = getBigDecimal(this.getAction()==null?null:this.getAction().getRemissionAmountRevList(revListInfo));

					appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					
					if(this.getOprtState().equals(OprtState.ADDNEW)) {  //新增单据时
						if(moenyInfo.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) {//应收款项中'定金'类是和实收金额比较,其它类是和剩余金额比较)
							if(revAmount.compareTo(appAmount.subtract(actRevAmount)) > 0){
								MsgBox.showInfo("款项"+moenyInfo.getName()+"的收款金额超过限制!["+revAmount+">(应收"+appAmount+"-已收"+actRevAmount+")]");
								this.abort();
							}			
						}else{
//							if(revAmount.compareTo(appAmount.subtract(allRemainAmount).subtract(remissionAmount)) > 0){
								if(revAmount.compareTo(appAmount.subtract(allRemainAmount)) > 0){
								///MsgBox.showInfo("款项"+moenyInfo.getName()+"的收款金额超过限制!["+revAmount+
										//">(应收"+appAmount+"-总剩余"+allRemainAmount+")]");
								        //这里的应收已经是减免后的金额不需要再重新减掉减免金额 --by jun_li
										//">(应收"+appAmount+"-总剩余"+allRemainAmount+ (remissionAmount.compareTo(FDCHelper.ZERO)>0?("-减免"+remissionAmount):"") +")]");
									/**
									 * 根据测试的要求更改此处的提示！
									 * by renliang at 2010-12-24
									 */
									MsgBox.showInfo("收款金额不能大于应收金额!");
								this.abort();
							}						
						}
					}else if(this.getOprtState().equals(OprtState.EDIT)) {						
						BigDecimal modifyAmount = revAmount.subtract(billEntryInfo.getRevAmount());  //修改的差额
						if(moenyInfo.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) {//应收款项中'定金'类是和实收金额比较,其它类是和剩余金额比较)
							BigDecimal leftAmount = appAmount.subtract(actRevAmount);	//剩余的金额
							if(modifyAmount.compareTo(leftAmount) > 0){
								MsgBox.showInfo("款项"+moenyInfo.getName()+"的收款金额超过限制![修改差额"+modifyAmount+">(应收"+appAmount+"-已收"+actRevAmount+")]");
								this.abort();
							}			
						}else{
							BigDecimal leftAmount = appAmount.subtract(allRemainAmount).subtract(remissionAmount);	//剩余的金额
							if(modifyAmount.compareTo(leftAmount) > 0){
								MsgBox.showInfo("款项"+moenyInfo.getName()+"的收款金额超过限制![修改差额"+modifyAmount+
										">(应收"+appAmount+"-总剩余"+allRemainAmount+ (remissionAmount.compareTo(FDCHelper.ZERO)>0?("-减免"+remissionAmount):"") +")]");
								this.abort();
							}						
						}						
					}
				}
			}
			
		}
		
	}
	
	//多种BOTP规则共存时,需要用户选择
	public boolean isNeedShowBOTPRule() {
		return true;
	}
	
	/**
	 * 初始化收款时的分录Table列名
	 * */
	protected void initEntryTableOfGathering() {
		this.tblEntry.checkParsed();
		this.tblEntry.getColumn(COL_FROM_MONEY_DEFINE).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_FROM_ACCOUNT).getStyleAttributes().setHided(true);
		
		this.tblEntry.getColumn(COL_STLE_COUNT).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_STLE_TYPE).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_STLE_NUMBER).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_REV_BANK_ACCOUNT).getStyleAttributes().setHided(false);
		this.tblEntry.getColumn(COL_CUS_ACCOUNT).getStyleAttributes().setHided(false);
		
		this.tblEntry.getColumn(COL_REV_ACCOUNT).getStyleAttributes().setHided(false);
		
		this.tblEntry.getColumn(COL_LIMIT_AMOUNT).getStyleAttributes().setHided(true);
		this.tblEntry.getColumn(COL_HAS_REFUNDMENT_AMOUNT).getStyleAttributes().setHided(true);
		
		this.tblEntry.getHeadRow(0).getCell(COL_MONEY_DEFINE).setValue("收款款项");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_ACCOUNT).setValue("收款科目");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_BANK_ACCOUNT).setValue("收款帐户");
		this.tblEntry.getHeadRow(0).getCell(COL_REV_AMOUNT).setValue("收款金额");
		
		this.tblEntry.getHeadRow(0).getCell(COL_OPP_ACCOUNT).setValue("对方科目");
		
		if(isShowSupplyCols()){
			this.tblEntry.getColumn(COL_SUPPLY_DES).getStyleAttributes().setHided(false);
			this.tblEntry.getColumn(COL_SUPPLY_ORG).getStyleAttributes().setHided(false);
		}else{
			this.tblEntry.getColumn(COL_SUPPLY_DES).getStyleAttributes().setHided(true);
			this.tblEntry.getColumn(COL_SUPPLY_ORG).getStyleAttributes().setHided(true);
		}
	}

	/**
	 * 收款单分录是否显示代收列
	 * */
	protected boolean isShowSupplyCols(){
		return false;
	}
	
	/**
	 * 在预收收款选择列表，是否显示应收金额，应收日期字段
	 * */
	protected boolean isShowAppColOfPreRev(){
		return true;
	}
	
	/**
	 * 初始化打开选择收款明细界面的传入参数
	 * */
	protected UIContext prepareSelectRevListContext() throws BOSException, EASBizException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put(KEY_IS_LOCK_BILL_TYPE, this.getUIContext().get(KEY_IS_LOCK_BILL_TYPE));
		
		uiContext.put(SelectRevListUI.KEY_IS_SHOW_APP_COL_OF_PREREV, new Boolean(isShowAppColOfPreRev()));
		
		//重新选择不清除原明细的情况下, TODO 只支持收款
		if(!isRmRowsAfterSelectRevList()  &&  this.comboRevBillType.getSelectedItem().equals(RevBillTypeEnum.gathering)){
			Set set = new HashSet();
			for(int i=0; i<this.tblEntry.getRowCount(); i++){
				IRow row = this.tblEntry.getRow(i);
				
				Object obj = row.getUserObject();
				if(obj instanceof FDCReceivingBillEntryInfo){
					FDCReceivingBillEntryInfo revEntry = (FDCReceivingBillEntryInfo) obj;
					if(revEntry.getRevListId() != null){
						set.add(revEntry.getRevListId());
					}
				}
			}
			if(!set.isEmpty()){
				uiContext.put(SelectRevListUI.KEY_HAS_SELECTED_IDS, set);
			}
		}
		
		uiContext.put(SelectRevListUI.KEY_APP_REV_LIST, getAppRevList());
		uiContext.put(SelectRevListUI.KEY_DIR_REV_LIST, getDirRevList());
		uiContext.put(SelectRevListUI.KEY_PRE_REV_LIST, getPreRevList());
		uiContext.put(SelectRevListUI.KEY_APP_REV_REFUNDMENT_LIST, getAppRevRefundmentList());
		uiContext.put(SelectRevListUI.KEY_APP_REFUNDMENT_LIST, getAppRefundmentList());
		
		uiContext.put(SelectRevListUI.KEY_BUILD_REV_LIST_ACTION, getAction());
		
		//TODO 后续转款的实现可能需要改变,因为需要适应转款规则的要求
		uiContext.put(SelectRevListUI.KEY_TO_TRANSFER_REV_LIST, getToTransferRevList());
		uiContext.put(SelectRevListUI.KEY_REV_BILL_TYPE, this.comboRevBillType.getSelectedItem());
		uiContext.put(SelectRevListUI.KEY_REV_BIZ_TYPE, this.comboRevBizType.getSelectedItem());
		uiContext.put(SelectRevListUI.KEY_MONEYSYSTYPE, getMoneySysType());
		
		return uiContext;
	}
	
	protected void f7Currency_dataChanged(DataChangeEvent e) throws Exception {
		Object newValue = e.getNewValue();
		if (newValue!=null && newValue instanceof CurrencyInfo) {
			BOSUuid srcid = ((CurrencyInfo)newValue).getId();			
	    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,company,bookedDate);
	    	
	    	//int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
	    	if(exchangeRate!=null){
	    		BigDecimal exRate = exchangeRate.getConvertRate();
	    		int exPrecision = exchangeRate.getPrecision();
		    	this.txtExchangeRate.setValue(exRate);
		    	this.txtExchangeRate.setPrecision(exPrecision);
	    	}else{
	    		this.txtExchangeRate.setValue(FDCHelper.ONE);
		    	this.txtExchangeRate.setPrecision(2);
	    	}
	    	
			// 本位币处理   如果选择的是本位币，则汇率不允许修改
	    	this.txtExchangeRate.setEditable(true);
			CurrencyInfo baseCurrency = company.getBaseCurrency();
			if (baseCurrency != null) {
				if (srcid.equals(baseCurrency.getId())) {
					this.txtExchangeRate.setEditable(false);
				}
			}
		}
	}
	
	protected void tblEntry_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IColumn col = this.tblEntry.getColumn(colIndex);
		if(col == null){
			this.abort();
		}
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(!CRMClientHelper.isValueChanged(e)){
			this.abort();
		}
		
		String colKey = col.getKey();
		
		int rowIndex = e.getRowIndex();
		IRow row = this.tblEntry.getRow(rowIndex);
		
		if(COL_STLE_COUNT.equals(colKey)){
			int newStleCount = (Integer)newValue == null ? 0 : ((Integer)newValue).intValue();
			int minCount = 1;
			int maxCount = 20;
			
			if(newStleCount < minCount  ||  newStleCount > maxCount){
				MsgBox.showInfo(this, "结算笔数只能在" + minCount + "到" + maxCount + "之间!");
				ICell cell = this.tblEntry.getCell(rowIndex, colIndex);
				if(cell != null){
					cell.setValue(oldValue);
				}
				this.abort();
			}
			
			//下面的增加减少结算方式行逻辑很危险.界面显示规则发生变动后,这里可能会出错 TODO
			int oldStleCount = ((Integer)oldValue).intValue();
			if(newStleCount > oldStleCount){
				int addRows = newStleCount - oldStleCount;
				for(int i=0; i<addRows; i++){
					FDCReceivingBillEntryInfo obj = (FDCReceivingBillEntryInfo) row.getUserObject();
					IRow addRow = (IRow) row.clone();
					addRow.setUserObject(obj.clone());
//					addRow.getCell(COL_STLE_COUNT).setValue(newValue);
					this.tblEntry.addRow(rowIndex + oldStleCount - 1, addRow);
				}
				mergeColumns(rowIndex, rowIndex + newStleCount - 1);
			}else if(newStleCount < oldStleCount){
				int removeRows = oldStleCount - newStleCount;
				for(int i=0; i<removeRows; i++){
					this.tblEntry.removeRow(rowIndex + newStleCount);
				}
			}
			setUnionAmount();
		}else if(COL_REV_AMOUNT.equals(colKey)){
			setUnionAmount();
		}else if(COL_FROM_ACCOUNT.equals(colKey)){
			//如果是转款,将转出科目同步到转入明细行的 收款科目 字段上
			if(RevBillTypeEnum.transfer.equals(this.comboRevBillType.getSelectedItem())){
				// TODO 按目前的转款模式,转入款项的借方要等于转出款项的贷方,此分录只会有一条记录.
				// 这样看来转款来源不用放在分录上,可以作为关联字段放在收款单分录上.
				// 但如果不考虑科目问题的话,此分录可以是多条的.故仍保留这种分录结构
				IRow toRow = this.tblEntry.getRow(rowIndex - 1);
				toRow.getCell(COL_REV_ACCOUNT).setValue(newValue);
			}
		}
	}

	protected void setUnionAmount() {
		BigDecimal tol = FDCHelper.ZERO;
		for(int i=0; i<this.tblEntry.getRowCount(); i++){
			IRow tmpRow = this.tblEntry.getRow(i);
			//转款情况下,转出款项的行的userObject是IRevListInfo
			if(!(tmpRow.getUserObject() instanceof FDCReceivingBillEntryInfo)){
				continue;
			}
			BigDecimal t = (BigDecimal) tmpRow.getCell(COL_REV_AMOUNT).getValue();
			if(t != null){
				tol = tol.add(t);
			}
		}
		this.txtRecAmount.setValue(tol);
		//TODO 通过汇率获得本位币金额
		this.txtRecLocAmount.setValue(tol);
	}
	
	public void onLoad() throws Exception {
		this.comboRevBillType.setEnabled(false);
		this.f7Currency.setEnabled(false);
		this.txtRecLocAmount.setEnabled(false);
		this.txtRecAmount.setEnabled(false);
		this.contCustomer.setVisible(false);
		this.f7FdcCustomers.setRequired(true);
		this.f7Creator.setDisplayFormat("$name$");
		this.f7Auditor.setDisplayFormat("$name$");
//		this.txtDescription.setMaxLength(255);
		
		initEntryTable();
		setProjectFilter();
		super.onLoad();
		
		this.txtDescription.setMaxLength(255);
		
		String sysTypeStr = (this.getMoneySysType()==null?"售楼":this.getMoneySysType().getAlias().substring(0,2));
		this.setUITitle( sysTypeStr + this.editData.getRevBillType() + "单");
		
		initEntryTableByRevBillType(this.editData.getRevBillType());
		
		Boolean isLockType = (Boolean)this.getUIContext().get(KEY_IS_LOCK_BILL_TYPE);
		if(isLockType != null  &&  isLockType.booleanValue()){
			this.comboRevBizType.setEnabled(false);
		}
		//目前修改暂不支持增减收款单分录条数,但其实还是可以支持的.后续如果支持了,这里的控制需要修改 TODO
		if(STATUS_VIEW.equals(oprtState)  ||  STATUS_FINDVIEW.equals(oprtState) || STATUS_EDIT.equals(oprtState)){
			this.tblEntry.getColumn(COL_STLE_COUNT).getStyleAttributes().setHided(true);
			this.btnSelectRevList.setEnabled(false);
		}else{
			this.tblEntry.getColumn(COL_STLE_COUNT).getStyleAttributes().setHided(false);
			this.btnSelectRevList.setEnabled(true);
		}
		
		//删除到序时簿去删吧. TODO 标准功能还是实现的好啊。。
		this.actionRemove.setVisible(false);
		
		this.actionSave.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionReceive.setVisible(true);
		this.actionAudit.setVisible(true);	
		this.actionReceive.setEnabled(true);
		
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		
		this.actionWorkFlowG.setVisible(true);
		this.actionNextPerson.setVisible(true);
		this.actionAuditResult.setVisible(true);
		this.actionMultiapprove.setVisible(true);
		
		//非实体销售组织不能发生业务 
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		List selectedIds = new ArrayList();
		selectedIds.add(this.editData.getId().toString());
		if(!RevBillStatusEnum.SUBMIT.equals(this.editData.getBillStatus()))
		{
			MsgBox.showInfo("只有提交状态的收款单才能审批!");
			this.abort();
		}
		((IFDCReceivingBill) getBizInterface()).audit(selectedIds);
		this.editData.setBillStatus(RevBillStatusEnum.AUDITED);
		
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		this.setOprtState("VIEW");
	}
	
	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}

	private void initEntryTable() {
		this.tblEntry.checkParsed();
		FDCTableHelper.disableDelete(this.tblEntry);
		//TODO 暂不考虑外币问题
		this.tblEntry.getColumn(COL_LOC_AMOUNT).getStyleAttributes().setHided(true);
		
		// 收款科目
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");

		ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
		if (isSettleTypeNecessary()) {
			this.tblEntry.getColumn(COL_REV_ACCOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
		this.tblEntry.getColumn(COL_REV_ACCOUNT).setEditor(f7Editor);
		
		//对方科目
		if (isSettleTypeNecessary()) {
			this.tblEntry.getColumn(COL_OPP_ACCOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
		this.tblEntry.getColumn(COL_OPP_ACCOUNT).setEditor(f7Editor);
		
		//
		this.tblEntry.getColumn(COL_FROM_ACCOUNT).setEditor(f7Editor);
		
		setColumnLocked(COL_MONEY_DEFINE);
		setColumnLocked(COL_FROM_MONEY_DEFINE);
		setColumnLocked(COL_FROM_ACCOUNT);
		setColumnLocked(COL_APP_AMOUNT);
		setColumnLocked(COL_ACT_REV_AMOUNT);
		setColumnLocked(COL_REMISSION_AMOUNT);
		setColumnLocked(COL_LIMIT_AMOUNT);
		setColumnLocked(COL_HAS_REFUNDMENT_AMOUNT);
		
		setColumnLocked(COL_SUPPLY_ORG);
//		setColumnLocked(COL_SUPPLY_DES);
		
		setAmountColumn(COL_REV_AMOUNT);
		setAmountColumn(COL_LOC_AMOUNT);
		setAmountColumn(COL_APP_AMOUNT);
		setAmountColumn(COL_ACT_REV_AMOUNT);
		setAmountColumn(COL_REMISSION_AMOUNT);
		setAmountColumn(COL_LIMIT_AMOUNT);
		setAmountColumn(COL_HAS_REFUNDMENT_AMOUNT);
		
		this.tblEntry.getColumn(COL_REV_AMOUNT).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblEntry.getColumn(COL_STLE_COUNT).setEditor(CRMClientHelper.getIntegerCellEditor());
		
		
		this.tblEntry.getColumn(COL_STLE_TYPE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery", null));
//		this.tblEntry.getColumn(COL_REV_ACCOUNT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.master.account.app.AccountViewQuery", null));
		
		//收款账户根据当前组织过滤
		EntityViewInfo vieInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		CompanyOrgUnitInfo companyInfo = SysContext.getSysContext().getCurrentFIUnit();
		if(companyInfo!=null){
			filterInfo.getFilterItems().add(new FilterItemInfo("company.id",companyInfo.getId().toString()));
		}
		vieInfo.setFilter(filterInfo);
		this.tblEntry.getColumn(COL_REV_BANK_ACCOUNT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery", vieInfo));
//		this.tblEntry.getColumn(COL_OPP_ACCOUNT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.master.account.app.AccountViewQuery", null));
		
		if (isSettleTypeNecessary()) {
			this.tblEntry.getColumn(COL_STLE_TYPE).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
//		this.tblEntry.getColumn(COL_REV_ACCOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblEntry.getColumn(COL_REV_AMOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
//		this.tblEntry.getColumn(COL_OPP_ACCOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.tblEntry.getColumn(COL_SUPPLY_ORG).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.org.app.FullOrgUnitQuery", null));
	}
	
	private void setAmountColumn(String colName){
		this.tblEntry.getColumn(colName).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.tblEntry.getColumn(colName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	
	private void setColumnLocked(String colName){
		IColumn col = this.tblEntry.getColumn(colName);
		col.getStyleAttributes().setLocked(true);
	}
	
	private void setProjectFilter() throws EASBizException, BOSException
	{
		
		
		 UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		 String permitSpStr = MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(userInfo);
		 EntityViewInfo viewInfo = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();	
		 filter.getFilterItems().add(new FilterItemInfo("id",permitSpStr,CompareType.INNER));
		 MoneySysTypeEnum sysType = getMoneySysType();
		 if(MoneySysTypeEnum.SalehouseSys.equals(sysType))		 {
			 filter.getFilterItems().add(new FilterItemInfo("isForSHE",Boolean.TRUE));
		 }else if(MoneySysTypeEnum.TenancySys.equals(sysType))		 {
			 filter.getFilterItems().add(new FilterItemInfo("isForTen",Boolean.TRUE)); 
		 }else if(MoneySysTypeEnum.ManageSys.equals(sysType))		 {
			 filter.getFilterItems().add(new FilterItemInfo("isForPPM",Boolean.TRUE));
		 }	 
		 viewInfo.setFilter(filter);
		 f7SellProject.setEntityViewInfo(viewInfo);
	}
	
	protected void initBizTypeCombo() {
		this.removeDataChangeListener(this.comboRevBizType);
		this.comboRevBizType.removeAllItems();
		this.comboRevBizType.addItems(getBizTypes());
		
		this.comboRevBizType.setSelectedItem(this.editData.getRevBizType());
		
		this.addDataChangeListener(this.comboRevBizType);
	}
	
	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 返回各业务系统可能的收款业务类型,如 new RevBizTypeEnum[]{RevBizTypeEnum.customer};
	 * */
	protected RevBizTypeEnum[] getBizTypes(){
		return null;
	};
	
	protected void comboRevBizType_itemStateChanged(ItemEvent e) throws Exception {
		if(isLoadFields){
			this.abort();
		}
		RevBizTypeEnum revBizType = (RevBizTypeEnum) e.getItem();
		initCompentByBizType(revBizType);
		this.tblEntry.removeRows(false);
		setUnionAmount();
	}

	/**
     * @description 本方法原本为抽象方法要在子类实现，因为本类从抽象类改造为实体类， 所以在子类中本方法必须重写
     * @author tim_gao
     * @date 2010-12-3
     */
	/**
	 * 根据收款业务类型,对各种控件进行显示或隐藏的设置
	 * */
	protected void initCompentByBizType(RevBizTypeEnum revBizType){};
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}
	
	/**
	 * 描述：由于物业收费中要求结算方式、收款科目、对方科目三个项目可以不录入
	 *       所以在这里添加一个方法供子类继承。子类可以继承他，来实现自己想要的校验逻辑。<br>
	 *       默认是返回true，即还是按照原来的逻辑进行校验。<br>
	 *       物业新收款单则要继承它，然后修改返回值为：false。<br>
	 * @author zhiyuan_tang  Date:2010/09/25
	 * @return    true:必填项
	 *            false：非必填项
	 */
	protected boolean isSettleTypeNecessary() {
		return true;
	}
	/**
	 * @author tim_gao
	 * @date 2010-12-3
	 */
	public IUIObject getInstance(Map uiContext) {// 因为要实例所以本编辑界面不能为抽象的
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("revBizType");
		RevBizTypeEnum revBizType =null;
			try {
				if (uiContext.get("BOTPFilter") != null) {

				uiContext.get("BillId");
				FDCReceivingBillCollection revs = FDCReceivingBillFactory
						.getRemoteInstance().getFDCReceivingBillCollection(
								(EntityViewInfo) uiContext.get("BOTPFilter"));
				if (revs != null && !revs.isEmpty()) {
					revBizType = revs.get(0).getRevBizType();

				}
				}else if( uiContext.get("isFromWorkflow") != null){
					if(uiContext.get("BillId")!=null){
					FDCReceivingBillInfo revs = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(uiContext.get("BillId").toString())), sels);
					if (revs != null && !revs.isEmpty()) {
						revBizType = revs.getRevBizType();
						
					}
					}
				}
				if(revBizType!=null){
				if (RevBizTypeEnum.manage.equals(revBizType)) {
					PPMNewReceiveBillEditUI rev = new PPMNewReceiveBillEditUI();
					return rev;
				} else if (RevBizTypeEnum.obligate.equals(revBizType)
						|| RevBizTypeEnum.tenancy.equals(revBizType)) {
					TENReceivingBillEditUI rev = new TENReceivingBillEditUI();
					return rev;
				} else {
					// 售楼的
					return new SHEReceivingBillEditUI();
				}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			
		
		return super.getInstance(uiContext);
	}

	
	

}