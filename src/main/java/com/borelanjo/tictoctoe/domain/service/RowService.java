package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RowService {

    Row init(final List<Column> columns);

    Row find(final Long rowId);
}
