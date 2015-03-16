
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.ProductTypeSellStateFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.framework.ITreeBase;

/**
 * 产品类型销售统计表
 * 
 * @author laiquan_luo
 * 
 */
public class ProductTypeSellStatListUI extends	AbstractProductTypeSellStatListUI
{
	private static final Logger logger = CoreUIObject.getLogger(ProductTypeSellStatListUI.class);

	private CommonQueryDialog commonQueryDialog = null;
	private ProductTypeSellStatFilterUI userFilter = null;
	
	/**
	 * 表格的key
	 */
	private final String tbl_key = "key";
	
	private final String tbl_productName = "productName";
	private final String tbl_roomType = "roomType";
	private final String tbl_totalCount = "totalCount";
	private final String tbl_totalArea = "totalArea";
	private final String tbl_totalStandPrice = "totalStandardPrice";
	private final String tbl_totalAvePrice= "averagePrice";
	private final String tbl_saleRate = "saleRate";
	
	private final String tbl_haveSellCount = "haveSellCount";
	private final String tbl_haveSellArea = "haveSellArea";
	private final String tbl_haveSellStandAvePrice = "haveSellStandardAveragePrice";
	private final String tbl_haveSellStandTotPrice = "haveSellStandardTotalPrice";
	
	private final String tbl_haveSellBaseAvePrice = "haveSellBaseAveragePrice";
	private final String tbl_haveSellBaseTotPrice = "haveSellBaseTotalPrice";
	private final String tbl_haveSellDealAvePrice = "haveSellDealAveragePrice";
	private final String tbl_haveSellDealTotPrice = "haveSellDealTotalPrice";
	
	private final String tbl_haveSellPrefAvePrice = "haveSellPreferentailAveragePrice";
	private final String tbl_haveSellPrefTotPrice = "haveSellPreferentialPrice";
	
	private final String tbl_unSellCount = "unSellCount";
	private final String tbl_unSellArea = "unsellArea";
	private final String tbl_unSellStandAvePrice = "unSellStandardAveragePrice";
	private final String tbl_unSellStandTotPrice = "unSellStandardTotalPrice";
	
	private final String tbl_sumGathering = "sumGathering";
	private final String tbl_RoomCompenstate = "roomComp";
	
	private final String tbl_querySellCount = "querySellCount";
	private final String tbl_querySellArea = "querySellArea";
	private final String tbl_queryPrefAvePrice = "queryPrefAvePrice";
	private final String tbl_queryPrefTotPrice = "queryPrefTotPrice";
	
	private final String tbl_queryDealAvePrice = "queryDealAvePrice";
	private final String tbl_queryDealTotPrice = "queryDealTotPrice";
	private final String tbl_queryStdAvePrice = "queryStdAvePrice";
	private final String tbl_queryStdTotPrice = "queryStdTotPrice";
	
	private final String tbl_querySumGathering = "querySumGathering";
	private final String tbl_querySellSumPrice = "querySellSumPrice";
	private final String tbl_queryPurSumPrice = "queryPurSumPrice";
	private final String tbl_querySignSumPrice = "querySignSumPrice";
	
	
	private boolean isAuditDate = false;
	
