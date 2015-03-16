/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.CluesManageCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.formula.SellHouseHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CluesCustomerDeliverEditUI extends
		AbstractCluesCustomerDeliverEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CluesCustomerDeliverEditUI.class);

	/**
	 * output class constructor
	 */
	public CluesCustomerDeliverEditUI() throws Exception {
		super();
	}

	protected void inOnload() throws Exception {

	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		
//		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProject");
//   		EntityViewInfo view = CusClientHelper.getPermitViewInfo(sellProject);
//   		this.prmtPropertyConsultant.setEntityViewInfo(view);
		
		Set set = SellHouseHelper.getPerson(((SellProjectInfo)this.getUIContext().get("sellProject")).getId().toString());
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		evi.setFilter(filterInfo);

		prmtPropertyConsultant.setEntityViewInfo(evi);
		prmtPropertyConsultant.getQueryAgent().resetRuntimeEntityView();
	}

	/**
	 * 隐藏多余按钮
	 */
	private void initControl() {
		this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}

	public static void showUI(IUIObject ui, List idList,SellProjectInfo sellProject)
			throws EASBizException, BOSException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("idList", idList);
		uiContext.put("sellProject",sellProject);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CluesCustomerDeliverEditUI.class.getName(), uiContext,
						null, STATUS_VIEW);
		uiWindow.show();
	}

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		verifyInput(e);
		List idList = (List) this.getUIContext().get("idList"); //多个置业顾问
		if (idList != null && idList.size() > 0) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", FDCHelper.list2Set(idList),
							CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("*");
			view.getSelector().add("propertyConsultant.id");
			view.getSelector().add("propertyConsultant.*");
			view.getSelector().add("shareProperty.*");
			view.getSelector().add("shareProperty.user.*");
			view.getSelector().add("shareProperty.operationPerson.*");
			CluesManageCollection cluesCustomerColl = CluesManageFactory
					.getRemoteInstance().getCluesManageCollection(view);
			if(cluesCustomerColl != null && cluesCustomerColl.size() >0){
				for (int i = 0; i < cluesCustomerColl.size(); i++) {
					CluesManageInfo info = cluesCustomerColl.get(i);
					Map deliverMap = new HashMap();
					deliverMap.put("propertyConsultant", prmtPropertyConsultant.getValue());
					CluesManageFactory.getRemoteInstance().deliverClues(info, deliverMap);
				}
			}
		}
		MsgBox.showInfo("线索客户转交成功!");
		this.destroyWindow();
	}
	
	/**
	 * output btnCancell_actionPerformed method
	 */
	protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
		// super.btnCancell_actionPerformed(e);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		UserInfo userInfo = (UserInfo)this.prmtPropertyConsultant.getValue();
		if (userInfo == null) {
			MsgBox.showInfo("转交的置业顾问不能为空!");
			this.abort();
		}
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CluesManageFactory.getRemoteInstance();
	}
}