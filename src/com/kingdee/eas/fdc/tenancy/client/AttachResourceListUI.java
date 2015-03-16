/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.BuildingEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;

/**
 * output class name
 */
public class AttachResourceListUI extends AbstractAttachResourceListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AttachResourceListUI.class);
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
    /**
     * output class constructor
     */
    public AttachResourceListUI() throws Exception
    {
        super();
    }
    
    protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
    
    protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		//this.actionQuery.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellproject.id", sellProject.getId()
							.toString()));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellproject.id", subarea
							.getSellProject().getId().toString()));
			filter.getFilterItems()
					.add(new FilterItemInfo("subarea.id", subarea.getId()
									.toString()));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else if(node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		}
		else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
		}	
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);
			uiContext.put("subarea", null);
			uiContext.put("buildingInfo", null);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			SellProjectInfo sellProject = null;
			if(subarea.getSellProject()!=null && subarea.getSellProject().getId()!=null)
				try
				{
					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(subarea.getSellProject().getId().toString())));
				} catch (Exception e1)
				{
					e1.printStackTrace();
				} 
			uiContext.put("sellProject", sellProject);
			uiContext.put("subarea", subarea);
			uiContext.put("buildingInfo", null);
		} else if(node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();
			SellProjectInfo sellProject = null;
			SubareaInfo subarea = null;
			if(buildingInfo.getSellProject()!=null && buildingInfo.getSellProject().getId()!=null)
			{
			  try
				{
					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSellProject().getId().toString())));
					subarea = (SubareaInfo) SubareaFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSubarea().getId().toString())));
				} catch (Exception e2)
				{
				   e2.printStackTrace();
				} 
				uiContext.put("sellProject", sellProject);
				uiContext.put("subarea",subarea);
				uiContext.put("buildingInfo", buildingInfo);
			}
		}
		super.prepareUIContext(uiContext, e);
	}
	
//	protected void atcionView_actionPerformed(ActionEvent e) throws Exception {
//		super.actionView_actionPerformed(e);
//	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected String getEditUIName() {
		return AttachResourceEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AttachResourceFactory.getRemoteInstance();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}

}