package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.LoanDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.LoanDataFactory;
import com.kingdee.eas.fdc.sellhouse.LoanDataInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RateExpression;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SimulantSellUI extends AbstractSimulantSellUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SimulantSellUI.class);

	private AgioParam currAgioParam = new AgioParam();
	private RoomInfo localRoomInfo = null;
	/**
	 * output class constructor
	 */
	public SimulantSellUI() throws Exception {
		super();
	}

	protected void inOnload() throws Exception {
		// super.inOnload();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		return super.getValue(pk);
	}

	protected void f7PayType_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		super.f7PayType_dataChanged(e);
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		if (payType == null) {
			this.tabPayment.removeRows();
			this.kDTable1.removeRows();
		} else {
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("moneyDefine.*");
			view.getSelector().add("currency.*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", payType.getId().toString()));
			PayListEntryCollection payList = new PayListEntryCollection();
			payList = PayListEntryFactory.getRemoteInstance()
					.getPayListEntryCollection(view);
			payType.getPayLists().clear();
			payType.getPayLists().addCollection(payList);

			// 初始化付款方案-付款明细
			this.kDTable1.removeRows();
			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entryInfo = payList.get(i);
				if(!MoneyTypeEnum.ReplaceFee.equals(entryInfo.getMoneyDefine().getMoneyType())){
					IRow row = this.kDTable1.addRow();
					row.setUserObject(entryInfo);
					row.getCell("moneyName").setValue(entryInfo.getMoneyDefine());
					row.getCell("bizTime").setValue(entryInfo.getBizTime());
					row.getCell("month").setValue(
							new Integer(entryInfo.getMonthLimit()));
					row.getCell("day").setValue(
							new Integer(entryInfo.getDayLimit()));
					row.getCell("appDate").setValue(entryInfo.getAppDate());
					row.getCell("des").setValue(entryInfo.getDescription());
					row.getCell("currency").setValue(entryInfo.getCurrency());
					row.getCell("amount").setValue(entryInfo.getValue());
					row.getCell("pro").setValue(entryInfo.getProportion());
					row.getCell("term").setValue(new Integer(entryInfo.getTerm()));
					row.getCell("jiange").setValue(
							new Integer(entryInfo.getMonthInterval()));
				}
			}
		}

		this.updatePayList();
	}

	protected void btnLittleAdjust_actionPerformed(ActionEvent e) throws Exception {
		updatePayList(true);
	}
	
	protected void f7LoanData_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		this.updatePayList();
	}

	protected void txtFitmentAmount_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		this.updateContractAmount();
		this.updatePayList();

	}

	protected void txtAttachAmount_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		this.updateContractAmount();
		this.updatePayList();

	}

	protected void txtAreaComAmount_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		this.updateContractAmount();
		this.updatePayList();

	}
	
	/**
	 * 获取房间的有效面积
	 * 
	 * @param room
	 * @param isLocaleSell
	 *            是否是现售
	 * @return 用来进行计算的有效面积
	 * */
	private BigDecimal getValidArea(RoomInfo room) {
		BigDecimal area = new BigDecimal(0);
		if (!room.isIsActualAreaAudited() && room.isIsCalByRoomArea()) {
			BigDecimal roomArea = room.getRoomArea();
			roomArea = roomArea == null ? new BigDecimal(0) : roomArea;
			area = roomArea;
		} else if (room.isIsActualAreaAudited() && room.isIsCalByRoomArea()) {
			BigDecimal roomArea = room.getActualRoomArea();
			roomArea = roomArea == null ? new BigDecimal(0) : roomArea;
			area = roomArea;
		} else if(!room.isIsActualAreaAudited() && !room.isIsCalByRoomArea())
		{
			BigDecimal buildArea = room.getBuildingArea();
			buildArea = buildArea==null?new BigDecimal(0):buildArea;
			area = buildArea;
		}else if(!room.isIsActualAreaAudited() && !room.isIsCalByRoomArea())
		{
			BigDecimal buildArea = room.getActualBuildingArea();
			buildArea = buildArea==null?new BigDecimal(0):buildArea;
			area = buildArea;
		}
		return area;
	}
	
	private BigDecimal getValidPrice(RoomInfo room) {
		BigDecimal price = new BigDecimal(0);
		BigDecimal roomPrice = room.getRoomPrice();
		roomPrice = roomPrice == null ? new BigDecimal(0) : roomPrice;
		BigDecimal buildPrice = room.getBuildPrice();
		buildPrice = buildPrice == null? new BigDecimal(0) : buildPrice;
		if (room.isIsCalByRoomArea()) {
			price = roomPrice;
		}else
		{
			price = buildPrice;
		}
		return price;
	}
	/**
	 * 设置合同总价
	 */
	protected void updateContractAmount() {
		RoomInfo room = (RoomInfo) this.txtRoomName.getUserObject();
		BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
		if (fitmentAmount == null) {
			fitmentAmount = FDCHelper.ZERO;
		}
		BigDecimal attachAmount = this.txtAttachAmount.getBigDecimalValue();
		if (attachAmount == null) {
			attachAmount = FDCHelper.ZERO;
		}
		BigDecimal areaComAmount = this.txtAreaComAmount.getBigDecimalValue();
		if (areaComAmount == null) {
			areaComAmount = FDCHelper.ZERO;
		}
		BigDecimal standardTotalAmount = room.getStandardTotalAmount();
		if (standardTotalAmount == null) {
			standardTotalAmount = FDCHelper.ZERO;
		}
						
		//房间单价和面积
		BigDecimal price = getValidPrice(room);
		BigDecimal area = getValidArea(room);
//		BigDecimal buchaArea = new BigDecimal(0);
//		BigDecimal roomPrice = room.getRoomPrice();
//		roomPrice = roomPrice==null?new BigDecimal(0):roomPrice;
//		if(!room.isIsActualAreaAudited())
//		{			
//			BigDecimal roomArea = room.getRoomArea();
//			roomArea = roomArea ==null?new BigDecimal(0):roomArea;
//			price = roomPrice;
//			area = roomArea;
//		}else if (room.isIsActualAreaAudited()) {
//			BigDecimal roomArea = room.getActualRoomArea();
//			roomArea = roomArea ==null?new BigDecimal(0):roomArea;
//			price = roomPrice;
//			area = roomArea;
//			buchaArea = roomArea.subtract(room.getRoomArea());
//		}		
		//主房产金额
		BigDecimal contractAmount = new BigDecimal(0);
		
		
		//打折只针对主房产金额,即标准总价加补差款
		AgioEntryCollection agios = (AgioEntryCollection) this.txtAgio.getUserObject();
		if (agios != null) {
			//计算折扣和减点时需要从取到价格计算方式来确定是总价折扣还是单价折扣
			if(PriceAccountTypeEnum.StandSetPrice.equals(this.currAgioParam.getPriceAccountType()))
			{
				BigDecimal mainRoomAmount = standardTotalAmount.add(areaComAmount);
				contractAmount = mainRoomAmount;
				for (int i = 0; i < agios.size(); i++) {
					PurchaseAgioEntryInfo entry = (PurchaseAgioEntryInfo)agios.get(i);
					AgioBillInfo aAgio = entry.getAgio();
					
					BigDecimal amount = entry.getAmount() == null ? FDCHelper.ZERO : entry.getAmount();
					BigDecimal pro = entry.getPro() == null ? FDCHelper.ZERO : entry.getPro();	
					if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
							contractAmount = contractAmount.subtract(amount);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {
							contractAmount = contractAmount.subtract(amount.multiply(area));
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
							contractAmount = contractAmount.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
							BigDecimal jiandianAmount = mainRoomAmount.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
							contractAmount = contractAmount.subtract(jiandianAmount);
					} 
				}
				contractAmount = contractAmount.add(fitmentAmount).add(attachAmount);
				
				contractAmount = SHEHelper.getAmountAfterToInteger(contractAmount, this.currAgioParam.isToInteger(), this.currAgioParam.getToIntegerType(), this.currAgioParam.getDigit());
				
				this.txtContractAmount.setValue(contractAmount);
			}else
			{
				contractAmount = standardTotalAmount;
				for (int i = 0; i < agios.size(); i++) {
					PurchaseAgioEntryInfo entry = (PurchaseAgioEntryInfo) agios.get(i);
					AgioBillInfo aAgio = entry.getAgio();
					
					BigDecimal amount = entry.getAmount() == null ? FDCHelper.ZERO : entry.getAmount();
					BigDecimal pro = entry.getPro() == null ? FDCHelper.ZERO : entry.getPro();
					//按照选择折扣的先后顺序算出单价
					if(aAgio.getCalType().equals(AgioCalTypeEnum.DanJia))
					{
						price = price.subtract(amount);	
//						price = getContractTotalAmountAfterToInteger(price);
//						contractAmount = price.multiply(amount).multiply(area);
//						if(buchaArea.compareTo(new BigDecimal(0))!=0)
//						{
//							//补差款
//							areaComAmount = buchaArea.multiply(price);
//						}
					}else if(aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe))
					{
						price = price.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
//						price = getContractTotalAmountAfterToInteger(price);
//						contractAmount = price.multiply(area);
//						if(buchaArea.compareTo(new BigDecimal(0))!=0)
//						{
//							areaComAmount = buchaArea.multiply(price);
//						}
					}else if(aAgio.getCalType().equals(AgioCalTypeEnum.JianDian))
					{
//						price = price.multiply((new BigDecimal(100).subtract(pro))).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
						BigDecimal jianDianPrice = getValidPrice(room);
						BigDecimal jiandianAmount = jianDianPrice.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);;
						price = price.subtract(jiandianAmount);
						
					}
				}
				price = getContractTotalAmountAfterToInteger(price);
				contractAmount = price.multiply(area);
//				if(buchaArea.compareTo(new BigDecimal(0))!=0)
//				{
//					areaComAmount = buchaArea.multiply(price);
//				}
				//contractAmount = contractAmount.add(areaComAmount);
				contractAmount = contractAmount.add(fitmentAmount).add(attachAmount);
				
				contractAmount = SHEHelper.getAmountAfterToInteger(contractAmount, this.currAgioParam.isToInteger(), this.currAgioParam.getToIntegerType(), this.currAgioParam.getDigit());
				
				//this.txtAreaComAmount.setValue(areaComAmount);
				this.txtContractAmount.setValue(contractAmount);
			}
		}else
		{
			BigDecimal mainRoomAmount = standardTotalAmount.add(areaComAmount);
			contractAmount = mainRoomAmount;
			this.txtContractAmount.setValue(contractAmount);
		}
	}
	
	/**
	 * 折扣选择按纽事件
	 */
	protected void btnChooseAgio_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnChooseAgio_actionPerformed(e);
		RoomInfo room = (RoomInfo) this.txtRoomName.getUserObject();
		if (room == null) {
			MsgBox.showInfo("请先选择房间!");
			return;
		}
		
		AgioEntryCollection agioEntryColl = (AgioEntryCollection) this.txtAgio.getUserObject();
		if(agioEntryColl==null)
			agioEntryColl = new AgioEntryCollection();
		
