package ru.practicum.explorewithme.stats.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    @Transactional
    public void create(HitDto hitDto) {
        statsRepository.save(StatsMapper.toHit(hitDto));
    }

    @Override
    public List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            if (CollectionUtils.isEmpty(uris)) {
                return statsRepository.findStatDtoDistinct(start, end);
            } else {
                return statsRepository.findStatDtoByUriIsDistinct(start, end, uris);
            }
        } else {
            if (CollectionUtils.isEmpty(uris)) {
                return statsRepository.findStatDto(start, end);
            } else {
                return statsRepository.findStatDtoByUriIs(start, end, uris);
            }
        }
    }
}
