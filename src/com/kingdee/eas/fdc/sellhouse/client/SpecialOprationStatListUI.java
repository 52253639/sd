/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SpecialOprationStatFacadeFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class SpecialOprationStatListUI extends AbstractSpecialOprationStatListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialOprationStatListUI.class);
    
	Map rowMap = new HashMap();

	private SpecialOprationStatNewFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	boolean isAuditDate = false;
	
	private ItemAction[] hideAction = {this.actionEdit,this.actionAddNew,this.actionRemove,this.actionLocate,this.actionView};
    
	/**
	 * 退房汇总列值
	 * 退房分类统计值
	 * 预订退房、认购退房、签约退房
	 * 房间物理套数和销售次数
	 */
	private Map quitRoomSumMap = new HashMap();
	private Map quitRoomSumDataMap = new HashMap();
	private Map orderQuitRoomMap = new HashMap();
	private Map purchaseQuitRoomMap = new HashMap();
	private Map signQuitRoomMap = new HashMap();
	private Map roomMap = new HashMap();
	private Map purchaseMap = new HashMap();
	private Map quitMap = new HashMap();
	
	/**
	 * 换房汇总列值
	 * 换房分类统计值
	 * 预订换房、认购换房、签约换房
	 */
	private Map swapRoomSumMap = new HashMap();
	private Map orderSwapRoomMap = new HashMap();
	private Map purchaseSwapRoomMap = new HashMap();
	private Map signSwapRoomMap = new HashMap();
	
	
	/**
	 * 更名汇总列值
	 * 更名分类统计值
	 * 预订更名、认购更名、签约更名
	 */
	private Map renameRoomSumMap = new HashMap();
	private Map orderRenameRoomMap = new HashMap();
	private Map purchaseRenameRoomMap = new HashMap();
	private Map signRenameRoomMap = new HashMap();
	
	
	/**
	 * 变更汇总列值
	 * 变更分类统计值
	 * 预订变更、认购变更、签约变更
	 */
	private Map changeRoomSumMap = new HashMap();
	private Map orderChangeRoomMap = new HashMap();
	private Map purchaseChangeRoomMap = new HashMap();
	private Map signChangeRoomMap = new HashMap();
	
	/**
	 * 调整汇总列值
	 * 包括如下列：建筑面积、套内面积、调整套数、调整总额、调整认购率
	 * 由于后续需求也会有调整汇总和调整分类统计的需求，
	 */
	private Map adjustRoomSumMap = new HashMap();
	private Map orderAdjustRoomMap = new HashMap();
	private Map purchaseAjustRoomMap = new HashMap();
	private Map signAjustRoomMap = new HashMap();
	
    /**
     * output class constructor
     */
    public SpecialOprationStatListUI() throws Exception
    {
        super();
        
    	RoomDisplaySetting setting= new RoomDisplaySetting();
    	this.isAuditDate = setting.getBaseRoomSetting().isAuditDate();
    }
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
    	super.actionQuery_actionPerformed(e);
    }
    public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
    public void onLoad() throws Exception
    {
    	this.tblMain.checkParsed();
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		
    	super.onLoad();
    	this.menuEdit.setVisible(false);
    	FDCClientHelper.setActionVisible(hideAction,false);
    }
    
    protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
