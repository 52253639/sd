/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CluesCustomerUI extends AbstractCluesCustomerUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CluesCustomerUI.class);

	/**
	 * output class constructor
	 */
	public CluesCustomerUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.comboCusType.addItems(CustomerTypeEnum.getEnumList().toArray());

		this.comboCusType.setRequired(true);
		this.txtCusName.setRequired(true);
		this.txtPhone.setRequired(true);
		this.txtPhone.setEnabled(false);

		if (this.getUIContext().get("cluesCustomer") != null) {
			CluesManageInfo cluesInfo = (CluesManageInfo) this.getUIContext()
					.get("cluesCustomer");
			this.comboCusType
					.setSelectedItem(CustomerTypeEnum.PERSONALCUSTOMER);
			txtCusName.setText(cluesInfo.getName());
			this.txtPhone.setText(cluesInfo.getPhone());

			this.comboCusType.setRequired(true);
			this.txtCusName.setRequired(true);
			this.txtPhone.setRequired(true);
		}

		this.contFirstLinkMan.setVisible(false);
	}

	protected void comboCusType_itemStateChanged(ItemEvent e) throws Exception {
		boolean isPer = CustomerTypeEnum.PERSONALCUSTOMER
				.equals(this.comboCusType.getSelectedItem());
		if (!isPer) {
			this.contFirstLinkMan.setVisible(true);
		} else {
			this.contFirstLinkMan.setVisible(false);
		}
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		HashMap map = (HashMap)this.getUIContext();
		if (this.txtCusName.getText() == null
				|| this.txtCusName.getText().trim().equals("")) {
			MsgBox.showInfo(this, "客户姓名必须输入!");
			this.abort();
		}

		if (this.txtPhone.getText() == null
				|| this.txtPhone.getText().trim().length() < 7) {
			MsgBox.showInfo(this, "联系电话必须输入,且至少需要输入7位以上!");
			this.abort();
		}

		boolean isPer = CustomerTypeEnum.PERSONALCUSTOMER
				.equals(this.comboCusType.getSelectedItem());
		if (!isPer) {
			if (this.txtFirstLinkman.getText() == null
					|| this.txtFirstLinkman.getText().trim().equals("")) {
				MsgBox.showInfo(this, "首选联系人必须输入!");
				this.abort();
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("customerType", this.comboCusType
						.getSelectedItem()));
		filter.getFilterItems().add(
				new FilterItemInfo("name", this.txtCusName.getText().trim()));
		filter.getFilterItems().add(
				new FilterItemInfo("phone", "%"
						+ this.txtPhone.getText().trim() + "%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("tel", "%" + this.txtPhone.getText().trim()
						+ "%", CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("officeTel", "%"
						+ this.txtPhone.getText().trim() + "%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("otherTel", "%"
						+ this.txtPhone.getText().trim() + "%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("fax", "%" + this.txtPhone.getText().trim()
						+ "%", CompareType.LIKE));
		filter
				.setMaskString("#0 and #1 and #2 and (#3 or #4 or #5 or #6 or #7)");

		view.setFilter(filter);
		SHECustomerCollection sheCustomerColl = SHECustomerFactory
				.getRemoteInstance().getSHECustomerCollection(view);
		if (sheCustomerColl != null && sheCustomerColl.size() > 0) {
			MsgBox.showInfo(this, "当前客户已经存在！");
			return;
		} else {
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
					.get("sellProject");
			CluesStatusEnum status = (CluesStatusEnum) this.getUIContext().get(
					"status");
			CluesManageInfo info = (CluesManageInfo) this.getUIContext().get(
					"cluesCustomer");

			if (status.equals(CluesStatusEnum.CUSTOMER)) {
				UIContext uiContext = new UIContext(this);
				uiContext.put("id", info.getId());
				uiContext.put("customerType", this.comboCusType
						.getSelectedItem());
				uiContext.put("cluesCustomer", info);
				uiContext.put("sellProject", info.getSellProject());
				uiContext.put("status", status);
				uiContext.put("propertyConsultant", info
						.getPropertyConsultant());
				uiContext.put("cusName", this.txtCusName.getText().trim());
				uiContext.put("cusPhone", this.txtPhone.getText().trim());
				uiContext.put("cusCertificateNum", this.txtCertificateNum
						.getText().trim());
				if (!isPer) {
					uiContext.put("firstLinkMan", this.txtFirstLinkman
							.getText().trim());
				} else {
					uiContext.put("firstLinkMan", null);
				}
				if(map.get("addnewDerict") != null){
					uiContext.put("addnewDerict", map.get("addnewDerict"));
				}
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						CustomerRptEditUI.class.getName(), uiContext, null,
						"ADDNEW");
				destroyWindow();
				uiWindow.show();
			} else {
				SHECustomerInfo customerInfo = CluesManageFactory
						.getRemoteInstance().updateCluesStatus(info,
								this.txtFirstLinkman.getText());

				// 若修改姓名和证件号码，更新新生成的客户
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("name");
				selector.add("phone");
				selector.add("certificateNumber");
				customerInfo.setName(this.txtCusName.getText());
				customerInfo.setCertificateNumber(this.txtCertificateNum
						.getText());
				customerInfo.setPhone(this.txtPhone.getText());
				SHECustomerFactory.getRemoteInstance().updatePartial(
						customerInfo, selector);

				List customerList = new ArrayList();
				customerList.add(customerInfo);
				destroyWindow();
				if (status.equals(CluesStatusEnum.PREPURCHASE)) {
					SHEManageHelper.toTransactionBill(customerInfo.getId(),
							sellProject, this, PrePurchaseManageEditUI.class
									.getName(), customerList);
				} else if (status.equals(CluesStatusEnum.PURCHASE)) {
					SHEManageHelper.toTransactionBill(customerInfo.getId(),
							sellProject, this, PurchaseManageEditUI.class
									.getName(), customerList);
				} else if (status.equals(CluesStatusEnum.SIGN)) {
					SHEManageHelper.toTransactionBill(customerInfo.getId(),
							sellProject, this,
							SignManageEditUI.class.getName(), customerList);
				} else if (status.equals(CluesStatusEnum.COMMERCECHANCE)) {

					UIContext uiContext = new UIContext(this);
					uiContext.put("id", info.getId());
					uiContext.put("sheCustomer", customerInfo);
					uiContext.put("cluesCustomer", info);
					uiContext.put("sellProject", info.getSellProject());
					uiContext.put("status", status);
					uiContext.put("propertyConsultant", info
							.getPropertyConsultant());
					uiContext.put(CommerceChangeNewEditUI.KEY_DESTORY_WINDOW,
							Boolean.TRUE);
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(
							CommerceChangeNewEditUI.class.getName(), uiContext,
							null, "ADDNEW");
					uiWindow.show();
				}
			}
		}
	}

	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		this.getUIContext().put("isContinue", Boolean.FALSE);
		destroyWindow();
	}
}