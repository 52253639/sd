/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.client.ChannelTypeListUI;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.SharePropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChangeNewEditUI extends AbstractCommerceChangeNewEditUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6093060273500009869L;
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChangeNewEditUI.class);
	private SellProjectInfo sellProject = null;
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
	
	private FullOrgUnitInfo orgUnit = SysContext.getSysContext()
	.getCurrentOrgUnit().castToFullOrgUnitInfo();
	
	private String filterStr = "";
	//是否是第一次建客户时新增的商机
	private boolean isDirect = false;

	/**
	 * output class constructor
	 */
	public CommerceChangeNewEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();

		if (!"".equals(this.txtDescription.getText())) {
			this.editData.setDescription(this.txtDescription.getText());
		}
		//if (!"".equals(this.txtStopReason.getText())) {
			//this.editData.setStopReason(this.txtStopReason.getText());
		//}
		if(this.prmtReason.getValue()!=null){
			this.editData.setLinkStopReason((CommerceChanceAssistantInfo)this.prmtReason.getValue());
		}
		if (!"".equals(this.txtReason.getText())) {
			this.editData.setReasonDesc(this.txtReason.getText());
		}
		this.editData.getChangeRoom().clear();
		
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			if (row == null) {
				continue;
			}
			RoomInfo room = new RoomInfo();
			room.setId((BOSUuid) row.getCell("id").getValue());
			room.setRoomModel((RoomModelInfo) row.getCell("roomModel")
					.getValue());
			room.setBuildingArea((BigDecimal) row.getCell("buildArea")
					.getValue());
			room.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
			room.setStandardTotalAmount((BigDecimal) row.getCell(
					"standardPrice").getValue());
			CommerceChangeRoomInfo info = new CommerceChangeRoomInfo();
			info.setRoom(room);
			this.editData.getChangeRoom().add(info);
		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			this.btnAddRoom.setEnabled(true);
			this.btnDelRoom.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        	this.btnAddRoom.setEnabled(true);
    		this.btnDelRoom.setEnabled(true);
    		
    		this.txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtCustomer.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtName.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtSaleMan.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtCommerceChanceStage.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtReason.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.pkEffectiveDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
    		this.txtNumber.setEnabled(false);
    		this.prmtCustomer.setEnabled(false);
    		this.txtName.setEnabled(false);
    		this.prmtSaleMan.setEnabled(false);
    		this.prmtCommerceChanceStage.setEnabled(false);
    		this.prmtReason.setEnabled(false);
    		this.pkEffectiveDate.setEnabled(false);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        	this.btnAddRoom.setEnabled(false);
    		this.btnDelRoom.setEnabled(false);
        }
	}

	public void loadFields() {
		super.loadFields();

		if (this.editData.getDescription() != null) {
			this.txtDescription.setText(this.editData.getDescription());
		}
		if (this.editData.getLinkStopReason() != null) {
			//this.txtStopReason.setText(this.editData.getStopReason());
			this.prmtReason.setValue(this.editData.getLinkStopReason());
		}
		if (this.txtReason.getText() != null) {
			this.txtReason.setText(this.editData.getReasonDesc());
		}
		if(this.editData.getCommerceSrc()!=null){
			this.comboCommerceSrc.setSelectedItem(this.editData.getCommerceSrc());
		}
		if(this.editData.getWorkArea() != null){
			this.prmtWorkArea.setValue(this.editData.getWorkArea());
		}
		if(this.editData.getStayArea() != null){
			this.prmtStayArea.setValue(this.editData.getStayArea());
		}
		if(this.editData.getIntentionType() != null){
			this.prmtIntentionType.setValue(this.editData.getIntentionType());
		}
		
		this.tblRoom.removeRows();
		CommerceChangeRoomCollection roomCollection = this.editData
				.getChangeRoom();
		for (int i = 0; i < roomCollection.size(); i++) {
			CommerceChangeRoomInfo info = roomCollection.get(i);
			this.addNewRoom(info.getRoom());
		}

		if (this.editData.getId() != null) {
			try {
				fillTrackTbl();
				fillQuestionTbl();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionSave.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.comboStatus.setEditable(false);
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.comboStatus.setSelectedItem(CommerceChangeNewStatusEnum.ACTIVE);
		}else{
			//wyh 非新增情况下将按钮隐藏
//			this.btnAddTrack.setEnabled(false);
//			this.btnAddQuestion.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.btnRemove.setEnabled(false);
			this.btnAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.btnEdit.setVisible(false);
//			this.btnRemove.setVisible(false);
//			this.menuItemEdit.setVisible(false);
//			this.menuItemRemove.setVisible(false);
		}
		
		this.comboStatus.setEnabled(false);
		this.comboCommerceSrc.setEditable(false);
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			if (this.getUIContext().get("cluesCustomer") != null) {
				this.comboCommerceSrc.setSelectedItem(CommerceSrcEnum.CULUES);
			}else{
				this.comboCommerceSrc.setSelectedItem(CommerceSrcEnum.ADDBYHAND);
			}
		}
	
		comboCommerceSrc.setEnabled(false);
		this.btnAddCustomer.setIcon(EASResource
				.getIcon("imgTbtn_conadministrator"));
		this.actionAddCustomer.setEnabled(true);
		initRoomTable();

		//this.txtStopReason.setEnabled(false);
		//this.prmtReason.setEnabled(false);
		this.txtReason.setEnabled(false);

		initF7();
		initF7Enable();
//		initSaleMans();
//		initCustomer();

		this.prmtCustomer.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				setCommerceChanceName();
			}
		});
		
		this.getfilterStr();
		this.tblRoom.getStyleAttributes().setLocked(true);
		this.tblTrack.getStyleAttributes().setLocked(true);
		
		if(this.getUIContext().get("customerId") != null){
			this.prmtCustomer.setValue((SHECustomerInfo)this.getUIContext().get("customerId"));
			editData.setCustomer((SHECustomerInfo)this.getUIContext().get("customerId"));
			this.txtName.setText(editData.getCustomer().getName()+"的商机");
		}
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		fillTrackTbl();
		fillQuestionTbl();
		
		if(CommerceChangeNewStatusEnum.CLOSE.equals(editData.getStatus())||CommerceChangeNewStatusEnum.END.equals(editData.getStatus())
				||CommerceChangeNewStatusEnum.TRANSACTION.equals(editData.getStatus())){
			this.btnAddTrack.setEnabled(false);
			this.btnAddQuestion.setEnabled(false);
		}
		contWorkArea.getBoundLabel().setForeground(Color.BLUE);
		contStayArea.getBoundLabel().setForeground(Color.BLUE);
	}

	private void initCustomer() throws EASBizException, BOSException {
		
		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo  info = (SellProjectInfo)this.getUIContext().get("sellProject");
			Set idRow = new HashSet();
			idRow = getCustomerId(info);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if (idRow != null && idRow.size() > 0) {
				filter.getFilterItems().add(
					new FilterItemInfo("id",FDCTreeHelper.getStringFromSet(idRow),
							CompareType.INNER));
			}else{
				filter.getFilterItems().add(
						new FilterItemInfo("id", null,
								CompareType.EQUALS));
			}
			view.setFilter(filter);
			this.prmtCustomer.setEntityViewInfo(view);	
		}
	}
	
	private void setCommerceChanceName() {
		if (this.prmtCustomer.getValue() != null) {
			SHECustomerInfo customer = (SHECustomerInfo) this.prmtCustomer
					.getValue();
			if (customer != null && customer.getName() != null) {
				this.txtName.setText(customer.getName() + "的商机");
			}
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		initF7Enable();
		this.actionAddCustomer.setVisible(false);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
//			handleCodingRule();
			SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		}
		
		if (Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))) {
			this.actionAddNew.setVisible(false);
		}
	}

	private void initF7() throws Exception {
		// 产品类型
		FilterInfo filterInfoProduct = new FilterInfo();
		filterInfoProduct.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		String queryInfoProduct = "com.kingdee.eas.fdc.sellhouse.app.ProductTypeForSHEQuery";
		SHEHelper.initF7(this.prmtProductType, queryInfoProduct,
				filterInfoProduct);
		// 建筑性质初始化
		FilterInfo filterInfoBuilding = new FilterInfo();
		filterInfoBuilding.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		String queryInfoBuilding = "com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyForSHEQuery";
		SHEHelper.initF7(this.prmtBuildingProperty, queryInfoBuilding,
				filterInfoBuilding);
		// 朝向
		FilterInfo filterInfoChao = new FilterInfo();
		filterInfoChao.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		String queryInfoChao = "com.kingdee.eas.fdc.sellhouse.app.DirectionForSHEQuery";
		SHEHelper.initF7(this.prmtOrientations, queryInfoChao, filterInfoChao);

		// 景观
		FilterInfo filterInfoJingGuan = new FilterInfo();
		filterInfoJingGuan.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoJingGuan.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_LANDSCAPE_ID,
						CompareType.EQUALS));
		if (this.editData.getSellProject() != null) {
			filterInfoJingGuan.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoJingGuan.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoJingGuan.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		filterInfoJingGuan.setMaskString("#0 and #1 and (#2 or #3)");

		String queryInfoJingGuan = "com.kingdee.eas.fdc.sellhouse.app.SignForSHEQuery";
		SHEHelper.initF7(this.prmtSign, queryInfoJingGuan, filterInfoJingGuan);

		// 户型
		FilterInfo filterInfoRoomModel = new FilterInfo();
		String queryInfoRoomModel = "com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery";
		SHEHelper.initF7(this.prmtRoomType, queryInfoRoomModel,
				filterInfoRoomModel);

		// 面积范围
		//FilterInfo filterInfoArea = new FilterInfo();
		FilterInfo filterInfoArea = SHEHelper.getDefaultFilterForTree();
		filterInfoArea.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoArea.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_AREA_SCOPE_ID,
						CompareType.EQUALS));
		
