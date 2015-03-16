/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;

/**
 * output class name
 */
public class ChangeNameDetailUI extends AbstractChangeNameDetailUI
{
	private static final Logger logger = CoreUIObject.getLogger(ChangeNameDetailUI.class);
	public ChangeNameDetailUI() throws Exception
	{
		super();
	}

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void onLoad() throws Exception
	{
		super.onLoad();
		initTree();
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnQuery.setEnabled(true);
		this.actionAddNew.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionView.setVisible(false);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		fillData();
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


	public void storeFields()
	{
		super.storeFields();
	}

	protected void execQuery()
	{
		try
		{
			fillData();
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
	}

	private void fillData() throws BOSException
	{
		this.tblMain.removeRows();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}
		tblMain.getColumn("buildingArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("sellProject").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("subArea").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("building").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("unit").getStyleAttributes().setHided(false);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();

		if (node.getUserObject() instanceof Integer) {   //单元
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("room.building.id", building.getId().toString()));
			roomFilter.getFilterItems().add(new FilterItemInfo("unit", unit));

			hideTheColumns(new String[]{"sellProject","subArea","building","unit"});

			//setTheViewSorter(view,new String[]{ "number"  });
		}else if (node.getUserObject() instanceof BuildingInfo) {   //楼栋
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("room.building.id", building.getId().toString()));

			hideTheColumns(new String[]{"sellProject","subArea","building"  ,building.getUnitCount()==0?"unit":""});		
		}else if(node.getUserObject() instanceof SubareaInfo){     //分区
			SubareaInfo subarea = (SubareaInfo)node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("room.building.subarea.id",subarea.getId().toString()));

			hideTheColumns(new String[]{"sellProject","subArea"});

		}else if(node.getUserObject() instanceof SellProjectInfo) { //销售项目
			SellProjectInfo sellProject = (SellProjectInfo)node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id",sellProject.getId().toString()));
		}else{			
			return;
		}

		roomFilter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyType",TenancyContractTypeEnum.CHANGENAME_VALUE,CompareType.EQUALS));
		view.setFilter(roomFilter);
		view.getSelector().add("id");
		view.getSelector().add("actDeliveryRoomDate");
		view.getSelector().add("room.id");
		view.getSelector().add("room.building.name");
		view.getSelector().add("room.building.subarea.name");
		view.getSelector().add("room.floor");
		view.getSelector().add("room.unit");
		view.getSelector().add("room.number");
		view.getSelector().add("room.tenancyArea");
		view.getSelector().add("tenancy.sellProject.name");
		view.getSelector().add("tenancy.oldTenancyBill.tenCustomerDes");
		view.getSelector().add("tenancy.tenCustomerDes");
		view.getSelector().add("tenancy.tenancyDate");
		TenancyRoomEntryCollection quitTen = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		for(int i=0;i<quitTen.size();i++)
		{
			IRow row = tblMain.addRow(i);
			TenancyRoomEntryInfo tenInfo = (TenancyRoomEntryInfo)quitTen.get(i);
			if(tblMain.getColumn("subArea").getStyleAttributes().isHided())
			{
			}else
			{
				if(tenInfo.getRoom().getBuilding().getSubarea()==null)
				{
					this.tblMain.getColumn("subArea").getStyleAttributes().setHided(true);
				}else
				{
					row.getCell("subArea").setValue(tenInfo.getRoom().getBuilding().getSubarea().getName());
				}

			}if(tblMain.getColumn("building").getStyleAttributes().isHided())
			{

			}else
			{
				if(tenInfo.getRoom().getBuilding()==null)
				{
					this.tblMain.getColumn("building").getStyleAttributes().setHided(true);
				}else
				{
					row.getCell("building").setValue(tenInfo.getRoom().getBuilding().getName());
				}
			}if(tblMain.getColumn("unit").getStyleAttributes().isHided())
			{

			}else
			{
				if(tenInfo.getRoom().getUnit()==0)
				{
					this.tblMain.getColumn("unit").getStyleAttributes().setHided(true);
				}else
				{
					row.getCell("unit").setValue(new Integer(tenInfo.getRoom().getUnit()));
				}
			}

			row.getCell("floor").setValue(new Integer(tenInfo.getRoom().getFloor()));
			row.getCell("roomNumber").setValue(tenInfo.getRoom().getNumber());
			row.getCell("buildingArea").setValue(tenInfo.getRoom().getTenancyArea());
			row.getCell("changeNameDate").setValue(tenInfo.getTenancy().getTenancyDate());
			row.getCell("beforeChangeCustomer").setValue(tenInfo.getTenancy().getOldTenancyBill().getTenCustomerDes());
			row.getCell("afterChangeCustomer").setValue(tenInfo.getTenancy().getTenCustomerDes());
		}
	}

	/**
	 * 隐藏指定的列   (列名有 : subArea,building,unit,number)
	 * @param colNames
	 */
	private void hideTheColumns(String[] colNames)  {
		if(colNames!=null && colNames.length>0) {
			for(int i=0,j=colNames.length;i<j;i++) {
				if(colNames[i]!=null && !colNames[i].equals(""))
					this.tblMain.getColumn(colNames[i]).getStyleAttributes().setHided(true);
			}
		}
	}

	protected String[] getLocateNames()
	{
		String[] locateNames = new String[2];
		locateNames[0] = "sellProject";
		locateNames[1] = IFWEntityStruct.dataBase_Name;
		return locateNames;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			return;
		}
		super.tblMain_tableClicked(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

}