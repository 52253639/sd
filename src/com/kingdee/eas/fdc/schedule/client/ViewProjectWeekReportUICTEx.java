package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.util.client.EASResource;

public class ViewProjectWeekReportUICTEx extends ViewProjectWeekReportUI{

	public ViewProjectWeekReportUICTEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		KDWorkButton btnAttach=new KDWorkButton();
		btnAttach.setText("查看附件");
		btnAttach.setIcon(EASResource.getIcon("imgTbtn_view"));
		this.toolBar.add(btnAttach);
		btnAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                	btnAttach_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
	}
	private void btnAttach_actionPerformed(ActionEvent e) {
		DefaultKingdeeTreeNode node = getLastSelectedNode();
		/*
		 * 取报告年-周
		 */
		
		String selectedItem = (String) cbYear.getSelectedItem();
		if(selectedItem==null) return;
		int year = 1990;
		int Week = 1;
		if (!("".equals(selectedItem)) && selectedItem.length() > 0) {
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			Week = Integer.parseInt(split[1]);
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("creator.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			CurProjectInfo curProjectInfo = cpinfo.getCurProject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
		} else if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
		}

		view.setFilter(filter);
		ProjectWeekReportCollection collection;
		try {
			collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
			if (collection.size() > 0) {
				ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
				 AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			        acm.showAttachmentListUIByBoID(projectWeekReportInfo.getId().toString(),this,false); // boID 是 与附件关联的 业务对象的 ID
			}
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
