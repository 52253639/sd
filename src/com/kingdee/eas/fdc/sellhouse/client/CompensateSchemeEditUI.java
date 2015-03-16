/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.CalcWayEnum;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DecimalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CompensateSchemeEditUI extends AbstractCompensateSchemeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CompensateSchemeEditUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public CompensateSchemeEditUI() throws Exception {
		super();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		
		if(this.editData.getOrgUnit()==null) {
			this.editData.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		}
	}
	
	private void checkDumpName() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData.getSellProject();
		if (info == null) {
			return;
		}
		
		String number = this.txtNumber.getText();
	
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null,
								CompareType.EQUALS));
			}
			if (CompensateSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("number", number));
		
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", null,
									CompareType.EQUALS));
				}
			
				if (CompensateSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下编码不能重复！");
					SysUtil.abort();
				}
			}
	
		}
		String name = this.txtName.getText().toString();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null,
								CompareType.EQUALS));
			}
			if (CompensateSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", null,
									CompareType.EQUALS));
				}
				
				if (CompensateSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}

	protected IObjectValue createNewData() {
		CompensateSchemeInfo objectValue = new CompensateSchemeInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		objectValue.setSellProject(sellProject);
		objectValue.setCalcWay(CalcWayEnum.SUPREME);
		objectValue.setCompensateType(CompensateTypeEnum.ROOMAREA);
		
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		objectValue.setBookedDate(new Date());
		objectValue.setDecimalCount(2);
		objectValue.setDecimalType(DecimalTypeEnum.ROUND_HALF_UP);
		
		objectValue.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime((new Timestamp(new Date().getTime())));

		return objectValue;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("isEnabled");
		selectors.add(new SelectorItemInfo("sellProject.id"));
		selectors.add(new SelectorItemInfo("sellProject.number"));
		selectors.add(new SelectorItemInfo("sellProject.name"));
		return selectors;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CompensateSchemeFactory.getRemoteInstance();
	}

	public static String getRes(String resName) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.sellhouse.SellHouseResource", resName);
	}

	private void initUI() throws Exception {
		// 分录增、删按钮调整至分录上方
		JButton btnAddRuleNew = ctnEntry.add(actionAddLine);
		JButton btnEditRuleNew = ctnEntry.add(actionInsertLine);
		JButton btnDelRuleNew = ctnEntry.add(actionRemoveLine);
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddRuleNew.setToolTipText(getRes("sell004"));
		btnAddRuleNew.setText(getRes("sell004"));
		btnAddRuleNew.setSize(22, 19);
		btnEditRuleNew.setIcon(EASResource.getIcon("imgTbtn_edit"));
		btnEditRuleNew.setToolTipText(getRes("sell005"));
		btnEditRuleNew.setText(getRes("sell005"));
		btnEditRuleNew.setSize(22, 19);
		btnDelRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelRuleNew.setToolTipText(getRes("sell006"));
		btnDelRuleNew.setText(getRes("sell006"));
		btnDelRuleNew.setSize(22, 19);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUI();
		
		this.txtDecimalCount.setNegatived(false);
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionAttachment, actionPrint, actionPrintPreview,
				actionWorkFlowG, actionCopy, actionPre, actionNext,
				actionFirst, actionLast, actionTraceDown, actionTraceUp,
				actionCreateFrom, actionSubmit,actionMultiapprove,
				actionNextPerson,actionAuditResult}, false);
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionAddLine,
					actionRemoveLine, actionInsertLine }, false);
		}
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnInsertLine.setVisible(false);

		if (editData != null && editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("scheme.id", editData.getId().toString()));
			if (RoomAreaCompensateFactory.getRemoteInstance().exists(filter)) {
				FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionEdit }, false);
			}
		}
		
		handleCodingRule();
		
		if(OprtState.VIEW.equals(getOprtState())){
			this.actionRemove.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
	}

	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		CompensateSchemeEntryInfo objectValue = new CompensateSchemeEntryInfo();
		return objectValue;
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		int seq = getDetailTable().getRowCount() + 1;
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		String number = txtNumber.getText();
		String name = txtName.getText();
		CompensateSchemeEntryCollection coll = new CompensateSchemeEntryCollection();
		for (int i = 0, size = getDetailTable().getRowCount(); i < size; i++) {
			coll.add((CompensateSchemeEntryInfo) getDetailTable().getRow(i)
					.getUserObject());
		}
		if (coll != null && coll.size() > 0)
			uiContext.put("compareColl", coll);
		uiContext.put("seq", Integer.toString(seq));
		uiContext.put("sellProject", sellProject);
		uiContext.put("number", number);
		uiContext.put("name", name);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CompensateSchemeEntryEditUI.class.getName(), uiContext,
						null, "ADDNEW");
		uiWindow.show();
		if (((CompensateSchemeEntryEditUI) uiWindow.getUIObject()).isOk()) {
			CompensateSchemeEntryInfo entry = ((CompensateSchemeEntryEditUI) uiWindow
					.getUIObject()).getSchemeEntry();
			addEntry(entry);
		}
	}

	protected IRow addEntry(IObjectValue detailData) {
		/*
		 * if(table == null) { return; } IObjectValue detailData =
		 * createNewDetailData(table);
		 */

		IRow row = kdtEntrys.addRow();
		((CompensateSchemeEntryInfo) detailData).setSeq(row.getRowIndex() + 1);
		loadLineFields(kdtEntrys, row, detailData);
		afterAddLine(kdtEntrys, detailData);

		return row;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if(this.txtNumber.isEditable()){
			if(this.txtNumber.getText() == null || this.txtNumber.getText().equals("")){
				MsgBox.showError(this, "编码不能为空！");
				SysUtil.abort();
			}
			else if(this.txtNumber.getText().length() > 100){
				MsgBox.showError(this, "编码长度不能超过100个字符！");
				SysUtil.abort();
			}
		}
		
		if(this.txtName.getText() == null || this.txtName.getText().equals("")){
			MsgBox.showError(this, "名称不能为空！");
			SysUtil.abort();
		}
		else if(this.txtName.getText().length() > 80){
			MsgBox.showError(this, "名称长度不能超过80个字符！");
			SysUtil.abort();
		}
		
		if (getDetailTable().getRowCount() < 1) {
			MsgBox.showError(this, "方案明细不能为空！");
			SysUtil.abort();
		}
		
		super.verifyInput(e);
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		if ((getDetailTable().getSelectManager().size() == 0)
				|| isTableColumnSelected(getDetailTable())) {
			MsgBox.showInfo(this, "没有选中分录，无法修改！");

			return;
		}
		KDTSelectManager selectManager = getDetailTable().getSelectManager();
		int size = selectManager.size();
		KDTSelectBlock selectBlock = null;
		// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
		String selectId = null;
		CompensateSchemeEntryInfo editEntry = null;
		int top = -1;
		for (int blockIndex = 0; blockIndex < size; blockIndex++) {
			selectBlock = selectManager.get(blockIndex);
			top = selectBlock.getBeginRow();
			if (getDetailTable().getRow(top) == null) {
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_NoneEntry"));
				return;
			} else if (getDetailTable().getCell(top, "id").getValue() != null) {
				selectId = getDetailTable().getCell(top, "id").getValue()
						.toString();
				editEntry = (CompensateSchemeEntryInfo) getDetailTable()
						.getRow(top).getUserObject();
			}
		}

		// getDetailTable().getSelectManager().get(0);
		UIContext uiText = new UIContext(this);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		String number = txtNumber.getText();
		String name = txtName.getText();
		uiText.put("sellProject", sellProject);
		uiText.put("number", number);
		uiText.put("name", name);
		CompensateSchemeEntryCollection coll = new CompensateSchemeEntryCollection();
		for (int i = 0, entrySize = getDetailTable().getRowCount(); i < entrySize; i++) {
			if (i != top)
				coll.add((CompensateSchemeEntryInfo) (getDetailTable()
						.getRow(i).getUserObject()));
		}
		if (coll != null && coll.size() > 0)
			uiText.put("compareColl", coll);
		if (selectId != null)
			uiText.put(UIContext.ID, selectId);
		if (editEntry != null)
			uiText.put(UIContext.INIT_DATAOBJECT, editEntry);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CompensateSchemeEntryEditUI.class.getName(), uiText,
						null, "EDIT");
		uiWindow.show();
		if (((CompensateSchemeEntryEditUI) uiWindow.getUIObject()).isOk()) {
			CompensateSchemeEntryInfo entry = ((CompensateSchemeEntryEditUI) uiWindow
					.getUIObject()).getSchemeEntry();
			IRow row = getDetailTable().getRow(top);
			row.setUserObject(entry);
			loadLineFields(kdtEntrys, row, entry);
		}
	}

	protected String getSelectedCostBillID() {
		// return super.getSelectedKeyValue();

		int selectIndex = -1;

		// KDTSelectBlock selectBlock = tblMain.getSelectManager().get();
		int[] selectRows = KDTableUtil.getSelectedRows(getDetailTable());

		// if (selectBlock != null) {
		if (selectRows.length > 0) {
			// int rowIndex = selectBlock.getTop();
			int rowIndex = selectRows[0];
			IRow row = getDetailTable().getRow(rowIndex);
			if (row == null) {
				return null;
			}

			selectIndex = rowIndex;
			// ICell cell = row.getCell(getKeyFieldName());
			ICell cell = row.getCell("id");

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	protected void getAttachMentItem(KDTable table) {
	}

	public KDMenuItem getAttachMenuItem(KDTable table) {
		return null;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnable();
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
		FDCClientHelper.setActionEnable(actionAttachment, false);
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   	
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception{
    	checkDumpName();
    	super.actionSave_actionPerformed(e);
    	if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
    }
    /**
     * @author xiaoao_liu
     * @date 2010-10-25
     * @description 重写父类，设置附件管理不可见
     */
    
    protected boolean isShowAttachmentAction()
    {
        return false;
    }
    /**
     * @author tim_gao
     * @date 2010-10-25 
     */
    protected void initOldData(IObjectValue dataObject)
    {
    	super.initOldData(dataObject);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	checkDumpName();
    	super.actionSubmit_actionPerformed(e);
    	if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
    }
    
    protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		IObjectValue objValue = this.editData;;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(objValue, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
							prepareNumber(objValue, number);
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
    
    public void setNumberTextEnabled(){
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	
	 //通过编码规则获取编码。子类可用。
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              当前用户所属组织不存在时，缺省实现是用集团的
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // 启用了断号支持功能,同时启用了用户选择断号功能
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // 没有启用断号支持功能，此时获取了编码规则产生的编码
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //如果启用了用户可修改
                    setNumberTextEnabled();
                }
                return;
            }
           
        } catch (Exception err) {
            //获取编码规则出错，现可以手工输入编码！
            handleCodingRuleError(err);

            //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
            setNumberTextEnabled();
        }

        //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
        setNumberTextEnabled();
    }

    protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	checkIsEnable();
    	checkBeforeEdit();
    	super.actionRemove_actionPerformed(e);
    }
    
    protected void checkIsEnable() {
		boolean isEnabled = this.editData.isIsEnabled();
		if(isEnabled) {
			MsgBox.showWarning("所选项为启用状态，不能执行此操作！");
			abort();
		}
	}
    
	private void checkBeforeEdit() throws SellHouseException, EASBizException, BOSException {
		String id = editData.getId().toString();
		if(id != null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("scheme.id", id));
			if (RoomAreaCompensateFactory.getRemoteInstance().exists(filter)) {
				throw new SellHouseException(SellHouseException.USEDBYCOMPENSATE);
			}
		}
	}

}