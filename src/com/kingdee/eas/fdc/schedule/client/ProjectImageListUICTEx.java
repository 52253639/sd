package com.kingdee.eas.fdc.schedule.client;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryInfo;

public class ProjectImageListUICTEx extends ProjectImageListUI{

	private Map imageName=null;
	public ProjectImageListUICTEx() throws Exception {
		super();
	}
	public void imageSelectChanged(int arg0) {
		super.imageSelectChanged(arg0);
		String name=(String) imageName.get(new Integer(arg0));
		this.imgContainer.setTitle(name);
	}
	public Map getImages() throws FileNotFoundException, IOException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Object obj = null;
		Set prjIDs = new HashSet();
		if (node != null) {
			obj = node.getUserObject();
			// 判断节点非空时是否是明细工程项目
			if (obj instanceof CurProjectInfo) {
				prjIDs.add(((CurProjectInfo) obj).getId().toString());
				// new Map();
				// 加载对象
				if(imageName==null){
					imageName=new HashMap();
				}else{
					imageName.clear();
				}
				// 获取缩略图
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				// sic.add("file");
				sic.add("fileName");
				sic.add("smallFile");
				view.setSelector(sic);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent.project.id", prjIDs, CompareType.INCLUDE));
				view.setFilter(filter);
				// 按时间倒序排列
				SorterItemCollection sort = new SorterItemCollection();
				SorterItemInfo sort1 = new SorterItemInfo("parent.lastUpdateTime");
				sort1.setSortType(SortType.DESCEND);
				SorterItemInfo sort2 = new SorterItemInfo("seq");
				sort2.setSortType(SortType.DESCEND);
				sort.add(sort1);
				sort.add(sort2);
				view.setSorter(sort);
				try {
					ProjectImageEntryCollection imgEntrys = ProjectImageEntryFactory.getRemoteInstance().getProjectImageEntryCollection(view);
					// 遍历初始化缩略图相关数据
					if (imgEntrys != null && imgEntrys.size() > 0) {
						for (int i = 0; i < imgEntrys.size(); i++) {
							ProjectImageEntryInfo imgInfo = imgEntrys.get(i);
							byte[] file = imgInfo.getSmallFile();
							if (file == null) {
								continue;
							}
							ByteArrayInputStream in = new ByteArrayInputStream(file);
							BufferedImage img = ImageIO.read(in);
							if (img == null) {
								continue;
							}
							Integer index = new Integer(i + 1);
							imageName.put(index, imgInfo.getFileName());
						}
					}

				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
		}
		return super.getImages();
	}

}
