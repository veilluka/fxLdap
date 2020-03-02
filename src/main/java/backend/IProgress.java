package backend;

public interface IProgress {
    void setProgress(double progress, String description);
    void signalTaskDone(String taskName, String description, Exception e);
    void setProgress(String taskName, double progress);
}
