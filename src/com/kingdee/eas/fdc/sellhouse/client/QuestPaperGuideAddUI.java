/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.print.TableUtil;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.plaf.KingdeeTreeUI;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineCollection;
import com.kingdee.eas.fdc.market.QuestionPaperDefineFactory;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.StoreItemCollection;
import com.kingdee.eas.fdc.market.StoreItemFactory;
import com.kingdee.eas.fdc.market.StoreItemInfo;
import com.kingdee.eas.fdc.market.StoreOptionCollection;
import com.kingdee.eas.fdc.market.StoreOptionInfo;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;
import com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class QuestPaperGuideAddUI extends AbstractQuestPaperGuideAddUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestPaperGuideAddUI.class);
    
    /**
     * output class constructor
     */
    public QuestPaperGuideAddUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    //-------------
//    	this.getUI().getPreferredSize(this).setSize(500, 300);
//    	this.getUI().getPreferredSize(this).setSize(660, 380);
    	this.orgUnitTree.setModel(getModel());
		this.orgUnitTree.expandAllNodes(true, (TreeNode) this.orgUnitTree.getModel().getRoot());
		this.orgUnitTree.setSelectionNode((DefaultKingdeeTreeNode)this.orgUnitTree.getModel().getRoot());
    	this.tblMain.getStyleAttributes().setLocked(true);
    	
    	//src start 只要复制问卷功能
    	this.radioAddNew.setSelected(false);
    	this.radioCopyNew.setSelected(true);
    	this.radioSelectAdd.setSelected(false);
    	this.radioAddNew.setVisible(false);
    	this.radioSelectAdd.setVisible(false);
    	//src end
    }
    private TreeModel getModel() throws Exception{
    	FullOrgUnitInfo org=CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)).castToFullOrgUnitInfo();
    	TreeModel model=FDCTreeHelper.getSaleOrgTree(actionOnLoad,org);
		return model;
    }
    
    protected void orgUnitTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
    	super.orgUnitTree_valueChanged(e);
    	tblMain.removeRows();
    	tblMain.checkParsed();
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)orgUnitTree.getLastSelectedPathComponent();
		if(treeNode !=null && treeNode.getUserObject() instanceof OrgStructureInfo){
			QuestPaperBizSceneEnum bizScene = (QuestPaperBizSceneEnum)this.combBizScene.getSelectedItem();
			OrgStructureInfo osInfo = (OrgStructureInfo)treeNode.getUserObject();
			EntityViewInfo evInfo=new EntityViewInfo();
			evInfo.getSelector().add(new SelectorItemInfo("paperType.*"));
			evInfo.getSelector().add(new SelectorItemInfo("*"));
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit",osInfo.getUnit().getId()));
			filter.getFilterItems().add(new FilterItemInfo("bizScene",bizScene.getValue()));
			evInfo.setFilter(filter);
			QuestionPaperDefineCollection  qpdCol=QuestionPaperDefineFactory.getRemoteInstance().getQuestionPaperDefineCollection(evInfo);
			for(int i=0;i<qpdCol.size();i++)	{
				IRow newRow = this.tblMain.addRow();
				QuestionPaperDefineInfo defineInfo = qpdCol.get(i);
				newRow.setUserObject(defineInfo);
				newRow.getCell("questionTopic").setValue(defineInfo.getTopric());
				newRow.getCell("id").setValue(defineInfo.getId());
				newRow.getCell("questionType").setValue(defineInfo.getPaperType());
				newRow.getCell("questionNumber").setValue(defineInfo.getNumber());
			}
		}
	}

	protected void btnNext_actionPerformed(ActionEvent e) throws Exception {
    	QuestPaperBizSceneEnum bizScene = (QuestPaperBizSceneEnum)this.combBizScene.getSelectedItem();
    	
    	if(this.radioAddNew.isSelected()) {
    		this.getUIWindow().close();
        	UIContext uiContext = new UIContext(this); 
        	uiContext.put("QuestionBizScene", bizScene);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
										create(QuestionPaperDefineEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();    		
    		return;
    	}else{
    		this.contBizScene.setVisible(false);
    		this.radioAddNew.setVisible(false);
    		this.radioCopyNew.setVisible(false);
    		this.radioSelectAdd.setVisible(false);
    		this.btnNext.setVisible(false);
    		this.tblMain.setVisible(true);
    		this.btnSure.setVisible(true);
    		
    		if(this.radioCopyNew.isSelected()){  //展示业务场景下的问卷定义列表,只能单选
    			this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    			this.tblMain.getColumn("documentTopic").getStyleAttributes().setHided(true);
    			this.tblMain.getColumn("itemTopic").getStyleAttributes().setHided(true);
    			this.tblMain.getColumn("subjectClass").getStyleAttributes().setHided(true);
    			this.treeScrollPane1.setEnabled(true);//--------
    			this.treeScrollPane1.setVisible(true);
    			this.orgUnitTree.setVisible(true);//-----------
    			this.orgUnitTree.setEnabled(true);//---------TODO
    			((KingdeeTreeUI)orgUnitTree.getUI()).resetSize();
//    			QuestionPaperDefineCollection paperDefineColl = QuestionPaperDefineFactory.getRemoteInstance()
//    										.getQuestionPaperDefineCollection("select *,paperType.* " +
//    												"where bizScene = '"+bizScene.getValue()+"' and orgUnit.id = '"+SysContext.getSysContext().getCurrentOrgUnit().getId()+"' ");
//    			for(int i=0;i<paperDefineColl.size();i++)	{
//    				IRow newRow = this.tblMain.addRow();
//    				QuestionPaperDefineInfo defineInfo = paperDefineColl.get(i);
//    				newRow.setUserObject(defineInfo);
//    				newRow.getCell("questionTopic").setValue(defineInfo.getTopric());
//    				newRow.getCell("id").setValue(defineInfo.getId());
//    				
//    				newRow.getCell("questionType").setValue(defineInfo.getPaperType());
//    				newRow.getCell("questionNumber").setValue(defineInfo.getNumber());
//    			}
    		}else if(this.radioSelectAdd.isSelected()){	//展现问题分组列表，可多选
    			this.tblMain.getColumn("questionTopic").getStyleAttributes().setHided(true);
    			this.tblMain.getColumn("questionType").getStyleAttributes().setHided(true);
    			this.tblMain.getColumn("questionNumber").getStyleAttributes().setHided(true);
    			
    			this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    			StoreItemCollection itemColl = StoreItemFactory.getRemoteInstance()
    									.getStoreItemCollection("select *,options.*,subjectId.topic,subjectId.storeSubClass.name " +
    											"   order by itemNumber ");	//应该要加上组织过滤
    			
    			for(int i=0;i<itemColl.size();i++){
    				IRow newRow = this.tblMain.addRow();
    				StoreItemInfo itemInfo = itemColl.get(i);
    				newRow.setUserObject(itemInfo);
    				newRow.getCell("documentTopic").setValue(itemInfo.getSubjectId().getTopic());
    				newRow.getCell("itemTopic").setValue(itemInfo.getTopic());
    				newRow.getCell("id").setValue(itemInfo.getId());    				
    				newRow.getCell("subjectClass").setValue(itemInfo.getSubjectId().getStoreSubClass());
    			}    			
    		}
    	}
    }
    
    
    protected void btnSure_actionPerformed(ActionEvent e) throws Exception {
    	if(KDTableUtil.getSelectedRowCount(this.tblMain)<1) {
    		FDCMsgBox.showWarning("请先选择！");
    		return;
    	}    		
    	
		if(this.radioCopyNew.isSelected()){  
			this.getUIWindow().close();
        	UIContext uiContext = new UIContext(this); 
        	QuestPaperBizSceneEnum bizScene = (QuestPaperBizSceneEnum)this.combBizScene.getSelectedItem();
        	uiContext.put("QuestionBizScene", bizScene);
        	QuestionPaperDefineInfo questPaperDefinInfo = (QuestionPaperDefineInfo)KDTableUtil.getSelectedRow(this.tblMain).getUserObject();
        	uiContext.put("QuestionPaperDefineInfo", questPaperDefinInfo);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
										create(QuestionPaperDefineEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();    	
		}else if(this.radioSelectAdd.isSelected()){
			int[] rowIndexs = KDTableUtil.getSelectedRows(this.tblMain);
			StoreItemCollection storeItemColl = new StoreItemCollection();
			for (int i = 0; i < rowIndexs.length; i++) {
				StoreItemInfo storeItemInfo = (StoreItemInfo)this.tblMain.getRow(rowIndexs[i]).getUserObject(); 
				storeItemColl.add(storeItemInfo);
			}			
			
			this.getUIWindow().close();
        	UIContext uiContext = new UIContext(this); 
        	QuestPaperBizSceneEnum bizScene = (QuestPaperBizSceneEnum)this.combBizScene.getSelectedItem();
        	uiContext.put("QuestionBizScene", bizScene);        	
        	uiContext.put("DocumentItemCollection", convertStor2Doc(storeItemColl));
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
										create(QuestionPaperDefineEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();    
		}    	
    }
    


	private DocumentItemCollection convertStor2Doc(StoreItemCollection ssColl) {
		DocumentItemCollection docItemColl = new DocumentItemCollection();
		for (int itemI = 0; itemI < ssColl.size(); itemI++) {
			StoreItemInfo sii = ssColl.get(itemI);
			DocumentItemInfo dii = new DocumentItemInfo();
			dii.setTopic(sii.getTopic());
			dii.setItemNumber(itemI);
			dii.setXFontSize(14);
			docItemColl.add(dii);
			//
			DocumentOptionCollection doc = dii.getOptions();
			StoreOptionCollection soc = sii.getOptions();
			for (int optionI = 0; optionI < soc.size(); optionI++) {
				StoreOptionInfo soi = soc.get(optionI);
				DocumentOptionInfo doi = new DocumentOptionInfo();
				doi.setTopic(soi.getTopic());
				doi.setIsTopicInverse(soi.isIsTopicInverse());
				doi.setXLength(soi.getXLength() == null ? 0 : soi.getXLength().intValue());
				doi.setOptionNumber(optionI);
				doi.setXFontSize(12);
				doc.add(doi);
			}
		}
		return docItemColl;
	}    


}