/**
 * 租赁欠款查询
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class ArrearageListUI extends AbstractArrearageListUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(ArrearageListUI.class);

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getColumn("apAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("apAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("actPayAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actPayAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("noPayAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("noPayAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("arrearageDayCount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("arrearageDayCount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
	}

	public void onLoad() throws Exception
	{
		this.actionQuery.setVisible(false);
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		
		this.tblMain.getColumn("moneyName").getStyleAttributes().setLocked(false);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{
		fillData();
	}

	private void fillData() throws BOSException
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}
		FilterInfo roomFilter = new FilterInfo();
		if (node.getUserObject() instanceof Integer)    //已作废
		{
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			roomFilter.getFilterItems().add(new FilterItemInfo("unit", unit));
		}else if (node.getUserObject() instanceof BuildingUnitInfo)    	{
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			roomFilter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
		} else if (node.getUserObject() instanceof BuildingInfo){
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				String buildingId = building.getId().toString();
				roomFilter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
			} else
			{
				roomFilter.getFilterItems().add(new FilterItemInfo("id", null));
			}
		} else
		{
			roomFilter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(roomFilter);
		view.getSelector().add("id");
		RoomCollection rooms = RoomFactory.getRemoteInstance()
				.getRoomCollection(view);
		Set idSet = new HashSet();
		for (int i = 0; i < rooms.size(); i++)
		{
			idSet.add(rooms.get(i).getId().toString());
		}
		view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("room.number"));
		view.getSelector().add("*");
		view.getSelector().add("room.*");
		view.getSelector().add("customerInfo.*");
		view.getSelector().add("customerInfo.customer.*");
		view.getSelector().add("payListEntry.*");
		view.getSelector().add("payListEntry.moneyDefine.*");
		view.getSelector().add("salesman.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (idSet.size() == 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("room.id", null));
		} else
		{
			filter.getFilterItems().add(
					new FilterItemInfo("room.id", idSet, CompareType.INCLUDE));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.ChangeRoomBlankOut,
						CompareType.NOTEQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("purchaseState",
								PurchaseStateEnum.ManualBlankOut,
								CompareType.NOTEQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("purchaseState",
								PurchaseStateEnum.NoPayBlankOut,
								CompareType.NOTEQUALS));
		PurchaseCollection purchases = PurchaseFactory.getRemoteInstance()
				.getPurchaseCollection(view);
		this.tblMain.removeRows();

		// 合计应付金额
		BigDecimal totalShouldPay = FDCHelper.ZERO;
		// 合计未付金额
		BigDecimal totalNotPay = FDCHelper.ZERO;
		// 实付金额
		BigDecimal totalActPay = FDCHelper.ZERO;

		// 合计应付定金金额
		BigDecimal dingjinTotalShouldPay = FDCHelper.ZERO;
		// 合计未付定金金额
		BigDecimal dingjinTotalNotPay = FDCHelper.ZERO;
		// 合计实付定金金额
		BigDecimal dingjinTotalActPay = FDCHelper.ZERO;
		// 合计应付首付金额
		BigDecimal shoufuTotalShouldPay = FDCHelper.ZERO;

		// 合计未付首付金额
		BigDecimal shoufuTotalNotPay = FDCHelper.ZERO;
		// 合计实付首付金额
		BigDecimal shoufuTotalActPay = FDCHelper.ZERO;

		// 合计应付贷款金额
		BigDecimal daikuanTotalShouldPay = FDCHelper.ZERO;
		// 合计未付贷款金额
		BigDecimal daikuanTotalNotPay = FDCHelper.ZERO;
		// 合计实付贷款金额
		BigDecimal daikuanTotalActPay = FDCHelper.ZERO;

		// 合计公积金
		BigDecimal gongjijinShouldPay = FDCHelper.ZERO;
		BigDecimal gongjijinNotPay = FDCHelper.ZERO;
		BigDecimal gongjijinActPay = FDCHelper.ZERO;

		// 合计补差款
		BigDecimal buchaShouldPay = FDCHelper.ZERO;
		BigDecimal buchaNotPay = FDCHelper.ZERO;
		BigDecimal buchaActPay = FDCHelper.ZERO;

		// 控制是否显示出来
		boolean gongjijinDebug = false;
		boolean buchaDebug = false;

		for (int i = 0; i < purchases.size(); i++)
		{
			PurchaseInfo purchase = purchases.get(i);
			if (purchase.getRoom() == null)
			{
				continue;
			}
			boolean isHaveAreeageData = false;
			PurchasePayListEntryCollection entrys = purchase.getPayListEntry();
			for (int j = 0; j < entrys.size(); j++)
			{
				PurchasePayListEntryInfo info = entrys.get(j);
				BigDecimal apAmount = info.getApAmount();
				BigDecimal actPayAmount = info.getActPayAmount();
				if (actPayAmount == null)
				{
					actPayAmount = FDCHelper.ZERO;
				}
				BigDecimal noPayAmount = apAmount.subtract(actPayAmount);
				if (noPayAmount.compareTo(FDCHelper.ZERO) > 0)
				{
					isHaveAreeageData = true;
					break;
				}
			}
			if (!isHaveAreeageData)
			{
				continue;
			}
			IRow row = this.tblMain.addRow();
			row.getCell("id").setValue(purchase.getId().toString());
			row.getCell("room").setValue(purchase.getRoom().getNumber());
			PurchaseCustomerInfoCollection customerInfos = purchase
					.getCustomerInfo();
			String text = "";
			for (int j = 0; j < customerInfos.size(); j++)
			{
				PurchaseCustomerInfoInfo customerInfo = customerInfos.get(j);
				if (j != 0)
				{
					text += ",";
				}
				if (customerInfo.getCustomer() != null)
				{
					text += customerInfo.getCustomer();
				}
			}
			row.getCell("customer").setValue(purchase.getCustomerNames());
			CellTreeNode treeNode = new CellTreeNode();
		/*	treeNode.addClickListener(new NodeClickListener()
			{
				public void doClick(CellTreeNode source, ICell cell, int type)
				{
					source.doTreeClick(tblMain, cell);
					tblMain.reLayoutAndPaint();
				}
			});*/
			treeNode.setValue("总额");
			treeNode.setTreeLevel(0);
			treeNode.setHasChildren(true);
			treeNode.setCollapse(false);
			row.getCell("moneyName").setValue(treeNode);
			BigDecimal apAmountSum = FDCHelper.ZERO;
			BigDecimal actPayAmountSum = FDCHelper.ZERO;
			int arrDayCountSum = 0;
			for (int j = 0; j < entrys.size(); j++)
			{
				PurchasePayListEntryInfo info = entrys.get(j);
				IRow lRow = this.tblMain.addRow();
				lRow.getCell("id").setValue(purchase.getId().toString());
				treeNode = new CellTreeNode();
				treeNode.setValue(info.getMoneyDefine());
				treeNode.setTreeLevel(1);
				treeNode.setHasChildren(false);
				lRow.getCell("moneyName").setValue(treeNode);
				BigDecimal apAmount = info.getApAmount();
				lRow.getCell("apAmount").setValue(apAmount);
				apAmountSum = apAmountSum.add(apAmount);
				BigDecimal actPayAmount = info.getActPayAmount();
				lRow.getCell("actPayAmount").setValue(actPayAmount);
				if (actPayAmount == null)
				{
					actPayAmount = FDCHelper.ZERO;
				}
				String moneyName = treeNode.toString().trim();

				if (moneyName.equals("定金"))
				{
					dingjinTotalShouldPay = dingjinTotalShouldPay.add(apAmount);
					dingjinTotalActPay = dingjinTotalActPay.add(actPayAmount);
					dingjinTotalNotPay = dingjinTotalShouldPay
							.subtract(dingjinTotalActPay);
				}
				if (moneyName.equals("首期") || moneyName.equals("首付"))
				{
					shoufuTotalShouldPay = shoufuTotalShouldPay.add(apAmount);
					shoufuTotalActPay = shoufuTotalActPay.add(actPayAmount);
					shoufuTotalNotPay = shoufuTotalShouldPay
							.subtract(shoufuTotalActPay);

				}
				if (moneyName.equals("贷款") || moneyName.equals("按揭")
						|| moneyName.equals("按揭款"))
				{
					daikuanTotalShouldPay = daikuanTotalShouldPay.add(apAmount);
					daikuanTotalActPay = daikuanTotalActPay.add(actPayAmount);
					daikuanTotalNotPay = daikuanTotalShouldPay
							.subtract(daikuanTotalActPay);

				}
				// 公积金
				if (moneyName.equals("公积金"))
				{
					//gongjijinDebug = true;

					gongjijinShouldPay = gongjijinShouldPay.add(apAmount);
					gongjijinActPay = gongjijinActPay.add(actPayAmount);
					gongjijinNotPay = gongjijinShouldPay
							.subtract(gongjijinActPay);
				}
				// 补差款
				if (moneyName.equalsIgnoreCase("补差款"))
				{
					//buchaDebug = true;

					buchaShouldPay = buchaShouldPay.add(apAmount);
					buchaActPay = buchaActPay.add(actPayAmount);
					buchaNotPay = buchaShouldPay.subtract(buchaActPay);
				}

				BigDecimal noPayAmount = apAmount.subtract(actPayAmount);
				// 合计未付金额
				totalNotPay = totalNotPay.add(noPayAmount);

				lRow.getCell("noPayAmount").setValue(noPayAmount);
				actPayAmountSum = actPayAmountSum.add(actPayAmount);
				Date apDate = info.getApDate();
				lRow.getCell("apDate").setValue(apDate);
				Date actPayDate = info.getActPayDate();

				if (actPayDate == null)
				{
					actPayDate = new Date();
				} else
				{
					lRow.getCell("actPayDate").setValue(actPayDate);
				}
				/**
				 * ***********************修改BUG
				 * BT290532增加的代码**********************************
				 */
				// 去掉后面时分秒，虽然没有显示出来，但是gettime仍然会计算在内,需用年月日重新构造
				actPayDate = new Date(actPayDate.getYear(), actPayDate
						.getMonth(), actPayDate.getDate());
				apDate = new Date(apDate.getYear(), apDate.getMonth(), apDate
						.getDate());
				/** ************************************************************************************ */
				int arrDayCount = (int) ((actPayDate.getTime() - apDate
						.getTime()) / 86400000);

				if (arrDayCount > 0)
				{
					lRow.getCell("arrearageDayCount").setValue(
							new Integer(arrDayCount));
					if (noPayAmount.compareTo(FDCHelper.ZERO) > 0)
					{
						lRow.getStyleAttributes().setFontColor(Color.RED);
					} else
					{
						lRow.getStyleAttributes().setFontColor(Color.BLUE);
					}
					arrDayCountSum += arrDayCount;
				}
			}
			row.getCell("apAmount").setValue(apAmountSum);
			row.getCell("actPayAmount").setValue(actPayAmountSum);
			row.getCell("noPayAmount").setValue(
					apAmountSum.subtract(actPayAmountSum));
			row.getCell("salesman").setValue(purchase.getSalesman());
			if (arrDayCountSum > 0)
			{
				row.getCell("arrearageDayCount").setValue(
						new Integer(arrDayCountSum));
			}
			// 将应付金额相加
			totalShouldPay = totalShouldPay.add(apAmountSum);
			// 合计实付金额
			totalActPay = totalActPay.add(actPayAmountSum);
			
			
			

			/*
			 * 这个界面中显示的是逾期欠款客户的收款信息，将已经交完全款，或者交款正常的信息 屏蔽
			 */

			if (arrDayCountSum < 1
					|| (arrDayCountSum > 0 && (apAmountSum.subtract(actPayAmountSum))
							.compareTo(FDCHelper.ZERO) <= 0))
			{
				for (int j = 0; j < entrys.size()+1; j++)
				{
					this.tblMain.removeRow(this.tblMain.getRowCount()-1);
				}
			}
			
			
		}
		
		
		/*
		 * 从界面上的实际数据来进行统计，改掉了以前的程序中来控制.以下清零的这些统计变量，在以上的程序中有进行过计算，以上的计算是没有作用的。
		 * 没有删除掉，是因为在程序稳定之前暂时留着。
		 */
	
		
		//将之前的清零
		dingjinTotalShouldPay =FDCHelper.ZERO;
		dingjinTotalActPay = FDCHelper.ZERO;
		dingjinTotalNotPay = FDCHelper.ZERO;
		
		shoufuTotalShouldPay = FDCHelper.ZERO;
		shoufuTotalActPay =FDCHelper.ZERO;
		shoufuTotalNotPay = FDCHelper.ZERO;
		
		daikuanTotalShouldPay=FDCHelper.ZERO;
		daikuanTotalActPay = FDCHelper.ZERO;
		daikuanTotalNotPay = FDCHelper.ZERO;
		
		gongjijinShouldPay = FDCHelper.ZERO;
		gongjijinActPay = FDCHelper.ZERO;
		gongjijinNotPay = FDCHelper.ZERO;
		
		buchaShouldPay = FDCHelper.ZERO;
		buchaActPay = FDCHelper.ZERO;
		buchaNotPay = FDCHelper.ZERO;
		
		BigDecimal buildingShouldPay = FDCHelper.ZERO;
		BigDecimal buildingActPay = FDCHelper.ZERO;
		BigDecimal buildingNotPay = FDCHelper.ZERO;
		
		boolean buildingDebug = false;
		
		totalShouldPay = FDCHelper.ZERO;
		totalNotPay = FDCHelper.ZERO;
		totalActPay = FDCHelper.ZERO;
		
		
		for(int i=0;i<this.tblMain.getRowCount();i++)
		{
			if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("总额"))
			{
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					totalShouldPay = totalShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					totalActPay = totalActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					totalNotPay = totalNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
			}
			else if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("定金"))
			{
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					dingjinTotalShouldPay = dingjinTotalShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					dingjinTotalActPay = dingjinTotalActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					dingjinTotalNotPay = dingjinTotalNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
			}
			else if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("首期"))
			{
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					shoufuTotalShouldPay = shoufuTotalShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					shoufuTotalActPay = shoufuTotalActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					shoufuTotalNotPay = shoufuTotalNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
			}
			else if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("贷款")
					||this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("按揭款"))
			{
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					daikuanTotalShouldPay = daikuanTotalShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					daikuanTotalActPay = daikuanTotalActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					daikuanTotalNotPay = daikuanTotalNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
			}
			else if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("公积金"))
			{
				gongjijinDebug = true;
				
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					gongjijinShouldPay = gongjijinShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					gongjijinActPay = gongjijinActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					gongjijinNotPay = gongjijinNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
			}
			else if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("补差款"))
			{
				buchaDebug = true;
				
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					buchaShouldPay = buchaShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					gongjijinActPay = gongjijinActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					buchaNotPay = buchaNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
				
			}
			
			else if(this.tblMain.getRow(i).getCell("moneyName").getValue().toString().trim().equalsIgnoreCase("楼款"))
			{
				buildingDebug = true;
				
				if(this.tblMain.getRow(i).getCell("apAmount").getValue()!=null)
				{
					buildingShouldPay = buildingShouldPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("apAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("actPayAmount").getValue()!=null)
				{
					buildingActPay = buildingActPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("actPayAmount").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("noPayAmount").getValue()!=null)
				{
					buildingNotPay = buildingNotPay.add(new BigDecimal(this.tblMain.getRow(i).getCell("noPayAmount").getValue().toString()));
				}
				
			}
			
		}
		
		
		
		
		
		
		// 增加一行 合计
		if (purchases.size() > 0)
		{
			if(totalNotPay.compareTo(FDCHelper.ZERO)==1)
			{
			
			CellTreeNode treeNode = new CellTreeNode();
			IRow totalRow = this.tblMain.addRow();
			totalRow.getCell("room").setValue("合计");
			treeNode.setValue("总额");
			treeNode.setTreeLevel(0);
			treeNode.setHasChildren(true);
			treeNode.setCollapse(false);
			totalRow.getCell("moneyName").setValue(treeNode);
			totalRow.getCell("apAmount").setValue(totalShouldPay);
			totalRow.getCell("noPayAmount").setValue(totalNotPay);
			totalRow.getCell("actPayAmount").setValue(totalActPay);

			treeNode = new CellTreeNode();
			treeNode.setValue("定金合计");
			treeNode.setTreeLevel(1);
			treeNode.setHasChildren(false);
			IRow totalRow2 = this.tblMain.addRow();
			totalRow2.getCell("moneyName").setValue(treeNode);
			totalRow2.getCell("apAmount").setValue(dingjinTotalShouldPay);
			totalRow2.getCell("actPayAmount").setValue(dingjinTotalActPay);
			totalRow2.getCell("noPayAmount").setValue(dingjinTotalNotPay);

			treeNode = new CellTreeNode();
			treeNode.setValue("首期合计");
			treeNode.setTreeLevel(1);
			treeNode.setHasChildren(false);
			IRow totalRow3 = this.tblMain.addRow();
			totalRow3.getCell("moneyName").setValue(treeNode);
			totalRow3.getCell("apAmount").setValue(shoufuTotalShouldPay);
			totalRow3.getCell("actPayAmount").setValue(shoufuTotalActPay);
			totalRow3.getCell("noPayAmount").setValue(shoufuTotalNotPay);

			treeNode = new CellTreeNode();
			treeNode.setValue("按揭款合计");
			treeNode.setTreeLevel(1);
			treeNode.setHasChildren(false);
			IRow totalRow4 = this.tblMain.addRow();
			totalRow4.getCell("moneyName").setValue(treeNode);
			totalRow4.getCell("apAmount").setValue(daikuanTotalShouldPay);
			totalRow4.getCell("actPayAmount").setValue(daikuanTotalActPay);
			totalRow4.getCell("noPayAmount").setValue(daikuanTotalNotPay);

			if (gongjijinDebug)
			{
				treeNode = new CellTreeNode();
				treeNode.setValue("公积金合计");
				treeNode.setTreeLevel(1);
				treeNode.setHasChildren(false);
				IRow totalRow5 = this.tblMain.addRow();
				totalRow5.getCell("moneyName").setValue(treeNode);
				totalRow5.getCell("apAmount").setValue(gongjijinShouldPay);
				totalRow5.getCell("actPayAmount").setValue(gongjijinActPay);
				totalRow5.getCell("noPayAmount").setValue(gongjijinNotPay);
			}

			if (buchaDebug)
			{
				treeNode = new CellTreeNode();
				treeNode.setValue("补差款合计");
				treeNode.setTreeLevel(1);
				treeNode.setHasChildren(false);
				IRow totalRow6 = this.tblMain.addRow();
				totalRow6.getCell("moneyName").setValue(treeNode);
				totalRow6.getCell("apAmount").setValue(buchaShouldPay);
				totalRow6.getCell("actPayAmount").setValue(buchaActPay);
				totalRow6.getCell("noPayAmount").setValue(buchaNotPay);
			}
			
			if (buildingDebug)
			{
				treeNode = new CellTreeNode();
				treeNode.setValue("楼款合计");
				treeNode.setTreeLevel(1);
				treeNode.setHasChildren(false);
				IRow totalRow7 = this.tblMain.addRow();
				totalRow7.getCell("moneyName").setValue(treeNode);
				totalRow7.getCell("apAmount").setValue(buildingShouldPay);
				totalRow7.getCell("actPayAmount").setValue(buildingActPay);
				totalRow7.getCell("noPayAmount").setValue(buildingNotPay);
			}

		}
			
		}
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e)
	{
	}

	/**
	 * output class constructor
	 */
	public ArrearageListUI() throws Exception
	{
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception
	{
		// super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception
	{
		// super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception
	{
		super.menuItemImportData_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return PurchaseFactory.getRemoteInstance();
	}

	protected String getEditUIName()
	{
		return null;
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

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
	{
		fillData();
	}
}