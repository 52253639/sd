/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageEnum;
import com.kingdee.eas.fdc.sellhouse.EffectImageFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class EffectImageListUI extends AbstractEffectImageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EffectImageListUI.class);
    
    /**
     * output class constructor
     */
    public EffectImageListUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    public void onLoad() throws Exception {
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsSaleOrgUnit()){
    		MsgBox.showError("不是销售组织不能进入!");
    		abort();
    	}
    	this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad));
    	this.treeMain.expandAllNodes(true,(TreeNode)this.treeMain.getModel().getRoot());
    	this.treeMain.setSelectionNode((DefaultKingdeeTreeNode)this.treeMain.getModel().getRoot());
    	super.onLoad();
    	this.actionAttachment.setVisible(false);
    	this.actionAuditResult.setVisible(false);
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return EffectImageFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return EffectImageEditUI.class.getName();
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		
		super.treeMain_valueChanged(e);
		this.execQuery();
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	DefaultKingdeeTreeNode node=(DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	FilterInfo filter = new FilterInfo();
    	if(node==null){
    		filter.getFilterItems().add(new FilterItemInfo("id",null));
    	}
    	
    	if(node.getUserObject() instanceof OrgStructureInfo) {//选择组织时 加载该组织下 所有项目方位效果图
    		OrgStructureInfo orgStructInfo = (OrgStructureInfo)node.getUserObject();
    		if(orgStructInfo.getUnit()!=null) {
	    		FullOrgUnitInfo info=orgStructInfo.getUnit();
	    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",info.getId()));
	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_SELLPROJECT_VALUE));
    		}
    	}else if(node.getUserObject() instanceof SellProjectInfo) {//选择租售项目时 加载 该租售项目的所有楼栋分布效果图
    		SellProjectInfo info=(SellProjectInfo)node.getUserObject();
    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",info.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDING_VALUE));
    	}else if(node.getUserObject() instanceof BuildingInfo) {//选择楼栋时 加载 不区分单元的该楼栋所有楼层效果图
    		BuildingInfo info=(BuildingInfo)node.getUserObject();
    		filter.getFilterItems().add(new FilterItemInfo("building.id",info.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
    	}else if(node.getUserObject() instanceof BuildingUnitInfo) {//选择单元时 加载 区分单元的该单元所有楼层效果图
    		BuildingUnitInfo info=(BuildingUnitInfo)node.getUserObject();
    		filter.getFilterItems().add(new FilterItemInfo("buildingUnit.id",info.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
    	}
    	viewInfo = (EntityViewInfo)this.mainQuery.clone();
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    

	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic=super.getSelectors();
    	sic.add("state");
    	sic.add("CU.id");
    	sic.add("orgUnit.id");
    	return sic;
    }

}