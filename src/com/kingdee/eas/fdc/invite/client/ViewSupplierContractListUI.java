package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.fdc.contract.client.ContractFullListUI;

public class ViewSupplierContractListUI extends ContractFullListUI {

	public ViewSupplierContractListUI() throws Exception {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9063316851906670619L;
	
	
	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}

	protected boolean initDefaultFilter() {
		return false;
	}
	
	protected boolean isIgnoreCUFilter()
    {
        return false;
    }
	public FilterInfo getFilterInfo() {
		SupplierInfo supplier = (SupplierInfo)this.getUIContext().get("supplier");
		if(supplier!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("partB.id", supplier.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
			return filter;
		}
		
		return super.getFilterInfo();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		// TODO 自动生成方法存根
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		
		try {
			commonQueryDialog.clearUserPanels();
			ViewSupplierContractFullFilterUI filterUI;
			filterUI = new ViewSupplierContractFullFilterUI(this,this.getUIContext(),
					this.actionOnLoad);
			commonQueryDialog.addUserPanel(filterUI);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
		return commonQueryDialog;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo){
		FilterInfo filter =  getFilterInfo();
		
		if(viewInfo.getFilter()==null){
			viewInfo.setFilter(filter);
		}else{
			FilterInfo filter1 = viewInfo.getFilter();
			try {
				filter1.mergeFilter(filter,"and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
			viewInfo.setFilter(filter1);
		}
		return super.getQueryExecutor(queryPK,viewInfo);
	}

}
