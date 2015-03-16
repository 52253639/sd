/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CluesManageCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CluesCustomerShareEditUI extends AbstractCluesCustomerShareEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CluesCustomerShareEditUI.class);

	/**
	 * output class constructor
	 */
	public CluesCustomerShareEditUI() throws Exception {
		super();
	}

	protected void inOnload() throws Exception {

	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		comboShareType_actionPerformed(null);
		prmtProject.setSelector(new FDCSellProjectDialog(Boolean.FALSE,
				MoneySysTypeEnum.SalehouseSys));

		// SellProjectInfo sellProject =
		// (SellProjectInfo)this.getUIContext().get("sellProject");
		// EntityViewInfo view = CusClientHelper.getPermitViewInfo(sellProject);
		// this.prmtPropertyConsultant.setEntityViewInfo(view);
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

	public static void showUI(IUIObject ui, List idList,
			SellProjectInfo sellProject) throws EASBizException, BOSException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("idList", idList);
		uiContext.put("sellProject", sellProject);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CluesCustomerShareEditUI.class.getName(), uiContext,
						null, STATUS_VIEW);
		uiWindow.show();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CluesManageFactory.getRemoteInstance();
	}

	protected void comboShareType_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if (this.comboShareType.getSelectedItem().equals(
				ShareTypeEnum.USERSHARE)) {
			this.prmtPropertyConsultant.setEnabled(true);
			this.prmtProject.setValue(null);
			this.prmtProject.setEnabled(false);
		} else {
			this.prmtPropertyConsultant.setEnabled(false);
			this.prmtPropertyConsultant.setValue(null);
			this.prmtProject.setEnabled(true);
		}
	}

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		verifyInput(e);
		List idList = (List) this.getUIContext().get("idList");
		if (idList != null && idList.size() > 0) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", FDCHelper.list2Set(idList),
							CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("propertyConsultant.*");
			view.getSelector().add("shareProperty.*");
			view.getSelector().add("shareProject.*");

			CluesManageCollection cluesCustomerColl = CluesManageFactory
					.getRemoteInstance().getCluesManageCollection(view);
			Map shareMap = new HashMap();
			shareMap.put("shareType", comboShareType.getSelectedItem());
			shareMap.put("propertyConsultant", prmtPropertyConsultant
					.getValue());
			shareMap.put("project", prmtProject.getValue());
			CluesManageFactory.getRemoteInstance().shareClues(
					cluesCustomerColl, shareMap);
		}
		MsgBox.showInfo("线索客户共享成功!");
		this.destroyWindow();
	}

	/**
	 * output btnCancell_actionPerformed method
	 */
	protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (this.comboShareType.getSelectedItem().equals(
				ShareTypeEnum.USERSHARE)) {
			Object[] userObject = (Object[]) prmtPropertyConsultant.getValue();
			if (userObject[0] == null) {
				MsgBox.showInfo("共享的置业顾问不能为空!");
				this.abort();
			}
		} else {
			Object[] sellProject = (Object[]) prmtProject.getValue();
			if (sellProject[0] == null) {
				MsgBox.showInfo("共享的项目不能为空!");
				this.abort();
			}
		}
	}
}