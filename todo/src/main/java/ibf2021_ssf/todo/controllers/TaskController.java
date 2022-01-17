package ibf2021_ssf.todo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2021_ssf.todo.TodoApplication;
import ibf2021_ssf.todo.service.TaskService;

@Controller
// must link with HTML form's action
@RequestMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
public class TaskController {

    @Autowired
    private TaskService taskService;

    // @Autowired
    // ToDoTask task;
    // Get fields from form and print out the fields
    // @GetMapping("/")
    // public String homePage(Model model) {
    // model.addAttribute("task", task);
    // return "index";
    // }

    private final Logger logger = Logger.getLogger(TodoApplication.class.getName());

    @GetMapping
    public String loginForm(Model model) {
        return "task";
    }

    @PostMapping(path = "/task")
    public String submitLogin(@RequestBody MultiValueMap<String, String> loginID, Model model) {
        String username = loginID.getFirst("login");
        // TODO Check if username exists within database
        // if it exists, load its tasklist else use a new instance
        return "task";
    }

    // Handling submission of form
    @PostMapping(path = "/task")
    public String formSubmit(@RequestBody MultiValueMap<String, String> form, Model model) {
        String taskName = form.getFirst("taskName");
        String hiddenTask = form.getFirst("contents");

        List<String> tasks = new LinkedList<>();
        if (hiddenTask != null) {
            hiddenTask = "%s/t%s".formatted(hiddenTask, taskName);
            tasks = new ArrayList<>(Arrays.asList(hiddenTask.trim().split("/t")));
        } else {
            hiddenTask = taskName;
            tasks.add(hiddenTask);
        }

        tasks.remove(0);

        model.addAttribute("contents", hiddenTask);
        model.addAttribute("tasks", tasks);
        logger.info("tasks --> " + tasks);
        // // A very long string with tab delimiter containing all tasks submitted
        // hiddenTask = hiddenTask + taskName + "\t";
        // logger.info("hiddenTask ---> " + hiddenTask);
        // // A String array of tasks based on hiddenTask split with tab delimiter and
        // // stripped to remove last tab at the end
        // String[] tasks = hiddenTask.strip().split("/t");
        // logger.info("tasks --> " + tasks);

        // logging
        logger.log(Level.INFO, "taskName: %s".formatted(taskName));
        logger.info("hiddenTask ---> " + hiddenTask);
        logger.info("Tasks --> " + tasks);

        return "task";
    }

    @PostMapping("save")
    public String saveTask(@RequestBody MultiValueMap<String, String> form) {
        String contents = form.getFirst("contents");
        logger.info("contents --> " + contents);
        taskService.save("my-todo", contents);
        return "task";
    }

}
