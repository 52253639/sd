/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillCollection;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class InsteadCollectOutBatchUI extends AbstractInsteadCollectOutBatchUI
{
    private static final Logger logger = CoreUIObject.getLogger(InsteadCollectOutBatchUI.class);
    
    /**
     * output class constructor
     */
    public InsteadCollectOutBatchUI() throws Exception
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

	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		queryAndFill();
		initTable();
		this.pkDate.setValue(new Date());
		showUnFittableData();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getColumn("entryID").getStyleAttributes().setHided(true);
		tblMain.getColumn("number").getStyleAttributes().setHided(true);
		this.prmtPerson.setValue((com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo().getPerson()));
	}
	
	
    public void onShow() throws Exception
	{
		// TODO Auto-generated method stub
		super.onShow();
	}
    /**
     * 显示转出费用不合理的数据
     */
    private void showUnFittableData() {
    	BigDecimal outFee = null;
    	IRow row = null;
    	for(int i = 0;i<tblMain.getBody().size();i++) {
    		row = tblMain.getRow(i);
    		outFee = (BigDecimal)row.getCell("curInsteadAmount").getValue();
    		if(outFee.compareTo(new BigDecimal(0))<=0) {
    			row.getStyleAttributes().setBackground(Color.yellow);
    		}
    	}
    }

	/**
     * 拼Sql
     * @return
     */
	private FDCSQLBuilder getSql() {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		Map map = this.getUIContext();
		if(map==null)return null;
		Set entryIDs = (Set) map.get("entryIDs");
		if(entryIDs==null)return null;
		Iterator it = entryIDs.iterator();
		StringBuffer condition = new StringBuffer("where elseEntry.fid in(");
		while(it.hasNext()) {
			condition.append("'"+it.next().toString().trim()+"',");
		}
		condition.append("'')");
		sql.appendSql(" select distinct sellProject.Fname_L2 sellProject, sellProject.fid sellProjectId, --项目\r\n" + 
				"       building.fname_l2 building,  --楼栋\r\n" + 
				"       buildingUnit.Fname_L2 unit,  --单元\r\n" + 
				"       room.fnumber roomNum,   --房间\r\n" + 
				"       moneyDefine.Fname_L2 moneyDefine, --款项类型\r\n" + 
				"       elseEntry.Fappamount apAmount,--应收金额\r\n" + 
				"       elseEntry.FActrevAmount actAmount, --已收金额\r\n" + 
				"       elseEntry.Frefundmentamount returnedAmount, --已退金额\r\n" + 
				"       insteaded.insteadedAmount  insteadedAmount, --已代付金额\r\n" + 
				" elseEntry.fid entryID --其他应付分录id\r\n"+
				"from  t_she_purchaseelsepaylistentry elseEntry\r\n" + 
				"left join t_She_Purchase purchase on elseEntry.fheadid = purchase.fid \r\n" + 
				"left join t_she_room room on purchase.froomid = room.fid\r\n" + 
				"left join t_she_buildingunit buildingUnit on room.fbuildunitid = buildingUnit.Fid\r\n" + 
				"left join t_she_building building on room.fbuildingid = building.fid\r\n" + 
				"left join t_she_sellproject sellProject on building.fsellprojectid = sellProject.Fid\r\n" + 
				"left join t_she_moneydefine moneyDefine on elseEntry.Fmoneydefineid = moneyDefine.Fid\r\n" + 
				"left join \r\n" + 
				"(SELECT \r\n" + 
				"\r\n" + 
				"insteaded.FPurchaseElsePayListEntryID entryID,\r\n" + 
				"Sum(insteaded.FMoneyInsteadCur) insteadedAmount,\r\n" + 
				"insteaded.FState state \r\n" + 
				"FROM T_SHE_InsteadCollectOutBill insteaded\r\n" + 
				"\r\n" + 
				"LEFT OUTER JOIN T_SHE_PurchaseElsePayListEntry elseEntry\r\n" + 
				"ON insteaded.FPurchaseElsePayListEntryID = elseEntry.FID\r\n" + 
				" where insteaded.FState = '4AUDITTED'\r\n" +
				"GROUP BY \r\n" + 
				"insteaded.FPurchaseElsePayListEntryID, insteaded.FState)insteaded\r\n" + 
				"ON elseEntry.FID = insteaded.entryID\r\n" );
				sql.appendSql(condition.toString());
				sql.appendSql(
				" order by sellProject.Fname_L2,building.fname_l2, buildingUnit.Fname_L2,room.fnumber,moneyDefine.Fname_L2");
		return sql;
	}
	/**
	 * 查询并填充表格
	 * @throws BOSException 
	 * @throws SQLException 
	 */
    private void queryAndFill() throws BOSException, SQLException {
    	FDCSQLBuilder sql = getSql();
    	if(sql==null)return;
    	IRowSet set = null;
    	set = sql.executeQuery();
    	fillRsToTable(set);
    }
    /**
     * 填充表格
     * @param set
     * @throws SQLException 
     * @throws BOSException 
     */
	private void fillRsToTable(IRowSet set) throws SQLException, BOSException{
		if(set==null)return;
//		if(set.ge)
			tblMain.checkParsed(true);
		
		while(set.next()) {
			
			String sellProject = set.getString("sellProject");  //项目
			String building = set.getString("building");  //楼栋
			String unit = set.getString("unit");   //单元
			String roomNum = set.getString("roomNum");   //房间
			String moneyDefine = set.getString("moneyDefine");   //款项类型
			
			BigDecimal apAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("apAmount")!=null){
				apAmount = set.getBigDecimal("apAmount");  //应收金额
			}
			
			BigDecimal actAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("actAmount")!=null){
				actAmount = set.getBigDecimal("actAmount");  //应收金额
			}
			
			
			BigDecimal returnedAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("returnedAmount")!=null){
				returnedAmount = set.getBigDecimal("returnedAmount");  //应收金额
			}
			
			
			BigDecimal insteadedAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("insteadedAmount")!=null){
				insteadedAmount = set.getBigDecimal("insteadedAmount");  //应收金额
			}
		
			/**
			 * 添加项目id
			 */
			String sellProjectId =  "";
			if(set.getString("sellProjectId")!=null || !"".equals(set.getString("sellProjectId"))){
				sellProjectId = set.getString("sellProjectId");
			}
			String entryID = set.getString("entryID");
			IRow row = this.tblMain.addRow();
			row.getCell(1).setValue(sellProject);
			row.getCell(2).setValue(building);
			row.getCell(3).setValue(unit);
			row.getCell(4).setValue(roomNum);
			row.getCell(5).setValue(moneyDefine);
			row.getCell(6).setValue(apAmount);
			row.getCell(7).setValue(actAmount);
			row.getCell(8).setValue(returnedAmount);
			row.getCell(9).setValue(insteadedAmount);
			row.getCell("entryID").setValue(entryID);
			row.getCell("sellProjectId").setValue(sellProjectId);
			BigDecimal allInsteadedAmount = new BigDecimal( getTrasferedCount(entryID));//所有已代付的金额，包括未审批的
			if(returnedAmount==null) {//无退款 本次代付=应收金额-代付金额
				row.getCell("curInsteadAmount").setValue(apAmount.subtract(allInsteadedAmount));
			}else {//有退款  本次代付=应收金额-已退金额-代付金额
				row.getCell("curInsteadAmount").setValue(apAmount.subtract(allInsteadedAmount).subtract(returnedAmount));
			}
		}
		
	}

    /**
     * 获取某个认购单其它应付分录的所有转出单
     * @return
     * @throws BOSException
     */
    private InsteadCollectOutBillCollection getAllInsteadBill(String entryID) throws BOSException{
    	InsteadCollectOutBillCollection collection = null;
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("purchaseElsePayListEntry.id",entryID,CompareType.EQUALS));
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("*"));
    	evi.setSelector(sic);
    	evi.setFilter(filter);
    	collection = InsteadCollectOutBillFactory.getRemoteInstance().getInsteadCollectOutBillCollection(evi);
    	return collection;
    }
    /**
     * 求所有已转出的费用
     * @throws BOSException 
     */
    private float getTrasferedCount(String entryID) throws BOSException{
    	float transferedCount = 0.0f;
    	InsteadCollectOutBillCollection bills = getAllInsteadBill(entryID);
    	InsteadCollectOutBillInfo bill = null;
    	float account = 0.0f;
    	for(int i = 0;i<bills.size();i++){
    		bill = bills.get(i);
    		account = account + bill.getMoneyInsteadCur().floatValue();
    	}
    	transferedCount = account;
    	return transferedCount;
    }
   
	private void initTable() {
		for(int i=0;i<tblMain.getColumnCount();i++) {
			if(i!=0&&i!=10&&i!=12) {
				tblMain.getColumn(i).getStyleAttributes().setLocked(true);
			}
		}
	}
	/**
	 * 取批量代付的数据
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private Set getRows() throws EASBizException, BOSException {
		Set rows = new HashSet();
		Map map = null;
		IRow row = null;
		for(int i=0;i<tblMain.getBody().size();i++) {
			row = tblMain.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(Color.yellow)) {
				return null;
			}
			//TODO:根据编码规则设置编码
			ICell cell = row.getCell("number");
//			setBillNumber(cell);
			map = new HashMap();
			map.put("number", row.getCell("number").getValue());
			map.put("curAmount", row.getCell("curInsteadAmount").getValue().toString());
			map.put("remark", row.getCell("remark").getValue());
			map.put("elseEntryID", row.getCell("entryID").getValue().toString());
			
			//添加销售项目ID
			if(row.getCell("sellProjectId").getValue()!=null){
				map.put("sellProjectId", row.getCell("sellProjectId").getValue().toString());
			}
			
			rows.add(map);
		}
		return rows;
	}
	
	/**
	 * 批量代付
	 */
	public void actionBatchInstead_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionBatchInstead_actionPerformed(e);
		if(tblMain.getBody().size()==0) {
			MsgBox.showError("没有要处理的数据！");
			return;
		}
		Set rows = getRows();
		if(rows==null) {
			MsgBox.showError("本次代付金额中有不合理的数据，请检查！");
			return;
		}
		PersonInfo personInfo = (PersonInfo) this.prmtPerson.getValue();
		Date bizDate = (Date) this.pkDate.getValue();
		InsteadCollectOutBillFactory.getRemoteInstance().generateNewData(personInfo, bizDate, rows);
		MsgBox.showInfo("批量代付成功！");
		this.uiWindow.close();
	}
	
	
	
	/**
	 * 取消
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionCancel_actionPerformed(e);
		this.uiWindow.close();
	}
	/**
	 * 分录编辑结束事件
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception
	{
		super.tblMain_editStopped(e);
		int rowNum = e.getRowIndex();
		int colNum = e.getColIndex();
		if(colNum != tblMain.getColumnIndex("curInsteadAmount"))return;
		try {
			String value = (String) tblMain.getCell(rowNum, colNum).getValue();
			BigDecimal bigDecimal = new BigDecimal(value==null?"0":value.toString());
			BigDecimal aptAmount = (BigDecimal)tblMain.getCell(rowNum, "shouldCollect").getValue()==null?new BigDecimal(0):(BigDecimal)tblMain.getCell(rowNum, "shouldCollect").getValue();//应收金额
//			BigDecimal insteadedAmount = (BigDecimal)tblMain.getCell(rowNum, "insteadedAmount").getValue()==null?new BigDecimal(0):(BigDecimal)tblMain.getCell(rowNum, "insteadedAmount").getValue();//已代付金额
			BigDecimal returnedAmount = (BigDecimal)tblMain.getCell(rowNum, "returnedAmount").getValue()==null?new BigDecimal(0):(BigDecimal)tblMain.getCell(rowNum, "returnedAmount").getValue();//已退金额
			BigDecimal allInsteadedAmount = new BigDecimal( getTrasferedCount(tblMain.getCell(rowNum, "entryID").getValue().toString()));//所有已代付的金额，包括未审批的
			if(bigDecimal.add(allInsteadedAmount).add(returnedAmount).compareTo(aptAmount)>0) {
				MsgBox.showWarning("本次代付金额不能大于"+aptAmount.subtract(allInsteadedAmount).subtract(returnedAmount));
				tblMain.getCell(rowNum, "curInsteadAmount").setValue(e.getOldValue());
				return;
			}
			if(bigDecimal.compareTo(new BigDecimal(0))>0) {
				tblMain.getRow(rowNum).getStyleAttributes().setBackground(Color.white);
			}else {
				tblMain.getRow(rowNum).getStyleAttributes().setBackground(Color.yellow);
			}
		}catch(Exception e1) {
			tblMain.getCell(rowNum, colNum).setValue(e.getOldValue());
			tblMain.getRow(rowNum).getStyleAttributes().setBackground(Color.yellow);
		}
		
	}
	
	/**
	 * 删除行
	 */
	public void actionBtnRemove_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		super.actionBtnRemove_actionPerformed(e);
		int []rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows == null ||rows.length == 0) {
			MsgBox.showWarning("请选中要删除的行！");
			return;
		}
		
		int rowIndex = -1;
		for(int i = rows.length-1;i>=0;i--) {
			rowIndex = rows[i];
			tblMain.removeRow(rowIndex);
		}
	}
	/**
	 * 根据编码规则设置单据编号
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setBillNumber(ICell cell) throws BOSException, EASBizException {
		if(cell != null && cell.getValue() != null && !cell.getValue().toString().equals("")){
			return;
		}
		String entryID = tblMain.getCell(cell.getRowIndex(), "entryID").getValue().toString();
		InsteadCollectOutBillCollection bills = getAllInsteadBill(entryID);
//		IObjectValue obj = new InsteadCollectOutBillInfo();
		IObjectValue obj = bills.get(0);
		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (iCodingRuleManager.isExist(obj, currentOrgId)) {
			// EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(obj, currentOrgId);
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(obj, currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(obj, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(obj, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(obj, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				cell.setValue(number);
		}
	}
}