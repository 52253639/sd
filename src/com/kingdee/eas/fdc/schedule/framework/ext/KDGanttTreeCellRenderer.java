package com.kingdee.eas.fdc.schedule.framework.ext;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.GanttTree2;
import net.sourceforge.ganttproject.GanttTree2.GanttTreeCellRenderer;
import net.sourceforge.ganttproject.font.Fonts;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.ResourceAssignment;
import net.sourceforge.ganttproject.task.Task;

/**
 * @author xiaohong_shi
 *
 *@see net.sourceforge.ganttproject.GanttTree2.GanttTreeCellRenderer
 */
public class KDGanttTreeCellRenderer extends DefaultTreeCellRenderer // JLabel-CL
implements TreeCellRenderer {
	private GanttLanguage language = GanttLanguage.getInstance();
    public KDGanttTreeCellRenderer() {
        setOpaque(true);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {

    	KDTask task = (KDTask) ((DefaultMutableTreeNode) value).getUserObject();// getTask(value.toString());
        if (task == null) {
            return this;
        }
        int type = 0;

        setFont(Fonts.GANTT_TREE_FONT);

        if (task.isMilestone()) {
            setIcon(new ImageIcon(getClass().getResource(
                    "/icons/meeting.gif")));
            type = 1;
        } else if (leaf) {
            if (task.getPriority() == GanttTask.LOW) {
                setIcon(new ImageIcon(getClass().getResource(
                        "/icons/task1.gif")));
            } else if (task.getPriority() == GanttTask.NORMAL) {
                setIcon(new ImageIcon(getClass().getResource(
                        "/icons/task.gif")));
            } else if (task.getPriority() == GanttTask.HIGHT) {
                setIcon(new ImageIcon(getClass().getResource(
                        "/icons/task2.gif")));
            }
            type = 2;
        } else {
            setIcon(new ImageIcon(getClass()
                    .getResource("/icons/mtask.gif")));
            setFont(Fonts.GANTT_TREE_FONT2);
        }

        setText(task.toString());
        setToolTipText(getToolTip(task, type));
        /*setBackground(selected ? new Color((float) 0.290, (float) 0.349,
                (float) 0.643) : row % 2 == 0 ? Color.white : new Color(
                (float) 0.933, (float) 0.933, (float) 0.933));*/
        setBackground(selected ? new Color((float) 0.290, (float) 0.349,
                (float) 0.643) : task.isScheduleTask() ? Color.white : new Color(
                (float) 0.933, (float) 0.933, (float) 0.933));
        setForeground(selected ? Color.white : Color.black);
        return this;
    }

    public String getToolTip(Task task, int type) {
        String res = "<html><body bgcolor=#EAEAEA>";
        res += "<b>" + task.toString() + "</b>" + "<br>" + task.getStart();
        if (type != 1) {
            res += "  -  " + task.getEnd();
        }
        if (type == 1) {
            res += "<br>" + language.getText("meetingPoint");
        }

        res += "<br><b>Pri</b> "
                + (task.getPriority() == 0 ? language.getText("low") : task
                        .getPriority() == 1 ? language.getText("normal")
                        : language.getText("hight"));

        ResourceAssignment[] assignments = task.getAssignments();
        if (assignments.length > 0) {
            res += "<br><b>Assign to </b><br>";
            for (int j = 0; j < assignments.length; j++) {
                res += "&nbsp;&nbsp;"
                        + assignments[j].getResource().getName() + "<br>";
            }
        }

        if (task.getNotes() != null && !task.getNotes().equals("")) {
            String notes = task.getNotes();
            res += "<br><b>Notes </b>: ";
            int maxLength = 150;
            if (notes.length() > maxLength) {
                notes = notes.substring(0, maxLength);
                int i = maxLength - 1;
                for (; i >= 0 && notes.charAt(i) != ' '; i--)
                    ;
                notes = notes.substring(0, i);
                notes += " <b>( . . . )</b>";
            }
            notes = notes.replaceAll("\n", "<br>");
            res += "<font size=\"-2\">" + notes + "</font>";
        }

        res += "</body></html>";
        return res;
    }

}
