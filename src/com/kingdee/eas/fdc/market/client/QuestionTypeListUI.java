/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;

import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineFactory;
import com.kingdee.eas.fdc.market.QuestionTypeInfo;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class QuestionTypeListUI extends AbstractQuestionTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(QuestionTypeListUI.class);

	/**
	 * output class constructor
	 */
	public QuestionTypeListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}


	public void onLoad() throws Exception {
		super.onLoad();
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}	
	}
	
	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		TreePath path =this.treeMain.getSelectionPath();
		if (path == null) {
			return;
		}
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();
		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof QuestionTypeInfo) {
			QuestionTypeInfo questTypeInfo = (QuestionTypeInfo) treenode.getUserObject();
			if (questTypeInfo != null) {
				getUIContext().put("questionType", questTypeInfo);
			}
		} 
		
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
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paperType.id", id));
		if (QuestionPaperDefineFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("该问卷类别已被问卷定义引用，不能删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}


	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionTypeFactory.getRemoteInstance();
	}

	/**
	 * output getTreeInterface method
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionTypeFactory.getRemoteInstance();
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
		return "问卷类别";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.QuestionTypeInfo objectValue = new com.kingdee.eas.fdc.market.QuestionTypeInfo();

		return objectValue;
	}

	
	protected boolean isIncludeAllChildren() {
		return true;
	}
}