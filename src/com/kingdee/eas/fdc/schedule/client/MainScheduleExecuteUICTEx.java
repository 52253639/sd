package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class MainScheduleExecuteUICTEx extends MainScheduleExecuteUI{

	public MainScheduleExecuteUICTEx() throws Exception {
		super();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		KDWorkButton exportXls = new KDWorkButton();
		exportXls.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		exportXls.setText("导出任务excel");
		this.toolBar.add(exportXls, 10);
		exportXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportManager exportM = new ExportManager();
			    String path = null;
			    File tempFile;
			    KDTable exportTable = new KDTable();
			    initExportTable(exportTable);
			    FDCScheduleTaskCollection taskColl = editData.getTaskEntrys();
			    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
			    DateFormat format = DateFormat.getDateInstance();
			    for(int i = 0; i < taskColl.size(); i++) {
			    	FDCScheduleTaskInfo scheTaskInfo = taskColl.get(i);
			    	int seq = scheTaskInfo.getSeq();//序号
			    	String TaskName = addSpaceToTaskName(scheTaskInfo.getName(), scheTaskInfo.getLevel());//任务名称
			    	String type=scheTaskInfo.getTaskType().getAlias();
			    	BigDecimal NatureTimes = scheTaskInfo.getEffectTimes();//工期
			    	Date PlanStart = scheTaskInfo.getPlanStart();//计划开始日期
			    	Date PlanEnd = scheTaskInfo.getPlanEnd();//计划完成日期
			    	Date ActualStartDate = scheTaskInfo.getActualStartDate();//实际开始日期
			    	Date ActualEndDate = scheTaskInfo.getActualEndDate();//实际完成日期
			    	Date CheckDate = scheTaskInfo.getCheckDate();//考核日期
			    	PersonInfo AdminPerson = scheTaskInfo.getAdminPerson();//责任人
			    	FullOrgUnitInfo AdminDept = scheTaskInfo.getAdminDept();//责任部门
			    	
			    	String planStart = (PlanStart == null ? "" : simpleFormat.format(PlanStart));
			    	String planEnd = (PlanEnd == null ? "" : simpleFormat.format(PlanEnd));
			    	String actualStartDate = (ActualStartDate == null ? "" : simpleFormat.format(ActualStartDate));
			    	String actualEndDate = (ActualEndDate == null ? "" : simpleFormat.format(ActualEndDate));
			    	String checkDate = (CheckDate == null ? "" : simpleFormat.format(CheckDate));

			    	IRow rowAdd = exportTable.addRow();
			    	rowAdd.getCell(0).setValue(seq);
			    	rowAdd.getCell(1).setValue(type);
			    	rowAdd.getCell(2).setValue(TaskName);
			    	rowAdd.getCell(3).setValue(NatureTimes);
			    	rowAdd.getCell(4).setValue(planStart);
			    	rowAdd.getCell(5).setValue(planEnd);
			    	rowAdd.getCell(6).setValue(actualStartDate);
			    	rowAdd.getCell(7).setValue(actualEndDate);
			    	rowAdd.getCell(8).setValue(checkDate);
			    	rowAdd.getCell(9).setValue(AdminPerson);
			    	rowAdd.getCell(10).setValue(AdminDept);
			    }
				try {
					tempFile = File.createTempFile("eastemp",".xls");
					path = tempFile.getCanonicalPath();
					KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
					tablesVO[0]=new KDTables2KDSBookVO(exportTable);
					tablesVO[0].setTableName("任务导出表");
					KDSBook book = null;
				    book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
				    exportM.exportToExcel(book, path);
				    
				    KDFileChooser fileChooser = new KDFileChooser();
					fileChooser.setFileSelectionMode(0);
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setSelectedFile(new File("任务导出表.xls"));
					int result = fileChooser.showSaveDialog(null);
					
					if (result == KDFileChooser.APPROVE_OPTION){
						File dest = fileChooser.getSelectedFile();
						File src = new File(path);
						if (dest.exists())
							dest.delete();
						src.renameTo(dest);
						MsgBox.showInfo("导出成功！");
						KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
					}
					tempFile.delete();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	private void initExportTable(KDTable table) {
		table.addHeadRow(0);
		String[] head = new String[] {"序号","任务类别","任务名称", "任务工期", "计划开始日期", "计划完成日期",
									  "实际开始日期", "实际完成日期", "考核日期", "责任人", "责任部门"};
		for(int i = 0; i < head.length; i++) {
			IColumn col = table.addColumn();
			col.setKey("" + i);
			table.getHeadRow(0).getCell("" + i).setValue(head[i]);
		}
	}
	
	private String addSpaceToTaskName(String taskName, int level) {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < level-1; i++)
			sb.append("    ");
		sb.append(taskName);
		return sb.toString();
	}

}
