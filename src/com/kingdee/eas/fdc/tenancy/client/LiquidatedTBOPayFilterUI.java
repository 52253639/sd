/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class LiquidatedTBOPayFilterUI extends AbstractLiquidatedTBOPayFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedTBOPayFilterUI.class);
    
    protected ItemAction actionListOnLoad;
	private boolean isLoaded;
	private String Areacomment;
	protected ListUI listUI;
	String[] customerIds=null;
	String[] customerNames =null;
	String[] moneyDefineIds =null;
	String[] moneyDefineNames=null;

    public LiquidatedTBOPayFilterUI() throws Exception
    {
        super();
    }
    public LiquidatedTBOPayFilterUI(ListUI listUI, ItemAction actionListOnLoad)
	throws Exception {
    	super();
    	this.listUI = listUI;
    	this.actionListOnLoad = actionListOnLoad;
}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
//    	this.sellProject.setRequired(true);

		if (!isLoaded ) {
			init();
			setDefaultValue();
		}
		isLoaded = true;
		this.contComment.setVisible(false);
	}
  
    protected void init() throws EASBizException, BOSException {
    	initPrmtSellProject(sellProject);
    		initPrmtCustomer(customer);
    		initPrmtContract(contract);
    		initPrmtMoneyDefine(moneyDefine);
    		initFromDate(dateFrom);
    		initIsAudit();
    		initToDate(dateTo);
    }
    
    /**
     * 初始化销售项目
     * @param box
     * @throws EASBizException
     * @throws BOSException
     */
    private void initPrmtSellProject(KDBizPromptBox box) throws EASBizException, BOSException{
    	box.setEditable(false);
      	box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
//		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(SysContext.getSysContext().getCurrentSaleUnit()!=null){
			String cuId = SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", cuId));
		}