	//第一次加载
	private boolean isFirstLoad = true;
	/**
	 * output class constructor
	 */
	public ProductTypeSellStatListUI() throws Exception
	{
		super();
		
		RoomDisplaySetting setting= new RoomDisplaySetting();
    	this.isAuditDate = setting.getBaseRoomSetting().isAuditDate();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

	protected String getEditUIName()
	{
		return null;
	}
	
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		execQuery();
	}

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,
				SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()));
		
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() throws Exception
	{
		if (this.userFilter == null)
		{
			this.userFilter = new ProductTypeSellStatFilterUI(this, this.actionOnLoad);
		}
		return this.userFilter;
	}

	protected void execQuery()
	{
		tblMain.removeRows();
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		try {
			initTableHead();
//			//获取报表的过滤参数表
			Map paramMap = getRptParamMap();
//			
//			//获取报表的相关数据
			Map rptDataMap = ProductTypeSellStateFacadeFactory.getRemoteInstance().getProductSellDate(paramMap);
//			
//			//根据获取的数据填充表格
			fillRptDataToTable(rptDataMap);
//			
//			//设置汇总行
			upDateTotal();
			
		} catch (Exception e1) {
			this.handleException(e1);
		}
	}
	
	/**
	 * rptDataMap 结构如下：
	 * ProductType     -> 存储的是产品类型
	 * RptTotalSumData -> 存储的是汇总的前4列值
	 * PrductToModel   -> 产品类型下的户型集合
	 * 
	 * key列的Cell值为产品类型ID+户型ID
	 * 产品类型列的Cell值为产品类型的名称
	 * 产品类型列的Cell的userObject为产品类型的Info
	 * @param rptDataMapParam
	 */
	protected void fillRptDataToTable(Map rptDataMapParam)
	{
		ProductTypeCollection prdTypeCols = (ProductTypeCollection)rptDataMapParam.get("ProductType");
		
		Map prductToModel = (Map)rptDataMapParam.get("PrductToModel");
		Map rptTotalSumData = (Map)rptDataMapParam.get("RptTotalSumData");
		Map selledRoomData = (Map)rptDataMapParam.get("SelledRoomData");
		
		Map unSelledRoomData = (Map)rptDataMapParam.get("UnselledRoomData");
		Map sumGatheringData = (Map)rptDataMapParam.get("SumGathering");
		Map subAmtRoomData = (Map)rptDataMapParam.get("SubAmtData");
		
		Map periodGatheringData = (Map)rptDataMapParam.get("PeriodGatheringData");
		Map periodNoGatheringData = (Map)rptDataMapParam.get("PeriodNoGatheringData");
		
		int mergeStart = 0;
		int mergeEnd = 0;
		for(int index = 0; index < prdTypeCols.size(); ++index)
		{
			ProductTypeInfo tmpPrdType = prdTypeCols.get(index);
			String tmpPrdKey = String.valueOf(tmpPrdType.getId());
			
			if(prductToModel.containsKey(tmpPrdKey))
			{
				//总套数求和
				Integer subRoomCount = new Integer(0);          //小计房间套数
				BigDecimal subTotalArea = FDCHelper.ZERO;       //小计总面积
				BigDecimal subTotalStandPrice = FDCHelper.ZERO ;//小计总面积
				
				//已售房间
				Integer selledRoomCount = new Integer(0);          //小计已售总套数
				BigDecimal selledTotalArea = FDCHelper.ZERO;       //小计已售总面积
				BigDecimal selledStandTotalPrice = FDCHelper.ZERO; //小计已售标准总价
				BigDecimal selledBaseTotalPrice = FDCHelper.ZERO;  //小计已售底价总价
				BigDecimal selledDealTotalPrice = FDCHelper.ZERO;  //小计已售成交总价
				
				//未售房间
				Integer unselledRoomCount = new Integer(0);           //小计未售总套数
				BigDecimal unselledTotalArea = FDCHelper.ZERO;	      //小计未售总面积
				BigDecimal unselledStandTotalPrice = FDCHelper.ZERO;  //小计未售标准总价
				
				//累计回款
				BigDecimal sumGatheringAmt = FDCHelper.ZERO;   //小计累计汇款
				
				//补差金额
				BigDecimal sumRoomCompAmt = FDCHelper.ZERO;    //小计补差金额
				
				//查询区间-累计回款
				BigDecimal periodSumGatheringAmt = FDCHelper.ZERO; 
				
				//查询区间-除累计回款外的列
				Integer periodSelledRoomCount = new Integer(0);
				BigDecimal periodSelledArea = FDCHelper.ZERO;
				BigDecimal periYouHuiAmt = FDCHelper.ZERO;
				BigDecimal periodDealTotPrice = FDCHelper.ZERO;
				BigDecimal periodStandTotPrice = FDCHelper.ZERO;
				BigDecimal periodSelledTotAmt = FDCHelper.ZERO;
				BigDecimal periodPuchaseAmt = FDCHelper.ZERO;
				BigDecimal periodSignSumPrice = FDCHelper.ZERO;
				
				ArrayList roomModels = (ArrayList)prductToModel.get(tmpPrdKey);
				for(int j = 0; j < roomModels.size(); ++j)
				{
					String roomModelId = String.valueOf(roomModels.get(j));
					String tmpKeyValue = tmpPrdKey + roomModelId;
					
					
					//在rptTotalSumData获取数据
					Object[] tmpRptSumData = (Object[])rptTotalSumData.get(tmpKeyValue);
					
					//填充总数据列
					IRow tmpRow = tblMain.addRow();
					tmpRow.getCell(tbl_key).setValue(tmpKeyValue); //设置Key的值
					tmpRow.getCell(tbl_productName).setValue(tmpPrdType.getName()); //产品类型列
					if(tmpRptSumData != null && tmpRptSumData.length > 0)
					{
						//填充数据部分
						tmpRow.getCell(tbl_roomType).setValue(tmpRptSumData[0]);        //户型
						tmpRow.getCell(tbl_totalCount).setValue(tmpRptSumData[1]);      //总套数
						tmpRow.getCell(tbl_totalArea).setValue(tmpRptSumData[2]);       //总面积
						tmpRow.getCell(tbl_totalStandPrice).setValue(tmpRptSumData[3]); //总金额
						if(tmpRptSumData[3]==null || tmpRptSumData[2]==null)
						{
							tmpRow.getCell(tbl_totalAvePrice).setValue(null); //总均价
						}else
						{
							tmpRow.getCell(tbl_totalAvePrice).setValue(FDCHelper.divide(tmpRptSumData[3], tmpRptSumData[2])); //总均价

						}
												
						//小计行显示部分
						subRoomCount = new Integer(FDCHelper.add(tmpRptSumData[1], subRoomCount).intValue());
						subTotalArea = FDCHelper.add(subTotalArea, tmpRptSumData[2]);
						subTotalStandPrice = FDCHelper.add(subTotalStandPrice, tmpRptSumData[3]);
					}
					
					//添加已售房间相关信息
					Object[] tmpRptSelledData = (Object[])selledRoomData.get(tmpKeyValue);
					if(tmpRptSelledData != null && tmpRptSelledData.length > 0)
					{
						tmpRow.getCell(tbl_haveSellCount).setValue(tmpRptSelledData[0]);         //总套数
						tmpRow.getCell(tbl_haveSellArea).setValue(tmpRptSelledData[1]);          //总面积
						tmpRow.getCell(tbl_haveSellStandAvePrice).setValue(FDCHelper.divide(tmpRptSelledData[2], tmpRptSelledData[1])); //标准均价
						tmpRow.getCell(tbl_haveSellStandTotPrice).setValue(tmpRptSelledData[2]); //标准总价
						if(tmpRptSelledData[3]==null || tmpRptSelledData[1] ==null)
						{
							tmpRow.getCell(tbl_haveSellBaseAvePrice).setValue(null);										
						}else
						{
							tmpRow.getCell(tbl_haveSellBaseAvePrice).setValue(
								FDCHelper.divide(tmpRptSelledData[5], tmpRptSelledData[1]));     //低价均价
						}
						
						tmpRow.getCell(tbl_haveSellBaseTotPrice).setValue(tmpRptSelledData[5]);  //低价总价
						if(tmpRptSelledData[4]==null || tmpRptSelledData[1]==null)
						{
							tmpRow.getCell(tbl_haveSellDealAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_haveSellDealAvePrice).setValue(
								FDCHelper.divide(tmpRptSelledData[4], tmpRptSelledData[1]));     //成交均价
						}
						
						tmpRow.getCell(tbl_haveSellDealTotPrice).setValue(tmpRptSelledData[4]);  //成交总价
						if(FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]).compareTo(new BigDecimal(0))==0 ||tmpRptSelledData[1]==null)
						{
							tmpRow.getCell(tbl_haveSellPrefAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_haveSellPrefAvePrice).setValue(
									FDCHelper.divide(FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]), 
											tmpRptSelledData[1])); //优惠均价
						}
						if(FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]).compareTo(new BigDecimal(0))==0)
						{
							tmpRow.getCell(tbl_haveSellPrefTotPrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_haveSellPrefTotPrice).setValue(
									FDCHelper.subtract(tmpRptSelledData[2], tmpRptSelledData[4]));   //总优惠额
						}
						
						
						
						//小计行显示部分
						selledRoomCount = new Integer(FDCHelper.add(selledRoomCount, tmpRptSelledData[0]).intValue());
						selledTotalArea = FDCHelper.add(selledTotalArea, tmpRptSelledData[1]);
						selledStandTotalPrice = FDCHelper.add(selledStandTotalPrice, tmpRptSelledData[2]);
						selledBaseTotalPrice = FDCHelper.add(selledBaseTotalPrice, tmpRptSelledData[5]);
						selledDealTotalPrice = FDCHelper.add(selledDealTotalPrice, tmpRptSelledData[4]);
						
						//填充销售率
						tmpRow.getCell(tbl_saleRate).setValue(FDCHelper.multiply(FDCHelper.toBigDecimal(100), 
								FDCHelper.divide(tmpRptSelledData[0], tmpRow.getCell(tbl_totalCount).getValue(), 4, BigDecimal.ROUND_HALF_UP)));
					}
					
					//添加未售房间相关信息
					Object[] tmpRptunselledData = (Object[])unSelledRoomData.get(tmpKeyValue);
					if(tmpRptunselledData != null && tmpRptunselledData.length > 0)
					{
						tmpRow.getCell(tbl_unSellCount).setValue(tmpRptunselledData[0]);  //总套数
						tmpRow.getCell(tbl_unSellArea).setValue(tmpRptunselledData[1]);   //总面积
						if(tmpRptunselledData[2]==null || tmpRptunselledData[1]==null)
						{
							tmpRow.getCell(tbl_unSellStandAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_unSellStandAvePrice).setValue(FDCHelper.divide(tmpRptunselledData[2], tmpRptunselledData[1]));
						}					
						tmpRow.getCell(tbl_unSellStandTotPrice).setValue(tmpRptunselledData[2]); //标准总价
						
						//小计行显示部分
						unselledRoomCount = new Integer(FDCHelper.add(unselledRoomCount, tmpRptunselledData[0]).intValue());
						unselledTotalArea = FDCHelper.add(unselledTotalArea, tmpRptunselledData[1]);
						unselledStandTotalPrice = FDCHelper.add(unselledStandTotalPrice, tmpRptunselledData[2]);
					}
					
					//添加累计回款数据
					BigDecimal tmpSumGatheringAmt = (BigDecimal)sumGatheringData.get(tmpKeyValue);
					if(tmpSumGatheringAmt != null)
					{
						tmpRow.getCell(tbl_sumGathering).setValue(tmpSumGatheringAmt); //累计回款
						
						//小计行显示部分
						sumGatheringAmt = FDCHelper.add(sumGatheringAmt, tmpSumGatheringAmt);
					}
					
					//添加补差金额
					BigDecimal tmpSumSubAmt = (BigDecimal)subAmtRoomData.get(tmpKeyValue);
					if(tmpSumSubAmt != null)
					{
						tmpRow.getCell(tbl_RoomCompenstate).setValue(tmpSumSubAmt);
						
						//小计行显示部分
						sumRoomCompAmt = FDCHelper.add(sumRoomCompAmt, tmpSumSubAmt);
					}
					
					//查询区间数据填充（不含累计回款）
					Object[] tmpPeriodRptData = new Object[9];
					if(periodNoGatheringData!=null)
					{
						tmpPeriodRptData = (Object[])periodNoGatheringData.get(tmpKeyValue);
					}
					if(tmpPeriodRptData != null && tmpPeriodRptData.length > 0)
					{
						tmpRow.getCell(tbl_querySellCount).setValue(tmpPeriodRptData[0]);//房间套数
						tmpRow.getCell(tbl_querySellArea).setValue(tmpPeriodRptData[1]);//销售面积
//						if(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2])==null || FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2]).compareTo(new BigDecimal(0))==0 || tmpPeriodRptData[1]==null)
//						{
//							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(null);
//						}else
//						{
//							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(
//									FDCHelper.divide(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2]), tmpPeriodRptData[1])); //优惠均价
//						}
						tmpRow.getCell(tbl_queryPrefTotPrice).setValue(tmpPeriodRptData[8]);//优惠总价
						if(tmpPeriodRptData[8]==null || tmpPeriodRptData[1]==null)
						{
							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_queryPrefAvePrice).setValue(FDCHelper.divide(tmpPeriodRptData[8], tmpPeriodRptData[1]));   //优惠均价
						}
