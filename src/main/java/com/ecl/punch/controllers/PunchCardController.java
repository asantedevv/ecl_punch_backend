package com.ecl.punch.controllers;


import com.ecl.punch.dtos.PunchCardListResponse;
import com.ecl.punch.dtos.PunchCardResponse;
import com.ecl.punch.models.PunchCard;
import com.ecl.punch.services.PunchCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/punchcard")
@CrossOrigin(origins = "*")
public class PunchCardController {

    private final PunchCardService punchCardService;

    @PostMapping("/create")
    public PunchCardResponse createPunchCard(@RequestBody PunchCard punchCard) {
        return punchCardService.punchIn(punchCard);
    }

    @PostMapping("/punchOut")
    public PunchCardResponse punchOut(@RequestBody PunchCard punchCard) {
        return punchCardService.punchOut(punchCard);
    }

    @PostMapping("/find-by-id/{id}")
    public PunchCardResponse findPunchCardById(@PathVariable Integer id) {
        return punchCardService.findPunchCardById(id);
    }

    @PostMapping("/find-by-userId/{id}")
    public PunchCardListResponse findPunchCardByUserId(@PathVariable Integer id) {
        return punchCardService.findPunchCardsByUserId(id);
    }

    @PostMapping("/find-all-punchcards")
    public PunchCardListResponse findAllPunchCards() {
        return punchCardService.findAllPunchCards();
    }

    @PostMapping("/update-by-id/{id}")
    public PunchCardResponse updatePunchCardById(@PathVariable Integer id, @RequestBody PunchCard punchCard) {
        return punchCardService.updatePunchCardById(id, punchCard);
    }

    @PostMapping("/delete-punchcard/{id}")
    public PunchCardResponse deletePunchCardById(@PathVariable Integer id) {
        return punchCardService.deletePunchCardById(id);
    }

}

