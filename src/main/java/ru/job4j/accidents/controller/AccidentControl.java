package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class AccidentControl {
    private final AccidentService accidents;

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id));
        return "show";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.createAccident(accident);
        return "redirect:/";
    }

    @GetMapping("/{id}/editAccident")
    public String viewEditAccident(Model model, @PathVariable("id") int id) {
        model.addAttribute("accident", accidents.findById(id));
        return "editAccident";
    }

    @PostMapping("{id}/updateAccident")
    public String edit(@ModelAttribute("accident") Accident accident, @PathVariable("id") int id) {
        accidents.updateAccident(id, accident);
        return "redirect:/";
    }
}