//						if(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2])==null ||FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2]).compareTo(new BigDecimal(0))==0)
//						{
//							tmpRow.getCell(tbl_queryPrefTotPrice).setValue(null);
//						}else
//						{
//							tmpRow.getCell(tbl_queryPrefTotPrice).setValue(FDCHelper.subtract(tmpPeriodRptData[3], tmpPeriodRptData[2])); //优惠总价
//						}
						
						if(tmpPeriodRptData[2]==null || tmpPeriodRptData[1]==null)
						{
							tmpRow.getCell(tbl_queryDealAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(tmpPeriodRptData[2], tmpPeriodRptData[1]));   //成交均价
						}						
						tmpRow.getCell(tbl_queryDealTotPrice).setValue(tmpPeriodRptData[2]);//成交总价
						if(tmpPeriodRptData[3]==null || tmpPeriodRptData[1] ==null)
						{
							tmpRow.getCell(tbl_queryStdAvePrice).setValue(null);
						}else
						{
							tmpRow.getCell(tbl_queryStdAvePrice).setValue(FDCHelper.divide(tmpPeriodRptData[3], tmpPeriodRptData[1]));   //标准均价
						}						
						tmpRow.getCell(tbl_queryStdTotPrice).setValue(tmpPeriodRptData[3]);//标准总价
						tmpRow.getCell(tbl_querySellSumPrice).setValue(tmpPeriodRptData[6]);//销售总价
						tmpRow.getCell(tbl_queryPurSumPrice).setValue(tmpPeriodRptData[4]);//已售合同总价
						tmpRow.getCell(tbl_querySignSumPrice).setValue(tmpPeriodRptData[7]);//已签约合同总价
						
						//小计行显示部分
						periodSelledRoomCount =new Integer(FDCHelper.add(periodSelledRoomCount, tmpPeriodRptData[0]).intValue());
						periodSelledArea = FDCHelper.add(periodSelledArea, tmpPeriodRptData[1]);
						periYouHuiAmt = FDCHelper.add(periYouHuiAmt, tmpPeriodRptData[8]);
						periodDealTotPrice = FDCHelper.add(periodDealTotPrice, tmpPeriodRptData[2]);
						periodStandTotPrice = FDCHelper.add(periodStandTotPrice, tmpPeriodRptData[3]);
						periodSelledTotAmt = FDCHelper.add(periodSelledTotAmt, tmpPeriodRptData[6]);
						periodPuchaseAmt = FDCHelper.add(periodPuchaseAmt, tmpPeriodRptData[4]);
						periodSignSumPrice = FDCHelper.add(periodSignSumPrice, tmpPeriodRptData[7]);
					}
					
					//查询区间数据填充-累计回款
					BigDecimal tmpPeriodSumGatheringAmt = null;
					if(periodGatheringData!=null)
					{
						tmpPeriodSumGatheringAmt = (BigDecimal)periodGatheringData.get(tmpKeyValue);
					}
					if(tmpPeriodSumGatheringAmt != null)
					{
						tmpRow.getCell(tbl_querySumGathering).setValue(tmpPeriodSumGatheringAmt);
						
						//小计行显示部分
						periodSumGatheringAmt = FDCHelper.add(periodSumGatheringAmt, tmpPeriodSumGatheringAmt);
					}
					//tmpRow.setTreeLevel(1);
				}
				
				//添加小计行
				IRow tmpSubSum = tblMain.addRow();
				//tmpSubSum.setTreeLevel(2);
				tmpSubSum.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
				tmpSubSum.getStyleAttributes().setFontColor(Color.RED);
				tmpSubSum.getCell(tbl_productName).setValue(tmpPrdType.getName());
				tmpSubSum.getCell(tbl_roomType).setValue("小计");
				tmpSubSum.getCell(tbl_key).setValue("SubSum");
				
				//总数据
				tmpSubSum.getCell(tbl_totalCount).setValue(subRoomCount);   //小计总套数
				tmpSubSum.getCell(tbl_totalArea).setValue(subTotalArea);    //小计总面积是
				tmpSubSum.getCell(tbl_totalStandPrice).setValue(subTotalStandPrice); //小计总金额

				if(subTotalStandPrice.compareTo(new BigDecimal(0))==0 || subTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_totalAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_totalAvePrice).setValue(subTotalStandPrice.divide(subTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //小计总均价
				}
				
				tmpSubSum.getCell(tbl_saleRate).setValue(FDCHelper.multiply(FDCHelper.toBigDecimal(100), 
						FDCHelper.divide(selledRoomCount, subRoomCount, 4, BigDecimal.ROUND_HALF_UP)));            //销售率
				
				//已售房间数据
				tmpSubSum.getCell(tbl_haveSellCount).setValue(selledRoomCount);
				tmpSubSum.getCell(tbl_haveSellArea).setValue(selledTotalArea);
				
				if(selledStandTotalPrice.compareTo(new BigDecimal(0))==0 || selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellStandAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellStandAvePrice).setValue(selledStandTotalPrice.divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //小计总均价
				}
//				tmpSubSum.getCell(tbl_haveSellStandAvePrice).setValue(
//						FDCHelper.divide(selledStandTotalPrice, selledTotalArea));
				tmpSubSum.getCell(tbl_haveSellStandTotPrice).setValue(selledStandTotalPrice);
				
				if(selledBaseTotalPrice.compareTo(new BigDecimal(0))==0 || selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellBaseAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellBaseAvePrice).setValue(selledBaseTotalPrice.divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //小计总均价
				}
//				tmpSubSum.getCell(tbl_haveSellBaseAvePrice).setValue(
//						FDCHelper.divide(selledBaseTotalPrice, selledTotalArea));
				tmpSubSum.getCell(tbl_haveSellBaseTotPrice).setValue(selledBaseTotalPrice);
				
				if(selledDealTotalPrice.compareTo(new BigDecimal(0))==0 || selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellDealAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellDealAvePrice).setValue(selledDealTotalPrice.divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //小计总均价
				}
//				tmpSubSum.getCell(tbl_haveSellDealAvePrice).setValue(
//						FDCHelper.divide(selledDealTotalPrice, selledTotalArea));
				tmpSubSum.getCell(tbl_haveSellDealTotPrice).setValue(selledDealTotalPrice);
				
				tmpSubSum.getCell(tbl_haveSellPrefTotPrice).setValue(FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice));
				if(FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice)==null || FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice).compareTo(new BigDecimal(0))==0
						||selledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_haveSellPrefAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_haveSellPrefAvePrice).setValue(
							 FDCHelper.subtract(selledStandTotalPrice, selledDealTotalPrice).divide(selledTotalArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				
				
				//未售房间数据
				tmpSubSum.getCell(tbl_unSellCount).setValue(unselledRoomCount);
				tmpSubSum.getCell(tbl_unSellArea).setValue(unselledTotalArea);
				if(unselledStandTotalPrice.compareTo(new BigDecimal(0))==0 || unselledTotalArea.compareTo(new BigDecimal(0))==0)
				{
					tmpSubSum.getCell(tbl_unSellStandAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_unSellStandAvePrice).setValue(unselledStandTotalPrice.divide(unselledTotalArea, 2, BigDecimal.ROUND_HALF_UP)); //小计总均价
				}
//				tmpSubSum.getCell(tbl_unSellStandAvePrice).setValue(FDCHelper.divide(unselledStandTotalPrice, unselledTotalArea));
				tmpSubSum.getCell(tbl_unSellStandTotPrice).setValue(unselledStandTotalPrice);
				
				//累计回款
				tmpSubSum.getCell(tbl_sumGathering).setValue(sumGatheringAmt);
				//补差金额
				tmpSubSum.getCell(tbl_RoomCompenstate).setValue(sumRoomCompAmt);
				
				//区间查询数据小计
				tmpSubSum.getCell(tbl_querySumGathering).setValue(periodSumGatheringAmt);
				tmpSubSum.getCell(tbl_querySellCount).setValue(periodSelledRoomCount);
				tmpSubSum.getCell(tbl_querySellArea).setValue(periodSelledArea);
//				if(FDCHelper.divide(FDCHelper.subtract(periodStandTotPrice, periodDealTotPrice), periodSelledArea)==null)
//				{
//					tmpSubSum.getCell(tbl_queryPrefAvePrice).setValue(new BigDecimal(0));
//				}else
//				{
//					tmpSubSum.getCell(tbl_queryPrefAvePrice).setValue(
//							FDCHelper.divide(FDCHelper.subtract(periodStandTotPrice, periodDealTotPrice), periodSelledArea));
//				}
				
//				tmpSubSum.getCell(tbl_queryPrefTotPrice).setValue(FDCHelper.subtract(periodStandTotPrice, periodDealTotPrice));
				tmpSubSum.getCell(tbl_queryPrefTotPrice).setValue(periYouHuiAmt);
				if(FDCHelper.divide(periYouHuiAmt, periodSelledArea)==null)
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(periYouHuiAmt, periodSelledArea));
				}
				if(FDCHelper.divide(periodDealTotPrice, periodSelledArea)==null)
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(periodDealTotPrice, periodSelledArea));
				}
				
				tmpSubSum.getCell(tbl_queryDealTotPrice).setValue(periodDealTotPrice);
				if(FDCHelper.divide(periodStandTotPrice, periodSelledArea)==null)
				{
					tmpSubSum.getCell(tbl_queryStdAvePrice).setValue(new BigDecimal(0));
				}else
				{
					tmpSubSum.getCell(tbl_queryStdAvePrice).setValue(FDCHelper.divide(periodStandTotPrice, periodSelledArea));
				}				
				tmpSubSum.getCell(tbl_queryStdTotPrice).setValue(periodStandTotPrice);
				tmpSubSum.getCell(tbl_querySellSumPrice).setValue(periodSelledTotAmt);
				tmpSubSum.getCell(tbl_queryPurSumPrice).setValue(periodPuchaseAmt);
				tmpSubSum.getCell(tbl_querySignSumPrice).setValue(periodSignSumPrice);
				
				mergeEnd = mergeStart + roomModels.size();
				
				this.tblMain.getMergeManager().mergeBlock(mergeStart,1,mergeEnd,1);
				mergeStart = mergeEnd+1;
			}
		}
	}
	
	protected Map getRptParamMap() throws Exception
	{
		//获取所选的节点
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node == null || node.getUserObject() == null)
		{
			node = new DefaultKingdeeTreeNode();
			node.setUserObject(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		}
		else if(node.getUserObject() instanceof OrgStructureInfo)
		{
			OrgStructureInfo orgStrInfo = (OrgStructureInfo)node.getUserObject();
			node.setUserObject(orgStrInfo.getUnit());
		}
		
		//根据所选的节点获取相关的数据
		 /**
	     * paramMap
	     * 描述：
	     * selectNode        = 所选树的节点，如果没有选，则设为当前组织
	     * IncludeAttachment = 包括附属房产，为真是包括，为假时不包括
	     * BuildArea         = 表示面积类型，为真是建筑面积，为假时为套内面积
	     * PreArea           = 表示面积类型，为真是预测面积，为假时为实测面积
	     * BeginDate         = 开始日期
	     * EndDate           = 结束日期
	     * IncludeOrder      = 包括预订业务， 为真是包括，为假时不包括
	     * IsAuditDate       = 已那个时间为标准（为真时以审批日期为标准，为假时已对应的日期为标准）
	     */
		Map paramMap = new HashMap();
		paramMap.put("selectNode", node);
		
		paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttachment()));
		paramMap.put("BuildArea", Boolean.valueOf(isBuildArea()));
		paramMap.put("PreArea", Boolean.valueOf(isPreArea()));
		paramMap.put("BeginDate", getBeginQueryDate());
		paramMap.put("EndDate", getEndQueryDate());
		paramMap.put("IncludeOrder", Boolean.valueOf(isIncludePreOrder()));
		paramMap.put("IsAuditDate", Boolean.valueOf(this.isAuditDate));
		paramMap.put("userInfo",SysContext.getSysContext().getCurrentUserInfo());
		return paramMap;
	}

	protected void checkTableParsed()
	{
		tblMain.checkParsed();
	}

	public void onLoad() throws Exception
	{
		this.tblMain.checkParsed();
		initTable();
		
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		
		isFirstLoad = false;
		
		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(false);		
		this.chkShowEtyLine.setVisible(false);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionLocate.setVisible(false);
	}
	
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	
	
	protected void initTableHead() throws Exception
	{
		for(int colIndex = tblMain.getColumnCount()-1; colIndex > 23; --colIndex)
		{
			tblMain.removeColumn(colIndex);
		}
		
		//添加动态列
		Calendar cal = new GregorianCalendar();
		cal.setTime(getEndQueryDate());
		cal.set(Calendar.DATE, cal.get(Calendar.DATE));
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		String des = "查询区间数据(" + FORMAT_DAY.format(getBeginQueryDate()) + "到"
				+ FORMAT_DAY.format(cal.getTime()) + ")";
		
		addDynamicCol(tblMain, tbl_querySellCount, des, "销售套数");
		addDynamicCol(tblMain, tbl_querySellArea, des, "销售面积");
		addDynamicCol(tblMain, tbl_queryPrefAvePrice, des, "优惠均价");
		addDynamicCol(tblMain, tbl_queryPrefTotPrice, des, "优惠额");
		
		addDynamicCol(tblMain, tbl_queryDealAvePrice, des, "成交均价");
		addDynamicCol(tblMain, tbl_queryDealTotPrice, des, "成交总价");
		addDynamicCol(tblMain, tbl_queryStdAvePrice, des, "标准均价");
		addDynamicCol(tblMain, tbl_queryStdTotPrice, des, "标准总价");
		
		addDynamicCol(tblMain, tbl_querySumGathering, des, "累计回款");
		addDynamicCol(tblMain, tbl_querySellSumPrice, des, "销售总价");
		addDynamicCol(tblMain, tbl_queryPurSumPrice, des, "已售合同总价");
		addDynamicCol(tblMain, tbl_querySignSumPrice, des, "签约合同总价");
		
		tblMain.checkParsed();
		//设置表头融合
		this.tblMain.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		this.tblMain.getHeadMergeManager().mergeBlock(0, 24, 0, 35);
		
		tblMain.getColumn(tbl_productName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		tblMain.getColumn(tbl_roomType).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		FDCHelper.formatTableNumber(tblMain, tbl_totalArea);
		FDCHelper.formatTableNumber(tblMain, tbl_totalStandPrice);
		FDCHelper.formatTableNumber(tblMain, tbl_totalAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_saleRate);
		
		//已售列格式设置
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellArea);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellBaseAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellBaseTotPrice);
		
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellDealAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellDealTotPrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellPrefAvePrice);
		
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellPrefTotPrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellStandAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_haveSellStandTotPrice);
		
		//未售列格式设置
		FDCHelper.formatTableNumber(tblMain, tbl_unSellArea);
		FDCHelper.formatTableNumber(tblMain, tbl_unSellStandAvePrice);
		FDCHelper.formatTableNumber(tblMain, tbl_unSellStandTotPrice);
		
		//累计回款和补差金额设置
		FDCHelper.formatTableNumber(tblMain, tbl_sumGathering);
		FDCHelper.formatTableNumber(tblMain, tbl_RoomCompenstate);
		tblMain.getColumn(tbl_querySellCount).getStyleAttributes().setNumberFormat("0");
	}
	
	protected IColumn addDynamicCol(KDTable table, String colKey, String head0Value, String head1Value)
	{
		IColumn col = table.addColumn();
		col.setKey(colKey);
		
		table.getHeadRow(0).getCell(colKey).setValue(head0Value);
		table.getHeadRow(1).getCell(colKey).setValue(head1Value);
		
		FDCHelper.formatTableNumber(table, colKey);
		return col;
	}
	protected void initTableBody()
	{
		
	}
	protected void initTable() throws Exception
	{
//		initTableHead();
	}
	

	protected String getKeyFieldName()
	{
		return "productName";
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		if(!isFirstLoad)
			this.execQuery();
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
	}
	
	protected void refresh(ActionEvent e) throws Exception
	{
		this.execQuery();
	}
	
	protected boolean initDefaultFilter()
	{
		return true;
	}
	
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
	
	/**
	 * 产品类型销售统计合计
	 * FDCTableHelper.periodTotalColor)
	 * FDCTableHelper.daySubTotalColor);
	 */
	private void upDateTotal()
	{
		KDTFootManager footRowManager= tblMain.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			String total="合计";
			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			tblMain.getIndexColumn().setWidth(50);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//设置到第一个可视行
			footRowManager.addIndexText(0, total);
		}else{
			footRow=tblMain.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=tblMain.addFootRow(1);
			};
		}
		
		//填充汇总行数据
		BigDecimal footTotalCount = FDCHelper.ZERO;
		BigDecimal footTotalArea = FDCHelper.ZERO;
		BigDecimal footTotalStandPrice = FDCHelper.ZERO;
		
		BigDecimal footSelledCount = FDCHelper.ZERO;
		BigDecimal footSelledArea = FDCHelper.ZERO;
		BigDecimal footSelledStandAmt = FDCHelper.ZERO;
		
		BigDecimal footSelledBaseTotalPrice = FDCHelper.ZERO;
		BigDecimal footSelledDealTotalPrice = FDCHelper.ZERO;
		
		BigDecimal footUnsellCount = FDCHelper.ZERO;
		BigDecimal footUnsellArea = FDCHelper.ZERO;
		BigDecimal footUnsellStandPrice = FDCHelper.ZERO;
		
		BigDecimal footSumGathering = FDCHelper.ZERO;
		BigDecimal footRoomComp = FDCHelper.ZERO;
		
		BigDecimal footPeriodRoomCount = FDCHelper.ZERO;
		BigDecimal footPeriodSellArea = FDCHelper.ZERO;
		BigDecimal footPeriodDealTotAmt = FDCHelper.ZERO;
		BigDecimal footPeriodStandTotAmt = FDCHelper.ZERO;
		
		BigDecimal footPeriodSumGathering = FDCHelper.ZERO;
		BigDecimal footPeriodSellTotAmt = FDCHelper.ZERO;
		BigDecimal footPeriodPurTotAmt = FDCHelper.ZERO;
		BigDecimal footPeriodSignTotAmt = FDCHelper.ZERO;
		
		for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)
		{
			IRow tmpRow = tblMain.getRow(rowIndex);
			if(tmpRow.getCell(tbl_key).getValue() != null &&
					"SubSum".equals(tmpRow.getCell(tbl_key).getValue().toString()))
			{
				footTotalCount = FDCHelper.add(footTotalCount, tmpRow.getCell(tbl_totalCount).getValue());
				footTotalArea = FDCHelper.add(footTotalArea, tmpRow.getCell(tbl_totalArea).getValue());
				footTotalStandPrice = FDCHelper.add(footTotalStandPrice, tmpRow.getCell(tbl_totalStandPrice).getValue());
				
				footSelledCount = FDCHelper.add(footSelledCount, tmpRow.getCell(tbl_haveSellCount).getValue());
				footSelledArea = FDCHelper.add(footSelledArea, tmpRow.getCell(tbl_haveSellArea).getValue());
				footSelledStandAmt = FDCHelper.add(footSelledStandAmt, tmpRow.getCell(tbl_haveSellStandTotPrice).getValue());
				footSelledBaseTotalPrice = FDCHelper.add(footSelledBaseTotalPrice, tmpRow.getCell(tbl_haveSellBaseTotPrice).getValue());
				footSelledDealTotalPrice = FDCHelper.add(footSelledDealTotalPrice, tmpRow.getCell(tbl_haveSellDealTotPrice).getValue());
				
				footUnsellCount = FDCHelper.add(footUnsellCount, tmpRow.getCell(tbl_unSellCount).getValue());
				footUnsellArea = FDCHelper.add(footUnsellArea, tmpRow.getCell(tbl_unSellArea).getValue());
				footUnsellStandPrice = FDCHelper.add(footUnsellStandPrice, tmpRow.getCell(tbl_unSellStandTotPrice).getValue());
				
				footSumGathering = FDCHelper.add(footSumGathering, tmpRow.getCell(tbl_sumGathering).getValue());
				footRoomComp = FDCHelper.add(footRoomComp, tmpRow.getCell(tbl_RoomCompenstate).getValue());
				
				footPeriodRoomCount = FDCHelper.add(footPeriodRoomCount, tmpRow.getCell(tbl_querySellCount).getValue());
				footPeriodSellArea = FDCHelper.add(footPeriodSellArea, tmpRow.getCell(tbl_querySellArea).getValue());
				footPeriodDealTotAmt = FDCHelper.add(footPeriodDealTotAmt, tmpRow.getCell(tbl_queryDealTotPrice).getValue());
				footPeriodStandTotAmt = FDCHelper.add(footPeriodStandTotAmt, tmpRow.getCell(tbl_queryStdTotPrice).getValue());
				
				footPeriodSumGathering = FDCHelper.add(footPeriodSumGathering, tmpRow.getCell(tbl_querySumGathering).getValue());
				footPeriodSellTotAmt = FDCHelper.add(footPeriodSellTotAmt, tmpRow.getCell(tbl_querySellSumPrice).getValue());
				footPeriodPurTotAmt = FDCHelper.add(footPeriodPurTotAmt, tmpRow.getCell(tbl_queryPurSumPrice).getValue());
				footPeriodSignTotAmt = FDCHelper.add(footPeriodSignTotAmt, tmpRow.getCell(tbl_querySignSumPrice).getValue());
			}
		}
		
		footRow.getCell(tbl_totalCount).setValue(new Integer(footTotalCount.intValue()));
		footRow.getCell(tbl_totalArea).setValue(footTotalArea);
		footRow.getCell(tbl_totalStandPrice).setValue(footTotalStandPrice);
		footRow.getCell(tbl_totalAvePrice).setValue(FDCHelper.divide(footTotalStandPrice, footTotalArea));
		
		footRow.getCell(tbl_haveSellCount).setValue(new Integer(footSelledCount.intValue()));
		footRow.getCell(tbl_saleRate).setValue(FDCHelper.multiply(new BigDecimal("100"), 
				FDCHelper.divide(footSelledCount, footTotalCount, 4, BigDecimal.ROUND_HALF_UP)));
		footRow.getCell(tbl_haveSellArea).setValue(footSelledArea);
		if(footSelledStandAmt==null || footSelledStandAmt.compareTo(new BigDecimal(0))==0 
				|| footSelledArea==null || footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellStandAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellStandAvePrice).setValue(footSelledStandAmt.divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		footRow.getCell(tbl_haveSellStandTotPrice).setValue(footSelledStandAmt);
		//均价小计和合计为空显示为0,
		if(footSelledBaseTotalPrice==null || footSelledBaseTotalPrice.compareTo(new BigDecimal(0))==0 
				|| footSelledArea==null || footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellBaseAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellBaseAvePrice).setValue(footSelledBaseTotalPrice.divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		
		footRow.getCell(tbl_haveSellBaseTotPrice).setValue(footSelledBaseTotalPrice);
		if(footSelledDealTotalPrice==null || footSelledDealTotalPrice.compareTo(new BigDecimal(0))==0 
				|| footSelledArea==null || footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellDealAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellDealAvePrice).setValue(footSelledDealTotalPrice.divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		footRow.getCell(tbl_haveSellDealTotPrice).setValue(footSelledDealTotalPrice);
		
		footRow.getCell(tbl_haveSellPrefTotPrice).setValue(FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice));
		if(FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice)==null || FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice).compareTo(new BigDecimal(0))==0
				|| footSelledArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_haveSellPrefAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_haveSellPrefAvePrice).setValue(FDCHelper.subtract(footSelledStandAmt, footSelledDealTotalPrice).divide(footSelledArea, 2, BigDecimal.ROUND_HALF_UP));
		}				
		footRow.getCell(tbl_unSellCount).setValue(new Integer(footUnsellCount.intValue()));
		footRow.getCell(tbl_unSellArea).setValue(footUnsellArea);
		if(footUnsellStandPrice==null || footUnsellStandPrice.compareTo(new BigDecimal(0))==0 
				|| footUnsellArea==null || footUnsellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_unSellStandAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_unSellStandAvePrice).setValue(footUnsellStandPrice.divide(footUnsellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
//		footRow.getCell(tbl_unSellStandAvePrice).setValue(FDCHelper.divide(footUnsellStandPrice, footUnsellArea));
		footRow.getCell(tbl_unSellStandTotPrice).setValue(footUnsellStandPrice);
		
		footRow.getCell(tbl_sumGathering).setValue(footSumGathering);
		footRow.getCell(tbl_RoomCompenstate).setValue(footRoomComp);
		
		footRow.getCell(tbl_querySellCount).setValue(new Integer(footPeriodRoomCount.intValue()));
		footRow.getCell(tbl_querySellArea).setValue(footPeriodSellArea);
		if(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt)==null || FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt).compareTo(new BigDecimal(0))==0
				|| footPeriodSellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_queryPrefAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_queryPrefAvePrice).setValue(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt).divide(footPeriodSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}	
