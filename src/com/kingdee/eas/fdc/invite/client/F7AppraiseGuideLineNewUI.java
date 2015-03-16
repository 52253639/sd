/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineType;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.scm.common.client.GeneralF7TreeListUI;
import com.kingdee.eas.scm.common.client.IF7Provider;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class F7AppraiseGuideLineNewUI extends AbstractF7AppraiseGuideLineNewUI	implements IF7Provider {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3248213500818224792L;
	private static final Logger logger = CoreUIObject
			.getLogger(F7AppraiseGuideLineNewUI.class);

	private FilterInfo quickFilterInfo;

	private IMetaDataPK queryPK;

	private boolean isMultiSelect = false;

	private IMetaDataPK treeBasePK;

	private String treeBaseBOSType;

	private String propName;
	private FilterInfo defaultFilterInfo;

	private DefaultKingdeeTreeNode oldTreeNode = null;

	/**
	 * output class constructor
	 */
	public F7AppraiseGuideLineNewUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {

		setIsNeedDefaultFilter(false);
		this.actionQuery.setVisible(false);
		super.onLoad();
		//this.actionQuery.setVisible(true);
		//this.treeView.setTitle("评标指标类型");
		
		// this.setAssociatePropertyName("guideLineType.id");
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("isEnable",Boolean.TRUE));
		// this.setFilterForQuery(filter);
		// addKDTableLisener();
		
		tblMain.setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_AUTO);
		
		tblMain.setRefresh(true);
		tblMain.reLayoutAndPaint();

		this.treeView.setShowControlPanel(false);

	}

	public IMetaDataPK getTreeBasePK() {
		return treeBasePK;
	}

	public void setTreeBasePK(IMetaDataPK treeBasePK) {
		this.treeBasePK = treeBasePK;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public FilterInfo getDefaultFilterInfo() {
		return defaultFilterInfo;
	}

	public void setDefaultFilterInfo(FilterInfo defaultFilterInfo) {
		this.defaultFilterInfo = defaultFilterInfo;
	}

	public FilterInfo getQuickFilterInfo() {
		return quickFilterInfo;
	}

	public IMetaDataPK getQueryPK() {
		return queryPK;
	}

	public String getTreeBaseBOSType() {
		return treeBaseBOSType;
	}

	public EntityViewInfo getDefaultQuery() {
		EntityViewInfo evi = this.mainQuery;

		FilterInfo tmpFilterInfo;
		try {
			tmpFilterInfo = (FilterInfo) defaultFilterInfo.clone();
			if (quickFilterInfo != null) {
				tmpFilterInfo.mergeFilter(quickFilterInfo, "and");
			}
			tmpFilterInfo.mergeFilter(this.mainQuery.getFilter(), "and");
			evi.setFilter(tmpFilterInfo);
		} catch (Exception e) {
			super.handUIException(e);
		}
		return evi;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
		if (this.mainQuery != null) {
			int start = ((Integer) e.getParam1()).intValue();
			int length = ((Integer) e.getParam2()).intValue() - start + 1;
			try {
				// 这里可以取到QueryPK, 同时我们也要指定ViewInfo
				IQueryExecutor exec;
				if (defaultFilterInfo != null) {
					if (queryPK != null) {
						exec = this.getQueryExecutor(this.queryPK,
								getDefaultQuery());
					} else {
						exec = this.getQueryExecutor(this.mainQueryPK,
								getDefaultQuery());
					}
				} else if (quickFilterInfo != null) {
					if (queryPK != null) {
						exec = this.getQueryExecutor(this.queryPK,
								getMainQuery());
					} else {
						exec = this.getQueryExecutor(this.mainQueryPK,
								getMainQuery());
					}
				} else {
					// 如果没有快速过滤条件时, 则同原有的刷新行为一致
					if (queryPK != null) {
						exec = this.getQueryExecutor(this.queryPK,
								this.mainQuery);
					} else {
						exec = this.getQueryExecutor(this.mainQueryPK,
								this.mainQuery);
					}
				}

				IRowSet rowSet = exec.executeQuery(start, length);
				e.setRowSet(rowSet);
				onGetRowSet(rowSet);
			} catch (Exception ee) {
				handUIException(ee);
			}
		}
	}

	/**
	 * 要重写的方法(选中相应的值)
	 */
	//PostRequisitionEditUI
//	F7MaterialTreeListUI
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
//		if (isOrderForClickTableHead() && e.getClickCount() != 2) {
			super.tblMain_tableClicked(e);
//		}
//		if (getData() != null && e.getClickCount() == 2) {
//			Container c = getParent();
//			while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
//				c = c.getParent();
//			}
//			((GeneralF7TreeListUI) c).clickOKBtn();
//		
//			
//		}
//		// 点击KDTable时，不清空快速查询条件，只有点击树节点时清空
//		oldTreeNode = (DefaultKingdeeTreeNode) treeMain
//				.getLastSelectedPathComponent();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */

	/**
	 * output chkIncludeChild_itemStateChanged method
	 */
	protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		super.chkIncludeChild_itemStateChanged(e);
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
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
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

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output actionGroupAddNew_actionPerformed
	 */
	public void actionGroupAddNew_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionGroupAddNew_actionPerformed(e);
	}

	/**
	 * output actionGroupView_actionPerformed
	 */
	public void actionGroupView_actionPerformed(ActionEvent e) throws Exception {
		super.actionGroupView_actionPerformed(e);
	}

	/**
	 * output actionGroupEdit_actionPerformed
	 */
	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionGroupEdit_actionPerformed(e);
	}

	/**
	 * output actionGroupRemove_actionPerformed
	 */
	public void actionGroupRemove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionGroupRemove_actionPerformed(e);
	}

	/**
	 * output actionGroupMoveTree_actionPerformed
	 */
	public void actionGroupMoveTree_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionGroupMoveTree_actionPerformed(e);
	}

	/**
	 * output actionMoveTree_actionPerformed
	 */
	public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception {
		super.actionMoveTree_actionPerformed(e);
	}

	protected String getGroupEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getQueryFieldName() {
		// TODO Auto-generated method stub
		return (propName != null ? propName : "guidelineType.id");
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		int propColumnNo = -1; // 关联属性所在的列
		// 如果没有设置关联属性时，按照父类的方式返回关联属性PK
		if (propName != null) {
			// 找出关联属性所在列的值
			for (int i = 0; i < tblMain.getColumnCount(); i++) {
				if (propName.equals(tblMain.getColumn(i).getFieldName())) {
					propColumnNo = i;
					break;
				}
			}
			if (propColumnNo != -1) {
				// 如果找到的话， 则返回指定列位置的值作为IObjectPK返回
				KDTSelectBlock sb = tblMain.getSelectManager().get();
				int top = sb.getTop();
				com.kingdee.bos.ctrl.kdf.table.ICell cell = tblMain.getCell(
						top, propColumnNo);
				String id = (String) cell.getValue();
				BOSUuid uuid = BOSUuid.read(id);
				return new ObjectUuidPK(uuid);
			}
			// 找不到的情况下，返回null
			return null;
		} else {
			return null;// super.getSelectedTreeKeyValue();
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return AppraiseGuideLineTypeFactory.getRemoteInstance();
	}

	// public Object getData() {
	// String keyValue = getSelectedKeyValue();
	// if (keyValue != null) {
	// try {
	// //获得TreeListUI对应的业务接口
	// ICoreBase iBiz = getBizInterface();
	// ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
	// return iBiz.getValue(pk);
	// Object[] keyValues = getSelectedIdValues().toArray();
	// if (keyValues != null) {
	// try {
	// //获得TreeListUI对应的业务接口
	// ICoreBase iBiz1 = getBizInterface();
	// ObjectUuidPK pk1 = null;
	// AppraiseGuideLineInfo[] supplierInfo = new
	// AppraiseGuideLineInfo[keyValues.length];
	// for(int i=0,c=keyValues.length;i<c;i++)
	// {
	// pk1 = new ObjectUuidPK(BOSUuid.read(keyValues[i].toString()));
	// supplierInfo[i] = (AppraiseGuideLineInfo)iBiz.getValue(pk1);
	// }
	// if(keyValues.length == 1)
	// {
	// return supplierInfo[0];
	// }else
	// {
	// return supplierInfo;
	// }
	// } catch (Exception er) {
	// super.handUIException(er);
	// }
	// return null;
	// }

	public Object getData() {
		if (this.isMultiSelect) {
			Object[] keyValues = getSelectedIdValues().toArray();
			if (keyValues != null && keyValues.length > 0) {
				try {
					// 获得TreeListUI对应的业务接口
					ICoreBase iBiz = getBizInterface();
					ObjectUuidPK pk = null;
					AppraiseGuideLineInfo[] Info = new AppraiseGuideLineInfo[keyValues.length];
					for (int i = 0, c = keyValues.length; i < c; i++) {
						pk = new ObjectUuidPK(BOSUuid.read(keyValues[i]
								.toString()));
						Info[i] = (AppraiseGuideLineInfo) iBiz
								.getValue(pk);
					}
					if (keyValues.length == 1) {
						return Info[0];
					} else {
						return Info;
					}
				} catch (Exception er) {
					super.handUIException(er);
				}
			}
		} else {
			String keyValue = getSelectedKeyValue();
			if (keyValue != null) {
				try {
					// 获得TreeListUI对应的业务接口
					ICoreBase iBiz = getBizInterface();
					ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
					System.out.println(pk);
					Object o = 	iBiz.getValue(pk);
					//System.out.println(o.toString());
					return o ;
				} catch (Exception er) {
					super.handUIException(er);
				}
			}
		}
		return null;
	}

	public void setAssociatePropertyName(String propName) {
		// TODO Auto-generated method stub
               

	}

	public void setFilterCU(String queryName, OrgUnitCollection bizOrgCollection) {
		// TODO Auto-generated method stub

	}

	public void setFilterCUID(String cuId) {
		// TODO Auto-generated method stub

	}

	public void setMultiSelect(boolean isMulti) {
		// TODO Auto-generated method stub

	}

	public void setQueryPK(IMetaDataPK pk) {
		this.queryPK = pk;

	}

	public void setQuickFilterInfo(FilterInfo quickFilterInfo) {
		this.quickFilterInfo = quickFilterInfo;

	}

	public void setTreeBaseBOSType(String type) {
		// TODO Auto-generated method stub
	   

	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		quickFilterInfo = null;
		super.treeMain_valueChanged(e);
		KDTreeNode treeNode = (KDTreeNode) treeMain.getLastSelectedPathComponent();
		if (treeNode.getParent() != null) {
			quickFilterInfo = null;
		}
	}

	public void setFilterInfo(FilterInfo defaultFilterInfo,
			FilterInfo quickFilterInfo) {
		this.defaultFilterInfo = defaultFilterInfo;
		this.quickFilterInfo = quickFilterInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return AppraiseGuideLineFactory.getRemoteInstance();
	}

}