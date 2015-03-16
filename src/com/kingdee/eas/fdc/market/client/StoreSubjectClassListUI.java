/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.market.StoreSubjectClassInfo;
import com.kingdee.eas.fdc.market.StoreSubjectFactory;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class StoreSubjectClassListUI extends AbstractStoreSubjectClassListUI {
	private static final Logger logger = CoreUIObject.getLogger(StoreSubjectClassListUI.class);

	/**
	 * output class constructor
	 */
	public StoreSubjectClassListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}


	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);

/*		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (orgNode == null) {
			return;
		}
		if (orgNode.getUserObject() != null && orgNode.getUserObject() instanceof StoreSubjectClassInfo) {
			StoreSubjectClassInfo storeSubjectClassInfo = (StoreSubjectClassInfo) orgNode.getUserObject();
			if (storeSubjectClassInfo != null) {
				getUIContext().put("storeSubjectClass", storeSubjectClassInfo);
			}
		}
		this.tblMain.removeRows();
		this.execQuery();*/
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
/*
		try {
			viewInfo = new EntityViewInfo();
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();

			String ctrlUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			if (ctrlUnitId != null && ctrlUnitId != "") {
				filter.getFilterItems().add(new FilterItemInfo("CU.id", ctrlUnitId));
			}
		
			if (orgNode != null && orgNode.getUserObject() instanceof StoreSubjectClassInfo) {
				StoreSubjectClassInfo mediaType = (StoreSubjectClassInfo) orgNode.getUserObject();
				if (mediaType != null) {
					String longNumber = mediaType.getLongNumber();
					filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
				}
			}
			viewInfo.setFilter(filter);
			viewInfo.getSorter().add(new SorterItemInfo("level"));
			viewInfo.getSorter().add(new SorterItemInfo("name"));
		} catch (Exception e) {
			handleException(e);
		}
*/			
		return super.getQueryExecutor(queryPK, viewInfo);
	}


	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("storeSubClass.id", id));
			if (StoreSubjectFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("题目类别已被题目引用，不能删除！");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}


	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.StoreSubjectClassFactory.getRemoteInstance();
	}

	/**
	 * output getTreeInterface method
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		return com.kingdee.eas.fdc.market.StoreSubjectClassFactory.getRemoteInstance();
	}

	/**
	 * output getLongNumberFieldName method
	 */
	protected String getLongNumberFieldName() {
		return "longNumber";
	}

	/**
	 * output getRootName method
	 */
	protected String getRootName() {
		return "题库题目类别";
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.StoreSubjectClassInfo objectValue = new com.kingdee.eas.fdc.market.StoreSubjectClassInfo();

		return objectValue;
	}

	protected boolean isIncludeAllChildren() {
		return true;
	}
	
}