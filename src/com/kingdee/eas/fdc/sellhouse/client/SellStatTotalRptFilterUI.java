/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class SellStatTotalRptFilterUI extends AbstractSellStatTotalRptFilterUI
{
	private static final String IS_PRE_INTO_SALE = "isPreIntoSale";

	private static final String IS_INCLUDE_ATTACH = "isIncludeAttach";
	
	/**
	 * 面积类型
	 */
	private final String BUILD_AREA = "buildArea";
	private final String PRE_AREA = "preArea";
	
	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;

	/**
	 * output class constructor
	 */
	public SellStatTotalRptFilterUI(ListUI listUI, ItemAction actionListOnLoad)
			throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}

	public void clear() {
		this.chkIsIncludeAttach.setSelected(false);
		this.chkIsPrePurchaseIntoSaleStat.setSelected(false);
	}

	public FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		filter.getFilterItems().add(new FilterItemInfo(IS_PRE_INTO_SALE, new Integer(para.getBoolean(IS_PRE_INTO_SALE)?1:0)));
		filter.getFilterItems().add(new FilterItemInfo(IS_INCLUDE_ATTACH, new Integer(para.getBoolean(IS_INCLUDE_ATTACH)?1:0)));
		
		return filter;
	}



	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		
		param.add(IS_INCLUDE_ATTACH, this.chkIsIncludeAttach.isSelected());
		param.add(IS_PRE_INTO_SALE, this.chkIsPrePurchaseIntoSaleStat.isSelected());
		
		if(this.rbBuildArea.isSelected())
		{
			param.add(BUILD_AREA,true);
		}
		else
		{
			param.add(BUILD_AREA,false);
		}
		
		if(this.rbPreArea.isSelected())
		{
			param.add(PRE_AREA, true);
		}else
		{
			param.add(PRE_AREA, false);
		}
		
		return param.getCustomerParams();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			// this.clear();
		}
		isLoaded = true;
	}


	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
	
		if(para.getBoolean(BUILD_AREA))
		{
			this.rbBuildArea.setSelected(true);
		}
		else
		{
			this.rbRoomArea.setSelected(false);
		}
		if(para.getBoolean(PRE_AREA))
		{
			this.rbPreArea.setSelected(true);
		}else
		{
			this.rbActualArea.setSelected(true);
		}
		
		this.chkIsIncludeAttach.setSelected(para.getBoolean(IS_INCLUDE_ATTACH));
		this.chkIsPrePurchaseIntoSaleStat.setSelected(para.getBoolean(IS_PRE_INTO_SALE));

		super.setCustomerParams(para.getCustomerParams());
	}
	
	
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}


	boolean hasCurrency = false;

	public boolean hasCurrency() {
		return hasCurrency;
	}

	public boolean isIncludeAttach(FDCCustomerParams para) {
		return para.getBoolean(IS_INCLUDE_ATTACH);
	}

	public boolean isPreIntoSale(FDCCustomerParams para) {
		return para.getBoolean(IS_PRE_INTO_SALE);
	}
	
	public boolean isBuildArea(FDCCustomerParams para)
	{
		return para.getBoolean(BUILD_AREA);
	}
	
	public boolean isPreArea(FDCCustomerParams para)
	{
		return para.getBoolean(PRE_AREA);
	}


}