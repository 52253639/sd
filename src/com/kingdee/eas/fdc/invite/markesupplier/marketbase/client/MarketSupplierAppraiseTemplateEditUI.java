/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.uitl.TableHelper;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;

/**
 * output class name
 */
public class MarketSupplierAppraiseTemplateEditUI extends
		AbstractMarketSupplierAppraiseTemplateEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MarketSupplierAppraiseTemplateEditUI.class);
	 /*
     * 评审维度
     */
    private final String TYPE_COL = "Accreditationwd";
    /*
     * 评审指标
     */
    private final String NAME_COL = "IndexName";
    /*
     * 权重
     */
    private final String WEIGHT_COL = "qz";
    /*
     * 满分标准
     */
    private final String DESC_COL = "threeStandard";
    /*
     * 状态序列号
     */
    private int states = 0;
	/**
	 * output class constructor
	 */
	public MarketSupplierAppraiseTemplateEditUI() throws Exception {
		super();
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		TableHelper.mergeThemeRow(kdtE1, "Accreditationwd", 1);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		prmttempType.setEnabled(false);
		super.onLoad();
		setFootRowSum();
		btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		btnSave.setText("保存");
		btnSave.setToolTipText("保存");
		btnSubmit.setToolTipText("提交");
		btnSubmit.setText("提交");
		btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		contauditPerson.setVisible(false);
		contauditDate.setVisible(false);
		contstate.setVisible(false);
		kDLabelContainer4.setVisible(false);
		kDLabelContainer3.setVisible(false);
		chkisEnable.setVisible(false);
		kdtE1_detailPanel.setTitle("指标分录");
		menuBar.setVisible(false);
		chkisEnable.setVisible(false);
		actionAddNew.setVisible(false);
		actionCopy.setVisible(false);
		actionCancel.setVisible(false);
		actionEdit.setVisible(false);
		actionRemove.setVisible(false);
		actionCancelCancel.setVisible(false);
		this.actionFirst.setVisible(false);// 第一
		this.actionPre.setVisible(false);// 前一
		this.actionNext.setVisible(false);// 后一
		this.actionLast.setVisible(false);// 最后一个
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filinfo = new FilterInfo();
		filinfo.getFilterItems().add(new FilterItemInfo("isEnable", "1"));
		view.setFilter(filinfo);
		this.prmtAccreditationType.setEntityViewInfo(view);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.prmtAccreditationType.setRequired(true);
		this.kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		this.kdtE1.getColumn("Accreditationwd").getStyleAttributes().setLocked(true);
		this.kdtE1.getColumn("threeStandard").getStyleAttributes().setLocked(true);
		this.kdtE1.getColumn("IndexDesc").getStyleAttributes().setLocked(true);
		this.kdtE1.getColumn("threeStandard").getStyleAttributes().setWrapText(true);
	}

	public void onShow() throws Exception {
		super.onShow();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtAccreditationType);
		int count = kdtE1.getRowCount();
		boolean flse = false;
		if(count <0){
			return;
		}else{
			for(int i =0;i<count;i++){
				if(UIRuleUtil.isNull(kdtE1.getRow(i).getCell(NAME_COL).getValue())){
					FDCMsgBox.showWarning(this, getSupplierResources("index")+getSupplierResources("notNull"));
					SysUtil.abort();
				}
				if(UIRuleUtil.isNull(kdtE1.getRow(i).getCell("ScoreType").getValue())){
					FDCMsgBox.showWarning(this, "评分类别不能为空！");
					SysUtil.abort();
				}
				if(AppraiseTypeEnum.WEIGHT.equals(kdtE1.getRow(i).getCell("ScoreType").getValue())){
					flse = true;
				}
			}
		}
		//判断是否有
		if(flse){
			BigDecimal big = getNumWeight();
			if(big.compareTo(new BigDecimal("100"))!=0){
				FDCMsgBox.showWarning(this, "权重之和必须等于100%");
				SysUtil.abort();
			}
		}
		super.verifyInput(e);
	}

	private String getSupplierResources(String key) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.invite.supplier.SupplierResource", key);

	}

	/**
	 * @description 获得所有权重的和
	 * @param
	 * @return BigDecimal
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private BigDecimal getNumWeight() {
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < kdtE1.getRowCount(); ++i) {
			IRow tmpRow = kdtE1.getRow(i);
			if (AppraiseTypeEnum.PASS.equals(tmpRow.getCell("ScoreType")
					.getValue())) {
				continue;
			}
			if (tmpRow.getCell(WEIGHT_COL).getValue() != null) {
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL)
						.getValue().toString());
				sum = sum.add(tmp);
			} else {
				tmpRow.getCell(WEIGHT_COL).setValue(new BigDecimal("1.00"));
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL)
						.getValue().toString());
				sum = sum.add(tmp);
			}
		}
		return sum;
	}

	/**
	 * @description 判断权重是否合格
	 * @author 胥凯
	 * @createDate 2010-11-26
	 * @param String
	 *            int
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void isWeightPoint(String colName, int rowIndex) {
		if (colName.equals(WEIGHT_COL)) {
			if (kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).getValue() != null) {
				BigDecimal tmp = new BigDecimal(kdtE1.getRow(rowIndex).getCell(
						WEIGHT_COL).getValue().toString());
				if (tmp.compareTo(FDCHelper.ZERO) < 0) {
					FDCMsgBox.showWarning(this, getSupplierResources("weight")
							+ getSupplierResources("lessZero"));
					kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(1));
					abort();
				}
				if (tmp.compareTo(new BigDecimal("100")) > 0) {
					FDCMsgBox.showWarning(this, getSupplierResources("weight")
							+ getSupplierResources("moreHundred"));
					kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(1));
					abort();
				}
				int i = setFootRowSum();
				if (i == 1) {
					kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(0));
					setFootRowSum();
				}
			}
		}
	}

	/**
	 * @description F7数据改变时候当评审维度相同合并单元格
	 * @author
	 * @createDate 2010-11-26
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	protected void setColumMerger() {
		kdtE1.checkParsed();
		KDTMergeManager mm = kdtE1.getMergeManager();
		int longth = kdtE1.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for (int i = 0; i < longth; i++) {
			/*
			 * 取得所有的纬度
			 */
			row = kdtE1.getRow(i);
			String type = (String) row.getCell(TYPE_COL).getValue();
			if (map.get(type) == null) {
				map.put(type, Boolean.TRUE);
			}
		}
		Set key = map.keySet();
		Iterator it = key.iterator();
		while (it.hasNext()) {
			String type = (String) it.next();
			int startEnd[] = getStartEnd(-1, -1, type, longth);
			if (startEnd[0] < startEnd[1]) {
				mm.mergeBlock(startEnd[0], 0, startEnd[1], 0,
						KDTMergeManager.SPECIFY_MERGE);
			}
		}
	}

	/**
	 * @description
	 * @author
	 * @createDate 2010-11-26
	 * @param int int String int
	 * @return int []
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private int[] getStartEnd(int ben, int end, String type, int longth) {
		IRow row = null;
		for (int i = 0; i < longth; i++) {
			// 取得所有的纬度
			row = kdtE1.getRow(i);
			String str = (String) row.getCell(TYPE_COL).getValue();
			if (type == null || str == null) {
				continue;
			}
			if (str.equals(type)) {
				if (ben < 0) {
					ben = i;
				} else {
					end = i;
				}
			}
		}
		int obj[] = new int[2];
		obj[0] = ben;
		obj[1] = end;
		return obj;
	}

	private int setFootRowSum() {
		int i = 0;
		IRow footRow = null;
		KDTFootManager footRowManager = kdtE1.getFootManager();
		if (footRowManager == null) {
			String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
			footRowManager = new KDTFootManager(kdtE1);
			footRowManager.addFootView();
			kdtE1.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
			kdtE1.getIndexColumn().setWidthAdjustMode((short) 1);
			kdtE1.getIndexColumn().setWidth(30);
			footRowManager.addIndexText(0, total);
		} else {
			footRow = footRowManager.getFootRow(0);
		}
		footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
		BigDecimal sum = getNumWeight();
		if (sum.compareTo(new BigDecimal("100")) > 0) {
			FDCMsgBox.showWarning(this, getSupplierResources("weight")
					+ getSupplierResources("onTotal")
					+ getSupplierResources("moreHundred"));
			i = 1;
		}
		footRow.getCell(WEIGHT_COL).setValue(sum);
		return i;
	}

	protected void kdtE1_editStopped(KDTEditEvent e) throws Exception {
		super.kdtE1_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if(e.getValue() instanceof MarketSplAuditIndexInfo){
			MarketSplAuditIndexInfo info = (MarketSplAuditIndexInfo)e.getValue();
			this.kdtE1.getRow(rowIndex).getCell("threeStandard").setValue(info.getThreeStandard());
			this.kdtE1.getRow(rowIndex).getCell("IndexDesc").setValue(info.getRemake());
			if(UIRuleUtil.isNotNull(info.getTreeid())){
				MarketSplAuditIndexTreeInfo treeinfo = MarketSplAuditIndexTreeFactory.getRemoteInstance()
				.getMarketSplAuditIndexTreeInfo(new ObjectUuidPK(info.getTreeid().getId()));
				this.kdtE1.getRow(rowIndex).getCell("Accreditationwd").setValue(treeinfo.getName());
			}
		}
		if(AppraiseTypeEnum.PASS.equals(e.getValue())){
			this.kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
			this.kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
		}else{
			this.kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(false);
	    	if(kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
	    		kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
	    	}
		}
		if(UIRuleUtil.isNull(kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).getValue())){
			kdtE1.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
		}
		
		for (int i = 0; i < kdtE1.getRowCount(); i++) {
			if(i==rowIndex){continue;};
			if(UIRuleUtil.isNotNull(kdtE1.getRow(rowIndex).getCell("IndexName").getValue())){
				String indexid = ((MarketSplAuditIndexInfo)kdtE1.getRow(rowIndex).getCell("IndexName").getValue()).getId().toString();
				if(UIRuleUtil.isNotNull(kdtE1.getRow(i).getCell("IndexName").getValue())){
					String lastid = ((MarketSplAuditIndexInfo)kdtE1.getRow(i).getCell("IndexName").getValue()).getId().toString();
					if(indexid.contains(lastid)){
						MsgBox.showWarning("第["+ UIRuleUtil.getIntValue(e.getRowIndex()+1) +"]行与第["+ ++i+"]行指标名称相同，请重新选择指标名称！");
						kdtE1.getRow(rowIndex).getCell("IndexName").setValue(null);
						kdtE1.getRow(rowIndex).getCell("Accreditationwd").setValue(null);
						kdtE1.getRow(rowIndex).getCell("threeStandard").setValue(null);
						kdtE1.getRow(rowIndex).getCell("IndexDesc").setValue(null);
						kdtE1.getRow(rowIndex).getCell("qz").setValue(new BigDecimal(0));
						SysUtil.abort();
					}
				}
			}
		}
//		setColumMerger();
		TableHelper.mergeThemeRow(kdtE1, "Accreditationwd", 1);
    	String colName  = kdtE1.getColumnKey(colIndex);
    	isWeightPoint(colName,rowIndex);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.state.setSelectedItem(FDCBillStateEnum.SAVED);
		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
		this.state.setSelectedItem(FDCBillStateEnum.SUBMITTED);
		super.actionSubmit_actionPerformed(arg0);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateFactory
				.getRemoteInstance();
	}

	/**
	 * output setDataObject method
	 */
	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
		if (STATUS_ADDNEW.equals(getOprtState())) {
			editData
					.put(
							"treeid",
							(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeInfo) getUIContext()
									.get(UIContext.PARENTNODE));
		}
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUser()));
		objectValue
				.setTempType((MarketSupplierAppraiseTemplateTreeInfo) getUIContext()
						.get("group"));
		return objectValue;
	}

}