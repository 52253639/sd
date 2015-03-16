/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class GatheringSettlementDetailListUI extends AbstractGatheringSettlementDetailListUI
{
	//合同款项 的8个款项
	private static final String ContractMoneyTypeStr = "'"+MoneyTypeEnum.PRECONCERTMONEY_VALUE +"','"+ MoneyTypeEnum.EARNESTMONEY_VALUE 
							+"','" + MoneyTypeEnum.HOUSEAMOUNT_VALUE +"','" + MoneyTypeEnum.FISRTAMOUNT_VALUE +"','"+ MoneyTypeEnum.COMPENSATEAMOUNT_VALUE 
							+"'";//,'"+ MoneyTypeEnum.REFUNDMENT_VALUE+"'";  不包含退款 zhicheng_jin 091225
	
    private static final String KEY_SHOUKUAN_TOTAL = "shoukuanTotal";	//收款合计

	private static final String KEY_ALL_TOTAL = "allTotal";    //所有款项合计

	private static final String KEY_ELSE_TOTAL = "elseTotal";

	private static final String KEY_TAX_TOTAL = "taxTotal";

	private static final String KEY_SHOUXU_TOTAL = "shouxuTotal";

	private static final String KEY_DAISHOU_TOTAL = "daishouTotal";

	private static final String KEY_PLAN_TOTAL = "planTotal";

	/** 定金结转列 */
	private static final String KEY_EARN_A = "asdf23224";
	
	/** 合同款外合计 */
	private static final String KEY_OUT_PLAN_TOTAL = "outPlanTotal";
	
	private static final Logger logger = CoreUIObject.getLogger(GatheringDetailListUI.class);
    
    private ItemAction[] hiddenAction = new ItemAction[]{this.actionView ,this.actionAddNew,this.actionEdit,this.actionRemove,this.actionLocate,this.actionEdit};

    private Map moneyDefineMap = new HashMap();
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    //结算方式明细
    private final String SETTLE_TYPE = "settlementType";
    private final String BANK_AMT = "bankAmount";
    private final String BANK_ACCOUNT = "bankAccount";
    private final String BANK_ACCT_NUM = "bankAccountNumber";
    
    private  Color preTax = new Color(0xD2E3CA);
	private  Color aftTax = new Color(0xE3EFDE);
    
    public GatheringSettlementDetailListUI() throws Exception
    {
        super();
    }
    
	protected boolean initDefaultFilter() {
		return true;
	}
	
	private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private GatheringDetailFilterUI filterUI = null;
	private GatheringDetailFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new GatheringDetailFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	this.initTable();
    	
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		FDCClientHelper.setActionVisible(this.hiddenAction,false);
    }

    public void storeFields()
    {
        super.storeFields();
    }
    protected String getKeyFieldName()
    {
    	return "room";
    }
    
    private void initTable() throws BOSException
    {
    	this.tblMain.checkParsed();
    	moneyDefineMap.clear();
    	    	
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		for(int i = tblMain.getColumnCount()-1; i > 5; --i)
		{
			tblMain.removeColumn(i);
		}
		/**
		 * 款项名称
		 */
		
    	//预收款
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");	
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PreMoney));		
		MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		
		IRow row1 = this.tblMain.getHeadRow(1);
    	IRow row0 = this.tblMain.getHeadRow(0);
    	
		for(int i = 0; i < moneyColl.size(); i ++)	{
			MoneyDefineInfo info = moneyColl.get(i);
	    	IColumn col = this.tblMain.addColumn();
	    	col.setKey(info.getId().toString());
	    	
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}		
		
		
		//计划性款项
    	view = new EntityViewInfo();
		filter = new FilterInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add("moneyType");	
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType",ContractMoneyTypeStr,CompareType.INCLUDE));
		view.setFilter(filter);
		moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);		

		for(int i = 0; i < moneyColl.size(); i ++)		{
			MoneyDefineInfo info = moneyColl.get(i);
			moneyDefineMap.put(info.getId().toString(), KEY_PLAN_TOTAL);
			
	    	IColumn col = this.tblMain.addColumn();
	    	col.setKey(info.getId().toString());
	    
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}

		IColumn col = null;
		if(moneyColl.size() > 0)		{
			// 合同款合计
			col = this.tblMain.addColumn();
			col.setKey(KEY_PLAN_TOTAL);

			row0.getCell(KEY_PLAN_TOTAL).setValue("款项名称");
			row1.getCell(KEY_PLAN_TOTAL).setValue("合同款合计");
		}
		
		//代收费用类型的款型
    	view = new EntityViewInfo();
		filter = new FilterInfo();
    	view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
    	filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.REPLACEFEE_VALUE));
    	
		moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		for(int i = 0; i < moneyColl.size(); i ++)		{
			MoneyDefineInfo info = moneyColl.get(i);
			moneyDefineMap.put(info.getId().toString(), KEY_DAISHOU_TOTAL);
			col = this.tblMain.addColumn();
	    	col.setKey(info.getId().toString());
	    	
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}
		
		//代收款合计
		if(moneyColl.size() > 0)		{
			col = this.tblMain.addColumn();
			col.setKey(KEY_DAISHOU_TOTAL);
			
			row0.getCell(KEY_DAISHOU_TOTAL).setValue("款项名称");
	    	row1.getCell(KEY_DAISHOU_TOTAL).setValue("代收款合计");
		}
    	//手续费款项
    	filter = new FilterInfo();
    	view = new EntityViewInfo();
    	view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
    	filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.COMMISSIONCHARGE_VALUE));    	   	
		moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);		
		for(int i = 0; i < moneyColl.size(); i ++)		{
			MoneyDefineInfo info = moneyColl.get(i);
			moneyDefineMap.put(info.getId().toString(), KEY_SHOUXU_TOTAL);
	    	col = this.tblMain.addColumn();
	    	col.setKey(info.getId().toString());
	    	
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}
		//手续费款合计
		if(moneyColl.size() > 0)		{
			col = this.tblMain.addColumn();
			col.setKey(KEY_SHOUXU_TOTAL);
			
			row0.getCell(KEY_SHOUXU_TOTAL).setValue("款项名称");
	    	row1.getCell(KEY_SHOUXU_TOTAL).setValue("手续费合计");
		}
    	
       //税金款项
    	filter = new FilterInfo();
    	view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.TAXFEE_VALUE));
    	
		moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		for(int i = 0; i < moneyColl.size(); i ++)		{
			MoneyDefineInfo info = moneyColl.get(i);
			moneyDefineMap.put(info.getId().toString(), KEY_TAX_TOTAL);
	    	col = this.tblMain.addColumn();
	    	
	    	col.setKey(info.getId().toString());	    
	    	
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}
		//税金合计
		if(moneyColl.size() > 0)		{
			col = this.tblMain.addColumn();
			col.setKey(KEY_TAX_TOTAL);
			
			row0.getCell(KEY_TAX_TOTAL).setValue("款项名称");
	    	row1.getCell(KEY_TAX_TOTAL).setValue("税金合计");
		}
    	
    	//其他款项
		filter = new FilterInfo();
    	view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.ELSEAMOUNT_VALUE));
		moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		for(int i = 0; i < moneyColl.size(); i ++)		{
			MoneyDefineInfo info = moneyColl.get(i);
			moneyDefineMap.put(info.getId().toString(), KEY_ELSE_TOTAL);
	    	col = this.tblMain.addColumn();
	    	col.setKey(info.getId().toString());
	    
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}
		//其他合计
		if(moneyColl.size() > 0)		{
			col = this.tblMain.addColumn();
			col.setKey(KEY_ELSE_TOTAL);
			
			row0.getCell(KEY_ELSE_TOTAL).setValue("款项名称");
	    	row1.getCell(KEY_ELSE_TOTAL).setValue("其他款合计");
		}
		
		//退款
		filter = new FilterInfo();
    	view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.REFUNDMENT_VALUE));
		moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		for(int i = 0; i < moneyColl.size(); i ++)		{
			MoneyDefineInfo info = moneyColl.get(i);
			col = this.tblMain.addColumn();
	    	col.setKey(info.getId().toString());
	    	
	    	row0.getCell(info.getId().toString()).setValue("款项名称");
	    	row1.getCell(info.getId().toString()).setValue(info.getName());
		}
    	
    	//所有款合计
    	col = this.tblMain.addColumn();
    	col.setKey(KEY_ALL_TOTAL);    	
    	row0.getCell(KEY_ALL_TOTAL).setValue("所有款项合计");
    	row1.getCell(KEY_ALL_TOTAL).setValue("所有款项合计");

		col = this.tblMain.addColumn();
		col.setKey(KEY_EARN_A);    	
    	row0.getCell(KEY_EARN_A).setValue("定金结转");
    	row1.getCell(KEY_EARN_A).setValue("定金结转");
    	
    	
    	/**
    	 * 结算方式添加4列
    	 */
    	IColumn col0 = this.tblMain.addColumn();
    	col0.setKey(SETTLE_TYPE);
		row0.getCell(SETTLE_TYPE).setValue("结算");
		row1.getCell(SETTLE_TYPE).setValue("结算方式");	 
		
		IColumn col1 = this.tblMain.addColumn();
		col1.setKey(BANK_AMT);
		row0.getCell(BANK_AMT).setValue("结算");
		row1.getCell(BANK_AMT).setValue("收款金额");	 
    	
		
		IColumn col2 = this.tblMain.addColumn();
		col2.setKey(BANK_ACCOUNT);
		row0.getCell(BANK_ACCOUNT).setValue("结算");
    	row1.getCell(BANK_ACCOUNT).setValue("收款银行账户");
    	
		IColumn col3 = this.tblMain.addColumn();
		col3.setKey(BANK_ACCT_NUM);
		row0.getCell(BANK_ACCT_NUM).setValue("结算");
		row1.getCell(BANK_ACCT_NUM).setValue("收款银行账号");	    	
    	
    	//收款合计
    	col = this.tblMain.addColumn();
    	col.setKey(KEY_SHOUKUAN_TOTAL);
    	col.getStyleAttributes().setNumberFormat("%r-[ ]0.2n");    	
    	row0.getCell(KEY_SHOUKUAN_TOTAL).setValue("收款合计");
    	row1.getCell(KEY_SHOUKUAN_TOTAL).setValue("收款合计");
    	
    	for (int i = 6; i < this.tblMain.getColumnCount() - 1; i++) {
			IColumn column = this.tblMain.getColumn(i);
			
			if(column.getKey().equals(BANK_ACCOUNT) || column.getKey().equals(BANK_ACCT_NUM))
			{
				continue;
			}
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
    	
    	tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
    }

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected void initTree() throws Exception 
	{
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		//初始化表格
		initTable();
		
		//填充数据
		getAllData();
	}
	
	protected void getAllData() throws EASBizException, BOSException
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		this.tblMain.removeRows(false);
		tblMain.getTreeColumn().setDepth(2);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		//根据所选组织架构节点填充过滤信息
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) 
		{
			filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.isForSHE", new Integer(1)));			
			if (node.getUserObject() instanceof Integer) 
			{
				Integer unit = (Integer) node.getUserObject();
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.building.id", buildingId));
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.unit", unit));
			}
			else if (node.getUserObject() instanceof BuildingInfo) 
			{
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.building.id", buildingId));
			}
			else if (node.getUserObject() instanceof SellProjectInfo) 
			{
				SellProjectInfo selectPro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.building.sellProject.id", selectPro.getId().toString()));
			} 
			else if (node.getUserObject() instanceof SubareaInfo) 
			{
				SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.sellProject.id", subInfo.getSellProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.building.subarea.id", subInfo.getId().toString()));
			} 
			else if(node.getUserObject() instanceof OrgStructureInfo)
			{
				OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.sellProject.id", CommerceHelper.getPermitProjectIdSql(getUserInfo()), CompareType.INNER));
				filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.sellProject.orgUnit.id", org.getId().toString()));
			}
		}

		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("fdcReceiveBill.billTypeEnum");
		selColl.add("fdcReceiveBill.settleMentType");
		selColl.add("fdcReceiveBill.purchase.isEarnestInHouseAmount");   //定金是否 隶属于房款（对合同款合计有影响）
		selColl.add("fdcReceiveBill.room.id");
		selColl.add("fdcReceiveBill.room.name");
		selColl.add("fdcReceiveBill.room.number");
		
		selColl.add("fdcReceiveBill.room.buildUnit.name");
		selColl.add("fdcReceiveBill.room.building.name");
		selColl.add("fdcReceiveBill.room.building.subArea.name");
		selColl.add("fdcReceiveBill.room.building.sellProject.name");
		
		selColl.add("fdcReceiveBillEntry.moneyDefine.id");
		selColl.add("fdcReceiveBillEntry.moneyDefine.name");
		selColl.add("fdcReceiveBillEntry.moneyDefine.moneyType");
		
		selColl.add("fdcReceiveBill.customerEntrys.customer.id");
		selColl.add("fdcReceiveBill.customerEntrys.customer.name");
		selColl.add("fdcReceiveBillEntry.amount");
		
		/**
		 * 添加结算方式、收款银行账号、收款银行账户
		 */
		selColl.add("fdcReceiveBillEntry.settlementType.id");
		selColl.add("fdcReceiveBillEntry.settlementType.number");
		selColl.add("fdcReceiveBillEntry.settlementType.name");
		
		selColl.add("fdcReceiveBillEntry.revAccountBank.id");
		selColl.add("fdcReceiveBillEntry.revAccountBank.bankAccountNumber");
		
		//名称里面存的收款银行账户
		selColl.add("fdcReceiveBillEntry.revAccountBank.name");
	
		view.setSelector(selColl);		
		
		/**
		 * 从过滤界面获得日期加到 bizDate 的过滤上,注意下时分秒的处理
		 */
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date BeginQueryDate = filterMap.get("BeginQueryDate")==null?null:new Date(((Timestamp)filterMap.get("BeginQueryDate")).getTime());
		Date EndQueryDate = filterMap.get("EndQueryDate")==null?null:new Date(((Timestamp)filterMap.get("EndQueryDate")).getTime());
		if(BeginQueryDate!=null)
			filter.getFilterItems().add(new FilterItemInfo("bizDate", BeginQueryDate, CompareType.GREATER_EQUALS));
		if(EndQueryDate!=null)
			filter.getFilterItems().add(new FilterItemInfo("bizDate", EndQueryDate, CompareType.LESS));
		
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill", null, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.moneySysType", MoneySysTypeEnum.SalehouseSys));

		ReceivingBillCollection billColl = null;
		billColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(view);
		
		/**
		 * 整理这些收款单数据
		 * 结构为 Map<CustomerInfo, Map<RoomInfo, Set<ReceivingBillInfo>>>
		 */
		Map map = new HashMap();

		for (int i = 0; i < billColl.size(); i++) 
		{
			ReceivingBillInfo info = billColl.get(i);
			FDCReceiveBillInfo fdc = info.getFdcReceiveBill();

			RoomInfo room = fdc.getRoom();
			
			//支持诚意金收款后，该房间可能为空
			if(room == null)
				continue;
			
			//构造Map<CustomerInfo, Map<RoomInfo, Set<ReceivingBillInfo>>>
			CustomerEntryCollection customerColl = fdc.getCustomerEntrys();
			for (int j = 0; j < customerColl.size(); j++) 
			{
				CustomerInfo customer = customerColl.get(j).getCustomer();
				if(customer==null) 
					continue;
				
				if (map.get(customer) == null) 
				{
					Map roomMap = new HashMap();
					Set revSet = new HashSet();
					revSet.add(info); 
					
					roomMap.put(room, revSet);
					map.put(customer, roomMap);
				} 
				else 
				{
					Map roomMap = (Map) map.get(customer);
					if(roomMap.get(room) == null)
					{
						Set revSet = new HashSet();
						revSet.add(info);    
						roomMap.put(room, revSet);
					}
					else
					{
						Set revSet = (Set) roomMap.get(room);						
						revSet.add(info);
					}
				}
			}
		}

		Set keySet = map.keySet();
		int temp = 0;
		int rowCounter = 0;
		for (Iterator itor = keySet.iterator(); itor.hasNext();) 
		{
			CustomerInfo customer = (CustomerInfo) itor.next();
			Map roomMap = (Map) map.get(customer);
			Set roomSet = roomMap.keySet();
			
			IRow row = null;
			
			
			for (Iterator tempIt = roomSet.iterator(); tempIt.hasNext();) 
			{
				RoomInfo room = (RoomInfo) tempIt.next();
				
				row = tblMain.addRow();
				row.setTreeLevel(1);
				row.getStyleAttributes().setBackground(aftTax);
				
				row.getCell("customer").setValue(customer==null?"":customer.getName());
				row.getCell("room").setValue(room.getNumber());
				if (room.getBuilding() != null && room.getBuilding().getSellProject() != null)
					row.getCell("sellProject").setValue(room.getBuilding().getSellProject().getName());
				if (room.getBuilding() != null)
					row.getCell("building").setValue(room.getBuilding().getName());
				row.getCell("unit").setValue(room.getBuildUnit() == null ? null : room.getBuildUnit().getName());
				
				Set revs = (Set) roomMap.get(room);
				BigDecimal totalAmount = FDCHelper.ZERO;
				Iterator revIt = revs.iterator();
				
				int mergeRow = 0;
				
				while(revIt.hasNext())
				{
					ReceivingBillInfo rev = (ReceivingBillInfo) revIt.next();
					FDCReceiveBillInfo fdcRev = rev.getFdcReceiveBill();
					ReceiveBillTypeEnum type = fdcRev.getBillTypeEnum();
					boolean isSettlement = ReceiveBillTypeEnum.settlement.equals(type);
					SettleMentTypeEnum settleType = fdcRev.getSettleMentType();
					FDCReceiveBillEntryCollection fdcRevEntrys = rev.getFdcReceiveBillEntry();
					
					for(int index = 0; index < fdcRevEntrys.size(); index++)
					{
						FDCReceiveBillEntryInfo fdcRevEntry = fdcRevEntrys.get(index);
						MoneyDefineInfo moneyName = fdcRevEntry.getMoneyDefine();
						SettlementTypeInfo settlementType = fdcRevEntry.getSettlementType();
						BigDecimal amount = fdcRevEntry.getAmount();
						
						totalAmount = FDCHelper.add(totalAmount, amount);
						
						if(moneyName != null)
						{
							String moneyNameID = moneyName.getId().toString();
							if(row.getCell(moneyNameID) != null)
							{
								BigDecimal srcValue = FDCHelper.toBigDecimal(row.getCell(moneyNameID).getValue());
								row.getCell(moneyNameID).setValue(FDCHelper.add(srcValue, amount));
							}
							
							String cellKeyName = (String)moneyDefineMap.get(moneyNameID);
							if(cellKeyName!=null) {
								boolean addToCell = true;  //是否要加入到统计列    （如果是合同款合计列，定金类的是否累计进去要参照对应的认购单属性）								
								if(cellKeyName!=null && KEY_PLAN_TOTAL.equals(cellKeyName) && MoneyTypeEnum.EarnestMoney.equals(moneyName.getMoneyType())) {
									if(fdcRev.getPurchase()==null || !fdcRev.getPurchase().isIsEarnestInHouseAmount()) {
										addToCell = false;
									}
								}
								
								ICell tmpTotalCell = row.getCell(cellKeyName);
								if(tmpTotalCell != null){
									BigDecimal srcToValue = (BigDecimal) tmpTotalCell.getValue();
									if(srcToValue == null) srcToValue = FDCHelper.ZERO;
									
									if(addToCell)
										tmpTotalCell.setValue(srcToValue.add(amount));
								}
							}
							
							
							/**
							 * 如果是定金的结转，汇总到定金结转列
							 * 目前是通过红冲类型和款项类型来判断的。收款单改造后这里需要修改
							 */
							if(isSettlement  &&  MoneyTypeEnum.EarnestMoney.equals(moneyName.getMoneyType()) && settleType!=null && settleType.equals(SettleMentTypeEnum.prePurSettle)){
								if(row.getCell(KEY_EARN_A) != null)
								{
									BigDecimal srcToValue = FDCHelper.toBigDecimal(row.getCell(KEY_EARN_A).getValue());
									row.getCell(KEY_EARN_A).setValue(FDCHelper.add(srcToValue, amount.abs()));
								}
							}
						}
						
						//填充结算方式汇总行
						if(settlementType != null)
						{
							if(row.getCell(BANK_AMT) != null)
							{
								BigDecimal srcValue = FDCHelper.toBigDecimal(row.getCell(BANK_AMT).getValue());
								row.getCell(BANK_AMT).setValue(FDCHelper.add(srcValue, amount));
							}
						}
						
						/**
						 * 填充结算方式对应的明细内容
						 */
						if(settlementType != null)
						{
							IRow detailRow = tblMain.addRow();
							detailRow.setTreeLevel(2);
							
							detailRow.getCell("customer").setValue(customer==null?"":customer.getName());
							detailRow.getCell("room").setValue(room.getNumber());
							if (room.getBuilding() != null && room.getBuilding().getSellProject() != null)
								detailRow.getCell("sellProject").setValue(room.getBuilding().getSellProject().getName());
							if (room.getBuilding() != null)
								detailRow.getCell("building").setValue(room.getBuilding().getName());
							detailRow.getCell("unit").setValue(room.getBuildUnit() == null ? null : room.getBuildUnit().getName());
							
							if(detailRow.getCell(SETTLE_TYPE) != null)
							{
								detailRow.getCell(SETTLE_TYPE).setValue(settlementType.getName());
							}
							
							if(detailRow.getCell(BANK_AMT) != null)
							{
								detailRow.getCell(BANK_AMT).setValue(amount);
							}
							if(detailRow.getCell(BANK_ACCOUNT) != null && fdcRevEntry.getRevAccountBank() != null)
							{
								detailRow.getCell(BANK_ACCOUNT).setValue(fdcRevEntry.getRevAccountBank().getName());
							}
							if(detailRow.getCell(BANK_ACCT_NUM) != null && fdcRevEntry.getRevAccountBank() != null)
							{
								detailRow.getCell(BANK_ACCT_NUM).setValue(fdcRevEntry.getRevAccountBank().getBankAccountNumber());
							}
							
							mergeRow++;
						}
					}
				}
				
				if(row.getCell(KEY_ALL_TOTAL) != null)row.getCell(KEY_ALL_TOTAL).setValue(totalAmount);
				if(row.getCell(KEY_SHOUKUAN_TOTAL) != null)row.getCell(KEY_SHOUKUAN_TOTAL).setValue(totalAmount);
				
				rowCounter = rowCounter + 1 + mergeRow;
				//rowCounter++;
				
			}
			
			this.tblMain.getMergeManager().mergeBlock(temp, 0, rowCounter-1, 5, KDTMergeManager.FREE_MERGE);
			temp = rowCounter;
		}
			

		//自动隐藏没有使用到的款项名称。
		int beginIndex = this.tblMain.getColumnIndex("room");
		int endIndex = this.tblMain.getColumnIndex(KEY_ALL_TOTAL);
		if(beginIndex>0 && endIndex>0) 
		{
			beginIndex++;
			if(endIndex>=beginIndex)
			{
				for(int i = beginIndex; i < endIndex; i++) 
				{
					boolean isUsed = false;
					for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)
					{
						if(this.tblMain.getCell(rowIndex, i).getValue()!=null)
						{
							isUsed = true;
							break;
						}
					}
					
					tblMain.getColumn(i).getStyleAttributes().setHided(!isUsed);
				}
			}
		}		
		
		/**
		 * 增加总计行
		 */
		this.tblMain.getGroupManager().setTotalize(true);
		IRow totalRow = this.tblMain.getGroupManager().getStatRowTemplate(-1);
		totalRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
		totalRow.getCell(0).setValue("总计：");
		for(int i=6;i<this.tblMain.getColumnCount();i++)
		{
			if(!(tblMain.getColumnKey(i).equals(SETTLE_TYPE) || tblMain.getColumnKey(i).equals(BANK_ACCOUNT) || tblMain.getColumnKey(i).equals(BANK_ACCT_NUM)))
			{
				BigDecimal tmpSum = FDCHelper.ZERO;
				for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)
				{
					if(tblMain.getRow(rowIndex).getTreeLevel() == 1)
					{
						tmpSum = FDCHelper.add(tblMain.getCell(rowIndex, i).getValue(), tmpSum);
					}
				}
				totalRow.getCell(i).setValue(tmpSum);
			}
		}
		
		this.tblMain.getGroupManager().group();		
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.execQuery();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{

	}

}