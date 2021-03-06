package model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Задача.
 *
 * @author Vladimir Ivanov (ivanov.vladimir.l@gmail.com)
 */
public class Задача {
	protected int ид;
	protected String название;
	protected String описание;
	protected String статус;

	protected LocalDateTime начало;
	protected int длительность;
	protected LocalDateTime окончание;

	public Задача(int ид, String название, String описание, String статус, LocalDateTime начало, int длительность) {
		this.ид = ид;
		this.название = название;
		this.описание = описание;
		this.статус = статус;
		this.начало = начало;
		this.длительность = длительность;
		this.окончание = начало.plusMinutes(длительность);
	}

	public Задача(String название, String описание, int ид, String статус) {
		this.название = название;
		this.описание = описание;
		this.ид = ид;
		this.статус = статус;
	}

	public Задача(String название, String описание, String статус) {
		this.название = название;
		this.описание = описание;
		this.ид = 0;
		this.статус = статус;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Задача)) return false;
		Задача задача = (Задача) o;
		return ид == задача.ид && Objects.equals(название, задача.название) && Objects.equals(описание, задача.описание) && Objects.equals(статус, задача.статус);
	}

	@Override
	public int hashCode() {
		return Objects.hash(название, описание, ид, статус);
	}

	public String getНазвание() {
		return название;
	}

	public void setНазвание(String название) {
		this.название = название;
	}

	public String getОписание() {
		return описание;
	}

	public void setОписание(String описание) {
		this.описание = описание;
	}

	public int getИд() {
		return ид;
	}

	public void setИд(int ид) {
		this.ид = ид;
	}

	public String getСтатус() {
		return статус;
	}

	public void setСтатус(String статус) {
		this.статус = статус;
	}

	public ТипыЗадач getТип() {
		return ТипыЗадач.TASK;
	}

	public Integer getЭпикИД() {
		return null;
	}

	@Override
	public String toString() {
		return "Задача{" +
				"название='" + название + '\'' +
				", описание='" + описание + '\'' +
				", ид=" + ид +
				", cтатус='" + статус + '\'' +
				'}';
	}

	public LocalDateTime getНачало() {
		return начало;
	}

	public void setНачало(LocalDateTime начало) {
		this.начало = начало;
	}

	public int getДлительность() {
		return длительность;
	}

	public void setДлительность(int длительность) {
		this.длительность = длительность;
	}

	public LocalDateTime getОкончание() {
		return окончание;
	}

	public void setОкончание(LocalDateTime окончание) {
		this.окончание = окончание;
	}
}
