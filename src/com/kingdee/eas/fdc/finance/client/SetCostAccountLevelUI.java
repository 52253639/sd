/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTCellEditorListener;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SetCostAccountLevelUI extends AbstractSetCostAccountLevelUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SetCostAccountLevelUI.class);

	private ProjectInvestPlanEditUI parentUI = null;

	private CurProjectInfo projectInfo = null;

	private List alreadySelectedAccount;

	private List alreadySetValueAccount;

	private List accountList;

	private Map setValueMap;

	private Color lock = new Color(235, 235, 235);

	/**
	 * output class constructor
	 */
	public SetCostAccountLevelUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		initTableStyle();
		String curProjectId = null;

		Map uiContext = getUIContext();
		if (uiContext.get("curProject") != null) {
			projectInfo = (CurProjectInfo) uiContext.get("curProject");
			curProjectId = projectInfo.getId().toString();
		}
		if (uiContext.get("parentUI") != null) {
			parentUI = (ProjectInvestPlanEditUI) uiContext.get("parentUI");
		}

		if (uiContext.get("alreadySelectAccount") != null) {
			alreadySelectedAccount = (List) uiContext
					.get("alreadySelectAccount");
		}
		if (uiContext.get("setValueMap") != null) {
			setValueMap = (Map) uiContext.get("setValueMap");
		}
		if (uiContext.get("alreadySetValueAccount") != null) {
			alreadySetValueAccount = (List) uiContext
					.get("alreadySetValueAccount");
		}
		fillCostAccountToTable(curProjectId);
		if (alreadySelectedAccount != null && alreadySelectedAccount.size() > 0) {
			refreshTableByIsSelected(alreadySelectedAccount);
		}

	}

	/**
	 * 获取工程项目对应的成本科目
	 * 
	 * @param curProjectId
	 * @return
	 */
	protected CostAccountCollection getAllCostAccountByCurProjectId(
			String curProjectId) {
		CostAccountCollection cols = null;
		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("parent.*"));
		viewInfo.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject", curProjectId));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		SorterItemCollection sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("longnumber"));
		viewInfo.setSorter(sort);
		viewInfo.setFilter(filter);
		try {
			cols = CostAccountFactory.getRemoteInstance()
					.getCostAccountCollection(viewInfo);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cols;
	}

	protected void initTableStyle() {
		kDTable1.checkParsed();
		KDCheckBox chkBox = new KDCheckBox();
		final KDTDefaultCellEditor editor = new KDTDefaultCellEditor(chkBox);
		editor.addCellEditorListener(new KDTCellEditorListener() {

			public void editingCanceled() {
				// TODO Auto-generated method stub

			}

			public void editingChanged() {
				// TODO Auto-generated method stub
				KDCheckBox chk = (KDCheckBox) editor.getComponent();
				boolean isSelected = chk.isSelected();
				int rowIndex = kDTable1.getSelectManager().getActiveRowIndex();
				CostAccountInfo info = (CostAccountInfo) kDTable1.getCell(
						rowIndex, "id").getUserObject();
				if (isSelected) {

					Set costSet = new HashSet();
					// 选取所有一级和二级科目
					getAllParentAccount(info, costSet);
					if (costSet.size() > 0) {
						CostAccountInfo otherInfo = null;
						CostAccountInfo cellInfo = null;
						// 把所有的下级科目都选上
						for (Iterator it = costSet.iterator(); it.hasNext();) {
							otherInfo = (CostAccountInfo) it.next();
							for (int i = 0; i < kDTable1.getRowCount(); i++) {
								cellInfo = (CostAccountInfo) kDTable1.getCell(
										i, "id").getUserObject();
								if (cellInfo.getId().toString().equals(
										otherInfo.getId().toString())) {
									kDTable1.getCell(i, "id").setValue(
											Boolean.TRUE);
								}
							}
						}
					}

				} else {
					if (setValueMap != null) {
						if (setValueMap.containsKey(info.getId().toString())) {
							int n = MsgBox.showConfirm2New(null, "当前科目:"
									+ info.getName()
									+ " 已经存在数据，重新设置将清除原有数据，是否进行 ");
							if (JOptionPane.NO_OPTION == n) {
								// kDTable1.getCell(rowIndex,
								// "id").setValue(Boolean.TRUE);
								chk.setSelected(true);
								abort();
							} else {
								// kDTable1.getCell(rowIndex,
								// "id").setValue(Boolean.FALSE);
								chk.setSelected(false);
							}
						}
					}

					// 取出所有下级科目
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("longnumber", info
									.getLongNumber()
									+ "!%", CompareType.LIKE));
					filter.getFilterItems().add(
							new FilterItemInfo("curProject", projectInfo
									.getId().toString()));
					view.setFilter(filter);
					List childrenList = null;
					try {
						CostAccountCollection cols = CostAccountFactory
								.getRemoteInstance().getCostAccountCollection(
										view);
						if (cols.size() > 0) {
							childrenList = new ArrayList();
							for (Iterator it = cols.iterator(); it.hasNext();) {
								childrenList.add(((CostAccountInfo) it.next())
										.getId().toString());
							}
						}
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CostAccountInfo otherInfo = null;

					// 如果取消了上级科目，则取消所有下级科目
					for (int i = 0; i < kDTable1.getRowCount(); i++) {
						otherInfo = (CostAccountInfo) kDTable1.getCell(i, "id")
								.getUserObject();
						// if(alreadySetValueAccount != null){
						// if(alreadySetValueAccount.contains(otherInfo)){
						// int n = MsgBox.showConfirm2New(null,
						// "当前科目:"+otherInfo
						// .getName()+" 已经存在数据，重新设置将清除原有数据，是否进行 ");
						// if(JOptionPane.NO_OPTION == n){
						// kDTable1.getCell(i, "id").setValue(Boolean.TRUE);
						// abort();
						// }
						// }
						// }
						if (childrenList != null
								&& childrenList.contains(otherInfo.getId()
										.toString())) {

							if (setValueMap != null) {
								if (setValueMap.containsKey(otherInfo.getId()
										.toString())) {
									int n = MsgBox
											.showConfirm2New(
													null,
													"当前科目:"
															+ otherInfo
																	.getName()
															+ " 已经存在数据，重新设置将清除原有数据，是否进行 ");
									if (JOptionPane.NO_OPTION == n) {
										// kDTable1.getCell(rowIndex,
										// "id").setValue(Boolean.TRUE);
										chk.setSelected(true);
									} else {
										// kDTable1.getCell(i,
										// "id").setValue(Boolean.FALSE);
										chk.setSelected(false);
									}
								} else {
									kDTable1.getCell(i, "id").setValue(
											Boolean.FALSE);
								}
							} else {
								kDTable1.getCell(i, "id").setValue(
										Boolean.FALSE);
							}

						}

					}
				}

			}

			public void editingStopped() {
				// TODO Auto-generated method stub
			}
		});
		this.kDTable1.getColumn("id").setEditor(editor);
		this.kDTable1.getColumn("number").setWidth(150);
		this.kDTable1.getColumn("name").setWidth(205);
		this.kDTable1.getColumn("number").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("name").getStyleAttributes().setLocked(true);
	}

	/***
	 * 获取该科目的所有上级科目
	 */
	protected void getAllParentAccount(CostAccountInfo costAccount, Set set) {
		CostAccountInfo info = costAccount;
		if (info.getParent() != null) {
			set.add(info.getParent());
			info = info.getParent();
			getAllParentAccount(info, set);
		}
	}

	/**
	 * 填充成本科目到KDTable中去
	 */
	protected void fillCostAccountToTable(String curProjectId) {
		CostAccountCollection cols = getAllCostAccountByCurProjectId(curProjectId);
		kDTable1.checkParsed();
		IRow row = null;
		CostAccountInfo account = null;
		if (cols.size() > 0) {

			for (Iterator it = cols.iterator(); it.hasNext();) {
				row = this.kDTable1.addRow();
				account = (CostAccountInfo) it.next();
				// 当前科目已经被选中
				if (alreadySelectedAccount != null
						&& alreadySelectedAccount.size() > 0) {
					if (!alreadySelectedAccount.contains(account)) {
						row.getCell("id").setValue(Boolean.FALSE);
					} else {
						row.getCell("id").setValue(Boolean.TRUE);
						if (account.getLevel() <= 2) {
							row.getCell("id").getStyleAttributes().setLocked(
									true);
							row.getStyleAttributes().setBackground(lock);
						}
					}
				} else {
					if (account.getLevel() <= 2) {
						row.getCell("id").setValue(Boolean.TRUE);
						row.getCell("id").getStyleAttributes().setLocked(true);
						row.getStyleAttributes().setBackground(lock);
					} else {
						row.getCell("id").setValue(Boolean.FALSE);
					}

				}
				row.getCell("id").setUserObject(account);
				row.getCell("number").setValue(
						account.getLongNumber().replace('!', '.'));
				row.getCell("name").setValue(account.getName());
			}
		}

		// for(int i = 0 ;i<kDTable1.getRowCount();i++){
		// row = this.kDTable1.getRow(i);
		// account = (CostAccountInfo) row.getCell("id").getUserObject();
		// if(!alreadySelectedAccount.contains(account)){
		//				
		// }
		// }

	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionOk_actionPerformed
	 */
	public void actionOk_actionPerformed(ActionEvent e) throws Exception {
		super.actionOk_actionPerformed(e);
		accountList = new ArrayList();
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			boolean isChecked = ((Boolean) kDTable1.getCell(i, "id").getValue())
					.booleanValue();
			if (isChecked) {
				CostAccountInfo account = (CostAccountInfo) kDTable1.getCell(i,
						"id").getUserObject();
				// accountMap.put(account.getLongNumber(),account);
				accountList.add(account);
				// if(account.getParent() != null &&
				// !accountList.contains(account.getParent()) ){
				// accountList.add(account.getParent());
				// }
			}

		}
		// refreshTableByIsSelected(accountList);
		// uiContext.put("account",accountMap);
		parentUI.fillCostAccount(accountList);
		this.destroyWindow();
	}

	/**
	 * output actionCancelSelect_actionPerformed
	 */
	public void actionCancelSelect_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelSelect_actionPerformed(e);
		this.disposeUIWindow();
	}

	public void refreshTableByIsSelected(List selectedAccount) {
		KDTable table = this.kDTable1;
		if (table.getRowCount() > 0) {
			for (int i = 0; i < table.getRowCount(); i++) {
				boolean isSelected = selectedAccount.contains(table.getCell(i,
						"id").getUserObject());
				boolean cellValue = Boolean.valueOf(
						table.getCell(i, "id").getValue().toString())
						.booleanValue();
				if (isSelected != cellValue && isSelected) {
					table.getCell(i, "id")
							.setValue(Boolean.valueOf(isSelected));
				}
			}
		}
	}

}