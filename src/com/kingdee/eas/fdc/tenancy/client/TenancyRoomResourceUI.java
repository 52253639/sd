/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomUsageCollection;
import com.kingdee.eas.fdc.sellhouse.RoomUsageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.TreePathUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenancyRoomResourceUI extends AbstractTenancyRoomResourceUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyRoomResourceUI.class);

    private Map buildingMap = null;//建筑性质
    private Map roomClassMap = null;//房间用途分类
    private Map roomSellProjectClassMap = null;
    private Map buildingColumnNameMap = null;//建筑性质列
    private Map roomColumnNameMap = null;//房间用途分类列
    public TenancyRoomResourceUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
    
    protected CommonQueryDialog initCommonQueryDialog() 
	{
		CommonQueryDialog dialog = super.initCommonQueryDialog();
		try 
		{
			dialog.setShowFilter(false);
			dialog.setShowSorter(false);
		}
		catch (Exception e) 
		{
			handUIException(e);
		}
		return dialog;
	}
    
    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		//initTableHead();
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnQuery.setEnabled(true);
		this.actionAddNew.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionView.setVisible(false);
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		this.tblMain.removeColumns();
		initTableHead(node);
		execQuery();
	}
    
    protected void execQuery() {  	
		try {
			fillData();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
    
    protected void refresh(ActionEvent e) throws Exception {
    	if(e==null)return;
    	ItemAction action = getActionFromActionEvent(e);
    	if (action.equals(actionRefresh))
        {         
           // super.refresh(e);
        }else
        {
        	super.refresh(e);
        }
    }
    
    private void initTableHead(DefaultKingdeeTreeNode node) {
    	//this.tblMain.removeHeadRows();
		getBuildingPropertyMap();
		getRoomClassMap();
		IRow headRow0 = this.tblMain.addHeadRow();
		IRow headRow1 = this.tblMain.addHeadRow();
		
		IColumn columnSumCount = this.tblMain.addColumn();
		columnSumCount.setKey("sumCount");
		headRow0.getCell("sumCount").setValue("总套数");
		headRow1.getCell("sumCount").setValue("总套数");
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSumCount.getColumnIndex(), 1, columnSumCount.getColumnIndex());
		
		IColumn columnSumArea = this.tblMain.addColumn();
		columnSumArea.setKey("sumArea");
		headRow0.getCell("sumArea").setValue("总面积");
		headRow1.getCell("sumArea").setValue("总面积");
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSumArea.getColumnIndex(), 1, columnSumArea.getColumnIndex());
		
		if(buildingMap!=null)  {  
			Set keySet = buildingMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				buildingColumnNameMap.put(key,"buildingProperty"+i);
				IColumn columnbuildingPro = this.tblMain.addColumn();
				columnbuildingPro.setKey("buildingProperty"+i);
				headRow0.getCell("buildingProperty"+i).setValue("建筑性质分类");
				headRow1.getCell("buildingProperty"+i).setValue(buildingMap.get(key));
				this.tblMain.getColumn("buildingProperty"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				i++;
			}
			this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("buildingProperty1"),0,this.tblMain.getColumnIndex("buildingProperty"+(i-1)));
		}

		if (node.getUserObject() instanceof SellProjectInfo) {
			if(roomClassMap!=null)
			{
				Set keySet = roomClassMap.keySet();
				Iterator iter = keySet.iterator();
				int i = 1;
				while(iter.hasNext()) {		
					String key = (String)iter.next();
					String newkey = key.substring(0, key.length()-1);
					if(newkey.equals(((SellProjectInfo)node.getUserObject()).getId().toString()))
					{
						roomColumnNameMap.put(key,"roomClass"+i);
						IColumn columnRoomClass = this.tblMain.addColumn();
						columnRoomClass.setKey("roomClass"+i);
						headRow0.getCell("roomClass"+i).setValue("房间用途分类");
						headRow1.getCell("roomClass"+i).setValue(roomClassMap.get(key));
						this.tblMain.getColumn("roomClass"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						i++;
					}			
				}
				this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("roomClass1"),0,this.tblMain.getColumnIndex("roomClass"+(i-1)));
			}
		}
	}
    
    private void getBuildingPropertyMap()
	{
		if(buildingMap!=null) return;
		BuildingPropertyCollection buildingColl  = null;
		try {
			 buildingColl = BuildingPropertyFactory.getRemoteInstance().getBuildingPropertyCollection();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(buildingColl.size()>0)
		{
			buildingMap = new HashMap();
			buildingColumnNameMap = new HashMap();
			for(int i=0;i<buildingColl.size();i++)
			{
				BuildingPropertyInfo buildingInfo = buildingColl.get(i);
				buildingMap.put(buildingInfo.getId().toString(), buildingInfo.getName());
			}
		}
	}
    
    private void getRoomClassMap()
    {
    	if(roomClassMap!=null) return;
    	RoomUsageCollection roomUsColl = null;
    	try {
    		roomUsColl = RoomUsageFactory.getRemoteInstance().getRoomUsageCollection();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	if(roomUsColl.size()>0)
    	{
    		  roomClassMap = new HashMap();
    		  roomSellProjectClassMap = new HashMap();
    	      roomColumnNameMap = new HashMap();
    	      for(int i=0;i<roomUsColl.size();i++)
    	      {
    	    	  RoomUsageInfo roomUsInfo = roomUsColl.get(i);
    	    	  roomClassMap.put(roomUsInfo.getSellProject().getId().toString()+i, roomUsInfo.getName());
    	    	  //roomSellProjectClassMap.put(roomUsInfo.getSellProject().getId().toString(), roomUsInfo.getName());
    	      }
    	}
    }
    
    private void fillData() throws BOSException {
		this.tblMain.removeRows();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SellProjectInfo projectInfo = null;
		if(node.getUserObject() instanceof SellProjectInfo)
		{
			try {
			projectInfo = (SellProjectInfo)node.getUserObject();
			//查找该项目下的房间总套数和总的建筑面积
			FDCSQLBuilder builder = new FDCSQLBuilder(); 
			builder.appendSql("select sum(room.FTenancyArea) buildingArea,count(*) count from t_she_room room ");
			builder.appendSql(" left join t_she_building building on room.fbuildingid = building.fid ");
			builder.appendSql(" left join t_she_sellproject project on building.fsellprojectid = project.fid ");
			builder.appendSql(" where project.Fid  = "+"'"+projectInfo.getId().toString()+"'" );
			IRowSet set = null;
			set = SQLExecutorFactory.getRemoteInstance(builder.getSql())
			.executeSQL();
			while(set.next())
			{
				    int count;
					count = set.getInt("count");
					BigDecimal buildingArea = set.getBigDecimal("buildingArea");
					IRow row = tblMain.addRow();
					row.getCell("sumCount").setValue(new Integer(count));
					row.getCell("sumArea").setValue(buildingArea);
					row.getCell("sumArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
    }
    
    protected String getKeyFieldName() {
		return "sumCount";
	}
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
}