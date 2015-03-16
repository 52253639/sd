/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Rectangle;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgStructureFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * by ....wancheng
 */
public class AccountSignReportFilterUI extends AbstractAccountSignReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccountSignReportFilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys));
    
    public AccountSignReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.txtFromDays.setValue(null);
		this.txtToDays.setValue(null);
		this.cbIsPre.setSelected(true);
		this.cbIsPur.setSelected(true);
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
        if(this.txtFromDays.getValue()!=null){
        	BigDecimal fromDays = (BigDecimal)txtFromDays.getValue();
        	pp.setBigDecimal("fromDays", fromDays);
        }else{
        	pp.setBigDecimal("fromDays", null);
        }
        if(this.txtToDays.getValue()!=null){
        	BigDecimal toDays = (BigDecimal)txtToDays.getValue();
        	pp.setBigDecimal("toDays", toDays);
        }else{
        	pp.setBigDecimal("toDays", null);
        }
        pp.setObject("isPre", this.cbIsPre.isSelected());
        pp.setObject("isPur", this.cbIsPur.isSelected());
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
		this.txtFromDays.setValue(params.getObject("fromDays"));
		this.txtToDays.setValue(params.getObject("toDays"));
		this.cbIsPre.setSelected(params.getBoolean("isPre"));
		this.cbIsPur.setSelected(params.getBoolean("isPur"));
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
			return getParentOrgInfo(OrgStructureFactory.getRemoteInstance().getOrgStructureInfo(new ObjectUuidPK(info.getParent().getId()), sel));
		}
	}
	public static TreeModel buildOrgTree() throws Exception{
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if(orgUnit==null){
			MsgBox.showInfo("非售楼组织不能操作");
			SysUtil.abort();
		}
		KingdeeTreeModel model=null;
		KDTreeNode root=null; 
		OrgStructureInfo rootInfo=OrgStructureFactory.getRemoteInstance().getOrgStructureInfo("select id,parent.id,unit.id,unit.name,level from where unit.id='"+orgUnit.getId()+"' and tree.id='00000000-0000-0000-0000-0000000000064F2827FD'");
		if(orgUnit.getLevel()<4){
			root = new KDTreeNode(orgUnit.getName());
			root.setUserObject(rootInfo);
			model=new KingdeeTreeModel(root);
			OrgStructureCollection col=OrgStructureFactory.getRemoteInstance().getOrgStructureCollection("select id,unit.id,unit.name from where level=4 and tree.id='00000000-0000-0000-0000-0000000000064F2827FD' and longnumber like '"+orgUnit.getNumber()+"!%' order by unit.number");
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
	public static TreeModel getSellProjectForSHESellProject(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam)throws Exception {
		TreeModel tree = buildOrgTree();
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getRoot();

		OrgUnitInfo saleOrgInfo = SysContext.getSysContext().getCurrentOrgUnit();
		Map orgIdMap = getAllObjectIdMap(rootNode);
		Map spIdToNode = new HashMap(); 
		String orgIdsSql = "select fid from t_org_sale where fid = '"+saleOrgInfo.getId()+"'" +" or flongNumber like '"+saleOrgInfo.getLongNumber()+"!%' "; 
		
		SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection("select * where isForShe = 1 and orgUnit.id in ("+orgIdsSql+") order by level,number");

		for (int i = 0; i < sellProjectColl.size(); i++) {
			SellProjectInfo thisSpInfo = sellProjectColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
			if (spIdToNode.get(thisSpInfo.getId().toString()) != null)
				continue;

			if (thisSpInfo.getOrgUnit()!=null && thisSpInfo.getLevel() == 1) {
				DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode) orgIdMap.get(thisSpInfo.getOrgUnit().getId().toString());
				if (orgNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						orgNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			} else {
				SellProjectInfo parentSpInfo = sellProjectColl.get(i).getParent();
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
		OrgStructureCollection col=OrgStructureFactory.getRemoteInstance().getOrgStructureCollection("select unit.id from where parent.id='"+objectInfo.getId()+"'");
		for(int i=0;i<col.size();i++){
			putAllSellProjectId(col.get(i),idMap,thisNode);
			idMap.put(col.get(i).getUnit().getId().toString(), thisNode);
		}
	}
	public static void fillTreeNodeIdMap(Map idMap, TreeNode treeNode) throws BOSException {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode != null&&thisNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo objectInfo = (OrgStructureInfo) thisNode.getUserObject();
			idMap.put(objectInfo.getUnit().getId().toString(), thisNode);
			
			putAllSellProjectId(objectInfo,idMap,thisNode);
		}
		int childCount = treeNode.getChildCount();
		while (childCount > 0) {
			fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1));
			childCount--;
		}
	}
}