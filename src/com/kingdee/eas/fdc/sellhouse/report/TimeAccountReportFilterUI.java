/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgStructure;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgStructureFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseCollection;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitFactory;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TimeAccountReportFilterUI extends AbstractTimeAccountReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(TimeAccountReportFilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,true));
    public TimeAccountReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.cbIsLock.setSelected(true);
		this.cbIsAll.setSelected(true);
		this.cbIsFitment.setSelected(true);
	}
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.pkFromDate.getValue()!=null){
    		 pp.setObject("fromDate", this.pkFromDate.getValue());
         }else{
        	 pp.setObject("fromDate", null);
         }
         if(this.pkToDate.getValue()!=null){
    		 pp.setObject("toDate", this.pkToDate.getValue());
         }else{
        	 pp.setObject("toDate", null);
         }
         if(this.txtSellProject.getUserObject()!=null){
        	 pp.setObject("tree", this.txtSellProject.getUserObject());
         }else{
        	 pp.setObject("tree", null);
         }
         if(this.txtSellProject.getText()!=null){
        	 pp.setObject("txtSp", this.txtSellProject.getText());
         }else{
        	 pp.setObject("txtSp", null);
         }
    	 pp.setObject("baseTree", this.dialog.getTree());
    	 pp.setObject("isLock", this.cbIsLock.isSelected());
    	 pp.setObject("isAll", this.cbIsAll.isSelected());
    	 pp.setObject("isFitment", this.cbIsFitment.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		if(params.getObject("tree")!=null){
			try {
				List sellProject=new ArrayList();
				getSellProjectName(sellProject,((DefaultKingdeeTreeNode) ((TreeModel)params.getObject("tree")).getRoot()));
				String spText="";
				for(int i=0;i<sellProject.size();i++){
					spText=spText+sellProject.get(i).toString()+";";
				}
				this.txtSellProject.setText(spText);
				this.txtSellProject.setUserObject(params.getObject("tree"));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		this.cbIsAll.setSelected(params.getBoolean("isAll"));
		this.cbIsLock.setSelected(params.getBoolean("isLock"));
	}
	public void getSellProjectName(List sellProject,DefaultKingdeeTreeNode node) throws BOSException{
		if(node.getUserObject() instanceof SellProjectInfo) {
			sellProject.add(((SellProjectInfo)node.getUserObject()).getName());
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			getSellProjectName(sellProject,(DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		dialog.show();
		if(dialog.getSelectTree()!=null){
			List sellProject=new ArrayList();
			getSellProjectName(sellProject,(DefaultKingdeeTreeNode) dialog.getSelectTree().getRoot());
			String spText="";
			for(int i=0;i<sellProject.size();i++){
				spText=spText+sellProject.get(i).toString()+";";
			}
			this.txtSellProject.setText(spText);
			this.txtSellProject.setUserObject(dialog.getSelectTree());
		}
	}
	public static OrgStructureInfo getParentOrgInfo(OrgStructureInfo info) throws EASBizException, BOSException{
		if(info.getLevel()==4){
			return info;
		}else{
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("id");
			sel.add("parent.id");
			sel.add("unit.id");
			sel.add("unit.name");
			sel.add("level");
			sel.add("unit.longNumber");
			return getParentOrgInfo(OrgStructureFactory.getRemoteInstance().getOrgStructureInfo(new ObjectUuidPK(info.getParent().getId()), sel));
		}
	}
	public static TreeModel buildOrgTree() throws Exception{
		IOrgStructure iOrgStructure=OrgStructureFactory.getRemoteInstance();
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if(orgUnit==null){
			MsgBox.showInfo("非售楼组织不能操作");
			SysUtil.abort();
		}
		KingdeeTreeModel model=null;
		KDTreeNode root=null; 
		OrgStructureInfo rootInfo=iOrgStructure.getOrgStructureInfo("select id,parent.id,unit.id,unit.name,level,unit.longNumber from where unit.id='"+orgUnit.getId()+"' and tree.id='00000000-0000-0000-0000-0000000000064F2827FD'");
		if(orgUnit.getLevel()<4){
			root = new KDTreeNode(orgUnit.getName());
			root.setUserObject(rootInfo);
			model=new KingdeeTreeModel(root);
			OrgStructureCollection col=iOrgStructure.getOrgStructureCollection("select id,unit.id,unit.name,unit.longNumber from where level=4 and tree.id='00000000-0000-0000-0000-0000000000064F2827FD' and longnumber like '"+orgUnit.getNumber()+"!%' order by unit.number");
			for(int i=0;i<col.size();i++){
				KDTreeNode child = new KDTreeNode(col.get(i).getUnit().getName());
				child.setUserObject(col.get(i));
				root.add(child);
			}
		}else if(orgUnit.getLevel()==4){
			root = new KDTreeNode(orgUnit.getName());
			root.setUserObject(rootInfo);
			model=new KingdeeTreeModel(root);
		}else if(orgUnit.getLevel()>4){
			OrgStructureInfo info=getParentOrgInfo(rootInfo);
		
			root = new KDTreeNode(info.getUnit().getName());
			root.setUserObject(info);
			model=new KingdeeTreeModel(root);
		}
		model.setRoot(root);
		return model;
	}
	public static TreeModel getSellProjectForSHESellProject(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam,boolean isAll)throws Exception {
		TreeModel tree = buildOrgTree();
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getRoot();
		Map orgMap =getAllObjectIdMap(rootNode);
		SellProjectCollection spColl=null;
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		Map spIdToNode = new HashMap(); 
		String orgIdsSql = "select fid from t_org_sale where fid = '"+orgUnit.getId()+"'" +" or flongNumber like '"+orgUnit.getLongNumber()+"!%' "; 
		
		if(isAll){
			spColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection("select * where isForShe = 1 and orgUnit.id in ("+orgIdsSql+") order by level,number");
		}else{
			spColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection("select * where isForShe = 1 and level=1 and orgUnit.id in ("+orgIdsSql+") order by level,number");
		}
		for (int i = 0; i < spColl.size(); i++) {
			SellProjectInfo thisSpInfo = spColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
			if (spIdToNode.get(thisSpInfo.getId().toString()) != null)
				continue;

			if (thisSpInfo.getOrgUnit()!=null && thisSpInfo.getLevel() == 1) {
				DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode) orgMap.get(thisSpInfo.getOrgUnit().getId().toString());
				if (orgNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						orgNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			} else {
				SellProjectInfo parentSpInfo = thisSpInfo.getParent();
				if (parentSpInfo == null)
					continue;
				DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) spIdToNode.get(parentSpInfo.getId().toString());
				if (parentNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						parentNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			}
		}
		return tree;
	}
	public static Map getAllObjectIdMap(TreeNode treeNode) throws BOSException {
		Map idMap = new HashMap();
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		fillTreeNodeIdMap(idMap, treeNode);
		return idMap;
	}
	public static void putAllSellProjectId(OrgStructureInfo objectInfo,Map idMap, TreeNode thisNode) throws BOSException{
		OrgStructureCollection col=OrgStructureFactory.getRemoteInstance().getOrgStructureCollection("select unit.id from where unit.longNumber like '"+objectInfo.getUnit().getLongNumber()+"!%'");
		for(int i=0;i<col.size();i++){
			idMap.put(col.get(i).getUnit().getId().toString(), thisNode);
		}
	}
	public static void fillTreeNodeIdMap(Map idMap, TreeNode treeNode) throws BOSException {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode != null&&thisNode.getUserObject() instanceof OrgStructureInfo) {
			int childCount = treeNode.getChildCount();
			OrgStructureInfo objectInfo = (OrgStructureInfo) thisNode.getUserObject();
			
			if(childCount==0){
				putAllSellProjectId(objectInfo,idMap,thisNode);
			}
			while (childCount > 0) {
				fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}
	protected void cbIsAll_actionPerformed(ActionEvent e) throws Exception {
		dialog=new TreeSelectDialog(this,getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,this.cbIsAll.isSelected()));
		this.txtSellProject.setText(null);
		this.txtSellProject.setUserObject(null);
	}
	
}