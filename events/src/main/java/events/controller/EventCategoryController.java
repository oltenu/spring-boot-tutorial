package events.controller;

import events.data.EventCategoryRepository;
import events.model.EventCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("category")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllCategories(Model model){
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", eventCategoryRepository.findAll());

        return "event_category/index";
    }

    @GetMapping("create")
    public String renderCreateCategoryForm(Model model){
        model.addAttribute("title", "Create Category");
        model.addAttribute(new EventCategory());

        return "event_category/create";
    }

    @PostMapping("create")
    public String processCreateCategoryForm(@ModelAttribute @Valid EventCategory eventCategory,
                                            Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Category");

            return "event_category/create";
        }

        eventCategoryRepository.save(eventCategory);

        return "redirect:/category";
    }

    @GetMapping("delete")
    public String renderDeleteCategoryForm(Model model){
        model.addAttribute("title", "Delete Category");
        model.addAttribute("categories", eventCategoryRepository.findAll());

        return "event_category/delete";
    }

    @PostMapping("delete")
    public String processDeleteCategoryForm(@RequestParam(required = false) int[] categoriesIds){
        if(categoriesIds != null){
            for(int id : categoriesIds){
                eventCategoryRepository.deleteById(id);
            }
        }

        return "redirect:/category";
    }
}
