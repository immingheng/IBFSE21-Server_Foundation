package ibf2021_ssf.todo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2021_ssf.todo.repositories.TaskRepo;

@Service
public class TaskService {

    // Injecting TaskRepo into service
    @Autowired
    private TaskRepo taskRepo;

    public boolean hasKey(String key) {
        Optional<String> opt = taskRepo.get(key);
        return opt.isPresent();
    }

    public List<String> get(String key) {
        Optional<String> opt = taskRepo.get(key);
        List<String> list = new LinkedList<>();
        if (opt.isEmpty())
            return list;
        for (String t : opt.get().split(",")) {
            list.add(t);
        }
        return list;
    }

    public void save(String key, String value) {
        taskRepo.save(key, value);
    }

    public void save(String key, List<String> values) {
        String l = values.stream().collect(Collectors.joining(","));
        this.save(key, l);
    }
}
