package xyz.zanereis.crdt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.zanereis.crdt.expression.ExpressionController;
import xyz.zanereis.crdt.expression.OrderedEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class WWWController {
    private final ExpressionController expressionController;
    private final ObjectWriter objectWriter;
    @Autowired
    WWWController(ExpressionController expressionController,
                  ObjectMapper objectMapper) {
        this.expressionController = expressionController;
        this.objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    }

    @GetMapping("/www/{id}")
    public String showSimulation(@PathVariable("id") Long id, Model model) {
        String env = "Error";
        List<OrderedEntry> expressions = new ArrayList<>();
        try {
            expressions = expressionController.getOrderedExpressions(id);
            if (expressions.size() != 0)
                env = serialize(expressionController.getEnvironment(id));
            else
                env = "Nothing happened yet.";
        } catch (NoSuchElementException e) {
            env = "This simulation does not exist";
        }
        model.addAttribute("simulation", id);
        model.addAttribute("env", env);
        model.addAttribute("expressions", expressions);
        return "simulation";
    }

    @GetMapping("/www/{id}/{index}")
    public String showSimulation(@PathVariable("id") Long id, @PathVariable Integer index, Model model) {
        List<OrderedEntry> expressions = expressionController.getOrderedExpressions(id);
        String env = serialize(expressionController.viewHistoricEnvironment(id, index));
        model.addAttribute("simulation", id);
        model.addAttribute("env", env);
        model.addAttribute("expressions", expressions);
        return "simulation";
    }
    private String serialize(Object o) {
        String envStr = "";
        try {
            envStr = objectWriter.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            envStr = "SERIALIZING FAILED";
            e.printStackTrace();
        }
        System.out.println(envStr);
        return envStr;
    }
}