//		commonQueryDialog.setUiObject(null);
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
    private SpecialOprationStatNewFilterUI getFilterUI() throws Exception
	{
		if (this.filterUI == null)
		{
			this.filterUI = new SpecialOprationStatNewFilterUI(this,this.actionOnLoad);
		}
		return this.filterUI;
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
  
    protected void execQuery()
    {
    	this.tblMain.checkParsed();
    	
    	try
		{
    		createTree();
    		initTable();
    		getAllData();
    		loadDataToTable();
    		setUnionData();
    		
		} catch (Exception e)
		{
			super.handUIException(e);
		}
    }
    protected void checkTableParsed()
    {
    	super.checkTableParsed();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	
    }

	protected String getEditUIName()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return null;
	}
	private void createTree() throws EASBizException, BOSException, SQLException
	{

		tblMain.removeRows();
		TreeModel buildingTree = null;
		try
		{
			buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit());
		} catch (Exception e)
		{
			this.handleException(e);
		}
		if(buildingTree == null)
			return;
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
	
	}
	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException
	{
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++)
		{
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + building.getName());
			row.setUserObject(node.getUserObject());
			this.rowMap.put(building.getId().toString(), row);
		} else
		{
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
			row.setUserObject(node.getUserObject());
		}
		if (!node.isLeaf())
		{
			for (int i = 0; i < node.getChildCount(); i++)
			{
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}		

	/**
	 * 填充数据
	 * @throws Exception
	 */
	private void getAllData() throws Exception
	{
		//获取退房汇总数据
		Map resultMap = SpecialOprationStatFacadeFactory.getRemoteInstance().getSpecialStatData(this.getRptParamMap());
		Map roomAndPurchaseMap = (Map)resultMap.get("sellAndRoom");
		if(roomAndPurchaseMap==null)return;
		roomMap = (Map)roomAndPurchaseMap.get("room");
		//销售次数
		purchaseMap = (Map)roomAndPurchaseMap.get("purchase");
		//退房次数
		quitMap = (Map)roomAndPurchaseMap.get("quitCount");
		
		/**
		 * 获取退房分类统计
		 * 预订退房、认购退房、签约退房
		 */
		Map quitClassMap = (Map)resultMap.get("quitClass");
		orderQuitRoomMap = (Map)quitClassMap.get("preQuit");
		purchaseQuitRoomMap = (Map)quitClassMap.get("purQuit");
		signQuitRoomMap = (Map)quitClassMap.get("signQuit");
		quitRoomSumMap = (Map)quitClassMap.get("allQuit");
		quitRoomSumDataMap = (Map)quitClassMap.get("allDataQuit");
		/**
		 * 获取换房汇总数据
		 */
		swapRoomSumMap = (Map)resultMap.get("swapRoom");
		/**
		 * 更名
		 */
		renameRoomSumMap = (Map)resultMap.get("renameRoom");
		/**
		 * 变更
		 * 包括如下列：变更套数
		 */
		changeRoomSumMap = (Map)resultMap.get("changeRoom");
	}
	
	protected IColumn addSepecialColumn(KDTable table, String colKey, String head0Value, String head1Value, String head2Value)
	{
		IColumn col = table.addColumn();
		col.setKey(colKey);
		
		table.getHeadRow(0).getCell(colKey).setValue(head0Value);
		table.getHeadRow(1).getCell(colKey).setValue(head1Value);
		table.getHeadRow(2).getCell(colKey).setValue(head2Value);
		
//		this.tblMain.getColumn(colKey).getStyleAttributes().setHided(true);
		FDCHelper.formatTableNumber(table, colKey);
		return col;
	}
	private final String COL_QUIT_SUM_KEY = "quitSum";
	
	private final String COL_BUILDAREA = "buildArea";
	private final String COL_SUITAREA = "suitArea";
	private final String COL_NEWBUILDAREA = "newbuildArea";
	private final String COL_NEWSUITAREA = "newsuitArea";
	private final String COL_SUIT = "suit";
	private final String COL_CONTRACT_AMT = "contractAmt";
	private final String COL_QUIT_AMT = "quitAmt";
	
	/**
	 * 退房（汇总）
	 * 包括如下列：建筑面积、套内面积、退房套数、合同总价、实际退款、认购退房率
	 */
	protected void initQuitHead()
	{
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+"roomCount", "退房", "退房合计", "房间物理套数");
		this.tblMain.getColumn(COL_QUIT_SUM_KEY+"roomCount").getStyleAttributes().setHided(true);
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+COL_BUILDAREA, "退房", "退房合计", "建筑面积");
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+COL_SUITAREA, "退房", "退房合计", "套内面积");
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+COL_CONTRACT_AMT, "退房", "退房合计", "合同总价");
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+COL_QUIT_AMT, "退房", "退房合计", "实际退款");
		
		IColumn quitCol = addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+COL_SUIT, "退房", "退房合计", "退房套数");
		quitCol.getStyleAttributes().setNumberFormat("#0");
		
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+"quitRate", "退房", "退房合计", "退房率（%）");
		
		//添加两列辅助列，销售次数，销售退房次数，用于计算销售退房率
		IColumn purchaseSuit = addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+"perchaseSuit", "退房", "退房合计", "销售次数");
		purchaseSuit.getStyleAttributes().setNumberFormat("#0");
		
		IColumn purchaseQuitSuit = addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+"perchaseQuitSuit", "退房", "退房合计", "销售退房次数");
		purchaseQuitSuit.getStyleAttributes().setNumberFormat("#0");
		
		addSepecialColumn(tblMain, COL_QUIT_SUM_KEY+"purchaseQuitRate", "退房", "退房合计", "销售退房率（%）");
	}
	
	private void loadQuitSumData(IRow row) throws Exception
	{
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		if(quitRoomSumMap==null)return;
		BigDecimal[] tmpArray = (BigDecimal[])quitRoomSumMap.get(buildId);
		if(tmpArray == null)
			return ;
		//如果包含附属房产的时候值都存在quitRoomSumMap里，如果不包含的时候quitRoomSumMap只包含实际退款的数据
		if(isIncludeAttach())
		{
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_BUILDAREA) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_BUILDAREA).setValue(tmpArray[0]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUITAREA) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_SUITAREA).setValue(tmpArray[1]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_CONTRACT_AMT) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_CONTRACT_AMT).setValue(tmpArray[2]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_QUIT_AMT) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_QUIT_AMT).setValue(tmpArray[3]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUIT) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_SUIT).setValue(tmpArray[4]);
			}
		}else
		{
			if(quitRoomSumDataMap==null)return;
			BigDecimal[] tmpArray2 = (BigDecimal[])quitRoomSumDataMap.get(buildId);
			
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_BUILDAREA) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_BUILDAREA).setValue(tmpArray2[0]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUITAREA) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_SUITAREA).setValue(tmpArray2[1]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_CONTRACT_AMT) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_CONTRACT_AMT).setValue(tmpArray2[2]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_QUIT_AMT) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_QUIT_AMT).setValue(tmpArray[3]);
			}
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUIT) != null)
			{
				row.getCell(COL_QUIT_SUM_KEY+COL_SUIT).setValue(tmpArray2[4]);
			}
		}