//		filter.getFilterItems().add(new FilterItemInfo("id",CommerceHelper.getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("isForTen", new Integer(1)));
		view.setFilter(filter);
		box.setEntityViewInfo(view);
	}
    
    /**
     * 初始化客户
     * @param box
     */
    private void initPrmtCustomer(KDBizPromptBox box) throws EASBizException, BOSException{
    	box.setEditable(false);
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerAllQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		box.setEnabledMultiSelection(true);
		box.setEntityViewInfo(CommerceHelper.getPermitCustomerView((SellProjectInfo) this.sellProject.getValue(),SysContext.getSysContext().getCurrentUserInfo()));
	}
   
    /**
     * 初始化租赁合同
     * @param box
     * @throws EASBizException
     * @throws BOSException
     */
    private void initPrmtContract(KDBizPromptBox box){
    	box.setEditable(false);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillF7Query");
		box.setDisplayFormat("$tenancyName$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		Object v = this.sellProject.getValue();
		if (v instanceof SellProjectInfo) {
			SellProjectInfo sellProjectInfo = (SellProjectInfo) v;
			EntityViewInfo evi = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("tenancyName");
			sic.add("tenancyState");
			sic.add("sellProject.id");
			box.setSelectorCollection(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectInfo.getId()));
			Set auditState = new HashSet();
			auditState.add("Executing");
			auditState.add("Audited");
			auditState.add("PartExecuted");
			auditState.add("ContinueTenancying");
			auditState.add("RejiggerTenancying");
			auditState.add("ChangeNaming");
			auditState.add("TenancyChanging");
			auditState.add("QuitTenancying");
			auditState.add("Expiration");
			if(!isAudit.isSelected()){
				filter.getFilterItems().add(new FilterItemInfo("tenancyState",auditState,CompareType.INCLUDE));
			}else{
				auditState.add("Auditing");
				auditState.add("Submitted");
				filter.getFilterItems().add(new FilterItemInfo("tenancyState",auditState,CompareType.INCLUDE));
				
			}
			
			evi.setFilter(filter);
			box.setEntityViewInfo(evi);

		}
    }
   
    
    /**
     * 初始化款项类型
     * @param box
     * @throws EASBizException
     * @throws BOSException
     */
    private void initPrmtMoneyDefine(KDBizPromptBox box) throws EASBizException, BOSException{
    	box.setEditable(false);
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("sysType");
		box.setSelectorCollection(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
		view.setFilter(filter);
		box.setEntityViewInfo(view);
//		//可以多选
		box.setEnabledMultiSelection(true);
	}
    
    
    private void initIsAudit(){
    	this.isAudit.setSelected(false);
    }
  
    
    /**
     * 初始化起始日期、结束日期
     * @param box
     * @throws EASBizException
     * @throws BOSException
     */
    private void initFromDate(KDDatePicker box) throws EASBizException, BOSException{
    	Date date= FDCDateHelper.getFirstDayOfCurMonth();
    	box.setValue(date);
    	
	}
    private void initToDate(KDDatePicker box) throws EASBizException, BOSException{
    	Date date= FDCDateHelper.getEndDayOfCurMonth();
    	box.setValue(date);
    	
	}
    /**
     * 初始化时set默认值
     */
    protected void setDefaultValue() {
    	
	}
    
    /**
     * 项目控件时间，来限制合同的选择范围
     */
    protected void sellProject_dataChanged(DataChangeEvent e) throws Exception {
    	if(e.getNewValue()!=null){
    		this.contract.setValue(null);
			initPrmtContract(contract);
		
    	}
    	
    	
    }
    
       
    protected void isAudit_mouseClicked(MouseEvent e) throws Exception {
    	if(this.sellProject.getValue()!=null){
//    		this.contract.setValue(null);
			initPrmtContract(contract);
    	}
    }
  
    public boolean verify() {
//		if(sellProject.getValue() == null){
//			MsgBox.showInfo("项目不能为空");
//			sellProject.requestFocus();
//			return false;
//		}
		return true;
	}
    
  
   public CustomerParams getCustomerParams() {
	 
	   FDCCustomerParams param = new FDCCustomerParams();
	    //项目
	   if(this.sellProject.getValue()!=null){
			param.add("sellProject",((SellProjectInfo)this.sellProject.getValue()).getId().toString());
		}
	   //客户的id，name
	   if(customer.getValue()!=null){
		   if(((Object[])customer.getValue()).length>0&&((Object[])customer.getValue())[0]!=null){
	   		Object[] v=(Object[])customer.getValue();
	   		 customerIds =new String[v.length];
	   		 customerNames =new String[v.length];
	   		for(int i=0;i<v.length;i++){
	   			FDCCustomerInfo ci=(FDCCustomerInfo)v[i];
	   			String cid=ci.getId().toString();
	   			String cname=ci.getName();
	   			customerIds[i]=cid;
	   			customerNames[i]=cname;
	   		}
	   		param.add("customer.id",customerIds);
	   		param.add("customer.name",customerNames);
		   }
	   }
	   
	   //合同
	   if(this.contract.getValue()!=null){
			param.add("contractname",((TenancyBillInfo)contract.getValue()).getTenancyName().toString());
			param.add("contractid", ((TenancyBillInfo)contract.getValue()).getId().toString());
		}
	   //款项类型
	   if(moneyDefine.getValue()!=null){
		   if(((Object[])moneyDefine.getValue())[0]!=null){
	   		Object[] s=(Object[])moneyDefine.getValue();
	   		 moneyDefineIds = new String[s.length];
	   		 moneyDefineNames = new String[s.length];
	   		for(int i=0;i<s.length;i++){
	   			MoneyDefineInfo mi=	(MoneyDefineInfo)s[i];
	   			moneyDefineIds[i] =mi.getId().toString();
	   			moneyDefineNames[i] = mi.getName();   			
	   			
	   		}
	   		param.add("moneyDefineIds",moneyDefineIds);
	   		param.add("moneyDefineNames",moneyDefineNames);
		   }
	 }
   		
	   //应付起始日期
	   param.add("dateFrom", (Date)this.dateFrom.getValue());
	   //应付截止日期
	   param.add("dateTo", (Date)this.dateTo.getValue());
	  
	   //是否
	   if(isAudit.isSelected()){
		   param.add("isAudit", isAudit.isSelected());
	   } 
	   return param.getCustomerParams();
   }
   public void setCustomerParams(CustomerParams cp) {
	  
	   if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp); 
		//项目
		if(para.getString("sellProject")!=null){
				try {
					this.sellProject.setValue(SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(para.getString("sellProject"))));
				} catch (Exception e) {
					this.handUIException(e);
				}
			}
		
