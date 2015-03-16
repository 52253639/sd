/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.QueryPanelCollection;
import com.kingdee.eas.base.commonquery.QueryPanelInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.XMLBean;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;

/**
 * output class name
 */
public class TenancyStatListRptUI extends AbstractTenancyStatListRptUI
{
	private static final Logger logger = CoreUIObject.getLogger(TenancyStatListRptUI.class);
	
	public TenancyStatListRptUI() throws Exception
	{
		super();
	}
	
	public void storeFields()
	{
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnView.setVisible(false);
		this.actionView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		if (this.getUIContext().get("buildingId") != null) {
			actionQuery.setVisible(false);
			actionQuery.setEnabled(false);
		}
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardRoomRent").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealRoomRent").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
	}
	
	protected void beforeExcutQuery(EntityViewInfo arg) {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
//		第一次打开默认查询
		try {
			if (isFirstDefaultQuery()) {
				QuerySolutionInfo querySolution = QuerySolutionFacadeFactory
				.getRemoteInstance()
				.getDefaultSolution(TenancyStatListRptUI.class.getName(),
				"com.kingdee.eas.fdc.tenancy.app.TenancyStatListRptQuery");
				if (querySolution != null) {
					if (querySolution.get("querypanelinfo") instanceof QueryPanelCollection) {
						QueryPanelCollection panelCollection = (QueryPanelCollection) querySolution
						.get("querypanelinfo");
						QueryPanelInfo queryPanelInfo = panelCollection
						.get(panelCollection.size() - 1);
						if (queryPanelInfo.get("customerparams") != null) {
							String customerparams = queryPanelInfo.get(
							"customerparams").toString();
							getFilterUI()
							.setCustomerParams(
									XMLBean
									.TransStrToCustParams(customerparams));
						}
						
					}
				}
			}
		} catch (EASBizException e1) {
			super.handUIException(e1);
		} catch (BOSException e1) {
			super.handUIException(e1);
		} catch (Exception e) {
			super.handUIException(e);
		}
		
		FilterInfo filter = new FilterInfo();
		
		Set proIds = null;
		try {
			proIds = CommerceHelper.getPermitProjectIds();
		} catch (BOSException e1) {
			this.handleException(e1);
			this.abort();
		}
		if (this.getUIContext().get("buildingId") == null) {
			if(proIds != null  &&  !proIds.isEmpty()){
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id",proIds,CompareType.INCLUDE));
			}
			
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
			.getCustomerParams());
			
			if(!para.getBoolean("isShowAll")){
			Date endDate = this.getFilterUI().getEndQueryDate(para);
			
			if (endDate != null) {
			filter.getFilterItems().add(
			new FilterItemInfo("actDeliveryRoomDate", FDCHelper
			.getNextDay(endDate), CompareType.LESS));
			}
			
			Date beginDate = this.getFilterUI().getBeginQueryDate(para);
			if (beginDate != null) {
			filter.getFilterItems().add(
			new FilterItemInfo("actDeliveryRoomDate", beginDate,
			CompareType.GREATER_EQUALS));
			}
			}
		}else {
			String buildingId = (String) this.getUIContext().get("buildingId");
			if (buildingId != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
			}
			CustomerParams para = (CustomerParams)this.getUIContext().get("cp");
			if(!para.getBoolean("isShowAll")){
			Date beginDate = (Date) this.getUIContext().get("beginDate");
			if (beginDate != null) {
			filter.getFilterItems().add(
			new FilterItemInfo("actDeliveryRoomDate", beginDate,
			CompareType.GREATER_EQUALS));
			}
			Date endDate = (Date) this.getUIContext().get("endDate");
			if (endDate != null) {
			filter.getFilterItems().add(
			new FilterItemInfo("actDeliveryRoomDate", FDCHelper
			.getNextDay(endDate), CompareType.LESS));
			}
			}
		}
		try {
			if (arg.getFilter() == null) {
				arg.setFilter(filter);
			} else {
				arg.getFilter().mergeFilter(filter, "and");
			}
		} catch (BOSException e) {
			super.handUIException(e);
		}
		super.beforeExcutQuery(arg);
	}
	
	protected void setActionState() {
		
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected String getEditUIName() {
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			return;
		}
		super.tblMain_tableClicked(e);
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
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}
	
	private TenancyStatRptFilterUI filterUI = null;
	
	private TenancyStatRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new TenancyStatRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	 protected String[] getLocateNames()
	 {
	        String[] locateNames = new String[2];
	        locateNames[0] = "sellProject.name";
	        locateNames[1] = IFWEntityStruct.dataBase_Name;
	        return locateNames;
	}
	
	/**
	 * 根据id显示窗体
	 * 
	 * @param filter
	 */
	public static void showUI(IUIObject ui, String buildingId, Date beginDate,
			Date endDate, CustomerParams cp)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("buildingId", buildingId);
		uiContext.put("beginDate", beginDate);
		uiContext.put("endDate", endDate);
		uiContext.put("cp", cp);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TenancyStatListRptUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
	}
	
}