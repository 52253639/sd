/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.market.HouseAnlysisCollection;
import com.kingdee.eas.fdc.market.HouseAnlysisFactory;
import com.kingdee.eas.fdc.market.HouseAnlysisInfo;
import com.kingdee.eas.fdc.market.IHouseAnlysis;
import com.kingdee.eas.fdc.market.MarketSurveyCollection;
import com.kingdee.eas.fdc.market.MarketSurveyFactory;
import com.kingdee.eas.fdc.market.MarketSurveyInfo;
import com.kingdee.eas.hr.rec.util.DateUtil;
import com.kingdee.eas.hr.train.MonthEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 市场调查管理
 * @author yinshujuan
 *
 */
public class MarketSurveyEditUI extends AbstractMarketSurveyEditUI {
	private static final long serialVersionUID = 7783354830145590284L;
	private static final Logger logger = CoreUIObject.getLogger(MarketSurveyEditUI.class);
	protected com.kingdee.bos.ctrl.swing.KDPanel future;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel entrys = null;
	private List houseAnlysisList = new ArrayList();

	/**
	 * output class constructor
	 */
	public MarketSurveyEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		houseAnlysisList = getAllHouseAnlysis();
		IRow row = null;
		for (int index = 0; index < houseAnlysisList.size(); index++) {
			HouseAnlysisInfo info = (HouseAnlysisInfo) houseAnlysisList
					.get(index);
			createHouseAnlysisPanel(info);
			row = kDTable1.addRow();
			row.getCell("houseColumn").setValue(info);
		}
		showCollectedData();
		lockEntrys();
		initEntrys();
		kdtEntrys_detailPanel.setTitle("调查结果");
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		this.actionAuditResult.setVisible(false);
		handleCodingRule();
		initOldData(this.editData);
	}
	protected void initOldData(IObjectValue dataObject) {
		//super.initOldData(dataObject);
	}
	  private void setAddCtrl() {
	    	String ctrlOrg=com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentAdminUnit().getId().toString();
	    	String cuid=OrgConstants.DEF_CU_ID;
	    	if(ctrlOrg.compareTo(cuid)!=0) {
	    		MsgBox.showError("在当前组织下不能进行该操作！");
	    		SysUtil.abort();
	    	}
	    }
	
	/**
	 * 设置分录某些列不能编辑
	 */
	private void lockEntrys(){
		kdtEntrys.getColumn("dateMonth").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("houseType").getStyleAttributes().setLocked(true);
		kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
		kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
		kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
		
	}
	/**
	 * 初始化分录
	 */
	private void initEntrys(){
		if(this.oprtState.equals(OprtState.ADDNEW)){
			String year = this.txtSurveyYear.getText();
			String month = this.surveyMonth.getSelectedItem().toString();
			if(!year.trim().equals("") && !month.trim().equals("")){
				this.dealEntrys(year, month);
			}
			
		}
	}

	/**
	 * 获取所有的房屋分类
	 * 
	 * @return
	 */
	private List getAllHouseAnlysis() {

		try {
			IHouseAnlysis instence = HouseAnlysisFactory.getRemoteInstance();

			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			HouseAnlysisCollection collection = instence
					.getHouseAnlysisCollection(view);
			for (int i = 0; i < collection.size(); i++) {
				HouseAnlysisInfo info = collection.get(i);
				houseAnlysisList.add(info);
			}
			logger.info("房屋分类的数量为" + houseAnlysisList.size());
		} catch (BOSException e) {
			MsgBox.showWarning("获取房屋分类失败！");
			SysUtil.abort(e);
		}
		return houseAnlysisList;
	}
	
	
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		if(houseAnlysisList == null || houseAnlysisList.size() ==0 ){
			return;
		}
