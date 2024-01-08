package io.automation.mapper;

import java.util.List;
import java.util.function.Function;

import io.automation.entity.SkillGemEntity;
import io.automation.model.Lines;
import io.automation.model.SkillGem;
import org.springframework.stereotype.Service;

@Service
public class SkillGemEntityMapper implements Function<Lines<SkillGem>, List<SkillGemEntity>> {

  @Override
  public List<SkillGemEntity> apply(Lines<SkillGem> skillGemLines) {
    return skillGemLines.getLines().stream()
        .map(skill -> new SkillGemEntity(
            skill.getName(),
            skill.getVariant(),
            skill.isCorrupted(),
            skill.getGemLevel(),
            skill.getGemQuality(),
            skill.getChaosValue()))
        .toList();
  }
}