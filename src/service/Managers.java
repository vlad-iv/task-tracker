package service;

public class Managers {
	public static TaskManager getDefaultTaskManager() {
		return new FileBackedTaskManager(getDefaultHistory());
	}

	public static HistoryManager getDefaultHistory() {
		return new InMemoryHistoryManager();
	}
}
