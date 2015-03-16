/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 项目各阶段目标成本测算参数设置界面
 */
public class MeasureStageCostFilterUI extends AbstractMeasureStageCostFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureStageCostFilterUI.class);
    
    /**
     * 单选，科目长编码即可
     * 开发成本
     */
    private static String DEVACCT="devAcct";
    /**
     * 开发间接费用
     */
    private static String INDIRECTACCT="indirectAcct";
    /**
     * 管理费用
     */
    private static String ADMINACCT="adminAcct";
    /**
     * 土地费用
     */
    private static String LANDACCT="landAcct";
    /**
     * 销售费用
     */
    private static String SALEACCT="saleAcct";
    
    /**
     * output class constructor
     */
    public MeasureStageCostFilterUI() throws Exception
    {
        super();
    }
    public void clear() {
		initDataStatus();
	}
   
    private void initDataStatus(){
		
	}
    
    /**
	 * 方案filter
	 */
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());


		FilterInfo filter = new FilterInfo();

		if (para.isNotNull(DEVACCT)) {
			filter.getFilterItems().add(new FilterItemInfo(DEVACCT, para.get(DEVACCT)));
		} 
		if (para.isNotNull(INDIRECTACCT)) {
			filter.getFilterItems().add(new FilterItemInfo(INDIRECTACCT, para.get(INDIRECTACCT)));
		} 
		if (para.isNotNull(ADMINACCT)) {
			filter.getFilterItems().add(new FilterItemInfo(ADMINACCT, para.get(ADMINACCT)));
		} 
		if (para.isNotNull(LANDACCT)) {
			filter.getFilterItems().add(new FilterItemInfo(LANDACCT, para.get(LANDACCT)));
		} 
		if (para.isNotNull(SALEACCT)) {
			filter.getFilterItems().add(new FilterItemInfo(SALEACCT, para.get(SALEACCT)));
		} 
		
		return filter;
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		if(prmtDevAcct.getValue() instanceof CostAccountInfo && prmtDevAcct.getValue()!=null){
			param.add(DEVACCT,((CostAccountInfo)prmtDevAcct.getValue()).getLongNumber());
		}
		if(prmtIndirectAcct.getValue() instanceof CostAccountInfo && prmtIndirectAcct.getValue()!=null){
			param.add(INDIRECTACCT,((CostAccountInfo)prmtIndirectAcct.getValue()).getLongNumber());
		}
		if(prmtAdminAcct.getValue() instanceof CostAccountInfo && prmtAdminAcct.getValue()!=null){
			param.add(ADMINACCT,((CostAccountInfo)prmtAdminAcct.getValue()).getLongNumber());
		}
		if(prmtLandAcct.getValue() instanceof CostAccountInfo && prmtLandAcct.getValue()!=null){
			param.add(LANDACCT,((CostAccountInfo)prmtLandAcct.getValue()).getLongNumber());
		}
		if(prmtSaleAcct.getValue() instanceof CostAccountInfo && prmtSaleAcct.getValue()!=null){
			param.add(SALEACCT,((CostAccountInfo)prmtSaleAcct.getValue()).getLongNumber());
		}
		return param.getCustomerParams();
	}
	
	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if(para.get(DEVACCT)!=null){
			prmtDevAcct.setValue(acctMap.get(para.get(DEVACCT)));
		}
		if(para.get(INDIRECTACCT)!=null){
			prmtIndirectAcct.setValue(acctMap.get(para.get(INDIRECTACCT)));
		}
		if(para.get(ADMINACCT)!=null){
			prmtAdminAcct.setValue(acctMap.get(para.get(ADMINACCT)));
		}
		if(para.get(LANDACCT)!=null){
			prmtLandAcct.setValue(acctMap.get(para.get(LANDACCT)));
		}
		if(para.get(SALEACCT)!=null){
			prmtSaleAcct.setValue(acctMap.get(para.get(SALEACCT)));
		}
		super.setCustomerParams(para.getCustomerParams());
	}
	public boolean verify() {
		
		if(this.prmtDevAcct.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择开发成本科目!");
			return false;
		}
		if(this.prmtIndirectAcct.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择开发间接费用科目!");
			return false;
		}
		if(this.prmtLandAcct.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择土地成本科目!");
			return false;
		}
		if(this.prmtAdminAcct.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择管理费用科目!");
			return false;
		}
		if(this.prmtSaleAcct.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择营销成本科目!");
			return false;
		}
		
		
		
		return true;
	}
	public Map acctMap = new HashMap();
	private Map initAcct(FilterInfo filter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			acctMap.put(info.getLongNumber(), info);
		}
		return acctMap;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		initAcct(filter); 
		EntityViewInfo view = prmtDevAcct.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		view.setFilter(filter);
		prmtDevAcct.setEntityViewInfo(view);
		prmtDevAcct.setEditable(false);
		prmtDevAcct.setRequired(true);
		
		view = prmtIndirectAcct.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		view.setFilter(filter);
		prmtIndirectAcct.setEntityViewInfo(view);
		prmtIndirectAcct.setEditable(false);
		prmtIndirectAcct.setRequired(true);
		
		view = prmtAdminAcct.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		view.setFilter(filter);
		prmtAdminAcct.setEntityViewInfo(view);
		prmtAdminAcct.setEditable(false);
		prmtAdminAcct.setRequired(true);
		
		view = prmtLandAcct.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		view.setFilter(filter);
		prmtLandAcct.setEntityViewInfo(view);
		prmtLandAcct.setEditable(false);
		prmtLandAcct.setRequired(true);
		
		view = prmtSaleAcct.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		view.setFilter(filter);
		prmtSaleAcct.setEntityViewInfo(view);
		prmtSaleAcct.setEditable(false);
		prmtSaleAcct.setRequired(true);
	}
	
}