//		if(quitRoomSumDataMap==null)return;
//		BigDecimal[] tmpArray2 = (BigDecimal[])quitRoomSumDataMap.get(buildId);
//		
//		if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_BUILDAREA) != null)
//		{
//			row.getCell(COL_QUIT_SUM_KEY+COL_BUILDAREA).setValue(tmpArray2[0]);
//		}
//		if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUITAREA) != null)
//		{
//			row.getCell(COL_QUIT_SUM_KEY+COL_SUITAREA).setValue(tmpArray2[1]);
//		}
//		if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_CONTRACT_AMT) != null)
//		{
//			row.getCell(COL_QUIT_SUM_KEY+COL_CONTRACT_AMT).setValue(tmpArray2[2]);
//		}
//		if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_QUIT_AMT) != null)
//		{
//			row.getCell(COL_QUIT_SUM_KEY+COL_QUIT_AMT).setValue(tmpArray[3]);
//		}
//		if(tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUIT) != null)
//		{
//			row.getCell(COL_QUIT_SUM_KEY+COL_SUIT).setValue(tmpArray2[4]);
//		}
//		if(tblMain.getColumn(COL_QUIT_SUM_KEY+"perchaseSuit") != null)
//		{
//			row.getCell(COL_QUIT_SUM_KEY+"perchaseSuit").setValue(tmpArray[5]);
//		}
		if(tblMain.getColumn(COL_QUIT_SUM_KEY+"perchaseQuitSuit") != null)
		{
//			row.getCell(COL_QUIT_SUM_KEY+"perchaseQuitSuit").setValue(tmpArray[7]);
		}
		/*if(tblMain.getColumn(COL_QUIT_SUM_KEY+"quitRate") != null)
		{
			row.getCell(COL_QUIT_SUM_KEY+"quitRate").setValue(FDCHelper.divide(tmpArray[4], tmpArray[5]));
		}*/
	}
	
	private final String COL_ORDER_QUIT = "orderQuit";
	private final String COL_TAKE_QUIT = "takeQuit";
	private final String COL_SIGN_QUIT = "signQuit";
	/**
	 * 退房分类统计明细
	 * 预订退房、认购退房、签约退房
	 * 对应的建筑面积,套内面积、合同总价、实际退款, 套数
	 */
	protected void initQuitClassifyHead()
	{
		//预订退房
		addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_BUILDAREA, "退房","预定退房", "建筑面积");
		addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_SUITAREA,  "退房","预定退房", "套内面积");
		addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_NEWBUILDAREA, "退房", "预定退房", "实际退房建筑面积");
		addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_NEWSUITAREA,  "退房", "预定退房", "实际退房套内面积");
		addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_CONTRACT_AMT, "退房","预定退房", "合同总价");
		addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_QUIT_AMT,  "退房","预定退房", "实际退款");
		
		IColumn orderCol = addSepecialColumn(tblMain, COL_ORDER_QUIT+COL_SUIT,      "退房","预定退房", "物理退房套数");
		orderCol.getStyleAttributes().setNumberFormat("#0");
		
		//认购退房
		addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_BUILDAREA, "退房", "认购退房", "建筑面积");
		addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_SUITAREA,  "退房", "认购退房", "套内面积");
		addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_NEWBUILDAREA, "退房", "认购退房", "实际退房建筑面积");
		addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_NEWSUITAREA,  "退房", "认购退房", "实际退房套内面积");
		addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_CONTRACT_AMT, "退房", "认购退房", "合同总价");
		addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_QUIT_AMT,  "退房", "认购退房", "实际退款");
		IColumn takeCol = addSepecialColumn(tblMain, COL_TAKE_QUIT+COL_SUIT,      "退房", "认购退房", "物理退房套数");
		takeCol.getStyleAttributes().setNumberFormat("#0");
		
		//签约退房
		addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_BUILDAREA, "退房", "签约退房", "建筑面积");
		addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_SUITAREA,  "退房", "签约退房", "套内面积");
		addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_NEWBUILDAREA, "退房", "签约退房", "实际退房建筑面积");
		addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_NEWSUITAREA,  "退房", "签约退房", "实际退房套内面积");
		addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_CONTRACT_AMT, "退房", "签约退房", "合同总价");
		addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_QUIT_AMT,  "退房", "签约退房", "实际退款");
		IColumn signCol = addSepecialColumn(tblMain, COL_SIGN_QUIT+COL_SUIT,      "退房", "签约退房", "物理退房套数");
		signCol.getStyleAttributes().setNumberFormat("#0");
		
	}
	
	private void loadQuitRoomAndSellData(IRow row)
	{
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		
		//房间物理套数
		BigDecimal roomCount = (BigDecimal)roomMap.get(buildId);
		roomCount = roomCount == null?new BigDecimal(0):roomCount;
		row.getCell(COL_QUIT_SUM_KEY+"roomCount").setValue(roomCount);
		//销售次数
		BigDecimal purchaseCount = (BigDecimal)purchaseMap.get(buildId);
		if(tblMain.getColumn(COL_QUIT_SUM_KEY+"perchaseSuit") != null)
		{
			row.getCell(COL_QUIT_SUM_KEY+"perchaseSuit").setValue(purchaseCount);
		}
		//销售退房次数
		BigDecimal quitCount = (BigDecimal)quitMap.get(buildId);
		if(tblMain.getColumn(COL_QUIT_SUM_KEY+"perchaseQuitSuit") != null)
		{
			row.getCell(COL_QUIT_SUM_KEY+"perchaseQuitSuit").setValue(quitCount);
		}
		purchaseCount = purchaseCount == null?new BigDecimal(0):purchaseCount;
		BigDecimal sellQuitCount = (BigDecimal)row.getCell(COL_QUIT_SUM_KEY+"perchaseQuitSuit").getValue();
		sellQuitCount = sellQuitCount==null?new BigDecimal(0):sellQuitCount;
		
		if(tblMain.getColumn(COL_QUIT_SUM_KEY+"purchaseQuitRate") != null)
		{
			if(sellQuitCount.compareTo(new BigDecimal(0))==0 || purchaseCount.compareTo(new BigDecimal(0))==0)
			{
				row.getCell(COL_QUIT_SUM_KEY+"purchaseQuitRate").setValue(null);
			}else
			{
				row.getCell(COL_QUIT_SUM_KEY+"purchaseQuitRate").setValue(sellQuitCount.multiply(new BigDecimal(100)).divide(purchaseCount,2,BigDecimal.ROUND_HALF_UP));
			}
			
		}
	}
	
	private void loadQuitDetailData(IRow row) throws Exception
	{
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		if(isIncludePreOrder())
		{
			if(orderQuitRoomMap==null)return;
			//预订退房
			BigDecimal[] tmpOrderQuit = (BigDecimal[])orderQuitRoomMap.get(buildId);
			if(tmpOrderQuit != null)
			{
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_BUILDAREA) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_BUILDAREA).setValue(tmpOrderQuit[0]);
				}
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_SUITAREA) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_SUITAREA).setValue(tmpOrderQuit[1]);
				}
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_CONTRACT_AMT) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_CONTRACT_AMT).setValue(tmpOrderQuit[2]);
				}
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_QUIT_AMT) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_QUIT_AMT).setValue(tmpOrderQuit[3]);
				}
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_SUIT) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_SUIT).setValue(tmpOrderQuit[4]);
				}
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_NEWBUILDAREA) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_NEWBUILDAREA).setValue(tmpOrderQuit[5]);
				}
				if(tblMain.getColumn(COL_ORDER_QUIT+COL_NEWSUITAREA) != null)
				{
					row.getCell(COL_ORDER_QUIT+COL_NEWSUITAREA).setValue(tmpOrderQuit[6]);
				}
			}
		}else
		{
			tblMain.getColumn(COL_ORDER_QUIT+COL_BUILDAREA).getStyleAttributes().setHided(true);
			tblMain.getColumn(COL_ORDER_QUIT+COL_SUITAREA).getStyleAttributes().setHided(true);
			tblMain.getColumn(COL_ORDER_QUIT+COL_CONTRACT_AMT).getStyleAttributes().setHided(true);
			tblMain.getColumn(COL_ORDER_QUIT+COL_QUIT_AMT).getStyleAttributes().setHided(true);
			tblMain.getColumn(COL_ORDER_QUIT+COL_SUIT).getStyleAttributes().setHided(true);
			tblMain.getColumn(COL_ORDER_QUIT+COL_NEWBUILDAREA).getStyleAttributes().setHided(true);
			tblMain.getColumn(COL_ORDER_QUIT+COL_NEWSUITAREA).getStyleAttributes().setHided(true);
		}
		
		if(purchaseQuitRoomMap==null)return;
		//认购退房
		BigDecimal[] tmppurchaseQuit = (BigDecimal[])purchaseQuitRoomMap.get(buildId);
		if(tmppurchaseQuit != null)
		{
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_BUILDAREA) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_BUILDAREA).setValue(tmppurchaseQuit[0]);
			}
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_SUITAREA) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_SUITAREA).setValue(tmppurchaseQuit[1]);
			}
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_CONTRACT_AMT) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_CONTRACT_AMT).setValue(tmppurchaseQuit[2]);
			}
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_QUIT_AMT) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_QUIT_AMT).setValue(tmppurchaseQuit[3]);
			}
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_SUIT) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_SUIT).setValue(tmppurchaseQuit[4]);
			}
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_NEWBUILDAREA) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_NEWBUILDAREA).setValue(tmppurchaseQuit[5]);
			}
			if(tblMain.getColumn(COL_TAKE_QUIT+COL_NEWSUITAREA) != null)
			{
				row.getCell(COL_TAKE_QUIT+COL_NEWSUITAREA).setValue(tmppurchaseQuit[6]);
			}
		}
		if(signQuitRoomMap==null)return;
		//签约认购
		BigDecimal[] tmpSignQuit = (BigDecimal[])signQuitRoomMap.get(buildId);
		if(tmpSignQuit != null)
		{
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_BUILDAREA) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_BUILDAREA).setValue(tmpSignQuit[0]);
			}
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_SUITAREA) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_SUITAREA).setValue(tmpSignQuit[1]);
			}
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_CONTRACT_AMT) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_CONTRACT_AMT).setValue(tmpSignQuit[2]);
			}
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_QUIT_AMT) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_QUIT_AMT).setValue(tmpSignQuit[3]);
			}
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_SUIT) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_SUIT).setValue(tmpSignQuit[4]);
			}
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_NEWBUILDAREA) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_NEWBUILDAREA).setValue(tmpSignQuit[5]);
			}
			if(tblMain.getColumn(COL_SIGN_QUIT+COL_NEWSUITAREA) != null)
			{
				row.getCell(COL_SIGN_QUIT+COL_NEWSUITAREA).setValue(tmpSignQuit[6]);
			}
		}
	}
	
	private final String COL_SWAP_SUM_KEY = "swapSum";
	private final String COL_SWAP_SUIT = "swapSuit";
	private final String COL_OLD_CONTRACTAMT = "oldContractAmt";
	private final String COL_NEW_CONTRACTAMT = "newContractAmt";
	private final String COL_SUB_AMT = "subAmt";
	/**
	 * 换房(汇总)
	 * 包括如下列：建筑面积、套内面积、换房套数、原房合同总价、新房合同总价、差额合同总价。
	 */
	protected void initSwapHead()
	{
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_BUILDAREA, "换房", "换房合计", "原主房产建筑面积");
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_SUITAREA,  "换房", "换房合计", "原主房产套内面积");
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_NEWBUILDAREA, "换房", "换房合计", "新主房产建筑面积");
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_NEWSUITAREA,  "换房", "换房合计", "新主房产套内面积");
		IColumn swapCol = addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_SWAP_SUIT, "换房", "换房合计", "换房套数");
		swapCol.getStyleAttributes().setNumberFormat("#0");
		
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_OLD_CONTRACTAMT, "换房", "换房合计", "原房间合同总价");
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_NEW_CONTRACTAMT, "换房", "换房合计", "新房间合同总价");
		addSepecialColumn(tblMain, COL_SWAP_SUM_KEY+COL_SUB_AMT, "换房", "换房合计", "差额合同总价");
	}
	private void loadSwapSumData(IRow row)
	{
		
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		
		BigDecimal[] tmpSwapSum = (BigDecimal[])swapRoomSumMap.get(buildId);
		if(tmpSwapSum == null)
			return ;
		
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_BUILDAREA) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_BUILDAREA).setValue(tmpSwapSum[0]);
		}
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_SUITAREA) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_SUITAREA).setValue(tmpSwapSum[1]);
		}
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_SWAP_SUIT) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_SWAP_SUIT).setValue(tmpSwapSum[2]);
		}
		
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_OLD_CONTRACTAMT) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_OLD_CONTRACTAMT).setValue(tmpSwapSum[3]);
		}
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_NEW_CONTRACTAMT) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_NEW_CONTRACTAMT).setValue(tmpSwapSum[4]);
		}
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_SUB_AMT) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_SUB_AMT).setValue(tmpSwapSum[5]);
		}
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_NEWBUILDAREA) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_NEWBUILDAREA).setValue(tmpSwapSum[6]);
		}
		if(tblMain.getColumn(COL_SWAP_SUM_KEY+COL_NEWSUITAREA) != null)
		{
			row.getCell(COL_SWAP_SUM_KEY+COL_NEWSUITAREA).setValue(tmpSwapSum[7]);
		}
	}
	
	private final String COL_ORDER_SWAP = "orderSwap";
	private final String COL_TAKE_SWAP = "takeSwap";
	private final String COL_SIGN_SWAP = "signSwap";
	
	/**
	 * 换房分类统计明细
	 * 预订换房、认购换房、签约换房
	 * 对应的建筑面积,套内面积,换房套数,原房合同总价,新房合同总价
	 */
	protected void initSwapClassifyHead()
	{
		//用于后续实现
		if(true)
			return ;
		
		//预订换房
		addSepecialColumn(tblMain, COL_ORDER_SWAP+COL_BUILDAREA, "换房", "预订换房", "建筑面积");
		addSepecialColumn(tblMain, COL_ORDER_SWAP+COL_SUITAREA,   "换房","预订换房", "套内面积");
		addSepecialColumn(tblMain, COL_ORDER_SWAP+COL_SUIT,  "换房","预订换房", "换房套数");
		addSepecialColumn(tblMain, COL_ORDER_SWAP+COL_OLD_CONTRACTAMT, "换房", "预订换房", "原房间合同总价");
		addSepecialColumn(tblMain, COL_ORDER_SWAP+COL_NEW_CONTRACTAMT,  "换房","预订换房", "新房间合同总价");
		
		//认购换房
		addSepecialColumn(tblMain, COL_TAKE_SWAP+COL_BUILDAREA,  "换房", "认购换房", "建筑面积");
		addSepecialColumn(tblMain, COL_TAKE_SWAP+COL_SUITAREA,   "换房", "认购换房", "套内面积");
		addSepecialColumn(tblMain, COL_TAKE_SWAP+COL_SUIT,  "换房", "认购换房", "换房套数");
		addSepecialColumn(tblMain, COL_TAKE_SWAP+COL_OLD_CONTRACTAMT,  "换房", "认购换房", "原房间合同总价");
		addSepecialColumn(tblMain, COL_TAKE_SWAP+COL_NEW_CONTRACTAMT,  "换房", "认购换房", "新房间合同总价");
		
		//签约换房
		addSepecialColumn(tblMain, COL_SIGN_SWAP+COL_BUILDAREA,  "换房", "签约换房", "建筑面积");
		addSepecialColumn(tblMain, COL_SIGN_SWAP+COL_SUITAREA,   "换房", "签约换房", "套内面积");
		addSepecialColumn(tblMain, COL_SIGN_SWAP+COL_SUIT,  "换房", "签约换房", "换房套数");
		addSepecialColumn(tblMain, COL_SIGN_SWAP+COL_OLD_CONTRACTAMT,  "换房", "签约换房", "原房间合同总价");
		addSepecialColumn(tblMain, COL_SIGN_SWAP+COL_NEW_CONTRACTAMT,  "换房", "签约换房", "新房间合同总价");
	}
	private void loadSwapDetailData(IRow row)
	{
		
	}
	
	private final String COL_RENAME_KEY = "renameSum";
	private final String COL_PURCHASE_SUIT = "purchaseSuit";

	/**
	 * 更名
	 * 包括如下列：更名套数，更名认购率
	 * 由于后续需求也会有更名汇总和更名分类统计的需求，
	 * 如果需要实现，在此添加即可
	 */
	protected void initRenameHead()
	{
		IColumn renameCol = addSepecialColumn(tblMain, COL_RENAME_KEY+COL_SUIT,  "更名", "更名", "更名套数");
		renameCol.getStyleAttributes().setNumberFormat("#0");
		
//		IColumn purchaseCol = addSepecialColumn(tblMain, COL_RENAME_KEY+COL_PURCHASE_SUIT,  "更名", "更名合计", "认购套数");
//		purchaseCol.getStyleAttributes().setNumberFormat("#0");
		
//		addSepecialColumn(tblMain, COL_RENAME_KEY+"renameRate",  "更名","更名合计", "更名认购率");
	}
	private void loadRenameSumData(IRow row)
	{
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		
		BigDecimal tmpRenameSum = (BigDecimal)renameRoomSumMap.get(buildId);
		if(tmpRenameSum == null)
			return ;
		if(tblMain.getColumn(COL_RENAME_KEY+COL_SUIT) != null)
		{
			row.getCell(COL_RENAME_KEY+COL_SUIT).setValue(tmpRenameSum);
		}
//		if(tblMain.getColumn(COL_RENAME_KEY+COL_PURCHASE_SUIT) != null)
//		{
//			row.getCell(COL_RENAME_KEY+COL_PURCHASE_SUIT).setValue(tmpRenameSum[1]);
//		}
		/*if(tblMain.getColumn(COL_RENAME_KEY+"renameRate") != null)
		{
			row.getCell(COL_RENAME_KEY+"renameRate").setValue(FDCHelper.divide(tmpRenameSum[0], tmpRenameSum[1]));
		}*/
	}
	
	/**
	 * 更名分类统计实现
	 * 由于后续需求也会有更名汇总和更名分类统计的需求，
	 * 如果需要实现，在此添加即可
	 */
	protected void initRenameClassifyHead()
	{
		
	}
	private void loadRenameDetailData(IRow row)
	{
		
	}
	
	private final String COL_CHANGE_KEY = "changeSum";
	/**
	 * 变更
	 * 包括如下列：变更套数，变更认购率
	 * 由于后续需求也会有变更汇总和变更分类统计的需求，
	 * 如果需要实现，在此添加即可
	 */
	protected void initChangeHead()
	{
		IColumn changeCols = addSepecialColumn(tblMain, COL_CHANGE_KEY+COL_SUIT,  "变更", "变更", "变更套数");
		changeCols.getStyleAttributes().setNumberFormat("#0");
		
//		IColumn purchaseCols = addSepecialColumn(tblMain, COL_CHANGE_KEY+COL_PURCHASE_SUIT,  "变更", "变更合计", "认购套数");
//		purchaseCols.getStyleAttributes().setNumberFormat("#0");
//		
//		addSepecialColumn(tblMain, COL_CHANGE_KEY+"changeRate",  "变更", "变更合计", "变更认购率");
	}
	private void loadChangeSumData(IRow row)
	{
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		
		BigDecimal tmpChangeSum = (BigDecimal)changeRoomSumMap.get(buildId);
		if(tmpChangeSum == null)
			return ;
		
		if(tblMain.getColumn(COL_CHANGE_KEY+COL_SUIT) != null)
		{
			row.getCell(COL_CHANGE_KEY+COL_SUIT).setValue(tmpChangeSum);
		}
//		if(tblMain.getColumn(COL_CHANGE_KEY+COL_PURCHASE_SUIT) != null)
//		{
//			row.getCell(COL_CHANGE_KEY+COL_PURCHASE_SUIT).setValue(tmpChangeSum[1]);
//		}
		/*if(tblMain.getColumn(COL_CHANGE_KEY+"changeRate") != null)
		{
			row.getCell(COL_CHANGE_KEY+"changeRate").setValue(FDCHelper.divide(tmpChangeSum[0], tmpChangeSum[1]));
		}*/
		
	}
	
	/**
	 * 变更分类统计实现
	 * 由于后续需求也会有变更汇总和变更分类统计的需求，
	 * 如果需要实现，在此添加即可
	 */
	protected void initChangeClassifyHead()
	{
		
	}
	private void loadChangeDetailData(IRow row)
	{
		
	}
	
	private final String COL_ADJUST_KEY = "adjustKey";
	/**
	 * 调整
	 * 包括如下列：建筑面积、套内面积、调整套数、调整总额、调整认购率
	 * 由于后续需求也会有调整汇总和调整分类统计的需求，
	 * 如果需要实现，在此添加即可
	 */
	protected void initAdjustHead()
	{
//		addSepecialColumn(tblMain, COL_ADJUST_KEY+COL_BUILDAREA,  "调整", "调整合计", "建筑面积");
//		addSepecialColumn(tblMain, COL_ADJUST_KEY+COL_SUITAREA,  "调整", "调整合计", "套内面积");
//		
//		IColumn suitCol = addSepecialColumn(tblMain, COL_ADJUST_KEY+COL_SUIT,  "调整", "调整合计", "调整套数");
//		suitCol.getStyleAttributes().setNumberFormat("#0");
//		IColumn purchaseSuit = addSepecialColumn(tblMain, COL_ADJUST_KEY+COL_PURCHASE_SUIT,  "调整", "调整合计", "认购套数");
//		purchaseSuit.getStyleAttributes().setNumberFormat("#0");
//		
//		addSepecialColumn(tblMain, COL_ADJUST_KEY+"adjustAmt",  "调整", "调整合计", "调整总额");
//		addSepecialColumn(tblMain, COL_ADJUST_KEY+"adjustRate",  "调整", "调整合计", "调整认购率");
	}
	private void loadAdjustSumData(IRow row)
	{
		BuildingInfo build = (BuildingInfo)row.getUserObject();
		String buildId = build.getId().toString();
		
		BigDecimal[] tmpAdjustSum = (BigDecimal[])adjustRoomSumMap.get(buildId);
		if(tmpAdjustSum == null)
			return ;
		
		if(tblMain.getColumn(COL_ADJUST_KEY+COL_BUILDAREA) != null)
		{
			row.getCell(COL_ADJUST_KEY+COL_BUILDAREA).setValue(tmpAdjustSum[0]);
		}
		if(tblMain.getColumn(COL_ADJUST_KEY+COL_SUITAREA) != null)
		{
			row.getCell(COL_ADJUST_KEY+COL_SUITAREA).setValue(tmpAdjustSum[1]);
		}
		if(tblMain.getColumn(COL_ADJUST_KEY+"adjustAmt") != null)
		{
			row.getCell(COL_ADJUST_KEY+"adjustAmt").setValue(tmpAdjustSum[2]);
		}
		if(tblMain.getColumn(COL_ADJUST_KEY+COL_SUIT) != null)
		{
			row.getCell(COL_ADJUST_KEY+COL_SUIT).setValue(tmpAdjustSum[3]);
		}
		if(tblMain.getColumn(COL_ADJUST_KEY+COL_PURCHASE_SUIT) != null)
		{
			row.getCell(COL_ADJUST_KEY+COL_PURCHASE_SUIT).setValue(tmpAdjustSum[4]);
		}
		/*if(tblMain.getColumn(COL_ADJUST_KEY+"adjustRate") != null)
		{
			row.getCell(COL_ADJUST_KEY+"adjustRate").setValue(FDCHelper.divide(tmpAdjustSum[3], tmpAdjustSum[4]));
		}*/
	}
	
	/**
	 * 调整分类统计实现
	 * 由于后续需求也会有调整汇总和调整分类统计的需求，
	 * 如果需要实现，在此添加即可
	 */
	protected void initAdjustClassifyHead()
	{
		
	}
	private void loadAjustDetailData(IRow row)
	{
		
	}
	
	protected void initTable() throws Exception
	{
		/**
		 * 删除原有列，根据所选的信息添加列
		 */
		for(int i = tblMain.getColumnCount()-1; i > 0; --i)
    	{
    		tblMain.removeColumn(i);
    	}
		
		/**
		 * 根据所选的信息动态添加列
		 */
		//退房
		initQuitHead();
//		if(getIsCheckOut())
//		{
			initQuitClassifyHead();
//		}
		
		//换房
		initSwapHead();
//		if(getIsSwap())
//		{
//			initSwapClassifyHead();
//		}
		
		//更名
		initRenameHead();
//		if(getIsRename())
//		{
//			initRenameClassifyHead();
//		}
		//变更
		initChangeHead();
//		if(getIsChange())
//		{
//			initChangeClassifyHead();
//		}
		
		//调整
		initAdjustHead();
//		if(getIsAdjust())
//		{
//			initAdjustClassifyHead();
//		}
		
		//设置表头融合
		tblMain.getHeadMergeManager().mergeBlock(0, 0, 2, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		tblMain.getViewManager().setFreezeView(-1, 1);
		
		/**
		 * COL_QUIT_SUM_KEY+"quitRate"
		 * COL_RENAME_KEY+"renameRate"
		 * COL_CHANGE_KEY+"changeRate"
		 * COL_ADJUST_KEY+"adjustRate"
		 */
		FDCHelper.formatTableNumber(tblMain, COL_QUIT_SUM_KEY+"quitRate", "#,####0.00;-#,####0.00");
//		FDCHelper.formatTableNumber(tblMain, new String[]{COL_QUIT_SUM_KEY+"quitRate", COL_RENAME_KEY+"renameRate", COL_CHANGE_KEY+"changeRate", COL_ADJUST_KEY+"adjustRate"}, "#,####0.0000;-#,####0.0000");
	}
	
	private void loadDataToTable() throws Exception
	{
		for(int i = 0; i < tblMain.getRowCount(); ++i)
		{
			IRow row = tblMain.getRow(i);
			if(row.getUserObject() != null && row.getUserObject() instanceof BuildingInfo)
			{
				/**
				 * 退房（汇总）
				 * 包括如下列：建筑面积、套内面积、退房套数、合同总价、实际退款、认购退房率
				 */
				loadQuitSumData(row);
//				if(getIsCheckOut())
//				{
					loadQuitDetailData(row);
					loadQuitRoomAndSellData(row);
//				}
				
				/**
				 * 换房(汇总)
				 * 包括如下列：建筑面积、套内面积、换房套数、原房合同总价、新房合同总价、差额合同总价。
				 */
				loadSwapSumData(row);
//				if(getIsSwap())
//				{
					loadSwapDetailData(row);
//				}
				
				/**
				 * 更名
				 * 包括如下列：更名套数，更名认购率
				 * 由于后续需求也会有更名汇总和更名分类统计的需求，
				 * 如果需要实现，在此添加即可
				 */
				loadRenameSumData(row);
//				if(getIsRename())
//				{
//					loadRenameDetailData(row);
//				}
				
				/**
				 * 变更
				 * 包括如下列：变更套数，变更认购率
				 * 由于后续需求也会有变更汇总和变更分类统计的需求，
				 * 如果需要实现，在此添加即可
				 */
				loadChangeSumData(row);
//				if(getIsChange())
//				{
//					loadChangeDetailData(row);
//				}
				
				/**
				 * 调整
				 * 包括如下列：建筑面积、套内面积、调整套数、调整总额、调整认购率
				 * 由于后续需求也会有调整汇总和调整分类统计的需求，
				 * 如果需要实现，在此添加即可
				 */
//				loadAdjustSumData(row);
//				if(getIsAdjust())
//				{
//					loadAjustDetailData(row);
//				}
			}
		}
	}
	
	protected void initTableParams()
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	
	public void setUnionData()
	{
		for (int i = 0; i < tblMain.getRowCount(); i++)
		{
			IRow row = tblMain.getRow(i);
			if (!(row.getUserObject() instanceof BuildingInfo))
			{
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++)
				{
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level)
					{
						break;
					}
					if (rowAfter.getUserObject() != null)
					{
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++)
				{
					if(tblMain.getColumnKey(j).equals(COL_QUIT_SUM_KEY+"quitRate") || tblMain.getColumnKey(j).equals(COL_RENAME_KEY+"renameRate")
							|| tblMain.getColumnKey(j).equals(COL_CHANGE_KEY+"changeRate") || tblMain.getColumnKey(j).equals(COL_ADJUST_KEY+"adjustRate"))
					{
						continue;
					}
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++)
					{
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null)
						{
							if (value instanceof BigDecimal)
							{
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer)
							{
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							}
							else if(value instanceof Float)
							{
								aimCost = aimCost.add(new BigDecimal(((Float) value).toString()));
							}
						}
					}
					row.getCell(j).setValue(aimCost);
					
				}
			}
		}
		
		/**
		 * COL_QUIT_SUM_KEY+"quitRate"
		 * COL_RENAME_KEY+"renameRate"
		 * COL_CHANGE_KEY+"changeRate"
		 * COL_ADJUST_KEY+"adjustRate"
		 */
		for (int i = 0; i < tblMain.getRowCount(); i++)
		{
			IRow row = tblMain.getRow(i);
			
			/**
			 * 退房率
			 * COL_QUIT_SUM_KEY+COL_SUIT
			 * COL_QUIT_SUM_KEY+"roomCount"
			 */
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+"quitRate") != null && 
					tblMain.getColumn(COL_QUIT_SUM_KEY+COL_SUIT) != null && tblMain.getColumn(COL_QUIT_SUM_KEY+"roomCount") != null)
			{
				BigDecimal suit = (BigDecimal)row.getCell(COL_QUIT_SUM_KEY+COL_SUIT).getValue();
				suit = suit==null?new BigDecimal(0):suit.multiply(new BigDecimal(100));
				BigDecimal count = (BigDecimal)row.getCell(COL_QUIT_SUM_KEY+"roomCount").getValue();
				count = count==null?new BigDecimal(0):count;
				if(suit.compareTo(new BigDecimal(0))==0 || count.compareTo(new BigDecimal(0))==0)
				{
					row.getCell(COL_QUIT_SUM_KEY+"quitRate").setValue(new BigDecimal(0));
				}else
				{
					row.getCell(COL_QUIT_SUM_KEY+"quitRate").setValue(suit.divide(count,2,BigDecimal.ROUND_HALF_UP));
				}
				
			}
			
			
			/**
			 * 销售退房率
			 * COL_QUIT_SUM_KEY+"perchaseQuitSuit"
			 * COL_QUIT_SUM_KEY+"perchaseSuit"
			 */
			if(tblMain.getColumn(COL_QUIT_SUM_KEY+"purchaseQuitRate") != null && 
					tblMain.getColumn(COL_QUIT_SUM_KEY+"perchaseQuitSuit") != null && tblMain.getColumn(COL_QUIT_SUM_KEY+"perchaseSuit") != null)
			{
				BigDecimal perchaseQuitSuit = (BigDecimal)row.getCell(COL_QUIT_SUM_KEY+"perchaseQuitSuit").getValue();
				perchaseQuitSuit = perchaseQuitSuit==null?new BigDecimal(0):perchaseQuitSuit.multiply(new BigDecimal(100));
				BigDecimal perchaseSuit = (BigDecimal)row.getCell(COL_QUIT_SUM_KEY+"perchaseSuit").getValue();
				perchaseSuit = perchaseSuit==null?new BigDecimal(0):perchaseSuit;
				if(perchaseQuitSuit.compareTo(new BigDecimal(0))==0 || perchaseSuit.compareTo(new BigDecimal(0))==0)
				{
					row.getCell(COL_QUIT_SUM_KEY+"purchaseQuitRate").setValue(new BigDecimal(0));
				}else
				{
					row.getCell(COL_QUIT_SUM_KEY+"purchaseQuitRate").setValue(perchaseQuitSuit.divide(perchaseSuit,2,BigDecimal.ROUND_HALF_UP));
				}
			}
			/**
			 * 更名认购率
			 * COL_RENAME_KEY+"renameRate"
			 * COL_RENAME_KEY+COL_SUIT
			 * COL_RENAME_KEY+COL_PURCHASE_SUIT
			 */
