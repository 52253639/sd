/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitFactory;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.CustomerTenancyAreaChangeFacadeFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class CustomerTenancyAreaChangeUI extends AbstractCustomerTenancyAreaChangeUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerTenancyAreaChangeUI.class);
    
	private CustomerTenancyAreaChangeUIFilter filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
    protected boolean initDefaultFilter() {
    	return true;
    }
	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	
	private CustomerTenancyAreaChangeUIFilter getFilterUI()
	{
		if (this.filterUI == null)
		{
			try
			{
				this.filterUI = new CustomerTenancyAreaChangeUIFilter();
				this.filterUI.onLoad();
			} catch (Exception e)
			{
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected void execQuery() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try{
	    	if (node == null){
	    		return;
	    	}
	    	List list = new ArrayList();
	    	FDCCustomerParams para = null;
	    	List sellProjectlist  = new ArrayList();
	    	Map param = new HashMap();
	    	if (node != null && node.getUserObject() instanceof SellProjectInfo){
	    		SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
	    		list.add(sellProject);
	    		param.put("sellProject", list);
	    		para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
	    		Date beginDate = this.getFilterUI().getBeginQueryDate(para);
	    		Date endDate = this.getFilterUI().getEndQueryDate(para);
	    		param.put("beginDate",beginDate);
	    		param.put("endDate",endDate);
	    		String customerid = para.get("f7Customer");
	    		String cpstateid = para.get("cpState");
	    		param.put("f7Customer", customerid);
	    		param.put("cpState", cpstateid);
	    		
	    	}else{
	    		if(node != null && node.getUserObject() instanceof OrgStructureInfo){
	    			OrgStructureInfo orgStructureInfo = (OrgStructureInfo) node.getUserObject();
	    			EntityViewInfo view  = new EntityViewInfo();
	    			FilterInfo filter = new FilterInfo();
	    			filter.getFilterItems().add(new FilterItemInfo("orgUnit",orgStructureInfo.getUnit().getId().toString()));
	    			view.setFilter(filter);
	    			CoreBaseCollection collection= SellProjectFactory.getRemoteInstance().getCollection(view);
					if (collection.size() != 0) {
						for (int i = 0; i < collection.size(); i++) {
							SellProjectInfo sellProject = (SellProjectInfo) collection.get(i);
							sellProjectlist.add(sellProject);
						}
					}
					EntityViewInfo view1 = new EntityViewInfo();
					FilterInfo filter1 = new FilterInfo();
					filter1.getFilterItems().add(new FilterItemInfo("orgUnit", orgStructureInfo.getUnit().getId().toString()));
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("head.*"));
					view1.setSelector(sic);
					view1.setFilter(filter1);
					CoreBaseCollection collection1 = ShareOrgUnitFactory.getRemoteInstance().getCollection(view1);
					for (int i = 0; i < collection1.size(); i++) {
						ShareOrgUnitInfo shareOrgUnitInfo = (ShareOrgUnitInfo) collection1.get(i);
						if (shareOrgUnitInfo.getHead() != null) {
							sellProjectlist.add(shareOrgUnitInfo.getHead());
						}
					}
				}
	    		param.put("sellProject", sellProjectlist);
	    		para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
	    		Date beginDate = this.getFilterUI().getBeginQueryDate(para);
	    		Date endDate = this.getFilterUI().getEndQueryDate(para);
	    		param.put("beginDate",beginDate);
	    		param.put("endDate",endDate);
	    		String customerid = para.get("f7Customer");
	    		String cpstateid = para.get("cpState");
	    		param.put("f7Customer", customerid);
	    		param.put("cpState", cpstateid);
	    	}
	    	Map listMap = CustomerTenancyAreaChangeFacadeFactory.getRemoteInstance().getValueMap(param);
	    	filldate(listMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
    public CustomerTenancyAreaChangeUI() throws Exception
    {
        super();
    }
    protected String getSelectedKeyValue() {
    	return null;
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionAddNew.setVisible(false);
    	actionRemove.setVisible(false);
    	actionEdit.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionQuery.setVisible(true);
    	actionView.setVisible(false);
    	actionRefresh.setVisible(false);
    	actionLocate.setVisible(false);
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	
    	
    }
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		treeView.setShowButton(true);

	}
	protected String getKeyFieldName() {
		return null;
	}
	protected void fillFirstData(RequestRowSetEvent e) {;
	}

    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

    	if (node == null)
    	{
    		return;
    	}
    	List list = new ArrayList();
    	FDCCustomerParams para = null;
    	List sellProjectlist  = new ArrayList();
    	Map param = new HashMap();
    	if (node != null && node.getUserObject() instanceof SellProjectInfo){
    		SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
    		list.add(sellProject);
    		param.put("sellProject", list);
    		para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
    		Date beginDate = this.getFilterUI().getBeginQueryDate(para);
    		Date endDate = this.getFilterUI().getEndQueryDate(para);
    		param.put("beginDate",beginDate);
    		param.put("endDate",endDate);
    		String customerid = para.get("f7Customer");
    		String cpstateid = para.get("cpState");
    		param.put("f7Customer", customerid);
    		param.put("cpState", cpstateid);
    	}else{
    		if(node != null && node.getUserObject() instanceof OrgStructureInfo){
    			OrgStructureInfo orgStructureInfo = (OrgStructureInfo) node.getUserObject();
    			EntityViewInfo view  = new EntityViewInfo();
    			FilterInfo filter = new FilterInfo();
    			filter.getFilterItems().add(new FilterItemInfo("orgUnit",orgStructureInfo.getUnit().getId().toString()));
    			view.setFilter(filter);
    			CoreBaseCollection collection= SellProjectFactory.getRemoteInstance().getCollection(view);
				if (collection.size() != 0) {
					for (int i = 0; i < collection.size(); i++) {
						SellProjectInfo sellProject = (SellProjectInfo) collection.get(i);
						sellProjectlist.add(sellProject);
					}
				}
				EntityViewInfo view1 = new EntityViewInfo();
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("orgUnit", orgStructureInfo.getUnit().getId().toString()));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("head.*"));
				view1.setSelector(sic);
				view1.setFilter(filter1);
				CoreBaseCollection collection1 = ShareOrgUnitFactory.getRemoteInstance().getCollection(view1);
				for (int i = 0; i < collection1.size(); i++) {
					ShareOrgUnitInfo shareOrgUnitInfo = (ShareOrgUnitInfo) collection1.get(i);
					if (shareOrgUnitInfo.getHead() != null) {
						sellProjectlist.add(shareOrgUnitInfo.getHead());
					}
				}

			}
    		param.put("sellProject", sellProjectlist);
    		para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
    		Date beginDate = this.getFilterUI().getBeginQueryDate(para);
    		Date endDate = this.getFilterUI().getEndQueryDate(para);
    		param.put("beginDate",beginDate);
    		param.put("endDate",endDate);
    		String customerid = para.get("f7Customer");
    		String cpstateid = para.get("cpState");
    		param.put("f7Customer", customerid);
    		param.put("cpState", cpstateid);	
    	}
    	Map listMap = CustomerTenancyAreaChangeFacadeFactory.getRemoteInstance().getValueMap(param);
    	filldate(listMap);
     }

    private void filldate(Map listMap) {
    	tblMain.setRefresh(false);
		tblMain.removeRows();
		for(int i = 0 ;i<listMap.size();i++){
			IRow irow = tblMain.addRow();
			Map maplist =  new HashMap();
			if (listMap.get(String.valueOf(i)) instanceof Map) {
				maplist = (Map) listMap.get(String.valueOf(i));
				if (maplist != null) {
					this.tblMain.getColumn("nowArea").getStyleAttributes().setNumberFormat("0.00");
					this.tblMain.getColumn("formerArea").getStyleAttributes().setNumberFormat("0.00");
					this.tblMain.getColumn("netArea").getStyleAttributes().setNumberFormat("0.00");
					irow.getCell("projectName").setValue(maplist.get("projectName"));
					irow.getCell("customer").setValue(maplist.get("customer"));
					irow.getCell("building").setValue(maplist.get("building"));
					irow.getCell("room").setValue(maplist.get("room"));
					irow.getCell("contractNumber").setValue(maplist.get("contractNumber"));
					irow.getCell("formerArea").setValue(maplist.get("oldarea"));
					irow.getCell("nowArea").setValue(maplist.get("nowarea"));
					irow.getCell("netArea").setValue(maplist.get("netArea"));
					irow.getCell("state").setValue(maplist.get("state"));
				}
			}
		}
		tblMain.setRefresh(true);
		tblMain.reLayoutAndPaint();
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}


	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

}