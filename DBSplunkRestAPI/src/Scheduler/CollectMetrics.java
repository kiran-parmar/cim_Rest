package Scheduler;

import java.sql.SQLException;
import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import Initialize.GetOS;
import Initialize.GetProcess;
import Splunk.SplunkRestApi;
import database.DBMonitoring;
import database.SqlServerMetric;
import utilities.JsonUtil;

public class CollectMetrics implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {

		boolean processRunning = false;
		processRunning = GetProcess.isProcessRunning("sqlservr");
		if (processRunning) {
			ArrayList<SqlServerMetric> sqlList = JsonUtil.jsonReader();
			StringBuilder sb = new StringBuilder();
			for (int count = 0; count < sqlList.size(); count++) {
				if (sqlList.get(0).getGroup() != null || sqlList.get(0).getGroup().length() > 0) {
					sb.append("Group=" + sqlList.get(0).getGroup());
				}
				if (sqlList.get(0).getSourceType() != null || sqlList.get(0).getSourceType().length() > 0) {
					sb.append(" sourcetype=" + sqlList.get(0).getSourceType());
				}

				try {
					String result = DBMonitoring.sqlServerConnection(sqlList.get(0).getSql());
					if (result.length() > 0) {
						sb.append(result);
						SplunkRestApi.publishToSplunk(sb.toString());
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.setLength(0);
			}
		}
	}

}
