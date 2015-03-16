/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceChanceTrackListUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MediaCostAssessmentReportUI extends AbstractMediaCostAssessmentReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MediaCostAssessmentReportUI.class);
    
    private TreeModel tree;
    
    private HashMap map;
    
    /**
     * output class constructor
     */
    public MediaCostAssessmentReportUI() throws Exception
    {
    	
        super();
        kDTable1.checkParsed();
        kDTable1.getDataRequestManager().addDataRequestListener(this);
        kDTable1.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(kDTable1);
        map = new HashMap();
    }

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new MediaCostAssessmentReportFilterUI();
	}

	protected KDTable getTableForPrintSetting() {
		return kDTable1;
	}

	protected void query() {
		int sum_tel = 0;
		int sum_visit = 0;
		int sum_sign = 0;
		BigDecimal sum_contract = new BigDecimal("0");
		BigDecimal sum_contractAmount = new BigDecimal("0");
		kDTable1.checkParsed();
		getTableForPrintSetting().removeRows();
		kDTable1.getTreeColumn().setDepth(2);
		try {
			if(params.getObject("beginDate") != null){
				map.put("beginDate", params.getObject("beginDate"));
			}
			else{
				map.put("beginDate", null);
			}
			if(params.getObject("endDate") != null){
				map.put("endDate", params.getObject("endDate"));
			}
			else{
				map.put("endDate", null);
			}
			Map mapData = MediaCostAssessmentReportFacadeFactory.getRemoteInstance().getDatas(map);
			if(mapData.get("rowSet_cus")!=null){
				String treeName = "";
				IRowSet rowSet = (IRowSet)mapData.get("rowSet_cus");
				while(rowSet.next()){
					IRow row = kDTable1.addRow();
					if(treeName.equals("") || !rowSet.getObject("treeName").toString().equals(treeName)){
						row.setTreeLevel(0);
						row.getStyleAttributes().setBackground(new Color(245,245,245));
						treeName = rowSet.getObject("treeName").toString();
						row.getCell("mediaClassAndName").setValue(treeName);
						row.getStyleAttributes().setFontColor(java.awt.Color.BLUE);
						row = kDTable1.addRow();
						row.setTreeLevel(1);
					}
					else{
						row.setTreeLevel(1);
					}
					String name = rowSet.getObject("channelName").toString();
					row.getCell("mediaClassAndName").setValue(name);
					row.getCell("mediaID").setValue(rowSet.getObject("channelTypeId"));
					if(rowSet.getObject("telCommerceId") != null){
						row.getCell("tel").setValue(Integer.valueOf(rowSet.getObject("telCommerceId").toString()));
					}
					else{
						row.getCell("tel").setValue(Integer.valueOf("0"));
					}
					if(rowSet.getObject("visitCommerceId") != null){
						row.getCell("visit").setValue(Integer.valueOf(rowSet.getObject("visitCommerceId").toString()));
					}
					else{
						row.getCell("visit").setValue(Integer.valueOf("0"));
					}
					if(rowSet.getObject("signManage") != null){
						row.getCell("signCus").setValue(Integer.valueOf(rowSet.getObject("signManage").toString()));
					}
					else{
						row.getCell("signCus").setValue(Integer.valueOf("0"));
					}
					if(rowSet.getObject("tel2visitCommerceId") != null){
						row.getCell("telToVisitPer").setValue(Integer.valueOf(rowSet.getObject("tel2visitCommerceId").toString()));
					}
					else{
						row.getCell("telToVisitPer").setValue(Integer.valueOf("0"));
					}
					
					
					if(mapData.get("rowSet_contract") != null){
						IRowSet rowSet_contract = (IRowSet)mapData.get("rowSet_contract");
						while(rowSet_contract.next()){
							if(!rowSet.getObject("channelTypeId").equals(rowSet_contract.getObject("MediaNameID"))){
								continue;
							}
							if(rowSet_contract.getObject("Contractamount") != null && rowSet_contract.getObject("Cwtamount") != null){
								row.getCell("contractAmount").setValue(new BigDecimal(rowSet_contract.getObject("Contractamount").toString()).add(new BigDecimal(rowSet_contract.getObject("Cwtamount").toString())));
							}
							else if(rowSet_contract.getObject("Contractamount") != null && rowSet_contract.getObject("Cwtamount") == null){
								row.getCell("contractAmount").setValue(new BigDecimal(rowSet_contract.getObject("Contractamount").toString()));
							}
							else if(rowSet_contract.getObject("Contractamount") == null && rowSet_contract.getObject("Cwtamount") != null){
								row.getCell("contractAmount").setValue(new BigDecimal(rowSet_contract.getObject("Cwtamount").toString()));
							}
							else{
								row.getCell("contractAmount").setValue(new BigDecimal("0"));
							}
							row.getCell("marketPayAmount").setValue(rowSet_contract.getObject("ACTPAYLOCAMT")==null?new BigDecimal("0"):new BigDecimal(rowSet_contract.getObject("actPayLocamt").toString()));
							row.getCell("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
						}
						rowSet_contract.beforeFirst();
					}
					if(row.getCell("contractAmount").getValue() == null){
						row.getCell("contractAmount").setValue(new BigDecimal("0"));
					}
					if(row.getCell("marketPayAmount").getValue() == null){
						row.getCell("marketPayAmount").setValue(new BigDecimal("0"));
					}
				}
			}

			KDTFootManager fm = new KDTFootManager(kDTable1);
			fm.addFootView();
			kDTable1.setFootManager(fm);
			IRow row = kDTable1.addFootRow(0);//添加一个Foot行
			row.getStyleAttributes().setBackground(new Color(255,255,204));
			for(int i=0;i<kDTable1.getRowCount();i++){
				IRow rows = kDTable1.getRow(i);
				sum_tel = sum_tel + Integer.parseInt(rows.getCell("tel").getValue()==null?"0":rows.getCell("tel").getValue().toString());
				sum_visit = sum_visit + Integer.parseInt(rows.getCell("visit").getValue()==null?"0":rows.getCell("visit").getValue().toString());
				sum_sign = sum_sign + Integer.parseInt(rows.getCell("signCus").getValue()==null?"0":rows.getCell("signCus").getValue().toString());
				sum_contract = sum_contract.add(new BigDecimal(rows.getCell("marketPayAmount").getValue()==null?"0":rows.getCell("marketPayAmount").getValue().toString()));
				sum_contractAmount = sum_contractAmount.add(new BigDecimal(rows.getCell("contractAmount").getValue()==null?"0":rows.getCell("contractAmount").getValue().toString()));
			}
			row.getCell("mediaClassAndName").setValue("合计");
			row.getCell("tel").setValue(Integer.valueOf(sum_tel));
			row.getCell("visit").setValue(Integer.valueOf(sum_visit));
			row.getCell("signCus").setValue(Integer.valueOf(sum_sign));
			row.getCell("marketPayAmount").setValue(sum_contract);
			row.getCell("contractAmount").setValue(sum_contractAmount);
//			row.getCell("tel").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
//			row.getCell("visit").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
//			row.getCell("signCus").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
//			row.getCell("marketPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//			row.getCell("contractAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			row.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
			row.getCell("mediaClassAndName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("center"));
			
			for(int i=0;i<kDTable1.getRowCount();i++){
				IRow rows = kDTable1.getRow(i);
				if(rows.getCell("tel").getValue() == null){
					continue;
				}
				if(rows.getCell("visit").getValue().toString().equals("0") || sum_visit == 0){
					rows.getCell("visitPer").setValue("0%");
				}
				else{
					rows.getCell("visitPer").setValue(new BigDecimal(rows.getCell("visit").getValue().toString()).divide(new BigDecimal(sum_visit), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)+"%");
				}
				if(rows.getCell("tel").getValue().toString().equals("0") || rows.getCell("telToVisitPer").getValue().toString().equals("0")){
					rows.getCell("telToVisitPer").setValue("0%");
				}
				else{
					rows.getCell("telToVisitPer").setValue(new BigDecimal(rows.getCell("telToVisitPer").getValue().toString()).divide(new BigDecimal(rows.getCell("tel").getValue().toString()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)+"%");
				}
				if(rows.getCell("visit").getValue().toString().equals("0") || rows.getCell("signCus").getValue().toString().equals("0")){
					rows.getCell("TurnoverRate").setValue("0%");
				}
				else{
					rows.getCell("TurnoverRate").setValue(new BigDecimal(rows.getCell("signCus").getValue().toString()).divide(new BigDecimal(rows.getCell("visit").getValue().toString()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)+"%");
				}
				if(rows.getCell("visit").getValue().toString().equals("0") || rows.getCell("marketPayAmount").getValue().toString().equals("0")){
					rows.getCell("visitCusCostRate").setValue(new BigDecimal("0"));
				}
				else{
					rows.getCell("visitCusCostRate").setValue(new BigDecimal(rows.getCell("marketPayAmount").getValue().toString()).divide(new BigDecimal(rows.getCell("visit").getValue().toString()), 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
				}
				if(rows.getCell("signCus").getValue().toString().equals("0") || rows.getCell("marketPayAmount").getValue().toString().equals("0")){
					rows.getCell("signCusCostRate").setValue(new BigDecimal("0"));
				}
				else{
					rows.getCell("signCusCostRate").setValue(new BigDecimal(rows.getCell("marketPayAmount").getValue().toString()).divide(new BigDecimal(rows.getCell("signCus").getValue().toString()), 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
				}
			}
			
			//设置小计数
			sum_tel = 0;
			sum_visit = 0;
			sum_sign = 0;
			sum_contract = new BigDecimal("0");
			sum_contractAmount = new BigDecimal("0");
			for(int i=kDTable1.getRowCount()-1;i>=0;i--){
				IRow rows = kDTable1.getRow(i);
				if(rows.getCell("tel").getValue() == null){
					rows.getCell("tel").setValue(Integer.valueOf(sum_tel));
					rows.getCell("visit").setValue(Integer.valueOf(sum_visit));
					rows.getCell("signCus").setValue(Integer.valueOf(sum_sign));
					rows.getCell("marketPayAmount").setValue(sum_contract);
					rows.getCell("contractAmount").setValue(sum_contractAmount);
//					rows.getCell("tel").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
//					rows.getCell("visit").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
//					rows.getCell("signCus").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
//					if(!String.valueOf(sum_contract).equals("0")){
//						rows.getCell("marketPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					}
//					if(!String.valueOf(sum_contractAmount).equals("0")){
//						rows.getCell("contractAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					}
					rows.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					rows.getCell("mediaClassAndName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("left"));
					sum_tel = 0;
					sum_visit = 0;
					sum_sign = 0;
					sum_contract = new BigDecimal("0");
					sum_contractAmount = new BigDecimal("0");
					continue;
				}
				sum_tel = sum_tel + Integer.parseInt(rows.getCell("tel").getValue()==null?"0":rows.getCell("tel").getValue().toString());
				sum_visit = sum_visit + Integer.parseInt(rows.getCell("visit").getValue()==null?"0":rows.getCell("visit").getValue().toString());
				sum_sign = sum_sign + Integer.parseInt(rows.getCell("signCus").getValue()==null?"0":rows.getCell("signCus").getValue().toString());
				sum_contract = sum_contract.add(new BigDecimal(rows.getCell("marketPayAmount").getValue()==null?"0":rows.getCell("marketPayAmount").getValue().toString()));
				sum_contractAmount = sum_contractAmount.add(new BigDecimal(rows.getCell("contractAmount").getValue()==null?"0":rows.getCell("contractAmount").getValue().toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		kDTable1.getColumn("marketPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kDTable1.getColumn("contractAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kDTable1.getColumn("visitCusCostRate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kDTable1.getColumn("signCusCostRate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kDTable1.getColumn("tel").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		kDTable1.getColumn("visit").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		kDTable1.getColumn("signCus").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
	}
	
	public void tableDataRequest(KDTDataRequestEvent arg0) {
		
	}
    /* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		initTree();
		tree=(TreeModel) params.getObject("tree");
		kDTable1.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		KDTSortManager sortManager = new KDTSortManager(kDTable1);
        sortManager.setSortAuto(false);
        sortManager.setEnableSortable(false);
	}
	
	protected void initTable(){
		kDTable1.getStyleAttributes().setLocked(true);
		kDTable1.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		kDTable1.getDataRequestManager().addDataRequestListener(this);
		kDTable1.getDataRequestManager().setDataRequestMode(1);
        this.kDTable1.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
        enableExportExcel(kDTable1);
        //设置垂直滚动条
        getTableForPrintSetting().setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        //设置水平滚动条
        getTableForPrintSetting().setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
        
		String[] fields=new String[kDTable1.getColumnCount()];
		for(int i=0;i<kDTable1.getColumnCount();i++){
			fields[i]=kDTable1.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(kDTable1,fields);
	}

	protected void initTree() throws Exception{
		if((tree==null&&params.getObject("tree")!=null)||(tree!=null&&params.getObject("tree")==null)||(tree!=null&&params.getObject("tree")!=null&&!tree.equals(params.getObject("tree")))||(tree==null&&params.getObject("tree")==null&&!this.isShowing())){
			if(params.getObject("tree")!=null){
				tree=(TreeModel) params.getObject("tree");
				this.kDTree1.setModel((TreeModel) params.getObject("tree"));
			}else{
				this.kDTree1.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
			}
//			this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel().getRoot());
		}
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
		
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				map.put("sellProject", null);
				params.setObject("sellProject", null);
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				if(allSpIdStr.trim().length()!=0){
					params.setObject("org", allSpIdStr);
				}else{
					params.setObject("org", null);
				}
			}else if(obj instanceof SellProjectInfo){
				map.put("sellProject", treeNode.getUserObject());
				params.setObject("sellProject", treeNode.getUserObject());
			}else if(obj instanceof ProductTypeInfo){
				map.put("sellProject", treeNode.getUserObject());
				params.setObject("sellProject", ((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject());
			}
			AdminOrgUnitInfo admin = SysContext.getSysContext().getCurrentAdminUnit();
			map.put("longNumber", admin.getLongNumber());
			map.put("CU", admin.getId().toString());
			query();
		}
	}

	//来电、来访、来电转来访穿透
	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			Set cctId=new HashSet();
			int rowNum = kDTable1.getSelectManager().getActiveRowIndex();
			String name = kDTable1.getCell(rowNum, 0).getValue().toString();
			if(this.kDTable1.getColumn(e.getColIndex()).getKey().equals("tel")){
				cctId=getTel(name);
			}
			else if(this.kDTable1.getColumn(e.getColIndex()).getKey().equals("visit")){
				cctId=getVisit(name);
			}
			else if(this.kDTable1.getColumn(e.getColIndex()).getKey().equals("signCus")){
				cctId = getSign(name);
			}
			if(cctId.size()>0){
				UIContext uiContext = new UIContext(this);
				uiContext.put("cctId", cctId);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceChanceTrackListUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
	
	private Set getTel(String name) throws SQLException, BOSException{
		Set id=new HashSet();
		FDCSQLBuilder sb = new FDCSQLBuilder();
		sb.appendSql("select t_she_commerceChanceTrack.fid,ct.fname_l2 from t_she_commerceChanceTrack inner join (");
		sb.appendSql("select ccts.fcommerceChanceid fcommerceChanceid,max(ccts.ftrackdate) ftrackdate from t_she_commerceChanceTrack ccts ");
		sb.appendSql("where ccts.finteractionType = 'TELEPHONE' ");
		if(((SellProjectInfo)map.get("sellProject")).get("id")!=null){
			sb.appendSql("and ccts.fsellprojectid = '"+((SellProjectInfo)map.get("sellProject")).get("id")+"' ");
		}
		if(map.get("beginDate")!=null){
    		Date beginDate = (Date)map.get("beginDate");
    		sb.appendSql("and ccts.ftrackDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(map.get("endDate")!=null){
			Date endDate = (Date)map.get("endDate");
			sb.appendSql("and ccts.ftrackDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sb.appendSql("group by ccts.fcommerceChanceid ");
		sb.appendSql(")asps on t_she_commerceChanceTrack.Fcommercechanceid = asps.fcommerceChanceid and t_she_commerceChanceTrack.ftrackdate = asps.ftrackdate ");
		sb.appendSql("left outer join t_she_commerceChance cc on t_she_commerceChanceTrack.Fcommercechanceid = cc.Fid ");
		sb.appendSql("left outer join T_MAR_ChannelType ct on ct.fid = t_she_commerceChanceTrack.Fclassifyid ");
		sb.appendSql("where ct.fname_l2 = '"+name+"' and t_she_commerceChanceTrack.finteractionType = 'TELEPHONE'");
		IRowSet rowSet =sb.executeQuery();
    	while(rowSet.next()){
    		id.add(rowSet.getString("fid"));
    	}
		return id;
	}
	
	private Set getVisit(String name) throws SQLException, BOSException{
		Set id=new HashSet();
		FDCSQLBuilder sb = new FDCSQLBuilder();
		sb.appendSql("select cct.fid,ct.fname_l2 from t_she_commerceChanceTrack cct inner join (");
		sb.appendSql("select ccts.fcommerceChanceid fcommerceChanceid,max(ccts.ftrackdate) ftrackdate from t_she_commerceChanceTrack ccts ");
		sb.appendSql("where ccts.finteractionType = 'INTERVIEW' ");
		if(((SellProjectInfo)map.get("sellProject")).get("id")!=null){
			sb.appendSql("and ccts.fsellprojectid = '"+((SellProjectInfo)map.get("sellProject")).get("id")+"' ");
		}
		if(map.get("beginDate")!=null){
    		Date beginDate = (Date)map.get("beginDate");
    		sb.appendSql("and ccts.ftrackDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(map.get("endDate")!=null){
			Date endDate = (Date)map.get("endDate");
			sb.appendSql("and ccts.ftrackDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sb.appendSql("group by ccts.fcommerceChanceid ");
		sb.appendSql(")asps on cct.Fcommercechanceid = asps.fcommerceChanceid and cct.Ftrackdate = asps.ftrackdate ");
		sb.appendSql("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid ");
		sb.appendSql("left outer join T_MAR_ChannelType ct on ct.fid = cct.Fclassifyid ");
		sb.appendSql("where ct.fname_l2 = '"+name+"' and cct.finteractionType = 'INTERVIEW' ");
		IRowSet rowSet =sb.executeQuery();
    	while(rowSet.next()){
    		id.add(rowSet.getString("fid"));
    	}
		return id;
	}
	
	
	private Set getSign(String name) throws SQLException, BOSException{
		Set id=new HashSet();
		Set id_1=new HashSet();
		FDCSQLBuilder sb = new FDCSQLBuilder();
		sb.appendSql("/*dialect*/select cc.fid from t_she_commerceChance cc ");
		sb.appendSql("left outer join t_she_shecustomer sc on cc.fcustomerid = sc.fid ");
//		sb.appendSql("left outer join T_MAR_ChannelType ct on ct.fid = cc.fclassifyid ");
		sb.appendSql("where cc.fcustomerid in ");
		sb.appendSql("(select sce.fcustomerid from t_she_signmanage sm ");
		sb.appendSql("left outer join T_SHE_SignCustomerEntry sce on sm.fid = sce.fheadid ");
		if(((SellProjectInfo)map.get("sellProject")).get("id")!=null){
    		sb.appendSql("where sce.fismain = '1' and sm.fsellprojectid in ");
    		sb.appendSql("(select fid from T_SHE_sellproject where fid = '"+((SellProjectInfo)map.get("sellProject")).get("id")+"' or fparentid = '"+((SellProjectInfo)map.get("sellProject")).get("id")+"') ");
		}
    	sb.appendSql("and sm.fbizstate in ('SignAudit','SignApple') ");
//    	sb.appendSql("and cc.fclassifyid is not null ");
    	if(map.get("beginDate")!=null){
    		Date beginDate = (Date)map.get("beginDate");
    		sb.appendSql("and sm.fbizdate >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(map.get("endDate")!=null){
			Date endDate = (Date)map.get("endDate");
			sb.appendSql("and sm.fbizdate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sb.appendSql(")");
		IRowSet rowSet =sb.executeQuery();
    	while(rowSet.next()){
    		id.add(rowSet.getString("fid"));
    	}
    	String ids = "";
    	sb = new FDCSQLBuilder();
    	sb.appendSql("/*dialect*/select cct.fid from t_she_commerceChanceTrack cct ");
    	sb.appendSql("left outer join T_MAR_ChannelType ct on ct.fid = cct.Fclassifyid ");
    	
    	sb.appendSql("inner join ( select ccts.fcommercechanceid fcommercechanceid,max(ccts.ftrackdate) trackDate ");
    	sb.appendSql("from t_she_commerceChanceTrack ccts where ccts.FTrackEvent = 'Sign' group by ccts.fcommercechanceid ");
    	sb.appendSql(") css on cct.fcommercechanceid = css.fcommercechanceid and cct.ftrackdate = css.trackDate ");
    	
    	sb.appendSql("where cct.fcommerceChanceid in (");
    	if(id.size() > 0){
    		Iterator it = id.iterator();
    		while(it.hasNext()){
    			ids = ids + "'"+it.next().toString()+"',";
    		}
    		if(ids.length() > 1){
    			sb.appendSql(ids.substring(0, ids.length()-1));
    		}
    	}
    	sb.appendSql(") and ct.fname_l2 = '"+name+"' and cct.FTrackEvent = 'Sign' and cct.fclassifyid is not null ");
    	if(map.get("beginDate")!=null){
    		Date beginDate = (Date)map.get("beginDate");
    		sb.appendSql("and cct.ftrackDate >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(map.get("endDate")!=null){
			Date endDate = (Date)map.get("endDate");
			sb.appendSql("and cct.ftrackDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
    	rowSet =sb.executeQuery();
    	while(rowSet.next()){
    		id_1.add(rowSet.getString("fid"));
    	}
		return id_1;
	}
}