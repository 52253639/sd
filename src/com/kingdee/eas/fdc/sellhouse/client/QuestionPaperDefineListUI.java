/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.User;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperDefineFactory;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuestionPaperDefineListUI extends AbstractQuestionPaperDefineListUI
{
	private static final Logger logger = CoreUIObject.getLogger(QuestionPaperDefineListUI.class);

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);

		this.actionGuideAdd.setEnabled(true);
		this.actionSimulateAnswer.setEnabled(true);
		
		this.kDTree.setModel(FDCTreeHelper.getSaleOrgTree(this.actionOnLoad));
		this.kDTree.expandAllNodes(true, (TreeNode) this.kDTree.getModel().getRoot());
		this.kDTree.setSelectionNode((DefaultKingdeeTreeNode)this.kDTree.getModel().getRoot());

	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * output class constructor
	 */
	public QuestionPaperDefineListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
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
		
		String id = this.getSelectedKeyValue();
		CommerceHelper.checkBeforEditOrDelete(QuestionPaperDefineFactory.getRemoteInstance()
						.getObjectBaseInfo("select orgUnit where id='"+id+"'"));
		
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = this.getSelectedKeyValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("questionPaper.id", id));
		if (QuestionPaperAnswerFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("该问卷定义已被问卷回答引用，不能删除！");
			return;
		}
		CommerceHelper.checkBeforEditOrDelete(QuestionPaperDefineFactory.getRemoteInstance()
				.getObjectBaseInfo("select orgUnit where id='"+id+"'"));
		
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance();
	}

	/**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.QuestionPaperDefineInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperDefineInfo();

		return objectValue;
	}
	/**
	 * 新增EditUI在新页签打开 hjx编写
	 */
	// protected String getEditUIModal()
	// {
	// String openModel = UIConfigUtility.getOpenModel();
	// if (openModel != null)
	// {
	// return UIFactoryName.NEWWIN;
	// }
	// else
	// {
	// return UIFactoryName.NEWTAB;
	// }
	// }
    protected OrgType getMainBizOrgType()    {
        return OrgType.Sale;
    }	

    protected String getEditUIName() {
    	return QuestionPaperDefineEditUI.class.getName();
    }
 
    
    public void actionJumpSet_actionPerformed(ActionEvent e) throws Exception {
    	super.actionJumpSet_actionPerformed(e);
    	
    	String selectID = this.getSelectedKeyValue();
    	if(selectID==null) return;
    	
    	UIContext uiContext = new UIContext(this); 
		String opera = OprtState.EDIT;
		uiContext.put(UIContext.ID, selectID);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
								.create(QuestPaperDocItemJumpSetUI.class.getName(), uiContext, null, opera);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
			SysUtil.abort();
		}
    }
    
    public void actionGuideAdd_actionPerformed(ActionEvent e) throws Exception {
    	CommerceHelper.openTheUIWindow(this, QuestPaperGuideAddUI.class.getName(), null);
    	this.refreshList();
    }
 
    public void actionSimulateAnswer_actionPerformed(ActionEvent e)
    		throws Exception {
    	String selectId = this.getSelectedKeyValue();
    	if(selectId ==null) return;
    	
    	QuestionPaperDefineInfo questDefineInfo = QuestionPaperDefineFactory.getRemoteInstance().getQuestionPaperDefineInfo("select documentId where id = '"+selectId+"'");
    	UIContext uiContext = new UIContext(this); 		
		uiContext.put("QuestionDefine", questDefineInfo);
		uiContext.put("SimulateAnser", "模拟答题");    	

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuestionAnswerIndexUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();   
    }
 
    
    protected void kDTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.execQuery();
    }
    
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) this.kDTree.getLastSelectedPathComponent();
    	OrgStructureInfo orgInfo = (OrgStructureInfo)proNode.getUserObject();
    	
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		if(proNode.isLeaf()){
			this.tblMain.getColumn("orgUnit").getStyleAttributes().setHided(true);
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgInfo==null?null:orgInfo.getUnit().getId()));
		}else{
			this.tblMain.getColumn("orgUnit").getStyleAttributes().setHided(false);
			String idsStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(proNode, "OrgStructure").keySet()); 
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", idsStr, CompareType.INNER));
		}
		
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		return super.getQueryExecutor(queryPK, viewInfo);
    }
    
}