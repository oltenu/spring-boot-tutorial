package events.controller;

import events.data.TagRepository;
import events.model.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllTags(Model model){
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", tagRepository.findAll());

        return "tag/index";
    }

    @GetMapping("create")
    public String renderCreateTagForm(Model model){
        model.addAttribute("title", "Create Tag");
        model.addAttribute(new Tag());

        return "tag/create";
    }

    @PostMapping("create")
    public String processCreateTagFrom(@ModelAttribute @Valid Tag tag, Errors errors){
        if(errors.hasErrors()){
            return "tag/create";
        }

        tag.setName("#" + tag.getName());

        tagRepository.save(tag);

        return "redirect:/tag";
    }
}
