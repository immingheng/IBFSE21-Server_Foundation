package ibf2021_ssf.todo.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ToDoTask implements Serializable {
    private String taskName;
    private String description;
    private long SerialVersionUID;

    public ToDoTask() {
    }

    // GETTERS AND SETTERS
    public long getSerialVersionUID() {
        return this.SerialVersionUID;
    }

    public void setSerialVersionUID(long SerialVersionUID) {
        this.SerialVersionUID = SerialVersionUID;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
