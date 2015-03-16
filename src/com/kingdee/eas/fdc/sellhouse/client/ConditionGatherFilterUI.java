/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ConditionGatherFilterUI extends AbstractConditionGatherFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ConditionGatherFilterUI.class);
    CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit(); 
    CtrlUnitInfo cuID = SysContext.getSysContext().getCurrentCtrlUnit();
    protected ItemAction actionListOnLoad;
	protected ListUI listUI;
	protected Map uicontext ;
	//收款单据类型
	private static final String REV_TYPE = "revType";
	//项目
	private static final String SELLPROJECT = "sellProject";
	//楼栋
	private static final String BUILDING = "building";
	//单元
	private static final String UNIT = "unit";
	//款项类别
	private static final String MONEYTYPE = "moneyType";
	//款项名称
	private static final String MONEYDEFINE = "moneyDefine";
	//结算方式
	private static final String SETTLEMENT = "settlement";
	//入账银行账户
	private static final String ACCOUNTBANK = "accountBank";
	//入账银行账号
	private static final String BANKNUMBER = "bankNumber";
	//业务开始日期
	private static final String BIZSTARTDATE = "bizStartDate";
	//业务结束日期
	private static final String BIZENDDATE = "bizEndDate";
	//汇总类型
	private static final String GATHERTYPE = "gatherType";
	
    public ConditionGatherFilterUI() throws Exception
    {
        super();
    }

    public ConditionGatherFilterUI(ListUI listUI, ItemAction actionListOnLoad)
			throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}  
    
    public ConditionGatherFilterUI(ListUI listUI, ItemAction actionListOnLoad,Map uicontext) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
		this.uicontext = uicontext;
	}  


    public void onLoad() throws Exception {
    	super.onLoad();
    	this.contBuilding.setVisible(false);
    	this.contUnit.setVisible(false);
    	this.comRevBillType.removeItem(RevBillTypeEnum.adjust);	//已没有调整类型 
    	this.contGatherType.setVisible(false);
    	initF7AccountBankFilter();
    	SellProjectInfo project = (SellProjectInfo)this.getUIContext().get("sellProject");
    	this.F7SellProject.setValue(project);
    	this.F7SellProject.setEnabled(false);
    	initF7SellProjectFilter(project);
    	F7MoneyDefineFilter();
    	this.setCustomerParams((CustomerParams)getUIContext().get("params"));
    }  
    
    protected void comRevBillType_itemStateChanged(ItemEvent e) throws Exception {
    	RevBillTypeEnum revBillType = (RevBillTypeEnum)this.comRevBillType.getSelectedItem();
    	if(revBillType.getValue().equals(RevBillTypeEnum.TRANSFER_VALUE))
    	{
    		this.contGatherType.setVisible(true);
    	}else
    	{
    		this.contGatherType.setVisible(false);
    	}
    }
    
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	ReceiveGatherFilterListUI parent = (ReceiveGatherFilterListUI) getUIContext().get("owner");   	
    	parent.updateTable(getCustomerParams());
    	this.uiWindow.close();
    }
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIWindow().close();
    }
    
    public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		
		SellProjectInfo project = (SellProjectInfo)this.getUIContext().get("sellProject");
		param.add(SELLPROJECT, project.getId().toString());
		
		BuildingInfo building = (BuildingInfo)this.F7Building.getValue();
		param.add(BUILDING, building==null?null:building.getId().toString());
		BuildingUnitInfo unit = (BuildingUnitInfo)this.F7Unit.getValue();
		param.add(UNIT, unit==null?null:unit.getId().toString());
		MoneyDefineInfo moneyDefine = (MoneyDefineInfo)this.F7MoneyDefine.getValue();
		param.add(MONEYDEFINE, moneyDefine==null?null:moneyDefine.getId().toString());
		SettlementTypeInfo sett = (SettlementTypeInfo)this.F7SettlementType.getValue();
		param.add(SETTLEMENT, sett==null?null:sett.getId().toString());
		AccountBankInfo accountBank = (AccountBankInfo)this.F7AccountBank.getValue();
		param.add(ACCOUNTBANK, accountBank==null?null:accountBank.getId().toString());
		
		param.add(BANKNUMBER, (String)this.txtBankNumber.getText());
		
		param.add(BIZSTARTDATE, (Date)this.pkBizStartDate.getValue());
		param.add(BIZENDDATE, (Date)this.pkBizEndDate.getValue());
		
		RevBillTypeEnum revBillType = (RevBillTypeEnum)this.comRevBillType.getSelectedItem();
		param.add(REV_TYPE, revBillType.getValue());
		
		if(RevBillTypeEnum.gathering.equals(revBillType))
		{
			param.add(GATHERTYPE, GatherTypeEnum.RECEIVEGATHER_VALUE);
		}else if(RevBillTypeEnum.refundment.equals(revBillType))
		{
			param.add(GATHERTYPE, GatherTypeEnum.REFUMENTGATHER_VALUE);
		}else if(RevBillTypeEnum.transfer.equals(revBillType))
		{
			GatherTypeEnum recGather = (GatherTypeEnum)this.comGatherType.getSelectedItem();
			param.add(GATHERTYPE, recGather.getValue());
		}		
		return param.getCustomerParams();
    }
    
    public void setCustomerParams(CustomerParams cusPar) {
		if (cusPar == null)
			return;
		clear();
		
		FDCCustomerParams para = new FDCCustomerParams(cusPar);
		this.pkBizStartDate.setValue(para.getDate(BIZSTARTDATE));
		this.pkBizEndDate.setValue(para.getDate(BIZENDDATE));
		this.txtBankNumber.setText(para.getString(BANKNUMBER));
		
		this.F7SellProject.setValue((SellProjectInfo)this.getUIContext().get("sellProject"));
		String buildingID = para.get(BUILDING);	
		String unitID = para.get(UNIT);
		String moneyDefineID = para.get(MONEYDEFINE);
		String settlementID = para.get(SETTLEMENT);
		String accountBankID = para.get(ACCOUNTBANK);
		try {
			if(!"".equals(buildingID) && buildingID!=null)
			{
				BuildingInfo build = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(BOSUuid.read(buildingID)));
				if(build!=null) this.F7Building.setValue(build);				
			}
			if(!"".equals(unitID) && unitID!=null)
			{
				BuildingUnitInfo unit = BuildingUnitFactory.getRemoteInstance().getBuildingUnitInfo(new ObjectUuidPK(BOSUuid.read(unitID)));
				if(unit!=null) this.F7Unit.setValue(unit);
			}
			if(!"".equals(moneyDefineID) && moneyDefineID!=null)
			{
				MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(BOSUuid.read(moneyDefineID)));
				if(moneyInfo!=null) this.F7MoneyDefine.setValue(moneyInfo);
			}
			if(!"".equals(settlementID) && settlementID!=null)
			{
				SettlementTypeInfo settlement = SettlementTypeFactory.getRemoteInstance().getSettlementTypeInfo(new ObjectUuidPK(BOSUuid.read(settlementID)));
				if(settlement!=null) this.F7SettlementType.setValue(settlement);
			}
			if(!"".equals(accountBankID) && accountBankID!=null)
			{
				AccountBankInfo accBank = AccountBankFactory.getRemoteInstance().getAccountBankInfo(new ObjectUuidPK(BOSUuid.read(accountBankID)));
				if(accBank!=null) this.F7AccountBank.setValue(accBank);
			}						
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}
			
		String revBillType = para.get(REV_TYPE);
		if(revBillType.equals(RevBillTypeEnum.GATHERING_VALUE))
		{
			this.comRevBillType.setSelectedItem(RevBillTypeEnum.gathering);
			this.comGatherType.setSelectedItem(GatherTypeEnum.ReceiveGather);
		}else if(revBillType.equals(RevBillTypeEnum.REFUNDMENT_VALUE))
		{
			this.comRevBillType.setSelectedItem(RevBillTypeEnum.refundment);
			this.comGatherType.setSelectedItem(GatherTypeEnum.RefumentGather);
		}else if(revBillType.equals(RevBillTypeEnum.TRANSFER_VALUE))
		{
			this.comRevBillType.setSelectedItem(RevBillTypeEnum.transfer);
			String gatherType = para.get(GATHERTYPE);
			if(GatherTypeEnum.BILLGATHER_VALUE.equals(gatherType))
			{
				this.comGatherType.setSelectedItem(GatherTypeEnum.BillGather);
			}else if(GatherTypeEnum.RECEIVEGATHER_VALUE.equals(gatherType))
			{
				this.comGatherType.setSelectedItem(GatherTypeEnum.ReceiveGather);
			}else if(GatherTypeEnum.RefumentGather.equals(gatherType))
			{
				this.comGatherType.setSelectedItem(GatherTypeEnum.RefumentGather);
			}
		}
		
		super.setCustomerParams(para.getCustomerParams());
    }
    
    public void clear() {
    	this.F7Building.setValue(null);
    	this.F7Unit.setValue(null);
    	this.F7MoneyDefine.setValue(null);
    	this.F7SettlementType.setValue(null);
    	this.F7AccountBank.setValue(null);
    	this.txtBankNumber.setText(null);
    	this.pkBizStartDate.setValue(null);
    	this.pkBizEndDate.setValue(null);
    }
    
	public void storeFields()
    {
        super.storeFields();
    }
	
	  /*
     * 银行账户根据财务组织来进行过滤
     */
    private void initF7AccountBankFilter() throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
		viewInfo.setFilter(filter);
		F7AccountBank.setEntityViewInfo(viewInfo);		
	}
    
    /*
     * 楼栋根据项目来进行过滤
     */
    private void initF7SellProjectFilter(SellProjectInfo project) throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", project.getId().toString()));
		viewInfo.setFilter(filter);
		F7Building.setEntityViewInfo(viewInfo);					
	}
    
    protected void F7Building_dataChanged(DataChangeEvent e) throws Exception {
    	if(this.F7Building.getValue()!=null)
		{
			BuildingInfo build = (BuildingInfo)this.F7Building.getValue();
			EntityViewInfo viewInfo1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("building.id", build.getId().toString()));
			viewInfo1.setFilter(filter1);
			F7Unit.setEntityViewInfo(viewInfo1);	
		}
    }
    
    protected void F7MoneyDefineFilter()
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE,CompareType.EQUALS));
//		filter.getFilterItems().add(
//				new FilterItemInfo("CU.id",cuID,CompareType.EQUALS));		
		view.setFilter(filter);
		F7MoneyDefine.setEntityViewInfo(view);
    }

    protected void comRevBillType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.comRevBillType_actionPerformed(e);
    }

    protected void comGatherType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.comGatherType_actionPerformed(e);
    }

}