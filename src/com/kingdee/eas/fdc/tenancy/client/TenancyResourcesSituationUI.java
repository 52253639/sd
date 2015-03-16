/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnit;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitFactory;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyResourcesSituationFacadeFactory;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class TenancyResourcesSituationUI extends AbstractTenancyResourcesSituationUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyResourcesSituationUI.class);
    public TenancyResourcesSituationUI() throws Exception
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
    	actionQuery.setVisible(false);
    	actionView.setVisible(false);
    	actionRefresh.setVisible(false);
    	actionLocate.setVisible(false);
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	
    	
    }
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getSellOrderTree(actionOnLoad, MoneySysTypeEnum.TenancySys));
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
    	List sellProjectlist  = new ArrayList();
    	Map param = new HashMap();
    	if (node != null && node.getUserObject() instanceof SellProjectInfo){
    		SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
    		list.add(sellProject);
    		param.put("sellProject", list);
        	Map maplist = TenancyResourcesSituationFacadeFactory.getRemoteInstance().getResult(param);
        	filldata1(maplist);
    	}else{
    		if(node != null && node.getUserObject() instanceof OrgStructureInfo){
    			
    			//错误的逻辑 ，当是节点是组织的时候，显示左边所有所有项目的资源统计 xin_wang 2010.09.19
//    			OrgStructureInfo orgStructureInfo = (OrgStructureInfo) node.getUserObject();
//    			EntityViewInfo view  = new EntityViewInfo();
//    			FilterInfo filter = new FilterInfo();
//    			filter.getFilterItems().add(new FilterItemInfo("orgUnit",orgStructureInfo.getUnit().getId().toString()));
//    			view.setFilter(filter);
//    			CoreBaseCollection collection= SellProjectFactory.getRemoteInstance().getCollection(view);
//				if (collection.size() != 0) {
//					for (int i = 0; i < collection.size(); i++) {
//						SellProjectInfo sellProject = (SellProjectInfo) collection.get(i);
//						sellProjectlist.add(sellProject);
//					}
//				}
//				EntityViewInfo view1 = new EntityViewInfo();
//				FilterInfo filter1 = new FilterInfo();
//				filter1.getFilterItems().add(new FilterItemInfo("orgUnit", orgStructureInfo.getUnit().getId().toString()));
//				SelectorItemCollection sic = new SelectorItemCollection();
//				sic.add(new SelectorItemInfo("head.*"));
//				view1.setSelector(sic);
//				view1.setFilter(filter1);
//				CoreBaseCollection collection1 = ShareOrgUnitFactory.getRemoteInstance().getCollection(view1);
//				for (int i = 0; i < collection1.size(); i++) {
//					ShareOrgUnitInfo shareOrgUnitInfo = (ShareOrgUnitInfo) collection1.get(i);
//					if (shareOrgUnitInfo.getHead() != null) {
//						sellProjectlist.add(shareOrgUnitInfo.getHead());
//					}
//				}
//
//			}
    		
//    		if(sellProjectList==null){
    		if(((OrgStructureInfo)node.getUserObject()).getUnit().isIsSaleOrgUnit())
    			sellProjectlist =getAllSellProject(node);
//    		}
    		param.put("sellProject", sellProjectlist);
        	Map maplist = TenancyResourcesSituationFacadeFactory.getRemoteInstance().getResult(param);
        	filldata2(maplist);
    		}
    	}
     }
    	
    	private List getAllSellProject(DefaultKingdeeTreeNode dkt){
    		List sellProjectList = new ArrayList();
    		for(int i =0;i<dkt.getChildCount();i++){
    			DefaultKingdeeTreeNode tn =(DefaultKingdeeTreeNode)dkt.getChildAt(i);
    			if(tn!=null&&tn.getUserObject() instanceof SellProjectInfo){
    				SellProjectInfo sp = (SellProjectInfo)tn.getUserObject();
    				sellProjectList.add(sp);
    			}
    		}
    	
    		return sellProjectList;
    	}
	private void filldata1(Map map) {
		KDTMergeManager mm = tblMain.getHeadMergeManager();
		tblMain.setRefresh(false);
		tblMain.removeRows();
		tblMain.removeColumns();
		IRow irow1 = null;
		IRow irow2 = null;
		boolean flag  = true;
		for(int i = 0 ;i<map.size();i++){
			Map maplist =  new HashMap();
			if (map.get(String.valueOf(i)) instanceof Map) {
				maplist = (Map) map.get(String.valueOf(i));
				if (maplist != null) {
					String buildingName = maplist.get("buildingName").toString();
					String roomcount = maplist.get("roomcount").toString();
					String allarea = maplist.get("allarea").toString();
					String nousearea = maplist.get("nousearea").toString();
					String proportionarea = maplist.get("proportionarea").toString();
					String type_count = maplist.get("type_count").toString();
					int count  = Integer.parseInt(type_count);
					if(flag){
						tblMain.addColumns(5+count*10);
						this.tblMain.getColumn(2).getStyleAttributes().setNumberFormat("0.00");
						this.tblMain.getColumn(3).getStyleAttributes().setNumberFormat("0.00");
						flag = false;
						irow1 = this.tblMain.addHeadRow();
						irow2 = this.tblMain.addHeadRow();
						irow1.getCell(0).setValue("楼栋");
						irow1.getCell(1).setValue("总套数");
						irow1.getCell(2).setValue("总计租面积");
						irow1.getCell(3).setValue("已租面积");
						irow1.getCell(4).setValue("出租率");
						irow2.getCell(0).setValue("楼栋");
						irow2.getCell(1).setValue("总套数");
						irow2.getCell(2).setValue("总计租面积");
						irow2.getCell(3).setValue("已租面积");
						irow2.getCell(4).setValue("出租率");
						mm.mergeBlock(0, 0,1, 0,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 1,1, 1,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 2,1, 2,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 3,1, 3,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 4,1, 4,KDTMergeManager.SPECIFY_MERGE);
					}

					IRow irow = this.tblMain.addRow();
					irow.getCell(0).setValue(buildingName);
					irow.getCell(1).setValue(roomcount);
					irow.getCell(2).setValue(allarea);
					irow.getCell(3).setValue(nousearea);
					irow.getCell(4).setValue(proportionarea+"%");		
					//"a1" == 计租面积总和
					//"a2" == 可租面积总和
					//"a3" == 保留面积总和
					//"a4" == 已租面积总和
					//"a5" == 空置面积总和
					//"a6" == 可租比例
					//"a7" == 保留比例
					//"a8" == 出租率
					//"a9" == 空置率
					//"a10" == 本类出租率
					for(int j = 0,k=5;j<count;j++){
						Map listMap  = (Map)maplist.get("productType"+j);
						String productTypeName = listMap.get("productTypeName").toString();
						mm.mergeBlock(0, k,0, k+9,KDTMergeManager.SPECIFY_MERGE);
						irow1.getCell(k).setValue(productTypeName);
						String a1 = listMap.get("a1").toString();
						irow2.getCell(k).setValue("计租面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a1);
						String a2 = listMap.get("a2").toString();
						irow2.getCell(k).setValue("可租面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a2);
						String a6 = listMap.get("a6").toString();
						irow2.getCell(k).setValue("可租比例");
						irow.getCell(k++).setValue(a6+"%");
						String a3 = listMap.get("a3").toString();
						irow2.getCell(k).setValue("保留面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a3);
						String a7 = listMap.get("a7").toString();
						irow2.getCell(k).setValue("保留比例");
						irow.getCell(k++).setValue(a7+"%");
						String a4 = listMap.get("a4").toString();
						irow2.getCell(k).setValue("已租面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a4);
						String a8 = listMap.get("a8").toString();
						irow2.getCell(k).setValue("出租率");
						irow.getCell(k++).setValue(a8+"%");
						String a10 = listMap.get("a10").toString();
						irow2.getCell(k).setValue("本类出租率");
						irow.getCell(k++).setValue(a10+"%");
						String a5 = listMap.get("a5").toString();
						irow2.getCell(k).setValue("空置面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a5);
						String a9 = listMap.get("a9").toString();
						irow2.getCell(k).setValue("空置率");
						irow.getCell(k++).setValue(a9+"%");
				
					}
				}
			}
		}
		tblMain.setRefresh(true);
		tblMain.reLayoutAndPaint();
	}
	private void filldata2(Map map) {
		KDTMergeManager mm = tblMain.getHeadMergeManager();
		int count = 0;
		tblMain.setRefresh(false);
		tblMain.removeRows();
		tblMain.removeColumns();
		IRow irow1 = null;
		IRow irow2 = null;
		boolean flag  = true;
		for(int i = 0 ;i<map.size();i++){
			Map maplist =  new HashMap();
			if (map.get(String.valueOf(i)) instanceof Map) {
				maplist = (Map) map.get(String.valueOf(i));
				if (maplist != null) {
					String sellProjectName = maplist.get("sellProjectName").toString();
					String buildingName = maplist.get("buildingName").toString();
					String roomcount = maplist.get("roomcount").toString();
					String allarea = maplist.get("allarea").toString();
					String nousearea = maplist.get("nousearea").toString();
					String proportionarea = maplist.get("proportionarea").toString();
					String type_count = maplist.get("type_count").toString();
					count  = Integer.parseInt(type_count);
					if(flag){
						tblMain.addColumns(6+count*10);
						this.tblMain.getColumn(3).getStyleAttributes().setNumberFormat("0.00");
						this.tblMain.getColumn(4).getStyleAttributes().setNumberFormat("0.00");
						flag = false;
						irow1 = this.tblMain.addHeadRow();
						irow2 = this.tblMain.addHeadRow();
						irow1.getCell(0).setValue("项目名称");
						irow1.getCell(1).setValue("楼栋");
						irow1.getCell(2).setValue("总套数");
						irow1.getCell(3).setValue("总计租面积");
						irow1.getCell(4).setValue("已租面积");
						irow1.getCell(5).setValue("出租率");
						irow2.getCell(0).setValue("项目名称");
						irow2.getCell(1).setValue("楼栋");
						irow2.getCell(2).setValue("总套数");
						irow2.getCell(3).setValue("总计租面积");
						irow2.getCell(4).setValue("已租面积");
						irow2.getCell(5).setValue("出租率");
						mm.mergeBlock(0, 0,1, 0,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 1,1, 1,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 2,1, 2,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 3,1, 3,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 4,1, 4,KDTMergeManager.SPECIFY_MERGE);
						mm.mergeBlock(0, 5,1, 5,KDTMergeManager.SPECIFY_MERGE);
					}

					IRow irow = this.tblMain.addRow();
//					String SellProjectName = "";
//					if (list.get(i) instanceof SellProjectInfo){
//						SellProjectName = ((SellProjectInfo)list.get(i)).getName();
//					}
					irow.getCell(0).setValue(sellProjectName);
					irow.getCell(1).setValue(buildingName);
					irow.getCell(2).setValue(roomcount);
					irow.getCell(3).setValue(allarea);
					irow.getCell(4).setValue(nousearea);
					irow.getCell(5).setValue(proportionarea+"%");		
					//"a1" == 计租面积总和
					//"a2" == 可租面积总和
					//"a3" == 保留面积总和
					//"a4" == 已租面积总和
					//"a5" == 空置面积总和
					//"a6" == 可租比例
					//"a7" == 保留比例
					//"a8" == 出租率
					//"a9" == 空置率
					//"a10" == 本类出租率
					for(int j = 0,k=6;j<count;j++){
						Map listMap  = (Map)maplist.get("productType"+j);
						String productTypeName = listMap.get("productTypeName").toString();
						mm.mergeBlock(0, k,0, k+9,KDTMergeManager.SPECIFY_MERGE);
						irow1.getCell(k).setValue(productTypeName);
						String a1 = listMap.get("a1").toString();
						irow2.getCell(k).setValue("计租面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a1);
						String a2 = listMap.get("a2").toString();
						irow2.getCell(k).setValue("可租面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a2);
						String a6 = listMap.get("a6").toString();
						irow2.getCell(k).setValue("可租比例");
						irow.getCell(k++).setValue(a6+"%");
						String a3 = listMap.get("a3").toString();
						irow2.getCell(k).setValue("保留面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a3);
						String a7 = listMap.get("a7").toString();
						irow2.getCell(k).setValue("保留比例");
						irow.getCell(k++).setValue(a7+"%");
						String a4 = listMap.get("a4").toString();
						irow2.getCell(k).setValue("已租面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a4);
						String a8 = listMap.get("a8").toString();
						irow2.getCell(k).setValue("出租率");
						irow.getCell(k++).setValue(a8+"%");
						String a10 = listMap.get("a10").toString();
						irow2.getCell(k).setValue("本类出租率");
						irow.getCell(k++).setValue(a10+"%");
						String a5 = listMap.get("a5").toString();
						irow2.getCell(k).setValue("空置面积");
						this.tblMain.getColumn(k).getStyleAttributes().setNumberFormat("0.00");
						irow.getCell(k++).setValue(a5);
						String a9 = listMap.get("a9").toString();
						irow2.getCell(k).setValue("空置率");
						irow.getCell(k++).setValue(a9+"%");
				
					}
				}
			}
		}
		if(map.size()!=0){
			BigDecimal amount1 = FDCConstants.ZERO;
			BigDecimal amount2 = FDCConstants.ZERO;
			int amount0 = 0;
			BigDecimal index1[] =  new BigDecimal[count];
			BigDecimal countindex1[] =  new BigDecimal[count];
			BigDecimal index2[] =  new BigDecimal[count];
			BigDecimal countindex2[] =  new BigDecimal[count];
			BigDecimal index3[] =  new BigDecimal[count];
			BigDecimal countindex3[] =  new BigDecimal[count];
			BigDecimal index4[] =  new BigDecimal[count];
			BigDecimal countindex4[] =  new BigDecimal[count];
			BigDecimal index5[] =  new BigDecimal[count];
			BigDecimal countindex5[] =  new BigDecimal[count];
			for(int m =0 ;m<count;m++){
				countindex1[m] =FDCConstants.ZERO;;
				countindex2[m] =FDCConstants.ZERO; 
				countindex3[m] =FDCConstants.ZERO; 
				countindex4[m] =FDCConstants.ZERO; 
				countindex5[m] =FDCConstants.ZERO; 
			}
			for (int i = 0;i < map.size(); i++) {
				for(int m =0 ;m<count;m++){
					index1[m] = new BigDecimal((String)tblMain.getRow(i).getCell(m*10+6).getValue());
					index2[m] = new BigDecimal((String)tblMain.getRow(i).getCell(m*10+7).getValue());
					index3[m] = new BigDecimal((String)tblMain.getRow(i).getCell(m*10+9).getValue());
					index4[m] = new BigDecimal((String)tblMain.getRow(i).getCell(m*10+11).getValue());
					index5[m] = new BigDecimal((String)tblMain.getRow(i).getCell(m*10+14).getValue());
				}
				int  val0 = Integer.parseInt((String)(tblMain.getRow(i).getCell(2).getValue()));
				BigDecimal val1 = new BigDecimal((String)tblMain.getRow(i).getCell(3).getValue());
				BigDecimal val2 = new BigDecimal((String)tblMain.getRow(i).getCell(4).getValue());
				amount1 = amount1.add((BigDecimal) val1);
				amount0 = amount0+val0;
				amount2 = amount2.add((BigDecimal) val2);
				for(int m =0 ;m<count;m++){
					countindex1[m] = index1[m].add(countindex1[m]);
					countindex2[m] = index2[m].add(countindex2[m]);
					countindex3[m] = index3[m].add(countindex3[m]);
					countindex4[m] = index4[m].add(countindex4[m]);
					countindex5[m] = index5[m].add(countindex5[m]);
				}
			}
			IRow irow = this.tblMain.addRow();
			irow.getStyleAttributes().setBackground(FMClientHelper.KDTABLE_TOTAL_BG_COLOR);
			irow.getCell(1).setValue("小计");
			irow.getCell(2).setValue(Integer.toString(amount0));
			irow.getCell(3).setValue(amount1);
			irow.getCell(4).setValue(amount2);
			for(int m =0 ;m<count;m++){
				irow.getCell(m*10+6).setValue(countindex1[m]);
				irow.getCell(m*10+7).setValue(countindex2[m]);
				irow.getCell(m*10+9).setValue(countindex3[m]);
				irow.getCell(m*10+11).setValue(countindex4[m]);
				irow.getCell(m*10+14).setValue(countindex5[m]);
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

	public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception {
		super.actionMoveTree_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}