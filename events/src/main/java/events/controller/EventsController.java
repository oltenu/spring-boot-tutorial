package events.controller;

import events.data.EventCategoryRepository;
import events.data.EventRepository;
import events.data.TagRepository;
import events.model.Event;
import events.model.EventCategory;
import events.model.Tag;
import events.model.dto.EventTagDTO;
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

    @Autowired
    private TagRepository tagRepository;

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

    @GetMapping("details")
    public String displayEvent(@RequestParam(required = false) Integer eventId, Model model) {
        if (eventId == null) {
            return "events/index";
        } else {
            Optional<Event> eventOptional = eventRepository.findById(eventId);

            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();

                model.addAttribute("title", "Event: " + event.getName());
                model.addAttribute("events", event);
            } else {
                return "events/index";
            }
        }

        return "events/details";
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

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            model.addAttribute("title", "Add Tag to: " + event.getName());
            model.addAttribute("tags", tagRepository.findAll());
            EventTagDTO eventTagDTO = new EventTagDTO();
            eventTagDTO.setEvent(event);
            model.addAttribute("eventTag", eventTagDTO);

            return "events/add-tag";
        }

        return "events/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTagDTO, Errors errors, Model model) {
        if (!errors.hasErrors()) {
            Event event = eventTagDTO.getEvent();
            Tag tag = eventTagDTO.getTag();
            if (event.getTags().contains(tag)) {
                event.addTag(tag);
                eventRepository.save(event);
            }

            return "redirect:/events";
        }
        return "events/add-tag";
    }
}
