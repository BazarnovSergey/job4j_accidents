package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentControl {
    private final AccidentService accidents;
    private final AccidentTypeService types;
    private final RuleService rules;
    private final AccidentStatusService statuses;

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id));
        return "show";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", types.getTypes());
        model.addAttribute("rules", rules.getRules());
        model.addAttribute("statuses", statuses.getStatuses());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam("file") MultipartFile file,
                       HttpServletRequest req) throws IOException {
        String[] ids = req.getParameterValues("rIds");
        byte[] photo = (file.getBytes());
        accidents.createAccident(accident, ids, photo);
        return "redirect:/";
    }

    @PostMapping("/updateAccident")
    public String edit(@ModelAttribute Accident accident,
                       @RequestParam("file") MultipartFile file,
                       HttpServletRequest req) throws IOException {
        String[] ids = req.getParameterValues("rIds");
        byte[] photo = (file.getBytes());
        accidents.updateAccident(accident.getId(), accident, ids, photo);
        return "redirect:/";
    }

    @GetMapping("/{id}/formUpdateAccident")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id));
        model.addAttribute("types", types.getTypes());
        model.addAttribute("rules", rules.getRules());
        model.addAttribute("statuses", statuses.getStatuses());
        return "formUpdateAccident";
    }

    @GetMapping("/posterAccident/{accidentId}")
    public ResponseEntity<Resource> download(@PathVariable("accidentId") Integer accidentId) {
        Optional<Accident> accident = Optional.of(accidents.findById(accidentId));
        return accident.<ResponseEntity<Resource>>map(value -> ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(value.getPhoto().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(value.getPhoto()))).orElse(null);
    }

}
