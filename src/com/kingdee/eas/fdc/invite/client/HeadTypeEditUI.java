/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.IHeadColumn;
import com.kingdee.eas.fdc.invite.IHeadType;
import com.kingdee.eas.fdc.invite.INewListTempletColumn;
import com.kingdee.eas.fdc.invite.INewListTempletPage;
import com.kingdee.eas.fdc.invite.INewListingColumn;
import com.kingdee.eas.fdc.invite.INewListingPage;
import com.kingdee.eas.fdc.invite.IRefPrice;
import com.kingdee.eas.fdc.invite.IRefPriceEntry;
import com.kingdee.eas.fdc.invite.NewListTempletColumnFactory;
import com.kingdee.eas.fdc.invite.NewListTempletPageFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.RefPriceEntryFactory;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class HeadTypeEditUI extends AbstractHeadTypeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(HeadTypeEditUI.class);

	private boolean fUsed = false;

	private Map colIsUsedMap = null;

	/**
	 * output class constructor
	 */
	public HeadTypeEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		beforeStoreFields();
		super.storeFields();
	}

	protected void treeColumn_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.treeColumn_valueChanged(e);
		if (editData == null || !editData.isIsLeaf()) {
			return;
		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo col = (HeadColumnInfo) node.getUserObject();
			if (col == null) {
				this.txtHeadName.setText(null);
				return;
			}
			this.txtHeadName.setText(col.getName());
			txtHeadDes.setText(col.getDescription());
			//20101013 zhougang
			ckcIsQuoting.setSelected(col.isIsQuoting());
//			if(!chkIsDefined.isSelected()){
//				ckcIsQuoting.setSelected(col.isIsQuoting());
//			}else{
//				ckcIsQuoting.setSelected(true);
//			}
			//20101013 zhougang
			comboColumnType.setSelectedItem(col.getColumnType());
			// this.comboType.setSelectedIndex(col.getColumnType());
			// 如果是查看页面，直接返回，无须修改界面上各个按钮的状态
			if(STATUS_VIEW.equals(getOprtState())){
				return;
			}
			this.txtHeadName.setEnabled(true);
			this.txtHeadName.requestFocus();
			// this.comboType.setEnabled(true);
			if (col.isIsHasChild()) {
				// this.txtHeadName.setEnabled(true);
				// this.comboType.setEnabled(true);
				if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
						.getCurrentCtrlUnit().getId().toString())) {
					if (!STATUS_VIEW.equals(getOprtState())) {
						if(STATUS_ADDNEW.equals(getOprtState()))
							actionAddLine.setEnabled(true);
						else
							actionAddLine.setEnabled(checkHeadType(node));
						actionRemoveLine.setEnabled(true);
					}
				}
			} else {
				// this.txtHeadName.setEnabled(false);
				// this.comboType.setEnabled(false);
				actionAddLine.setEnabled(false);
				actionRemoveLine.setEnabled(false);
			}
			String resSystem = EASResource.getString(
					"com.kingdee.eas.fdc.invite.FDCInviteResource", "system");
			//如果是系统预设
			if (resSystem.equals(col.getDescription())) {
				txtHeadName.setEnabled(false);
				txtHeadDes.setEnabled(false);
				ckcIsQuoting.setEnabled(false);
				comboColumnType.setEnabled(false);
				actionRemoveLine.setEnabled(false);
				// 如果是系统预设，直接返回，无须判断是否被引用
				return;
			} else if (col.getProperty().equals(DescriptionEnum.Amount)
					|| col.getProperty().equals(DescriptionEnum.TotalPrice)
					|| col.getProperty().equals(DescriptionEnum.ProjectAmt)) {
				//综合单价下的金额信息，允许修改名称，不允许修改属性，可删除
				txtHeadName.setEnabled(true);
				txtHeadDes.setEnabled(false);
				ckcIsQuoting.setEnabled(false);
				actionRemoveLine.setEnabled(true);
			} else {
				//其它自定义信息，修改名称，允许修改属性，可删除
				txtHeadName.setEnabled(true);
				txtHeadDes.setEnabled(false);
				ckcIsQuoting.setEnabled(true);
				actionRemoveLine.setEnabled(true);
				comboColumnType.setEnabled(true);
			}
			// 增加引用后界面状态的控制
			if (editData != null && editData.getId() != null) {
				if (colIsUsedMap != null && colIsUsedMap.get(col) != null) {
					boolean colUsed = ((Boolean) colIsUsedMap.get(col))
							.booleanValue();
					setObjectsDisabled(colUsed);
				}
			}			
		} else {
			this.txtHeadName.setText(null);
			this.txtHeadName.setEnabled(false);
			// this.comboType.setEnabled(false);
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
					.getCurrentCtrlUnit().getId().toString())) {
				if (!STATUS_VIEW.equals(getOprtState())) {
					actionAddLine.setEnabled(true);
					actionRemoveLine.setEnabled(false);
				}
			}
		}
	}
	
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		if(getHeadCollByTree(treeColumn.getModel()).size() > editData.getEntries().size()){
			int result = MsgBox.showConfirm2New(this, "单据内容已改变，是否保存?");
			if(JOptionPane.YES_OPTION == result){
					try{
                		    actionSave_actionPerformed(null);
					}catch(Exception e1){
						abort();
					}
					
			}
		}
		super.actionExitCurrent_actionPerformed(e);
	}
	
	public boolean destroyWindow() {
		// TODO Auto-generated method stub
		if(getHeadCollByTree(treeColumn.getModel()).size() > editData.getEntries().size()){
			int result = MsgBox.showConfirm2New(this, "单据内容已改变，是否保存?");
			if(JOptionPane.YES_OPTION == result){
				try {
					actionSave_actionPerformed(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					abort();
				}
			}
		}
		return super.destroyWindow();
	}
	/*****
	 * 表头中，综合单价没有分量
	 * 且，这个表头被引用
	 * 这种情况不允许再为综合单价增加分量
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean checkHeadType(DefaultKingdeeTreeNode node) throws EASBizException, BOSException{		
		if(node.isLeaf())
			return !isHeadTypeUsed();
		else
			return true;
	}

	private boolean checkColName(DefaultKingdeeTreeNode node,Map nameMap) {
		for(int i=0;i<node.getChildCount();i++){
			DefaultKingdeeTreeNode aNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			String name = aNode.getText();
			if (nameMap.containsKey(name)) {
				return false;
			}
			nameMap.put(name, aNode);
			if(!checkColName(aNode,nameMap))
				return false;
		}
		return true;
	}

	public HeadColumnCollection getHeadCollByTree(TreeModel columnTree) {
		HeadColumnCollection collection = new HeadColumnCollection();
		this.addHeadCollByNode(collection, (DefaultKingdeeTreeNode) columnTree
				.getRoot());
		return collection;
	}

	public void addHeadCollByNode(HeadColumnCollection col,
			DefaultKingdeeTreeNode node) {
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo column = (HeadColumnInfo) node.getUserObject();
			HeadColumnInfo parentColumn = new HeadColumnInfo();
			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
					.getParent();
			if (parent.getUserObject() instanceof HeadColumnInfo) {
				parentColumn = (HeadColumnInfo) parent.getUserObject();
				column.setParent(parentColumn);
			}
			int index = parent.getIndex(node);
			column.setSeq(index);
			column.setNumber(Integer.toString(index));
			column.setLongNumber(parentColumn.getLongNumber()
					+ (index >= 10 ? "!" : "!0") + String.valueOf(index));
			column.setLevel(node.getLevel() - 1);
			column.setIsLeaf(node.isLeaf());
			col.add(column);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.addHeadCollByNode(col, (DefaultKingdeeTreeNode) node
					.getChildAt(i));
		}
	}

	private void addAmountNode(DefaultKingdeeTreeNode amount,
			DefaultKingdeeTreeNode node) {
		//
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo column = (HeadColumnInfo) node.getUserObject();
			if (column.getName().equals(
					EASResource.getString(
							"com.kingdee.eas.fdc.invite.FDCInviteResource",
							"ProjectAmt"))) {
				for (int i = 0; i < node.getChildCount(); i++) {
					DefaultKingdeeTreeNode leafNode = (DefaultKingdeeTreeNode) node
							.getChildAt(i);
					HeadColumnInfo temp = (HeadColumnInfo) leafNode
							.getUserObject();
					DefaultKingdeeTreeNode addNode = new DefaultKingdeeTreeNode(
							"");
					HeadColumnInfo columnTemp = new HeadColumnInfo();
					columnTemp.setName(temp.getName());
					columnTemp.setIsQuoting(temp.isIsQuoting());
					columnTemp.setDescription(temp.getDescription());
					columnTemp.setColumnType(temp.getColumnType());
					columnTemp.setIsRefPrice(false);
					if (temp.getName().equals("小计")) {
						columnTemp.setProperty(DescriptionEnum.AmountSum);
					} else {
						columnTemp.setProperty(DescriptionEnum.Amount);
					}
					addNode.setUserObject(columnTemp);
					this.treeColumn.addNodeInto(addNode, amount);
					// treeColumn.addNodeInto(, amount);
				}
				treeColumn.revalidate();
			} else {
				// for (int i = 0; i < node.getChildCount(); i++) {
				// addAmountNode(amount,(DefaultKingdeeTreeNode) node
				// .getChildAt(i));
				// }
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			addAmountNode(amount, (DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}

	private void beforeStoreFields() {
		CacheServiceFactory.getInstance().discardType(new HeadTypeInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new HeadColumnInfo().getBOSType());
		Map nameMap = new HashMap();
		if (!checkColName((DefaultKingdeeTreeNode) this.treeColumn.getModel()
				.getRoot(),nameMap)) {
			MsgBox.showError("列名不能重复!");
			this.abort();
		}
		// DefaultKingdeeTreeNode root =
		// (DefaultKingdeeTreeNode)treeColumn.getModel().getRoot();
		// DefaultKingdeeTreeNode node;
		// DefaultKingdeeTreeNode amount = null;
		// for(int i=0; i<root.getChildCount(); i++){
		// node = (DefaultKingdeeTreeNode) root
		// .getChildAt(i);
		// if (node.getUserObject() instanceof HeadColumnInfo) {
		// HeadColumnInfo column = (HeadColumnInfo) node.getUserObject();
		// if(column.getName().equals(EASResource
		// .getString(
		// "com.kingdee.eas.fdc.invite.FDCInviteResource",
		// "Amount"))){
		// amount = node;
		// amount.removeAllChildren();
		// break;
		// }
		// }
		// }
		// if(amount!=null&&amount.getUserObject() instanceof HeadColumnInfo)
		// addAmountNode(amount,root);
		editData.getEntries().clear();
		editData.getEntries().addCollection(getHeadCollByTree(treeColumn.getModel()));
		
		//20101013 zhougang
		if(chkIsDefined.isSelected()){
//			if(editData.getEntries() != null){
//				for(int i = 0; i<editData.getEntries().size();i++){
//					editData.getEntries().get(i).setIsQuoting(true);
//				}
//			}
			bizName.setSelectedItemData(bizName.getSelectedItemData().toString().replace("（供应商自定义清单）", "") + "（供应商自定义清单）");
		}
	}

	private void initUI() throws Exception {
		// 分录增、删按钮调整至分录上方
		JButton btnAddRuleNew = contHeadColumn.add(actionAddLine);
		JButton btnDelRuleNew = contHeadColumn.add(actionRemoveLine);
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddRuleNew.setToolTipText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "addNode"));
		btnAddRuleNew.setText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "addNode"));
		btnAddRuleNew.setSize(22, 19);
		btnDelRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelRuleNew.setToolTipText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "removeNode"));
		btnDelRuleNew.setText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "removeNode"));
		btnDelRuleNew.setSize(22, 19);
	}

	protected IObjectValue createNewData() {
		HeadTypeInfo objectValue = new HeadTypeInfo();
		objectValue.setIsEnabled(true);
		objectValue.setOrg((FullOrgUnitInfo) SysContext.getSysContext()
				.getCurrentOrgUnit());
		objectValue.setIsLeaf(true);
		objectValue.put("parent", getUIContext().get(UIContext.PARENTNODE));

		// HeadColumnInfo entryInfoItemNum = new HeadColumnInfo();
		// entryInfoItemNum.setName(EASResource
		// .getString(
		// "com.kingdee.eas.fdc.invite.FDCInviteResource",
		// "ListintItemNumber"));
		// entryInfoItemNum.setDescription(EASResource
		// .getString(
		// "com.kingdee.eas.fdc.invite.FDCInviteResource",
		// "system"));
		// entryInfoItemNum.setIsQuoting(false);
		// entryInfoItemNum.setLevel(1);
		// entryInfoItemNum.setHeadType(objectValue);
		// entryInfoItemNum.setIsHasChild(false);
		// entryInfoItemNum.setColumnType(ColumnTypeEnum.String);
		// entryInfoItemNum.setIsRefPrice(false);
		// entryInfoItemNum.setProperty(DescriptionEnum.System);
		// entryInfoItemNum.setId(BOSUuid.create(entryInfoItemNum.getBOSType()));
		// objectValue.getEntries().add(entryInfoItemNum);

		HeadColumnInfo entryInfoItemName = new HeadColumnInfo();
		entryInfoItemName.setName(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource",
				"ListintItemName"));
		entryInfoItemName.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
		entryInfoItemName.setIsQuoting(false);
		entryInfoItemName.setLevel(1);
		entryInfoItemName.setHeadType(objectValue);
		entryInfoItemName.setIsHasChild(false);
		entryInfoItemName.setColumnType(ColumnTypeEnum.String);
		entryInfoItemName.setIsRefPrice(false);
		entryInfoItemName.setProperty(DescriptionEnum.System);
		entryInfoItemName.setId(BOSUuid.create(entryInfoItemName.getBOSType()));
		objectValue.getEntries().add(entryInfoItemName);

		HeadColumnInfo entryInfoUnit = new HeadColumnInfo();
		entryInfoUnit.setName(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "Unit"));
		entryInfoUnit.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
		entryInfoUnit.setIsQuoting(false);
		entryInfoUnit.setLevel(1);
		entryInfoUnit.setHeadType(objectValue);
		entryInfoUnit.setIsHasChild(false);
		entryInfoUnit.setColumnType(ColumnTypeEnum.String);
		entryInfoUnit.setIsRefPrice(false);
		entryInfoUnit.setProperty(DescriptionEnum.System);
		entryInfoUnit.setId(BOSUuid.create(entryInfoUnit.getBOSType()));
		objectValue.getEntries().add(entryInfoUnit);

		HeadColumnInfo entryInfoProjectAmt = new HeadColumnInfo();
		entryInfoProjectAmt.setName(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "ProjectAmt"));
		entryInfoProjectAmt.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
		entryInfoProjectAmt.setIsQuoting(false);
		entryInfoProjectAmt.setLevel(1);
		entryInfoProjectAmt.setHeadType(objectValue);
		entryInfoProjectAmt.setIsHasChild(false);
		entryInfoProjectAmt.setColumnType(ColumnTypeEnum.Amount);
		entryInfoProjectAmt.setIsRefPrice(false);
		entryInfoProjectAmt.setProperty(DescriptionEnum.ProjectAmtSum);
		entryInfoProjectAmt.setId(BOSUuid.create(entryInfoProjectAmt
				.getBOSType()));
		objectValue.getEntries().add(entryInfoProjectAmt);

		HeadColumnInfo entryInfoPrice = new HeadColumnInfo();
		entryInfoPrice.setName(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "TotalPrice"));
		entryInfoPrice.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
		entryInfoPrice.setIsQuoting(true);
		entryInfoPrice.setLevel(1);
		entryInfoPrice.setHeadType(objectValue);
		entryInfoPrice.setIsHasChild(true);
		entryInfoPrice.setColumnType(ColumnTypeEnum.Amount);
		entryInfoPrice.setIsRefPrice(true);
		entryInfoPrice.setProperty(DescriptionEnum.TotalPriceSum);
		entryInfoPrice.setId(BOSUuid.create(entryInfoPrice.getBOSType()));
		objectValue.getEntries().add(entryInfoPrice);

		HeadColumnInfo entryInfoAmount = new HeadColumnInfo();
		entryInfoAmount.setName(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "Amount"));
		entryInfoAmount.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
		entryInfoAmount.setIsQuoting(false);
		entryInfoAmount.setLevel(1);
		entryInfoAmount.setHeadType(objectValue);
		entryInfoAmount.setIsHasChild(false);
		entryInfoAmount.setColumnType(ColumnTypeEnum.Amount);
		entryInfoAmount.setIsRefPrice(false);
		entryInfoAmount.setProperty(DescriptionEnum.AmountSum);
		entryInfoAmount.setId(BOSUuid.create(entryInfoAmount.getBOSType()));
		objectValue.getEntries().add(entryInfoAmount);

		HeadColumnInfo entryInfoDes = new HeadColumnInfo();
		entryInfoDes.setName(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "Descri"));
		entryInfoDes.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
		entryInfoDes.setIsQuoting(false);
		entryInfoDes.setLevel(1);
		entryInfoDes.setHeadType(objectValue);
		entryInfoDes.setIsHasChild(false);
		entryInfoDes.setColumnType(ColumnTypeEnum.String);
		entryInfoDes.setIsRefPrice(true);
		entryInfoDes.setProperty(DescriptionEnum.System);
		entryInfoDes.setId(BOSUuid.create(entryInfoDes.getBOSType()));
		objectValue.getEntries().add(entryInfoDes);
		
		this.bizName.setEnabled(true);
		this.txtLongNumber.setEnabled(true);
		this.txtDescription.setEnabled(true);
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return HeadTypeFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		// HeadTypeEntryInfo info = new HeadTypeEntryInfo();
		// return info;
		return null;
	}

	public void loadFields() {
		super.loadFields();
		if (editData != null && editData.isIsLeaf()) {
			if (!STATUS_VIEW.equals(this.getOprtState())) {
				actionAddLine.setEnabled(true);
				actionRemoveLine.setEnabled(true);
			}
		} else {
			actionAddLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
		}
		Map map = getUIContext();
		HeadTypeInfo fatherHeadTypeInfo = (HeadTypeInfo) map
				.get(UIContext.PARENTNODE);

		if (null != fatherHeadTypeInfo) {
			String fatherLongNum = fatherHeadTypeInfo.getLongNumber();
			fatherLongNum = fatherLongNum.replaceAll("!", ".");
			this.txtUpperNum.setText(fatherLongNum);// 设置上级编码

			String operateState = this.getOprtState();
			if (STATUS_ADDNEW.equals(operateState)) {
				this.txtLongNumber.setText(fatherLongNum + ".");
			} else if (STATUS_EDIT.equals(operateState)) {
				String longNumber = this.editData.getLongNumber();
				longNumber = longNumber.replaceAll("!", ".");
				this.txtLongNumber.setText(longNumber);

			} else if (STATUS_VIEW.equals(operateState)) {
				String longNumber = this.editData.getLongNumber();
				longNumber = longNumber.replaceAll("!", ".");
				this.txtLongNumber.setText(longNumber);
			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				// this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {

				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId()
						.toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				String strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
			}
		}
		try {
			TreeModel model = getColumnTreeByHead(editData);
			treeColumn.setModel(model);
			treeColumn.setSelectionNode((DefaultKingdeeTreeNode) model
					.getRoot());
		} catch (BOSException e) {
			super.handUIException(e);
		} catch (EASBizException ex) {
			super.handUIException(ex);
		}
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
				.getCurrentCtrlUnit().getId().toString())) {
			actionAddNew.setEnabled(true);
			if (STATUS_VIEW.equals(getOprtState())) {
				actionEdit.setEnabled(true);
			} else if (STATUS_EDIT.equals(getOprtState())) {
				actionEdit.setEnabled(false);
			}
			actionRemove.setEnabled(true);
		} else {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}

	}

	public void onLoad() throws Exception {
		// 表头被引用后还是可以添加内容，不能修改原有数据
//		if (this.getOprtState().equals("EDIT")) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("headType", this.getUIContext()
//							.get("ID")));
//			if (NewListingPageFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("表头已经被引用,不能修改或删除!");
//				this.abort();
//			}
//		}
		super.onLoad();
		initUI();
		if (editData != null && editData.getId() != null) {
			String id = editData.getId().toString();
			String tables[] = new String[] { "T_INV_NewListTempletPage",
					"T_INV_NewListingPage", "T_INV_ListingItem" };
			Object[] newTables = HeadTypeFactory.getRemoteInstance()
					.getRelateData(id, tables);
			// 被引用了
			if (newTables.length > 0) {
				fUsed = true;
				setObjectsDisabled(fUsed);
				
			}
		}
		// buildHeadColumnTree();
		this.txtHeadDes.setEnabled(false);
		
		//20101013 zhougang
		if(bizName.getSelectedItemData() !=null){
			bizName.setSelectedItemData(bizName.getSelectedItemData().toString().replace("（供应商自定义清单）", ""));
		}
		//20101013 zhougang
	}
	/***
	 * 如果表头中的列已经用过,不允许修改
	 * @param isUsed
	 */
	private void setObjectsDisabled(boolean isUsed){
		if(isUsed){
			actionRemoveLine.setEnabled(false);
			txtLongNumber.setEnabled(false);
			txtDescription.setEnabled(false);
			bizName.setEnabled(false);
			
			ckcIsQuoting.setEnabled(false);
			contColumnType.setEnabled(false);
			contHeadDescription.setEnabled(false);
		}
	}

	public void buildHeadColumnTree() throws Exception {
		HeadColumnCollection columns = editData.getEntries();
		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("列");
		HashMap nodeMap = new HashMap();
		// for (int i = 0; i < columns.size(); i++) {
		// HeadColumnInfo column = (HeadColumnInfo) columns.getObject(i);
		// DefaultKingdeeTreeNode subTreeNode = new DefaultKingdeeTreeNode(
		// column);
		// subTreeNode.setUserObject(column);
		// nodeMap.put(column.getId().toString(), subTreeNode);
		// }
		for (int i = 0; i < columns.size(); i++) {
			HeadColumnInfo column = (HeadColumnInfo) columns.getObject(i);
			DefaultKingdeeTreeNode subTreeNode = new DefaultKingdeeTreeNode(
					column);
			subTreeNode.setUserObject(column);
			HeadColumnInfo parentColumn = (HeadColumnInfo) column.getParent();
			if (parentColumn == null) {
				this.addHeadSeqNode(rootTreeNode, subTreeNode);
			} else {
				if (nodeMap.containsKey(parentColumn.getId().toString())) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) nodeMap
							.get(parentColumn.getId().toString());
					this.addHeadSeqNode(node, subTreeNode);
				}
			}
			nodeMap.put(column.getId().toString(), subTreeNode);
		}
		TreeModel treeModel = new DefaultTreeModel(rootTreeNode);
		this.treeColumn.setModel(treeModel);
		treeColumn.setSelectionNode((DefaultKingdeeTreeNode) treeModel
				.getRoot());
	}

	public void addHeadSeqNode(DefaultKingdeeTreeNode parent,
			DefaultKingdeeTreeNode child) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) parent
					.getChildAt(i);
			HeadColumnInfo column1 = (HeadColumnInfo) node.getUserObject();
			HeadColumnInfo column2 = (HeadColumnInfo) child.getUserObject();
			if (column1.getSeq() > column2.getSeq()) {
				int index = parent.getIndex(node);
				parent.insert(child, index);
				return;
			}
		}
		parent.add(child);
	}

	// private void buildHeadColumnTree() throws Exception {
	// FilterInfo filter = new FilterInfo();
	// // filter.getFilterItems().add(
	// // new FilterItemInfo("isEnabled", Boolean.TRUE));
	// filter.getFilterItems().add(
	// new FilterItemInfo("headType.id", editData.getId().toString()));
	// TreeModel model = FDCClientHelper.createDataTree(HeadColumnFactory
	// .getRemoteInstance(), filter, "所有类型");
	// this.treeColumn.setModel(model);
	// }

	public TreeModel getColumnTreeByHead(HeadTypeInfo head)
			throws BOSException, EASBizException {
		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("列");
		HashMap nodeMap = new HashMap();
		boolean fEdit = editData != null && editData.getId() != null;
		HeadColumnCollection columns = getHeadColumnInfos(head, fEdit);
		if (fEdit) {
			colIsUsedMap = new HashMap();
		}

		for (int i = 0; i < columns.size(); i++) {
			HeadColumnInfo column = (HeadColumnInfo) columns.getObject(i);
			if (fEdit) {
				boolean fUsed = isColUsed(column);
				colIsUsedMap.put(column, Boolean.valueOf(fUsed));
			}
			DefaultKingdeeTreeNode subTreeNode = new DefaultKingdeeTreeNode(
					column);
			subTreeNode.setUserObject(column);
			HeadColumnInfo parentColumn = (HeadColumnInfo) column.getParent();
			if (parentColumn == null) {
				this.addHeadSeqNode(rootTreeNode, subTreeNode);
			} else {
				if (nodeMap.containsKey(parentColumn.getId().toString())) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) nodeMap
							.get(parentColumn.getId().toString());
					this.addHeadSeqNode(node, subTreeNode);
				}
			}
			nodeMap.put(column.getId().toString(), subTreeNode);
		}
		TreeModel treeModel = new DefaultTreeModel(rootTreeNode);
		return treeModel;
	}
	private boolean isHeadTypeUsed() throws EASBizException, BOSException{
		if(this.getUIContext().get("ID")==null)
			return false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("item.headType", this.getUIContext()
						.get("ID")));
		IRefPrice iRefPrice = RefPriceFactory.getRemoteInstance();
		boolean fItemUsed = iRefPrice.exists(filter);		
		if(fItemUsed)
			return true;
		
		INewListTempletPage iTemplPage = NewListTempletPageFactory
				.getRemoteInstance();
		boolean fTemplUsed = iTemplPage
				.exists("select id where headType.id = '" + this.getUIContext()
						.get("ID") + "' ");
		if (fTemplUsed)
			return true;

		INewListingPage iLstPage = NewListingPageFactory.getRemoteInstance();
		boolean fLstUsed = iLstPage.exists("select id where headType.id = '"
				+ this.getUIContext().get("ID") + "' ");

		return fLstUsed;
		
	}

	private boolean isColUsed(HeadColumnInfo column) throws BOSException,
			EASBizException {
		String colId = column.getId().toString();
		//首先判断参考价格里面是否有引用此列，如果有，直接返回，无须进行下面的判断
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("column.id", colId));
		IRefPriceEntry refPrices = RefPriceEntryFactory.getRemoteInstance();
		boolean fRefPriceUsed = refPrices.exists(filter);
		if( fRefPriceUsed)
			return true;		
		//然后判断清单模版里面是否有引用此列，如果有，直接返回，无须进行下面的判断
		INewListTempletColumn iTemplCol = NewListTempletColumnFactory
				.getRemoteInstance();
		boolean fTemplUsed = iTemplCol
				.exists("select id where headColumn.id = '" + colId + "' ");
		if (fTemplUsed)
			return true;
		//接着判断清单里面是否有引用此列
		INewListingColumn iLstCol = NewListingColumnFactory.getRemoteInstance();
		boolean fLstUsed = iLstCol.exists("select id where headColumn.id = '"
				+ colId + "' ");
		return fLstUsed;
	}

	private HeadColumnCollection getHeadColumnInfos(HeadTypeInfo head,
			boolean fEdit) throws BOSException {
		HeadColumnCollection columns = null;
		if (fEdit) {
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("headType.id", editData.getId()
					.toString(), CompareType.EQUALS));
			SelectorItemCollection selectors = evInfo.getSelector();
			selectors.add("id");
			selectors.add("*");
			selectors.add("name");
			selectors.add("property");
			selectors.add("description");
			selectors.add("isQuoting");
			selectors.add("seq");
			selectors.add("isLeaf");
			selectors.add("number");
			selectors.add("level");
			selectors.add("longNumber");
			selectors.add("headType.*");
			selectors.add("parent.*");
			evInfo.setFilter(filter);

			SorterItemInfo sii = new SorterItemInfo();
			sii.setPropertyName("longNumber");
			sii.setSortType(SortType.ASCEND);
			evInfo.getSorter().add(sii);

			IHeadColumn iHeadColumn = HeadColumnFactory.getRemoteInstance();
			columns = iHeadColumn.getHeadColumnCollection(evInfo);
		} else {
			columns = head.getEntries();
		}
		return columns;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		Map map = getUIContext();
		Object parentObj = map.get(UIContext.PARENTNODE);
		// 编码是否为空
		if (this.txtLongNumber.getText() == null
				|| this.txtLongNumber.getText().trim().equals("")) {
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		} else {
			String number = "";
			if (parentObj != null) {
				HeadTypeInfo parentHeadTypeInfo = (HeadTypeInfo) parentObj;
				this.editData.setParent(parentHeadTypeInfo);

				String parentNumber = parentHeadTypeInfo.getLongNumber();// 上级编码
				parentNumber = parentNumber.replaceAll("!", ".");

				if (STATUS_ADDNEW.equals(getOprtState())) { // 新增状态
					String longNumber = txtLongNumber.getText();
					if ((longNumber.equals(parentNumber + "."))
							|| (longNumber.length() < parentNumber.length() + 1)) {
						// 编码不完整
						throw new FDCBasedataException(
								FDCBasedataException.NUMBER_CHECK_2);
					}

					if ((longNumber.substring(parentNumber.length() + 1,
							longNumber.length()).indexOf(".") > 0)// 用户打了点
							|| (!longNumber.substring(0, parentNumber.length())
									.equals(parentNumber))) {// 初始父编码被修改，编码不符合规范
						throw new FDCBasedataException(
								FDCBasedataException.NUMBER_CHECK_3);
					}

					String longNum = this.txtLongNumber.getText();

					number = longNum.substring(longNum.lastIndexOf(".") + 1,
							longNum.length());

				} else {
					String longNum = this.txtLongNumber.getText();

					number = longNum.substring(longNum.lastIndexOf(".") + 1,
							longNum.length());
					this.editData.setNumber(number);
				}
			} else {
				if (!(this.txtLongNumber.getText().indexOf(".") < 0)) {
					// 编码不符合规范
					throw new FDCBasedataException(
							FDCBasedataException.NUMBER_CHECK_3);
				}
				number = this.txtLongNumber.getText();
			}

			this.editData.setNumber(number);
			String temp = this.txtLongNumber.getText();
			temp = temp.replace('.', '!');
			this.editData.setLongNumber(temp);
		}
		// 编码
		FilterInfo filterInfo = new FilterInfo();
		filterInfo
				.appendFilterItem("longNumber", this.editData.getLongNumber());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (getBizInterface().exists(filterInfo)) {
			throw new FDCInviteException(
					FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
		}

		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(
				this.bizName, this.editData, "name");
		if (flag) {
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

		filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("name", this.editData.getName());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (getBizInterface().exists(filterInfo)) {
			throw new FDCInviteException(FDCInviteException.NAME_IS_OVER);
		}
		// 描述超界处理
		if (this.txtDescription.getText() != null
				&& this.txtDescription.getText().length() > 255) {
			this.editData.setDescription(this.txtDescription.getText()
					.substring(0, 254));
		}
		if (map.containsKey("updateTable") && map.containsKey("updateID")
				&& !map.containsKey("alreadUpdate")) {
			this.editData.getExtendedProperties().put("updateTable",
					map.get("updateTable"));
			this.editData.getExtendedProperties().put("updateID",
					map.get("updateID"));
			map.put("alreadUpdate", "");
		}

		int count = editData.getEntries().size();
		 for (int i = 0; i < count; i++) {
			HeadColumnInfo column = editData.getEntries().get(i);
			if (StringUtils.isEmpty(column.getName())) {
				MsgBox.showError("列名不能为空");
				this.abort();
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("entries.property"));
		return sic;
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node == null) {
			MsgBox.showWarning(this, "请先选中节点！");
			SysUtil.abort();
		}
		String custStrRes = EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "personal");
		if (node.getUserObject() != null
				&& node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo parent = (HeadColumnInfo) node.getUserObject();
			String totalPriceRes = EASResource.getString(
					"com.kingdee.eas.fdc.invite.FDCInviteResource",
					"TotalPrice");
			if ((parent.getName().equals(EASResource.getString(
					"com.kingdee.eas.fdc.invite.FDCInviteResource",
					"ProjectAmt")))
					|| (parent.getName().equals(totalPriceRes))) {
				int childCount = node.getChildCount();
				if (childCount == 0) {
					DefaultKingdeeTreeNode addSumNode = new DefaultKingdeeTreeNode(
							"");
					HeadColumnInfo columnSum = new HeadColumnInfo();
					columnSum.setName("小计");
					columnSum.setIsQuoting(parent.isIsQuoting());
					columnSum.setColumnType(ColumnTypeEnum.Amount);
					if (parent.getName().equals(totalPriceRes)) {
						columnSum.setIsRefPrice(true);
						columnSum.setProperty(DescriptionEnum.TotalPriceSum);
						// parent.setProperty(DescriptionEnum.TotalPrice);
					} else {
						columnSum.setIsRefPrice(false);
						columnSum.setProperty(DescriptionEnum.ProjectAmtSum);
						// parent.setProperty(DescriptionEnum.ProjectAmt);
					}
					columnSum.setDescription(EASResource.getString(
							"com.kingdee.eas.fdc.invite.FDCInviteResource",
							"system"));
					addSumNode.setUserObject(columnSum);
					this.treeColumn.addNodeInto(addSumNode, node);
				}
				DefaultKingdeeTreeNode addNode = new DefaultKingdeeTreeNode("");
				HeadColumnInfo column = new HeadColumnInfo();
				column.setName("");
				column.setIsQuoting(parent.isIsQuoting());
				column.setColumnType(ColumnTypeEnum.Amount);
				if (parent.getName().equals(totalPriceRes)) {
					column.setIsRefPrice(true);
					column.setProperty(DescriptionEnum.TotalPrice);
				} else {
					column.setIsRefPrice(false);
					column.setProperty(DescriptionEnum.ProjectAmt);
				}
				column.setDescription(custStrRes);
				addNode.setUserObject(column);
				this.treeColumn.insertNodeInto(addNode, node, node
						.getChildCount() - 1);
				treeColumn.revalidate();
				this.treeColumn.setSelectionNode(addNode);
				this.txtHeadName.requestFocus();
				//20101013 zhougang
				ckcIsQuoting.setSelected(column.isIsQuoting());
//				if(!chkIsDefined.isSelected()){
//					ckcIsQuoting.setSelected(column.isIsQuoting());
//				}else{
//					ckcIsQuoting.setSelected(true);
//				}

				txtHeadDes.setText(custStrRes);
				return;
			}
		}
		
		DefaultKingdeeTreeNode addNode = new DefaultKingdeeTreeNode("");
		HeadColumnInfo column = new HeadColumnInfo();
		column.setName("");
		column.setIsQuoting(false);
		column.setDescription(custStrRes);
		column.setColumnType(ColumnTypeEnum.String);
		column.setProperty(DescriptionEnum.Personal);
		column.setIsRefPrice(false);
		addNode.setUserObject(column);
		this.treeColumn.addNodeInto(addNode, node);
		this.treeColumn.setSelectionNode(addNode);
		this.txtHeadName.requestFocus();
		
		//20101013 zhougang
		ckcIsQuoting.setSelected(false);
//		if(!chkIsDefined.isSelected()){
//			ckcIsQuoting.setSelected(false);
//		}else{
//			ckcIsQuoting.setSelected(true);
//		}
		
		txtHeadDes.setText(custStrRes);
		//如果是在顶节点下增加子节点,相关的三个控件应该为true
		//2008-05-30 周勇
		if(node.getParent() == null){
			ckcIsQuoting.setEnabled(true);
			contColumnType.setEnabled(true);
			contHeadDescription.setEnabled(true);
		}
		txtHeadDes.setEnabled(false);
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
				.getParent();
		this.treeColumn.removeNodeFromParent(node);
		if (parent.getUserObject() != null
				&& parent.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo parentColumn = (HeadColumnInfo) parent
					.getUserObject();
			if ((parentColumn.getName().equals(EASResource.getString(
					"com.kingdee.eas.fdc.invite.FDCInviteResource",
					"ProjectAmt")))
					|| (parentColumn.getName().equals(EASResource.getString(
							"com.kingdee.eas.fdc.invite.FDCInviteResource",
							"TotalPrice")))) {
				int childCount = parent.getChildCount();
				if (childCount == 1) {
					treeColumn.removeAllChildrenFromParent(parent);
					if (parentColumn
							.getName()
							.equals(
									EASResource
											.getString(
													"com.kingdee.eas.fdc.invite.FDCInviteResource",
													"ProjectAmt")))
						parentColumn.setProperty(DescriptionEnum.ProjectAmtSum);
					else
						parentColumn.setProperty(DescriptionEnum.TotalPriceSum);
				}
			}
		}
		this.treeColumn.setSelectionNode(parent);
	}

	/**
	 * 判断当前编辑的数据是否发生变化
	 */
	public boolean isModify() {
		try {
			com.kingdee.bos.ctrl.common.util.ControlUtilities
					.checkFocusAndCommit();
		} catch (ParseException e) {
			handleControlException();
			abort();
		}

		if (OprtState.VIEW.equals(getOprtState())) {
			return false;
		}
		try {
			storeFields();
		} catch (Exception exc) {
			return false;
		}

		// return !ObjectValueForEditUIUtil.objectValueEquals(oldData,
		// editData);
		return false;
	}

	protected void ckcIsQuoting_stateChanged(ChangeEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo col = (HeadColumnInfo) node.getUserObject();
			if (col == null) {
				return;
			}
			// this.treeColumn.setNodeText(node, this.txtHeadName.getText());
			col.setIsQuoting(ckcIsQuoting.isSelected());
		}
	}

	protected void txtHeadName_keyReleased(KeyEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo col = (HeadColumnInfo) node.getUserObject();
			if (col == null) {
				return;
			}if(txtHeadName.getText().equals("内部编码")){
				MsgBox.showError(this,"内部编码为子目默认内部属性，不能再添加名称为内部编码的表头！");
				return;
			}
			this.treeColumn.setNodeText(node, this.txtHeadName.getText());
			col.setName(this.txtHeadName.getText());
		}
	}

	protected void comboColumnType_actionPerformed(ActionEvent e)
			throws Exception {
		super.comboColumnType_actionPerformed(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo col = (HeadColumnInfo) node.getUserObject();
			if (col == null) {
				return;
			}
			col.setColumnType((ColumnTypeEnum) comboColumnType
					.getSelectedItem());
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// 表头被引用，可以添加列，不能修改删除列
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("headType", this.getUIContext()
//						.get("ID")));
//		if (NewListingPageFactory.getRemoteInstance().exists(filter)) {
//			MsgBox.showInfo("表头已经被引用,不能修改或删除!");
//			return;
//		}
		super.actionEdit_actionPerformed(e);
		this.treeColumn.setSelectionRow(1);
		this.treeColumn.setSelectionRow(0);
		Map map = getUIContext();
		HeadTypeInfo fatherHeadTypeInfo = (HeadTypeInfo) map
				.get(UIContext.PARENTNODE);
		if (null != fatherHeadTypeInfo) {
			this.actionAddLine.setEnabled(true);
		}
		if(editData!=null&&editData.getId()!=null){
			String tables[] = new String[] { "T_INV_NewListTempletPage",
					"T_INV_NewListingPage", "T_INV_ListingItem" };
			IHeadType iHeadType = HeadTypeFactory.getRemoteInstance();
			
			String id = editData.getId().toString();			
			Object[] newTables = iHeadType.getRelateData(id, tables);
			if (newTables.length > 0) {
				//this.txtHeadName.setEnabled(false);
				this.bizName.setEnabled(false);
				this.txtDescription.setEnabled(false);
				this.txtLongNumber.setEnabled(false);
			}
			
		}
		
	}
	
	//20101012 zhougang
	/**
	 * 报价列显示控制。
	 */
	protected void chkIsDefined_stateChanged(ChangeEvent e) throws Exception {
//		HeadTypeInfo info = new HeadTypeInfo();
//		info.setIsDefined(chkIsDefined.isSelected());
		if(chkIsDefined.isSelected()){
			ckcIsQuoting.setVisible(false);
			//ckcIsQuoting.setSelected(true);
		}else{
			ckcIsQuoting.setVisible(true);
		}
	}
}