//		footRow.getCell(tbl_queryPrefAvePrice).setValue(FDCHelper.divide(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt), footPeriodSellArea));
		footRow.getCell(tbl_queryPrefTotPrice).setValue(FDCHelper.subtract(footPeriodStandTotAmt, footPeriodDealTotAmt));
		
		if(footPeriodDealTotAmt==null || footPeriodDealTotAmt.compareTo(new BigDecimal(0))==0 
				|| footPeriodSellArea==null || footPeriodSellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_queryDealAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_queryDealAvePrice).setValue(footPeriodDealTotAmt.divide(footPeriodSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
//		footRow.getCell(tbl_queryDealAvePrice).setValue(FDCHelper.divide(footPeriodDealTotAmt, footPeriodSellArea));
		footRow.getCell(tbl_queryDealTotPrice).setValue(footPeriodDealTotAmt);
		if(footPeriodStandTotAmt==null || footPeriodStandTotAmt.compareTo(new BigDecimal(0))==0 
				|| footPeriodSellArea==null || footPeriodSellArea.compareTo(new BigDecimal(0))==0)
		{
			footRow.getCell(tbl_queryStdAvePrice).setValue(new BigDecimal(0));
		}else
		{
			footRow.getCell(tbl_queryStdAvePrice).setValue(footPeriodStandTotAmt.divide(footPeriodSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
//		footRow.getCell(tbl_queryStdAvePrice).setValue(FDCHelper.divide(footPeriodStandTotAmt, footPeriodSellArea));
		footRow.getCell(tbl_queryStdTotPrice).setValue(footPeriodStandTotAmt);
		
		footRow.getCell(tbl_querySumGathering).setValue(footPeriodSumGathering);
		footRow.getCell(tbl_querySellSumPrice).setValue(footPeriodSellTotAmt);
		footRow.getCell(tbl_queryPurSumPrice).setValue(footPeriodPurTotAmt);
		footRow.getCell(tbl_querySignSumPrice).setValue(footPeriodSignTotAmt);
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//获取过滤界面的参数
	/**
	 * 结束日期
	 * @return
	 * @throws Exception
	 */
	private Date getEndQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI) this.getFilterUI()).getEndQueryDate(para);
	}
	/**
	 * 开始日期
	 * @return
	 * @throws Exception
	 */
	private Date getBeginQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI) this.getFilterUI()).getBeginQueryDate(para);
	}
	
	private boolean isIncludeAttachment() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI) this.getFilterUI()).isIncludeAttach(param);
	}
	private boolean isIncludePreOrder() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI)this.getFilterUI()).isPreIntoSale(param);
	}
	
	/**
	 * 如果为真则为建筑面积，如果为假则为套内面积
	 * @return
	 * @throws Exception
	 */
	private boolean isBuildArea() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI)this.getFilterUI()).isBuildArea(param);
	}
	
	/**
	 * 如果为真则为预测面积，如果为假则为实测面积
	 * @return
	 * @throws Exception
	 */
	private boolean isPreArea() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((ProductTypeSellStatFilterUI)this.getFilterUI()).isPreArea(param);
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		//super.tblMain_tableSelectChanged(e);
	}
	
	protected void chkShowEtyLine_actionPerformed(ActionEvent e)
	throws Exception {

		for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)
		{
			IRow tmpRow = tblMain.getRow(rowIndex);
			if(tmpRow.getCell(tbl_key).getValue() != null && checkIsEmptyLine(tmpRow))
			{
				tmpRow.getStyleAttributes().setHided(chkShowEtyLine.isSelected());
			}
		}
	}
	
	private boolean checkIsEmptyLine(IRow paramRow)
	{
		boolean empty = true;
		
		for(int colIndex = 2; colIndex < tblMain.getColumnCount(); ++colIndex)
		{
			if(paramRow.getCell(colIndex).getValue() != null)
			{
				empty = false;
				break;
			}
		}
		
		return empty;
	}
}