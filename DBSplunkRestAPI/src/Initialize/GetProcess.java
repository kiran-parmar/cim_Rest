package Initialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

public class GetProcess {
	public static void main(String[] args) {
		List<ProcessInfo> processesList = JProcesses.getProcessList();

		for (final ProcessInfo processInfo : processesList) {
			if (processInfo.getTime().toString() != "00:00:00") {
				System.out.println("Process PID: " + processInfo.getPid());
				System.out.println("Process Name: " + processInfo.getName());
				System.out.println("Process Used Time: " + processInfo.getTime());
				System.out.println("Full command: " + processInfo.getCommand());
				System.out.println("------------------");
			}
		}
	}

	public static boolean isProcessRunning(String targetProcess) {
		String os = GetOS.runningOs();
		if (os.equalsIgnoreCase("Windows")) {
			return getWindowsProcess(targetProcess);
		} else if (os.equalsIgnoreCase("Linux")) {
			return getLinuxProcess(targetProcess);
		}
		return false;
	}

	private static boolean getLinuxProcess(String targetProcess) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ps -e");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		return isRunning(processReader, targetProcess);
	}

	private static boolean getWindowsProcess(String targetProcess) {
		Process process = null;
		try {
			process = Runtime.getRuntime()
					.exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe ");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		return isRunning(processReader, targetProcess);
	}

	private static boolean isRunning(BufferedReader br, String targetProcess) {
		String sCurrentLine = null;
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains(targetProcess))
					return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
