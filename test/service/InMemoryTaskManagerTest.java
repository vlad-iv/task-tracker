package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Task;

@DisplayName("InMemoryTaskManager должен ")
class InMemoryTaskManagerTest {

	InMemoryTaskManager taskManager;

	@BeforeEach
	void beforeEach() {
		taskManager = new InMemoryTaskManager(new EmptyHistoryManager());
//		taskManager.tasks.put()
	}

	@DisplayName(" создавать задачу и задавать ей идентификатор ")
	@Test
	void shouldCreateTaskWithId() {
		Task task = new Task();
		Task task2 = new Task();
		Task task3 = task;
		assertEquals(task, task2); // Ok
		assertSame(task, task2); // Fail
		assertSame(task, task3); // OK

		Task result = taskManager.create(task);

		assertNotNull(result);
		assertEquals(taskManager.seq, result.getId(),  "Task id should equals new sequans value");
		assertEquals(task.getName(), result.getName(), "Task name");

		assertTrue(taskManager.tasks.containsKey(task), "Task added to tasks map");
//		assertFalse(taskManager.epics.containsKey(task), "Task not added to epics map");
//		assertFalse(taskManager.subTasks.containsKey(task), "Task not added to subtask map");
	}

	@DisplayName(" возвращать историю из менеджера истории ")
	@Test
	void shouldReturnHistoryFromHistoryManager() {
		Task task = new Task();
		InMemoryTaskManager taskManager = new InMemoryTaskManager(new HistoryManager() {
			@Override
			public void add(Task task) {
			}

			@Override
			public List<Task> getAll() {
				return Collections.singletonList(task);
			}

			@Override
			public void remove(int id) {
			}
		});

		List<Task> history = taskManager.getHistory();

		assertNotNull(history);
		assertEquals(1, history.size(), "History contains 1 Task");
		assertTrue(history.contains(task), "History contains Task");
	}

	private static class EmptyHistoryManager implements HistoryManager {
		@Override
		public void add(Task task) {
		}

		@Override
		public List<Task> getAll() {
			return Collections.emptyList();
		}

		@Override
		public void remove(int id) {
		}
	}
}