//			if(tblMain.getColumn(COL_RENAME_KEY+"renameRate") != null && 
//					tblMain.getColumn(COL_RENAME_KEY+COL_SUIT) != null && tblMain.getColumn(COL_RENAME_KEY+COL_PURCHASE_SUIT) != null)
//			{
//				row.getCell(COL_RENAME_KEY+"renameRate").setValue(FDCHelper.divide(row.getCell(COL_RENAME_KEY+COL_SUIT).getValue(), 
//						row.getCell(COL_RENAME_KEY+COL_PURCHASE_SUIT).getValue(), 4, BigDecimal.ROUND_HALF_UP));
//			}
			
			/**
			 * 变更认购率
			 * COL_CHANGE_KEY+"changeRate"
			 * COL_CHANGE_KEY+COL_SUIT
			 * COL_CHANGE_KEY+COL_PURCHASE_SUIT
			 */
//			if(tblMain.getColumn(COL_CHANGE_KEY+"changeRate") != null && 
//					tblMain.getColumn(COL_CHANGE_KEY+COL_SUIT) != null && tblMain.getColumn(COL_CHANGE_KEY+COL_PURCHASE_SUIT) != null)
//			{
//				row.getCell(COL_CHANGE_KEY+"changeRate").setValue(FDCHelper.divide(row.getCell(COL_CHANGE_KEY+COL_SUIT).getValue(), 
//						row.getCell(COL_CHANGE_KEY+COL_PURCHASE_SUIT).getValue(), 4, BigDecimal.ROUND_HALF_UP));
//			}
			
			/**
			 * 调整认购率
			 * COL_ADJUST_KEY+"adjustRate"
			 * COL_ADJUST_KEY+COL_SUIT
			 * COL_ADJUST_KEY+COL_PURCHASE_SUIT
			 */
