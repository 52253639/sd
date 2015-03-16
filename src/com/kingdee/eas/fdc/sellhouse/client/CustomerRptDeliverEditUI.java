/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeReasonEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeReasonFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignManage;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.formula.SellHouseHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CustomerRptDeliverEditUI extends AbstractCustomerRptDeliverEditUI {
	private static final Logger logger = CoreUIObject.getLogger(CustomerRptDeliverEditUI.class);

	private static String porjectID = "";

	protected void inOnload() throws Exception {

	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
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

	public static boolean hasDeliverPermission(String customerId) throws EASBizException, BOSException, UuidException {
		if (customerId == null)
			return false;
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();

		if (SHECustomerFactory.getRemoteInstance().exists("where id='" + customerId + "' and propertyConsultant.id='" + currUserInfo.getId().toString() + "' "))
			return true; // 当前登录人员的自己的客户

		boolean hasPermission = false;

		// 当前组织下，作为指定客户所属的顾问的负责人，且拥有业务操作权限
		hasPermission = SHEHelper.hasOperatorPermission(customerId);
		if (hasPermission)
			return true;

		/**
		 * 需实现的代码
		 */

		return false;
	}

	public static void showUI(IUIObject ui, List idList, SellProjectInfo sellProject) throws EASBizException, BOSException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("idList", idList);
		uiContext.put("sellProject", sellProject);
		porjectID = sellProject.getId().toString();
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptDeliverEditUI.class.getName(), uiContext, null, STATUS_VIEW);
		uiWindow.show();
	}

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		// super.btnConfirm_actionPerformed(e);
		verifyInput(e);
		// UserInfo userInfo = (UserInfo)
		// this.prmtPropertyConsultant.getValue();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		List idList = (List) this.getUIContext().get("idList"); // 多个置业顾问
		UserInfo ui = UserFactory.getRemoteInstance().getUserByID(new ObjectUuidPK(((UserInfo)this.prmtPropertyConsultant.getValue()).getId()));
		if(ui.getPerson() == null){
			FDCMsgBox.showInfo("该用户无对应职员，无法进行转交!");
			SysUtil.abort();
		}
		IObjectPK personid =  new ObjectUuidPK(ui.getPerson().getId());
		PersonInfo pi = PersonFactory.getRemoteInstance().getPersonInfo(personid);
		if (idList != null && idList.size() > 0) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(idList), CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("*");
			view.getSelector().add("propertyConsultant.id");
			view.getSelector().add("propertyConsultant.*");
			view.getSelector().add("shareProperty.*");
			view.getSelector().add("shareProperty.user.*");
			view.getSelector().add("shareProperty.operationPerson.*");
			view.getSelector().add("sellProject.*");

			SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if (customerColl != null && customerColl.size() > 0) {
				for (int i = 0; i < customerColl.size(); i++) {
					SHECustomerInfo info = customerColl.get(i);
					Map deliverMap = new HashMap();
					deliverMap.put("propertyConsultant", this.prmtPropertyConsultant.getValue());
					SHECustomerFactory.getRemoteInstance().deliverCustomer(info, deliverMap);
				}
			}
			for (int i = 0; i < customerColl.size(); i++) {
				SHECustomerInfo info = customerColl.get(i);
				info.setPropertyConsultant((UserInfo) this.prmtPropertyConsultant.getValue());
				SHECustomerChangeReasonFacadeFactory.getRemoteInstance().addNewMessage(info.getSellProject().getOrgUnit(), SHECustomerChangeReasonEnum.TRANSMIT_VALUE, info,
						info.getPropertyConsultant());
				// 更新为最新的置业顾问 wyh
				SHECustomerInfo info_forupdate = new SHECustomerInfo();
				info_forupdate.setId(info.getId());
				info_forupdate.setIsPublic(false);
				info_forupdate.setPropertyConsultant((UserInfo) this.prmtPropertyConsultant.getValue());
				SHECustomerFactory.getRemoteInstance().updatePartial(info_forupdate, getSelectorItemCollection());
				// 将商机转给最新的置业顾问
				CommerceChanceCollection ccc = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection("where Customer.id = '" + info.getId() + "'");
				for (int j = 0; j < ccc.size(); j++) {
					CommerceChanceInfo cci = ccc.get(j);
					cci.setSaleMan((UserInfo) this.prmtPropertyConsultant.getValue());
					CommerceChanceFactory.getRemoteInstance().updatePartial(cci, getCommerceChance());
					// 将跟进转给最新的置业顾问
					this.updateCommerceChanceTrack(cci);
					// 将问卷转给最新的置业顾问
					this.updateQuestion(cci,pi);
				}
				SelectorItemCollection update=new SelectorItemCollection();
				update.add("saleManNames");
				SignCustomerEntryCollection signEntryCol=SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection("select head.id from where isMain=1 and customer.id= '" + info.getId() + "'");
				for (int j = 0; j < signEntryCol.size(); j++) {
					SignSaleManEntryFactory.getRemoteInstance().delete("where head.id='"+signEntryCol.get(j).getHead().getId().toString()+"'");
					
					SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
					entry.setHead(signEntryCol.get(j).getHead());
					entry.setUser((UserInfo) this.prmtPropertyConsultant.getValue());
					SignSaleManEntryFactory.getRemoteInstance().addnew(entry);
					
					SignManageInfo sign=new SignManageInfo();
					sign.setSaleManNames(((UserInfo) this.prmtPropertyConsultant.getValue()).getName());
					sign.setId(signEntryCol.get(j).getHead().getId());
					SignManageFactory.getRemoteInstance().updatePartial(sign, update);
				}
			}
		}
		MsgBox.showInfo("客户转交成功!");
		this.destroyWindow();
	}

	public void updateCommerceChanceTrack(CommerceChanceInfo cci) throws BOSException, EASBizException {
		CommerceChanceTrackCollection cctc = CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection("where commerceChance.id = '" + cci.getId() + "'");
		for(int i=0;i<cctc.size();i++){
			CommerceChanceTrackInfo ccti = cctc.get(i);
			ccti.setSaleMan((UserInfo) this.prmtPropertyConsultant.getValue());
			CommerceChanceTrackFactory.getRemoteInstance().updatePartial(ccti, getCommerceChanceTrack());				
		}
	}
	
	public void updateQuestion(CommerceChanceInfo cci,PersonInfo pi) throws BOSException, EASBizException {
		QuestionPaperAnswerCollection qpac = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection("where commerceChance.id = '" + cci.getId() + "'");
		for(int i=0;i<qpac.size();i++){
			QuestionPaperAnswerInfo qpai = qpac.get(i);
			qpai.setPersion(pi);
			CommerceChanceTrackFactory.getRemoteInstance().updatePartial(qpai, getQuestion());				
		}
	}

	public SelectorItemCollection getSelectorItemCollection() {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add(new SelectorItemInfo("propertyConsultant"));
		collection.add(new SelectorItemInfo("isPublic"));
		return collection;
	}

	public SelectorItemCollection getCommerceChance() {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add(new SelectorItemInfo("saleMan"));
		return collection;
	}

	public SelectorItemCollection getCommerceChanceTrack() {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add(new SelectorItemInfo("saleMan"));
		return collection;
	}

	public SelectorItemCollection getQuestion() {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add(new SelectorItemInfo("persion"));
		return collection;
	}
	
	/**
	 * output btnCancell_actionPerformed method
	 */
	protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		this.destroyWindow();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		UserInfo userInfo = (UserInfo) this.prmtPropertyConsultant.getValue();
		if (userInfo == null) {
			MsgBox.showInfo("转交的置业顾问不能为空!");
			this.abort();
		}
	}

	// 过滤当前项目置业顾问
	protected void prmtPropertyConsultant_willCommit(CommitEvent e) throws Exception {
		super.prmtPropertyConsultant_willCommit(e);
		setPerson();
	}

	// 过滤当前项目置业顾问
	protected void prmtPropertyConsultant_willShow(SelectorEvent e) throws Exception {
		super.prmtPropertyConsultant_willShow(e);

		setPerson();
	}

	private void setPerson() throws EASBizException, BOSException, SQLException {
		// 过滤顾问
		Set set = NewCommerceHelper.getPermitSaleManIdSet(null,null);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter= new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		evi.setFilter(filter);

		prmtPropertyConsultant.setEntityViewInfo(evi);
		prmtPropertyConsultant.getQueryAgent().resetRuntimeEntityView();
	}

	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECustomerFactory.getRemoteInstance();
	}

	/**
	 * output class constructor
	 */
	public CustomerRptDeliverEditUI() throws Exception {
		super();
	}

}