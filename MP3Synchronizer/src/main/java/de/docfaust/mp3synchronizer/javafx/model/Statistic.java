package de.docfaust.mp3synchronizer.javafx.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Statistic {
    private final SimpleStringProperty totalFilesProperty = new SimpleStringProperty("");
    private final SimpleStringProperty totalLengthProperty = new SimpleStringProperty("");
    private final SimpleStringProperty totalTimeProperty = new SimpleStringProperty("");
    private final SimpleStringProperty currentFilesProperty = new SimpleStringProperty("");
    private final SimpleStringProperty currentLengthProperty = new SimpleStringProperty("");
    private final SimpleStringProperty currentTimeProperty = new SimpleStringProperty("");
    private final SimpleStringProperty avgTimeProperty = new SimpleStringProperty("");
    private final SimpleStringProperty avgSpeedProperty = new SimpleStringProperty("");
    private final SimpleDoubleProperty progressProperty = new SimpleDoubleProperty(0);
    private final SimpleStringProperty statusProperty = new SimpleStringProperty("");

    public SimpleStringProperty getTotalFilesProperty() {
	return totalFilesProperty;
    }

    public SimpleStringProperty getTotalLengthProperty() {
	return totalLengthProperty;
    }

    public SimpleStringProperty getTotalTimeProperty() {
	return totalTimeProperty;
    }

    public SimpleStringProperty getCurrentFilesProperty() {
	return currentFilesProperty;
    }

    public SimpleStringProperty getCurrentLengthProperty() {
	return currentLengthProperty;
    }

    public SimpleStringProperty getCurrentTimeProperty() {
	return currentTimeProperty;
    }

    public SimpleStringProperty getAvgTimeProperty() {
	return avgTimeProperty;
    }

    public SimpleStringProperty getAvgSpeedProperty() {
	return avgSpeedProperty;
    }

    public SimpleDoubleProperty getProgressProperty() {
	return progressProperty;
    }

    public SimpleStringProperty getStatusProperty() {
	return statusProperty;
    }

    public String getTotalFiles() {
	return totalFilesProperty.get();
    }

    public String getTotalLength() {
	return totalLengthProperty.get();
    }

    public String getTotalTime() {
	return totalTimeProperty.get();
    }

    public String getCurrentFiles() {
	return currentFilesProperty.get();
    }

    public String getCurrentLength() {
	return currentLengthProperty.get();
    }

    public String getCurrentTime() {
	return currentTimeProperty.get();
    }

    public String getAvgTime() {
	return avgTimeProperty.get();
    }

    public String getAvgSpeed() {
	return avgSpeedProperty.get();
    }

    public double getProgress() {
	return progressProperty.get();
    }

    public String getStatus() {
	return statusProperty.get();
    }

    public void setTotalFiles(String totalFiles) {
	this.totalFilesProperty.set(totalFiles);
    }

    public void setTotalLength(String totalLength) {
	this.totalLengthProperty.set(totalLength);
    }

    public void setTotalTime(String totalTime) {
	this.totalTimeProperty.set(totalTime);
    }

    public void setCurrentFiles(String currentFiles) {
	this.currentFilesProperty.set(currentFiles);
    }

    public void setCurrentLength(String currentLength) {
	this.currentLengthProperty.set(currentLength);
    }

    public void setCurrentTime(String currentTime) {
	this.currentTimeProperty.set(currentTime);
    }

    public void setAvgTime(String avgTime) {
	this.avgTimeProperty.set(avgTime);
    }

    public void setAvgSpeed(String avgSpeed) {
	this.avgSpeedProperty.set(avgSpeed);
    }

    public void setProgress(double progress) {
	this.progressProperty.set(progress);
    }

    public void setStatus(String status) {
	this.statusProperty.set(status);
    }

}