//		下面语句作用是只显示houseAnlysisList中的第一条数据。在此之前要判断houseAnlysisList是否为空、是否有数据。
		filterEntryTable((HouseAnlysisInfo) houseAnlysisList.get(0));    
	}

	/**
	 * 创建房屋pane
	 * 
	 * @param info
	 */
	private void createHouseAnlysisPanel(HouseAnlysisInfo info) {
		KDPanel myPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
		myPanel.setUserObject(info);
		myPanel.getUserObject();
		myPanel.setName(info.getName());

		kDTabbedPane1.add(myPanel, info.getName());
	}

	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {

		KDPanel panel = (KDPanel) this.kDTabbedPane1.getSelectedComponent();
		panel.setLayout(new BorderLayout());


		//
		for (int index = 0; index < houseAnlysisList.size(); index++) {
			HouseAnlysisInfo info = (HouseAnlysisInfo) houseAnlysisList
					.get(index);
			if (info.equals(panel.getUserObject())) {
				filterEntryTable(info);

				panel.add(this.kdtEntrys.getParent().getParent());
				break;
			} else
				continue;
		}
	}

	/**
	 * 过滤分录
	 * 
	 * @param info
	 */
	private void filterEntryTable(HouseAnlysisInfo info) {

		if (!(kdtEntrys.getBody().size() > 0))
			return;

		for (int i = 0; i < kdtEntrys.getBody().size(); i++) {
			IRow row = kdtEntrys.getRow(i);
			HouseAnlysisInfo typeInRow = (HouseAnlysisInfo) row.getCell(
					"houseType").getValue();
		

			if (info.getName().equalsIgnoreCase(typeInRow.getName()))
				row.getStyleAttributes().setHided(false); //显示分录
			else
				row.getStyleAttributes().setHided(true);  //隐藏分录
		}
	}

	protected void doAfterSave(IObjectPK pk) throws Exception {
		super.doAfterSave(pk);

		if (this.kDTabbedPane1.getSelectedComponent() != null) {
			KDPanel panel = (KDPanel) this.kDTabbedPane1.getSelectedComponent();
			HouseAnlysisInfo info = (HouseAnlysisInfo) panel.getUserObject();
			filterEntryTable(info);
		}
	}
	
	/**
	 * 分录发生改变
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		int colNum=e.getColIndex();
		int rowNum=e.getRowIndex();
		
		int plateAreaNum = kdtEntrys.getColumnIndex("plateArea");  //放盘面积
		int saleAreaNum = kdtEntrys.getColumnIndex("salesArea");   //销售面积
		int salesNumNum = kdtEntrys.getColumnIndex("salesNum");  //销售数量
		int salesMoneyNum = kdtEntrys.getColumnIndex("salesMoney");  //销售金额
		if(colNum != plateAreaNum && colNum != saleAreaNum && colNum != salesNumNum && colNum != salesMoneyNum){
			return;
		}
		HouseAnlysisInfo info=null;
		if (this.kDTabbedPane1.getSelectedComponent() != null) {
			KDPanel panel = (KDPanel) this.kDTabbedPane1.getSelectedComponent();
			info = (HouseAnlysisInfo) panel.getUserObject(); //获取显示的房屋分类
		}
		HouseAnlysisInfo info1 = null;
		float collectValue = 0;     //汇总值
		for(int i = 0;i<kdtEntrys.getRowCount();i++){
			info1 = (HouseAnlysisInfo)kdtEntrys.getCell(i, "houseType").getValue();
			if(info.getName().equals(info1.getName())){
				if(kdtEntrys.getCell(i, colNum).getValue() != null){
					collectValue = collectValue + new Float(kdtEntrys.getCell(i, colNum).getValue().toString()).floatValue();
				}
			}
		}
		for(int k = 0;k<kDTable1.getRowCount();k++){
			if(kDTable1.getCell(k, "houseColumn").getValue().toString().trim().equalsIgnoreCase(info.getName().trim())){
				if(colNum == plateAreaNum){
					kDTable1.getCell(k, "plateArea").setValue(new Float(collectValue));
				}else if(colNum == saleAreaNum){
					kDTable1.getCell(k, "salesArea").setValue(new Float(collectValue));
				}else if(colNum == salesNumNum){
					kDTable1.getCell(k, "salesNum").setValue(new Float(collectValue));
				}else if(colNum == salesMoneyNum){
					kDTable1.getCell(k, "salesMoney").setValue(new Float(collectValue));
				}
			}
		}
	}

	/**
	 * 显示汇总的数据
	 */
	private void showCollectedData(){
		if(kdtEntrys == null)
			return;
		HouseAnlysisInfo info= null;
		HouseAnlysisInfo info1 = null;
		float plateAreas = 0;      //放盘面积
		float saleAreas = 0;      //销售面积
		int saleNums = 0;        //销售数量
		float saleMoneys = 0;        //销售金额
		BigDecimal plateAreaBig = null;
		BigDecimal saleAreaBig = null;
		Integer saleNumBig = null;
		BigDecimal saleMoneyBig = null;
		for(int i = 0;i<houseAnlysisList.size();i++){
			info = (HouseAnlysisInfo)houseAnlysisList.get(i);
			
			for(int j = 0;j<kdtEntrys.getRowCount();j++){
				info1 = (HouseAnlysisInfo)kdtEntrys.getCell(j, "houseType").getValue();
				if(info1 != null && info1.getId().toString().equals(info.getId().toString())){
					plateAreaBig = (BigDecimal) kdtEntrys.getCell(j, "plateArea").getValue();
					if(plateAreaBig != null){
						plateAreas = plateAreas + plateAreaBig.floatValue();
					}
					saleAreaBig = (BigDecimal) kdtEntrys.getCell(j, "salesArea").getValue();
					if(saleAreaBig != null){
						saleAreas = saleAreas + saleAreaBig.floatValue();
					}
					saleNumBig = (Integer)kdtEntrys.getCell(j, "salesNum").getValue();
					if(saleNumBig != null){
						saleNums = saleNums + saleNumBig.intValue();
					}
					saleMoneyBig = (BigDecimal) kdtEntrys.getCell(j, "salesMoney").getValue();
					if(saleMoneyBig != null){
						saleMoneys = saleMoneys + saleMoneyBig.floatValue();
					}
				}
			}
			
			for(int k = 0;k<kDTable1.getRowCount();k++){
				if(kDTable1.getCell(k, "houseColumn").getValue().toString().trim().equalsIgnoreCase(info.getName().trim())){
					kDTable1.getCell(k, "plateArea").setValue(new BigDecimal(plateAreas));
					kDTable1.getCell(k, "salesArea").setValue(new BigDecimal(saleAreas));
					kDTable1.getCell(k, "salesNum").setValue(new BigDecimal(saleNums));
					kDTable1.getCell(k, "salesMoney").setValue(new BigDecimal(saleMoneys));
					break;
				}
			}
			plateAreas = 0;
			saleAreas = 0;
			saleNums = 0;
			saleMoneys = 0;
		}
	}

	/**
	 * 判断该省，市，（县）的该年，月份的调查单已经存在
	 * @throws BOSException 
	 */
	private boolean isHasEqualsBill() throws BOSException{
		String year = this.txtSurveyYear.getText().trim();
		String month = this.surveyMonth.getSelectedItem().toString();
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("surveyYear",year,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("surveyMonth",month,CompareType.EQUALS));
		
		ProvinceInfo province = (ProvinceInfo)this.prmtprovince.getValue();
		BOSUuid provinceID = province.getId();
		filter.getFilterItems().add(new FilterItemInfo("province.id",provinceID,CompareType.EQUALS));
		
		CityInfo city = (CityInfo)this.prmtarea.getValue();
		if(city != null){
			BOSUuid cityID = city.getId();
			filter.getFilterItems().add(new FilterItemInfo("area.id",cityID,CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("area.id",null,CompareType.EQUALS));   //市为空的情况
		}
		
		RegionInfo region = (RegionInfo)this.prmtRegion.getValue();
		if(region != null){
			BOSUuid regionID = region.getId();
			filter.getFilterItems().add(new FilterItemInfo("region.id",regionID,CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("region.id",null,CompareType.EQUALS));          //县为空的情况
		}
		
		evi.setFilter(filter);
		/*MarketSurveyCollection collection = MarketSurveyFactory.getRemoteInstance().getMarketSurveyCollection(evi);
		if(collection != null && collection.size()>0){
			MarketSurveyInfo info = (MarketSurveyInfo)collection.get(0);
			if(this.txtNumber.getText() != null){
				if(!info.getNumber().trim().equalsIgnoreCase(this.txtNumber.getText().trim())){
					return true;
				}
			}
		}*/
		return false;
	}
	
	
	

	private boolean isHasNull() throws Exception {
		// TODO Auto-generated method stub
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		IObjectValue objValue = this.editData;
		if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
			if(this.txtNumber.getText().trim().equals("")){
				MsgBox.showWarning("调查编码不能为空！");
				this.txtNumber.grabFocus();
				return true;
			}
		}
		if(this.txtsurveyName.getText().trim().equals("")){
			MsgBox.showWarning("调查名称不能为空！");
			this.txtsurveyName.grabFocus();
			return true;
		}
		return false;
	}


	/**
	 * 调查月份发生改变
	 */
	protected void surveyMonth_itemStateChanged(ItemEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.surveyMonth_itemStateChanged(e);
		String year = this.txtSurveyYear.getText();
		if(!year.trim().equals("")){
			String month = this.surveyMonth.getSelectedItem().toString().trim();
			kdtEntrys.removeRows();
			dealEntrys(year,month);
			dealAccountEntry();
		}
	}
	/**
	 * 调查年份发生改变
	 */
	protected void txtSurveyYear_focusLost(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtSurveyYear_focusLost(e);
		String year = this.txtSurveyYear.getText();
		if(year.trim().equals("")){
			kdtEntrys.removeRows();
			dealAccountEntry();
			return;
		}
		if( year.trim().length() != 4){
			MsgBox.showWarning("请输入格式为“YYYY”的四位数字年份！");
			txtSurveyYear.setText("");
			txtSurveyYear.grabFocus();
			return;
		}
		if(isIlligleChar(year.trim())){
			MsgBox.showWarning("请输入格式为“YYYY”的四位数字年份！");
			txtSurveyYear.setText("");
			txtSurveyYear.grabFocus();
			return;
		}
		if(!year.trim().equals("")){
			String month = this.surveyMonth.getSelectedItem().toString().trim();
			kdtEntrys.removeRows();
			dealEntrys(year,month);
			dealAccountEntry();
		}
	}
	/**
	 * 判断所属入的年份中是否存在非法字符
	 * @param year
	 * @return
	 */
	private boolean isIlligleChar(String year){
		char ch = ' ';
		for(int i = 0;i<year.length();i++){
			ch = year.charAt(i);
			if(ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' ||
					ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9'){
				continue;
			}else{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 处理分录，产生一个月的分录
	 * @param year
	 * @param month
	 */
	private void dealEntrys(String year,String month){
		Calendar  cal=Calendar.getInstance();  
		if(year != null && !year.trim().equals("")){
			cal.set(Calendar.YEAR, Integer.parseInt(year));
		}
		if(month != null && !month.trim().equals("")){
			cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
		}
	    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    IRow row = null;
		for(int i = 0;i<houseAnlysisList.size();i++){
			for(int j = 1;j<=days;j++){
				cal.set(Calendar.DATE, j);
				row = kdtEntrys.addRow();
				row.getCell("dateMonth").setValue(cal.getTime());
				row.getCell("houseType").setValue(houseAnlysisList.get(i));
			}
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
		}
	}
	/**
	 * 出来汇总分录
	 */
	private void dealAccountEntry(){
		IRow row = null;
		for(int i =0;i<kDTable1.getRowCount();i++){
			row = kDTable1.getRow(i);
			row.getCell("plateArea").setValue("0.00");
			row.getCell("salesArea").setValue("0.00");
			row.getCell("salesNum").setValue("0");
			row.getCell("salesMoney").setValue("0.00");
		}
	}
	
	
	

	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		this.prmtRegion.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				// TODO Auto-generated method stub
				prmtRegion_willShow(e);
			}
			
		});
	}
	/**
	 * 过滤县，只能选择市下的县
	 * @param e
	 */
	private void prmtRegion_willShow(SelectorEvent e){
		prmtRegion.setRefresh(true);
		CityInfo city = (CityInfo)this.prmtarea.getValue();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(city == null){
			e.setCanceled(true);
			return;
		}
		BOSUuid cityID = city.getId();
		filter.getFilterItems().add(new FilterItemInfo("city.id",cityID,CompareType.EQUALS));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		evi.setFilter(filter);
		prmtRegion.setEntityViewInfo(evi);
		prmtRegion.getQueryAgent().resetRuntimeEntityView();
	
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {

		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(isHasNull()){
			return;
		}
		if(this.oprtState.equals(OprtState.ADDNEW) && this.isHasEqualsBill()){
			MsgBox.showWarning("该省份地区的"+this.txtSurveyYear.getText()+"年"+this.surveyMonth.getSelectedItem().toString()+"月的调查单已经存在，无需再次创建！");
			return;
		}
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(isHasNull()){
			return;
		}
		if(this.oprtState.equals(OprtState.ADDNEW) && this.isHasEqualsBill()){
			MsgBox.showWarning("该省份地区的"+this.txtSurveyYear.getText()+"年"+this.surveyMonth.getSelectedItem().toString()+"月的调查单已经存在，无需再次创建");
			return;
		}
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		setAddCtrl();
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		String nodeType = (String)this.getUIContext().get("nodeType");
		if(nodeType==null){
			MsgBox.showWarning("请选择市场调查管理所在的市！");
    		return;
		}
		super.actionAddNew_actionPerformed(e);
		initEntrys();
		if(houseAnlysisList.size()>0){
			filterEntryTable((HouseAnlysisInfo) houseAnlysisList.get(0));
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.market.MarketSurveyFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.MarketSurveyInfo objectValue = new com.kingdee.eas.fdc.market.MarketSurveyInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		objectValue.setSurveyPerson((UserInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser());
		String yearValue = new Integer(DateUtil.getYear(DateUtil.getCurrentDate())).toString();
		objectValue.setSurveyYear(Integer.parseInt(yearValue));      //初始化调查年份
		String monthValue = new Integer(DateUtil.getCurrentDate().getMonth()+1).toString();   
		objectValue.setSurveyMonth(MonthEnum.getEnum(monthValue));//初始化调查月份
		objectValue.setBizDate(DateUtil.getCurrentDate());
		ProvinceInfo province = (ProvinceInfo) this.getUIContext().get("province");   
		CityInfo city = null;
		RegionInfo region = null;
		objectValue.setProvince(province);   //初始化省
		
		String nodeType = (String)this.getUIContext().get("nodeType");
		if(nodeType != null){
			if(nodeType.trim().equals("region")){      //如果list界面选择的树节点是县
				city = (CityInfo)this.getUIContext().get("city");
				objectValue.setArea(city);    //初始化市
				region = (RegionInfo) this.getUIContext().get("region");
				objectValue.setRegion(region);
			}
			if(nodeType.trim().equals("city")){         //如果list界面选择的树节点是市
				city = (CityInfo) this.getUIContext().get("city");
				objectValue.setArea(city);
			}
			
		}
		return objectValue;
	}
	
	 protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
			String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (!STATUS_ADDNEW.equals(this.oprtState)) {
				return;
			}
			boolean isExist = true;
			IObjectValue objValue = this.editData;
			if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
				currentOrgId = FDCClientHelper.getCurrentOrgId();
				if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
					isExist = false;
				}
			}

			if (isExist) {
				boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
				if (isAddView) {
					getNumberByCodingRule(objValue, currentOrgId);
				} else {
					String number = null;
					if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
						if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
							CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
							Object object = null;
							if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
								intermilNOF7.show();
								object = intermilNOF7.getData();
							}
							if (object != null) {
								number = object.toString();
								prepareNumber(objValue, number);
							}
						}
					}
					getNumberCtrl().setText(number);
				}
				setNumberTextEnabled();
			}
		}
	    
	    public void setNumberTextEnabled(){
			if (getNumberCtrl() != null) {
				OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
				if (org != null) {
					boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, org.getId().toString());

					getNumberCtrl().setEnabled(isAllowModify);
					getNumberCtrl().setEditable(isAllowModify);
					getNumberCtrl().setRequired(isAllowModify);
				}
			}
		}
		
		protected KDTextField getNumberCtrl() {
			return this.txtNumber;
		}
		
		
		 //通过编码规则获取编码。子类可用。
	    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
	        try {
	            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	            if (orgId == null || orgId.trim().length() == 0)
	            {
//	              当前用户所属组织不存在时，缺省实现是用集团的
	                 orgId = OrgConstants.DEF_CU_ID;
	            }
	            if (iCodingRuleManager.isExist(caller, orgId))
	            {
	                String number = "";
	                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
	                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
	                    if (iCodingRuleManager.isUserSelect(caller, orgId))
	                    {
	                        // 启用了断号支持功能,同时启用了用户选择断号功能
	                        // KDBizPromptBox pb = new KDBizPromptBox();
	                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
	                                caller, orgId, null, null);
	                        // pb.setSelector(intermilNOF7);
	                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
	                        Object object = null;
	                        if (iCodingRuleManager.isDHExist(caller, orgId))
	                        {
	                            intermilNOF7.show();
	                            object = intermilNOF7.getData();
	                        }
	                        if (object != null)
	                        {
	                            number = object.toString();
	                        }
	                        else
	                        {
	                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
	                            number = iCodingRuleManager
	                                    .getNumber(caller, orgId);
	                        }
	                    }
	                    else
	                    {
	                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
	                        number = iCodingRuleManager.readNumber(caller, orgId);
	                    }
	                }
	                else
	                {
	                    // 没有启用断号支持功能，此时获取了编码规则产生的编码
	                    number = iCodingRuleManager.getNumber(caller, orgId);
	                }

	                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
	                prepareNumber(caller, number);
	                if (iCodingRuleManager.isModifiable(caller, orgId))
	                {
	                    //如果启用了用户可修改
	                    setNumberTextEnabled();
	                }
	                return;
	            }
	           
	        } catch (Exception err) {
	            //获取编码规则出错，现可以手工输入编码！
	            handleCodingRuleError(err);

	            //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
	            setNumberTextEnabled();
	        }

	        //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
	        setNumberTextEnabled();
	    }

	    protected void prepareNumber(IObjectValue caller, String number) {
			super.prepareNumber(caller, number);
			getNumberCtrl().setText(number);
		}


}