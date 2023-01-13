package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AccidentControl {
    private final AccidentService accidents;
    private final AccidentTypeService types;
    private final RuleService rules;

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id));
        return "show";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", types.getTypes());
        model.addAttribute("rules", rules.getRules());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.addTypesToAccident(ids, accident);
        accidents.createAccident(accident);
        return "redirect:/";
    }

    @PostMapping("/updateAccident")
    public String edit(@ModelAttribute("accident") Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.addTypesToAccident(ids, accident);
        accidents.updateAccident(accident.getId(), accident);
        return "redirect:/";
    }

    @GetMapping("/{id}/formUpdateAccident")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id));
        model.addAttribute("types", types.getTypes());
        model.addAttribute("rules", rules.getRules());
        return "formUpdateAccident";
    }

}