//		if(this.orgUnit!=null){
//			filterInfoArea.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		
		if (this.editData.getSellProject() != null) {
			filterInfoArea.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoArea.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoArea.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));
		
		filterInfoArea.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		
		String queryInfoArea = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForAreaQuery";
		SHEHelper.initF7(this.prmtAreaScope, queryInfoArea, filterInfoArea);

		// 楼层区间
		//FilterInfo filterInfoFloor = new FilterInfo();
		FilterInfo filterInfoFloor = SHEHelper.getDefaultFilterForTree();
		filterInfoFloor.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoFloor.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_FLOOR_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoFloor.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoFloor.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoFloor.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoFloor.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		filterInfoFloor.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		
		String queryInfoFloor = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForFloorQuery";
		SHEHelper.initF7(this.prmtFloorScope, queryInfoFloor, filterInfoFloor);

		// 总价范围
//		FilterInfo filterInfoTotal = new FilterInfo();
		FilterInfo filterInfoTotal = SHEHelper.getDefaultFilterForTree();
		filterInfoTotal.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoTotal.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_TOTALAMOUNT_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoTotal.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoTotal.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoTotal.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoTotal.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		filterInfoTotal.setMaskString("#0 and #1 and #2 and (#3 or #4)");
	
		String queryInfoTotal = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForTotalQuery";
		SHEHelper.initF7(this.prmtTotal, queryInfoTotal, filterInfoTotal);

		// 单价区间
		//FilterInfo filterInfoPrice = new FilterInfo();
		FilterInfo filterInfoPrice = SHEHelper.getDefaultFilterForTree();
		filterInfoPrice.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoPrice.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_PRICE_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoPrice.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoPrice.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoPrice.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoPrice.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		filterInfoPrice.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		

		String queryInfoPrice = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForPriceQuery";
		SHEHelper.initF7(this.prmtPriceScope, queryInfoPrice, filterInfoPrice);

		// 商机级别
		//FilterInfo filterInfoLevel = new FilterInfo();
		FilterInfo filterInfoLevel = SHEHelper.getDefaultFilterForTree();
		filterInfoLevel.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoLevel.getFilterItems().add(
				new FilterItemInfo("type.id",
						CRMConstants.COMMERCECHANCE_LEVEL_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoLevel.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoLevel.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoLevel.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoLevel.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		
		filterInfoLevel.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		
		String queryInfoLevel = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForSHEQuery";
		SHEHelper.initF7(this.prmtLevel, queryInfoLevel, filterInfoLevel);
		
		// 商机阶段
		//FilterInfo filterInfoState = new FilterInfo();
		FilterInfo filterInfoState = SHEHelper.getDefaultFilterForTree();
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoState.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//			filterInfoState.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",null,
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoState.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoState.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}
		
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));
		
		if(this.filterStr!=null && this.filterStr.length()>0){
			filterInfoState.getFilterItems().add(
					new FilterItemInfo("id", this.filterStr, CompareType.NOTINNER));
		}else{
			getfilterStr();
			filterInfoState.getFilterItems().add(
					new FilterItemInfo("id", this.filterStr, CompareType.NOTINNER));
		}
		
		filterInfoState.setMaskString("#0 and #1 and  #2 and (#3 or #4) and #5 ");
//		if(this.filterStr!=null && this.filterStr.length()>0){
//			//filterInfoState.setMaskString("#0 and #1 and (#2 or #3) and (#4 or #5) and #6");
//			filterInfoState.setMaskString("#0 and #1 and  #2 and (#3 or #4) and #5 ");
//		}else{
//			//filterInfoState.setMaskString("#0 and #1 and (#2 or #3) and (#4 or #5)");
//			filterInfoState.setMaskString("#0 and #1 and #2 and ( #3 or #4 ) and #5 ");
//		}
		

		String queryInfoState = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForStageQuery";
		SHEHelper.initF7(this.prmtCommerceChanceStage, queryInfoState,
				filterInfoState);

		// 置业目的
		//FilterInfo filterInfoMuDi = new FilterInfo();
		FilterInfo filterInfoMuDi = SHEHelper.getDefaultFilterForTree();
		filterInfoMuDi.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoMuDi.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_PURPOSE_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoMuDi.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoMuDi.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoMuDi.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoMuDi.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		
		filterInfoMuDi.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		
		String queryInfoMuDi = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForPurposeQuery";
		SHEHelper.initF7(this.prmtPurpose, queryInfoMuDi, filterInfoMuDi);

		// 商机产生的原因
		//FilterInfo filterInfoReason = new FilterInfo();
		FilterInfo filterInfoReason = SHEHelper.getDefaultFilterForTree();
		filterInfoReason.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));

		filterInfoReason.getFilterItems().add(
				new FilterItemInfo("type.id",
						CRMConstants.COMMERCECHANCE_REASON_ID,
						CompareType.EQUALS));

