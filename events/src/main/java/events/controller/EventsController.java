package events.controller;

import events.data.EventCategoryRepository;
import events.data.EventRepository;
import events.model.Event;
import events.model.EventCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventsController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model) {
        if (categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else {
            Optional<EventCategory> eventCategoryOptional = eventCategoryRepository.findById(categoryId);
            if (eventCategoryOptional.isPresent()) {
                EventCategory category = eventCategoryOptional.get();
                model.addAttribute("title", "Events in category " + category.getName());
                model.addAttribute("events", category.getEvents());
            } else {
                model.addAttribute("title", "Invalid category ID!");
            }
        }

        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());

        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Event");

            return "events/create";
        }

        eventRepository.save(newEvent);

        return "redirect:/events";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete events");
        model.addAttribute("events", eventRepository.findAll());

        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {
        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:/events";
    }
}
