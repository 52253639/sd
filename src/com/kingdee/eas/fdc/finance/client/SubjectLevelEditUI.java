/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.TblPtg;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.finance.ISubjectLevel;
import com.kingdee.eas.fdc.finance.SubjectLevelFactory;
import com.kingdee.eas.fdc.finance.SubjectLevelInfo;
import com.kingdee.eas.fdc.finance.SubjectLevelSubjectCollection;
import com.kingdee.eas.fdc.finance.SubjectLevelSubjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SubjectLevelEditUI extends AbstractSubjectLevelEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SubjectLevelEditUI.class);

	/**
	 * output class constructor
	 */
	public SubjectLevelEditUI() throws Exception {
		super();
	}

	/**
	 * 如果启用了，则带出上次保存的分录<br>
	 * 否则，新增时，带出所有最新成本科目<br>
	 * 否则，带出所有最新成本科目，并将其中上次勾选的科目也勾选
	 */
	public void loadFields() {
		super.loadFields();
		kdtSubject.removeRows();
		initTable();
		// 如果启用了，则带出上次保存的分录
		if (editData.isIsEnabled()) {
			SubjectLevelSubjectCollection subjects = editData.getSubject();
			for (int i = 0; i < subjects.size(); i++) {
				SubjectLevelSubjectInfo subject = subjects.get(i);
				IRow row = kdtSubject.addRow();
				row.setUserObject(subject);
				row.getCell("id").setValue(subject.getId());
				row.getCell("isSelected").setValue(
						Boolean.valueOf(subject.isIsSelected()));
				CostAccountInfo account = subject.getAccountItem();
				if (account != null) {
					String longNumber = account.getLongNumber();
					if (longNumber != null) {
						longNumber = longNumber.replaceAll("!", ".");
					}
					row.getCell("costAccount").setValue(longNumber);
					row.getCell("costAccount").setUserObject(account);
					row.getCell("accountName").setValue(account.getName());
					row.getCell("accountLevel").setValue(
							new Integer(account.getLevel()));
					row.setTreeLevel(account.getLevel() - 1);
				}
				row.getCell("description").setValue(subject.getDescription());

			}
		} else {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("longNumber");
			sic.add("name");
			sic.add("level");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id",
							"00000000-0000-0000-0000-000000000000CCE7AED4"));
			view.setFilter(filter);
			SorterItemInfo soter = new SorterItemInfo("longNumber");
			view.getSorter().add(soter);
			CostAccountCollection accounts = null;
			try {
				accounts = CostAccountFactory.getRemoteInstance()
						.getCostAccountCollection(view);
			} catch (BOSException e) {
				handUIException(e);
			}
			// 否则，新增时，带出所有最新成本科目
			if (STATUS_ADDNEW.equals(getOprtState())) {
				if (accounts != null) {
					for (int i = 0; i < accounts.size(); i++) {
						CostAccountInfo account = accounts.get(i);
						IRow row = kdtSubject.addRow();
						row.setUserObject(new SubjectLevelSubjectInfo());
						row.getCell("isSelected").setValue(Boolean.FALSE);
						String longNumber = account.getLongNumber();
						if (longNumber != null) {
							longNumber = longNumber.replaceAll("!", ".");
						}
						row.getCell("costAccount").setValue(longNumber);
						row.getCell("costAccount").setUserObject(account);
						row.getCell("accountName").setValue(account.getName());
						row.getCell("accountLevel").setValue(
								new Integer(account.getLevel()));
						row.setTreeLevel(account.getLevel() - 1);
						if (account.getLevel() <= 2) {
							row.getCell("isSelected").setValue(Boolean.TRUE);
						}
					}

				}
			} else {
				// 否则，带出所有最新成本科目，并将其中上次勾选的科目也勾选
				try {
					Map ids = ((ISubjectLevel) getBizInterface())
							.getSelectedIDS(editData.getId().toString());
					if (accounts != null) {
						for (int i = 0; i < accounts.size(); i++) {
							CostAccountInfo account = accounts.get(i);
							IRow row = kdtSubject.addRow();
							String id = account.getId().toString();
							if (ids.containsKey(id)) {
								row.getCell("isSelected")
										.setValue(Boolean.TRUE);
								row.getCell("description")
										.setValue(ids.get(id));
							} else {
								row.getCell("isSelected").setValue(
										Boolean.FALSE);
							}
							String longNumber = account.getLongNumber();
							if (longNumber != null) {
								longNumber = longNumber.replaceAll("!", ".");
							}
							row.getCell("costAccount").setValue(longNumber);
							row.getCell("costAccount").setUserObject(account);
							row.getCell("accountName").setValue(
									account.getName());
							row.getCell("accountLevel").setValue(
									new Integer(account.getLevel()));
							row.setTreeLevel(account.getLevel() - 1);
						}
					}
				} catch (BOSException e) {
					handUIException(e);
				} catch (Exception e) {
					handUIException(e);
				}
			}
		}
		System.out.println("1");
	}

	/**
	 * 循环分录存到editData
	 */
	public void storeFields() {
		// String number = editData.getNumber();
		// this.txtNumber.setText(number);
		super.storeFields();
		editData.getSubject().clear();
		for (int i = 0; i < kdtSubject.getRowCount(); i++) {
			IRow row = kdtSubject.getRow(i);
			SubjectLevelSubjectInfo entry = new SubjectLevelSubjectInfo();
			entry.setParent(editData);
			if (row.getCell("id").getValue() != null) {
				entry.setId((BOSUuid) row.getCell("id").getValue());
			}
			if (row.getCell("isSelected").getValue() != null) {
				Boolean isSelected = (Boolean) row.getCell("isSelected")
						.getValue();
				entry.setIsSelected(isSelected.booleanValue());
			} else {
				entry.setIsSelected(false);
			}
			if (row.getCell("costAccount").getUserObject() != null) {
				CostAccountInfo account = (CostAccountInfo) row.getCell(
						"costAccount").getUserObject();
				entry.setAccountItem(account);
			}
			if (row.getCell("description").getValue() != null) {
				entry.setDescription((String) row.getCell("description")
						.getValue());
			}
			editData.getSubject().add(entry);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();

		String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		if (!orgID.equals("00000000-0000-0000-0000-000000000000CCE7AED4")) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		} else {
			this.actionAddNew.setVisible(true);
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setVisible(true);
			this.actionEdit.setEnabled(true);
		}
		if (editData.isIsEnabled())
		{
			this.actionSave.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(false);
		}
		// Integer o = new Integer(this.editData.get("isEnabled").toString());
		// if(o.intValue() == 1){
		// this.actionEdit.setEnabled(false);
		// }
		
		ICodingRuleManager iCoding = CodingRuleManagerFactory.getRemoteInstance();
		if (STATUS_ADDNEW.equals(getOprtState()) && iCoding.isAddView(editData, orgID)){
			getNumberByCodingRule(editData,orgID);
		}
		if (iCoding.isUseIntermitNumber(editData, orgID)||!iCoding.isModifiable(editData, orgID))
		{
			txtNumber.setEditable(false);
		}
//		if(iCoding.isExist(editData, orgID)){
//			if(iCoding.isAddView(editData, orgID)){
//				txtNumber.setEnabled(false);
//			}
//		}
		
		KDTextField txt = new KDTextField();
		txt.setMaxLength(255);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
		kdtSubject.getColumn("description").setEditor(editor);
	}

	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		// caller.put("number", number);
		this.txtNumber.setText(number);
	}

	protected void setNumberTextEnabled() {
		// this.txtNumber.setEnabled(true);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("isEnabled");
		sic.add("Subject.id");
		sic.add("Subject.isSelected");
		sic.add("Subject.accountItem.id");
		sic.add("Subject.accountItem.number");
		sic.add("Subject.accountItem.longNumber");
		sic.add("Subject.accountItem.name");
		sic.add("Subject.accountItem.level");
		sic.add("Subject.description");
		sic.add("creator.id");
		sic.add("creator.number");
		sic.add("creator.name");
		sic.add("createTime");
		return sic;
	}

	/**
	 * output getBizInterface method
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return SubjectLevelFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected IObjectValue createNewData() {
		SubjectLevelInfo objectValue = new SubjectLevelInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());

		return objectValue;

	}

	/**
	 * 一、如果改变的是‘选择’列<br>
	 * 1、打钩，则将其所有上级打钩<br>
	 * 2、取消，则将其所有下级取消<br>
	 * 二、如果改变的是‘科目’列<br>
	 * 1、将‘科目名称’、‘科目级次’列填上默认值
	 */
	protected void kdtSubject_editValueChanged(KDTEditEvent e) throws Exception {
		int colNum = e.getColIndex();
		int rowNum = e.getRowIndex();
		IRow curRow = kdtSubject.getRow(rowNum);
		// 选择
		if (colNum == 1 && e.getValue() instanceof Boolean) {
			Boolean isSelect = (Boolean) e.getValue();
			int curLevel = ((Integer) curRow.getCell("accountLevel").getValue())
					.intValue();
			// 打钩，则将其所有上级打钩
			if (isSelect.booleanValue()) {
				for (int i = rowNum - 1; i >= 0; i--) {
					IRow row = kdtSubject.getRow(i);
					int level = ((Integer) row.getCell("accountLevel")
							.getValue()).intValue();
					if (level == curLevel - 1) {
						row.getCell("isSelected").setValue(Boolean.TRUE);
						curLevel--;
						if (curLevel <= 1) {
							break;
						}
					}
				}
			} else {
				// 取消，则将其所有下级取消
				for (int i = rowNum + 1; i < kdtSubject.getRowCount(); i++) {
					IRow row = kdtSubject.getRow(i);
					int level = ((Integer) row.getCell("accountLevel")
							.getValue()).intValue();
					if (level > curLevel) {
						row.getCell("isSelected").setValue(Boolean.FALSE);
					} else {
						break;
					}
				}
			}
		}
		// 科目
		else if (colNum == 2 && e.getValue() instanceof CostAccountInfo) {
			// 将‘科目名称’、‘科目级次’列填上默认值
			CostAccountInfo account = (CostAccountInfo) e.getValue();
			curRow.getCell("accountName").setValue(account.getName());
			curRow.getCell("accountLevel").setValue(
					new Integer(account.getLevel()));
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		ICodingRuleManager iCoding = CodingRuleManagerFactory
				.getRemoteInstance();
		String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		if (!iCoding.isExist(editData, orgID)
				|| iCoding.isAddView(editData, orgID)) {
			if (editData.getNumber() == null || editData.getNumber().equals("")) {
				MsgBox.showWarning(this, "编码不能为空");
				txtNumber.grabFocus();
				SysUtil.abort();
			}
		}
		boolean isExistRule = false;
		String org;
//		org = crm.getCurrentAppOUID(editData);
		

		if (editData.getName() == null || editData.getName().equals("")) {
			MsgBox.showWarning(this, "名称不能为空");
			txtName.grabFocus();
			SysUtil.abort();
		}
	}

	public boolean isModify() {
		return false;
	}

	private void initTable() {
		kdtSubject.checkParsed();

		kdtSubject.getColumn("costAccount").getStyleAttributes()
				.setLocked(true);
		kdtSubject.getColumn("accountName").getStyleAttributes()
				.setLocked(true);
		kdtSubject.getColumn("accountLevel").getStyleAttributes().setLocked(
				true);
		kdtSubject.getTreeColumn().setDepth(5);

		// 框架bug，树级次改变刷新行数时滚动条不会更新，所以一直显示
		kdtSubject.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);

		// kdtSubject.getDataRequestManager().setDataRequestMode(
		// KDTDataRequestManager.REAL_MODE);

		KDBizPromptBox prmtSubject = new KDBizPromptBox();
		prmtSubject.setDisplayFormat("$longNumber$");
		prmtSubject.setEditFormat("$number$");
		prmtSubject.setCommitFormat("$number$");
		prmtSubject
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("longNumber");
		sic.add("name");
		sic.add("level");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		String curOrgID = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", curOrgID));
		view.setFilter(filter);
		SorterItemInfo soter = new SorterItemInfo("longNumber");
		view.getSorter().add(soter);
		prmtSubject.setEntityViewInfo(view);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(prmtSubject);
		kdtSubject.getColumn("costAccount").setEditor(editor);

	}
}