//		filterInfoReason.getFilterItems().add(
//				new FilterItemInfo("type.id",
//						CRMConstants.COMMERCECHANCE_REASON_ID,
//						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoReason.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//		}
		
		
		
		if (this.editData.getSellProject() != null) {
			filterInfoReason.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoReason.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterInfoReason.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

	
		filterInfoReason.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		
		String queryInfoReason = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForReasonQuery";
		SHEHelper.initF7(this.prmtCommerceChangeReason, queryInfoReason,
				filterInfoReason);
		
		// 商机终止原因

		FilterInfo filterStop = SHEHelper.getDefaultFilterForTree();
		filterStop.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterStop.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.COMMERCECHANCE_STOP_REASON,
						CompareType.EQUALS));
		if (this.editData.getSellProject() != null) {
			filterStop.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterStop.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}

		filterStop.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));

		filterStop.setMaskString("#0 and #1 and #2 and (#3 or #4)");
	
		String queryStopReason = "com.kingdee.eas.fdc.sellhouse.app.CommercechanceForStopReasonQuery";
		SHEHelper.initF7(this.prmtReason, queryStopReason, filterStop);
		
		//wyh 居住区域
		FilterInfo filterInfoStay = new FilterInfo();
		filterInfoStay.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE,CompareType.EQUALS));
		filterInfoStay.getFilterItems().add(new FilterItemInfo("group.name", "居住区域",CompareType.EQUALS));
		filterInfoStay.getFilterItems().add(new FilterItemInfo("project.id", editData.getSellProject().getId(),CompareType.EQUALS));
		filterInfoStay.getFilterItems().add(new FilterItemInfo("project.id", null,CompareType.EQUALS));
		filterInfoStay.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),CompareType.EQUALS));
		
		
		filterInfoStay.setMaskString("#0 and #1 and (#2 or (#3 and #4) )");
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filterInfoStay);
		prmtStayArea.setEntityViewInfo(evi);
		
		//wyh 工作区域
		FilterInfo filterInfoWork = new FilterInfo();
		filterInfoWork.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE,CompareType.EQUALS));
		filterInfoWork.getFilterItems().add(new FilterItemInfo("group.name", "工作区域",CompareType.EQUALS));
		filterInfoWork.getFilterItems().add(new FilterItemInfo("project.id", editData.getSellProject().getId(),CompareType.EQUALS));
		filterInfoWork.getFilterItems().add(new FilterItemInfo("project.id", null,CompareType.EQUALS));
		filterInfoWork.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),CompareType.EQUALS));
		
		
		filterInfoWork.setMaskString("#0 and #1 and (#2 or (#3 and #4) )");
		evi = new EntityViewInfo();
		evi.setFilter(filterInfoWork);
		prmtWorkArea.setEntityViewInfo(evi);
		
		
		evi= new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("statrusing",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		evi.setFilter(filter);
		
		HashMap ctx=new HashMap();
		ctx.put("EntityViewInfo", evi);
		ctx.put("EnableMultiSelection", new Boolean(false));
		ctx.put("HasCUDefaultFilter", new Boolean(true));
		ChannelTypeListUI ctlistUI=new ChannelTypeListUI();
		ctlistUI.setF7Use(true, ctx);
		ctlistUI.getUIToolBar().setVisible(false);
		ctlistUI.getUIMenuBar().setVisible(false);
		if((Window)SwingUtilities.getWindowAncestor(ctlistUI)!=null){
			((Window)SwingUtilities.getWindowAncestor(ctlistUI)).setAlwaysOnTop(true);
		}
		this.prmtClassify.setSelector(ctlistUI);
	}

	private void getfilterStr() {
		this.filterStr = "'"+CRMConstants.COMMERCECHANCE_STAGE_PAIHAO+"'" +
				","+"'"+CRMConstants.COMMERCECHANCE_STAGE_BOOKING+"'" +
						","+"'"+CRMConstants.COMMERCECHANCE_STAGE_PURCHASE+"'" +
								","+"'"+CRMConstants.COMMERCECHANCE_STAGE_SIGN+"'" +
										","+"'"+CRMConstants.COMMERCECHANCE_STAGE_QUITROOM+"'" +
												","+"'"+CRMConstants.COMMERCECHANCE_STAGE_CHANGENAME+"'";
	}
	
	private void initSaleMans() throws EASBizException, BOSException{
		
		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo  info = (SellProjectInfo)this.getUIContext().get("sellProject");
			EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(info,false);
			this.prmtSaleMan.setEntityViewInfo(view);	
	
		}
	
	}

	private void initF7Enable() {
		this.prmtCustomer.setEditable(false);
		this.prmtLevel.setEditable(false);
		this.prmtCommerceChanceStage.setEditable(false);
		this.prmtSaleMan.setEditable(false);
		this.prmtPurpose.setEditable(false);
		this.prmtCommerceChangeReason.setEditable(false);
		this.prmtProductType.setEditable(false);
		this.prmtSign.setEditable(false);
		this.prmtFloorScope.setEditable(false);
		this.prmtBuildingProperty.setEditable(false);
		this.prmtRoomType.setEditable(false);
		this.prmtTotal.setEditable(false);
		this.prmtAreaScope.setEditable(false);
		this.prmtPriceScope.setEditable(false);
		this.prmtOrientations.setEditable(false);

		this.prmtCustomer.setEnabled(false);
		this.prmtSaleMan.setEnabled(false);
		this.prmtCommerceChanceStage.setEnabled(false);
	}

	/**
	 * 判断传入的用户是否是当前组织的管控人员
	 * 
	 * @param user
	 */
	private boolean isMarketUnitControl(FullOrgUnitInfo orgUnitInfo,
			UserInfo user) {

		boolean isControl = false;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id",
						orgUnitInfo.getId().toString(), CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("controler.id", user.getId().toString(),
						CompareType.EQUALS));
		try {
			if (MarketUnitControlFactory.getRemoteInstance().exists(filter)) {
				isControl = true;
			}
		} catch (EASBizException e) {
			logger.error(e.getMessage() + "管控人员判断失败!");
		} catch (BOSException e) {
			logger.error(e.getMessage() + "管控人员判断失败!");
		}

		return isControl;
	}

	private boolean checkCommerceChance() {
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		if (user != null) {
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("saleMan.id", user.getId()
								.toString(), CompareType.EQUALS));
				if (this.sellProject != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", sellProject
									.getId().toString(), CompareType.EQUALS));
				}
				FullOrgUnitInfo info = SysContext.getSysContext()
						.getCurrentOrgUnit().castToFullOrgUnitInfo();
				if (info != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", info.getId()
									.toString(), CompareType.EQUALS));
				}
				if (this.prmtCustomer.getValue() != null) {
					SHECustomerInfo customer = (SHECustomerInfo) this.prmtCustomer
							.getValue();
					filter.getFilterItems().add(
							new FilterItemInfo("customer.id", customer.getId()
									.toString(), CompareType.EQUALS));
				}
				filter.getFilterItems().add(
						new FilterItemInfo("status",
								CommerceChangeNewStatusEnum.ACTIVE_VALUE,
								CompareType.EQUALS));
				boolean res = CommerceChanceFactory.getRemoteInstance().exists(
						filter);
				if (res) {

					return true;
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage() + "获得商机信息失败!");
				return false;
			}

		}

		return false;
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			this.alertMsg("请先提交!");
		}
		CommerceChangeNewStatusEnum status = this.editData.getStatus();
		checkStatus(status, "delete", this.editData.getId().toString());
		super.actionRemove_actionPerformed(e);
		this.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			this.alertMsg("请先提交!");
		}
		CommerceChangeNewStatusEnum status = this.editData.getStatus();
		checkStatus(status, "update", this.editData.getId().toString());

		super.actionEdit_actionPerformed(e);
		this.comboStatus.setEnabled(false);
		comboCommerceSrc.setEnabled(false);
		//this.txtStopReason.setEnabled(false);
		//this.prmtReason.setEnabled(false);
		this.txtReason.setEnabled(false);
		initF7Enable();

	}

	private void checkStatus(CommerceChangeNewStatusEnum status, String type,
			String id) {

		if (status.equals(CommerceChangeNewStatusEnum.CLOSE)) {
			if (type.equals("update")) {
				alertMsg("商机已经关闭，不能修改!");
			} else if (type.equals("delete")) {
				alertMsg("商机已经关闭，不能删除!");
			} else if (type.equals("track")) {
				alertMsg("商机已经关闭，不能新增跟进!");
			} else if (type.equals("toTransaction")) {
				alertMsg("商机已经关闭，不能转交易!");
			} else if (type.equals("share")) {
				alertMsg("商机已经关闭，不能共享!");
			} else if (type.equals("paper")) {

				alertMsg("商机已经关闭，不能填写问卷!");
			}
		} else if (status.equals(CommerceChangeNewStatusEnum.END)) {
			if (type.equals("update")) {
				alertMsg("商机已经终止，不能修改!");
			} else if (type.equals("delete")) {

				alertMsg("商机已经终止，不能删除!");
			} else if (type.equals("track")) {
				alertMsg("商机已经终止，不能新增跟进!");
			} else if (type.equals("toTransaction")) {
				alertMsg("商机已经终止，不能转交易!");
			} else if (type.equals("paper")) {
				alertMsg("商机已经终止，不能填写问卷!");
			}
		} else if (status.equals(CommerceChangeNewStatusEnum.TRANSACTION)) {
			if (type.equals("update")) {
				alertMsg("商机已经成交，不能修改!");
			} else if (type.equals("delete")) {
				alertMsg("商机已经成交，不能删除!");
			} else if (type.equals("track")) {
				alertMsg("商机已经成交，不能新增跟进!");
			} else if (type.equals("toTransaction")) {
				alertMsg("商机已经成交，不能转交易!");
			} else if (type.equals("paper")) {
				alertMsg("商机已经成交，不能填写问卷!");
			}
		} else if (status.equals(CommerceChangeNewStatusEnum.ACTIVE)) {
			if (type.equals("delete")) {
				try {
					CommerceChanceInfo res = CommerceChanceFactory
							.getRemoteInstance()
							.getCommerceChanceInfo(
									"select id,questionPaperAnser.id,questionPaperAnser.name,questionPaperAnser.number where id='"
											+ id + "'");
					if (res.getQuestionPaperAnser() != null
							&& res.getQuestionPaperAnser().size() > 0) {
						this.alertMsg("商机已经填写问卷,不能删除!");
					} else {
						boolean answ = CommerceChanceTrackFactory
								.getRemoteInstance().exists(
										"select id ,name where commerceChance.id='"
												+ id + "'");

						if (answ) {
							this.alertMsg("商机已经跟进,不能删除!");
						}
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void alertMsg(String msg) {
		FDCMsgBox.showWarning(this, msg);
		this.abort();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		super.actionAddNew_actionPerformed(e);
//		handleCodingRule();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
		this.comboStatus.setEnabled(false);
		comboCommerceSrc.setEnabled(false);
		this.comboStatus.setSelectedItem(CommerceChangeNewStatusEnum.ACTIVE);
		this.comboCommerceSrc.setSelectedItem(CommerceSrcEnum.ADDBYHAND);
		//this.txtStopReason.setEnabled(false);
		//this.prmtReason.setEnabled(false);
		this.txtReason.setEnabled(false);
		initF7Enable();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCustomer);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCommerceChanceStage);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSaleMan);
		//FDCClientVerifyHelper.verifyEmpty(this, this.prmtCommerceChangeReason);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkEffectiveDate);

		if(txtProbability.getBigDecimalValue()!=null){
			if(txtProbability.getBigDecimalValue().compareTo(FDCHelper.ZERO)<0
					|| txtProbability.getBigDecimalValue().compareTo(FDCHelper.ONE_HUNDRED)>0){
				FDCMsgBox.showWarning(this, "成交概率必须在0~100之间!");
				this.abort();
			}
		}
		
		FullOrgUnitInfo orgUnit = SysContext.getSysContext()
				.getCurrentOrgUnit().castToFullOrgUnitInfo();
		UserInfo user = (UserInfo) this.prmtSaleMan.getValue();
		if (orgUnit != null && user != null) {
			boolean isCon = isMarketUnitControl(orgUnit, user);
			if (isCon) {
				FDCMsgBox.showWarning(this, "营销管控人员不能新增商机!");
				this.abort();
			}
		}

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			if (checkCommerceChance()) {
				FDCMsgBox.showWarning(this, "此客户已经存在商机，不能新增!");
				this.abort();
			}
		}

		SHECustomerInfo customerInfo = (SHECustomerInfo)this.prmtCustomer.getValue();
		if(customerInfo.getClues() != null ){
			CluesManageInfo clues = null;
			if(customerInfo.getClues().getNumber()==null){
				try{
					clues = CluesManageFactory.getRemoteInstance().getCluesManageInfo(
							"select id,number,name where id='"
									+ customerInfo.getClues().getId().toString()
									+ "'");
				}catch(Exception ex){
					//有可能数据被删除,如果不做处理的话,这里会直接抛出"该记录不存在的提示",而不是把异常抛到上层
					logger.error(ex.getMessage());
				}
			}
			if(clues!=null){
				this.editData.setCluesCustomer(clues);
			}
			
		}

		//wyh
		if(this.getOprtState().equals(OprtState.ADDNEW)){
			if((this.tblTrack.getRowCount()==0) || (this.kdtQuestion.getRowCount() == 0)){
				FDCMsgBox.showWarning("跟进与问卷必须填写!");
				this.abort();
			}
		}
		
		if(this.prmtStayArea.getValue() == null){
			FDCMsgBox.showInfo("居住区域不能为空！");
			SysUtil.abort();
		}
		if(this.prmtWorkArea.getValue() == null){
			FDCMsgBox.showInfo("工作区域不能为空！");
			SysUtil.abort();
		}
		
		if(this.getUIContext().get("HashMap") != null){
			HashMap hp = (HashMap)this.getUIContext().get("HashMap");
			hp.put("CommerceChance", fillEditData());
			hp.put("roomCollection", fillRoomCollection());
			disposeUIWindow();
			return;
		}
		
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
		if(this.getOprtState().equals(OprtState.ADDNEW)){
			this.btnSubmit.setEnabled(false);
			this.btnEdit.setEnabled(true);
			isDirect = false;
		}
		this.comboStatus.setEnabled(false);
		comboCommerceSrc.setEnabled(false);
		this.comboStatus.setSelectedItem(CommerceChangeNewStatusEnum.ACTIVE);
		this.comboCommerceSrc.setSelectedItem(CommerceSrcEnum.ADDBYHAND);
		//this.txtStopReason.setEnabled(false);
		//this.prmtReason.setEnabled(false);
		this.txtReason.setEnabled(false);
		this.prmtReason.setValue(null);
		initF7Enable();
		
		if (Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))) {
			destroyWindow();
		}else{
//			this.actionAddNew_actionPerformed(e);
		}
		
	}

	//填充数据 wyh
	public CommerceChanceInfo fillEditData(){
		this.editData.setDescription(this.txtDescription.getText());
		this.editData.setNumber(this.txtNumber.getText());
		this.editData.setWorkArea((SHECusAssistantDataInfo)this.prmtWorkArea.getValue());
		this.editData.setStayArea((SHECusAssistantDataInfo)this.prmtStayArea.getValue());
		this.editData.setIntentionType((RoomModelInfo)this.prmtIntentionType.getValue());
		this.editData.setCommerceChanceStage((CommerceChanceAssistantInfo)this.prmtCommerceChanceStage.getValue());
		this.editData.setCommerceSrc((CommerceSrcEnum)this.comboCommerceSrc.getSelectedItem());
		this.editData.setStatus((CommerceChangeNewStatusEnum)this.comboStatus.getSelectedItem());
		this.editData.setName(this.txtName.getText());
		
		HashMap hp = (HashMap)this.getUIContext().get("HashMap");
		hp.put("question_num", Integer.valueOf(kdtQuestion.getRowCount()));
		hp.put("track_num", Integer.valueOf(tblTrack.getRowCount()));
		return (CommerceChanceInfo)editData;
	}
	
	public RoomCollection fillRoomCollection() throws EASBizException, BOSException{
		RoomCollection rc = new RoomCollection();
		for(int i=0;i<tblRoom.getRowCount();i++){
			IRow row = tblRoom.getRow(i);
			String roomId = row.getCell("id").getValue().toString();
			RoomInfo ri = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId));
			rc.add(ri);
		}
		return rc;
	}
	
	protected IObjectValue createNewData() {
		CommerceChanceInfo info = new CommerceChanceInfo();
		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo sell = (SellProjectInfo) this.getUIContext().get(
					"sellProject");
			info.setSellProject(sell);
			this.sellProject = sell;
		}
		info.setSysType(MoneySysTypeEnum.SalehouseSys);

		if (SysContext.getSysContext().getCurrentUserInfo() != null) {
			info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		}
		try {
			info.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
			info.setEffectiveDate(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			logger.error(e.getMessage() + "获得时间失败!");
		}

		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		if (user != null) {
			info.setSaleMan(user);
		}
//		info.setName("某某客户的销售机会");

		// add by youzhen,20110720 线索客户，客户台账
		if (this.getUIContext().get("cluesCustomer") != null) {
			CluesManageInfo cluesInfo = (CluesManageInfo) this.getUIContext()
					.get("cluesCustomer");
			info.setCluesCustomer(cluesInfo);
			// 根据cluesInfo查到转成的新客户资料
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			filter.getFilterItems().add(new FilterItemInfo("name", cluesInfo.getName()));
			view.setFilter(filter);
			SHECustomerCollection sheCustomerColl;
			try {
				sheCustomerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
				if (sheCustomerColl != null && sheCustomerColl.size() > 0) {
					info.setCustomer(sheCustomerColl.get(0));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if (this.getUIContext().get("sheCustomer") != null) {
			SHECustomerInfo custInfo = (SHECustomerInfo) this.getUIContext()
					.get("sheCustomer");
			info.setCustomer(custInfo);
			
			if(custInfo.getClues() != null ){
				info.setCluesCustomer(custInfo.getClues());
			}
			if(custInfo.getName()!=null){
				info.setName(custInfo.getName()+"的销售机会");
			}
		}

		info.setIsToPre(false);
		info.setIsToPur(false);
		info.setIsToSign(false);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		try {
			CommerceChanceAssistantInfo ccainfo = CommerceChanceAssistantFactory.getRemoteInstance().getCommerceChanceAssistantInfo("select * from where name='来电'");
			info.setCommerceChanceStage(ccainfo);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("reasonDesc"));
		sic.add(new SelectorItemInfo("stopReason"));
		sic.add(new SelectorItemInfo("changeRoom.room.id"));
		sic.add(new SelectorItemInfo("changeRoom.room.name"));
		sic.add(new SelectorItemInfo("changeRoom.room.number"));
		sic.add(new SelectorItemInfo("changeRoom.room.buildingArea"));
		sic.add(new SelectorItemInfo("changeRoom.room.roomArea"));
		sic.add(new SelectorItemInfo("changeRoom.room.standardTotalAmount"));
		sic.add(new SelectorItemInfo("changeRoom.room.roomModel.id"));
		sic.add(new SelectorItemInfo("changeRoom.room.roomModel.name"));
		sic.add(new SelectorItemInfo("changeRoom.room.roomModel.number"));
		sic.add(new SelectorItemInfo("changeRoom.head"));
		sic.add(new SelectorItemInfo("sellProject.id"));
		sic.add(new SelectorItemInfo("sellProject.name"));
		sic.add(new SelectorItemInfo("sellProject.number"));
		sic.add(new SelectorItemInfo("effectiveDate"));
		sic.add(new SelectorItemInfo("worth"));
		sic.add(new SelectorItemInfo("probability"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("status"));
		sic.add(new SelectorItemInfo("saleMan.id"));
		sic.add(new SelectorItemInfo("saleMan.name"));
		sic.add(new SelectorItemInfo("saleMan.number"));
		sic.add(new SelectorItemInfo("customer.id"));
		sic.add(new SelectorItemInfo("customer.name"));
		sic.add(new SelectorItemInfo("customer.clues.id"));
		sic.add(new SelectorItemInfo("customer.clues.number"));
		sic.add(new SelectorItemInfo("customer.clues.name"));
		sic.add(new SelectorItemInfo("newLevel.id"));
		sic.add(new SelectorItemInfo("newLevel.name"));
		sic.add(new SelectorItemInfo("newLevel.number"));
		sic.add(new SelectorItemInfo("commerceChanceStage.id"));
		sic.add(new SelectorItemInfo("commerceChanceStage.name"));
		sic.add(new SelectorItemInfo("commerceChanceStage.number"));
		sic.add(new SelectorItemInfo("purpose.id"));
		sic.add(new SelectorItemInfo("purpose.name"));
		sic.add(new SelectorItemInfo("purpose.number"));
		sic.add(new SelectorItemInfo("commerceChangeReason.id"));
		sic.add(new SelectorItemInfo("commerceChangeReason.name"));
		sic.add(new SelectorItemInfo("commerceChangeReason.number"));
		sic.add(new SelectorItemInfo("productType.id"));
		sic.add(new SelectorItemInfo("productType.name"));
		sic.add(new SelectorItemInfo("productType.number"));
		sic.add(new SelectorItemInfo("buildingProperty.id"));
		sic.add(new SelectorItemInfo("buildingProperty.name"));
		sic.add(new SelectorItemInfo("buildingProperty.number"));
		sic.add(new SelectorItemInfo("orientations.id"));
		sic.add(new SelectorItemInfo("orientations.name"));
		sic.add(new SelectorItemInfo("orientations.number"));
		sic.add(new SelectorItemInfo("sign.id"));
		sic.add(new SelectorItemInfo("sign.name"));
		sic.add(new SelectorItemInfo("sign.number"));
		sic.add(new SelectorItemInfo("roomType.id"));
		sic.add(new SelectorItemInfo("roomType.name"));
		sic.add(new SelectorItemInfo("roomType.number"));
		sic.add(new SelectorItemInfo("areaScope.id"));
		sic.add(new SelectorItemInfo("areaScope.name"));
		sic.add(new SelectorItemInfo("areaScope.number"));
		sic.add(new SelectorItemInfo("floorScope.id"));
		sic.add(new SelectorItemInfo("floorScope.name"));
		sic.add(new SelectorItemInfo("floorScope.number"));
		sic.add(new SelectorItemInfo("total.id"));
		sic.add(new SelectorItemInfo("total.name"));
		sic.add(new SelectorItemInfo("total.number"));
		sic.add(new SelectorItemInfo("priceScope.id"));
		sic.add(new SelectorItemInfo("priceScope.name"));
		sic.add(new SelectorItemInfo("priceScope.number"));
		sic.add(new SelectorItemInfo("cluesCustomer.id"));
		sic.add(new SelectorItemInfo("commerceSrc"));
		sic.add(new SelectorItemInfo("linkStopReason.id"));
		sic.add(new SelectorItemInfo("linkStopReason.name"));
		sic.add(new SelectorItemInfo("linkStopReason.number"));
		sic.add(new SelectorItemInfo("workArea.id"));
		sic.add(new SelectorItemInfo("workArea.name"));
		sic.add(new SelectorItemInfo("workArea.number"));
		sic.add(new SelectorItemInfo("stayArea.id"));
		sic.add(new SelectorItemInfo("stayArea.name"));
		sic.add(new SelectorItemInfo("stayArea.number"));
		sic.add(new SelectorItemInfo("intentionType.id"));
		sic.add(new SelectorItemInfo("intentionType.name"));
		sic.add(new SelectorItemInfo("intentionType.number"));
		sic.add("CU.*");
		sic.add("classify.*");
		sic.add("trackDate");
		return sic;
	}

	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRoom_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery")));
		EntityViewInfo view = new EntityViewInfo();
		// 过滤条件
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isForSHE", Boolean.TRUE)); // 房间的售楼属性
		filterItems.add(new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE)); // 项目的售楼属性
		if (this.editData.getSellProject() != null
				&& this.editData.getSellProject().getId() != null) {
			filterItems.add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,this.editData.getSellProject()),CompareType.INCLUDE));
		}
		view.setFilter(filter);
		/*SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("roomModel.id"));
		coll.add(new SelectorItemInfo("roomModel.name"));
		coll.add(new SelectorItemInfo("roomModel.number"));
		coll.add(new SelectorItemInfo("standardTotalAmount"));
		coll.add(new SelectorItemInfo("buildingArea"));
		coll.add(new SelectorItemInfo("roomArea"));
		view.setSelector(coll);*/
		dlg.setEntityViewInfo(view);
		// 设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();

