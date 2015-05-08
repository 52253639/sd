/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.schedule.CtrlItemFactory;
import com.kingdee.eas.fdc.schedule.CtrlItemInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class CtrlItemEditUI extends AbstractCtrlItemEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CtrlItemEditUI.class);

	/**
	 * output class constructor
	 */
	public CtrlItemEditUI() throws Exception {
		super();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		CtrlItemInfo crtItemInfo = new CtrlItemInfo();
		crtItemInfo.setIsEnabled(isEnabled);
		return crtItemInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CtrlItemFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		return sic;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		txtName.setMaxLength(80);
		txtNumber.setMaxLength(80);
		bizDescription.setMaxLength(255);
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
				FDCBaseDataClientUtils.CTRLITEM));
	}
}