/*		PurchaseAgioEntryCollection sAgios = (PurchaseAgioEntryCollection) this.txtAgio.getUserObject();
		if(sAgios == null) sAgios = new PurchaseAgioEntryCollection();
		AgioEntryCollection agioEntryColl = new AgioEntryCollection();
		for(int i=0;i<sAgios.size();i++)
			agioEntryColl.add(sAgios.get(i));*/
			
//		AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), agioEntryColl, this.currAgioParam,false);
//		if(agioParam != null){
//			if (agioParam.getAgios() != null) {//正常情况下这里agios肯定不会为空
//				this.txtAgio.setUserObject(agioParam.getAgios());
//				this.txtAgio.setText(this.getAgioDes());
//				this.updateContractAmount();
//				this.updatePayList();
//			}
//		}
	}

	/**
	 * 折扣
	 */
	private String getAgioDes() {
		return SHEHelper.getAgioDes((AgioEntryCollection)this.txtAgio.getUserObject(), null, 
					this.currAgioParam.isToInteger(), this.currAgioParam.getToIntegerType(), this.currAgioParam.getDigit());		
	}

	/**
	 * 界面显示
	 */
	public static void showUI(IUIObject ui, String roomId, String projectID)
			throws EASBizException, BOSException {
		UIContext uiContext = new UIContext(ui);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(
				new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		uiContext.put("roomId", roomId);
		uiContext.put("room", room);
		if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
			MsgBox.showInfo("房间已经被保留!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("房间没有开盘!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
			MsgBox.showInfo("房间已经被预定!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
			MsgBox.showInfo("房间已经签约!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
			MsgBox.showInfo("房间已经被认购!");
			return;
		}
		uiContext.put("projectID", projectID);
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(SimulantSellUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}

	/**
	 * 打印
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = new KDTable();
		// table = tabPayment;
		table.addColumns(4);
		if (this.f7PayType.getValue() == null) {
			table.getPrintManager().printPreview();
		} else {
			for (int i = 0; i < tabPayment.getRowCount(); i++) {
				IRow irow = (IRow) tabPayment.getRow(i).clone();
				table.addRow(i, irow);
			}
			this.getSimulantSell(table);
			table.getPrintManager().print();
			// this.getTableHead(table);
		}
		super.actionPrint_actionPerformed(e);
	}

	private void getSimulantSell(KDTable table) {
		table.removeHeadRows();
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("房间信息");
		row.getCell(1).setValue(
				((RoomInfo) this.txtRoomName.getUserObject()).getName());
		IRow row1 = table.addHeadRow();
		row1.getCell(0).setValue("付款方案");
		row1.getCell(1).setValue((SHEPayTypeInfo) this.f7PayType.getValue());
		row1.getCell(2).setValue("折扣信息");
		row1.getCell(3).setValue(this.getAgioDes());
		IRow row2 = table.addHeadRow();
		row2.getCell(0).setValue("补差款");
		row2.getCell(1).setValue(
				(BigDecimal) this.txtAreaComAmount.getNumberValue());
		row2.getCell(2).setValue("附属款");
		row2.getCell(3).setValue(
				(BigDecimal) this.txtAttachAmount.getNumberValue());
		IRow row3 = table.addHeadRow();
		row3.getCell(0).setValue("装修款");
		row3.getCell(1).setValue(
				(BigDecimal) this.txtFitmentAmount.getNumberValue());
		row3.getCell(2).setValue("合同总价");
		row3.getCell(3).setValue(
				(BigDecimal) this.txtContractAmount.getNumberValue());
		IRow row4 = table.addHeadRow();
		row4.getCell(0).setValue("");
		row4.getCell(1).setValue("");
		row4.getCell(2).setValue("");
		row4.getCell(3).setValue("");
		IRow row5 = table.addHeadRow();
		row5.getCell(0).setValue("还款日期");
		row5.getCell(1).setValue("款项名称");
		row5.getCell(2).setValue("金额");
		row5.getCell(3).setValue("币别");
	}

	private void getTableHead(KDTable table) {
		table.removeHeadRows();
		IRow row6 = table.addHeadRow();
		row6.getCell(0).setValue("还款日期");
		row6.getCell(1).setValue("款项名称");
		row6.getCell(2).setValue("金额");
		row6.getCell(3).setValue("币别");
	}

	/**
	 * 打印预览
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		KDTable table = new KDTable();
		table = tabPayment;
		if (this.f7PayType.getValue() == null) {
			table.getPrintManager().printPreview();
		} else {
			this.getSimulantSell(table);
			table.getPrintManager().printPreview();
			this.getTableHead(table);
		}
		super.actionPrintPreview_actionPerformed(e);
	}
	
	public void actionSimuPrintPreView_actionPerformed(ActionEvent e) throws Exception
	{
		if(this.kDTable1.getRowCount()==0)
		{
			MsgBox.showInfo("付款计划表为空,不能打印!");
			return ;
		}
		String roomID = "";
		RoomInfo room = null;
		if (this.getUIContext().get("room") != null) {
			 roomID = ((RoomInfo) this.getUIContext().get("room"))
					.getId().toString();
			  room = (RoomInfo) this.getUIContext().get("room");
		} else {
			if (localRoomInfo != null) {
				room = localRoomInfo;
				roomID = localRoomInfo.getId().toString();
			} else {
				FDCMsgBox.showWarning(this, "房间信息不能为空！");
				SysUtil.abort();
			}
			
		}
		SHEPayTypeInfo payTypeInfo = (SHEPayTypeInfo)this.f7PayType.getValue();
		LoanDataInfo loanData = (LoanDataInfo)this.f7LoanData.getValue();
		String agioDes = this.getAgioDes();
		BigDecimal areaComAmount = this.txtAreaComAmount.getBigDecimalValue();
		if(areaComAmount==null)
		{
			areaComAmount = FDCHelper.ZERO;
		}
		BigDecimal attachAmount = this.txtAttachAmount.getBigDecimalValue();
		if(attachAmount==null)
		{
			attachAmount = FDCHelper.ZERO;
		}
		BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
		if(fitmentAmount==null)
		{
			fitmentAmount = FDCHelper.ZERO;
		}
		BigDecimal contractAmount = this.txtContractAmount.getBigDecimalValue();
		if(contractAmount==null)
		{
			contractAmount = FDCHelper.ZERO;
		}
		int term = 0;
		int monthInterval = 0;
		for(int i=0;i<kDTable1.getRowCount();i++)
		{
			IRow row = kDTable1.getRow(i);
			PayListEntryInfo entry = (PayListEntryInfo)row.getUserObject();
			if(MoneyTypeEnum.LoanAmount.equals(entry.getMoneyDefine().getMoneyType())
					|| MoneyTypeEnum.AccFundAmount.equals(entry.getMoneyDefine().getMoneyType()))
			{
				term = entry.getTerm();
				monthInterval = entry.getMonthInterval();
			}
		}
		HashMap parmterMap = new HashMap();
		parmterMap.put("room", room);
		parmterMap.put("payType", payTypeInfo);
		parmterMap.put("loanData", loanData);
		parmterMap.put("agioDes", agioDes);
		parmterMap.put("areaComAmount", areaComAmount);
		parmterMap.put("attachAmount", attachAmount);
		parmterMap.put("fitmentAmount", fitmentAmount);
		parmterMap.put("contractAmount", contractAmount);
		parmterMap.put("term", new Integer(term));
		parmterMap.put("monthInterval", new Integer(monthInterval));
		SimulantPrintDataProvider data = new SimulantPrintDataProvider(
				roomID,
				new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.SimulantRoomPrintQuery"),parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/simulantPrint", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrintPreview_actionPerformed(e);
	}
	
	public void actionSimuPrint_actionPerformed(ActionEvent e) throws Exception
	{
		if(this.kDTable1.getRowCount()==0)
		{
			MsgBox.showInfo("付款计划表为空,不能打印!");
			return ;
		}
		String roomID = ((RoomInfo)this.getUIContext().get("room")).getId().toString();
		RoomInfo room = (RoomInfo)this.getUIContext().get("room");
		SHEPayTypeInfo payTypeInfo = (SHEPayTypeInfo)this.f7PayType.getValue();
		LoanDataInfo loanData = (LoanDataInfo)this.f7LoanData.getValue();
		String agioDes = this.getAgioDes();
		BigDecimal areaComAmount = this.txtAreaComAmount.getBigDecimalValue();
		if(areaComAmount==null)
		{
			areaComAmount = FDCHelper.ZERO;
		}
		BigDecimal attachAmount = this.txtAttachAmount.getBigDecimalValue();
		if(attachAmount==null)
		{
			attachAmount = FDCHelper.ZERO;
		}
		BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
		if(fitmentAmount==null)
		{
			fitmentAmount = FDCHelper.ZERO;
		}
		BigDecimal contractAmount = this.txtContractAmount.getBigDecimalValue();
		if(contractAmount==null)
		{
			contractAmount = FDCHelper.ZERO;
		}
		int term = 0;
		int monthInterval = 0;
		for(int i=0;i<kDTable1.getRowCount();i++)
		{
			IRow row = kDTable1.getRow(i);
			PayListEntryInfo entry = (PayListEntryInfo)row.getUserObject();
			if(MoneyTypeEnum.LoanAmount.equals(entry.getMoneyDefine().getMoneyType())
				|| MoneyTypeEnum.AccFundAmount.equals(entry.getMoneyDefine().getMoneyType()))
		    {
				term = entry.getTerm();
				monthInterval = entry.getMonthInterval();
			}
		}
		HashMap parmterMap = new HashMap();
		parmterMap.put("room", room);
		parmterMap.put("payType", payTypeInfo);
		parmterMap.put("loanData", loanData);
		parmterMap.put("agioDes", agioDes);
		parmterMap.put("areaComAmount", areaComAmount);
		parmterMap.put("attachAmount", attachAmount);
		parmterMap.put("fitmentAmount", fitmentAmount);
		parmterMap.put("contractAmount", contractAmount);
		parmterMap.put("term", new Integer(term));
		parmterMap.put("monthInterval", new Integer(monthInterval));
		SimulantPrintDataProvider data = new SimulantPrintDataProvider(
				roomID,
				new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.SimulantRoomPrintQuery"),parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/simulantPrint", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * 房间按纽选择事件
	 */
	protected void btnSelectRoom_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.tabPayment.getBody() != null) {
			this.tabPayment.removeRows();
		}
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get(
				"building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
		RoomInfo room = null;
		if(isFromVirtual()){
			room = RoomSelectUI.showVirtualOneRoomSelectUI(this, buildingInfo,buildUnit,MoneySysTypeEnum.SalehouseSys, null,null);
		}else{
			room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,buildUnit,MoneySysTypeEnum.SalehouseSys);
		}
		if (room == null) {
			return;
		}
		this.getUIContext().put("room", room);
		localRoomInfo = room;
		this.f7PayType.setValue(null);
		this.f7LoanData.setValue(null);
		this.txtAttachAmount.setValue(null);
		this.txtFitmentAmount.setValue(null);
		this.txtAreaComAmount.setValue(null);
		verifyAddNewRoom(room);
		updateRoomInfo(room);
		addF7PayTypeAndF7LoanDataFilter();

	}

	private boolean isFromVirtual() {
		if(this.getUIContext().get("isFromVirtual") != null){
			return true;
		}
		return false;
	}

	/**
	 * 显示房间信息
	 * 
	 * @param room
	 * @return
	 * @throws
	 */
	private void updateRoomInfo(RoomInfo room) {
		this.txtRoomName.setUserObject(room);
		if(room.getName()!=null){
			this.txtRoomName.setText(room.getName());
		}else{
			RoomInfo info = null;
			try {
				info = RoomFactory.getRemoteInstance().getRoomInfo("select id,name,number where id='"+room.getId().toString()+"'");
				if(info!=null){
					this.txtRoomName.setText(info.getName());
				}
			} catch (EASBizException e) {
				logger.error(e.getMessage());
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
			
		}
		
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtFloorHeight.setValue(room.getFloorHeight());
		this.f7Direcition.setValue((HopedDirectionInfo) room.getDirection());
		this.f7Sight.setValue((SightRequirementInfo) room.getSight());
		this.f7RoomModel.setValue((RoomModelInfo) room.getRoomModel());
		this.f7BuildingProperty.setValue((BuildingPropertyInfo) room
				.getBuildingProperty());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());

		this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());
		this.chkIsCalByRoom.setSelected(room.isIsCalByRoomArea());
		if (room.isIsCalByRoomArea()) {
			this.txtStandardPrice.setValue(room.getRoomPrice());
		} else {
			this.txtStandardPrice.setValue(room.getBuildPrice());
		}
		this.comboRoomState.setSelectedItem(room.getSellState());
		if (room.isIsActualAreaAudited()) {
			BigDecimal roomPrice = room.getRoomPrice();
			if (roomPrice == null) {
				roomPrice = FDCHelper.ZERO;
				;
			}
			BigDecimal areaComAmount = (room.getActualRoomArea().subtract(room
					.getRoomArea())).multiply(roomPrice);
			this.txtAreaComAmount.setValue(areaComAmount);
			this.txtAreaComAmount.setEditable(false);
		}
//		if (room.isIsActualAreaAudited() && room.isIsCalByRoomArea()) {
//			BigDecimal roomPrice = room.getRoomPrice();
//			if (roomPrice == null) {
//				roomPrice = FDCHelper.ZERO;
//				;
//			}
//			BigDecimal areaComAmount = (room.getActualRoomArea().subtract(room
//					.getRoomArea())).multiply(roomPrice);
//			this.txtAreaComAmount.setValue(areaComAmount);
//			this.txtAreaComAmount.setEditable(false);
//		}
//		if (room.isIsActualAreaAudited() && !room.isIsCalByRoomArea()) {
//			BigDecimal buildPrice = room.getBuildPrice();
//			if (buildPrice == null) {
//				buildPrice = FDCHelper.ZERO;
//				;
//			}
//			BigDecimal areaComAmount = (room.getActualBuildingArea()
//					.subtract(room.getBuildingArea())).multiply(buildPrice);
//			this.txtAreaComAmount.setValue(areaComAmount);
//			this.txtAreaComAmount.setEditable(false);
//		}
		this.updateContractAmount();
	}

	private  void verifyAddNewRoom(RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId().toString()));
		// RoomAttachmentEntryCollection attaches = null;
		// try {
		// attaches = RoomAttachmentEntryFactory.getRemoteInstance()
		// .getRoomAttachmentEntryCollection(view);
		// } catch (BOSException e) {
		// e.printStackTrace();
		// }
		// for (int i = 0; i < attaches.size(); i++) {
		// if (attaches.get(i).isIsMergeToPurchase()) {
		// MsgBox.showInfo("该房间已经被其他房间绑定并且并入合同,不能单独销售!");
		// this.abort();
		// }
		// }
		if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
			MsgBox.showInfo("房间已经被保留!");
			this.abort();
		}
		if (room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("房间没有开盘!");
			this.abort();
		}
		if(room.getBuildPrice()==null || room.getRoomPrice()==null)
		{
			MsgBox.showInfo("房间还没有定价!");
			this.abort();
		}
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
			MsgBox.showInfo("房间已经被预定!");
			this.abort();
		}
		if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
			MsgBox.showInfo("房间已经签约!");
			this.abort();
		}
		if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
			MsgBox.showInfo("房间已经被认购!");
			this.abort();
		}
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		String roomId = (String) this.getUIContext().get("roomId");
		if(roomId ==null)
		{
		}else {
			// 创建UI对象并显示
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("building.sellProject");
			sels.add("roomModel.*");
			sels.add("buildingProperty.*");
			sels.add("direction.*");
			sels.add("sight.*");
			RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(BOSUuid.read(roomId)), sels);
			updateRoomInfo(room);
		}
		//对付款方式,按揭资料的过滤
		addF7PayTypeAndF7LoanDataFilter();
		initPayTable();
		this.txtAgio.setText(this.getAgioDes());
		this.actionCalc.setEnabled(true);
	}

	public void loadFields() {
		super.loadFields();
	}

	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalc_actionPerformed(e);
		
		/**
		 * 因为此界面是个纯粹的计算用的
		 */
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", "");
		// 创建UI对象并显示
		
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MortgageCalcUI.class.getName(), uiContext,null,OprtState.ADDNEW);
		uiWindow.show();
	}
	private void updatePayList() throws EASBizException, BOSException {
		updatePayList(false);
	}
	
	/**
	 * 款项列表
	 * @param isAdjustLittle 是否进行万位调整
	 * @return
	 * @throws EASBizException,BOSException
	 */
	private void updatePayList(boolean isAdjustLittle) throws EASBizException, BOSException {
		if (this.tabPayment.getBody() != null) {
			this.tabPayment.removeRows();
		}
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		if (payType != null) {
			PayListEntryCollection payList = payType.getPayLists();
			BigDecimal eareatMoney = FDCHelper.ZERO;
			BigDecimal remain = this.txtContractAmount.getBigDecimalValue();
			if (remain == null) {
				remain = FDCHelper.ZERO;
			}
			
			PurchasePayListEntryCollection purPays = new PurchasePayListEntryCollection();
			
			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entry = payList.get(i);
				if(!MoneyTypeEnum.ReplaceFee.equals(entry.getMoneyDefine().getMoneyType())){
					PurchasePayListEntryInfo purEntry = new PurchasePayListEntryInfo();
					Date curDate = new Date();
					if (entry.getBizTime().equals(BizTimeEnum.AppTime)) {
						curDate = entry.getAppDate();
					} else {
						curDate = new Date();
						int monthLimit = entry.getMonthLimit();
						int dayLimit = entry.getDayLimit();
						Calendar cal = Calendar.getInstance();
						cal.setTime(curDate);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						curDate = cal.getTime();
					}
					purEntry.setAppDate(curDate);
					BigDecimal amount = FDCHelper.ZERO;
					if (entry.getValue() != null) {
						amount = entry.getValue();
					} else {
						BigDecimal proportion = entry.getProportion();
						amount = this.txtContractAmount.getBigDecimalValue();
						if (amount == null) {
							amount = FDCHelper.ZERO;
						}
						amount = amount.multiply(proportion).divide(
								new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
					}
					MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
					if (moneyDefine.getMoneyType().equals(
							MoneyTypeEnum.EarnestMoney)) {
						eareatMoney = eareatMoney.add(amount);
					} else {
						amount = amount.subtract(eareatMoney);
						eareatMoney = FDCHelper.ZERO;
						if (amount.compareTo(FDCHelper.ZERO) < 0) {
							amount = FDCHelper.ZERO;
						}
					}

					if (amount.compareTo(remain) > 0) {
						amount = remain;
						remain = FDCHelper.ZERO;
					} else {
						remain = remain.subtract(amount);
					}
					if (i == payList.size() - 1) {
						amount = amount.add(remain);
					}
					purEntry.setAppAmount(amount);
					purEntry.setMoneyDefine(moneyDefine);
					purEntry.setCurrency(entry.getCurrency());
					purEntry.setTerm(entry.getTerm());
					purEntry.setMonthInterval(entry.getMonthInterval());
					
					purPays.add(purEntry);
				}
			}
			
			if(isAdjustLittle){
				BigDecimal littleUnit = null;// 尾数标准,可能改为可配置
				Object digit = this.comboDigit.getSelectedItem();
				if(digit.equals(SHEHelper.THOUSHAND_DIGIT)){
					littleUnit = new BigDecimal("1000");
				}else{
					littleUnit = new BigDecimal("10000");
				}
				
				PurchasePayListEntryInfo firstAmountPay = null;
				PurchasePayListEntryInfo lastAmountBeforeLoan = null;
				boolean beforeLoan = true;
				Map toReducedPays = new HashMap();
				BigDecimal totalLittleLoanAmount = FDCHelper.ZERO;
				for (int i = 0; i < purPays.size(); i++) {
					PurchasePayListEntryInfo purPay = purPays.get(i);
					MoneyDefineInfo moneyDefineInfo = purPay.getMoneyDefine();
					if (moneyDefineInfo == null) {
						continue;
					}

					MoneyTypeEnum moneyType = moneyDefineInfo.getMoneyType();
					if (moneyType.equals(MoneyTypeEnum.FisrtAmount)) {// 首付
						firstAmountPay = purPay;
					} else if (moneyType.equals(MoneyTypeEnum.LoanAmount)) {// 按揭
						beforeLoan = false;
						BigDecimal loanAmount = purPay.getApAmount();
						if (loanAmount.compareTo(littleUnit) <= 0) {
							continue;
						}
						BigDecimal littleAmount = SHEHelper.remainder(loanAmount, littleUnit);
						toReducedPays.put(purPay, loanAmount.subtract(littleAmount));
						totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
					} else if (moneyType.equals((MoneyTypeEnum.AccFundAmount))) {// 公积金
						beforeLoan = false;
						BigDecimal accFundAmount = purPay.getApAmount();
						if (accFundAmount.compareTo(littleUnit) <= 0) {
							continue;
						}
						BigDecimal littleAmount = SHEHelper.remainder(accFundAmount, littleUnit);
						toReducedPays.put(purPay, accFundAmount.subtract(littleAmount));
						totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
					} else {
						if (beforeLoan)
							lastAmountBeforeLoan = purPay;
					}
				}
			
				// 将按揭的尾数加入到首期款上
				// 如果没有首期款项,则将尾数加到按揭,公积金款项前的最后一行
				PurchasePayListEntryInfo toAddedPay = firstAmountPay != null ? firstAmountPay : lastAmountBeforeLoan;
				if (toAddedPay != null) {
					BigDecimal srcAmount = toAddedPay.getApAmount();
					if(srcAmount == null) srcAmount = FDCHelper.ZERO;
					
					toAddedPay.setAppAmount(srcAmount.add(totalLittleLoanAmount));
					
					Set to = toReducedPays.keySet();
					for(Iterator itor = to.iterator(); itor.hasNext(); ){
						PurchasePayListEntryInfo toReducedPay = (PurchasePayListEntryInfo) itor.next();
						BigDecimal value = (BigDecimal) toReducedPays.get(toReducedPay);
						toReducedPay.setAppAmount(value);
					}
				}
			}
			
			for(int i=0; i<purPays.size(); i++){
				addPayListEntryRow(purPays.get(i));
			}
		}
	}

	/**
	 * 按揭还款详细列表
	 * 
	 * @param entry
	 * @return
	 * @throws EASBizException,BOSException
	 */
	private void addPayListEntryRow(PurchasePayListEntryInfo entry)
			throws EASBizException, BOSException {
		this.tabPayment.getTreeColumn().setDepth(2);
		IRow row = this.tabPayment.addRow();
		row.setTreeLevel(0);
		String formatString = "yyyy-MM-dd";
		row.setUserObject(entry);
		row.getCell("term").setValue(entry.getAppDate());
		row.getCell("term").getStyleAttributes().setNumberFormat(formatString);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("apAmount").setValue(entry.getApAmount());
		row.getCell("apAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		row.getCell("currency").setValue(entry.getCurrency());
		BigDecimal countAmount = FDCHelper.ZERO;
		BigDecimal amount = entry.getApAmount();
		if (amount != null && amount.compareTo(FDCHelper.ZERO) != 0) {
			row.getCell("term").getStyleAttributes().setLocked(true);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("apAmount").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
		}
//		if (entry.getMoneyDefine().getName().equals("按揭")
//				|| entry.getMoneyDefine().getName().equals("按揭款")
//				|| entry.getMoneyDefine().getName().equals("贷款")) {
		if(MoneyTypeEnum.LoanAmount.equals(entry.getMoneyDefine().getMoneyType())
				|| MoneyTypeEnum.AccFundAmount.equals(entry.getMoneyDefine().getMoneyType()))
		{
			SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
			// 还款次数
			int requMonth = entry.getTerm();
			if(requMonth==1)
			{
				return;
			}
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("loanLoanData.*");
			if (payType.getLoanLoanData() == null) {
				if (this.f7LoanData.getValue() != null) {
					payType.setLoanLoanData((LoanDataInfo) this.f7LoanData
							.getValue());
				} else {
					MsgBox.showInfo("请填写按揭资料");
					this.abort();
				}
			}
			LoanDataInfo loan = LoanDataFactory
					.getRemoteInstance()
					.getLoanDataInfo(
							new ObjectUuidPK(payType.getLoanLoanData().getId()));

			 //贷款年限
			int yearCount = loan.getLoanFixedYear();
			// 贷款利率
			BigDecimal loanInterestRate = FDCHelper.ZERO;
			LoanDataEntryCollection loanColl = (LoanDataEntryCollection) loan
					.getEntrys();
			int min = 0;
			if (loanColl.size() > 0) {
				int fixedYear = loanColl.get(0).getFixedYear();
				min = Math.abs(yearCount - fixedYear);
				loanInterestRate = loanColl.get(0).getInterestRate();
			}
			for (int i = 0; i < loanColl.size(); i++) {
				LoanDataEntryInfo loanInfo = loanColl.get(i);
				int fixedYear = loanInfo.getFixedYear();
				if (yearCount == fixedYear) {
					loanInterestRate = loanInfo.getInterestRate();
					break;
				} else if (min > Math.abs(yearCount - fixedYear)) {
					min = Math.abs(yearCount - fixedYear);
					loanInterestRate = loanInfo.getInterestRate();
				}
			}
			// 还款次数
			//int requMonth = entry.getTerm();
			//int monthInterval = entry.getMonthInterval();
			// 实际还款次数
//			int requMonth = 0;
//			if ((12 * yearCount) % monthInterval != 0) {
//				requMonth = (12 * yearCount / monthInterval) + 1;
//			} else {
//				requMonth = 12 * yearCount / monthInterval;
//			}
			for (int j = 0; j < requMonth; j++) {
				row = this.tabPayment.addRow();
				row.setTreeLevel(1);
				row.getCell("moneyName").setValue(
						entry.getMoneyDefine() + (j + 1 + "期"));
				Calendar cal = Calendar.getInstance();
				if (j == 0) {
					cal.setTime(entry.getAppDate());
				} else {

					SimpleDateFormat simpleDate = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date date = null;
					try {
						date = simpleDate.parse(entry.getAppDate().toString());
					} catch (Exception e) {
					}
					cal.setTime(date);
					cal.add(Calendar.MONTH, entry.getMonthInterval() * j);
				}

				LoanCalculateTypeEnum loanCalculate  = null;
				//如果是按揭款取按揭计算方式如果是公积金取公积金计算方式
				if(MoneyTypeEnum.LoanAmount.equals(entry.getMoneyDefine().getMoneyType()))
				{
					loanCalculate = payType.getLoanCalculateType();
				}else
				{
					loanCalculate = payType.getAccumulationCalculateType();
				}
				BigDecimal termAmount = FDCHelper.ZERO;
				if (loanCalculate
						.equals(LoanCalculateTypeEnum.DescendingAmount)) {
					termAmount = RateExpression.getAmountPrincipal(amount,
							requMonth, loanInterestRate, j);
					countAmount = countAmount.add(termAmount);
				} else {
					termAmount = RateExpression.getAmountAccrual(amount,
							requMonth, loanInterestRate);
					countAmount = countAmount.add(termAmount);
				}
				row.getCell("term").setValue(cal.getTime());
				row.getCell("term").getStyleAttributes().setNumberFormat(
						formatString);
				row.getCell("apAmount").setValue(termAmount);
				row.getCell("apAmount").getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				row.getCell("currency").setValue(entry.getCurrency());
				if (termAmount != null
						&& termAmount.compareTo(FDCHelper.ZERO) != 0) {
					row.getStyleAttributes().setBackground(
							FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
					row.getCell("term").getStyleAttributes().setLocked(true);
					row.getCell("moneyName").getStyleAttributes().setLocked(
							true);
					row.getCell("apAmount").getStyleAttributes()
							.setLocked(true);
					row.getCell("currency").getStyleAttributes()
							.setLocked(true);
				}
			}
		}
	}

	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return SimulantSellUI.class.getName();
	}

	private void initControl() {
		this.comboDigit.addItem(SHEHelper.TEN_THOUSHAND_DIGIT);
		this.comboDigit.addItem(SHEHelper.THOUSHAND_DIGIT);
		
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnSimuPrint.setVisible(true);
		this.btnSimuPrintPreView.setVisible(true);
		this.btnSimuPrint.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtRoomName.setEditable(false);
		this.txtBuildingArea.setEnabled(false);
		this.txtRoomArea.setEnabled(false);
		this.txtActualBuildingArea.setEnabled(false);
		this.txtActualRoomArea.setEnabled(false);
		this.txtAgio.setEnabled(false);
		this.txtApportionArea.setEnabled(false);
		this.txtBalconyArea.setEnabled(false);
		this.txtFloorHeight.setEnabled(false);
		this.f7Direcition.setEnabled(false);
		this.f7Sight.setEnabled(false);
		this.f7RoomModel.setEnabled(false);
		this.f7BuildingProperty.setEnabled(false);
		this.comboHouseProperty.setEnabled(false);
		this.txtStandardPrice.setEnabled(false);
		this.txtStandardTotalAmount.setEnabled(false);
		this.chkIsCalByRoom.setEnabled(false);
		this.comboRoomState.setEnabled(false);
		this.txtContractAmount.setEnabled(false);
		this.txtStandardPrice.setSupportedEmpty(true);
		this.txtStandardPrice.setRemoveingZeroInDispaly(false);
		this.txtStandardPrice.setRemoveingZeroInEdit(false);
		this.txtStandardPrice.setPrecision(2);

		this.txtAreaComAmount.setRemoveingZeroInDispaly(false);
		this.txtAreaComAmount.setRemoveingZeroInEdit(false);
		this.txtAreaComAmount.setPrecision(2);
		this.txtAreaComAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAreaComAmount.setSupportedEmpty(true);

		this.txtAttachAmount.setRemoveingZeroInDispaly(false);
		this.txtAttachAmount.setRemoveingZeroInEdit(false);
		this.txtAttachAmount.setPrecision(2);
		this.txtAttachAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAttachAmount.setSupportedEmpty(true);

		this.txtFitmentAmount.setRemoveingZeroInDispaly(false);
		this.txtFitmentAmount.setRemoveingZeroInEdit(false);
		this.txtFitmentAmount.setPrecision(2);
		this.txtFitmentAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFitmentAmount.setSupportedEmpty(true);

		this.txtContractAmount.setRemoveingZeroInDispaly(false);
		this.txtContractAmount.setRemoveingZeroInEdit(false);
		this.txtContractAmount.setPrecision(2);
		this.txtContractAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtContractAmount.setSupportedEmpty(true);

		this.txtRoomArea.setRemoveingZeroInDispaly(false);
		this.txtRoomArea.setRemoveingZeroInEdit(false);
		this.txtRoomArea.setPrecision(2);
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setSupportedEmpty(true);
		this.txtApportionArea.setRemoveingZeroInDispaly(false);
		this.txtApportionArea.setRemoveingZeroInEdit(false);
		this.txtApportionArea.setPrecision(2);
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApportionArea.setSupportedEmpty(true);
		this.txtBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingArea.setRemoveingZeroInEdit(false);
		this.txtBuildingArea.setPrecision(2);
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setSupportedEmpty(true);
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFloorHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBalconyArea.setHorizontalAlignment(JTextField.RIGHT);

		this.txtStandardPrice.setRemoveingZeroInDispaly(false);
		this.txtStandardPrice.setRemoveingZeroInEdit(false);
		this.txtStandardPrice.setPrecision(2);
		this.txtStandardPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardPrice.setSupportedEmpty(true);

		this.txtStandardTotalAmount.setRemoveingZeroInDispaly(false);
		this.txtStandardTotalAmount.setRemoveingZeroInEdit(false);
		this.txtStandardTotalAmount.setPrecision(2);
		this.txtStandardTotalAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardTotalAmount.setSupportedEmpty(true);
	}

	private void addF7PayTypeAndF7LoanDataFilter() {
		RoomInfo room = (RoomInfo) this.txtRoomName.getUserObject();
		if(room == null){
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("startDate", new Date(),	CompareType.LESS_EQUALS));
		/*CtrlUnitInfo cu = SysContext.getSysContext().getCurrentCtrlUnit();
		filter.getFilterItems().add(new FilterItemInfo("CU.id", cu.getId().toString()));*/
		if(room.getBuilding().getSellProject() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("project.id",room.getBuilding().getSellProject().getId().toString()));
		}
		this.f7PayType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(room.getBuilding().getSellProject() != null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("sellProject.id", room.getBuilding().getSellProject().getId().toString()));	
		}
		
		this.f7LoanData.setEntityViewInfo(view);
	}
	
	private void initPayTable() {
		this.kDTable1.checkParsed();

		this.kDTable1.getColumn("month").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("month").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		this.kDTable1.getColumn("day").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("day").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		String formatString = "yyyy-MM-dd";
		this.kDTable1.getColumn("appDate").getStyleAttributes()
				.setNumberFormat(formatString);

		this.kDTable1.getColumn("amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("pro").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("pro").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("term").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("term").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		this.kDTable1.getColumn("jiange").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("jiange").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.INTEGER);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kDTable1.getColumn("term").setEditor(numberEditor);
		this.kDTable1.getColumn("jiange").setEditor(numberEditor);

	}

	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		if (this.kDTable1.getRow(rowIndex).getUserObject() instanceof PayListEntryInfo) {
			PayListEntryInfo payListEntryInfo = (PayListEntryInfo) this.kDTable1
					.getRow(rowIndex).getUserObject();
			if (this.kDTable1.getColumn(e.getColIndex()).getKey()
					.equals("term")) {
				payListEntryInfo.setTerm(Integer.parseInt(e.getValue()
						.toString()));
			} else if (this.kDTable1.getColumn(e.getColIndex()).getKey()
					.equals("jiange")) {
				payListEntryInfo.setMonthInterval(Integer.parseInt(e.getValue()
						.toString()));
			}
		}
		this.updatePayList();

	}
	
	/**
	 * 将金额进行取整操作，返回取整后的金额
	 * */
	private BigDecimal getContractTotalAmountAfterToInteger(BigDecimal contractTotalAmount) {
		return SHEHelper.getAmountAfterToInteger(contractTotalAmount, this.currAgioParam.isToInteger(), this.currAgioParam.getToIntegerType(), this.currAgioParam.getDigit());
	}
}