//			if(tblMain.getColumn(COL_ADJUST_KEY+"adjustRate") != null && 
//					tblMain.getColumn(COL_ADJUST_KEY+COL_SUIT) != null && tblMain.getColumn(COL_ADJUST_KEY+COL_PURCHASE_SUIT) != null)
//			{
//				row.getCell(COL_ADJUST_KEY+"adjustRate").setValue(FDCHelper.divide(row.getCell(COL_ADJUST_KEY+COL_SUIT).getValue(), 
//						row.getCell(COL_ADJUST_KEY+COL_PURCHASE_SUIT).getValue(), 4, BigDecimal.ROUND_HALF_UP));
//			}
		}
	}	
	
	protected String getKeyFieldName()
	{
		return "name";
	}
	
	private Date getBeginDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
		{
			return this.getFilterUI().getBeginQueryDate(para);
		}else
		{
			java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date beginDate = df.parse("19000101");
			return beginDate;
		}
	}
	private Date getEndDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
		{
			return this.getFilterUI().getEndQueryDate(para);
		}else
		{
			java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date endDate = FDCDateHelper.getServerTimeStamp();
			return endDate;
		}
	}
	
	private boolean isIncludeAttach() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().isIncludeAttach(para);
	}
	private boolean isIncludePreOrder() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().isPreIntoSale(param);
	}
	
	protected Map getRptParamMap() throws Exception {
		/**
		 * paramMap 描述： IncludeAttachment = 包括附属房产，为真是包括，为假时不包括 BuildArea =
		 * 表示面积类型，为真是建筑面积，为假时为套内面积 PreArea = 表示面积类型，为真是预测面积，为假时为实测面积
		 * IncludeOrder = 包括预订业务， 为真是包括，为假时不包括
		 */
		Map paramMap = new HashMap();
		paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttach()));
		paramMap.put("IncludeOrder", Boolean.valueOf(isIncludePreOrder()));
		paramMap.put("BeginDate", getBeginDate());
		paramMap.put("EndDate", getEndDate());
		paramMap.put("isAuditDate",Boolean.valueOf(isAuditDate));		
		return paramMap;
	}
	
	/**
	 * 获取所选分类统计的值
	 * @throws Exception 
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}
	protected boolean initDefaultFilter()
	{
		return true;
	}
	protected boolean isAllowDefaultSolutionNull()
	{
		return false;
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
		tblMain.getViewManager().setFreezeView(-1, 1);
	}

}