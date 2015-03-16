package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 *
 * 描述:成本科目F7
 *
 * @author jackwang date:2006-11-2
 *         <p>
 * @version EAS5.2
 */
public class ListingItemPromptBox extends KDCommonPromptDialog {
	protected IUIWindow classDlg = null;

	protected IUIObject owner;

	protected boolean isOnlyLeaf = false;// 是否只选叶结点

	protected boolean isMultiSelect = false;// 是否多选

	protected boolean isOrgFilter = false;// 是否组织隔离

	protected SelectorItemCollection userSeletor = null;

	public ListingItemPromptBox() {
	}

	public ListingItemPromptBox(IUIObject owner) {
		this.owner = owner;
	}

	public ListingItemPromptBox(IUIObject owner, boolean isOnlyLeaf, boolean isMultiSelect, boolean isOrgFilter, SelectorItemCollection userSelector) {
		this.owner = owner;
		this.isOnlyLeaf = isOnlyLeaf;
		this.isMultiSelect = isMultiSelect;
		this.isOrgFilter = isOrgFilter;
		this.userSeletor = userSelector;
	}

	public String getUITitle() {
		String resFullName = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource.CostAccount";
		String returnValue = EASResource.getString(resFullName);
		return returnValue;
	}

	public void show() {
		Map map = new HashMap();
		map.put("Owner", ComponentUtil.getRootComponent((Component) owner));
		map.put("isF7", Boolean.valueOf(true));
		map.put("isOrgFilter", Boolean.valueOf(this.isOrgFilter));
		map.put("isOnlyLeaf", Boolean.valueOf(this.isOnlyLeaf));
		map.put("userSelector", this.userSeletor);
		map.put("isMultiSelect", Boolean.valueOf(this.isMultiSelect));
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			classDlg = uiFactory.create("com.kingdee.eas.fdc.invite.client.ListingItemF7UI", map);

			classDlg.show();
		} catch (BOSException ex) {
			ExceptionHandler.handle(this, ex);
			return;
		}
	}

	public boolean isCanceled() {
		if (classDlg != null) {
			return ((ListingItemF7UI) classDlg.getUIObject()).isCanceled ;
			// return false;
		} else {
			return true;
		}
		// return true;
	}

	// public KDPromptData getData()
	public Object getData() {
		// TODO 自动生成方法存根
		ListingItemF7UI ui = (ListingItemF7UI) classDlg.getUIObject();
		ListingItemInfo[] returnInfos = null;
		int len = 0;
		try {
			returnInfos = ui.getReturnValues();

			if (returnInfos != null) {
				len = returnInfos.length;
			} else {
				return null;
			}
		} catch (Exception err) {
			// MsgBox.showError(this , err.getMessage()) ;
			return null;
		}
//		Object[] returnValue = new Object[len];
//		// Object[] returnValue = null ;
//		returnValue = returnInfos;
		// ui.getUIWindow().close();
		return returnInfos;
	}

}