//		//合同name
//		if(para.isNotNull("contractname")){
//			try {
//				this.contract.setText(para.get("contractname"));
//			} catch (Exception e){
//				this.handUIException(e);
//			}
//		}
		//合同ID
		if(para.isNotNull("contractid")){
			try {
				
				this.contract.setValue(TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(para.getString("contractid"))));
			} catch (Exception e){
				this.handUIException(e);
			}
		}
		

		//客户名称
			if (para.isNotNull("customer.name")) {
				StringBuffer sbuffer = new StringBuffer();
				String[] costArray = para.getStringArray("customer.name");
				for (int i = 0; i < costArray.length; i++) {
					if (i > 0) {
						sbuffer.append(";");
					}
					sbuffer.append(costArray[i]);
				}
				this.customer.setText(sbuffer.toString());
				
				
			}
		//客户ID
		if (para.isNotNull("customer.id")) {
			
			String[] costArray = para.getStringArray("customer.id");
			Object[] oa = new Object[costArray.length];
			try {
				for (int i = 0; i < costArray.length; i++) {
					
						oa[i]=FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(costArray[i]));
					
				}
			} catch (EASBizException e) {
				logger.error(e);
			} catch (BOSException e) {
				logger.error(e);
			}
			this.customer.setValue(oa);
		}
		
		//款项类型
			if (para.isNotNull("moneyDefineNames")) {
				StringBuffer sbuffer = new StringBuffer();
				String[] costArray = para.getStringArray("moneyDefineNames");
				for (int i = 0; i < costArray.length; i++) {
					if (i > 0) {
						sbuffer.append(";");
					}
					sbuffer.append(costArray[i]);
				}
				this.moneyDefine.setText(sbuffer.toString());
			}
		//款项类型ID
			if (para.isNotNull("moneyDefineIds")) {
				
				String[] costArray = para.getStringArray("moneyDefineIds");
				Object[] oa = new Object[costArray.length];
				try {
					for (int i = 0; i < costArray.length; i++) {
							oa[i]=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(costArray[i]));
					}
				} catch (EASBizException e) {
					logger.error(e);
				} catch (BOSException e) {
					logger.error(e);
				}
				this.moneyDefine.setValue(oa);
			}
		//起始截止日期
			this.dateFrom.setValue(para.getDate("dateFrom"));
			this.dateTo.setValue(para.getDate("dateTo"));
		//是否
			this.isAudit.setSelected(para.getBoolean("isAudit"));
			super.setCustomerParams(para.getCustomerParams());
   }
   public FilterInfo getFilterInfo() {
	   FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	   FilterInfo filter = new FilterInfo();
	   //项目
	   try {
		   if (para.isNotNull("sellProject")) {
			
//				filter.getFilterItems().add(new FilterItemInfo("sellProject.name", SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(para.getString("sellProject"))).getName()));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", para.getString("sellProject"), CompareType.EQUALS));
				
		   } else {//如果没有选择工程项目，给出提示
//			   filter.getFilterItems().add(new FilterItemInfo("sellProject.id", CommerceHelper.getPermitProjectIds(),CompareType.INNER));
			
			   MsgBox.showError(this, "请选择工程项目");
		   }
		   } catch (Exception e) {
			this.handUIException(e);
		   }
	   //客户ID
	   if (para.isNotNull("customer.id")) {
			filter.getFilterItems().add(new FilterItemInfo("customer.id", FDCHelper.getSetByArray(para.getStringArray("customer.id")), CompareType.INCLUDE));
		}
	   //合同name
	   if (para.isNotNull("contractid")) {
		   filter.getFilterItems().add(new FilterItemInfo("contractid", para.get("contractid"), CompareType.EQUALS));
		}
	 //合同name
	   if (para.isNotNull("contractname")) {
			filter.getFilterItems().add(new FilterItemInfo("contract", para.get("contractname"), CompareType.GREATER_EQUALS));

		   
		}
	   //款项类型
	   if (para.isNotNull("moneyDefineIds")) {
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine", FDCHelper.getSetByArray(para.getStringArray("moneyDefineIds")), CompareType.INCLUDE));
		}
	   //起始截止日期
	   if (para.isNotNull("dateFrom")) {
			filter.getFilterItems().add(new FilterItemInfo("dateFrom", para.getDate("dateFrom"), CompareType.GREATER_EQUALS));
		}
	   if (para.isNotNull("dateTo")) {
			filter.getFilterItems().add(new FilterItemInfo("dateTo", para.getDate("dateTo"), CompareType.GREATER_EQUALS));
		}
	   //是否
	   if(para.isNotNull("isAudit")){
		   filter.getFilterItems().add(new FilterItemInfo("isAudit", Boolean.valueOf(para.getBoolean("isAudit"))));
	   }
	   return filter;	
   }
   public void clear() {
//	   try {
//		   this.sellProject.setValue(null);
//		   this.contract.setValue(null);
//		   this.customer.setValue(null);
//		   this.moneyDefine.setValue(null);
//		   init();
//	} catch (Exception e) {
//		this.handUIException(e);
//	}
   }

}