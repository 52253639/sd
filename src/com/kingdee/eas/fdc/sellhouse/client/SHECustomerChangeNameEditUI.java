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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
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
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHECustomerChangeNameEditUI extends
		AbstractSHECustomerChangeNameEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SHECustomerChangeNameEditUI.class);

	/**
	 * output class constructor
	 */
	public SHECustomerChangeNameEditUI() throws Exception {
		super();
	}

	protected void inOnload() throws Exception {

	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.btnSubmit.setEnabled(true);

		List idList = (List) this.getUIContext().get("idList");
		if (idList != null && idList.size() > 0) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", FDCHelper.list2Set(idList),
							CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("*");
			SHECustomerCollection sheCustomerColl = SHECustomerFactory
					.getRemoteInstance().getSHECustomerCollection(view);

			for (int i = 0; i < sheCustomerColl.size(); i++) {
				SHECustomerInfo info = sheCustomerColl.get(0);
				this.txtName.setText(info.getName());
			}
		}
	}

	protected IObjectValue createNewData() {
		// SHECustomerInfo info = new SHECustomerInfo();
		// info = (SHECustomerInfo)this.getUIContext().get("sheCustomer");
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECustomerFactory.getRemoteInstance();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
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
			view.getSelector().add("id");
			view.getSelector().add("sellProject.*");
			view.getSelector().add("*");

			SHECustomerCollection sheCustomerColl = SHECustomerFactory
					.getRemoteInstance().getSHECustomerCollection(view);
			if(sheCustomerColl != null && sheCustomerColl.size() >0){
				SHECustomerInfo info = sheCustomerColl.get(0);
				Map changeNameMap = new HashMap();
				changeNameMap.put("newName", this.txtNewName.getText());
				changeNameMap.put("sellProject", info.getSellProject());
				try {
					 SHECustomerFactory.getRemoteInstance().changeName(info, changeNameMap);
					 SelectorItemCollection upsel=new SelectorItemCollection();
					 upsel.add("name");
					 CommerceChanceCollection col=CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection("select id from where customer.id='"+info.getId().toString()+"'");
					 for(int i=0;i<col.size();i++){
						 col.get(i).setName(this.txtNewName.getText()+"的商机");
						 CommerceChanceFactory.getRemoteInstance().updatePartial(col.get(i), upsel);
					 }
					 MsgBox.showInfo("客户更名成功");
					 this.destroyWindow();
				} catch (EASBizException ex) {
					MsgBox.showInfo(ex.getMessage().toString());
				}
			}
		}
	}

	public static void showUI(IUIObject ui, List idList)
			throws EASBizException, BOSException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("idList", idList);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(SHECustomerChangeNameEditUI.class.getName(), uiContext,
						null, STATUS_VIEW);
		uiWindow.show();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		// FDCClientVerifyHelper.verifyEmpty(this,this.txtNewName);
		if (txtNewName.getText() == null || txtNewName.getText().equals("")) {
			MsgBox.showInfo("新客户名称不能为空!");
			this.abort();
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		return sels;
	}

}