package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.RowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RowServiceImpl implements RowService {

    private final RowRepository rowRepository;

    @Override
    public Row init(final List<Column> columns) {
        return rowRepository.init(columns);
    }

    @Override
    public Row find(Long rowId) {
        return rowRepository.find(rowId);
    }
}
