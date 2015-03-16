/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementFactory;
import com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureFactory;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginFactory;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardFactory;
import com.kingdee.eas.fdc.sellhouse.EventTypeFactory;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtFactory;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningFactory;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaFactory;
import com.kingdee.eas.fdc.sellhouse.HopePitchFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedFloorFactory;
import com.kingdee.eas.fdc.sellhouse.HopedTotalPricesFactory;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceFactory;
import com.kingdee.eas.fdc.sellhouse.MarketBaseDataList;
import com.kingdee.eas.fdc.sellhouse.NoiseFactory;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomUsageFactory;
import com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 * 
 */
public class ProjectArchListUI extends AbstractProjectArchListUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectArchListUI.class);

	public ProjectArchListUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getBaseDataTree(this.actionOnLoad, null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		this.btnRangeEnactment.setVisible(true);
		this.btnRangeEnactment.setEnabled(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnQuery.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRangeEnactment.setEnabled(false);
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		initTree();
		this.treeMain.setSelectionRow(0);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		this.tblMain.removeRows(false);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof ProjectArchivesEntryInfo) {
			ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo) node.getUserObject();
			if (BaseDataPropertyEnum.ProjectControl.equals(projectArchInfo.getBaseDataProperty())) {
				this.btnRangeEnactment.setEnabled(false);
				this.actionRangeEnactment.setEnabled(false);
				this.btnAddNew.setVisible(true);
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setVisible(true);
				this.btnEdit.setEnabled(true);
				this.btnRemove.setVisible(true);
				this.btnRemove.setEnabled(true);
				SellProjectInfo project = (SellProjectInfo) ((DefaultMutableTreeNode) node.getParent().getParent()).getUserObject();
				String sql = "select fid,fname_l2,fnumber from " + projectArchInfo.getName() + " where FSellProjectID = '" + project.getId().toString() + "'";
				IRowSet sellSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
				List idList = new ArrayList();
				while (sellSet.next()) {
					String projectArchID = sellSet.getString("fid");
					String name = sellSet.getString("fname_l2");
					String number = sellSet.getString("fnumber");
					if (!StringUtils.isEmpty(projectArchID)) {
						idList.add(projectArchID);
						idList.add(name);
						idList.add(number);
					}
					IRow row = this.tblMain.addRow();
					row.getCell("id").setValue(projectArchID);
					row.getCell("scopeType").setValue(ScopeTypeEnum.AllScope);
					row.getCell("baseDataProperty").setValue(BaseDataPropertyEnum.ProjectControl);
					row.getCell("archivesEntrys.name").setValue(name);
					row.getCell("archivesEntrys.number").setValue(number);
				}
			} else {
				this.btnRangeEnactment.setEnabled(true);
				this.actionRangeEnactment.setEnabled(true);
				this.btnAddNew.setVisible(false);
				this.btnEdit.setVisible(false);
				this.btnRemove.setVisible(false);
				if (projectArchInfo.getScopeType().equals(ScopeTypeEnum.AllScope)) {
					String sql = "select fid,fname_l2,fnumber from " + projectArchInfo.getName();
					IRowSet sellSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
					List idList = new ArrayList();
					while (sellSet.next()) {
						String projectArchID = sellSet.getString("fid");
						String name = sellSet.getString("fname_l2");
						String number = sellSet.getString("fnumber");
						if (!StringUtils.isEmpty(projectArchID)) {
							idList.add(projectArchID);
							idList.add(name);
							idList.add(number);
						}
						IRow row = this.tblMain.addRow();
						row.getCell("id").setValue(projectArchID);
						row.getCell("scopeType").setValue(projectArchInfo.getScopeType());
						row.getCell("baseDataProperty").setValue(projectArchInfo.getBaseDataProperty());
						row.getCell("archivesEntrys.name").setValue(name);
						row.getCell("archivesEntrys.number").setValue(number);
					}
				} else // 如果是设定范围的话到分录去找保存的记录，如果是完整范围的话直接从基础资料表找
				// 这样是为了如果在完整范围也保存到分录里去，但是后来这个基础资料新增了一条记录，但是没有
				// 修改项目管控档案类那么这个新增的资料就丢失了
				{
					ArchivesEntryCollection archEntryColl = projectArchInfo.getArchivesEntrys();
					for (int i = 0; i < archEntryColl.size(); i++) {
						ArchivesEntryInfo archEntryInfo = archEntryColl.get(i);
						IRow row = this.tblMain.addRow();
						//当我们已经选择了设定范围但是基础资料又改变的话这里也同步改
						String id = archEntryInfo.getDescription();
						String sql = "select fid,fname_l2,fnumber from " + projectArchInfo.getName() +" where fid='"+id+"'";
						IRowSet saveSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
						String name = "";
						String number = "";
						while(saveSet.next())
						{
							name = saveSet.getString("fname_l2");
							number = saveSet.getString("fnumber");
						}
						row.getCell("id").setValue(archEntryInfo.getDescription());
						row.getCell("scopeType").setValue(projectArchInfo.getScopeType());
						row.getCell("baseDataProperty").setValue(projectArchInfo.getBaseDataProperty());
						row.getCell("archivesEntrys.name").setValue(name);
						row.getCell("archivesEntrys.number").setValue(number);
					}
				}
			}
		} else {
			this.btnRangeEnactment.setEnabled(false);
			this.actionRangeEnactment.setEnabled(false);
			this.btnAddNew.setVisible(false);
			this.btnEdit.setVisible(false);
			this.btnRemove.setVisible(false);
		}
	}

	public void actionRangeEnactment_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!(node.getUserObject() instanceof ProjectArchivesEntryInfo)) {
			return;
		}
		ProjectArchivesEntryInfo projectArchEntryInfo = (ProjectArchivesEntryInfo) node.getUserObject();
		UIContext uiContext = new UIContext(ui);
		String projectArachID = projectArchEntryInfo.getId().toString();
		String tableName = projectArchEntryInfo.getName().toString();
		uiContext.put("projectArachID", projectArachID);
		uiContext.put("tableName", tableName);
		SellProjectInfo project = (SellProjectInfo) ((DefaultMutableTreeNode) node.getParent().getParent()).getUserObject();
		RangeSelectUI.showUI(this, projectArchEntryInfo.getId().toString(), project);
		this.refresh(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		// return ProjectArchivesEntryFactory.getRemoteInstance();
		String entiyFactoryName = "";
		Map entiyFactoryMap = getMap("entiyFactory");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof ProjectArchivesEntryInfo) {
			ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo) node.getUserObject();
			entiyFactoryName = (String) entiyFactoryMap.get(projectArchInfo.getArchivesName());
			return this.getEntiyBizInterface(entiyFactoryName);
		}
		return null;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof ProjectArchivesEntryInfo) {
			SellProjectInfo project = (SellProjectInfo) ((DefaultMutableTreeNode) node.getParent().getParent()).getUserObject();
			uiContext.put("sellProject", project);
		}
		super.prepareUIContext(uiContext, e);
	}

	protected String getEditUIName() {
		String editUIName = "";
		Map editUINameMap = new HashMap();
		try {
			editUINameMap = getMap("editUIName");
		} catch (Exception e) {
			e.printStackTrace();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo) node.getUserObject();
		editUIName = (String) editUINameMap.get(projectArchInfo.getArchivesName());
		return editUIName;
	}
	
	protected Map getMap(String str) throws Exception
	{
		Map map = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select * from t_she_projectDataBase ");
		IRowSet projectSet = builder.executeQuery();
		if("editUIName".equals(str))
		{
			while(projectSet.next())
			{
				String name = projectSet.getString("fname_l2");
				String editUIName = projectSet.getString("FEditUIName");
				map.put(name, editUIName);
			}
		}else if("entiyFactory".equals(str))
		{
			while(projectSet.next())
			{
				String name = projectSet.getString("fname_l2");
				String entiyFactory = projectSet.getString("FEntiyFactory");
				map.put(name, entiyFactory);
			}
		}
		return map;
	}

	// protected void setActionState() {
	//
	// }
	protected ICoreBase getEntiyBizInterface(String entiyFactoryName) throws BOSException {
		if (entiyFactoryName.equals("ProductDetialFactory")) {
			return ProductDetialFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("RoomUsageFactory")) {
			return RoomUsageFactory.getRemoteInstance();
		}
		if (entiyFactoryName.equals("RoomModelTypeFactory")) {
			return RoomModelTypeFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("BuildingPropertyFactory")) {
			return BuildingPropertyFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("ProductTypeFactory")) {
			return ProductTypeFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("SightRequirementFactory")) {
			return SightRequirementFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("HopedDirectionFactory")) {
			return HopedDirectionFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("BuildingStructureFactory")) {
			return BuildingStructureFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("ProductDetialFactory")) {
			return ProductDetialFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("RoomUsageFactory")) {
			return RoomUsageFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("NoiseFactory")) {
			return NoiseFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("EyeSignhtFactory")) {
			return EyeSignhtFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("PossessionStandardFactory")) {
			return PossessionStandardFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("DecorationStandardFactory")) {
			return DecorationStandardFactory.getRemoteInstance();
		} 
		else if (entiyFactoryName.equals("CustomerOriginFactory")) {
			return CustomerOriginFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("CustomerGradeFactory")) {
			return CustomerGradeFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("FamillyEarningFactory")) {
			return FamillyEarningFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("HabitationAreaFactory")) {
			return HabitationAreaFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("ReceptionTypeFactory")) {
			return ReceptionTypeFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("HopedUnitPriceFactory")) {
			return HopedUnitPriceFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("HopedTotalPricesFactory")) {
			return HopedTotalPricesFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("HopedFloorFactory")) {
			return HopedFloorFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("FirstPayProportionFactory")) {
			return FirstPayProportionFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("BuyHouseReasonFactory")) {
			return BuyHouseReasonFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("HopePitchFactory")) {
			return HopePitchFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("AreaRequirementFactory")) {
			return AreaRequirementFactory.getRemoteInstance();
		} else if (entiyFactoryName.equals("EventTypeFactory")) {
			return EventTypeFactory.getRemoteInstance();
		} else {
			return ProjectArchivesEntryFactory.getRemoteInstance();
		}
	}

	protected String getKeyFieldName() {
		return "id";
	}

	public int getRowCountFromDB() {
		return -1;
	}
}