package com.kingdee.eas.fdc.aimcost.client;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSCell;
import com.kingdee.bos.ctrl.kdf.kds.KDSRow;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class MeasureImporter {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.aimcost.client.MeasureImporter");
	/**
	 * @param args
	 */
	private File file = null;
	private String orgId = null;

	public MeasureImporter(File file, String orgId) {
		this.file = file;
		this.orgId = orgId;
		if (file == null || orgId == null) {
			throw new NullPointerException("file cannt be null!");
		}

	}
	
	/**
	 * 由于要判断需要导入的和现有是否重复，修改了函数的接口，添加了一个参数
	 * List verNumber: 当前组织下所有的版本号
	 */
	private void verityExcel(List verNumber) throws Exception {
		setProcessString("正在进行表头校验...");
		if (book.getSheetCount() < 3) {
			MsgBox.showWarning("无效的导入模板：页签不完整，至少存在“测算汇总表”，“规划指标表“，”六类公摊及期间费“三个页签");
			SysUtil.abort();
		}

		KDSSheet sheet = getSheet(0);
		String sheetName = sheet.getName();
		if (FDCHelper.isEmpty(sheetName)
				|| !sheetName.trim().equalsIgnoreCase("测算汇总表")) 
		{
			MsgBox.showWarning("第一个页签必须为测算汇总表");
			SysUtil.abort();
			
		}
		sheet = getSheet(1);
		sheetName = sheet.getName();
		if (FDCHelper.isEmpty(sheetName)
				|| !sheetName.trim().equalsIgnoreCase("规划指标表")) {
			MsgBox.showWarning("第二个页签必须为规划指标表");
			SysUtil.abort();
		}
		sheet = getSheet(2);
		sheetName = sheet.getName();
		if (FDCHelper.isEmpty(sheetName)
				|| !sheetName.trim().equalsIgnoreCase("六类公摊及期间费")) {	
			MsgBox.showWarning("第三个页签必须为六类公摊及期间费");
			SysUtil.abort();
		}
		// 检查页签名是否有重复
		if (book.getSheetCount() > 3) {
			Set set = new HashSet();
			set.add("测算汇总表");
			set.add("规划指标表");
			set.add("六类公摊及期间费");
			for (int i = 3; i < book.getSheetCount(); i++) {
				if (getSheet(i) == null
						|| FDCHelper.isEmpty(getSheet(i).getName())) {
					continue;
				}
				String sheetName2 = getSheet(i).getName();
				if (set.contains(sheetName2)) {
					throw new Exception("页签“" + sheetName2 + "”重复");
				} else {
					set.add(sheetName2);
				}
			}
			set.clear();
		}

		// 表头检查－测算汇总表
		sheet = getSheet(0);
		Object value = sheet.getCell(0, 0, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("版本号")) {
			handleErr(sheet, 0, 0, "表头“版本号”不存在");
		}
		value = sheet.getCell(0, 1, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("版本名称")) {
			handleErr(sheet, 0, 1, "表头“版本名称”不存在");
		}
		value = sheet.getCell(0, 2, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("项目系列编码")) {
			handleErr(sheet, 0, 2, "表头“项目系列编码”不存在");
		}
		value = sheet.getCell(0, 3, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("工程项目编码")) {
			handleErr(sheet, 0, 3, "表头“工程项目编码”不存在");
		}
		value = sheet.getCell(2, 0, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("科目编码")) {
			handleErr(sheet, 2, 0, "表头“科目编码”不存在");
		}
		value = sheet.getCell(2, 1, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("科目名称")) {
			handleErr(sheet, 2, 1, "表头“科目名称”不存在");
		}
		value = sheet.getCell(2, 2, true).getValue();
		if (FDCHelper.isEmpty(value) || !getString(value).equals("分配方式")) {
			handleErr(sheet, 2, 2, "表头“分配方式”不存在");
		}
		
		//如果该目录下有和要导入数据的组织名称和版本号一样，则报错，避免重复
		String verNo = sheet.getCell(1, 0, true).getValue().toString();
		String oldVerNo=verNo;
		if(verNo.indexOf('.')==-1){
			verNo=verNo+".0";
		}
		BigDecimal tmp = FDCHelper.toBigDecimal(verNo);
		if(tmp.signum()==0){
			handleErr(sheet, 1, 0, "“版本号” "+oldVerNo+" 无效");
		}
		sheet.getCell(1, 0, true).setValue(verNo);
		if(verNumber != null)
		{
			for(int j = 0; j < verNumber.size(); ++j)
			{
				if(verNumber.get(j).toString().equalsIgnoreCase(verNo))
				{
					handleErr(sheet, 1, 0, "“版本号”"+verNo+"已经存在");
				}
			}
		}
		
		//测算汇总表必录项不应该为空，如果为空，应该友好提示
		sheet = getSheet(0);
		
		//如果科目为空则导入失败
		if(sheet.getRowCount() >= 4)
		{
			for(int j = 3; j < sheet.getRowCount();++j)
			{
				value = sheet.getCell(j, 0, true).getValue();
				if(FDCHelper.isEmpty(value))
				{
					handleErr(sheet, j, 0, "科目编码数据不完整");
				}
				
				value = sheet.getCell(j, 1, true).getValue();
				if(FDCHelper.isEmpty(value))
				{
					handleErr(sheet, j, 1, "科目名称数据不完整");
				}
				
				value = sheet.getCell(j, 2, true).getValue();
				if(FDCHelper.isEmpty(value))
				{
					handleErr(sheet, j, 2, "分配方式数据不完整");
				}
			}
		}
		else
		{
			MsgBox.showWarning("测算汇总表没有可导入数据");
			SysUtil.abort();
		}
		
		// TODO 各页签产品之间的勾稽关系检验
		verifyProduct(book);
		// TODO 规划指标表
		verifyPlanIndex(book);

		// 其它页签
		for (int i = 2; i < book.getSheetCount(); i++) {
			sheet = getSheet(i);
			if (sheet.getName() != null && sheet.getName().equals("模板说明")) {
				continue;
			}
			value = sheet.getCell(0, 0, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("科目编码")) {
				handleErr(sheet, 0, 0, "表头“科目编码”不存在");
			}
			value = sheet.getCell(0, 1, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("科目名称")) {
				handleErr(sheet, 0, 1, "表头“科目名称”不存在");
			}
			value = sheet.getCell(0, 2, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("原始指标名称")) {
				handleErr(sheet, 0, 2, "表头“原始指标名称”不存在");
			}
			value = sheet.getCell(0, 3, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("原始指标值")) {
				handleErr(sheet, 0, 3, "表头“原始指标值”不存在");
			}
			value = sheet.getCell(0, 4, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("系数名称")) {
				handleErr(sheet, 0, 4, "表头“系数名称”不存在");
			}
			value = sheet.getCell(0, 5, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("系数值")) {
				handleErr(sheet, 0, 5, "表头“系数值”不存在");
			}
			value = sheet.getCell(0, 6, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("工作量")) {
				handleErr(sheet, 0, 6, "表头“工作量”不存在");
			}
			value = sheet.getCell(0, 7, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("单位")) {
				handleErr(sheet, 0, 7, "表头“单位”不存在");
			}
			value = sheet.getCell(0, 8, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("单价")) {
				handleErr(sheet, 0, 8, "表头“单价”不存在");
			}
			value = sheet.getCell(0, 9, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("合价")) {
				handleErr(sheet, 0, 9, "表头“合价”不存在");
			}
			value = sheet.getCell(0, 10, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("可售单方")) {
				handleErr(sheet, 0, 10, "表头“可售单方”不存在");
			}
			value = sheet.getCell(0, 11, true).getValue();
			if (FDCHelper.isEmpty(value) || !getString(value).equals("描述")) {
				handleErr(sheet, 0, 11, "表头“描述”不存在");
			}

		}
		
		//检查六类公摊及区间费数据完整性
		sheet = getSheet(2);
		if(sheet.getRowCount() > 1)
		{
			for(int j = 1; j < sheet.getRowCount();++j)
			{
				value = sheet.getCell(j, 0, true).getValue();
				if(FDCHelper.isEmpty(value))
				{
					handleErr(sheet, j, 0, "科目编码数据不完整");
				}
				
				value = sheet.getCell(j, 9, true).getValue();
				if(FDCHelper.isEmpty(value))
				{
					handleErr(sheet, j, 9, "合计数据不完整");
				}
			
			}
		}
		else
		{
			MsgBox.showWarning("六类公摊及区间费表没有可导入数据");
			SysUtil.abort();
		}
	}

	private void verifyPlanIndex(KDSBook book) {

	}

	/**
	 * 对模板的产品进行检验：
	 * 1.页签上的产品与汇总表及规化指标表内的产品一致
	 * 2.产品要在系统内存在
	 * @param book
	 */
	private void verifyProduct(KDSBook book) {
		Set sheetProductSet = getSheetProductSet(book);
		Set collectProductSet = new HashSet(sheetProductSet.size());
		KDSSheet sheet = getSheet(0);
		for (int i = 3; i <=sheet.getColumnCount(); i++) {
			KDSCell cell = sheet.getCell(2, i, false);
			if (cell == null || getString(cell.getValue()) == null) {
				continue;
			}
			collectProductSet.add(getString(cell.getValue()));
		}
		boolean isTrue = true;
		if (sheetProductSet.size() == collectProductSet.size()) {
			for (Iterator iter = sheetProductSet.iterator(); iter.hasNext();) {
				String value = (String) iter.next();
				if (!collectProductSet.contains(value)) {
					isTrue = false;
					break;
				}
			}
		} else {
			isTrue = false;
		}
		if (!isTrue) {
			handleErr(sheet, 2, -1, "汇总表内的产品与页签内的不一致\n页签：" + sheetProductSet
					+ "\n汇总表：" + collectProductSet);
		}
		//产品要在规划指标表内存在
		int dynRowBase = 10;
		// 产品列索引
		int productColummnIndex = 1;
		KDSSheet sheetPlanIndex=getSheet(1);
		Set planIndexProductSet=new HashSet();
		for (int i = dynRowBase; i < sheetPlanIndex.getRowCount(); i++) {
			Object value = sheetPlanIndex.getCell(i, productColummnIndex, true)
					.getValue();
			String productName = getString(value);
			if (productName==null||value.equals("小计")||value.equals("合计")) {
				continue;
			}
			planIndexProductSet.add(productName);
		}
		
		for (Iterator iter = sheetProductSet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			if(!planIndexProductSet.contains(number)){
				handleErr(sheetPlanIndex, "如下产品编码对应的产品类型在规划指标表内不存在：" + number);
				break;
			}
		}
		Map productMap = getProductMap();
		String productNumber = "";
		for (Iterator iter = sheetProductSet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			if (productMap.get(number) == null) {
				productNumber = productNumber + number + "、";
			}
		}
		if (productNumber.length() > 1) {
			productNumber = productNumber.substring(0,
					productNumber.length() - 1);
			handleErr(sheet, 2, -1, "如下产品编码对应的产品类型在系统内不存在：" + productNumber);
		}
	}
	/**
	 * 由于要判断需要导入的和现有是否重复，修改了函数的接口，添加了一个参数
	 * List verNumber: 当前组织下所有的版本号
	 */
	public MeasureCostInfo transToMeasure(List verNumber) throws Exception {
		MeasureCostInfo measure = new MeasureCostInfo();
		FullOrgUnitInfo orgUnit = new FullOrgUnitInfo();
		orgUnit.setId(BOSUuid.read(this.orgId));
		measure.setOrgUnit(orgUnit);
		measure.setId(BOSUuid.create(measure.getBOSType()));
		KDSBook book = getBook();
		verityExcel(verNumber);
		fetchData();
		measure.setProject(getProjectInfo());
		measure.setProjectType(getProjectTypeInfo());
		importPlanIndex(measure, getSheet(1));
		importSix(measure, getSheet(2));
		for (int i = 3; i < book.getSheetCount(); i++) {
			importProduct(measure, getSheet(i));
		}
		importMeasureCollect(measure, getSheet(0));
		handleObjectValue(measure);
		return measure;

	}

	private void handleObjectValue(MeasureCostInfo measure) throws BOSException {
		// 设置单位
		HashSet set = new HashSet();
		for (Iterator iter = measure.getCostEntry().iterator(); iter.hasNext();) {
			MeasureEntryInfo entry = (MeasureEntryInfo) iter.next();
			String value = entry.getString("tempUnit");
			if(!FDCHelper.isEmpty(value)){
				set.add(value);
			}
		}
		if (set.size() > 0) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("name", set, CompareType.INCLUDE));
			MeasureUnitCollection c = MeasureUnitFactory.getRemoteInstance()
					.getMeasureUnitCollection(view);
			if (c.size() > 0) {
				HashMap unitMap = new HashMap(c.size());
				for (Iterator iter = c.iterator(); iter.hasNext();) {
					MeasureUnitInfo info = (MeasureUnitInfo) iter.next();
					unitMap.put(info.getName(), info);
				}
				for (Iterator iter = measure.getCostEntry().iterator(); iter
						.hasNext();) {
					MeasureEntryInfo entry = (MeasureEntryInfo) iter.next();
					entry.setUnit((MeasureUnitInfo) unitMap.get(entry
							.getString("tempUnit")));
				}
			}
		}
	}

	private void importMeasureCollect(MeasureCostInfo measure, KDSSheet sheet) {
		setProcessString("正在引入测算汇总表...");
		int maxRow = sheet.getRowCount();
		int maxColumn = sheet.getColumnCount()+1;
		Object value = sheet.getCell(1, 0, true).getValue();
		if (!FDCHelper.isEmpty(value)) {
			measure.setVersionNumber(value.toString().trim());
		}
		value = sheet.getCell(1, 1, true).getValue();
		if (!FDCHelper.isEmpty(value)) {
			measure.setVersionName(value.toString().trim());
		}
		// 初始化产品列
		Map productMap = getProductMap();
		Map map = new HashMap();
		for (int i = 3; i < maxColumn; i++) {
			value = sheet.getCell(2, i, true).getValue();
			Object object = productMap.get(getString(value));
			if (object != null) {
				map.put(new Integer(i), ((ProductTypeInfo) object).getId()
						.toString());
			}
		}
		for (int i = 3; i < maxRow; i++) {
			value = sheet.getCell(i, 0, true).getValue();
			String acctNumber = getString(value);
			value = sheet.getCell(i, 2, true).getValue();
			String splitType = getString(value);
			/*
			 * if(acctNumber==null||splitType==null){ continue; }
			 */
			if (FDCHelper.isEmpty(acctNumber)) {
				handleErr(sheet, i, -1, "成本科目编码不存在");
			}
			MeasureEntryCollection entrys = (MeasureEntryCollection) sixEntryMap
					.get(acctNumber);
			if (entrys == null) {
				continue;
			}
			String number = null;
			if (splitType == null) {
				handleErr(sheet, i, 2, "分摊类型为空");
			}
			if (splitType != null && splitType.equals("指定分摊")) {
				StringBuffer productValue = new StringBuffer("man");
				for (int j = 3; j < maxColumn; j++) {
					value = sheet.getCell(i, j, true).getValue();
					String productId = (String) map.get(new Integer(j));
					if (productId != null) {
						productValue.append("[]").append(productId).append('|')
								.append(
										FDCHelper.toBigDecimal(
												getBigDecimal(value))
												.toBigInteger());
					}
				}
				number = productValue.toString();
			} else {
				number = (String) getIndexMap(false).get(splitType);
			}
			if (FDCHelper.isEmpty(number)) {
				handleErr(sheet, i, -1, "分摊类型不存在");
			}

			if (entrys != null && entrys.size() > 0) {
				for (Iterator iter = entrys.iterator(); iter.hasNext();) {
					MeasureEntryInfo entry = (MeasureEntryInfo) iter.next();
					entry.setNumber(number);
				}
			}
		}
	}

	private void importPlanIndex(MeasureCostInfo measure, KDSSheet sheet) {
		setProcessString("正在引入规划指标表...");
		PlanIndexInfo planIndex = new PlanIndexInfo();
		planIndex.setHeadID(measure.getId().toString());
		planIndex.setId(BOSUuid.create(planIndex.getBOSType()));
		Object totalContainAreaCell = sheet.getCell(1, 2, true).getValue();
		Object buildAreaCell = sheet.getCell(1, 4, true).getValue();
		Object totalBuildAreaCell = sheet.getCell(1, 6, true).getValue();
		Object buildContailAreaCell = sheet.getCell(2, 2, true).getValue();
		Object buildDensityCell = sheet.getCell(2, 4, true).getValue();
		Object greenAreaRateCell = sheet.getCell(2, 6, true).getValue();
		Object cubageRateAreaCell = sheet.getCell(3, 2, true).getValue();
		Object cubageRateCell = sheet.getCell(3, 4, true).getValue();
		Object totalRoadAreaCell = sheet.getCell(5, 0, true).getValue();
		Object pitchRoadCell = sheet.getCell(5, 2, true).getValue();
		Object concreteRoadCell = sheet.getCell(5, 3, true).getValue();
		Object hardRoadCell = sheet.getCell(5, 4, true).getValue();
		Object hardSquareCell = sheet.getCell(5, 5, true).getValue();
		Object hardManRoadCell = sheet.getCell(5, 6, true).getValue();
		Object totalGreenAreaCell = sheet.getCell(7, 0, true).getValue();
		Object importPubGreenAreaCell = sheet.getCell(6, 2, true).getValue();
		Object houseGreenAreaCell = sheet.getCell(7, 3, true).getValue();
		Object privateGardenCell = sheet.getCell(7, 4, true).getValue();
		Object warterViewAreaCell = sheet.getCell(7, 5, true).getValue();
		planIndex.setTotalContainArea(getBigDecimal(totalContainAreaCell));
		planIndex.setBuildArea(getBigDecimal(buildAreaCell));
		planIndex.setTotalBuildArea(getBigDecimal(totalBuildAreaCell));
		planIndex.setBuildContainArea(getBigDecimal(buildContailAreaCell));
		planIndex.setBuildDensity(getBigDecimal(buildDensityCell));
		planIndex.setGreenAreaRate(getBigDecimal(greenAreaRateCell));
		planIndex.setCubageRateArea(getBigDecimal(cubageRateAreaCell));
		planIndex.setCubageRate(getBigDecimal(cubageRateCell));
		planIndex.setTotalRoadArea(getBigDecimal(totalRoadAreaCell));
		planIndex.setPitchRoad(getBigDecimal(pitchRoadCell));
		planIndex.setConcreteRoad(getBigDecimal(concreteRoadCell));
		planIndex.setHardRoad(getBigDecimal(hardRoadCell));
		planIndex.setHardSquare(getBigDecimal(hardSquareCell));
		planIndex.setHardManRoad(getBigDecimal(hardManRoadCell));
		planIndex.setTotalGreenArea(getBigDecimal(totalGreenAreaCell));
		planIndex.setImportPubGreenArea(getBigDecimal(importPubGreenAreaCell));
		planIndex.setHouseGreenArea(getBigDecimal(houseGreenAreaCell));
		planIndex.setPrivateGarden(getBigDecimal(privateGardenCell));
		planIndex.setWarterViewArea(getBigDecimal(warterViewAreaCell));

		int dynRowBase = 10;
		// 产品列索引
		int productColummnIndex = 1;
		/**
		 * 占地面积
		 */
		int containAreaColummnIndex = 2;
		/**
		 * 容积率面积
		 */
		int cubageRateColummnIndex = 3;
		/**
		 * 建筑面积
		 */
		int buildAreaColummnIndex = 4;
		/**
		 * 可售面积
		 */
		int sellAreaColummnIndex = 5;
		/**
		 * 产品比例
		 */
		int productRateColummnIndex = 6;
		/**
		 * 平均每户面积
		 */
		int avgHuColummnIndex = 7;
		/**
		 * 单元数
		 */
		int unitColummnIndex = 8;
		/**
		 * 户数
		 */
		int huColummnIndex = 9;
		/**
		 * 是否分摊
		 */
		int splitColummnIndex = 10;
		/**
		 * 备注
		 */
		int descColummnIndex = 11;
		/**
		 * 车位数
		 */
		int parksColummnIndex = 3;
		int subTotalCount = 1;
		for (int i = dynRowBase; i < sheet.getRowCount(); i++) {
			Object value = sheet.getCell(i, productColummnIndex, true)
					.getValue();
			if (FDCHelper.isEmpty(value.toString())) {
				continue;
			}
			String productName = value.toString();
			if (value.equals("小计")) {
				PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
				switch (subTotalCount) {
				case 1:
					entry.setType(PlanIndexTypeEnum.house);
					break;
				case 2:
					entry.setType(PlanIndexTypeEnum.publicBuild);
					break;
				case 3:
					entry.setType(PlanIndexTypeEnum.parking);
					break;
				}
				planIndex.getEntrys().add(entry);
				subTotalCount++;
				continue;
			}
			if (subTotalCount > 3) {
				break;
			}
			PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
			entry.setParent(planIndex);
			entry.setId(BOSUuid.create(entry.getBOSType()));
			ProductTypeInfo product = (ProductTypeInfo) getProductMap().get(
					productName);
			if (product != null) {
				entry.setProduct(product);
				entry.setIsProduct(true);
			} else {
				entry.setName(productName);
				entry.setIsProduct(false);
			}
			value = sheet.getCell(i, sellAreaColummnIndex, true).getValue();
			entry.setSellArea(getBigDecimal(value));
			value = sheet.getCell(i, cubageRateColummnIndex, true).getValue();
			entry.setCubageRate(getBigDecimal(value));
			value = sheet.getCell(i, buildAreaColummnIndex, true).getValue();
			entry.setBuildArea(getBigDecimal(value));
			value = sheet.getCell(i, containAreaColummnIndex, true).getValue();
			entry.setContainArea(getBigDecimal(value));
			value = sheet.getCell(i, productRateColummnIndex, true).getValue();
			entry.setProductRate(getBigDecimal(value));
			value = sheet.getCell(i, huColummnIndex, true).getValue();
			entry.setDoors(getBigDecimal(value));
			value = sheet.getCell(i, unitColummnIndex, true).getValue();
			entry.setUnits(getBigDecimal(value));
			value = sheet.getCell(i, avgHuColummnIndex, true).getValue();
			entry.setUnitArea(getBigDecimal(value));
			value = sheet.getCell(i, descColummnIndex, true).getValue();
			if (!FDCHelper.isEmpty(value)) {
				entry.setDesc(value.toString());
			}
			value = sheet.getCell(i, splitColummnIndex, true).getValue();
			if (!FDCHelper.isEmpty(value)) {
				entry.setIsSplit(value.toString().trim().equals("是"));
			} else {
				entry.setIsSplit(false);
			}
			switch (subTotalCount) {
			case 1:
				entry.setType(PlanIndexTypeEnum.house);
				entry.setIsSplit(true);
				break;
			case 2:
				entry.setType(PlanIndexTypeEnum.publicBuild);
				break;
			case 3:
				entry.setType(PlanIndexTypeEnum.parking);
				break;
			}
			planIndex.getEntrys().add(entry);
		}
		measure.put("PlanIndex", planIndex);
	}

	private Map sixEntryMap = new HashMap();

	private void importSix(MeasureCostInfo measure, KDSSheet sheet) {
		setProcessString("正在引入六类公摊及期间费...");
		Map indexMap = getIndexMap(true);
		for (int i = 1; i < sheet.getRowCount(); i++) {
			MeasureEntryInfo entry = new MeasureEntryInfo();
			entry.setHead(measure);
			entry.setId(BOSUuid.create(entry.getBOSType()));
			// 科目编码
			Object value = sheet.getCell(i, 0, true).getValue();
			if (value == null || FDCHelper.isEmpty(value)) {
				continue;
			}
			String acctNumber = getString(value);
			if (FDCHelper.isEmpty(acctNumber)) {
				handleErr(sheet, i, -1, "成本科目编码不存在");
			}
			entry.setCostAccount(getCostAccount(acctNumber));
			if (entry.getCostAccount() == null) {
				handleErr(sheet, i, -1, "成本科目“" + acctNumber + "”在当前公司不存在");
			}
			if (!entry.getCostAccount().isIsLeaf()) {
				continue;
			}
			// 科目名称
			value = sheet.getCell(i, 1, true).getValue();
			entry.setEntryName(getString(value));
			// 原始指标名称
			value = sheet.getCell(i, 2, true).getValue();
			entry.setIndexName(getString(value));
			entry.setSimpleName((String) indexMap.get(getString(value)));
			// 原始指标值
			value = sheet.getCell(i, 3, true).getValue();
			entry.setIndexValue(getBigDecimal(value));
			// 系数名称
			value = sheet.getCell(i, 4, true).getValue();
			entry.setCoefficientName(getString(value));
			// 系数值
			value = sheet.getCell(i, 5, true).getValue();
			entry.setCoefficient(getBigDecimal(value));
			// 工作量
			value = sheet.getCell(i, 6, true).getValue();
			entry.setWorkload(getBigDecimal(value));
			// 单位
			value = sheet.getCell(i, 7, true).getValue();
			entry.put("tempUnit", getString(value));
			// 单价
			value = sheet.getCell(i, 8, true).getValue();
			entry.setPrice(getBigDecimal(value));
			// 合价
			value = sheet.getCell(i, 9, true).getValue();
			entry.setCostAmount(getBigDecimal(value));
			if (FDCHelper.toBigDecimal(value).signum() == 0) {
				handleErr(sheet, i, -1, "合价不能为空或者为零");
			}
			// 可售单方
			value = sheet.getCell(i, 10, true).getValue();
			// 描述
			value = sheet.getCell(i, 11, true).getValue();
			entry.setDescription(getString(value));
			MeasureEntryCollection entrys = (MeasureEntryCollection) sixEntryMap
					.get(acctNumber);
			if (entrys == null) {
				entrys = new MeasureEntryCollection();
				entrys.add(entry);
				sixEntryMap.put(acctNumber, entrys);
			} else {
				entrys.add(entry);
			}
			// System.out.println("sixentry:"+entry);
			measure.getCostEntry().add(entry);
		}

	}

	private void importProduct(MeasureCostInfo measure, KDSSheet sheet) {
		String sheetName = sheet.getName();
		if (sheet.getName() != null && sheet.getName().equals("模板说明")) {
			return;
		}
		if (sheetName == null || FDCHelper.isEmpty(sheetName)) {
			logger.error("导入测算时页签名为空!");
			return;
		}
		setProcessString("正在引入产品：" + sheetName + " ...");
		Map indexMap = getIndexMap(false);
		ProductTypeInfo product = (ProductTypeInfo) getProductMap().get(sheetName.trim());
		for (int i = 1; i < sheet.getRowCount(); i++) {
			MeasureEntryInfo entry = new MeasureEntryInfo();
			entry.setHead(measure);
			entry.setId(BOSUuid.create(entry.getBOSType()));
			entry.setProduct(product);
			// 科目编码
			Object value = sheet.getCell(i, 0, true).getValue();
			if (FDCHelper.isEmpty(value)) {
				continue;
			}
			String acctNumber = getString(value);
			if (FDCHelper.isEmpty(acctNumber)) {
				handleErr(sheet, i, -1, "成本科目编码不存在");
			}
			entry.setCostAccount(getCostAccount(acctNumber));
			if (entry.getCostAccount() == null) {
				handleErr(sheet, i, -1, "成本科目“" + acctNumber + "”+在当前公司不存在");
			}
			if (!entry.getCostAccount().isIsLeaf()) {
				continue;
			}
			// 科目名称
			value = sheet.getCell(i, 1, true).getValue();
			entry.setEntryName(getString(value));
			// 原始指标名称
			value = sheet.getCell(i, 2, true).getValue();
			entry.setIndexName(getString(value));
			entry.setSimpleName((String) indexMap.get(getString(value)));
			// 原始指标值
			value = sheet.getCell(i, 3, true).getValue();
			entry.setIndexValue(getBigDecimal(value));
			// 系数名称
			value = sheet.getCell(i, 4, true).getValue();
			entry.setCoefficientName(getString(value));
			// 系数值
			value = sheet.getCell(i, 5, true).getValue();
			entry.setCoefficient(getBigDecimal(value));
			// 工作量
			value = sheet.getCell(i, 6, true).getValue();
			entry.setWorkload(getBigDecimal(value));
			// 单位
			value = sheet.getCell(i, 7, true).getValue();
			entry.put("tempUnit", getString(value));
			// 单价
			value = sheet.getCell(i, 8, true).getValue();
			entry.setPrice(getBigDecimal(value));
			// 合价
			value = sheet.getCell(i, 9, true).getValue();
			entry.setCostAmount(getBigDecimal(value));
			if (FDCHelper.toBigDecimal(value).signum() == 0) {
				handleErr(sheet, i, -1, "合价不能为空或者为零");
			}
			// 可售单方
			value = sheet.getCell(i, 10, true).getValue();
			// 描述
			value = sheet.getCell(i, 11, true).getValue();
			entry.setDescription(getString(value));
			System.out.println("product entry:" + entry);
			measure.getCostEntry().add(entry);
		}

	}

	private KDSBook book = null;

	private KDSBook getBook() {
		if (book == null) {
			String fileName = file.getAbsolutePath();
			try {
				book = POIXlsReader.parse2(fileName);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				MsgBox.showError(FDCClientHelper.getCurrentActiveWindow(),
						"读取文件时出错!", sw.toString());
				try {
					sw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return book;
	}

	public static void main(String[] args) {

	}

	private Map dataMap = null;

	private Map getDataMap() {
		if (dataMap == null) {
			fetchData();
			if (dataMap == null) {
				dataMap = new HashMap();
			}
		}
		return dataMap;
	}

	private void fetchData() {
		KDSBook book = getBook();
		KDSSheet sheet = getSheet(0);
		Map params = new HashMap();
		Object value = sheet.getCell(1, 2, true).getValue();
		params.put("ProjectTypeNumber", getString(value));
		value = sheet.getCell(1, 3, true).getValue();
		params.put("ProjectNumber", getString(value));
		params.put("OrgId", orgId);

		Set productNumberSet = getSheetProductSet(book);
		params.put("ProductNumberSet", productNumberSet);
		try {
			dataMap = MeasureCostFactory.getRemoteInstance().getImportData(
					params);
		} catch (Exception e) {
			Exception e1 = new Exception("服务器端取数据时出错", e);
			FDCMsgBox.showError(FDCClientHelper.getCurrentActiveWindow(), e1
					.getMessage());
		}
	}

	/**
	 * 页签上的产品编码集合
	 * 
	 * @param book
	 * @return
	 */
	private Set getSheetProductSet(KDSBook book) {
		Set productNumberSet = new HashSet();
		for (int i = 3; i < book.getSheetCount(); i++) {
			String sheetName = getSheet(i).getName();
			if (sheetName != null && sheetName.equals("模板说明")) {
				continue;
			}
			productNumberSet.add(sheetName);
		}
		return productNumberSet;
	}

	public ProjectTypeInfo getProjectTypeInfo() {
		return (ProjectTypeInfo) getDataMap().get("projectType");
	}

	public CurProjectInfo getProjectInfo() {
		return (CurProjectInfo) getDataMap().get("project");
	}

	public Map getAcctMap() {
		Map map = (Map) getDataMap().get("acctMap");
		if (map == null) {
			map = new HashMap();
		}
		return map;
	}

	public Map getProductMap() {
		Map map = (Map) getDataMap().get("productMap");
		if (map == null) {
			map = new HashMap();
		}
		return map;
	}

	public CostAccountInfo getCostAccount(String longNumber) {
		CostAccountInfo info = (CostAccountInfo) getAcctMap().get(longNumber);
		return info;
	}

	public BigDecimal getBigDecimal(Object value) {
		return FDCHelper.toBigDecimal(value);
	}

	public String getString(Object value) {
		if (FDCHelper.isEmpty(value)) {
			return null;
		}
		return value.toString().trim();
	}

	private Map indexMapSix = null;
	private Map indexMapMain = null;

	private Map getIndexMap(boolean isSix) {
		if (isSix) {
			if (indexMapSix == null) {
				indexMapSix = new HashMap();
				indexMapSix.put("", "empty");
				indexMapSix.put("总占地面积", "totalContainArea");
				indexMapSix.put("建筑用地面积", "buildArea");
				indexMapSix.put("总建筑面积", "totalBuildArea");
				indexMapSix.put("建筑物占地面积", "buildContainArea");
				indexMapSix.put("建筑密度", "buildDensity");
				indexMapSix.put("绿地率", "greenAreaRate");
				indexMapSix.put("计容积率面积", "cubageRateArea");
				indexMapSix.put("容积率", "cubageRate");
				indexMapSix.put("道路用地合计", "totalRoadArea");
				indexMapSix.put("绿化用地合计	", "totalGreenArea");
				indexMapSix.put("沥青路面车行道", "pitchRoad");
				indexMapSix.put("砼路面车行道（停车场）", "concreteRoad");
				indexMapSix.put("硬质铺装车行道", "hardRoad");
				indexMapSix.put("硬质铺装广场", "hardSquare");
				indexMapSix.put("硬质铺装人行道", "hardManRoad");
				indexMapSix.put("重要公共绿地", "importPubGreenArea");
				indexMapSix.put("组团宅间绿化", "houseGreenArea");
				indexMapSix.put("底层私家花园", "privateGarden");
				indexMapSix.put("水景面积", "warterViewArea");
				indexMapSix.put("景观面积", "viewArea");
				indexMapSix.put("户数", "doors");
			}
			return indexMapSix;
		} else {
			if (indexMapMain == null) {
				indexMapMain = new HashMap();
				indexMapMain.put("", "empty");
				indexMapMain.put("占地面积", "containArea");
				indexMapMain.put("建筑面积", "buildArea");
				indexMapMain.put("可售面积", "sellArea");
				indexMapMain.put("容积率", "cubageRate");
				indexMapMain.put("产品比例", "productRate");
				indexMapMain.put("平均每户面积", "unitArea");
				indexMapMain.put("单元数", "units");
				indexMapMain.put("户数", "doors");
			}
			return indexMapMain;
		}
	}

	private KDSSheet getSheet(int i) {
		KDSSheet sheet = this.book.getSheet(new Integer(i));
		return sheet;
	}

	private void handleErr(KDSSheet sheet, int i, int j, String errorDetail) {
		String msg = "页签：“" + sheet.getName()+" ”";
		if(i>=0){
			msg+="第" + (i + 1) + "行";
			
		}
		if (j >= 0) {
			msg += "第" + (j + 1) + "列出错\n";
		}
		msg += errorDetail;
		FDCMsgBox.showDetailAndOK(FDCClientHelper.getCurrentActiveWindow(),
				"导入数据出错", msg, 0);
		SysUtil.abort();
	}

	private void handleWarn(KDSSheet sheet, int i, int j, String warnDetail) {
		String msg = "页签：“" + sheet.getName()+" ”";
		if(i>=0){
			msg+="第" + (i + 1) + "行";
			
		}
		if (j >= 0) {
			msg += "第" + (j + 1) + "列出错\n" + warnDetail;
		}
		FDCMsgBox.showDetailAndOK(FDCClientHelper.getCurrentActiveWindow(),
				"导入数据出错", msg, 2);
	}
	private void handleErr(KDSSheet sheet,String errorDetail) {
		handleErr(sheet, -1, -1, errorDetail);
	}
	private void handleWarn(KDSSheet sheet, String warnDetail) {
		handleWarn(sheet, -1, -1, warnDetail);
	}
	private String tips = "";

	public String getProcessString() {
		return tips;
	}

	public void setProcessString(String tips) {
		this.tips = tips;
		if (this.tips == null) {
			this.tips = "";
		}
	}
}