//		this.tblRoom.removeRows();
		Set roomId=new HashSet();
		for(int i=0;i<this.tblRoom.getRowCount();i++){
			if(this.tblRoom.getRow(i).getCell("id").getValue()!=null){
				roomId.add(this.tblRoom.getRow(i).getCell("id").getValue().toString());
			}
		}
		if (object != null) {
			for (int i = 0; i < object.length; i++) {
				RoomInfo room = (RoomInfo) object[i];
				if(roomId.contains(room.getId().toString())){
					continue;
				}
				addNewRoom(room);
			}
		}
	}

	private void addNewRoom(RoomInfo room) {
		if (room == null) {
			return;
		}
		this.tblRoom.checkParsed();
		initRoomTable();
		int activeRowIndex = this.tblRoom.getSelectManager()
				.getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblRoom.addRow();
		} else {
			row = this.tblRoom.addRow(activeRowIndex + 1);
		}

		showLoanRoom(row, room);
	}

	private void initRoomTable() {
		if (this.tblRoom.getColumn("roomArea") != null) {
			this.tblRoom.getColumn("roomArea").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("buildArea") != null) {
			this.tblRoom.getColumn("buildArea").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("standardPrice") != null) {
			this.tblRoom.getColumn("standardPrice").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
	}

	private void showLoanRoom(IRow row, RoomInfo room) {

		if (room != null) {
			row.setUserObject(room);
			if (room.getId() != null) {
				row.getCell("id").setValue(room.getId());
			}
			if (room.getName() != null) {
				row.getCell("name").setValue(room.getName());
			}
			if (room.getRoomModel() != null) {
				RoomModelInfo model = room.getRoomModel();
				if (room.getRoomModel().getId() != null
						&& room.getRoomModel().getName() == null) {
					try {
						model = RoomModelFactory.getRemoteInstance().getRoomModelInfo(
								"select id,name,number where id='"
										+ room.getRoomModel().getId().toString()
										+ "'");
					} catch (EASBizException e) {
						logger.error(e.getMessage()+"获得户型失败!");
					} catch (BOSException e) {
						logger.error(e.getMessage()+"获得户型失败!");
					}
				}
				row.getCell("roomModel").setValue(model);
			}
			if (room.getStandardTotalAmount() != null) {
				row.getCell("standardPrice").setValue(
						room.getStandardTotalAmount());
			}
			if (room.getBuildingArea() != null) {
				row.getCell("buildArea").setValue(room.getBuildingArea());
			}
			if (room.getRoomArea() != null) {
				row.getCell("roomArea").setValue(room.getRoomArea());
			}
		}
	}

	public void actionAddCustomer_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddCustomer_actionPerformed(e);
		FDCMsgBox.showWarning(this, "販売顧客は新規顧客、準備ができていないではありません！");
	}

	protected void btnDelRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelRoom_actionPerformed(e);
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblRoom);
		if (selectRows == null || selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			SysUtil.abort();
		}
		for (int i = 0; i < selectRows.length; i++) {
			int select = selectRows[i];
			IRow row = this.tblRoom.getRow(select);
			if (row == null) {
				continue;
			}
			if (row.getCell("id").getValue() != null) {
				this.tblRoom.removeRow(select);
			}
		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			CommerceChanceDataProvider data = new CommerceChanceDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.CommerceChanceForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/customerManage", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrint_actionPerformed(e);
		}
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			CommerceChanceDataProvider data = new CommerceChanceDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.CommerceChanceForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/sellhouse/customerManage", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrintPreview_actionPerformed(e);
		}
	}

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		CommerceChanceInfo pur = new CommerceChanceInfo();
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(pur, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(pur, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(pur,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(pur, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(pur, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(pur, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								pur, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(pur, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}

	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						new CommerceChanceInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}

	protected void initOldData(IObjectValue dataObject) {
		// super.initOldData(dataObject);
	}
	
	/**
	 * 查询满足条件的记录 当前置业顾问、所选项目、共享到当前置业顾问、共享到当前项目
	 * 
	 * @param sellProjectInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Set getCustomerId(SellProjectInfo sellProjectInfo)
			throws BOSException, EASBizException {
		Set rs = new HashSet();
		Set propertyConsultantSet = this.getUserIdSet();//登录人能看到的id集合
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		//共享置业顾问是当前置业顾问的
		filter.getFilterItems().add(new FilterItemInfo("user.id", propertyConsultantSet, CompareType.INCLUDE));
		view.setFilter(filter);
		SharePropertyCollection shareProColl = SharePropertyFactory.getRemoteInstance().getSharePropertyCollection(view);
		Set propertyCustomerSet = new HashSet();
		if(shareProColl != null && shareProColl.size() > 0){
			for(int i = 0;i<shareProColl.size();i++){
				SharePropertyInfo  info = (SharePropertyInfo)shareProColl.get(i);
				if(info.getCustomer() != null){
					propertyCustomerSet.add(info.getCustomer().getId());
				}
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", propertyCustomerSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
		view.setFilter(filter);
		SHECustomerCollection colls = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		if(colls != null && colls.size() > 0){
			for(int i = 0;i<colls.size();i++){
				rs.add(colls.get(i).getId().toString());
			}
		}
		
		
		//共享项目的
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
		view.setFilter(filter);
		ShareSellProjectCollection projectColl = ShareSellProjectFactory.getRemoteInstance().getShareSellProjectCollection(view);
		Set projectCustomerSet = new HashSet();
		if(projectColl != null && projectColl.size() > 0){
			for(int i = 0;i<projectColl.size();i++){
				ShareSellProjectInfo info = (ShareSellProjectInfo)projectColl.get(i);
				if(info.getCustomer() != null){
				    projectCustomerSet.add(info.getCustomer().getId().toString());
				}
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", projectCustomerSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultantSet, CompareType.INCLUDE));
		view.setFilter(filter);
		SHECustomerCollection coll = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		if(projectColl != null && coll.size() > 0){
			for(int i = 0;i<coll.size();i++){
				rs.add(coll.get(i).getId().toString());
			}
		}
		
		//当前置业顾问，当前项目的
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultantSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
		view.setFilter(filter);
		SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		if(customerColl != null && customerColl.size() > 0){
			for(int i = 0;i<customerColl.size();i++){
				rs.add(customerColl.get(i).getId().toString());
			}
		}
		return rs;
	}
	
	/**
	 * 满足条件的置业顾问集合
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Set getUserIdSet()throws BOSException, EASBizException{
		Set idSet = new HashSet();
		EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(editData.getSellProject(),null);
		UserCollection userColl = UserFactory.getRemoteInstance().getUserCollection(view);
		if(userColl != null && userColl.size() > 0){
			for(int i=0;i<userColl.size();i++){
				idSet.add(userColl.get(i).getId());
			}
		}
		//添加自己
		UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
		idSet.add(currentInfo.getId());
		return idSet;
	}

	/**
	 * 新增问卷
	 * wyh
	 * @throws Exception
	 */
	protected void btnAddQuestion_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddQuestion_actionPerformed(e);
		showEditUI(QuestionPaperAnswerEditUI.class.getName());
	}

	/**
	 * 新增跟进记录
	 * wyh
	 * @throws Exception
	 */
	protected void btnAddTrack_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddTrack_actionPerformed(e);
		showEditUI(CommerceChanceTrackEditUI.class.getName());
	}
	
	/**
	 * 打开各功能新增界面,供各功能调用
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void showEditUI(String name) throws Exception {
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		HashMap hp = (HashMap)this.getUIContext().get("HashMap");
		//设置已有条数
		if(hp != null){
			hp.put("question_num", Integer.valueOf(kdtQuestion.getRowCount()));
			hp.put("track_num", Integer.valueOf(tblTrack.getRowCount()));
		}
		if(editData.getId() != null){
			CommerceChanceInfo ccni = (CommerceChanceInfo)editData;
			this.getUIContext().put("commerceChance", ccni);
			this.getUIContext().put("SelectCommerceChance", ccni);
		}
		else{
			CommerceChanceInfo ccni = new CommerceChanceInfo();
			BOSUuid bosid = BOSUuid.create(ccni.getBOSType());
			editData.setId(bosid);
			this.getUIContext().put("commerceChance", (CommerceChanceInfo)editData);
			this.getUIContext().put("SelectCommerceChance", (CommerceChanceInfo)editData);
		}
		this.getUIContext().put("commerceChanceStage", this.prmtCommerceChanceStage.getValue());
		this.getUIContext().put("sellProject", editData.getSellProject());
		if(this.getUIContext().get("customerId") != null){
			this.getUIContext().put("sheCustomer", (SHECustomerInfo)this.getUIContext().get("customerId"));
		}
		else{
			this.getUIContext().put("sheCustomer", (SHECustomerInfo)this.prmtCustomer.getValue());
		}
		this.getUIContext().put("prmtLevel", this.prmtLevel);
		this.getUIContext().put("prmtClassify", this.prmtClassify);
		this.getUIContext().put("prmtCommerceChanceStage", this.prmtCommerceChanceStage);
		IUIWindow curDialog = uiFactory.create(name, this.getUIContext(), null,OprtState.ADDNEW);
		((Window)curDialog).setAlwaysOnTop(true);
		curDialog.show();
		//获取返回值，更新记录表
		fillTrackTbl();
		fillQuestionTbl();
		hp = (HashMap)this.getUIContext().get("HashMap");
		//设置已有条数
		if(hp != null){
		//最近一次的商机跟进级别的值
			if(hp.get("lastTrackChanceLevel") != null){
				this.prmtLevel.setValue(hp.get("lastTrackChanceLevel"));
				editData.setNewLevel((CommerceChanceAssistantInfo)hp.get("lastTrackChanceLevel"));
				
				this.prmtClassify.setValue(hp.get("lastClassify"));
				editData.setClassify((ChannelTypeInfo)hp.get("lastClassify"));
				
				this.prmtCommerceChanceStage.setValue(hp.get("lastCommerceChanceStage"));
				editData.setCommerceChanceStage((CommerceChanceAssistantInfo)hp.get("lastCommerceChanceStage"));
				
				this.editData.setTrackDate((Date) hp.get("trackDate"));
			}
		}
	}
	
	//填充跟进表
	public void fillTrackTbl() throws BOSException{
		this.tblTrack.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("saleMan.name");
		sic.add("interactionType");
		sic.add("trackContent");
		sic.add("number");
		sic.add("trackDate");
		sic.add("commerceChanceStage.name");
		sic.add("classify.name");
		FilterInfo filter = new FilterInfo();
		if (editData.getId() != null && !editData.getId().equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("commerceChance.id", this.editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);
			SorterItemCollection col = new SorterItemCollection();
			col.add(new SorterItemInfo("number"));
			view.setSorter(col);
			CommerceChanceTrackCollection coll = CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection(view);
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					CommerceChanceTrackInfo info = coll.get(i);
					this.trackValue(info);
				}
			}
			else{
				if(this.getUIContext().get("HashMap") != null){
					HashMap hp = (HashMap)this.getUIContext().get("HashMap");
					if(hp.get("track_num")!=null){
						int num = Integer.parseInt(hp.get("track_num").toString());
						for(int i=0;i<=num;i++){
							String key = "track_"+i;
							CommerceChanceTrackInfo info = (CommerceChanceTrackInfo)hp.get(key);
							this.trackValue(info);
						}
					}
				}
			}
		}
	}
	
	private void trackValue(CommerceChanceTrackInfo info){
		tblTrack.checkParsed();
		tblTrack.setEditable(false);
		tblTrack.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (info != null) {
			IRow addRow = tblTrack.addRow();
			addRow.getCell("id").setValue(info.getId());
			if (info.getNumber() != null) {
				addRow.getCell("trackNumber").setValue(info.getNumber());
			} else {
				addRow.getCell("trackNumber").setValue("");
			}
			if (info.getTrackDate() != null) {
				addRow.getCell("trackDate").setValue(info.getTrackDate());
			} else {
				addRow.getCell("trackDate").setValue("");
			}
			if (info.getSaleMan() != null) {
				addRow.getCell("saleMan").setValue(info.getSaleMan().getName());
			} else {
				addRow.getCell("saleMan").setValue("");
			}
			if (info.getInteractionType() != null) {
				addRow.getCell("trackType").setValue(info.getInteractionType());
			} else {
				addRow.getCell("trackType").setValue("");
			}
			if (info.getCommerceChanceStage() != null) {
				addRow.getCell("trackState").setValue(info.getCommerceChanceStage().getName());
			} else {
				addRow.getCell("trackState").setValue("");
			}
			if (info.getTrackContent() != null) {
				addRow.getCell("trackContext").setValue(info.getTrackContent());
			} else {
				addRow.getCell("trackContext").setValue("");
			}
			if (info.getClassify() != null) {
				addRow.getCell("trackMediaChannel").setValue(info.getClassify().getName());
			} else {
				addRow.getCell("trackMediaChannel").setValue("");
			}
		}
	}
	
	//填充问卷表
	public void fillQuestionTbl() throws BOSException{
		this.kdtQuestion.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("questionPaper.topric");
		sic.add("sheCustomer.name");
		sic.add("inputDate");
		sic.add("number");
		sic.add("trackDate");
		sic.add("description");
		FilterInfo filter = new FilterInfo();
		if (editData.getId() != null && !editData.getId().equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("commerceChance.id", this.editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);
			SorterItemCollection col = new SorterItemCollection();
			col.add(new SorterItemInfo("number"));
			view.setSorter(col);
			QuestionPaperAnswerCollection coll = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(view);
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					QuestionPaperAnswerInfo info = coll.get(i);
					this.questionValue(info);
				}
			}
			else{
				if(this.getUIContext().get("HashMap") != null){
					HashMap hp = (HashMap)this.getUIContext().get("HashMap");
					if(hp.get("question_num")!=null){
						int num = Integer.parseInt(hp.get("question_num").toString());
						for(int i=0;i<=num;i++){
							String key = "question_"+i;
							QuestionPaperAnswerInfo info = (QuestionPaperAnswerInfo)hp.get(key);
							this.questionValue(info);
						}
					}
				}
			}
		}
	}
	
	private void questionValue(QuestionPaperAnswerInfo info){
		kdtQuestion.checkParsed();
		kdtQuestion.setEditable(false);
		kdtQuestion.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (info != null) {
			IRow addRow = kdtQuestion.addRow();
			addRow.getCell("id").setValue(info.getId());
			if (info.getNumber() != null) {
				addRow.getCell("quesNumber").setValue(info.getNumber());
			} else {
				addRow.getCell("quesNumber").setValue("");
			}
			if (info.getQuestionPaper() != null) {
				addRow.getCell("quesChoose").setValue(info.getQuestionPaper().getTopric());
			} else {
				addRow.getCell("quesChoose").setValue("");
			}
			if (info.getInputDate() != null) {
				addRow.getCell("quesDate").setValue(info.getInputDate());
			} else {
				addRow.getCell("quesDate").setValue("");
			}
			if (info.getSheCustomer() != null) {
				addRow.getCell("quesPerson").setValue(info.getSheCustomer().getName());
			} else {
				addRow.getCell("quesPerson").setValue("");
			}
			if (info.getDescription() != null) {
				addRow.getCell("quesRemark").setValue(info.getDescription());
			} else {
				addRow.getCell("quesRemark").setValue("");
			}
		}
	}
	/**
	 * 过滤意向户型
	 * */
	protected void prmtIntentionType_willCommit(CommitEvent e) throws Exception {
		super.prmtIntentionType_willCommit(e);
		setIntentionType();
	}

	protected void prmtIntentionType_willShow(SelectorEvent e) throws Exception {
		super.prmtIntentionType_willShow(e);
		setIntentionType();
	}
	
	public void setIntentionType(){
		//过滤意向户型
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", editData.getSellProject().getId(), CompareType.EQUALS));
		evi.setFilter(filterInfo);
		
		prmtIntentionType.setEntityViewInfo(evi);
		prmtIntentionType.getQueryAgent().resetRuntimeEntityView();
	}

	public boolean destroyWindow() {
		//如果新增商机不保存,则将之前产生的跟进与问卷全部删除
		if(isDirect){
			if((kdtQuestion.getRowCount()>0) || (tblTrack.getRowCount()>0)){
				if(!MsgBox.isYes(MsgBox.showConfirm2(this,"是否删除对应的跟进与问卷?"))){
					SysUtil.abort();
				}
				try {
					//删除跟进
					CommerceChanceTrackFactory.getRemoteInstance().delete("where commerceChance.id = '"+editData.getId()+"'");
					//删除问卷
					QuestionPaperAnswerFactory.getRemoteInstance().delete("where commerceChance.id = '"+editData.getId()+"'");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			this.getUIContext().put("commerceChance", editData.getId());
		}
		return super.destroyWindow();
	}

	protected void kdtQuestion_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = kdtQuestion.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				ObjectUuidPK pk = new ObjectUuidPK(idStr);
				IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(), pk);
				String uiClassName = "";
				if (objectValue instanceof QuestionPaperAnswerInfo) {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.QuestionPaperAnswerEditUI";
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}
		}
	}

	protected void tblTrack_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = tblTrack.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				ObjectUuidPK pk = new ObjectUuidPK(idStr);
				IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(), pk);
				String uiClassName = "";
				if (objectValue instanceof CommerceChanceTrackInfo) {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.CommerceChanceTrackEditUI";
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, OprtState.VIEW);
					uiWindow.show();
					
					syncDataFromDB();
					handleOldData();
				}
			}
		}
	}
	 protected void syncDataFromDB()throws Exception{
	     if(getUIContext().get("ID") == null)
	     {
	         String s = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_IDIsNull");
	         MsgBox.showError(s);
	         SysUtil.abort();
	     }
	     com.kingdee.bos.dao.IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get("ID").toString()));
	     setDataObject(getValue(pk));
	     loadFields();
	 }
	 protected void handleOldData(){
        if(getOprtState() != "FINDVIEW" && getOprtState() != "VIEW")
            FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
                public void run()
                {
                    storeFields();
                    initOldData(editData);
                }
            }
        );
     }
}