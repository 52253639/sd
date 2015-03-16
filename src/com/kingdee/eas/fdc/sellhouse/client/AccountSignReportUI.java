/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.AccountSignReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.report.TimeAccountReportFilterUI;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 *by ....wancheng
 */
public class AccountSignReportUI extends AbstractAccountSignReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccountSignReportUI.class);
    private TreeModel tree;
    private DefaultKingdeeTreeNode resultTreeNode; 
    private boolean isQuery=false;
    public AccountSignReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    public void onLoad() throws Exception {
    	setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tree=(TreeModel) params.getObject("tree");
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
    }
    protected void initTree() throws Exception{
		if((tree==null&&params.getObject("tree")!=null)||(tree!=null&&params.getObject("tree")==null)||(tree!=null&&params.getObject("tree")!=null&&!tree.equals(params.getObject("tree")))||(tree==null&&params.getObject("tree")==null&&!this.isShowing())){
			if(params.getObject("tree")!=null){
				tree=(TreeModel) params.getObject("tree");
				this.treeMain.setModel((TreeModel) params.getObject("tree"));
			}else{
				this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
			}
		    this.treeMain.expandPath(this.treeMain.getSelectionPath());
		}
	}
	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new AccountSignReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return AccountSignReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	/**
	 * 计算两个日期相减得到天数
	 * */
	public static int diffdates(Date date1, Date date2)
    {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return (int)((time2 - time1) / 86400000L);
    }

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		getRowSum();
		tblMain.getColumn("returnPeriod").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof BigDecimal){
					int day = Integer.parseInt(obj.toString());
					if(day>=0&&day<7){
						return PeriodEnum.InnerSeven.getAlias();
					}else if(day>=7&&day<=14){
						return PeriodEnum.SevenToFourteen.getAlias();
					}else if(day>=15&&day<=30){
						return PeriodEnum.FifteenToThirty.getAlias();
					}else if(day>=31&&day<=60){
						return PeriodEnum.thirtyoneToSixty.getAlias();
					}else if(day>60){
						return PeriodEnum.SixtyMore.getAlias();
					}else{
						return "";
					}
				}
				return super.getText(obj);
			}
		});
		tblMain.getColumn("orgUnit").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					return obj.toString().split("_")[3];
				}
				return super.getText(obj);
			}
		});
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
	}
	/**
	 * 从临时表取数
	 * */
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
    	try {
			initTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
        resultTreeNode=treeNode;
    	if(treeNode!=null){
    		Window win = SwingUtilities.getWindowAncestor(this);
            LongTimeDialog dialog = null;
            if(win instanceof Frame){
            	dialog = new LongTimeDialog((Frame)win);
            }else if(win instanceof Dialog){
            	dialog = new LongTimeDialog((Dialog)win);
            }
            if(dialog==null){
            	dialog = new LongTimeDialog(new Frame());
            }
            dialog.setLongTimeTask(new ILongTimeTask() {
                public Object exec()throws Exception{
                	RptParams pp=(RptParams)params.clone();
                	pp.setObject("tree", null);
                	tblMain.setRefresh(false);
                	
                	RptParams resultRpt= getRemoteInstance().query(pp);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	tblMain.setRefresh(false);
                	
                	RptParams pp=(RptParams)params.clone();
                	pp.setObject("tree", null);
                	
                	RptParams rpt = getRemoteInstance().createTempTable(pp);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, tblMain);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
        	        KDTableUtil.insertRows(rs, 0, tblMain);
                }
            }
            );
            dialog.show();
    	}else{
			try {
				RptParams pp = (RptParams)params.clone();
				pp.setObject("tree", null);
	        	
	        	RptParams rpt = getRemoteInstance().createTempTable(pp);
	            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
	            KDTableUtil.setHeader(header, tblMain);
	            tblMain.setRowCount(0);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	isQuery=false;
	}
	/**
	 * 行总计
	 * */
	private void getRowSum(){
		tblMain.setRowCount(tblMain.getRowCount());
		CRMClientHelper.getFootRow(tblMain, new String[]{"buildArea","contractTotal","revAmount"});
        KDTFootManager footRowManager = tblMain.getFootManager();
        IRow footRow = footRowManager.getFootRow(0);
        if(footRow.getCell("buildArea").getValue()!=null&&((BigDecimal)footRow.getCell("buildArea").getValue()).compareTo(FDCHelper.ZERO)!=0){
        	footRow.getCell("buildPrice").setValue(((BigDecimal)footRow.getCell("contractTotal").getValue()).divide((BigDecimal)footRow.getCell("buildArea").getValue(), BigDecimal.ROUND_HALF_UP,2));
        }
        CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"buildArea","buildPrice","contractTotal","revAmount"});
        CRMClientHelper.fmtDate(tblMain, new String[]{"planSignDate","busAdscriptionDate","lastTime"});
        tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				if(obj instanceof SellProjectInfo){
					params.setObject("psp","'"+SHEManageHelper.getParentSellProject(null, (SellProjectInfo) obj).getId().toString()+"'");
				}else{
					params.setObject("psp",allSpIdStr);
				}
			}else{
				params.setObject("sellProject", null);
				params.setObject("psp",null);
				params.setObject("productType", null);
			}
			query();
		}
    }

    /**
     * 选择一条数据，将id带过去
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex==-1){
    		return;
    	}
	    IRow row   = tblMain.getRow(rowIndex);
		if(rowIndex==-1){
    		return;
    	}
		String type=row.getCell("type").getValue().toString();
	    if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
	    	UIContext uiContext = new UIContext(this);
		    uiContext.put("ID", row.getCell("id").getValue());
		    IUIWindow uiWindow = null;
		    if("认购".equals(type)){
		    	uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseManageEditUI.class.getName(),uiContext,null,OprtState.VIEW);
		    }else{
		    	uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PrePurchaseManageEditUI.class.getName(),uiContext,null,OprtState.VIEW);
		    }
			uiWindow.show();
	    }
    }

    protected ArrayList getSelectedIdValues()
	{
	    ArrayList selectList = new ArrayList();
	    int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
	    for(int i=0;i<selectRows.length;i++){
	    	String id= (String)tblMain.getRow(selectRows[i]).getCell("id").getValue();
	    	selectList.add(id);
	    }
	    return selectList;
	}
    public void actionOverdue_actionPerformed(ActionEvent e) throws Exception
    {
    	SHEManageHelper.checkSelected(this, tblMain);
		List id=getSelectedIdValues();
		if(id.size()>1){
			 FDCMsgBox.showInfo(this,"请选择一条记录!");
	    	 return;
		}
		UIContext uiContext = new UIContext(this);
	    uiContext.put("tranID", id.get(0).toString());
	    IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(OverdueDescribeTwoListUI.class.getName(),uiContext,null,OprtState.ADDNEW);
		uiWindow.show();
    }
	protected Set getSelectSellProjects(DefaultKingdeeTreeNode treeNode) {
		Set projects = new HashSet();
		if(treeNode!=null){
			Enumeration en = treeNode.breadthFirstEnumeration();
			while (en.hasMoreElements()) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) en.nextElement();
				if (child.getUserObject() instanceof SellProjectInfo) {
					projects.add(((SellProjectInfo) child.getUserObject()).getId().toString());
				}
			}
		}
		return projects;
	}
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}
}