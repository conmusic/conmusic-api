package school.sptech.conmusicapi.modules.recurrence.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public class CreateRecurrenceRulesDto {
    @Future
    private LocalDate endRecurrence;

    @NotEmpty
    private List<@Valid RecurrenceRuleDto> rules;

    public LocalDate getEndRecurrence() {
        return endRecurrence;
    }

    public void setEndRecurrence(LocalDate endRecurrence) {
        this.endRecurrence = endRecurrence;
    }

    public List<RecurrenceRuleDto> getRules() {
        return rules;
    }

    public void setRules(List<RecurrenceRuleDto> rules) {
        this.rules = rules;
    }
}
