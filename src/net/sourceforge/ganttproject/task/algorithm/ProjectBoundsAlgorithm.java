package net.sourceforge.ganttproject.task.algorithm;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sourceforge.ganttproject.task.Task;

import org.apache.log4j.Logger;

public class ProjectBoundsAlgorithm {
	
	private static Logger logger = Logger
			.getLogger("net.sourceforge.ganttproject.task.algorithm.ProjectBoundsAlgorithm");
	
    public static class Result {
        public final Date lowerBound;

        public final Date upperBound;

        private Result(Date lowerBound, Date upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
    }

	/**
	 * 描述：
	 * 
	 * @param tasks
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-10
	 */
	public Result getBounds(Collection/* <Task> */tasks) {
		// 进度性能优化: 性能瓶颈 6.4% - 5,609 ms - 518 inv by skyiter_wang 2014-06-11

		// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11
		// 1、KDTask.<init> --> TaskImpl$MutatorImpl.commit
		// KDTask.setEndAndCalDur --> TaskImpl$MutatorImpl.commit
		// AlgorithmImpl.recalculateSupertaskSchedule --> TaskImpl$MutatorImpl.commit
		// 2、TaskImpl$MutatorImpl.commit --> TaskManagerImpl.fireTaskScheduleChanged -->
		// 3、TaskManagerImpl.fireTaskChange -- >TaskManagerImpl.getProjectStart -->
		// 4、ProjectBoundsAlgorithm.getBounds

		// 1.1、调用TaskManagerImpl.fireTaskChange(3处)
		// 1.2、TaskManagerImpl.fireTaskChange调用了TaskManagerImpl.getProjectStart，getProjectEnd
		// 1.3、TaskManagerImpl.getProjectStart，getProjectEnd调用了ProjectBoundsAlgorithm.getBounds
		// 1.4、总共有3*2处调用ProjectBoundsAlgorithm.getBounds(6处)
		// 1.5、性能至少会消耗 6.4%* 6 = 38%左右

		// 优化方案：使用缓存TreeSet排序
		// 详见：TaskManagerImpl.TaskMap.addTask、getMinStartDate;TaskManagerImpl.getProjectStart

		Result result = null;
		Date lowerBound = null;
		Date upperBound = null;
		for (Iterator it = tasks.iterator(); it.hasNext();) {
			Task next = (Task) it.next();

			// 进度性能优化： 性能瓶颈 2.4% - 2,154 ms - 134,421 inv by skyiter_wang 2014-06-11
			Date start = next.getStart().getTime();
			// 进度性能优化： 性能瓶颈 3.1% - 2,708 ms - 134,421 inv by skyiter_wang 2014-06-11
			Date end = next.getEnd().getTime();

			if (lowerBound == null || lowerBound.after(start)) {
				lowerBound = start;
			}
			if (upperBound == null || upperBound.before(end)) {
				upperBound = end;
			}
		}
		result = new Result(lowerBound, upperBound);

		if (true) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String lowerBoundStr = df.format(lowerBound);
			String upperBoundStr = df.format(upperBound);
			logger.info("======================lowerBound: " + lowerBoundStr + ", upperBound: " + upperBoundStr);
		}

		return result;
	}
}
