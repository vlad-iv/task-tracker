package service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskType;

/**
 * File backed TaskManager.
 *
 * @author Vladimir Ivanov (ivanov.vladimir.l@gmail.com)
 */
public class FileBackedTaskManager extends InMemoryTaskManager {
	private final File file;

	public FileBackedTaskManager(HistoryManager historyManager) {
		this(historyManager, new File("task.csv"), false);
	}

	public FileBackedTaskManager(HistoryManager historyManager, File file) {
		this(historyManager, file, false);
	}


	public FileBackedTaskManager(HistoryManager historyManager, File file, boolean load) {
		super(historyManager);
		this.file = file;
		if (load) {
			load();
		}
	}

	@Override
	public List<Task> getAll() {
		return super.getAll();
	}

	@Override
	public Task get(int id) {
		return super.get(id);
	}

	@Override
	public Task create(Task task) {
		Task newTask = super.create(task);
		save();
		return newTask;
	}

	@Override
	public void update(Task task) {
		super.update(task);
	}

	@Override
	public void updateEpic(Epic epic) {
		super.updateEpic(epic);
	}

	@Override
	public void updateSubTask(SubTask subTask) {
		super.updateSubTask(subTask);
	}

	@Override
	public void delete(int id) {
		super.delete(id);
	}

	@Override
	public void deleteSubTask(int id) {
		super.deleteSubTask(id);
	}

	// Todo
	private String toString(Task task) {
		return "";
	}

	// Todo
	private Task fromString(String value) {
		final String[] columns = value.split(",");


//		switch (type = valueOf(columns[1])) {
//			case TASK:
//			case SUBTASK:
//			case EPIC:
//		}
		return null;
	}

	static String toString(HistoryManager manager) {
		StringBuilder sb = new StringBuilder();
		for (Task task : manager.getAll()) {
			// TODO
		}
		return sb.toString();
	}

	static List<Integer> historyFromString(String value) {
		final String[] ids = value.split(",");
		List<Integer> history = new ArrayList<>();
		for (String id : ids) {
//			история.add(Integer.parseInt(v));
			history.add(Integer.valueOf(id));
		}
		return history;
	}

	// Сохранение в файл
	private void save() {
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			// TODO  Заголовок id,type,name,status,description,epic
			for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
				writer.append(toString(entry.getValue()));
				writer.newLine();
			}
			// TODO подзадачи , эпики, история
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO ManagerSaveException
		}

	}

	// Восстановление из в файла
	private void load() {
//		try {
//			final String s = Files.readString(file.toPath());
//			s.split("\n");
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

		int maxId = 0;
		try (final BufferedReader reader = new BufferedReader(new FileReader(file, UTF_8))) {
			reader.readLine(); // Пропускаем заголовок
			while (true) {
				String line = reader.readLine();
				// Задачи
				final Task task = fromString(line);
				// TODO добавить задачу в менеджер
				final int id = task.getId();
				if (task.getType() == TaskType.TASK) {
					tasks.put(id, task);
//				} else if () { // TODO

				}

				if (maxId < id) {
					maxId = id;
				}
				if (line.isEmpty()) {
					break;
				}
			}

			String line = reader.readLine();
			// История

		} catch (IOException e) {
			throw new RuntimeException(e); // TODO ManagerSaveException
		}
		// генератор
		seq = maxId;

	}

	public static FileBackedTaskManager loadFromFile(File file) {
		final FileBackedTaskManager manager = new FileBackedTaskManager(Managers.getDefaultHistory(), file, true);
		return manager;
	}
}